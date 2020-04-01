package edu.daec.mcu

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Message
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONArray
import java.io.InputStream
import java.lang.Exception
import java.math.BigInteger
import java.security.MessageDigest
import java.sql.Timestamp
import kotlin.math.max
import kotlin.math.min

class MainActivity : AppCompatActivity() {


    private lateinit var mucDudeAdapter : MUCDudeRecyclerAdapter

    var jAvenger : String? = null
    var range = 0
    //val btn_fwd = findViewById(R.id.forward) as Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val btn_fwd = findViewById<Button>(R.id.forward)
        btn_fwd.setOnClickListener { fwd() }
        val btn_bwc = findViewById<Button>(R.id.backward)
        btn_bwc.setOnClickListener { bwd() }

        setRecyclerView()// Adapter instanciado
        Log.i("Maverl api", getMarvelAPIUrl())
        //mucDudeAdapter.setData(getDataSet()) // Datos
        executeMuc()

    }
    fun fwd(){
        if(range == 100){
            Toast.makeText(this, "No hay mas adelante amigo Dale al otro",Toast.LENGTH_LONG).show()
        }
        range = min(100, range+10)
        executeMuc()
    }
    fun bwd(){
        if(range == 0){
            Toast.makeText(this, "No hay mas atras amigo Dale al otro",Toast.LENGTH_LONG).show()
        }
        range = max(0, range-10)
        executeMuc()
    }

    fun executeMuc(){
        MUCMarvelVolley(getMarvelAPIUrl(), this, mucDudeAdapter).callMarvelAPI(range)
    }

    fun String.md5(): String{
        val md5Al = MessageDigest.getInstance("MD5")
        return BigInteger(1,md5Al.digest(toByteArray())).toString(16)
                                        .padStart(32, '0')
    }

    fun getMarvelAPIUrl(): String{
        val tString = Timestamp(System.currentTimeMillis()).toString()
        val hString = tString + "b9e2c5b1ca64adbfa1ca924346b1937726094f78" + "86af7ee6cbffa9478b01f3030ca9aa44"
        val hash = hString.md5()

        var marvelAPI : String = "https://gateway.marvel.com:443/v1/public/characters?ts=" +
                tString +
                "&limit=100&apikey=86af7ee6cbffa9478b01f3030ca9aa44&hash="+hash
        return marvelAPI

    }

    private fun setRecyclerView(){
        recycler_view_muc.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            mucDudeAdapter = MUCDudeRecyclerAdapter()
            adapter = mucDudeAdapter
        }
    }


    private fun getDataSet() : ArrayList<MUCDude> {
        val dudes = ArrayList<MUCDude>()
        jAvenger = leaJSON()
        Log.i("edu.daec.mcu", jAvenger)
        val jArray = JSONArray(jAvenger)
        for ( i in 0..jArray.length()-1){
            val jobject = jArray.getJSONObject(i)
            val name = jobject.getString("name/alias")
            val notes = jobject.getString("notes")
            if(name!= null)  dudes.add(  MUCDude( name, notes, "" ) )
        }
        return  dudes
    }


    fun leaJSON():String?{
        var jContenido:String? = null
        try {

            val inputS : InputStream = assets.open("avengers.json")
            jContenido = inputS.bufferedReader().use { it.readLine() }
        }catch ( ex: Exception){
            ex.printStackTrace()
            Toast.makeText(this, "POM! no Avenger",Toast.LENGTH_LONG).show()

        }

        return jContenido
    }
}
