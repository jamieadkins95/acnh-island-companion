package com.jamieadkins.acnh.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.jamieadkins.acnh.CritterListDecoration
import com.jamieadkins.acnh.NavGraphDirections
import com.jamieadkins.acnh.R
import com.jamieadkins.acnh.bugs.BugItem
import com.jamieadkins.acnh.databinding.FragmentHomeBinding
import com.jamieadkins.acnh.domain.BugFishSummaryEntity
import com.jamieadkins.acnh.domain.bugs.BugEntity
import com.jamieadkins.acnh.domain.fish.FishEntity
import com.jamieadkins.acnh.fish.FishItem
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.Section
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import com.xwray.groupie.kotlinandroidextensions.Item
import dagger.android.support.DaggerFragment
import timber.log.Timber
import javax.inject.Inject

class HomeFragment : DaggerFragment(), HomeContract.View {

    private var binding: FragmentHomeBinding? = null
    @Inject lateinit var presenter: HomeContract.Presenter

    private val groupAdapter = GroupAdapter<GroupieViewHolder>()
    private val currentlyAvailableSection = Section()
    private val goingSoonSection = Section()
    private val comingSoonSection = Section()
    private val newThisMonthSection = Section()
    private val fishSection = Section()
    private val bugsSection = Section()

    init {
        groupAdapter.add(currentlyAvailableSection)
        groupAdapter.add(goingSoonSection)
        groupAdapter.add(comingSoonSection)
        groupAdapter.add(newThisMonthSection)
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

        groupAdapter.setOnItemClickListener { item, _ ->
            when (item) {
                is BugFishSummaryItem -> {
                    val directions = when (item.titleText) {
                        R.string.going_away_soon -> NavGraphDirections.toGoingSoon()
                        R.string.coming_soon -> NavGraphDirections.toComingSoon()
                        R.string.new_this_month -> NavGraphDirections.toNewThisMonth()
                        else -> return@setOnItemClickListener
                    }
                    findNavController().navigate(directions)
                }
            }
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

    override fun showCrittersAvailableNow(bugFishSummary: BugFishSummaryEntity) {
        currentlyAvailableSection.update(listOf(BugFishSummaryItem(bugFishSummary, R.string.bugs_available_now, true)))
        fishSection.update(listOf(SubHeaderItem(R.string.fish_available_now, bugFishSummary.timeEvaluatedAt)) + bugFishSummary.fish.map (::FishItem))
        bugsSection.update(listOf(SubHeaderItem(R.string.bugs_available_now, bugFishSummary.timeEvaluatedAt)) + bugFishSummary.bugs.map(::BugItem))
    }

    override fun showCrittersGoingSoon(goingSoon: BugFishSummaryEntity) {
        val subheader = listOf(SubHeaderWithSubTitleItem(R.string.going_away_soon, R.string.unavailable_next_month))
        val summary = listOf(BugFishSummaryItem(goingSoon, R.string.going_away_soon, false))
        goingSoonSection.update(subheader + summary + mapCrittersToItems(goingSoon))
        Timber.d("Fish going away soon: ${goingSoon.fish.map { it.name }.joinToString()}" )
        Timber.d("Bugs going away soon: ${goingSoon.bugs.map { it.name }.joinToString()}" )
    }

    override fun showCrittersComingSoon(comingSoon: BugFishSummaryEntity) {
        val subheader = listOf(SubHeaderWithSubTitleItem(R.string.coming_soon, R.string.available_next_month))
        val summary = listOf(BugFishSummaryItem(comingSoon, R.string.coming_soon, false))
        comingSoonSection.update(subheader + summary + mapCrittersToItems(comingSoon))
        Timber.d("Fish coming soon: ${comingSoon.fish.map { it.name }.joinToString()}" )
        Timber.d("Bugs coming soon: ${comingSoon.bugs.map { it.name }.joinToString()}" )
    }

    override fun showCrittersNewThisMonth(newThisMonth: BugFishSummaryEntity) {
        val subheader = listOf(SubHeaderWithSubTitleItem(R.string.new_this_month, R.string.new_this_month_subtitle))
        val summary = listOf(BugFishSummaryItem(newThisMonth, R.string.new_this_month, false))
        newThisMonthSection.update(subheader + summary + mapCrittersToItems(newThisMonth))
        Timber.d("New fish this month: ${newThisMonth.fish.map { it.name }.joinToString()}" )
        Timber.d("New bugs this month: ${newThisMonth.bugs.map { it.name }.joinToString()}" )
    }

    private fun mapCrittersToItems(critters: BugFishSummaryEntity): List<Item> {
        return critters.fish.map(::FishItem) + critters.bugs.map(::BugItem)
    }
}
