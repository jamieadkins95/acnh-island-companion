package com.jamieadkins.acnh.predictions

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.jamieadkins.acnh.R
import com.jamieadkins.acnh.databinding.FragmentPredictionsBinding
import com.jamieadkins.acnh.domain.PredictionState
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import dagger.android.support.DaggerFragment
import timber.log.Timber
import javax.inject.Inject

class PredictionsFragment : DaggerFragment(), PredictionsContract.View {

    private var binding: FragmentPredictionsBinding? = null
    @Inject lateinit var presenter: PredictionsContract.Presenter

    private val groupAdapter = GroupAdapter<GroupieViewHolder>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val newBinding = FragmentPredictionsBinding.inflate(inflater, container, false)
        binding = newBinding
        return newBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.onAttach(this)
        groupAdapter.spanCount = resources.getInteger(R.integer.span)
        binding?.recyclerView?.apply {
            adapter = groupAdapter
            addItemDecoration(PredictionsDecoration())
            layoutManager = GridLayoutManager(context, groupAdapter.spanCount).apply {
                spanSizeLookup = groupAdapter.spanSizeLookup
            }
        }
    }

    override fun onDestroyView() {
        presenter.onDetach()
        binding = null
        super.onDestroyView()
    }

    override fun showLoadingIndicator() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun hideLoadingIndicator() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showPredictions(predictions: PredictionState) {
        val items = predictions.gameweeks.map { gameweeks -> gameweeks.matches.map(::PredictionItem) }.flatten()
        groupAdapter.update(items)
    }
}
