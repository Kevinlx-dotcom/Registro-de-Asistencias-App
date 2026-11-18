package com.example.appcursos.Modelo

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class dataPermisos(
    @SerialName(value = "id_permiso") val idpermiso: Int = 0,
    @SerialName(value = "id_trabajador") val idtrabajador: Int = 0,
    @SerialName(value = "fecha_inicio") val fechainicio: String = "",
    @SerialName(value = "fecha_fin") val fechafin: String = "",
    @SerialName(value = "motivo") val motivo: String = "",
    @SerialName(value = "estado") val estado: String = ""
)
