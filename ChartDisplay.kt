package com.erikk.divtracker.charts

import android.graphics.Color
import android.os.Build
import android.util.Log
import com.erikk.divtracker.model.History
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.*
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IAxisValueFormatter
import com.github.mikephil.charting.utils.ColorTemplate
import java.text.SimpleDateFormat
import java.util.*


/**
 * Created by Erik on 2017-05-23.
 */
class ChartDisplay {

    val TAG = "ChartDisplay"

    fun showNaked(chart: LineChart?, dataSet: LineDataSet){
        if(chart == null)
            return

        dataSet.setDrawValues(false)
//        dataSet.setValueTextColors  (ColorTemplate.MATERIAL_COLORS.toMutableList())
//        dataSet.setColors (ColorTemplate.COLORFUL_COLORS.toMutableList())
        dataSet.setLineWidth(2f)

        dataSet.setDrawCircles(false)

        val data = LineData(dataSet)
        chart.setData(data);

        // style chart
        // chart.setBackgroundColor(getResources().getColor(...)); // use your bg color
//		 chart.setDescription(Description("Naked chart"))
        chart.setDrawGridBackground(false)
        chart.setDrawBorders(false)

        chart.setAutoScaleMinMaxEnabled(true);

        // remove axis
        val leftAxis: YAxis = chart.getAxisLeft();
        leftAxis.setEnabled(false);
//        leftAxis.setTextColor(Color.YELLOW)

        val rightAxis: YAxis = chart.getAxisRight();
        rightAxis.setEnabled(false);

        val  xAxis : XAxis = chart.getXAxis();
//        xAxis.setTextColor(Color.YELLOW)
        xAxis.setEnabled(false);

        // hide legend
        val legend: Legend = chart.getLegend();
        legend.setTextColor(Color.YELLOW)
        legend.setTextSize(14f)
//		 legend.setEnabled(false);

        chart.setPinchZoom(false)

        chart.invalidate()

    }


    fun showChart(mLineChart:LineChart?, dataSet: LineDataSet, list:List<History>) {

        if(mLineChart==null)
            return



        dataSet.setDrawCircles(false)
        dataSet.setDrawValues(false)

        dataSet.setDrawFilled(true)
        dataSet.fillAlpha = 255
        //		 dataSet.setValueTextColor(R.color.accent_material_light)

        //		 dataSet.fillColor = ctx?.resources.getColor(R.color.blue, The)  //:"#03A9F4"
        //				 this.activity.resources.getColor(R.color.blue)

        dataSet.setFillColor(Color.parseColor("#03A9F4"))

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            mLineChart.setHardwareAccelerationEnabled(false)
        }

        val lineData = LineData(dataSet)

//        mLineChart.description("")

        mLineChart.xAxis?.setTextColor(Color.YELLOW)
        mLineChart.axisLeft.setEnabled(true)
        mLineChart.axisLeft.setTextColor(Color.YELLOW)
//        mLineChart.axisRight.setTextColor(Color.YELLOW)
        mLineChart.axisRight.setEnabled(false)

//        mLineChart.xAxis?.setValueFormatter { value, axis -> MyXAxisValueFormatter()  }

//        val listOfValues:List<Float> = dataSet.values.map { it.x}
        val listOfDates :List<Date> = list.map { it.date }.sorted()

        Log.d(TAG, "listofdates size:" + listOfDates.size)



        val formatter = MyXAxis5YearValueFormatter(listOfDates)
        mLineChart.xAxis?.setGranularity(1f)

        mLineChart.xAxis?.setValueFormatter (formatter)

//        mLineChart.xAxis?.setValueFormatter { value, axis ->  formatter}

        mLineChart.setBackgroundColor(Color.TRANSPARENT) //set whatever color you prefer
        mLineChart.setDrawGridBackground(false)// this is a must

        val legend: Legend = mLineChart.getLegend()
        legend.setTextColor(Color.YELLOW)
        legend.setTextSize(14f)

        val description = Description()
        description.setTextColor( ColorTemplate.VORDIPLOM_COLORS[2])
        description.setText ("Chart Data")

        mLineChart.setDescription( description)



        // enable scaling and dragging
//        mChart.setDragEnabled(true);
//        mChart.setScaleEnabled(true);

        // if disabled, scaling can be done on x- and y-axis separately
//        mChart.setPinchZoom(true);
//        mLineChart..setDrawYLabels(true);

        mLineChart.setNoDataText("Getting data...")

        mLineChart.setDrawMarkers(true)
        mLineChart.setDrawBorders(true)

        mLineChart.setData(lineData)
        mLineChart.invalidate() // refresh
    }

    internal inner class MyXAxis5YearValueFormatter(val values:List<Date>) : IAxisValueFormatter {

        private val mValues: List<Date> = values
        private val mFormat: SimpleDateFormat = SimpleDateFormat("MMM yyyy")

        override fun getFormattedValue(value: Float, axis: AxisBase): String {
            val date = mValues [value.toInt()]
            return mFormat.format(date)
        }

        /** this is only needed if numbers are returned, else return 0  */
        val decimalDigits: Int
            get() = 0

    }

}