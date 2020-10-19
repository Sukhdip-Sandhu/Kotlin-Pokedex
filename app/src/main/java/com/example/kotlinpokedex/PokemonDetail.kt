package com.example.kotlinpokedex


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DecodeFormat
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import com.bumptech.glide.request.target.Target.SIZE_ORIGINAL
import com.example.kotlinpokedex.Adapter.EvolutionAdapter
import com.example.kotlinpokedex.Adapter.PokemonTypeAdapter
import com.example.kotlinpokedex.Common.Common
import com.example.kotlinpokedex.Model.Pokemon
import com.robertlevonyan.views.chip.Chip

/**
 * A simple [Fragment] subclass.
 */
class PokemonDetail : Fragment() {
    internal lateinit var detailsBackground: RelativeLayout

    internal lateinit var pokemonImg: ImageView
    internal lateinit var pokemonName: TextView
    internal lateinit var pokemonId: TextView
    internal lateinit var pokemonWeight: Chip
    internal lateinit var pokemonHeight: Chip

    internal lateinit var recyclerType: RecyclerView
    internal lateinit var recyclerWeakness: RecyclerView
    internal lateinit var recyclerPrevEvolution: RecyclerView
    internal lateinit var recyclerNextEvolution: RecyclerView

    companion object {
        internal var instance: PokemonDetail? = null

        fun getInstance(): PokemonDetail {
            if (instance == null)
                instance = PokemonDetail()
            return instance!!
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val itemView = inflater.inflate(R.layout.fragment_pokemon_detail, container, false)

        val pokemon: Pokemon?

        if (arguments!!.getString("num") == null)
            pokemon = Common.pokemonList[arguments!!.getInt("position")]
        else
            pokemon = Common.findPokemonByNum(arguments!!.getString("num"))

        pokemonImg = itemView.findViewById(R.id.pokemon_image_details) as ImageView

        detailsBackground = itemView.findViewById(R.id.details_background) as RelativeLayout

        pokemonName = itemView.findViewById(R.id.pokemon_name_details) as TextView
        pokemonId = itemView.findViewById(R.id.pokemon_id_details) as TextView
        pokemonWeight = itemView.findViewById(R.id.pokemon_weight_details) as Chip
        pokemonHeight = itemView.findViewById(R.id.pokemon_height_details) as Chip

        recyclerType = itemView.findViewById(R.id.type_recycler_view) as RecyclerView
        recyclerType.setHasFixedSize(true)
        recyclerType.layoutManager =
            LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)

        recyclerWeakness = itemView.findViewById(R.id.weakness_recycler_view) as RecyclerView
        recyclerWeakness.setHasFixedSize(true)
        recyclerWeakness.layoutManager =
            LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)

        recyclerPrevEvolution = itemView.findViewById(R.id.prev_evol_recycler_view) as RecyclerView
        recyclerPrevEvolution.setHasFixedSize(true)
        recyclerPrevEvolution.layoutManager =
            LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)

        recyclerNextEvolution = itemView.findViewById(R.id.next_evol_recycler_view) as RecyclerView
        recyclerNextEvolution.setHasFixedSize(true)
        recyclerNextEvolution.layoutManager =
            LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)

        setDetailPokemon(pokemon)

        return itemView
    }

    private fun setDetailPokemon(pokemon: Pokemon?) {

        detailsBackground.setBackgroundColor(Common.getColorByType(pokemon!!.type[0]))

        Glide.with(activity!!)
            .load(pokemon!!.img)
            .dontTransform()
            .format(DecodeFormat.PREFER_ARGB_8888)
            .into(pokemonImg)

        pokemonName.text = pokemon.name
        pokemonId.text = "#${pokemon.id.toString().padStart(3, '0')}"
        pokemonHeight.chipText = pokemon.height
        pokemonWeight.chipText = pokemon.weight

        val typeAdapter = PokemonTypeAdapter(activity!!, pokemon.type!!)
        recyclerType.adapter = typeAdapter

        val weaknessAdapter = PokemonTypeAdapter(activity!!, pokemon.weaknesses!!)
        recyclerWeakness.adapter = weaknessAdapter

        if (!pokemon.prevEvolution.isNullOrEmpty()) {
            val previousEvolutionAdapter = EvolutionAdapter(activity!!, pokemon.prevEvolution!!)
            recyclerPrevEvolution.adapter = previousEvolutionAdapter
        }
        if (!pokemon.nextEvolution.isNullOrEmpty()) {
            val nextEvolutionAdapter = EvolutionAdapter(activity!!, pokemon.nextEvolution!!)
            recyclerNextEvolution.adapter = nextEvolutionAdapter
        }
    }


}
