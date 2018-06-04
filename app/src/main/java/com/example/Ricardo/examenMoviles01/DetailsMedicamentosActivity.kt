package com.example.Ricardo.examenMoviles01

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_details_medicamento.*

class DetailsMedicamentosActivity : AppCompatActivity() {

    var medicamento: Medicamento? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details_medicamento)

        medicamento = intent.getParcelableExtra("medicamento")

        txtShowGramosAIngerir.text = medicamento?.gramosAIngerir.toString()
        txtShowNombreMedicamento.text = medicamento?.nombre
        txtShowComposicion.text = medicamento?.composicion.toString()
        txtShowUsadoPara.text = medicamento?.usadoPara.toString()
        txtShowFechaCaducidad.text = medicamento?.fechaCaducidad
        txtShowNumeroPastillas.text = medicamento?.numeroPastillas.toString()
    }
}
