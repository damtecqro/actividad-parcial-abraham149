package com.test.pokedex.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.koushikdutta.ion.Ion
import com.test.pokedex.R

class AdapterMovs :RecyclerView.Adapter<AdapterMovs.ViewHolder>() {

    private lateinit var context: Context
    private lateinit var data:JsonArray

    fun AdapterMovs(context:Context,data:JsonArray){
        this.context = context
        this.data = data
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterMovs.ViewHolder {
        var layoutInflater = LayoutInflater.from(parent.context)
        return ViewHolder(layoutInflater.inflate(R.layout.activity_movs,parent,false))
    }

    override fun getItemCount(): Int {
        return data.size()
    }

    override fun onBindViewHolder(holder: AdapterMovs.ViewHolder, position: Int) {
        var item:JsonObject = data.get(position).asJsonObject
        holder.bind(item,context)


    }

    class ViewHolder(view:View):RecyclerView.ViewHolder(view){
        private var Name: TextView= view.findViewById(R.id.Name)
        private var Power: TextView= view.findViewById(R.id.Power)
        private var Accuracy: TextView= view.findViewById(R.id.Accuracy)


        fun bind(item:JsonObject,context: Context){

            Name.setText(item.get("move").asJsonObject.get("name").asString)

            Ion.with(context)
                .load(item.get("move").asJsonObject.get("url").asString)
                .asJsonObject()
                .done { e, result ->
                    if(e == null){
                        Power.setText(result.get("power").asString)
                        Accuracy.setText(result.get("accuracy").asString)
                    }
                }
        }


    }


    interface OnPokemonItemClickListener{
        fun OnItemClick(item: JsonObject, position:Int)
    }



}