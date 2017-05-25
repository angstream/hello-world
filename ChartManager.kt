package com.erikk.divtracker.charts

import android.content.Context
import android.util.Log
import com.erikk.divtracker.global.CHART
import com.erikk.divtracker.history.ChartCacheManager
import com.erikk.divtracker.history.JSONUtil
import com.erikk.divtracker.history.JsoupChartAsyncTask
import com.erikk.divtracker.history.URLStrings
import com.erikk.divtracker.model.History
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineDataSet
import java.util.*

/**
 * Created by Erik on 2017-05-22.
 */
class ChartManager(val listener: ChartManagerListener, val ctx: Context, symbol:String){
    var mCtx:Context = ctx
    var mSymbol :String  = symbol
    var cmListener: ChartManagerListener = listener
    val TAG = "ChartManager"
    interface ChartManagerListener{
        fun onResponse(dataSet:LineDataSet, list:List<History>)
    }

    fun initRequest(chartType: CHART) {
        val url_log = URLStrings().getPriceHistory5years(mSymbol)


        Log.d(TAG, url_log)

        val cachedJsonString = ChartCacheManager().checkCachedPriceHistory(ctx, mSymbol, chartType)
        Log.d(TAG, mSymbol + " : cachedJSONString:" + cachedJsonString?.length)
        if (cachedJsonString != null) {
            val list = JSONUtil().createListOfHistory(cachedJsonString)
            if (list != null) {
                processList(list.reversed(), mSymbol)
                return
            }
        }

        //Did not find cached price history; doing a network call
        val url = URLStrings().getPriceHistory5years(mSymbol)
        val task = JsoupChartAsyncTask(this, ctx, mSymbol, chartType)
        task.execute(url)
    }


    fun processList(list:List<History>, symbol: String){
        val dataSet = createDataPoints(list)
        cmListener.onResponse(dataSet, list)
    }

    fun onResponse(list: List<History>, symbol:String){
        processList(list, symbol)
    }

    fun createDataPoints(list: List<History>):LineDataSet{
        val entries = ArrayList<Entry>()
        var indx = 0
        for (md in list) {
//            Log.d(TAG, "date:" + md.date)
            //            entries.add(new Entry(md.getQuoteDate().getTime(), md.getAmount()));
//            Log.d(TAG, "value:" + md.dividendValue.toFloat())
            entries.add(Entry(indx++.toFloat(), md.dividendValue.toFloat()))
        }
        Log.d(TAG, "added entries size :" + entries.size)
        val dataSet = LineDataSet(entries, mSymbol) // add entries to dataset

        return dataSet
    }

}