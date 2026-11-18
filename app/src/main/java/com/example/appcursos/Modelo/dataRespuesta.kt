
package com.example.appcursos.Modelo

import kotlinx.serialization.Serializable

@Serializable
data class dataRespuesta<T>(
    val status: String,
    val message: String,
    val data: T
)
