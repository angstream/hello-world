package com.erikk.divtracker.history

import android.content.Context
import android.os.AsyncTask
import android.util.Log
import com.erikk.divtracker.charts.ChartManager
import com.erikk.divtracker.charts.DividendToHistory
import com.erikk.divtracker.global.CHART
import com.erikk.divtracker.model.History

/**
 * Created by Erik on 2017-05-18.
 */
class JsoupChartAsyncTask  (callback: ChartManager, context: Context, tag:String, chartType: CHART) : AsyncTask<String, String, List<History>?>() {

    var mCallback : ChartManager = callback
    var mTag:String = tag
    var mContext =context
    var mChartType = chartType

    var mException: Exception? = null
    val TAG = "JsoupChartAsync"

    override fun doInBackground(vararg url: String?): List<History>? {
        if(url == null || url[0]== null) {
            mException = Exception("url is null")
            return null
        }
        var historyList:List<History>? = null
        try {
             Log.d(TAG, "executing JsoupAsyncTask url:" + url[0])
            if(url.size> 0 )
                historyList = parseHtml(url[0])
        }
        catch(ex: Exception){
            mException = ex
        }
//        Log.d(TAG, "history list ")
        return historyList
    }

    private fun parseHtml(url: String?): List<History>? {
        var historyList1 : List<History>? = null
        if(url!= null) {
            val list = JsoupHistory(mContext,mTag, 5).getPriceHistory(url, mChartType)
            historyList1 = DividendToHistory().reverseDividendList(list)
        }
        return historyList1
    }



    override fun onPostExecute(result: List<History>?) {
         if(mException != null || result == null){
             Log.d(TAG, "onPostExecute exception or result is null")
             //mCallback.onError("Could not get historical dividends")
             return
         }

        mCallback.onResponse(result, mTag)

//         mCallback.onResponse()
    }

}