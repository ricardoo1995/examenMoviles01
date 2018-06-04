package com.example.Ricardo.examenMoviles01

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_list.*

class ListActivity : AppCompatActivity() {

    lateinit var adaptador: PacienteAdapter
    lateinit var pacientes: ArrayList<Paciente>
    lateinit var dbHandler: DBPacienteHandlerAplicacion

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)

        dbHandler = DBPacienteHandlerAplicacion(this)
        pacientes = dbHandler.getPacientesList()

        val layoutManager = LinearLayoutManager(this)
        adaptador = PacienteAdapter(pacientes)
        recycler_view.layoutManager = layoutManager
        recycler_view.itemAnimator = DefaultItemAnimator()
        recycler_view.adapter = adaptador
        adaptador.notifyDataSetChanged()

        registerForContextMenu(recycler_view)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        var position = adaptador.getPosition()
        var paciente = pacientes[position]

        when (item.itemId) {
            R.id.item_menu_compartir -> {
                val intent = Intent(Intent.ACTION_SEND)
                intent.type = "text/html"
                intent.putExtra(Intent.EXTRA_SUBJECT, "${getString(R.string.paciente)} - ${getString(R.string.app_name)}")
                intent.putExtra(Intent.EXTRA_TEXT, "${getString(R.string.name)} ${paciente.nombre} ${paciente.apellido}\n${getString(R.string.numero_hijos)} ${paciente.numeroHijos}\n${"Fecha Nacimiento"}\n" +
                        "\${paciente.fechaNacimiento}")
                startActivity(intent)
                return true
            }
            R.id.item_menu_editar -> {
                val intent = Intent(this, CreateActivity::class.java)
                intent.putExtra("tipo", "Edit")
                intent.putExtra("paciente", paciente)
                startActivity(intent)
                return true
            }
            R.id.item_menu_borrar -> {
                val builder = AlertDialog.Builder(this)
                builder.setMessage("Seguro que desea eliminar")
                        .setPositiveButton("SI", { dialog, which ->
                            dbHandler.deletePaciente(paciente.id)
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
