package com.jamieadkins.acnh.data.bugs

import android.annotation.SuppressLint
import androidx.annotation.VisibleForTesting
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QuerySnapshot
import com.jamieadkins.acnh.data.database.CaughtCritter
import com.jamieadkins.acnh.data.database.CaughtCritterDao
import com.jamieadkins.acnh.domain.HemisphereEntity
import com.jamieadkins.acnh.domain.HemisphereRepository
import com.jamieadkins.acnh.domain.bugs.BugEntity
import com.jamieadkins.acnh.domain.bugs.BugRepository
import io.reactivex.Observable
import io.reactivex.functions.Function3
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import javax.inject.Inject

class BugRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val caughtCritterDao: CaughtCritterDao,
    private val hemisphereRepository: HemisphereRepository
) : BugRepository {

    override fun getBugs(): Observable<List<BugEntity>> {
        return Observable.combineLatest(
            getFirebaseBugs(),
            caughtCritterDao.getAll().map { all -> all.filter { bugs -> bugs.caught }.map { it.id }.toSet() }.distinctUntilChanged(),
            hemisphereRepository.observeHemisphere(),
            Function3 { allBugs: List<Pair<String?, FirebaseBug?>>, caught: Set<String>, hemisphere: HemisphereEntity ->
                allBugs.mapNotNull { bug ->
                    val id = bug.first
                    val result = bug.second
                    mapToBugEntity(id, result, caught.contains(id), hemisphere)
                }
            }
        )
    }

    override fun getBug(id: String): Observable<BugEntity> {
        val getBug = Observable.create<FirebaseBug> { emitter ->
            val ref = firestore.collection("bugs").document(id)

            val listenerRegistration = ref
                .addSnapshotListener(EventListener<DocumentSnapshot> { snapshot, e ->
                    if (e != null) {
                        Timber.e(e)
                        emitter.onError(e)
                        return@EventListener
                    }

                    val result = snapshot?.toObject(FirebaseBug::class.java)
                    if (result != null) emitter.onNext(result)

                })
            emitter.setCancellable { listenerRegistration.remove() }
        }
        return Observable.combineLatest(
            getBug,
            caughtCritterDao.observerCaught(id).startWith(false),
            hemisphereRepository.observeHemisphere(),
            Function3 { bug: FirebaseBug, caught: Boolean, hemisphere: HemisphereEntity -> mapToBugEntity(id, bug, caught, hemisphere)!! }
        )
    }

    @SuppressLint("CheckResult")
    override fun toggleBugCaught(bugId: String) {
        caughtCritterDao.isCaught(bugId)
            .toSingle(false)
            .onErrorReturnItem(false)
            .subscribeOn(Schedulers.io())
            .subscribe { caught -> caughtCritterDao.insert(CaughtCritter(bugId, !caught)) }
    }

    private fun getFirebaseBugs() : Observable<List<Pair<String?, FirebaseBug?>>> {
        return Observable.create { emitter ->
            val ref = firestore.collection("bugs")

            val listenerRegistration = ref.orderBy("name", Query.Direction.ASCENDING)
                .addSnapshotListener(EventListener<QuerySnapshot> { snapshot, e ->
                    if (e != null) {
                        Timber.e(e)
                        emitter.onError(e)
                        return@EventListener
                    }

                    val bug = snapshot?.documents?.mapNotNull { doc ->
                        doc.id to doc.toObject(FirebaseBug::class.java)
                    } ?: emptyList()

                    emitter.onNext(bug)
                })
            emitter.setCancellable { listenerRegistration.remove() }
        }
    }

    private fun mapToBugEntity(id: String?, firebaseBug: FirebaseBug?, caught: Boolean, hemisphere: HemisphereEntity): BugEntity? {
        val months = if (hemisphere == HemisphereEntity.Northern) firebaseBug?.northernHemisphereMonths else firebaseBug?.southernHemisphereMonths
        return BugEntity(
            id ?: return null,
            firebaseBug?.name ?: "",
            firebaseBug?.image ?: "",
            firebaseBug?.location ?: "",
            firebaseBug?.price ?: "",
            firebaseBug?.startHour ?: 0,
            firebaseBug?.endHour ?: 24,
            firebaseBug?.timeRange ?: "All Day",
            months ?: emptyList(),
            caught
        )
    }

    companion object {

        @VisibleForTesting
        fun getMonthRanges(months: List<Int>): List<Pair<Int, Int>> {
            val ranges = mutableListOf<Pair<Int, Int>>()
            var lastMonth: Int? = null
            var currentStartMonth: Int? = null
            months.sorted().forEachIndexed { index, month ->
                when {
                    currentStartMonth == null -> { currentStartMonth = month }
                    lastMonth != null && (month != lastMonth?.plus(1)) -> { ranges.add(currentStartMonth!! to month) }
                    month == 12 -> ranges.add(currentStartMonth!! to month)
                }

                if (index == months.size - 1) {
                    ranges.add(currentStartMonth!! to month)
                }

                lastMonth = month
            }

            return ranges

            // Find all ranges
            // Combine all ranges that end in 12 and start with 1
        }
    }
}