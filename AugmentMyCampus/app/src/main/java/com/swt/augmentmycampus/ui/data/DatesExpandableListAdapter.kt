package com.swt.augmentmycampus.ui.data

import android.content.Context;
import android.graphics.Color
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;
import com.swt.augmentmycampus.R


class DatesExpandableListAdapter(context: Context, expandableListTitle: List<String>,
                                 expandableListDetail: HashMap<String, List<Pair<String, Boolean>>>) : BaseExpandableListAdapter() {
    private val context: Context = context
    private val expandableListTitle: List<String> = expandableListTitle
    private val expandableListDetail: HashMap<String, List<Pair<String, Boolean>>> =
        expandableListDetail

    override fun getChild(listPosition: Int, expandedListPosition: Int): Any {
        return expandableListDetail[expandableListTitle[listPosition]]!![expandedListPosition]
    }

    override fun getChildId(listPosition: Int, expandedListPosition: Int): Long {
        return expandedListPosition.toLong()
    }

    override fun getChildView(listPosition: Int, expandedListPosition: Int,
                              isLastChild: Boolean, convertView: View?, parent: ViewGroup?): View? {
        var convertView: View? = convertView
        //val appointment = getChild(listPosition, expandedListPosition) as java.util.Map.Entry<String, Boolean>
        val appointment = getChild(listPosition, expandedListPosition) as Pair<String, Boolean>
        val date = appointment.first
        val wasAttended = appointment.second
        if (convertView == null) {
            val layoutInflater = context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            convertView = layoutInflater.inflate(R.layout.list_item_dates, null)
        }
        val expandedListTextView = convertView!!.findViewById(R.id.expandedListItemDates) as TextView
        expandedListTextView.text = date
        if(wasAttended)
            expandedListTextView.setBackgroundColor(Color.GREEN)
        else
            expandedListTextView.setBackgroundColor(Color.RED)
        return convertView
    }

    override fun getChildrenCount(listPosition: Int): Int {
        return expandableListDetail[expandableListTitle[listPosition]]!!.size
    }

    override fun getGroup(listPosition: Int): Any {
        return expandableListTitle[listPosition]
    }

    override fun getGroupCount(): Int {
        return expandableListTitle.size
    }

    override fun getGroupId(listPosition: Int): Long {
        return listPosition.toLong()
    }

    override fun getGroupView(listPosition: Int, isExpanded: Boolean,
                              convertView: View?, parent: ViewGroup?): View? {
        var convertView: View? = convertView
        val listTitle = getGroup(listPosition) as String
        if (convertView == null) {
            val layoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            convertView = layoutInflater.inflate(R.layout.list_group_dates, null)
        }
        val listTitleTextView = convertView!!.findViewById(R.id.listTitleDates) as TextView
        listTitleTextView.setTypeface(null, Typeface.BOLD)
        listTitleTextView.text = listTitle
        return convertView
    }

    override fun hasStableIds(): Boolean {
        return false
    }

    override fun isChildSelectable(listPosition: Int, expandedListPosition: Int): Boolean {
        return true
    }

}