package com.example.appAsistencias.Datos

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class dataPendienteRespuesta(
    @SerialName("exito") val exito: Boolean = false,
    @SerialName("mensaje") val mensaje: String = "",
    @SerialName("total") val total: Int = 0,
    @SerialName("pendientes") val pendientes: List<dataPendiente> = emptyList()
)