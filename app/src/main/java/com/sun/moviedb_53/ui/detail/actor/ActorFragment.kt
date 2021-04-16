package com.sun.moviedb_53.ui.detail.actor

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import com.sun.moviedb_53.R

class ActorFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_actor, container, false)
    }

    companion object {
        private const val BUNDLE_ID_ACTOR_DETAIL = "BUNDLE_ID_ACTOR_DETAIL"

        fun newInstance(id: Int) = ActorFragment().apply {
            arguments = bundleOf(BUNDLE_ID_ACTOR_DETAIL to id)
        }
    }
}
