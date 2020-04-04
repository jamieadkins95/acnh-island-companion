package com.jamieadkins.acnh.bugs.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import coil.api.load
import com.jamieadkins.acnh.CritterListDecoration
import com.jamieadkins.acnh.R
import com.jamieadkins.acnh.databinding.FragmentBugBinding
import com.jamieadkins.acnh.databinding.FragmentBugProfileBinding
import com.jamieadkins.acnh.domain.bugs.BugEntity
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class BugProfileFragment : DaggerFragment(), BugProfileContract.View {

    private val args: BugProfileFragmentArgs by navArgs()
    private var binding: FragmentBugProfileBinding? = null
    @Inject lateinit var presenter: BugProfileContract.Presenter

    private val groupAdapter = GroupAdapter<GroupieViewHolder>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val newBinding = FragmentBugProfileBinding.inflate(inflater, container, false)
        binding = newBinding
        return newBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.onAttach(this)
        presenter.init(args.id)
        groupAdapter.spanCount = resources.getInteger(R.integer.span)
    }

    override fun onDestroyView() {
        presenter.onDetach()
        binding = null
        super.onDestroyView()
    }

    override fun showLoadingIndicator() {
//        binding?.loadingIndicator?.visibility = View.VISIBLE
    }

    override fun hideLoadingIndicator() {
//        binding?.loadingIndicator?.visibility = View.GONE
    }

    override fun showBug(bug: BugEntity) {
        binding?.image?.load(bug.imageUrl)
    }
}
