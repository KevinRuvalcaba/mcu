package edu.daec.mcu

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.mcu_dude_layout.view.*

class MUCDudeRecyclerAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    private  var dudes: List<MUCDude> = ArrayList()

    /*
    Crea el layout
    * */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            return MUCDudeViewHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.mcu_dude_layout, parent, false)
            )
    }

    override fun getItemCount(): Int {
        return dudes.size
    }



    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder){
            is MUCDudeViewHolder -> {
                holder.bind(dudes.get(position))
                holder.itemView.setOnClickListener{
                    val contexto = it.context
                    val intent = Intent(contexto, MarvelDudeActivity::class.java)
                    intent.putExtra("dude", dudes.get(position))
                    contexto.startActivity(intent)
                    Toast.makeText(contexto, "insede dude!!!", Toast.LENGTH_LONG).show()
                }
            }
        }

    }

    fun setData(listDudes: List<MUCDude>){
        dudes = listDudes
        notifyDataSetChanged()
    }

    class MUCDudeViewHolder constructor(itemView: View) : RecyclerView.ViewHolder(itemView){
        val name = itemView.name_alias
        val notes = itemView.notes
        val imageDude = itemView.imageView
        fun bind(mucDude: MUCDude){
            name.text = mucDude.alias
            notes.text = mucDude.notes
            imageDude.setPicDude(mucDude.imageDude)
        }

        fun ImageView.setPicDude(url:String){
            val options = RequestOptions()
                .error(R.mipmap.ic_launcher_round)
            Glide.with(this)
                .setDefaultRequestOptions(options)
                .load(url)
                .into(this)
        }


    }








}