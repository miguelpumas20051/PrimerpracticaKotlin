package com.mik3.contactos4

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.SearchView
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import android.widget.*

class MainActivity : AppCompatActivity() {

    var lista: ListView? = null
    var grid: GridView? = null
    var viewSwitchter: ViewSwitcher?= null


    companion object {
        var contactos: ArrayList<Contacto>? = null
        var adaptador: AdaptadorCutsom? = null
        var adaptadorGrid: adapatadorCustomGrid? = null

        fun agregarContacto(contacto: Contacto) {
            adaptador?.addItem(contacto)
            adaptadorGrid?.addItem(contacto)
        }

        fun obtenerContacto(index: Int): Contacto{
            return adaptador?.getItem(index) as Contacto
        }

        fun eliminarContacto(index: Int){
            adaptador?.removeItem(index)
            adaptadorGrid?.removeItem(index)
        }

        fun actualizarContacto(index: Int, nuevoContacto: Contacto){
            adaptador?.updateItem(index, nuevoContacto)
            adaptadorGrid?.updateItem(index, nuevoContacto)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        contactos = ArrayList()
        contactos?.add(Contacto("Mike","[SM]","Nini [SM]",35,78.5f   ,"Calle Falsa 123","555555555","correofalso@servidorfalso.com",R.drawable.foto_01))
        contactos?.add(Contacto("Cosme" ,"Fulanito","Google",999,125.5f,"Avenida siempreviva 742","85548462","google@google.com",R.drawable.foto_05))
        contactos?.add(Contacto("Ichigo" ,"Kurosaki","Sociedad de almas",999,125.5f,"Seireitei S/N","85548462","ichigo@soulsociety.com",R.drawable.foto_03))
        contactos?.add(Contacto("Renji" ,"Abarai","Sociedad de almas",999,125.5f,"Seireitei S/N","85548462","renji@soulsociety.com",R.drawable.foto_02))


        lista = findViewById<ListView>(R.id.lista)
        grid = findViewById<GridView>(R.id.grid)
        adaptador = AdaptadorCutsom(this,contactos!!)
        adaptadorGrid = adapatadorCustomGrid(this,contactos!!)

        viewSwitchter = findViewById(R.id.viewSwitcher)

        lista?.adapter = adaptador
        grid?.adapter = adaptadorGrid


        lista?.setOnItemClickListener { parent, view, position, id ->
            val intent= Intent(this,Detalle::class.java)
            intent.putExtra("ID",position.toString())
            intent.putExtra("vista","lista")
            startActivity(intent)
        }

        grid?.setOnItemClickListener{parent,view, position, id->
            val intent=Intent(this,Detalle::class.java)
            intent.putExtra("ID",position.toString())
            intent.putExtra("vista","grid")
            startActivity(intent)
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main,menu)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val itemBusqueda= menu?.findItem(R.id.searchView)
        val searchView = itemBusqueda?.actionView as SearchView

        val itemSwitch = menu?.findItem(R.id.switchView)
        itemSwitch.setActionView(R.layout.switch_item)
        val switchView = itemSwitch?.actionView?.findViewById<Switch>(R.id.sCambiaVista)


        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.queryHint="Buscar Contacto..."

        searchView.setOnQueryTextFocusChangeListener {
                v, hasFocus ->

        }
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String?): Boolean {
                //filtrar
                adaptador?.filtrar(newText!!)
                adaptadorGrid?.filtrar(newText!!)
                return true
            }

            override fun onQueryTextSubmit(query: String?): Boolean {
                //filtrar
                return true
            }
        })

        switchView?.setOnCheckedChangeListener { buttonView, isChecked ->
            viewSwitchter?.showNext()
        }

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item?.itemId){
            R.id.iNuevo ->{
                val intent = Intent(this, Nuevo::class.java)
                startActivity(intent)
                return true
            }else ->{
            return super.onOptionsItemSelected(item)
        }
        }
    }

    override fun onResume() {
        super.onResume()
        adaptadorGrid?.notifyDataSetChanged()
        adaptador?.notifyDataSetChanged()
    }
}