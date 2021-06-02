package com.swt.augmentmycampus.ui.search

import android.database.Cursor
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListAdapter
import android.widget.SearchView
import android.widget.SimpleCursorAdapter
import androidx.fragment.app.ListFragment
import com.swt.augmentmycampus.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : ListFragment() {

    private lateinit var searchViewModel: SearchViewModel
    private lateinit var listAdapter: SimpleCursorAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_search, container, false)
        searchViewModel = SearchViewModel()


        val searchField : SearchView = root.findViewById(R.id.search_field)

        searchField.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }

            override fun onQueryTextSubmit(query: String): Boolean {
                Log.i("search",query);
                onSearch(query)
                return true
            }
        })

        return root
    }

    fun onSearch(queryText: String) {
        this.setListAdapter(ArrayAdapter(this.requireContext(), android.R.layout.simple_list_item_1, listOf("line 1","line 2","line 3")))
    }
}