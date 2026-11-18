package com.example.appcursos.Modelo

class dataDocumento {
    @SerializedName("nombre")
    val nombre: String,

    @SerializedName("descripcion")
    val descripcion: String?,

    @SerializedName("obligatorio")
    val obligatorio: Boolean
}