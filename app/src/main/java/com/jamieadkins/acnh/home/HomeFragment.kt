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
                        R.string.currently_available -> NavGraphDirections.toAvailableNow()
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

    override fun showCrittersAvailableNow(bugFishSummary: BugFishSummaryEntity, rarestCritter: Any?) {
        val summary = listOf(BugFishSummaryItem(bugFishSummary, R.string.currently_available, true))
        currentlyAvailableSection.update(summary + mapCritterToItem(rarestCritter))
    }

    override fun showCrittersGoingSoon(goingSoon: BugFishSummaryEntity, rarestCritter: Any?) {
        val summary = listOf(BugFishSummaryItem(goingSoon, R.string.going_away_soon, false))
        goingSoonSection.update(summary + mapCritterToItem(rarestCritter))
    }

    override fun showCrittersComingSoon(comingSoon: BugFishSummaryEntity, rarestCritter: Any?) {
        val summary = listOf(BugFishSummaryItem(comingSoon, R.string.coming_soon, false))
        comingSoonSection.update(summary + mapCritterToItem(rarestCritter))
    }

    override fun showCrittersNewThisMonth(newThisMonth: BugFishSummaryEntity, rarestCritter: Any?) {
        val summary = listOf(BugFishSummaryItem(newThisMonth, R.string.new_this_month, false))
        newThisMonthSection.update(summary + mapCritterToItem(rarestCritter))
    }

    private fun mapCrittersToItems(critters: BugFishSummaryEntity): List<Item> {
        return critters.fish.map(::FishItem) + critters.bugs.map(::BugItem)
    }

    private fun mapCritterToItem(critter: Any?): List<Item> {
        return when (critter) {
            is BugEntity -> listOf(BugItem(critter))
            is FishEntity -> listOf(FishItem(critter))
            else -> emptyList()
        }
    }
}
