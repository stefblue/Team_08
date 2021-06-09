package com.swt.augmentmycampus.ui.data

import android.os.Build
import android.os.Bundle
import android.text.format.DateUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.swt.augmentmycampus.R
import dagger.hilt.android.AndroidEntryPoint
import org.json.JSONArray
import org.json.JSONObject
import java.time.Duration
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.collections.HashMap


@AndroidEntryPoint
class DataFragment : Fragment() {

    //private lateinit var dataViewModel: DataViewModel

    val args: DataFragmentArgs by navArgs()

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //dataViewModel =
        //        ViewModelProvider(this).get( DataViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_data, container, false)
        val textView: TextView = root.findViewById(R.id.label_header)

        /*dataViewModel = ViewModelProvider(this).get(DataViewModel::class.java)
        dataViewModel.dataText.value = jsonObj.getString("ects")*/

        var contentString = ""
        var listOfDates : MutableList<String> = ArrayList()
        if(args.dataText != null && !args.dataText.isEmpty()) {
            val jsonObj = JSONObject(args.dataText)

            val ectsValueTextView: TextView = root.findViewById(R.id.label_ects_value)
            ectsValueTextView.text = jsonObj.getString("ects")

            val titleValueTextView: TextView = root.findViewById(R.id.label_header)
            titleValueTextView.text = jsonObj.getString("title")

            val numberValueTextView: TextView = root.findViewById(R.id.label_number_value)
            numberValueTextView.text = jsonObj.getString("number")

            val semesterValueTextView: TextView = root.findViewById(R.id.label_semester_value)
            semesterValueTextView.text= jsonObj.getString("semester")

            val lecturerValueTextView: TextView = root.findViewById(R.id.label_lecturer_value)
            lecturerValueTextView.text = jsonObj.getString("lecturer")

            contentString = jsonObj.getString("content")

            var jArray : JSONArray = jsonObj.getJSONArray("dates")



            if (jArray != null) {
                for(i in 0 until jArray.length()) {
                    val tmpObject : JSONObject = jArray.getJSONObject(i)

                    val startDateTime : LocalDateTime = LocalDateTime.parse(tmpObject.getString("key"), DateTimeFormatter.ISO_LOCAL_DATE_TIME)
                    val duration : Duration = Duration.parse(tmpObject.getString("value"))
                    val formatter : DateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm")
                    val tmpString : String = startDateTime.format(formatter).plus(" for ").plus(DateUtils.formatElapsedTime(duration.seconds))

                    listOfDates.add(tmpString)
                }
            }
            else {
                throw Exception("Backend didn't send any date information.")
            }
        }

        /*val tvDataText: TextView = root.findViewById(R.id.fragment_data_text)
        dataViewModel.dataText.observe(
                requireActivity(),
                Observer { tvDataText.text = it.toString() })*/

        var expandableListViewContent = root.findViewById<View>(R.id.expandableListViewContent) as ExpandableListView
        var expandableListDetailContent = HashMap<String, List<String>>()
        expandableListDetailContent.put("Content",  Collections.singletonList(contentString))
        var expandableListTitleContent = ArrayList(expandableListDetailContent!!.keys)
        var expandableListAdapterContent = ContentExpandableListAdapter(requireContext().applicationContext, expandableListTitleContent!!, expandableListDetailContent!!)
        CreateListView(expandableListViewContent, expandableListAdapterContent)

        var expandableListViewDates = root.findViewById<View>(R.id.expandableListViewDates) as ExpandableListView
        var expandableListDetailDates = HashMap<String, List<Pair<String, Boolean>>>()
        var appointments = ArrayList<Pair<String, Boolean>>()
        if (listOfDates.size >= 2) {
            for(i in 0 until 1)
                appointments.add(Pair(listOfDates[i], true))
            for(i in 1 until listOfDates.size)
                appointments.add(Pair(listOfDates[i], false))
        }
        expandableListDetailDates.put("Dates",  appointments)
        var expandableListTitleDates = ArrayList(expandableListDetailDates!!.keys)
        var expandableListAdapterDates = DatesExpandableListAdapter(requireContext().applicationContext, expandableListTitleDates!!, expandableListDetailDates!!)
        CreateListView(expandableListViewDates, expandableListAdapterDates)
        
        var futureAppointments = ArrayList<Pair<String, Boolean>>()
        val formatter : DateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm")
        var currentDateDeft = LocalDateTime.now()
        var currentDateFormatted = currentDateDeft.format(formatter)

        for(i in 0 until appointments.size)
        {
            if (appointments[i].first < currentDateFormatted)
            {
                futureAppointments.add(appointments[i])
            }
        }

        return root
    }

    private fun CreateListView(expandableListView: ExpandableListView, expandableListAdapter: ExpandableListAdapter) : ExpandableListView {
        expandableListView!!.setAdapter(expandableListAdapter)

        return expandableListView;
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