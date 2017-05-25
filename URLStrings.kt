package com.erikk.divtracker.history

import com.erikk.divtracker.global.CHART
import java.util.*

/**
 * Created by Erik on 2017-05-18.
 */
class URLStrings {
    //1136091600 = Jan 1, 2006

    val urlDividendHistory = "https://finance.yahoo.com/quote/%s/history?period1=1136091600&period2=%d&interval=div|split&filter=div&frequency=1mo"

    val monthlyInterval = "1mo"
    val weeklyInterval = "1wk"
    val dailyInterval = "1d"

    val urlHistoricalPrices5Year ="https://finance.yahoo.com/quote/%s/history?period1=1337659200&period2=%d&interval=%s&filter=history&frequency=%s"

    val urlHistoricalPrices ="https://finance.yahoo.com/quote/%s/history?period1=%d&period2=%d&interval=%s&filter=history&frequency=%s"


    fun getYahooHistoryUrl (symbol:String):String{
        val number:Long   = Date().time
//        println("current time:" + number)
        val url = String.format(urlDividendHistory, symbol, number)
//        println("yahoo url:" + url)
        return url
    }

    fun getPriceHistory5years(symbol:String) :String{
        val number:Long   = Date().time //now

        val url = String.format(urlHistoricalPrices5Year, symbol, number, monthlyInterval, monthlyInterval)
        return url
    }


    fun getPriceHistory(symbol:String, chartType: CHART):String{
        val numberNow:Long   = Date().time //now
        val cal = Calendar.getInstance()
        cal.setTime(Date())
//        if (chart)

        return ""
    }

    fun getPriceHistory3M(symbol:String) :String{
        val numberNow:Long   = Date().time //now
        println(numberNow)
        val cal = Calendar.getInstance()
        cal.setTime(Date())
        cal.add(Calendar.MONTH, -3)

        val date3M  = cal.time
        println(date3M.toString())

        val url = String.format(urlHistoricalPrices, symbol, date3M, numberNow, monthlyInterval, monthlyInterval)
        return url
    }

}