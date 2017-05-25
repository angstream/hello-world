package com.erikk.divtracker.history

import android.content.Context
import android.util.Log
import com.erikk.divtracker.global.CHART
import org.jsoup.Jsoup

/**
 * Created by Erik on 2017-05-21.
 */
class JsoupHistory (context: Context, tag:String, val colIndex:Int = JsoupHistory.defaultColIndex){

//    constructor(tag:String, val colIndex){
//        this.mTag = tag
//
//    }

    companion object {
        const val defaultColIndex = 1
    }

    val mContext:Context = context
    val mTag:String = tag

     fun getDividendHistory(url: String): List<Dividend?>? {
        val list = getYahooListOfDividends(url)

        if (list != null && list.size >0) {
            val json = JSONUtil().createDividendJson(list, mTag)

            CacheManager().saveToTempFile(mContext, mTag, json.toString())
        }
        return list
    }


    private val  TAG: String = "JsoupHistory"

    fun getPriceHistory(url: String, chartType: CHART): List<Dividend?>? {
        val list = getYahooListOfDividends(url)

        if (list != null && list.size >0) {
            val json = JSONUtil().createDividendJson(list, mTag)
            Log.d(TAG, "saving price history for chart:" + chartType)
            ChartCacheManager().saveToTempFile(mContext, mTag, json.toString(), chartType)
        }
        return list
    }

    fun getYahooListOfDividends(url:String?):List<Dividend?>?{
        if(url == null)
            return null
        var divList :List<Dividend?>? = null
        println("jsoup url:" + url)
        try {
            val doc = Jsoup.connect(url)
                    //                    .data("query", "Java")
                    .userAgent("Mozilla")
                    //.cookie("auth", "token")
                    .timeout(5000).get()

            println("async doc length:" + doc.toString().length)

            //val selector="data-test=\"historical-prices\""
            val elements = doc.select("table")
            println("async elements size:" + elements.size)

            if (elements == null || elements.size == 0) {
                println("async tables not found")
                return null
            }
            println("tables length:"+ elements.size)
            divList = KHistoryUtil(colIndex).processTables(elements)
            //list?.forEach { println(it) }
        }
        catch(ex:Exception ){
//            println(ex.message)
            Log.d(TAG, ex.message)
            return null
        }

        return divList
    }
}