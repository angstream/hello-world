package com.erikk.divtracker.charts

import com.erikk.divtracker.history.Dividend
import com.erikk.divtracker.model.History

/**
 * Created by Erik on 2017-05-23.
 */
class DividendToHistory {

    fun reverseDividendList(list: List<Dividend?>?): List<History>? {
        return   list?.reversed()?.map { History(it) }
    }

}