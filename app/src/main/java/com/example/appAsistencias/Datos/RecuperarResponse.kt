package com.example.appAsistencias.Datos

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RecuperarResponse(
    @SerialName("exito") val exito: Boolean = false,
    @SerialName("mensaje") val mensaje: String = ""
)
