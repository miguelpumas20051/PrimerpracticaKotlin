package com.mik3.contactos4

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView

class adapatadorCustomGrid(var contexto: Context, items: ArrayList<Contacto>): BaseAdapter() {
    var items: ArrayList<Contacto>? = null
    var copiaItems:ArrayList<Contacto>? = null
    //alamacenar los elementos que se van a mostrar en el listview
    init {
        this.items= ArrayList(items)
        this.copiaItems=items
    }

    override fun getCount(): Int {
        //regresar el numero de elementos de mi lista
        return this.items?.count()!!
    }

    fun addItem(item: Contacto) {
        copiaItems?.add(item)
        items = ArrayList(copiaItems)
        notifyDataSetChanged()
    }

    fun removeItem(index: Int) {
        copiaItems?.removeAt(index)
        items = ArrayList(copiaItems)
        notifyDataSetChanged()

    }

    fun updateItem(index: Int, newItem: Contacto) {
        copiaItems?.set(index, newItem)
        items = ArrayList(copiaItems)
        notifyDataSetChanged()
    }

    fun filtrar(str: String) {
        items?.clear()
        if(str.isEmpty()){
            items = ArrayList(copiaItems)
            notifyDataSetChanged()
            return
        }

        var busqueda = str.lowercase()
        for(item in copiaItems!!) {
            val nombre = item.nombre.lowercase()
            if(nombre.contains(busqueda)) {
                items?.add(item)
            }
        }
        notifyDataSetChanged()
    }

    fun actualizarItems(){

    }

    fun obtenerItem(){}

    override fun getItem(position: Int): Any {
        return this.items?.get(position)!!
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var viewHolder: ViewHolder? = null
        var vista: View? = convertView

        if(vista == null) {
            vista= LayoutInflater.from(contexto).inflate(R.layout.template_contacto_grid,null)
            viewHolder= ViewHolder(vista)
            vista.tag=viewHolder
        } else {
            viewHolder=vista.tag as? ViewHolder
        }
        val item = getItem(position) as Contacto
        //Asiganacion de valores a elementos graficos
        viewHolder?.nombre?.text = item.nombre + " " + item.apellidos
        viewHolder?.foto?.setImageResource(item.foto)

        return vista!!
    }

    private class ViewHolder(vista: View) {
        var nombre: TextView? = null
        var foto: ImageView? = null
        init {
            nombre = vista.findViewById(R.id.tvNombre3)
            foto = vista.findViewById(R.id.ivFoto3)
        }
    }
}