package com.example.appAsistencias.Datos

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class dataIncidencias(
    @SerialName(value = "id_incidencia") val idincidencia: Int = 0,
    @SerialName(value = "justificacion") val justificacion: String = "",
    @SerialName(value = "fecha") val fecha: String = "",
    @SerialName(value = "id_trabajador") val idtrabajador: Int = 0,
    @SerialName(value = "id_asistencia") val idasistencia: Int = 0,
    @SerialName(value = "id_tipo_incidencia") val idtipoincidencia: Int = 0,
    @SerialName(value = "nombre_tipo") val nombretipo: String = "",
    @SerialName(value = "estatus") val estatus: String = ""
)
