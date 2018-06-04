package com.example.frani.examen1moviles

import android.os.Parcel
import android.os.Parcelable

class Medicamento(var gramosAIngerir: Float, var nombre: String, var composicion: String, var usadoPara: String, var fechaCaducidad: String, var numeroPastillas: Int, var pacienteID: Int) : Parcelable {

    constructor(parcel: Parcel) : this(
            parcel.readFloat(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readInt(),
            parcel.readInt()) {
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(destino: Parcel?, p1: Int) {
        destino?.writeFloat(gramosAIngerir)
        destino?.writeString(nombre)
        destino?.writeString(composicion)
        destino?.writeString(usadoPara)
        destino?.writeString(fechaCaducidad)
        destino?.writeInt(numeroPastillas)
        destino?.writeInt(pacienteID)
    }

    companion object CREATOR : Parcelable.Creator<Medicamento> {
        override fun createFromParcel(parcel: Parcel): Medicamento {
            return Medicamento(parcel)
        }

        override fun newArray(size: Int): Array<Medicamento?> {
            return arrayOfNulls(size)
        }
    }

}