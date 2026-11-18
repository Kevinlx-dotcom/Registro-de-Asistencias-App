
package com.example.appcursos.Modelo

import kotlinx.serialization.Serializable

@Serializable
data class dataContacto(
    val nombre: String? = null,
    val cargo: String? = null,
    val telefono: String? = null,
    val correo: String? = null
)

@Serializable
data class dataContactosRespuesta(
    val exito: Boolean,
    val mensaje: String,
    val contactos: List<dataContacto> = emptyList(),
    val administracion: List<dataContacto> = emptyList(),
    val directivos: List<dataContacto> = emptyList()
)