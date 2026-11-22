package com.example.appAsistencias.Datos

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CambiarClaveRequest(
    @SerialName("correo") val correo: String = "",
    @SerialName("codigo") val codigo: String = "",
    @SerialName("nuevaClave") val nuevaClave: String = ""
)
