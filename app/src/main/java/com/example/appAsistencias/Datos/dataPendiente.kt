package com.example.appAsistencias.Datos

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class dataPendiente(
    @SerialName("tipo") val tipo: String = "",
    @SerialName("fecha") val fecha: String = "",
    @SerialName("entrada") val entrada: String? = null,
    @SerialName("dias_restantes") val diasRestantes: Int? = null,
    @SerialName("vencida") val vencida: Boolean = false
)
