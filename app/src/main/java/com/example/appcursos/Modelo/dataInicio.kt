package com.example.appcursos.Modelo

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class dataInicio(
    @SerialName("exito") val exito: Boolean = false,
    @SerialName("mensaje") val mensaje: String = "",
    @SerialName("usuario") val usuario: dataUsuarioInicio = dataUsuarioInicio(),
    @SerialName("ultimas_checadas") val ultimaschecadas: List<dataChecada> = emptyList(),
    @SerialName("incidencias_pendientes") val incidenciaspendientes: Int = 0
)
