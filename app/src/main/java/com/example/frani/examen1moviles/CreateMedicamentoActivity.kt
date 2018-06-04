package com.example.frani.examen1moviles

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_create_medicamento.*

class CreateMedicamentoActivity : AppCompatActivity() {

    lateinit var dbHandler: DBMedicamentoHandlerAplicacion
    var idPaciente: Int = 0
    var medicamento: Medicamento? = null
    var tipo = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_medicamento)

        val type = intent.getStringExtra("tipo")

        if (type.equals("Edit")) {
            textViewMedicamento.text = getString(R.string.edit_medicamento)
            medicamento = intent.getParcelableExtra("medicamento")
            fillFields()
            tipo = true
        }

        idPaciente = intent.getIntExtra("idPaciente", 0)

        dbHandler = DBMedicamentoHandlerAplicacion(this)

        btnCrearMedicamento.setOnClickListener{
            v: View? ->  crearMedicamento()
        }
    }

    fun fillFields() {
        txtGramosAIngerir.setText(medicamento?.gramosAIngerir.toString())
        txtNombreMedicamento.setText(medicamento?.nombre)
        txtComposicion.setText(medicamento?.composicion.toString())
        txtUsadoPara.setText(medicamento?.usadoPara)
        txtFechaCaducidad.setText(medicamento?.fechaCaducidad)
        txtNumeroPastillas.setText(medicamento?.numeroPastillas.toString())
    }

    fun crearMedicamento() {
        var gramosAIngerir = txtGramosAIngerir.text.toString().toFloat()
        var nombre = txtNombreMedicamento.text.toString()
        var composicion = txtComposicion.text.toString()
        var usadoPara = txtUsadoPara.text.toString()
        var fechaCaducidad = txtFechaCaducidad.text.toString()
        var numeroPastillas = txtNumeroPastillas.text.toString().toInt()
        var medicamento = Medicamento(gramosAIngerir, nombre, composicion, usadoPara, fechaCaducidad, numeroPastillas, idPaciente)

        if (!tipo) {
            dbHandler.insertarMedicamento(medicamento)
        } else {
            dbHandler.updateMedicamento(medicamento)
        }

        finish()
    }
}
