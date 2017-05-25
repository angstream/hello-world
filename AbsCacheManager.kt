package com.erikk.divtracker.history

import android.content.Context
import com.erikk.divtracker.util.DateUtil
import com.erikk.divtracker.util.DateUtil2
import com.erikk.divtracker.util.FileUtil
import java.util.*

/**
 * Created by Erik on 2017-05-23.
 */
abstract class AbsCacheManager : ICacheManager{

     fun isCacheFileStale2 (context: Context, filename: String): Boolean {
        val tempFileHandle = FileUtil.getTempFile(context, filename)
        if (!tempFileHandle.exists()) {
            return true
        }
        val dateModified = Date(tempFileHandle.lastModified())

//        Log.d(TAG, symbol + ", dateModified: " + dateModified.toString())

        val isWeekend =(DateUtil.isWeekend())

//        if(isWeekend)
//            return false

        return (!DateUtil2.isSameDay(dateModified, Date()))

    }


}