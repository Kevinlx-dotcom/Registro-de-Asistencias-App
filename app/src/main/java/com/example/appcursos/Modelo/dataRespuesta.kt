package com.example.appcursos.Modelo

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class dataRespuesta<T>(
    @SerialName(value = "estado") val estado: Boolean = false,
    @SerialName(value = "mensaje") val mensaje: String = "",
    @SerialName(value = "datos") val datos: T? = null
)
