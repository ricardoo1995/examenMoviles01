package com.example.Ricardo.examenMoviles01

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.MenuItem
import android.view.View
import kotlinx.android.synthetic.main.activity_details.*

class DetailsActivity : AppCompatActivity() {

    var paciente: Paciente? = null
    lateinit var adaptador: MedicamentoAdapter
    lateinit var medicamentos: ArrayList<Medicamento>
    lateinit var dbHandler: DBMedicamentoHandlerAplicacion

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        paciente = intent.getParcelableExtra("paciente")

        txtShowNombrePaciente.text = paciente?.nombre
        txtShowApellidoPaciente.text = paciente?.apellido
        txtShowFechaNPaciente.text = paciente?.fechaNacimiento
        txtShowNumHijos.text = paciente?.numeroHijos.toString()
        txtShowSeguro.text = if(paciente?.tieneSeguro ==  1) "Si" else "No"

        dbHandler = DBMedicamentoHandlerAplicacion(this)
        medicamentos = dbHandler.getMedicamentosList(paciente?.id!!)

        val layoutManager = LinearLayoutManager(this)
        adaptador = MedicamentoAdapter(medicamentos)
        recycler_view_medicamentos.layoutManager = layoutManager
        recycler_view_medicamentos.itemAnimator = DefaultItemAnimator()
        recycler_view_medicamentos.adapter = adaptador
        adaptador.notifyDataSetChanged()

        registerForContextMenu(recycler_view_medicamentos)

        btnNuevoMedicamento.setOnClickListener{
            v: View? ->  crearMedicamento()
        }
    }

    fun crearMedicamento() {
        val intent = Intent(this, CreateMedicamentoActivity::class.java)
        intent.putExtra("tipo", "Create")
        intent.putExtra("idPaciente", paciente?.id!!)
        startActivity(intent)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        var position = adaptador.getPosition()
        var medicamento = medicamentos[position]

        when (item.itemId) {
            R.id.item_menu_compartir -> {
                val intent = Intent(Intent.ACTION_SEND)
                intent.type = "text/html"
                intent.putExtra(Intent.EXTRA_SUBJECT, "${"Medicamentos"} - ${"Medicamentos y Pacientes"}")
                intent.putExtra(Intent.EXTRA_TEXT, "${"Gramos a ingerir"} ${medicamento.gramosAIngerir}\n${getString(R.string.name)} ${medicamento.nombre}\n${"Usado para:"} ${medicamento.usadoPara}\n${"Numero Pastillas"} ${medicamento.numeroPastillas}")
                startActivity(intent)
                return true
            }
            R.id.item_menu_editar -> {
                val intent = Intent(this, CreateMedicamentoActivity::class.java)
                intent.putExtra("tipo", "Edit")
                intent.putExtra("medicamento", medicamento)
                startActivity(intent)
                return true
            }
            R.id.item_menu_borrar -> {
                val builder = AlertDialog.Builder(this)
                builder.setMessage("¿Seguro desea eliminar?")
                        .setPositiveButton("SI", { dialog, which ->
                            dbHandler.deleteMedicamento(medicamento.gramosAIngerir)
                            finish()
                            startActivity(intent)
                        }
                        )
                        .setNegativeButton(R.string.no, null)
                val dialogo = builder.create()
                dialogo.show()
                return true
            }
            else -> {
                Log.i("menu", "Todos los demas")
                return super.onOptionsItemSelected(item)
            }
        }
    }
}
