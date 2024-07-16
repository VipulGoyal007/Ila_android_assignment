package com.example.testapplication.usecase

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import androidx.core.content.ContextCompat
import com.example.testapplication.R
import com.example.testapplication.datamodel.BannerDataModel
import com.example.testapplication.datamodel.GetAllPatientResponse
import com.example.testapplication.datamodel.SearchDataModel
import com.example.testapplication.repository.MainDataRepository
import com.example.testapplication.utils.Resource
import com.intellihealth.truemeds.data.model.mixpanel.MxInternalErrorOccurred
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL
import java.util.Collections
import javax.inject.Inject

class MainActivityUseCase @Inject constructor(
    private val userDataRepository: MainDataRepository,
    @ApplicationContext private val context: Context,
) {

    suspend fun getAllBannerListing(): ArrayList<BannerDataModel> {

        var bannerList= arrayListOf<BannerDataModel>()
        bannerList.add(BannerDataModel(1,"Fruit", arrayListOf("Apple","Banana","Orange","kiwi","Mangos","Graphes","Cherry","Guava","Pomegranate","Sweet Lime") ,ContextCompat.getDrawable(context, R.drawable.fruits)))
        bannerList.add(BannerDataModel(2,"Vegetable", arrayListOf("Tomato","Potato","Pea","Cabbage","Ginger") , ContextCompat.getDrawable(context, R.drawable.vegetables)))
        bannerList.add(BannerDataModel(3,"Bird", arrayListOf("Duck","Crow","Canary","Peacock") , ContextCompat.getDrawable(context, R.drawable.birds)))
        bannerList.add(BannerDataModel(4,"Gadget", arrayListOf("Mobile","Laptop","Charger") , ContextCompat.getDrawable(context, R.drawable.gadgets)))

        return bannerList
        }

    suspend fun getAllSearchListing(bannerPosition:Int=0): ArrayList<SearchDataModel> {

        var searchList= arrayListOf<SearchDataModel>()
        val bannerItem=getAllBannerListing().get(bannerPosition)
        for(i in 0..bannerItem.sublist.size-1){

            searchList.add(SearchDataModel(i,"${ bannerItem.sublist.get(i)}","${bannerItem.name} "+(i+1),bannerItem.imageUrl))
        }


        return searchList
    }

     fun provideBottomSheetCountData(bottomSheetTitlelist: ArrayList<String>): String {
         var bottomSheetCharCountlist: HashMap<String, Int> = hashMapOf()
         var bottomSheetFinalCharCountlistShown: HashMap<Int, ArrayList<String>> = hashMapOf()

         bottomSheetCharCountlist.clear()
         bottomSheetFinalCharCountlistShown.clear()

         for (item in bottomSheetTitlelist) {
             for (i in 0..item.count() - 1) {
                 if (bottomSheetCharCountlist.containsKey(item[i].toString().lowercase())) {
                     var currentCount = bottomSheetCharCountlist.getValue(item[i].toString().lowercase())
                     bottomSheetCharCountlist.set(item[i].toString().lowercase(), currentCount + 1)
                 } else {
                     bottomSheetCharCountlist.put(item[i].toString().lowercase(), 1)
                 }
             }
         }

         var tempArrayList: ArrayList<Int> = arrayListOf()

         bottomSheetCharCountlist.map {
         if(!tempArrayList.contains(it.value))
             tempArrayList.add(it.value)
         }

         Collections.sort(tempArrayList, Collections.reverseOrder())
         val tempArrayList2 = tempArrayList.filterIndexed { index, i -> index < 3 }

         bottomSheetCharCountlist.map {
             if (tempArrayList2.contains(it.value)) {
                 if (bottomSheetFinalCharCountlistShown.containsKey(it.value)) {
                     var tempArray = bottomSheetFinalCharCountlistShown.getValue(it.value)
                     tempArray.add(it.key)
                     bottomSheetFinalCharCountlistShown.set(it.value, tempArray)
                 } else {
                     bottomSheetFinalCharCountlistShown.put(it.value, arrayListOf(it.key))
                 }
             }
         }


         var countList= arrayListOf<Int>()
         bottomSheetFinalCharCountlistShown.map {
             countList.add(it.key)
         }

         var countData=""

         for (i in countList.size-1 downTo 0){
             countData=countData+"\n \n"+bottomSheetFinalCharCountlistShown.getValue(countList.get(i)).toString()+" = "+countList.get(i)
         }

   return countData
    }

//normal flows which are cold
     fun flowUsage(): Flow<Int> {

        var i =1
        val latestNews: Flow<Int> = flow<Int> {

            while(i<=10) {
              //  Log.e("data_flowss_send::", ":::->" + i)
                emit(i) // Emits the result of the request to the flow
                i = i + 1
                delay(1000) // Suspends the coroutine for some time
            }

        }.catch {
            Log.e("Exception_msg=>","::::"+it.message)
           // emit(-1)        //we can also emit some default value in case of exception
        }

        return latestNews
    }

    //shared flows which are hot- it emits repeated values also
    //Hot flows example with implementation
    fun sharedfloworHotFlowUsage(): Flow<Int> {
        val mutableSharedFlow= MutableSharedFlow<Int>(
            replay = 0 // it means in will store last 2 values and if someone collect the values late then it will get previous 2 values also
        )
      GlobalScope.launch {
          val list= listOf(1,2,3,4,5,5,5,6)
        list.forEach{
            mutableSharedFlow.emit(it)
           // Log.e("data_flowss_send::", ":::->" + it)
            delay(1000)
        }}
        return mutableSharedFlow
    }

    //state flows which are also hot - but they by default maintain(buffer of ) the last state value - means latest last value
    //main difference- if someone join the coroutines late they in case of sharedflow it will collect the new value emitter
    //in case of stateflow it will collect the new value emitter- it doesnot emits repeated values

    //Hot flows example with implementation
    fun statefloworHotFlowUsage(): Flow<Int> {
        val mutableStateFlow= MutableStateFlow<Int>(value = 0)
        GlobalScope.launch {
            val list= listOf(1,2,3,4,5,6)
            list.forEach{
                mutableStateFlow.emit(it)
               // Log.e("data_flowss_send::", ":::->" + it)
                delay(1000)
            }}
        return mutableStateFlow
    }

    suspend fun getAllPatientsListFromApi(
        showMyself: Boolean,
        customerId: Long
    ): GetAllPatientResponse? =
        when (val res = userDataRepository.getAllPatient(
            MxInternalErrorOccurred(), showMyself, customerId
        )) {
            is Resource.Success -> res.value?.body()
            is Resource.Failure -> null
            else -> null
        }
}