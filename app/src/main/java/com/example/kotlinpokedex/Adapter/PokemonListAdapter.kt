package com.example.kotlinpokedex.Adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.kotlinpokedex.Common.Common
import com.example.kotlinpokedex.Model.Pokemon
import com.example.kotlinpokedex.R

class PokemonListAdapter(
    internal var context: Context,
    internal var pokemonList: List<Pokemon>
) : RecyclerView.Adapter<PokemonListAdapter.MyViewHolder>() {

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        internal var pokemonImage: ImageView
        internal var pokemonName: TextView

        init {
            pokemonImage = itemView.findViewById(R.id.pokemon_image) as ImageView
            pokemonName = itemView.findViewById(R.id.pokemon_name) as TextView
        }

        fun setItemClickListener(onClickListener: View.OnClickListener) {
            itemView.setOnClickListener { view ->
                onClickListener.onClick(view)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView =
            LayoutInflater.from(context).inflate(R.layout.pokemon_list_item, parent, false)
        return MyViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return pokemonList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        Glide.with(context).load(pokemonList[position].img).into(holder.pokemonImage)
        holder.pokemonName.text = pokemonList[position].name
        holder.setItemClickListener(View.OnClickListener {
            LocalBroadcastManager.getInstance(context)
                .sendBroadcast(Intent(Common.KEY_ENABLE_HOME).putExtra("position", position))
        })
    }

}