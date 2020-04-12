package com.jamieadkins.acnh.data.fish

import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QuerySnapshot
import com.jamieadkins.acnh.domain.fish.FishEntity
import com.jamieadkins.acnh.domain.fish.FishRepository
import io.reactivex.Observable
import io.reactivex.functions.BiFunction
import io.reactivex.subjects.BehaviorSubject
import timber.log.Timber
import javax.inject.Inject

class FishRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore
) : FishRepository {

    private val currentCaught = BehaviorSubject.createDefault<Set<String>>(emptySet())

    override fun getFish(): Observable<List<FishEntity>> {
        return Observable.combineLatest(
            getFirebaseFish(),
            currentCaught,
            BiFunction { allFish: List<Pair<String?, FirebaseFish?>>, caught: Set<String> ->
                allFish.mapNotNull { fish ->
                    val id = fish.first
                    val result = fish.second
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
                        result?.northernHemisphereMonths ?: emptyList(),
                        caught.contains(id)
                    )
                }
            }
        )
    }

    override fun toggleFishCaught(fishId: String) {
        val caught = (currentCaught.value ?: emptySet()).toMutableSet()
        if (caught.contains(fishId)) caught.remove(fishId) else caught.add(fishId)
        currentCaught.onNext(caught)
    }

    private fun getFirebaseFish() : Observable<List<Pair<String?, FirebaseFish?>>> {
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