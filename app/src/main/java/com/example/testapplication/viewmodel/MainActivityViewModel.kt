package com.example.testapplication.viewmodel

import android.util.Log
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import com.example.testapplication.datamodel.BannerDataModel
import com.example.testapplication.datamodel.MESSAGES
import com.example.testapplication.datamodel.SearchDataModel
import com.example.testapplication.usecase.MainActivityUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import java.util.Collections
import javax.inject.Inject


@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val mainActivityUseCase: MainActivityUseCase,
) : ViewModel(), DefaultLifecycleObserver {

    var setBannerList = MutableLiveData<ArrayList<BannerDataModel>>(arrayListOf())
    val setBannerListLiveData: LiveData<ArrayList<BannerDataModel>> get() = setBannerList
    val searchList: MutableLiveData<MutableList<SearchDataModel>> = MutableLiveData(mutableListOf())
    var searchListOriginal: List<SearchDataModel> = listOf()
    var filteredlist: MutableList<SearchDataModel> = mutableListOf()
    var bottomSheetTitlelist: ArrayList<String> = arrayListOf()
    var countData = ""
    var showBottomSheetDialog = MutableLiveData<MESSAGES>(MESSAGES.NO_MESSAGE)
    val showBottomSheetDialogLiveData: LiveData<MESSAGES> get() = showBottomSheetDialog


    init {
        getBannerListing()
        getSearchListing(0)
        getAllPatientsFromApi()
    }

    fun getBannerListing() {
        viewModelScope.launch(IO) {
            val response = mainActivityUseCase.getAllBannerListing()
            setBannerList.postValue(response)

        }
    }

    fun flowUsage() {
        viewModelScope.launch(IO) {
            mainActivityUseCase.flowUsage().collect { data ->
                Log.e("data_flowss::", ":::->" + data)
            }
        }
    }

    fun floatingActionButtonClick() {
        if (!filteredlist.isNullOrEmpty() && filteredlist.size > 0) {
            bottomSheetTitlelist.clear()
            for (item in filteredlist) {
                bottomSheetTitlelist.add(item.title.trim())
            }
            countData = mainActivityUseCase.provideBottomSheetCountData(bottomSheetTitlelist)
            showBottomSheetDialog.postValue(MESSAGES.BOTTOM_SHEET_DATA_FOUND)
        } else {
            showBottomSheetDialog.postValue(MESSAGES.BOTTOM_SHEET_NO_DATA_FOUND)
        }
    }

    fun getSearchListing(position: Int) {
        filteredlist.clear()
        searchListOriginal = listOf()
        viewModelScope.launch(Dispatchers.IO) {
            val response = mainActivityUseCase.getAllSearchListing(position)
            response?.forEach {
                it?.run {
                    filteredlist.add(it)

                }
            }
            searchListOriginal = filteredlist.toList()
            searchList.postValue(filteredlist)

        }
    }

    fun searchListItemClick(position: Int) {
        Log.e("list_item_clicked", ":::" + position)
    }

    fun performFilterOperation(text: String) {
        filteredlist.clear()

        for (item in searchListOriginal) {
            if (text.isNotEmpty()) {
                if (!item.title.isNullOrEmpty() && item.title.lowercase()
                        .contains(text.lowercase())
                ) {
                    filteredlist.add(item)
                }
            } else {
                filteredlist.add(item)
            }
        }
        searchList.postValue(filteredlist)
    }


    fun getAllPatientsFromApi() {
        viewModelScope.launch(IO) {
            val response = mainActivityUseCase.getAllPatientsListFromApi(
                true, 787
            )
            Log.e("response:::",response.toString())
        }
    }
}