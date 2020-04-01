package edu.daec.mcu

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject

class MUCMarvelVolley(val url:String,val contexto:Context,val mucAdapter: MUCDudeRecyclerAdapter){
    val queue = Volley.newRequestQueue(contexto)

    fun callMarvelAPI(range: Int){
        val dataHeores = ArrayList<MUCDude>()
        println(url)
        val requestMarvel = JsonObjectRequest(Request.Method.GET, url, null,
            Response.Listener<JSONObject>{
                response ->
                Log.d("Finderman", "in MUC")
                val heroes = response.getJSONObject("data").getJSONArray("results")

                for(i in range..range+10){
                    val personaje = heroes.getJSONObject(i)
                    val image = personaje.getJSONObject("thumbnail")
                    val thumbnail = image.getString("path")+"."+image.getString("extension")
                    val mcuDude = MUCDude(personaje.getString("name"), personaje.getString("description"),
                        thumbnail)
                    dataHeores.add(mcuDude)
                }
                println(dataHeores.size)
                mucAdapter.setData(dataHeores)
            }, Response.ErrorListener {
                Toast.makeText(contexto, "Error garrafal compa", Toast.LENGTH_LONG).show()
                Log.i("MUC error", "RESPnone")
            })



        queue.add(requestMarvel)

    }
}