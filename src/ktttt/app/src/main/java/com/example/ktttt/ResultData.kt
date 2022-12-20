package com.example.ktttt

import android.content.ContentValues.TAG
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemClickListener
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.view.isVisible
import com.example.ktttt.databinding.ActivityMapsBinding
import com.google.android.gms.common.api.Status
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.AutocompleteSupportFragment
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener
import org.json.JSONObject
import kotlin.math.pow


class ResultData(val name:String,val lat:Double, val long:Double,val type:ArrayList<Int>) {



    fun calcType(context: Context): ArrayList<String> {
        var strarr = ArrayList<String>()
        var resultStr = ""
        var resultStr1 = ""
        var day = type[0]
        var time = type[1]
        var age = type[2]
        var gender = type[3]
        var count = 0

        Log.d("test",name+day.toString()+time.toString()+age.toString())
        var temp = ""
        if(day == 0)
            temp = context.getString(R.string.day0)
        else if(day == 1)
            temp = context.getString(R.string.day1)
        else if(day == 2)
            temp = context.getString(R.string.day2)
        else if(day == 3)
            temp = context.getString(R.string.day3)
        else if(day == 4)
            temp = context.getString(R.string.day4)

        resultStr += " $temp"


        temp = ""
        if(time == 0)
            temp = context.getString(R.string.time0)
        else if(time == 1)
            temp = context.getString(R.string.time1)
        else if(time == 2)
            temp = context.getString(R.string.time2)
        else if(time == 3)
            temp = context.getString(R.string.time3)
        else if(time == 4)
            temp = context.getString(R.string.time4)
        resultStr += " $temp"


        temp = ""
        if(age == 0)
            temp = context.getString(R.string.age0)
        else if(age == 1)
            temp = context.getString(R.string.age1)
        else if(age == 2)
            temp = context.getString(R.string.age2)
        else if(age == 3)
            temp = context.getString(R.string.age3)
        else if(age == 4)
            temp = context.getString(R.string.age4)
        resultStr1 += " $temp"



        temp = ""
        if(gender == 0)
            temp = context.getString(R.string.gender0)
        else if(gender == 1)
            temp = context.getString(R.string.gender1)
        else if(gender == 2)
            temp = context.getString(R.string.gender2)
        else if(gender == 3)
            temp = context.getString(R.string.gender3)
        else if(gender == 4)
            temp = context.getString(R.string.gender4)

        resultStr1 += " $temp"
        strarr.add(resultStr)
        strarr.add(resultStr1)
        return strarr

    }
    override fun toString(): String {
        return String.format("ResultData(name=%s, lat=%s, long=%s, type=%s)", this.name,this.lat.toString(),this.long.toString(),this.type.toString())
    }



}