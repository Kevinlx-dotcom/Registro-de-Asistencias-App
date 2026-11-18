package com.example.appcursos.Modelo

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class dataDescuentos(
    @SerialName(value = "id_descuento") val iddescuento: Int = 0,
    @SerialName(value = "id_trabajador") val idtrabajador: Int = 0,
    @SerialName(value = "id_incidencia") val idincidencia: Int = 0,
    @SerialName(value = "fecha") val fecha: String = "",
    @SerialName(value = "concepto") val concepto: String = "",
    @SerialName(value = "dias_descontados") val diasdescontados: Double = 0.0,
    @SerialName(value = "monto") val monto: Double = 0.0
)
