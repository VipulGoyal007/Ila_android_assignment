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
import com.example.testapplication.localdb.CartMedicine
import com.example.testapplication.localdb.LocalDbUseCase
import com.example.testapplication.usecase.MainActivityUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Collections
import javax.inject.Inject


@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val mainActivityUseCase: MainActivityUseCase,
    private val localDbUseCase: LocalDbUseCase,
) : ViewModel(), DefaultLifecycleObserver {

    //  var setBannerList = MutableLiveData<ArrayList<BannerDataModel>>(arrayListOf())
    //  val setBannerListLiveData: LiveData<ArrayList<BannerDataModel>> get() = setBannerList

    val searchList: MutableLiveData<MutableList<SearchDataModel>> = MutableLiveData(mutableListOf())
    // val searchList: MutableStateFlow<MutableList<SearchDataModel>> = MutableStateFlow(mutableListOf())
    //  val _searchList:StateFlow<MutableList<SearchDataModel>>  = searchList


    var searchListOriginal: List<SearchDataModel> = listOf()

    val _myUiState = MutableStateFlow<ArrayList<BannerDataModel>>(arrayListOf())
    val myUiState: StateFlow<ArrayList<BannerDataModel>> = _myUiState


    var filteredlist: MutableList<SearchDataModel> = mutableListOf()
    var bottomSheetTitlelist: ArrayList<String> = arrayListOf()
    var countData = ""
    var showBottomSheetDialog = MutableSharedFlow<MESSAGES>()
    val showBottomSheetDialogLiveData: SharedFlow<MESSAGES> get() = showBottomSheetDialog


    init {
        getBannerListing()
        getSearchListing(0)
        getAllPatientsFromApi()
    }

    fun getBannerListing() {
        viewModelScope.launch(IO) {
            val response = mainActivityUseCase.getAllBannerListing()
            //  setBannerList.postValue(response)
            _myUiState.update { response }


        }
    }

    fun flowUsage() {
        viewModelScope.launch(Dispatchers.Main) //by this line we are specifying consumer will run on main thread
        {
            mainActivityUseCase.flowUsage()
                .flowOn(Dispatchers.IO)//by this line we are specifying producer will run on bg thread-
                // it means flowOn k upar wala sara code IO thread p run hoga

                .collect { data ->
                    Log.e("data_flowss_received::", ":::->" + data)
                }
        }
    }

    fun hotFlowUsage() {
        viewModelScope.launch(Dispatchers.Main) //by this line we are specifying consumer will run on main thread
        {
            val result1 = mainActivityUseCase.sharedfloworHotFlowUsage()
            result1.collect { data ->
                Log.e("hot_flowss_received1::", ":::->" + data)
            }


        }

       /* viewModelScope.launch(Dispatchers.Main) //by this line we are specifying consumer will run on main thread
        {
            val result2 = mainActivityUseCase.sharedfloworHotFlowUsage()
            delay(2500)
            result2.collect { data ->
                Log.e("hot_flowss_received2::", ":::->" + data)
            }
        }*/
    }

    fun hotFlowUsageStateFlow() {
        viewModelScope.launch(Dispatchers.Main) //by this line we are specifying consumer will run on main thread
        {
            val result1 = mainActivityUseCase.statefloworHotFlowUsage()
            delay(6000)
            result1.collect { data ->
                Log.e("hot_flowss_received1::", ":::->" + data)
            }


        }

        viewModelScope.launch(Dispatchers.Main) //by this line we are specifying consumer will run on main thread
        {
            //    val result2=mainActivityUseCase.statefloworHotFlowUsage()
            /*     delay(6000)
                 result2.collect { data ->
                     Log.e("hot_flowss_received2::", ":::->" + data)
                 }*/
        }
    }


    fun floatingActionButtonClick() {
        viewModelScope.launch(Dispatchers.IO) {
            if (!filteredlist.isNullOrEmpty() && filteredlist.size > 0) {
                bottomSheetTitlelist.clear()
                for (item in filteredlist) {
                    bottomSheetTitlelist.add(item.title.trim())
                }
                countData = mainActivityUseCase.provideBottomSheetCountData(bottomSheetTitlelist)
                showBottomSheetDialog.emit(MESSAGES.BOTTOM_SHEET_DATA_FOUND)
            } else {
                showBottomSheetDialog.emit(MESSAGES.BOTTOM_SHEET_NO_DATA_FOUND)
            }
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


            // val tempList=filteredlist
            // searchList.update {tempList }
            //    searchList.value=tempList
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
        // val tempList=filteredlist
        //  searchList.value=tempList

    }


    fun getAllPatientsFromApi() {
        viewModelScope.launch(IO) {
            val response = mainActivityUseCase.getAllPatientsListFromApi(
                true, 787
            )

            localDbUseCase.addToCart(CartMedicine(1, "Title 1", "Sub title 1"))
            val medData = localDbUseCase.getAddedMedicines()
            medData.map {
                Log.e("responsee::::${it.id}::::", it.medicineId + ":::" + it.medicineName)
            }

        }
    }
}