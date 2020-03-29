package com.jamieadkins.acnh.data.bugs

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
                        BugEntity(
                            doc?.id ?: return@mapNotNull null,
                            result?.name ?: "",
                            result?.location ?: "",
                            result?.price ?: "",
                            result?.startHour ?: 0,
                            result?.endHour ?: 24,
                            result?.timeRange ?: "All Day",
                            result?.northernHemisphereMonths ?: emptyList()
                        )
                    } ?: emptyList()

                    emitter.onNext(bug)
                })
            emitter.setCancellable { listenerRegistration.remove() }
        }
    }
}