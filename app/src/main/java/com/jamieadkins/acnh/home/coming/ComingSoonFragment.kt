package com.jamieadkins.acnh.home.coming

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.jamieadkins.acnh.CritterListDecoration
import com.jamieadkins.acnh.R
import com.jamieadkins.acnh.bugs.BugItem
import com.jamieadkins.acnh.databinding.FragmentCritterListBinding
import com.jamieadkins.acnh.domain.BugFishSummaryEntity
import com.jamieadkins.acnh.fish.FishItem
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import com.xwray.groupie.kotlinandroidextensions.Item
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class ComingSoonFragment : DaggerFragment(), ComingSoonContract.View {

    private var binding: FragmentCritterListBinding? = null
    @Inject lateinit var presenter: ComingSoonContract.Presenter

    private val groupAdapter = GroupAdapter<GroupieViewHolder>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val newBinding = FragmentCritterListBinding.inflate(inflater, container, false)
        binding = newBinding
        return newBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val navController = findNavController()
        val appBarConfiguration = AppBarConfiguration(navController.graph)
        binding?.toolbar?.setupWithNavController(navController, appBarConfiguration)
        binding?.toolbar?.setTitle(R.string.coming_soon)
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

    override fun showCrittersComingSoon(comingSoon: BugFishSummaryEntity) {
        groupAdapter.update(mapCrittersToItems(comingSoon))
    }

    private fun mapCrittersToItems(critters: BugFishSummaryEntity): List<Item> {
        return critters.fish.map { FishItem(it, presenter::onFishCaughtToggled) } + critters.bugs.map { BugItem(it, presenter::onBugCaughtToggled) }
    }
}
