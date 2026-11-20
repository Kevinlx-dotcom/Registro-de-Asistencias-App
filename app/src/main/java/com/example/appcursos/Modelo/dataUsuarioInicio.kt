package com.example.appcursos.Modelo

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class dataUsuarioInicio(
    @SerialName("nombreCompleto") val nombrecompleto: String = ""
)
