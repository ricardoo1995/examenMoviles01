package com.example.Ricardo.examenMoviles01
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.beust.klaxon.Klaxon
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.result.Result
import java.util.*




class HttpFuel : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create)

        "http://172.31.104.19:1337/Paciente/1"
                .httpGet()
                .responseString { request, response, result ->
                    when (result) {
                        is Result.Failure -> {
                            val ex = result.getException()
                            Log.i("http-ejemplo", "Error ${ex.response}")
                        }
                        is Result.Success -> {
                            val jsonStringPaciente = result.get()
                            Log.i("http-ejemplo", "Exito ${jsonStringPaciente}")

                            val Paciente: Paciente? = Klaxon()
                                    .parse<Paciente>(jsonStringPaciente)

                            if (Paciente != null) {
                                Log.i("http-ejemplo", "Nombre: ${Paciente.nombre}")
                                Log.i("http-ejemplo", "Nombre: ${Paciente.apellido}")
                                Log.i("http-ejemplo", "Apellido: ${Paciente.fechaNacimiento}")
                                Log.i("http-ejemplo", "Id: ${Paciente.numeroHijos}")
                                Log.i("http-ejemplo", "Medallas: ${Paciente.tieneSeguro}")



                                Paciente.Medicamentos.forEach { Medicamento: Medicamento ->
                                    Log.i("http-ejemplo", "Nombre ${Medicamento.gramosAIngerir}")
                                    Log.i("http-ejemplo", "Nombre ${Medicamento.nombre}")
                                    Log.i("http-ejemplo", "Nombre ${Medicamento.composicion}")
                                    Log.i("http-ejemplo", "Nombre ${Medicamento.usadoPara}")
                                    Log.i("http-ejemplo", "Nombre ${Medicamento.fechaCaducidad}")
                                    Log.i("http-ejemplo", "Nombre ${Medicamento.numeroPastillas}")
                                    Log.i("http-ejemplo", "Nombre ${Medicamento.pacienteID}")


                                }



                            } else {
                                Log.i("http-ejemplo", "Paciente nulo")
                            }


                        }
                    }
                }
    }
}

class Paciente(  var id: Int,
                 var nombre: String,
                 var apellido: String,
                 var fechaNacimiento: String,
                 var numeroHijos: Int,
                 var tieneSeguro: Boolean,
                 var createdAt: Long,
                 var updatedAt: Long,

                 var Medicamentos: List<Medicamento>) {
                 var createdAtDate = Date(updatedAt)
                 var updatedAtDate = Date(createdAt)


}

class Medicamento(var nombre: String,
              var numero: Int,
              var tipo: String,
              var createdAt: Long,
              var updatedAt: Long,
              var id: Int,
              var PacienteId: Int) {
    var createdAtDate = Date(updatedAt)
    var updatedAtDate = Date(createdAt)
}
