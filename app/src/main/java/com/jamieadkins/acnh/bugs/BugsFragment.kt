package com.jamieadkins.acnh.bugs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.jamieadkins.acnh.BuildConfig
import com.jamieadkins.acnh.CritterListDecoration
import com.jamieadkins.acnh.NavGraphDirections
import com.jamieadkins.acnh.R
import com.jamieadkins.acnh.databinding.FragmentBugBinding
import com.jamieadkins.acnh.domain.bugs.BugEntity
import com.jamieadkins.acnh.fish.FishItem
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class BugsFragment : DaggerFragment(), BugContract.View {

    private var binding: FragmentBugBinding? = null
    @Inject lateinit var presenter: BugContract.Presenter

    private val groupAdapter = GroupAdapter<GroupieViewHolder>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val newBinding = FragmentBugBinding.inflate(inflater, container, false)
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

        if (BuildConfig.DEBUG) {
            groupAdapter.setOnItemClickListener { item, _ ->
                when (item) {
                    is BugItem -> if (BuildConfig.DEBUG) findNavController().navigate(NavGraphDirections.toBugProfile(item.bug.id))
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

    override fun showBugs(bugs: List<BugEntity>) {
        groupAdapter.update(bugs.map { BugItem(it, presenter::onBugCaughtToggled) })
    }
}
