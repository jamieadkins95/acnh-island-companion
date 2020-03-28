package com.jamieadkins.acnh.predictions

import com.jamieadkins.acnh.domain.PredictionState

interface PredictionsContract {

    interface View {
        fun showLoadingIndicator()
        fun hideLoadingIndicator()
        fun showPredictions(predictions: PredictionState)
    }

    interface Presenter {
        fun onAttach(newView: View)
        fun onDetach()

        fun makePrediction(matchId: Long, teamId: Long)
    }
}