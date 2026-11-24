package com.example.appAsistencias.Datos

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class dataDiaEntradaSalida(
    @SerialName("fecha") val fecha: String = "",
    @SerialName("entrada") val entrada: String = "",
    @SerialName("salida") val salida: String = "",
    @SerialName("estatus") val estatus: String = "",
    @SerialName("pendiente_justificar") val pendientejustificar: Boolean = false
)
