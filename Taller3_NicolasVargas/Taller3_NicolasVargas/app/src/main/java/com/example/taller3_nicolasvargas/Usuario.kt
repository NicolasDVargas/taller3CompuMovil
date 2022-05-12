package com.example.taller3_nicolasvargas

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class Usuario (
    var nombre: String?=null,
    var edad: String?=null,
    var email: String?=null,
    var apellido: String?=null,
    var contra: String?=null,
    var numero : String?=null,
    var imageUri: String?=null,
    var disponible: Boolean?=null,
    var lon : Double?=null,
    var lat:Double?=null){}