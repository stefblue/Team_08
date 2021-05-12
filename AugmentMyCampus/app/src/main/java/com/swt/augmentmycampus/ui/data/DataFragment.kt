package com.swt.augmentmycampus.ui.data

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ExpandableListAdapter
import android.widget.ExpandableListView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.swt.augmentmycampus.R
import java.util.*

class DataFragment : Fragment() {

    private lateinit var dataViewModel: DataViewModel
    var expandableListView: ExpandableListView? = null
    var expandableListAdapter: ExpandableListAdapter? = null
    var expandableListTitle: ArrayList<String>? = null
    var expandableListDetail: HashMap<String, List<String>>? = null

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        dataViewModel =
                ViewModelProvider(this).get( DataViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_data, container, false)
        val textView: TextView = root.findViewById(R.id.label_header)
        dataViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })


        expandableListView = root.findViewById<View>(R.id.expandableListView) as ExpandableListView
        expandableListDetail =  HashMap<String, List<String>>()
        expandableListDetail!!.put("test",  Collections.singletonList("test blablabla \nnew line blABLA"))
        expandableListTitle = ArrayList(expandableListDetail!!.keys)
        expandableListAdapter = DataExpandableListAdapter(requireContext().applicationContext, expandableListTitle!!, expandableListDetail!!)
        expandableListView!!.setAdapter(expandableListAdapter)
        expandableListView!!.setOnGroupExpandListener { groupPosition ->
            Toast.makeText(requireContext().applicationContext, expandableListTitle!!.get(groupPosition) + " List Expanded.",
                    Toast.LENGTH_SHORT).show()
        }
        expandableListView!!.setOnGroupCollapseListener { groupPosition ->
            Toast.makeText(requireContext().applicationContext, expandableListTitle!!.get(groupPosition) + " List Collapsed.",
                    Toast.LENGTH_SHORT).show()
        }
        expandableListView!!.setOnChildClickListener { parent, v, groupPosition, childPosition, id ->
            Toast.makeText(
                    requireContext().applicationContext,
                    expandableListTitle!!.get(groupPosition) + " -> "
                            + expandableListDetail!![expandableListTitle!!.get(groupPosition)]!![childPosition], Toast.LENGTH_SHORT
            ).show()
            false
        }




        return root

    }
}