package com.example.afontgou17alumnes.mypillrecord.data.model

class Agenda {
    var contactes=ArrayList<Contacte>()

    constructor()//Constructor vuit
    constructor(contactes:ArrayList<Contacte>){
        for(contacte in contactes)
            this.contactes.add(contacte)
    } //constructor copia

    fun addContact(contacte:Contacte){
        contactes.add(contacte)
        refreshAgenda()
        notifyContacte()
    }
    fun createContacte(username:String,type:String):Contacte{
        return Contacte(username,type)
    }
    fun deleteContacte(username: String){
        for(index in contactes.indices){
            if(contactes[index].username.equals(username))
                contactes.drop(index)
        }
        refreshAgenda()
    }
    fun changeAccesibilityContact(username: String,type: String){
        for(index in contactes.indices){
            if(contactes[index].username.equals(username))
                contactes[index].tipus=type
        }
        refreshAgenda()
    }

    fun validateAcces(username: String,type: String):Boolean{
        var retorn=false
        var indexMevaAccessivilitat=0
        var indexSevaAccessivilitat=1
        var accessivilitats= arrayListOf<String>("Doctor","Care Taker","Family","Pacient")
        for(contacte in contactes){
            if(contacte.username.equals(username))
                for(index in accessivilitats.indices){
                    if(contacte.tipus.equals(accessivilitats[index]))
                        indexMevaAccessivilitat=index
                    if(type.equals(accessivilitats[index]))
                        indexSevaAccessivilitat=index
                    break
                }
        }
        if(indexMevaAccessivilitat>=indexSevaAccessivilitat)//Si li estic donant una accessiviliat més alta o igual a la que demana, li concedeixo
            retorn=true
        return retorn
    } //Retorna accés si li estàs donant igual o més accessivilitat de la que demana

    fun refreshAgenda(){
        TODO()//Cal fer un observer per a que la llista estigui actualitzada a tot canvi que es faci
    }
    fun notifyContacte(){
        TODO()//Cal fer que el contacte revi la invitació per correu o algo així i pugui acceptar
        //O denegar el contacte

        //Potser millor ara per ara podriem fer que quan afegeixes només ho fas a la teva agenda
        //El contacte podrà decidir si afegirte o no sense cap notificació, haurà d'afegirte, exemple:
        //Jo poso al Àlex com a metge meu i ell no rebrà res. Ell si vol, pot afegir-me a la seva
        //agenda com a pacient i llavors quan vulgui rear terapies o veure els meus reminders, podrà
        // perquè té accés ja que està com a metge a la meva agenda, si jo el tingués com a pacient,
        // ell no podria accedir a res meu i jo no podria accedir a res seu.

        //serà molt més fàcil d'implementar, per tant aquesta funció careix de sentit, de moment
        // la deixaré per a recordar la funcionalitat.
    }

    override fun toString(): String {
        return "Agenda(contactes=$contactes)"
    }


}