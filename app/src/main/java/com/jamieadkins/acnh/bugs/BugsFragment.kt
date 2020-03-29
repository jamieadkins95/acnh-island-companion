package com.jamieadkins.acnh.bugs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jamieadkins.acnh.CritterListDecoration
import com.jamieadkins.acnh.R
import com.jamieadkins.acnh.databinding.FragmentBugBinding
import com.jamieadkins.acnh.domain.bugs.BugEntity
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
    }

    override fun onDestroyView() {
        presenter.onDetach()
        binding = null
        super.onDestroyView()
    }

    override fun showLoadingIndicator() {

    }

    override fun hideLoadingIndicator() {

    }

    override fun showBugs(bugs: List<BugEntity>) {
        groupAdapter.update(bugs.map(::BugItem))
    }
}
