package com.example.appAsistencias.Datos

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LoginResponse(
    @SerialName("exito") val exito: Boolean = false,
    @SerialName("mensaje") val mensaje: String = "",
    @SerialName("idUsuario") val idUsuario: Int = 0,
    @SerialName("idTrabajador") val idTrabajador: Int = 0,
    @SerialName("nombreUsuario") val nombreUsuario: String = "",
    @SerialName("nombreCompleto") val nombreCompleto: String = "",
    @SerialName("perfil") val perfil: String = ""
)
