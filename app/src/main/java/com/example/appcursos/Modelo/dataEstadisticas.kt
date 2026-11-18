package com.example.appcursos.Modelo

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class dataEstadisticas(
    @SerialName(value = "asistencias") val asistencias: Int = 0,
    @SerialName(value = "retardos") val retardos: Int = 0,
    @SerialName(value = "faltas") val faltas: Int = 0,
    @SerialName(value = "descuentos") val descuentos: Int = 0,
    @SerialName(value = "total_permisos") val totalpermisos: Int = 0,
    @SerialName(value = "periodo") val periodo: String = "",
    @SerialName(value = "resumen_texto") val resumentexto: String = ""
)
