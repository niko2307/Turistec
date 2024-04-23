package com.example.turistecdos

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView

class RutaAdapter(context: Context, rutas: List<Ruta>) :
    ArrayAdapter<Ruta>(context, R.layout.list_item_ruta, rutas) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var view = convertView
        val holder: ViewHolder

        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.list_item_ruta, parent, false)
            holder = ViewHolder()
            holder.textViewNombre = view.findViewById(R.id.textViewNombre)
            holder.textViewDistancia = view.findViewById(R.id.textViewDistancia)
            holder.textViewDuracion = view.findViewById(R.id.textViewDuracion)
            holder.textViewDescripcion = view.findViewById(R.id.textViewDescripcion)
            holder.textViewTipo = view.findViewById(R.id.textViewTipo)
            view.tag = holder
        } else {
            holder = view.tag as ViewHolder
        }

        val ruta = getItem(position)
        holder.textViewNombre?.text = ruta?.nombre
        holder.textViewDistancia?.text = "Distancia: ${ruta?.distancia} km"
        holder.textViewDuracion?.text = "Duración: ${ruta?.duracion}"
        holder.textViewDescripcion?.text = "Descripción: ${ruta?.descripcion}"
        holder.textViewTipo?.text = "Tipo: ${ruta?.tipo}"

        return view!!
    }

    private class ViewHolder {
        var textViewNombre: TextView? = null
        var textViewDistancia: TextView? = null
        var textViewDuracion: TextView? = null
        var textViewDescripcion: TextView? = null
        var textViewTipo: TextView? = null
    }
}