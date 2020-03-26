package com.test.pokedex.Activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.google.gson.JsonArray
import com.google.gson.JsonElement
import com.koushikdutta.ion.Ion
import com.test.pokedex.Adapters.AdapterMovs
import com.test.pokedex.R
import kotlinx.android.synthetic.main.activity_details.*

class ActivityDetails : AppCompatActivity() {

    private lateinit var linearLayoutManager:LinearLayoutManager
    private lateinit var adapter:AdapterMovs
    private lateinit var moves: JsonArray

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)



            Ion.with(this)
                .load(intent.getStringExtra("url"))
                .asJsonObject()
                .done { e, result ->
                    if(e == null){
                        moves = result.getAsJsonArray("moves")

                        supportActionBar?.title = "#"+result.get("id").asString +"\t"+ result.get("name").asString.capitalize()
                        pokemon_name.text = result.get("name").asString.toUpperCase()
                        var types:String = ""
                        var barra = false
                        for(type:JsonElement in result.getAsJsonArray("types")){
                            if(barra) types+="/"
                            types+=type.asJsonObject.get("type").asJsonObject.get("name").asString.replace("\"", "")
                            barra=true
                        }
                        pokemon_types.setText(types)
                        SP_holder.setText(result.getAsJsonArray("stats")[0].asJsonObject.get("base_stat").asString)
                        SPDEF_holder.setText(result.getAsJsonArray("stats")[1].asJsonObject.get("base_stat").asString)
                        SPATK_holder.setText(result.getAsJsonArray("stats")[2].asJsonObject.get("base_stat").asString)
                        DEF_holder.setText(result.getAsJsonArray("stats")[3].asJsonObject.get("base_stat").asString)
                        ATK_holder.setText(result.getAsJsonArray("stats")[4].asJsonObject.get("base_stat").asString)
                        HP_holder.setText(result.getAsJsonArray("stats")[5].asJsonObject.get("base_stat").asString)

                        Glide
                            .with(this)
                            .load(result.get("sprites").asJsonObject.get("front_default").asString)
                            .placeholder(R.drawable.pokemon_logo_min)
                            .error(R.drawable.pokemon_logo_min)
                            .into(pokemon_image_top);

                        initializeData(moves)

                    }else{
                        supportActionBar?.title = "#N POKEMON NAME"
                    }
                }





    }

    fun initializeData(moves:JsonArray){
        linearLayoutManager = LinearLayoutManager(this)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        linearLayoutManager.scrollToPosition(0)

        adapter = AdapterMovs()
        adapter.AdapterMovs(this, moves)

        recycler_view_movs.layoutManager = linearLayoutManager
        recycler_view_movs.adapter = adapter
        recycler_view_movs.itemAnimator = DefaultItemAnimator()

    }



}

