package com.jamieadkins.acnh.data.bugs

import androidx.annotation.VisibleForTesting
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QuerySnapshot
import com.jamieadkins.acnh.domain.bugs.BugEntity
import com.jamieadkins.acnh.domain.bugs.BugRepository
import com.jamieadkins.acnh.domain.fish.FishEntity
import com.jamieadkins.acnh.domain.fish.FishRepository
import io.reactivex.Observable
import timber.log.Timber
import javax.inject.Inject

class BugRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore
) : BugRepository {

    override fun getBugs(): Observable<List<BugEntity>> {
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
                        val result = doc.toObject(FirebaseBug::class.java)
                        val months = result?.northernHemisphereMonths ?: emptyList()
                        BugEntity(
                            doc?.id ?: return@mapNotNull null,
                            result?.name ?: "",
                            result?.location ?: "",
                            result?.price ?: "",
                            result?.startHour ?: 0,
                            result?.endHour ?: 24,
                            result?.timeRange ?: "All Day",
                            months
                        )
                    } ?: emptyList()

                    emitter.onNext(bug)
                })
            emitter.setCancellable { listenerRegistration.remove() }
        }
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