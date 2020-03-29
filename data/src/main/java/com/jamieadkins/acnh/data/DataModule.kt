package com.jamieadkins.acnh.data

import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides

@Module
class DataModule {

    @Provides
    fun firestore(): FirebaseFirestore = FirebaseFirestore.getInstance()
}