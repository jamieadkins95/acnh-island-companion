package com.jamieadkins.acnh.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jamieadkins.acnh.CritterListDecoration
import com.jamieadkins.acnh.R
import com.jamieadkins.acnh.bugs.BugItem
import com.jamieadkins.acnh.databinding.FragmentFishBinding
import com.jamieadkins.acnh.databinding.FragmentHomeBinding
import com.jamieadkins.acnh.domain.AvailableNowEntity
import com.jamieadkins.acnh.domain.bugs.BugEntity
import com.jamieadkins.acnh.domain.fish.FishEntity
import com.jamieadkins.acnh.fish.FishItem
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.Section
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class HomeFragment : DaggerFragment(), HomeContract.View {

    private var binding: FragmentHomeBinding? = null
    @Inject lateinit var presenter: HomeContract.Presenter

    private val groupAdapter = GroupAdapter<GroupieViewHolder>()
    private val currentlyAvailableSection = Section()
    private val fishSection = Section()
    private val bugsSection = Section()

    init {
        groupAdapter.add(currentlyAvailableSection)
        groupAdapter.add(fishSection)
        groupAdapter.add(bugsSection)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val newBinding = FragmentHomeBinding.inflate(inflater, container, false)
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

    override fun showCrittersAvailableNow(availableNow: AvailableNowEntity) {
        currentlyAvailableSection.update(listOf(CurrentlyAvailableItem(availableNow)))
        fishSection.update(listOf(SubHeaderItem(R.string.fish_available_now, availableNow.timeEvaluatedAt)) + availableNow.fish.map (::FishItem))
        bugsSection.update(listOf(SubHeaderItem(R.string.bugs_available_now, availableNow.timeEvaluatedAt)) + availableNow.bugs.map(::BugItem))
    }
}
