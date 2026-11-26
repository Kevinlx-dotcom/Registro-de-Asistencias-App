package com.example.appAsistencias.Datos

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class dataReporte(
    @SerialName("anio") val anio: Int = 0,
    @SerialName("mes") val mes: Int = 0,
    @SerialName("asistencias") val asistencias: Int = 0,
    @SerialName("retardos") val retardos: Int = 0,
    @SerialName("faltas") val faltas: Int = 0,
    @SerialName("generado") val generado: Boolean = false
)
