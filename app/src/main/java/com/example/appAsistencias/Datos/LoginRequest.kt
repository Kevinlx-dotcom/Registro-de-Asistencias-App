package com.example.appAsistencias.Datos

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LoginRequest(
    @SerialName("correo") val correo: String = "",
    @SerialName("passusuario") val passusuario: String = ""
)
