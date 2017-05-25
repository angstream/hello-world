package com.erikk.divtracker.history

import android.content.Context
import android.util.Log
import com.erikk.divtracker.global.CHART
import com.erikk.divtracker.util.DateUtil
import com.erikk.divtracker.util.DateUtil2
import com.erikk.divtracker.util.FileUtil
import java.util.*

/**
 * Created by Erik on 2017-05-23.
 */
class ChartCacheManager {
    val TAG = "ChartCacheManager"


    fun getChartFileName(symbol: String, chartType: CHART):String{
        return symbol+ "_" + chartType + "_prices.json"
    }


    fun isCacheFileStale(context: Context, symbol: String, chartType:CHART): Boolean {
        val filename = getChartFileName(symbol, chartType)
        val tempFileHandle = FileUtil.getTempFile(context, filename)
        if (!tempFileHandle.exists()) {
            return true
        }
        val dateModified = Date(tempFileHandle.lastModified())

        Log.d(TAG, symbol + ", dateModified: " + dateModified.toString())

        val isWeekend =(DateUtil.isWeekend())

//        if(isWeekend)
//            return false

        return (!DateUtil2.isSameDay(dateModified, Date()))

    }

    fun saveToTempFile(context: Context, symbol: String, jsonString: String, chartType: CHART ) {
        //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        try {
            val filename = getChartFileName(symbol, chartType)
            val file = FileUtil.getTempFile(context, filename)
            if (file != null) {
                FileUtil.writeToTempFile(file, jsonString)
                Log.d(TAG, "saved to temp file:" + filename + "; length:" + jsonString.length);
            }
        } catch (ex: Exception) {
            Log.d(TAG, ex.message)
        }

    }

    fun checkCachedPriceHistory(context: Context, symbol: String, chartType: CHART): String? {
        //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        val filename = getChartFileName(symbol, chartType)
        val tempFileHandle = FileUtil.getTempFile(context, filename)

        Log.d(TAG, symbol + ", path:" + tempFileHandle.absolutePath)
        //            FileUtil.listCacheFiles(mCtx);

        if (!tempFileHandle.exists()) {
            return null
        }
        val dateModified = Date(tempFileHandle.lastModified())
        val isWeekend =(DateUtil.isWeekend())
        val isCurrentCache =DateUtil2.isSameDay(dateModified, Date())

        if(!isCurrentCache) {
            Log.d(TAG, "the cache is not current")
            return null
        }

        val cachedResponse = FileUtil.readTempFile(context, filename)

        if (cachedResponse != null && cachedResponse.length > 10) {
            Log.d(TAG, "returning cachedResponse for :" + symbol)
            return cachedResponse
        }

        return null
    }

}