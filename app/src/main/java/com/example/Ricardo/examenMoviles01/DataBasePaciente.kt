package com.example.Ricardo.examenMoviles01

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

class DataBasePaciente {
    companion object {
        val DB_NAME = "paciente"
        val TABLE_NAME = "paciente"
        val CAMPO_ID = "id"
        val CAMPO_NOMBRE = "nombre"
        val CAMPO_APELLIDO = "apellido"
        val CAMPO_FECHANACIMIENTO = "fechaNacimiento"
        val CAMPO_NUMEROHIJOS = "numeroHijos"
        val CAMPO_TIENESEGURO = "tieneSeguro"
    }
}

class DBPacienteHandlerAplicacion(context: Context) : SQLiteOpenHelper(context, DataBasePaciente.DB_NAME, null, 1) {

    override fun onCreate(db: SQLiteDatabase?) {

        val createTableSQL = "CREATE TABLE ${DataBasePaciente.TABLE_NAME} (${DataBasePaciente.CAMPO_ID} INTEGER PRIMARY KEY AUTOINCREMENT, ${DataBasePaciente.CAMPO_NOMBRE} VARCHAR(50),${DataBasePaciente.CAMPO_APELLIDO} VARCHAR(50),${DataBasePaciente.CAMPO_FECHANACIMIENTO} VARCHAR(20), ${DataBasePaciente.CAMPO_NUMEROHIJOS} INTEGER, ${DataBasePaciente.CAMPO_TIENESEGURO} INTEGER)"
        db?.execSQL(createTableSQL)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun insertarPaciente(paciente: Paciente) {
        val dbWriteable = writableDatabase
        val cv = ContentValues()

        cv.put(DataBasePaciente.CAMPO_NOMBRE, paciente.nombre)
        cv.put(DataBasePaciente.CAMPO_APELLIDO, paciente.apellido)
        cv.put(DataBasePaciente.CAMPO_FECHANACIMIENTO, paciente.fechaNacimiento)
        cv.put(DataBasePaciente.CAMPO_NUMEROHIJOS, paciente.numeroHijos)
        cv.put(DataBasePaciente.CAMPO_TIENESEGURO, paciente.tieneSeguro)

        val resultado = dbWriteable.insert(DataBasePaciente.TABLE_NAME, null, cv)

        Log.i("database", "Si es -1 hubo error, sino exito: Respuesta: $resultado")

        dbWriteable.close()
    }

    fun updatePaciente(paciente: Paciente) {
        val dbWriteable = writableDatabase
        val cv = ContentValues()

        cv.put(DataBasePaciente.CAMPO_NOMBRE, paciente.nombre)
        cv.put(DataBasePaciente.CAMPO_APELLIDO, paciente.apellido)
        cv.put(DataBasePaciente.CAMPO_FECHANACIMIENTO, paciente.fechaNacimiento)
        cv.put(DataBasePaciente.CAMPO_NUMEROHIJOS, paciente.numeroHijos)
        cv.put(DataBasePaciente.CAMPO_TIENESEGURO, paciente.tieneSeguro)

        val whereClause = "${DataBasePaciente.CAMPO_ID} = ${paciente.id}"
        val resultado = dbWriteable.update(DataBasePaciente.TABLE_NAME, cv, whereClause, null)

        Log.i("database", "Si es -1 hubo error, sino exito: Respuesta: $resultado")

        dbWriteable.close()
    }

    fun deletePaciente(id: Int): Boolean {
        val dbWriteable = writableDatabase
        val whereClause = "${DataBasePaciente.CAMPO_ID} = $id"
        return dbWriteable.delete(DataBasePaciente.TABLE_NAME, whereClause, null) > 0
    }

    fun getPacientesList(): ArrayList<Paciente> {
        var lista = ArrayList<Paciente>()
        val dbReadable = readableDatabase
        val query = "SELECT * FROM ${DataBasePaciente.TABLE_NAME}"
        val resultado = dbReadable.rawQuery(query, null)

        if (resultado.moveToFirst()) {
            do {
                val id = resultado.getString(0).toInt()
                val nombre = resultado.getString(1)
                val apellido = resultado.getString(2)
                val fechaNacimiento = resultado.getString(3)
                val numeroHijos = resultado.getString(4).toInt()
                val tieneSeguro = resultado.getString(5).toBoolean()

                lista.add(Paciente(id, nombre, apellido, fechaNacimiento, numeroHijos, tieneSeguro))
            } while (resultado.moveToNext())
        }

        resultado.close()
        dbReadable.close()

        return lista
    }

}
