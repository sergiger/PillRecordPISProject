package com.example.afontgou17alumnes.mypillrecord.data.model

class Contacte {
    var username:String
        get() =field
        set(value) {field=value}
    var tipus:String
        get() =field
        set(value) {field=value}
                    //Hi haurà 4 tipus: Metge, Cuidador, Familiar, Pacient
                    //METGE: podrà veure tot i accedir a afegir reminders i terapies
                    //Cuidador: podrà veure tot i afegir reminders
                    //Faminilar: podrà veure tot.
                    //Pacient: No podrà fer res, simplement serà un contacte més

                    //Per exemple, si jo soc metge de l'alex, jo tindria a l'alex com a pacient
                    //però ell em tindria com a doctor,
                    //Si jo tinc a l'alex com a pacient, ell em podria tenir com a metge, cuidador, o familiar, donant-me cert accés

    constructor(username:String,tipus:String){
        this.username=username
        this.tipus=tipus
    }

    fun toStirng():String{
        return username.toString()+": "+ tipus.toString()
    }

}
