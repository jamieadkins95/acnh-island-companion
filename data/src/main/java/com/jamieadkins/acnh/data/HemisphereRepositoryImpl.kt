package com.jamieadkins.acnh.data

import android.content.Context
import androidx.preference.PreferenceManager
import com.f2prateek.rx.preferences2.RxSharedPreferences
import com.jamieadkins.acnh.domain.HemisphereEntity
import com.jamieadkins.acnh.domain.HemisphereRepository
import io.reactivex.Observable
import javax.inject.Inject

class HemisphereRepositoryImpl @Inject constructor(@ApplicationContext context: Context): HemisphereRepository {

    private val preferences = PreferenceManager.getDefaultSharedPreferences(context)
    private val rxPreferences = RxSharedPreferences.create(preferences)

    override fun observeHemisphere(): Observable<HemisphereEntity> {
        return rxPreferences.getString(KEY, NORTHERN).asObservable()
            .map { hemisphere ->
                when (hemisphere) {
                    NORTHERN -> HemisphereEntity.Northern
                    SOUTHERN -> HemisphereEntity.Southern
                    else -> HemisphereEntity.Northern
                }
            }
    }

    override fun setHemisphere(hemisphere: HemisphereEntity) {
        val value = when (hemisphere) {
            HemisphereEntity.Northern -> NORTHERN
            HemisphereEntity.Southern -> SOUTHERN
        }
        rxPreferences.getString(KEY).set(value)
    }
    
    companion object {
        private const val KEY = "hemisphere"
        private const val NORTHERN = "northern"
        private const val SOUTHERN = "southern"
    }
}