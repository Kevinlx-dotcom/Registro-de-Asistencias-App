package com.example.appAsistencias.Datos

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RecuperarRequest(
    @SerialName("correo") val correo: String = ""
)
