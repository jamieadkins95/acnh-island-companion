package com.jamieadkins.acnh.data.fish

import android.annotation.SuppressLint
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QuerySnapshot
import com.jamieadkins.acnh.data.database.CaughtCritter
import com.jamieadkins.acnh.data.database.CaughtCritterDao
import com.jamieadkins.acnh.domain.HemisphereEntity
import com.jamieadkins.acnh.domain.HemisphereRepository
import com.jamieadkins.acnh.domain.fish.FishEntity
import com.jamieadkins.acnh.domain.fish.FishRepository
import io.reactivex.Observable
import io.reactivex.functions.Function3
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import javax.inject.Inject

class FishRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val caughtCritterDao: CaughtCritterDao,
    private val hemisphereRepository: HemisphereRepository
) : FishRepository {

    override fun getFish(): Observable<List<FishEntity>> {
        return Observable.combineLatest(
            getFirebaseFish(),
            caughtCritterDao.getAll().map { all -> all.filter { fish -> fish.caught }.map { it.id }.toSet() }.distinctUntilChanged(),
            hemisphereRepository.observeHemisphere(),
            Function3 { allFish: List<Pair<String?, FirebaseFish?>>, caught: Set<String>, hemisphere: HemisphereEntity ->
                allFish.mapNotNull { fish ->
                    val id = fish.first
                    val result = fish.second
                    val months = if (hemisphere == HemisphereEntity.Northern) result?.northernHemisphereMonths else result?.southernHemisphereMonths
                    FishEntity(
                        id ?: return@mapNotNull null,
                        result?.name ?: "",
                        result?.image ?: "",
                        result?.location ?: "",
                        result?.price ?: "",
                        result?.size ?: "",
                        result?.startHour ?: 0,
                        result?.endHour ?: 24,
                        result?.timeRange ?: "All Day",
                        months ?: emptyList(),
                        caught.contains(id)
                    )
                }
            }
        )
    }

    @SuppressLint("CheckResult")
    override fun toggleFishCaught(fishId: String) {
        caughtCritterDao.isCaught(fishId)
            .toSingle(false)
            .onErrorReturnItem(false)
            .subscribeOn(Schedulers.io())
            .subscribe { caught -> caughtCritterDao.insert(CaughtCritter(fishId, !caught)) }
    }

    private fun getFirebaseFish(): Observable<List<Pair<String?, FirebaseFish?>>> {
        return Observable.create { emitter ->
            val ref = firestore
                .collection("fish")

            val listenerRegistration = ref.orderBy("name", Query.Direction.ASCENDING)
                .addSnapshotListener(EventListener<QuerySnapshot> { snapshot, e ->
                    if (e != null) {
                        Timber.e(e)
                        emitter.onError(e)
                        return@EventListener
                    }

                    val fish = snapshot?.documents?.mapNotNull { doc ->
                        doc.id to doc.toObject(FirebaseFish::class.java)
                    } ?: emptyList()

                    emitter.onNext(fish)
                })
            emitter.setCancellable { listenerRegistration.remove() }
        }
    }
}