package com.swt.augmentmycampus.ui.search

import androidx.lifecycle.ViewModel
import com.swt.augmentmycampus.api.model.SearchResultItem
import com.swt.augmentmycampus.businessLogic.DataBusinessLogic
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val dataBusinessLogic: DataBusinessLogic
) : ViewModel() {

    fun getSearchResults(queryString: String): List<SearchResultItem> {
        return dataBusinessLogic.getResultsForSearchQuery(queryString.trim())
    }


}