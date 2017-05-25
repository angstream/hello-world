package com.erikk.divtracker.history

import android.content.Context
import android.util.Log
import com.erikk.divtracker.util.DateUtil
import com.erikk.divtracker.util.DateUtil2
import com.erikk.divtracker.util.FileUtil
import java.util.*

/**
 * Created by Erik on 2017-05-21.
 */
class CacheManager : ICacheManager {

    val TAG = "HistoryCacheManager"
    override fun getDividendFileName(symbol: String): String {
        return symbol + "_dividends.json"
    }


    override fun isCacheFileStale(context: Context, symbol: String): Boolean {
        val filename = getDividendFileName(symbol)
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

    override fun checkCachedDividendHistory(context: Context, symbol: String): String? {

        val filename = getDividendFileName(symbol)
        val tempFileHandle = FileUtil.getTempFile(context, filename)

        Log.d(TAG, symbol + ", path:" + tempFileHandle.absolutePath)
        //            FileUtil.listCacheFiles(mCtx);

        if (!tempFileHandle.exists()) {
            return null
        }

//        val dateModified = Date(tempFileHandle.lastModified())
//        Log.d(TAG, " dateModified: " + dateModified.toString())

        // Compare date modified and Today's date
        //THE FRESH DATA WILL BE PICKED UP BY A SERVICE
        //        if(!DateUtil2.isSameDay(dateModified, new Date())) {
        //            //refresh since the day is different
        //            return null  ;
        //        }

        //        Log.d(TAG, "same day");

        val cachedResponse = FileUtil.readTempFile(context, filename)

        if (cachedResponse != null && cachedResponse.length > 10) {
            Log.d(TAG, "returning cachedResponse for :" + symbol)
            return cachedResponse
        }

        return null
    }

    override fun saveToTempFile(ctx: Context, symbol: String, jsonString: String) {
        try {
            val filename = getDividendFileName(symbol)
            val file = FileUtil.getTempFile(ctx, filename)
            if (file != null) {
                FileUtil.writeToTempFile(file, jsonString)
                Log.d(TAG, "saved to temp file:" + filename + "; length:" + jsonString.length);
            }
        } catch (ex: Exception) {
            Log.d(TAG, ex.message)
        }
    }
}