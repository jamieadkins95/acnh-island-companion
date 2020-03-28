package com.jamieadkins.acnh.data.fish

import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QuerySnapshot
import com.jamieadkins.acnh.domain.FishEntity
import com.jamieadkins.acnh.domain.FishRepository
import io.reactivex.Observable
import timber.log.Timber
import javax.inject.Inject

class FishRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore
) : FishRepository {

    override fun getFish(): Observable<List<FishEntity>> {
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
                        val result = doc.toObject(FirebaseFish::class.java)
                        FishEntity(
                            doc?.id ?: return@mapNotNull null,
                            result?.name ?: "",
                            result?.location ?: "",
                            result?.price ?: "",
                            result?.size ?: "",
                            result?.startHour ?: 0,
                            result?.endHour ?: 24,
                            "All Day",
                            result?.northernHemisphereMonths ?: emptyList()
                        )
                    } ?: emptyList()

                    emitter.onNext(fish)
                })
            emitter.setCancellable { listenerRegistration.remove() }
        }
    }
}