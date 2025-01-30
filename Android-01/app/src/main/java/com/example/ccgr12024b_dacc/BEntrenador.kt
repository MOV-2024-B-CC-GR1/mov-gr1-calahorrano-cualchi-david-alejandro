package com.example.ccgr12024b_dacc

import android.os.Parcel
import android.os.Parcelable

class BEntrenador (
    var id: Int,
    var nombre: String,
    var descripcion: String?
):Parcelable {
    // Constructor para leer desde Parcel
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString()?: "", // Evita el uso de `!!`, previene crashes si el valor es null
        parcel.readString()
    ) {
    }

    override fun toString(): String {
        return "$nombre ${descripcion ?: "Sin descripción"}"
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(nombre)
        parcel.writeString(descripcion)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<BEntrenador> {
        override fun createFromParcel(parcel: Parcel): BEntrenador {
            return BEntrenador(parcel)
        }

        override fun newArray(size: Int): Array<BEntrenador?> {
            return arrayOfNulls(size)
        }
    }
}