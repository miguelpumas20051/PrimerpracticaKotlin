package com.mik3.contactos4

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.*

class Nuevo : AppCompatActivity() {
    var fotoIndex: Int=0
    val fotos = arrayOf(R.drawable.foto_01,R.drawable.foto_02,R.drawable.foto_03,R.drawable.foto_04,R.drawable.foto_05,R.drawable.foto_06)
    var foto:ImageView? = null
    var index:Int=-1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nuevo)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        var actionBar =  supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)

        foto=findViewById<ImageView>(R.id.ivFoto2)
        foto?.setOnClickListener{
            seleccionarFoto()
        }

        //reconocer acción de nuevo vs editar
        if(intent.hasExtra("ID")){
            index= intent.getStringExtra("ID")!!.toInt()
            rellenarDatos(index)
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_nuevo,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item?.itemId){
            android.R.id.home->{
                finish()
                return true
            }
            R.id.iCrearNuevo ->{
                /// Crear Nuevo elemento de tipo contacto
                val nombre = findViewById<EditText>(R.id.tvNombre2)
                val apellido = findViewById<EditText>(R.id.tvApellidos)
                val empresa = findViewById<EditText>(R.id.tvEmpresa2)
                val edad = findViewById<EditText>(R.id.tvEdad)
                val peso = findViewById<EditText>(R.id.tvPeso)
                val telefono= findViewById<EditText>(R.id.tvTelefono)
                val email = findViewById<EditText>(R.id.tvEmail)
                val direccion = findViewById<EditText>(R.id.tvDireccion)

                //validar campos
                var campos = ArrayList<String>()
                campos.add(nombre.text.toString())
                campos.add(apellido.text.toString())
                campos.add(empresa.text.toString())
                campos.add(edad.text.toString())
                campos.add(peso.text.toString())
                campos.add(telefono.text.toString())
                campos.add(email.text.toString())
                campos.add(direccion.text.toString())

                var flag=0
                for (campo in campos){
                    if (campo.isNullOrEmpty())
                        flag++
                }
                if(flag>0){
                    Toast.makeText(this,"Rellena todos los campos",Toast.LENGTH_SHORT).show()
                }else{
                    if(index>-1){
                        MainActivity.actualizarContacto(index,Contacto(campos.get(0),campos.get(1),campos.get(2),campos.get(3).toInt(),campos.get(4).toFloat(),campos.get(5),campos.get(6),campos.get(7),obtenerFoto(fotoIndex)))
                    } else{
                        MainActivity.agregarContacto(Contacto(campos.get(0),campos.get(1),campos.get(2),campos.get(3).toInt(),campos.get(4).toFloat(),campos.get(5),campos.get(6),campos.get(7),obtenerFoto(fotoIndex)))
                    }
                        finish()
                        Log.d("No. elementos",MainActivity.contactos?.count().toString())
                    }
                    return true
                }
                else ->{return super.onOptionsItemSelected(item)
            }
        }
    }
    fun seleccionarFoto(){
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Selecciona Imagen de Perfil")

        val adapatdorDialogo = ArrayAdapter<String>(this,android.R.layout.simple_selectable_list_item)
        adapatdorDialogo.add("Foto 01")
        adapatdorDialogo.add("Foto 02")
        adapatdorDialogo.add("Foto 03")
        adapatdorDialogo.add("Foto 04")
        adapatdorDialogo.add("Foto 05")
        adapatdorDialogo.add("Foto 06")

        builder.setAdapter(adapatdorDialogo){
            dialog,which->
            fotoIndex=which
            foto?.setImageResource(obtenerFoto(fotoIndex))
        }
        builder.setNegativeButton("Cancelar"){
                dialog, which ->
            dialog.dismiss()
        }
        builder.show()
    }

    fun obtenerFoto(index: Int):Int{
        return fotos.get(index)
    }

    fun rellenarDatos(index:Int){
        val contacto=MainActivity.obtenerContacto(index)

        val tvNombre = findViewById<EditText>(R.id.tvNombre2)
        val tvApellido=findViewById<EditText>(R.id.tvApellidos)
        val tvEmpresa = findViewById<EditText>(R.id.tvEmpresa2)
        val tvEdad= findViewById<EditText>(R.id.tvEdad)
        val tvPeso = findViewById<EditText>(R.id.tvPeso)
        val tvTelefono = findViewById<EditText>(R.id.tvTelefono)
        val tvEmail = findViewById<EditText>(R.id.tvEmail)
        val tvDireccion = findViewById<EditText>(R.id.tvDireccion)
        val ivFoto = findViewById<ImageView>(R.id.ivFoto2)

        tvNombre.setText(contacto.nombre,TextView.BufferType.EDITABLE)
        tvApellido.setText(contacto.apellidos,TextView.BufferType.EDITABLE)
        tvEmpresa.setText(contacto.empresa,TextView.BufferType.EDITABLE)
        tvEdad.setText(contacto.edad.toString(),TextView.BufferType.EDITABLE)
        tvPeso.setText(contacto.peso.toString(),TextView.BufferType.EDITABLE)
        tvTelefono.setText(contacto.telefono,TextView.BufferType.EDITABLE)
        tvEmail.setText(contacto.email,TextView.BufferType.EDITABLE)
        tvDireccion.setText(contacto.direccion,TextView.BufferType.EDITABLE)

        ivFoto.setImageResource(contacto.foto)

        var posicion=0
        for (foto in fotos){
            if(contacto.foto==foto){
                fotoIndex=posicion
            }
            posicion++
        }

    }
}