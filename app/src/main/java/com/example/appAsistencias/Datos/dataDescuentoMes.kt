package com.example.appAsistencias.Datos

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class dataDescuentoMes(
    @SerialName("fecha") val fecha: String = "",
    @SerialName("concepto") val concepto: String = "",
    @SerialName("dias_descontados") val diasdescontados: String = "",
    @SerialName("monto") val monto: String = ""
)
