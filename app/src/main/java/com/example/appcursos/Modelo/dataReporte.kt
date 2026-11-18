
package com.example.appcursos.Modelo

import kotlinx.serialization.Serializable

@Serializable
data class dataReporte(
    val anio: Int,
    val mes: Int,
    val asistencias: Int,
    val retardos: Int,
    val faltas: Int,
    val generado: Boolean
)

@Serializable
data class dataReportesRespuesta(
    val exito: Boolean,
    val mensaje: String,
    val reportes: List<dataReporte> = emptyList()
)