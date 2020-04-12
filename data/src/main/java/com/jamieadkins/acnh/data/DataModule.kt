package com.jamieadkins.acnh.data

import android.content.Context
import com.google.firebase.firestore.FirebaseFirestore
import com.jamieadkins.acnh.data.database.CaughtCritterDao
import com.jamieadkins.acnh.data.database.DatabaseProvider
import dagger.Module
import dagger.Provides

@Module
class DataModule {

    @Provides
    fun firestore(): FirebaseFirestore = FirebaseFirestore.getInstance()

    @Provides
    fun caughtDao(@ApplicationContext context: Context): CaughtCritterDao = DatabaseProvider.getInstance(context).caughtDao()
}