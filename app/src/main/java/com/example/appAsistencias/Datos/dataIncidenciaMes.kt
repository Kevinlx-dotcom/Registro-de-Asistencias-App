package com.example.appAsistencias.Datos

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class dataIncidenciaMes(
    @SerialName("tipo") val tipo: String = "",
    @SerialName("fecha") val fecha: String = "",
    @SerialName("entrada") val entrada: String = "",
    @SerialName("justificada") val justificada: Boolean = false,
    @SerialName("justificacion") val justificacion: String = ""
)
