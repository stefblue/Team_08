package com.swt.augmentmycampus.ui.search

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.ListFragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.swt.augmentmycampus.R
import com.swt.augmentmycampus.api.model.SearchResultItem
import com.swt.augmentmycampus.businessLogic.CouldNotReachServerException
import com.swt.augmentmycampus.businessLogic.InvalidUrlException
import com.swt.augmentmycampus.ui.camera.CameraFragmentDirections
import dagger.hilt.android.AndroidEntryPoint
import java.lang.Exception

@AndroidEntryPoint
class SearchFragment : ListFragment() {

    private lateinit var searchViewModel: SearchViewModel
    private lateinit var resultList: List<SearchResultItem>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_search, container, false)
        searchViewModel = ViewModelProvider(this).get(SearchViewModel::class.java)

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

    private fun onSearch(queryText: String) {
        resultList = ArrayList()

        try {
            resultList = searchViewModel.getSearchResults(queryText)
        } catch (ex: Exception) {
            Toast.makeText(this.requireContext(), ex.message, Toast.LENGTH_LONG).show()
        }

        listAdapter = ArrayAdapter(this.requireContext(), android.R.layout.simple_list_item_1, resultList)
    }
}