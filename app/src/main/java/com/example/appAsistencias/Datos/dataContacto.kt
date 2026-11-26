package com.example.appAsistencias.Datos

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class dataContacto(
    @SerialName("nombre") val nombre: String = "",
    @SerialName("cargo") val cargo: String? = null,
    @SerialName("telefono") val telefono: String? = null,
    @SerialName("correo") val correo: String? = null
)
