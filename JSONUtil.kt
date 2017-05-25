package com.erikk.divtracker.history

import android.util.Log
import com.erikk.divtracker.model.History
import org.json.JSONArray
import org.json.JSONObject
import java.util.*

/**
 * Created by Erik on 2017-05-18.
 */
class JSONUtil {

    val TAG = "JSONUtil"

    fun createDividendJson(list:List<Dividend?>, symbol:String): JSONObject {

        var arr:JSONArray = JSONArray()

        list.forEach {
            arr.put(createJsonItem(it))
        }

        val jso = JSONObject()

        jso.put("dividends", arr)
        jso.put("name", symbol)

        return jso
    }

    fun createJsonItem(item:Dividend?): JSONObject {
        val jsonObj =  JSONObject()
        jsonObj.put("date", item?.DivDate?.time)
        jsonObj.put("amount", item?.Amount)
        return jsonObj
    }


    fun createListOfHistory(jsonString:String):List<History>?  {
        var listOfHistory :List<History>? = null
        try {
            //Log.d(TAG, "trying to create History List from json")
            val jso = JSONObject(jsonString)
            //Log.d(TAG, "trying to getJSONArray from jso:" + jso)
            val arr = jso.getJSONArray("dividends")
            //Log.d(TAG, "got JSONArray from jso" )
            listOfHistory = jsonArrayToListofHistory(arr)
            //Log.d(TAG, "created listofhistory size:" + listOfHistory.size )
        }
        catch (e:Exception){
            Log.d(TAG, "create ListofHistory error:"+ e.message)
        }
        finally
        {
            return listOfHistory
        }
    }

    fun createListOfQuotes(jsonString:String):List<History>?  {
        var listOfHistory :List<History>? = null
        try {
            //Log.d(TAG, "trying to create History List from json")
            val jso = JSONObject(jsonString)
            //Log.d(TAG, "trying to getJSONArray from jso:" + jso)
            val arr = jso.getJSONArray("quotes")
            //Log.d(TAG, "got JSONArray from jso" )
            listOfHistory = jsonArrayToListofHistory(arr)
            //Log.d(TAG, "created listofhistory size:" + listOfHistory.size )
        }
        catch (e:Exception){
            Log.d(TAG, "create createListOfQuotes error:"+ e.message)
        }
        finally
        {
            return listOfHistory
        }
    }

    fun jsonArrayToListofHistory(arr: JSONArray):List<History> {
        var listOfHistory: ArrayList<History> = ArrayList<History>()

        for (i in 0..(arr.length() - 1)) {
            val item = arr.getJSONObject(i)

            val div = Dividend(Date(item.getLong("date")), item.getDouble("amount").toFloat())
            val history = History(div)

            listOfHistory.add(history)
        }
        return listOfHistory
    }
}