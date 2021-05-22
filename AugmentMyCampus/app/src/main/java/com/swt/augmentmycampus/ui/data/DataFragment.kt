package com.swt.augmentmycampus.ui.data

import android.os.Bundle
import android.support.v4.media.session.MediaSessionCompat.Token.fromBundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.swt.augmentmycampus.R
import java.util.*
import dagger.hilt.android.AndroidEntryPoint
import org.json.JSONObject

@AndroidEntryPoint
class DataFragment : Fragment() {

    private lateinit var dataViewModel: DataViewModel

    val args: DataFragmentArgs by navArgs()

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        dataViewModel =
                ViewModelProvider(this).get( DataViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_data, container, false)
        val textView: TextView = root.findViewById(R.id.label_header)

        dataViewModel = ViewModelProvider(this).get(DataViewModel::class.java)

        val jsonObj = JSONObject(args.dataText)
        dataViewModel.dataText.value = jsonObj.getString("ects")

        val ectsValueTextView: TextView = root.findViewById(R.id.label_ects_value)
        ectsValueTextView.text = jsonObj.getString("ects")

        val  titleValueTextView: TextView = root.findViewById(R.id.label_header)
        titleValueTextView.text = jsonObj.getString("title")

        val tvDataText: TextView = root.findViewById(R.id.fragment_data_text)
        dataViewModel.dataText.observe(
                requireActivity(),
                Observer { tvDataText.text = it.toString() })

        var expandableListViewContent = root.findViewById<View>(R.id.expandableListViewContent) as ExpandableListView
        var expandableListDetailContent = HashMap<String, List<String>>()
        expandableListDetailContent.put("Content",  Collections.singletonList("test blablabla \nnew line blABLA"))
        var expandableListTitleContent = ArrayList(expandableListDetailContent!!.keys)
        var expandableListAdapterContent = ContentExpandableListAdapter(requireContext().applicationContext, expandableListTitleContent!!, expandableListDetailContent!!)
        CreateListView(expandableListViewContent, expandableListAdapterContent, expandableListTitleContent, expandableListDetailContent);

        var expandableListViewDates = root.findViewById<View>(R.id.expandableListViewDates) as ExpandableListView
        var expandableListDetailDates = HashMap<String, List<String>>()
        expandableListDetailDates.put("Dates",  Collections.singletonList("test blablabla \nnew line blABLA"))
        var expandableListTitleDates = ArrayList(expandableListDetailDates!!.keys)
        var expandableListAdapterDates = DatesExpandableListAdapter(requireContext().applicationContext, expandableListTitleDates!!, expandableListDetailDates!!)
        CreateListView(expandableListViewDates, expandableListAdapterDates, expandableListTitleDates, expandableListDetailDates);

        return root
    }

    private fun CreateListView(expandableListView: ExpandableListView, expandableListAdapter: ExpandableListAdapter, expandableListTitle: ArrayList<String>, expandableListDetail: HashMap<String, List<String>>) : ExpandableListView {
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
        return expandableListView;
    }
}