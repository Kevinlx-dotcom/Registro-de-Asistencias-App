package com.example.appcursos.Modelo

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class dataChecada(
    @SerialName("fecha") val fecha: String = "",
    @SerialName("registro") val registro: String = "",
    @SerialName("tipo_registro") val tiporegistro: String = "",
    @SerialName("estatus") val estatus: String = ""
)
