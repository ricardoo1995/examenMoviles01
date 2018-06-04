package com.example.frani.examen1moviles

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

class DataBaseMedicamento {
    companion object {
        val DB_NAME = "Medicamentos"
        val TABLE_NAME = "medicamento"
        val CAMPO_GRAMOSAINGERIR = "gramosAIngerir"
        val CAMPO_NOMBRE = "nombre"
        val CAMPO_COMPOSICION = "composicion"
        val CAMPO_USADOPARA = "usadoPara"
        val CAMPO_FECHACADUCIDAD = "fechaCaducidad"
        val CAMPO_NUMEROPASTILLAS = "numeroPastillas"
        val CAMPO_PACIENTEID = "pacienteID"
    }
}

class DBMedicamentoHandlerAplicacion(context: Context) : SQLiteOpenHelper(context, DataBaseMedicamento.DB_NAME, null, 1) {

    override fun onCreate(db: SQLiteDatabase?) {

        val createTableSQL = "CREATE TABLE ${DataBaseMedicamento.TABLE_NAME} (${DataBaseMedicamento.CAMPO_GRAMOSAINGERIR} INTEGER PRIMARY KEY, ${DataBaseMedicamento.CAMPO_NOMBRE} VARCHAR(50),${DataBaseMedicamento.CAMPO_COMPOSICION} VARCHAR(60),${DataBaseMedicamento.CAMPO_USADOPARA} VARCHAR(20), ${DataBaseMedicamento.CAMPO_FECHACADUCIDAD} VARCHAR(20), ${DataBaseMedicamento.CAMPO_NUMEROPASTILLAS} INTERGER,  ${DataBaseMedicamento.CAMPO_PACIENTEID} INTEGER)"
        db?.execSQL(createTableSQL)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun insertarMedicamento(medicamento: Medicamento) {
        val dbWriteable = writableDatabase
        val cv = ContentValues()

        cv.put(DataBaseMedicamento.CAMPO_GRAMOSAINGERIR, medicamento.gramosAIngerir)
        cv.put(DataBaseMedicamento.CAMPO_NOMBRE, medicamento.nombre)
        cv.put(DataBaseMedicamento.CAMPO_COMPOSICION, medicamento.composicion)
        cv.put(DataBaseMedicamento.CAMPO_USADOPARA, medicamento.usadoPara)
        cv.put(DataBaseMedicamento.CAMPO_FECHACADUCIDAD, medicamento.fechaCaducidad)
        cv.put(DataBaseMedicamento.CAMPO_NUMEROPASTILLAS, medicamento.numeroPastillas)
        cv.put(DataBaseMedicamento.CAMPO_PACIENTEID, medicamento.pacienteID)

        val resultado = dbWriteable.insert(DataBaseMedicamento.TABLE_NAME, null, cv)

        Log.i("database", "Si es -1 hubo error, sino exito: Respuesta: $resultado")

        dbWriteable.close()
    }

    fun updateMedicamento(medicamento: Medicamento) {
        val dbWriteable = writableDatabase
        val cv = ContentValues()

        cv.put(DataBaseMedicamento.CAMPO_GRAMOSAINGERIR, medicamento.gramosAIngerir)
        cv.put(DataBaseMedicamento.CAMPO_NOMBRE, medicamento.nombre)
        cv.put(DataBaseMedicamento.CAMPO_COMPOSICION, medicamento.composicion)
        cv.put(DataBaseMedicamento.CAMPO_USADOPARA, medicamento.usadoPara)
        cv.put(DataBaseMedicamento.CAMPO_FECHACADUCIDAD, medicamento.fechaCaducidad)
        cv.put(DataBaseMedicamento.CAMPO_NUMEROPASTILLAS, medicamento.numeroPastillas)
        cv.put(DataBaseMedicamento.CAMPO_PACIENTEID, medicamento.pacienteID)

        val whereClause = "${DataBaseMedicamento.CAMPO_GRAMOSAINGERIR} = ${medicamento.gramosAIngerir}"
        val resultado = dbWriteable.update(DataBaseMedicamento.TABLE_NAME, cv, whereClause, null)

        Log.i("database", "Si es -1 hubo error, sino exito: Respuesta: $resultado")

        dbWriteable.close()
    }

    fun deleteMedicamento(gramosAlIngerir: Float): Boolean {
        val dbWriteable = writableDatabase
        val whereClause = "${DataBaseMedicamento.CAMPO_GRAMOSAINGERIR} = $gramosAlIngerir"
        return dbWriteable.delete(DataBaseMedicamento.TABLE_NAME, whereClause, null) > 0
    }

    fun getMedicamentosList(idAutor: Int): ArrayList<Medicamento> {
        var lista = ArrayList<Medicamento>()
        val dbReadable = readableDatabase
        val query = "SELECT * FROM ${DataBaseMedicamento.TABLE_NAME}"
        val resultado = dbReadable.rawQuery(query, null)

        if (resultado.moveToFirst()) {
            do {
                val gramosAlIngerir = resultado.getString(0).toFloat()
                val nombre = resultado.getString(1)
                val Composicion = resultado.getString(2)
                val usadoPara = resultado.getString(3)
                val fechaCaducidad = resultado.getString(4)
                val numeroPastillas = resultado.getString(5).toInt()
                val pacienteID = resultado.getString(6).toInt()

                lista.add(Medicamento(gramosAlIngerir, nombre, Composicion, usadoPara, fechaCaducidad, numeroPastillas, pacienteID))
            } while (resultado.moveToNext())
        }

        resultado.close()
        dbReadable.close()

        return lista
    }

}
