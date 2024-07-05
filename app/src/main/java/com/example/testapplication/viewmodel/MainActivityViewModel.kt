package com.example.testapplication.viewmodel

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testapplication.datamodel.BannerDataModel

import com.example.testapplication.datamodel.SearchDataModel
import com.example.testapplication.usecase.MainActivityUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val mainActivityUseCase: MainActivityUseCase,
) : ViewModel(), DefaultLifecycleObserver {

    private var _bannerListFlow = MutableStateFlow<List<BannerDataModel>>(emptyList())
    val bannerListFlow = _bannerListFlow.asStateFlow()

    var searchListOriginal: List<SearchDataModel> = listOf()
    var bottomSheetTitlelist: ArrayList<String> = arrayListOf()
    var countData=""

    val _searchListFlowJetpack: MutableStateFlow<MutableList<SearchDataModel>> = MutableStateFlow(mutableListOf())
    val _isBottomSheetDataPresent: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val _bottomSheetMessage: MutableStateFlow<String> = MutableStateFlow("")
    val _searchTextJetpack:MutableState<TextFieldValue> = mutableStateOf(TextFieldValue(""))
    val _isShowBottomSheet: MutableStateFlow<Boolean> = MutableStateFlow(false)

    fun getBannerListing() {
        viewModelScope.launch(Dispatchers.IO) {
            val response = mainActivityUseCase.getAllBannerListing()
            _bannerListFlow.update { response.toList() }

        }
    }

    fun floatingActionButtonClick() {
        if (!_searchListFlowJetpack.value.isNullOrEmpty() && _searchListFlowJetpack.value.size > 0) {

            bottomSheetTitlelist.clear()

            for (item in _searchListFlowJetpack.value) {
                bottomSheetTitlelist.add(item.title.trim())
            }
            countData=mainActivityUseCase.provideBottomSheetCountData(bottomSheetTitlelist)



            val message="List item ="+bottomSheetTitlelist.toString()+"\n"+
                    "List item count="+bottomSheetTitlelist.size +" items"+countData+"\n\n\n"

            _bottomSheetMessage.update { message}
            _isBottomSheetDataPresent.update { true }
            _isShowBottomSheet.update { true }
        }
        else{
            _isShowBottomSheet.update { true }
            _isBottomSheetDataPresent.update { false }
        }
    }

    fun getSearchListing(position: Int) {
        searchListOriginal = listOf()
        viewModelScope.launch(Dispatchers.IO) {
            val response = mainActivityUseCase.getAllSearchListing(position)
            _searchListFlowJetpack.value=response
            searchListOriginal= response
        }
        _isBottomSheetDataPresent.update { true }
        _isShowBottomSheet.update { false }
    }

    fun performFilterOperation(text: String?) {
        if (text.isNullOrEmpty()) {
            _searchListFlowJetpack.update {
                searchListOriginal.toMutableList()
            }
            return
        }

        val filtered = searchListOriginal.filter {
            it.title.lowercase().contains(text.lowercase())
        }
        _searchListFlowJetpack.update {
            filtered.toMutableList()
        }

        _isBottomSheetDataPresent.update { true }
        _isShowBottomSheet.update { false }
    }

    init {
        getBannerListing()
    }

}