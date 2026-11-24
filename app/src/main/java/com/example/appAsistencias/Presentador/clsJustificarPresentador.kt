package com.example.appAsistencias.Presentador

import com.example.appAsistencias.Modelo.clsJustificarModelo
import com.example.appAsistencias.Vista.JustificarActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class clsJustificarPresentador(private val vista: JustificarActivity) {

    private val justificarModelo = clsJustificarModelo()

    fun obtenerPendientes(idTrabajador: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val respuesta = justificarModelo.obtenerPendientes(idTrabajador)
                withContext(Dispatchers.Main) {
                    if (respuesta.exito) {
                        val primero = respuesta.pendientes.firstOrNull()
                        if (primero != null) {
                            vista.mostrarPendiente(
                                "${primero.tipo} · ${primero.fecha}",
                                "Entrada ${primero.entrada ?: "--:--"}"
                            )
                            // Cargar los documentos de este tipo de incidencia (Retardo o Falta)
                            obtenerDocumentosJustificar(primero.tipo)
                        }
                    } else {
                        vista.mostrarError(respuesta.mensaje)
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
                withContext(Dispatchers.Main) {
                    vista.mostrarError("No se pudieron cargar los pendientes")
                }
            }
        }
    }

    private fun obtenerDocumentosJustificar(tipoIncidencia: String) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val respuesta = justificarModelo.obtenerDocumentosJustificar(tipoIncidencia)
                withContext(Dispatchers.Main) {
                    if (respuesta.exito) {
                        val lista = respuesta.documentos
                        val doc1 = lista.getOrNull(0)?.let { "${it.nombre}\n${it.descripcion ?: ""}" } ?: ""
                        val doc2 = lista.getOrNull(1)?.let { "${it.nombre}\n${it.descripcion ?: ""}" } ?: ""
                        val doc3 = lista.getOrNull(2)?.let { "${it.nombre}\n${it.descripcion ?: ""}" } ?: ""
                        vista.mostrarDocumentos(doc1, doc2, doc3)
                    } else {
                        vista.mostrarError(respuesta.mensaje)
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
                withContext(Dispatchers.Main) {
                    vista.mostrarError("No se pudieron cargar los documentos")
                }
            }
        }
    }
}