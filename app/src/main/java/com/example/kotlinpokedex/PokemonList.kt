package com.example.kotlinpokedex


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlinpokedex.Adapter.PokemonListAdapter
import com.example.kotlinpokedex.Common.Common
import com.example.kotlinpokedex.Common.ItemOffsetDecoration
import com.example.kotlinpokedex.Model.Pokedex
import com.example.kotlinpokedex.Retrofit.IPokemonList
import com.example.kotlinpokedex.Retrofit.RetrofitClient
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


class PokemonList : Fragment() {

    internal var compositeDisposable = CompositeDisposable()
    internal var iPokemonList: IPokemonList

    internal lateinit var recyclerView: RecyclerView

    init {
        val retrofit = RetrofitClient.instance
        iPokemonList = retrofit.create(IPokemonList::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val itemview = inflater.inflate(R.layout.fragment_pokemon_list, container, false)

        recyclerView = itemview.findViewById(R.id.pokemon_recycler_view) as RecyclerView

        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = GridLayoutManager(activity, 2)
        val itemDecoration = ItemOffsetDecoration(activity!!, R.dimen.spacing)
        recyclerView.addItemDecoration(itemDecoration)

        fetchData()

        return itemview
    }

    private fun fetchData() {
        compositeDisposable.add(iPokemonList.listPokemon
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { pokedex: Pokedex? ->
                Common.pokemonList = pokedex!!.pokemon
                val adapter = PokemonListAdapter(activity!!, Common.pokemonList)
                recyclerView.adapter = adapter
            }
        )
    }


}
