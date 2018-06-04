package com.example.Ricardo.examenMoviles01

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_create.*

class CreateActivity : AppCompatActivity() {

    lateinit var dbHandler: DBPacienteHandlerAplicacion
    var paciente: Paciente? = null
    var tipo = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create)

        val type = intent.getStringExtra("tipo")

        if (type.equals("Edit")) {
            textViewPaciente.text = getString(R.string.edit_paciente)
            paciente = intent.getParcelableExtra("paciente")
            fillFields()
            tipo = true
        }

        dbHandler = DBPacienteHandlerAplicacion(this)

        btnCrearPaciente.setOnClickListener{
            v: View? -> crearPaciente()
        }
    }

    fun fillFields() {
        txtNombrePaciente.setText(paciente?.nombre)
        txtApellidoPaciente.setText(paciente?.apellido)
        txtFechaPaciente.setText(paciente?.fechaNacimiento)
        txtNumeroHijos.setText(paciente?.numeroHijos.toString())
        if (paciente?.tieneSeguro == 1) {
            switchSegPaciente.toggle()
        }
    }

    fun crearPaciente() {
        var nombre = txtNombrePaciente.text.toString()
        var apellido = txtApellidoPaciente.text.toString()
        var fecha = txtFechaPaciente.text.toString()
        var numeroHijos = txtNumeroHijos.text.toString().toInt()
        var tieneSeguro = if (switchSegPaciente.isChecked) 1 else 0

        if (!tipo) {
            var paciente = Paciente(0, nombre, apellido, fecha, numeroHijos, tieneSeguro)
            dbHandler.insertarPaciente(paciente)
        } else {
            var paciente = Paciente(paciente?.id!!, nombre, apellido, fecha, numeroHijos, tieneSeguro)
            dbHandler.updatePaciente(paciente)
        }

        irAListView()
    }

    fun irAListView() {
        val intent = Intent(this, ListActivity::class.java)
        startActivity(intent)
    }
}
