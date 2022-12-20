package com.example.ktttt

import android.content.Context
import android.media.Image
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView

class MainListAdapter(val context:Context,val ResultData_list:ArrayList<ResultData>) :
    BaseAdapter() {
    override fun getCount(): Int {

        return ResultData_list.size
    }

    override fun getItem(p0: Int): Any {

        return ResultData_list[p0]
    }

    override fun getItemId(p0: Int): Long {

        return 0
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

        Log.d("test","리스트실행")
        /* LayoutInflater는 item을 Adapter에서 사용할 View로 부풀려주는(inflate) 역할을 한다. */
        val view: View = LayoutInflater.from(context).inflate(R.layout.main_item, null)

        /* 위에서 생성된 view를 res-layout-main_lv_item.xml 파일의 각 View와 연결하는 과정이다. */

        val title = view.findViewById<TextView>(R.id.titleString)
        val main = view.findViewById<TextView>(R.id.mainString)
        val main2 = view.findViewById<TextView>(R.id.mainString2)
        val ImageString = view.findViewById<ImageView>(R.id.listImage)

        /* ArrayList<Dog>의 변수 dog의 이미지와 데이터를 ImageView와 TextView에 담는다. */

        title.text = ResultData_list[position].name
        val temp = ResultData_list[position].calcType(context)
        main.text = temp[0]
        main2.text = temp[1]


        ImageString.setImageResource(R.drawable.ic_launcher_foreground)



        return view



    }


}