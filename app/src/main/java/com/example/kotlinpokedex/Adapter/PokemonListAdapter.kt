package com.example.kotlinpokedex.Adapter

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.opengl.Visibility
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.kotlinpokedex.Common.Common
import com.example.kotlinpokedex.Model.Pokemon
import com.example.kotlinpokedex.R
import com.robertlevonyan.views.chip.Chip

class PokemonListAdapter(
    internal var context: Context,
    internal var pokemonList: List<Pokemon>
) : RecyclerView.Adapter<PokemonListAdapter.MyViewHolder>() {

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        internal var pokemonImage: ImageView
        internal var pokemonName: TextView
        internal var pokemonId: TextView
        internal var linearLayout: LinearLayout
        internal var type_chip_one: Chip
        internal var type_chip_two: Chip

        init {
            pokemonImage = itemView.findViewById(R.id.pokemon_image) as ImageView
            pokemonName = itemView.findViewById(R.id.pokemon_name) as TextView
            pokemonId = itemView.findViewById(R.id.pokemon_id) as TextView
            linearLayout = itemView.findViewById(R.id.linear_layout_card_background) as LinearLayout
            type_chip_one = itemView.findViewById(R.id.chip_one) as Chip
            type_chip_two = itemView.findViewById(R.id.chip_two) as Chip
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
        holder.pokemonId.text = "#${pokemonList[position].id.toString().padStart(3, '0')}"

        holder.type_chip_one.chipText = pokemonList[position].type[0]

        if (pokemonList[position].type.size == 2) {
            holder.type_chip_two.chipText = pokemonList[position].type[1]
            holder.type_chip_two.visibility = View.VISIBLE
        } else {
            holder.type_chip_two.visibility = View.INVISIBLE
        }

        holder.setItemClickListener(View.OnClickListener {
            LocalBroadcastManager.getInstance(context)
                .sendBroadcast(Intent(Common.KEY_ENABLE_HOME).putExtra("position", position))
        })

        holder.linearLayout.setBackgroundColor(Common.getColorByType(pokemonList[position].type[0]))

    }

}