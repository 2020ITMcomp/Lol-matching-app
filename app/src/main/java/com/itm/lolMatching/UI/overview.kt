package com.itm.lolMatching.UI

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.itm.lolMatching.R
import com.itm.lolMatching.databinding.FragmentOverviewBinding


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [overview.newInstance] factory method to
 * create an instance of this fragment.
 */
class overview : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_overview, container, false)

        view.findViewById<Button>(R.id.fastSearchButton).setOnClickListener{
            Navigation.findNavController(view).navigate(R.id.action_overview_to_searchSummorer)
        }

        view.findViewById<Button>(R.id.detailSearchButton).setOnClickListener{
            Navigation.findNavController(view).navigate(R.id.action_overview_to_waitingRoom)
        }

        return view
    }
}