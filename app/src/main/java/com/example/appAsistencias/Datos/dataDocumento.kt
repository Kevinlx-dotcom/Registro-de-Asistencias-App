package com.example.appAsistencias.Datos

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class dataDocumento(
    @SerialName("id_documento_requerido") var idDocumentoRequerido: Int = 0,
    @SerialName("tipo_incidencia") var tipoIncidencia: String? = null,
    @SerialName("nombre") var nombre: String? = null,
    @SerialName("descripcion") var descripcion: String? = null,
    @SerialName("obligatorio") var obligatorio: Boolean = false
)
