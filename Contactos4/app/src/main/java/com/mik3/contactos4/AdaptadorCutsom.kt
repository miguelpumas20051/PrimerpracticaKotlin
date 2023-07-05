package com.mik3.contactos4

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import org.w3c.dom.Text

class AdaptadorCutsom (var contexto: Context, items: ArrayList<Contacto>):BaseAdapter(){

    var items: ArrayList<Contacto>?= null
    var copiaItems:ArrayList<Contacto>?= null
    var vistaPantalla:String ?= null

    //alamacenar los elementos que se van a mostrar en el listview
    init {
        this.items= ArrayList(items)
        this.copiaItems=items
        this.vistaPantalla=vistaPantalla
    }


    override fun getCount(): Int {
        //regresar el numero de elementos de mi lista
        return this.items?.count()!!
    }

    fun addItem(item: Contacto){
        copiaItems?.add(item)
        items= ArrayList(copiaItems)
        notifyDataSetChanged()
    }

    fun removeItem(index:Int){
        copiaItems?.removeAt(index)
        items= ArrayList(copiaItems)
        notifyDataSetChanged()
    }

    fun updateItem(index:Int, newItem:Contacto){
        copiaItems?.set(index,newItem)
//        items= ArrayList(copiaItems)
        notifyDataSetChanged()
    }

    fun filtrar(str: String){
        items?.clear()
        if(str.isEmpty()){
            items= ArrayList(copiaItems)
            notifyDataSetChanged()
            return
        }

        var busqueda=str
        busqueda=busqueda.lowercase()
        for(item in copiaItems!!){
            val nombre= item.nombre.lowercase()
            if(nombre.contains(busqueda)){
                items?.add(item)
            }
        }
        notifyDataSetChanged()
    }

    fun obtenerItem(){}

    override fun getItem(position: Int): Any {
        return this.items?.get(position)!!
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var viewHolder:ViewHolder? = null
        var vista:View? = convertView

        if(vista==null){

                vista=LayoutInflater.from(contexto).inflate(R.layout.template_contacto,null)
                viewHolder= ViewHolder(vista)
                vista.tag=viewHolder

        }else{
            viewHolder=vista.tag as? ViewHolder
        }
        val item = getItem(position) as Contacto
        //Asiganacion de valores a elementos graficos
        viewHolder?.nombre?.text = item.nombre + " " + item.apellidos
        viewHolder?.empresa?.text = item.empresa
        viewHolder?.foto?.setImageResource(item.foto)

        return vista!!
    }

    private class ViewHolder(vista:View){
        var nombre:TextView? = null
        var foto:ImageView? = null
        var empresa:TextView? = null
        init {
            nombre = vista.findViewById(R.id.tvNombre)
            empresa= vista.findViewById(R.id.tvEmpresa)
            foto=vista.findViewById(R.id.ivFoto)
        }
    }
}