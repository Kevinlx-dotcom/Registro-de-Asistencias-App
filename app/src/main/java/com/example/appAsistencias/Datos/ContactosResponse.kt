package com.example.appAsistencias.Datos

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ContactosResponse(
    @SerialName("exito") val exito: Boolean = false,
    @SerialName("mensaje") val mensaje: String = "",
    @SerialName("contactos") val contactos: List<dataContacto> = emptyList(),
    @SerialName("administracion") val administracion: List<dataContacto> = emptyList(),
    @SerialName("directivos") val directivos: List<dataContacto> = emptyList()
)
