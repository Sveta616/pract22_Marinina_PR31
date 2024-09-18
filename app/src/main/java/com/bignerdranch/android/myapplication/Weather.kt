package com.bignerdranch.android.myapplication

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.TextView
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.android.material.snackbar.Snackbar
import org.json.JSONObject


class Weather : AppCompatActivity() {
    private lateinit var textView1: TextView
    private lateinit var textView2: TextView
    private lateinit var textView3: TextView
    private lateinit var city1:EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weather)

    }

    fun getResult(view:View) {
        city1=findViewById(R.id.ed)
        if(city1.text.toString().isNotEmpty()&&city1.text.toString()!=null)
        {
            var key="a9db202538bf4905ee946659b68b3ca3"
            var url="https://api.openweathermap.org/data/2.5/weather?q="+city1.text.toString()+"&appid="+key+"&units=metric&lang=ru"
            val queue= Volley.newRequestQueue(this)
            val stringRequest=StringRequest(
                Request.Method.GET,
                url,
                {
                        response->
                    val obj = JSONObject(response)
                    val temp=obj.getJSONObject("main")
                    Log.d("MyLog", "Responce:${temp.getString("temp")}")
                    textView1=findViewById(R.id.tempr)
                    textView1.setText("Temperature: ${temp.getString("temp")}")

                    val pressure = obj.getJSONObject("main")
                    textView2=findViewById(R.id.pres)
                    textView2.setText("Air pressure: ${pressure.getString("pressure")}")

                    val speed = obj.getJSONObject("wind")
                    textView3=findViewById(R.id.wind)
                    textView3.setText("Wind speed: ${speed.getString("speed")}")

                },
                {
                    Log.d("MyLog","Volley error:$it")
                }
            )
            queue.add(stringRequest)
        }
        else
        {
            var sn= Snackbar.make(findViewById(android.R.id.content),"There is no city",Snackbar.LENGTH_LONG)
            sn.setActionTextColor(Color.RED)
            sn.show()
        }

    }
}