package com.swt.augmentmycampus.ui.search

import androidx.lifecycle.ViewModel
import com.swt.augmentmycampus.api.model.LectureInformation
import com.swt.augmentmycampus.api.model.SearchResultItem
import com.swt.augmentmycampus.businessLogic.DataBusinessLogic
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val dataBusinessLogic: DataBusinessLogic
) : ViewModel() {

    fun getSearchResults(queryString: String): List<LectureInformation> {
        return dataBusinessLogic.getResultsForSearchQuery(queryString.trim())
    }
    fun getTextData(url: String): LectureInformation {
        return dataBusinessLogic.getLectureInformationFromTag(url)
    }
}