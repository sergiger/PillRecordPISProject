package com.example.afontgou17alumnes.mypillrecord.data.model

import android.os.Parcel
import android.os.Parcelable

class Frequency :Parcelable {


    constructor(parcel: Parcel){
        //aqui has de separar totes les parts del parel i posarles a cada variable this.
    }

    //Aqui posariem els paràmetres
    constructor(frequency: Frequency){
        //Aquest és el constructor copia
    }
    constructor(){//haurem de tenir varis constructors, un per a cada tipus de frequencia
        //Aquest és el constructor vuit i encara podriem fer un altre constructor amb tota la informació
    }

    override fun writeToParcel(dest: Parcel?, flags: Int) {
        dest?.let {
            //aqui es montaria el parcel i es posaria tot dintre del dest de la seguent manera
            //dest.writeSerializable(this.startDate) as LocalDate
        }
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun toString(): String {
        return "Frequency()"
    }

    companion object CREATOR : Parcelable.Creator<Frequency> {
        override fun createFromParcel(parcel: Parcel): Frequency {
            return Frequency(parcel)
        }

        override fun newArray(size: Int): Array<Frequency?> {
            return arrayOfNulls(size)
        }
    }
}