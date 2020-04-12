package com.jamieadkins.acnh.fish

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jamieadkins.acnh.CritterListDecoration
import com.jamieadkins.acnh.R
import com.jamieadkins.acnh.databinding.FragmentFishBinding
import com.jamieadkins.acnh.domain.fish.FishEntity
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class FishFragment : DaggerFragment(), FishContract.View {

    private var binding: FragmentFishBinding? = null
    @Inject lateinit var presenter: FishContract.Presenter

    private val groupAdapter = GroupAdapter<GroupieViewHolder>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val newBinding = FragmentFishBinding.inflate(inflater, container, false)
        binding = newBinding
        return newBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.onAttach(this)
        groupAdapter.spanCount = resources.getInteger(R.integer.span)
        binding?.recyclerView?.apply {
            adapter = groupAdapter
            addItemDecoration(CritterListDecoration())
        }
    }

    override fun onDestroyView() {
        presenter.onDetach()
        binding = null
        super.onDestroyView()
    }

    override fun showLoadingIndicator() {
        binding?.loadingIndicator?.visibility = View.VISIBLE
    }

    override fun hideLoadingIndicator() {
        binding?.loadingIndicator?.visibility = View.GONE
    }

    override fun showFish(fish: List<FishEntity>) {
        groupAdapter.update(fish.map { FishItem(it, presenter::onFishCaughtToggled) })
    }
}
