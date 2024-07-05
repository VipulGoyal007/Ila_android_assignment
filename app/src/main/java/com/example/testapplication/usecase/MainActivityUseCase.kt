package com.example.testapplication.usecase

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.core.content.ContextCompat
import com.example.testapplication.R
import com.example.testapplication.datamodel.BannerDataModel
import com.example.testapplication.datamodel.SearchDataModel
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL
import java.util.Collections
import javax.inject.Inject

class MainActivityUseCase @Inject constructor(
    @ApplicationContext private val context: Context
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

}