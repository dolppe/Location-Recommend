package com.example.ktttt

import android.content.ContentValues.TAG
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


class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding
    private lateinit var temp_place: Place
    private lateinit var add_btn: Button
    private lateinit var end_btn: Button
    private lateinit var list_btn: Button
    private lateinit var select_listview: ListView
    private lateinit var result_listview: ListView
    private lateinit var array_adapter: ArrayAdapter<String>
    private lateinit var array_adapter_result: MainListAdapter

    var latList = ArrayList<Double>()
    var longList = ArrayList<Double>()
    var nameList = ArrayList<String>()
    var typeList = ArrayList<ArrayList<Int>>()
    var recommendIdxList = ArrayList<Int>()
    var jsonSize = 0

    private var select_list: MutableList<Place> = mutableListOf()
    private var result_list: ArrayList<ResultData> = ArrayList()
    private var string_list_adap: ArrayList<String> = ArrayList()
    private var string_list_adap_result: ArrayList<String> = ArrayList()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        var apiKey = BuildConfig.API_KEY
        // Initialize the SDK
        Places.initialize(applicationContext, apiKey)


        add_btn = findViewById(R.id.add_btn)

        add_btn.setOnClickListener {
            select_list.add(temp_place)
            add_btn.isVisible = false
            string_list_adap.add(temp_place.name.toString())

            array_adapter.notifyDataSetChanged()
            Log.d("test", select_list.toString())
        }
        end_btn = findViewById(R.id.end_btn)
        // 모든 리스트의 place => 중간 장소 출력
        end_btn.setOnClickListener {



            var point_list = mutableListOf<Point>()
            for (temp in select_list) {
                var x = temp.latLng.latitude
                var y = temp.latLng.longitude
                var p = Point(x, y)
                point_list.add(p)

            }
            Log.d("test", point_list.toString())
            var c = SmallestEnclosingCircle.makeCircle(point_list)
            Log.d("test", c.toString())
            mMap?.moveCamera(
                CameraUpdateFactory.newLatLngZoom(
                    LatLng(c.x, c.y), 15f
                )
            )
            recommend(c.x, c.y, 10)

            result_listview.isVisible = true
        }
        list_btn = findViewById(R.id.list_btn)
        list_btn.setOnClickListener{
            select_listview.isVisible = !select_listview.isVisible

        }

        // Create a new PlacesClient instance
        val placesClient = Places.createClient(this)
        val autocompleteFragment =
            supportFragmentManager.findFragmentById(R.id.autocomplete_fragment)
                    as AutocompleteSupportFragment

        autocompleteFragment.setPlaceFields(
            listOf(
                Place.Field.ID,
                Place.Field.NAME,
                Place.Field.LAT_LNG
            )
        )
        autocompleteFragment.setOnPlaceSelectedListener(object : PlaceSelectionListener {
            override fun onPlaceSelected(place: Place) {
                Log.i(TAG, "Place: ${place.name}, ${place.id}, ${place.latLng}")
                temp_place = place
                add_btn.isVisible = true
                end_btn.isVisible = true
                mMap.clear()
                mMap?.moveCamera(
                    CameraUpdateFactory
                        .newLatLngZoom(place.latLng, 15f)
                )

                

            }

            override fun onError(status: Status) {
                // TODO: Handle the error.
                Log.i(TAG, "An error occurred: $status")
            }
        })

        result_listview = findViewById(R.id.resultView)
        array_adapter_result = MainListAdapter(this, result_list)
        result_listview.adapter = array_adapter_result

        result_listview.onItemClickListener = object : OnItemClickListener {

            override fun onItemClick(adapterView: AdapterView<*>?, view: View?, i: Int, l: Long) {

                mMap?.moveCamera(
                    CameraUpdateFactory.newLatLngZoom(
                        LatLng(result_list[i].lat, result_list[i].long), 15f
                    )
                )

                result_listview.isVisible = false




            }


        }


        array_adapter =
            ArrayAdapter(applicationContext, android.R.layout.simple_list_item_1, string_list_adap)
        select_listview = findViewById(R.id.listView)
        select_listview.adapter = array_adapter


        select_listview.onItemClickListener = object : OnItemClickListener {

            override fun onItemClick(adapterView: AdapterView<*>?, view: View?, i: Int, l: Long) {

                string_list_adap.removeAt(i)
                select_list.removeAt(i)
                array_adapter.notifyDataSetChanged()
                Log.d("test", select_list.toString())
            }

        }

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        // json 가져오기
        val jsonString = assets.open("final_result.json").reader().readText();
        val jsonObj = JSONObject(jsonString)
        //val jsonArray = JSONArray(jsonString)

        //Log.d("test",jsonObj.toString())
        val codeJsonObj = jsonObj.getJSONObject("code")
        val nameJsonObj = jsonObj.getJSONObject("name")
        val dayJsonObj = jsonObj.getJSONObject("day_cluster")
        val timeJsonObj = jsonObj.getJSONObject("time_cluster")
        val ageJsonObj = jsonObj.getJSONObject("age_cluster")
        val genderJsonObj = jsonObj.getJSONObject("gender_cluster")
        val latJsonObj = jsonObj.getJSONObject("latitude")
        val longJsonObj = jsonObj.getJSONObject("longitude")
        val temp = codeJsonObj.getString("1")
        Log.d("test", codeJsonObj.length().toString())
        jsonSize = codeJsonObj.length()




        for (idx: Int in 0 until (jsonSize - 1)) {
            val stridx = idx.toString()
            val latTemp = latJsonObj.getString(stridx)
            val longTemp = longJsonObj.getString(stridx)
            val nameTemp = nameJsonObj.getString(stridx)
            val dayTemp = dayJsonObj.getString(stridx)
            val timeTemp = timeJsonObj.getString(stridx)
            val ageTemp = ageJsonObj.getString(stridx)
            val genderTemp = genderJsonObj.getString(stridx)
            val arrayTemp = ArrayList<Int>()
            arrayTemp.add(dayTemp.toInt())
            arrayTemp.add(timeTemp.toInt())
            arrayTemp.add(ageTemp.toInt())
            arrayTemp.add(genderTemp.toInt())
            latList.add(latTemp.toDouble())
            longList.add(longTemp.toDouble())
            nameList.add(nameTemp)
            typeList.add(arrayTemp)

        }


    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Add a marker in Sydney and move the camera
        val sydney = LatLng(-34.0, 151.0)
        mMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))

    }


    fun recommend(inputLat: Double, inputLong: Double, num: Int) {
        val calcList = ArrayList<Pair<Double, Int>>()
        Log.d("test", inputLat.toString())
        Log.d("test", inputLong.toString())
        Log.d("test", result_list.toString())
        result_list.clear()
        recommendIdxList.clear()
        Log.d("test", result_list.toString())
        array_adapter_result.notifyDataSetChanged()

        for (idx in 0 until (jsonSize - 1)) {
            val tempLat = inputLat - latList[idx]
            val tempLong = inputLong - longList[idx]
            val tempCalc = tempLat.pow(2) + tempLong.pow(2)
            val tempPair = Pair<Double, Int>(tempCalc, idx)
            calcList.add(tempPair)


        }
        for (idx in 0 until (num - 1)) {
            val min = calcList.minBy { it.first }
            val tempMin = min.second
            recommendIdxList.add(tempMin)
            calcList.remove(min)
        }
        Log.d("test", recommendIdxList.toString())

        for (idx in 0 until (recommendIdxList.size - 1)) {
            val realIdx = recommendIdxList[idx]
            val tempResult = ResultData(
                nameList[realIdx],
                latList[realIdx],
                longList[realIdx],
                typeList[realIdx]
            )
            result_list.add(tempResult)

        }
        Log.d("test", result_list.toString())
        array_adapter_result.notifyDataSetChanged()


    }



}