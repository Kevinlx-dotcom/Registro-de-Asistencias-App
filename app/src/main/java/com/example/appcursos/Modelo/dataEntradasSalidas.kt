package com.example.appcursos.Modelo

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class dataEntradasSalidas(
    @SerialName(value = "id_asistencia") val idasistencia: Int = 0,
    @SerialName(value = "fecha") val fecha: String = "",
    @SerialName(value = "registro") val registro: String = "",
    @SerialName(value = "hora_entrada") val horaentrada: String = "",
    @SerialName(value = "hora_salida") val horasalida: String = "",
    @SerialName(value = "id_trabajador") val idtrabajador: Int = 0,
    @SerialName(value = "ruta_foto") val rutafoto: String = "",
    @SerialName(value = "estatus_registro") val estatusregistro: String = ""
)
