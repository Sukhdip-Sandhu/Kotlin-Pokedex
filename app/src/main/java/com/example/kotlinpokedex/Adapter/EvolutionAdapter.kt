package com.example.kotlinpokedex.Adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlinpokedex.Common.Common
import com.example.kotlinpokedex.Model.Evolution
import com.example.kotlinpokedex.R
import com.robertlevonyan.views.chip.Chip

class EvolutionAdapter(
    internal var context: Context,
    var evolutionList: List<Evolution>?
//    var nextEvolutionList: List<NextEvolution>?
) : RecyclerView.Adapter<EvolutionAdapter.MyViewHolder>() {

    init {
        if (evolutionList == null)
            evolutionList = ArrayList()
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        internal var chip: Chip

        init {
            chip = itemView.findViewById(R.id.chip) as Chip
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView =
            LayoutInflater.from(context).inflate(R.layout.chip_item, parent, false)
        return MyViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return evolutionList!!.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.chip.chipText = evolutionList!![position].name
        holder.chip.changeBackgroundColor(
            Common.getColorByType(
                Common.findPokemonByNum(evolutionList!![position].num)!!.type[0])
        )
        holder.chip.setOnChipClickListener {
            LocalBroadcastManager.getInstance(context)
                .sendBroadcast(Intent(Common.KEY_NUM_EVOLUTION).putExtra("num", evolutionList!![position].num))
        }
    }

}