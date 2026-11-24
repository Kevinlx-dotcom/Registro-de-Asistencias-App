package com.example.appAsistencias.Datos

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class dataResumenMes(
    @SerialName("asistencias") val asistencias: Int = 0,
    @SerialName("entradas") val entradas: Int = 0,
    @SerialName("salidas") val salidas: Int = 0,
    @SerialName("retardos") val retardos: Int = 0,
    @SerialName("faltas") val faltas: Int = 0,
    @SerialName("reportes") val reportes: Int = 0
)
