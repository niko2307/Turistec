package com.example.turistecdos

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Filterable
import android.widget.TextView
import java.util.Locale
import android.widget.Filter


class NormaAdapter(context: Context, normas: List<Norma>) :
    ArrayAdapter<Norma>(context, 0, normas), Filterable {

    private var normasList: List<Norma> = normas
    private var normasListFiltered: List<Norma> = normas

    override fun getCount(): Int {
        return normasListFiltered.size
    }

    override fun getItem(position: Int): Norma? {
        return normasListFiltered[position]
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var view = convertView
        if (view == null) {
            view = LayoutInflater.from(context).inflate(android.R.layout.simple_list_item_2, parent, false)
        }

        val norma = getItem(position)
        val textViewTitulo = view?.findViewById<TextView>(android.R.id.text1)
        val textViewDescripcion = view?.findViewById<TextView>(android.R.id.text2)

        textViewTitulo?.text = norma?.titulo
        textViewDescripcion?.text = norma?.descripcion

        return view!!
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charString = constraint.toString().toLowerCase(Locale.getDefault())
                normasListFiltered = if (charString.isEmpty()) {
                    normasList
                } else {
                    val filteredList = normasList.filter { it.titulo.toLowerCase(Locale.getDefault()).contains(charString) }
                    filteredList
                }

                val filterResults = FilterResults()
                filterResults.values = normasListFiltered
                return filterResults
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                normasListFiltered = results?.values as List<Norma>
                notifyDataSetChanged() // Notificar al adaptador que los datos han cambiado
            }
        }
    }
}