package com.example.appAsistencias.Presentador

import com.example.appAsistencias.Modelo.clsJustificarModelo
import com.example.appAsistencias.Vista.JustificarActivity
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class clsJustificarPresentador(private val vista: JustificarActivity) {

    private val justificarModelo = clsJustificarModelo()
    private val espanol = Locale.forLanguageTag("es-MX")

    fun obtenerPendientes(idTrabajador: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val respuesta = justificarModelo.obtenerPendientes(idTrabajador)
                withContext(Dispatchers.Main) {
                    if (respuesta.exito) {
                        val primero = respuesta.pendientes.firstOrNull()
                        val total = if (respuesta.total > 0) respuesta.total else respuesta.pendientes.size

                        if (primero != null) {
                            val tipoFormateado = primero.tipo.replaceFirstChar { it.uppercase() }
                            val fechaFormateada = formatearFecha(primero.fecha)
                            val horaFormateada = "Entrada ${primero.entrada?.take(5) ?: "--:--"} h"

                            vista.mostrarPendiente(
                                total,
                                "$tipoFormateado · $fechaFormateada",
                                horaFormateada,
                                primero.tipo
                            )
                            obtenerDocumentosJustificar(primero.tipo)
                        } else {
                            vista.mostrarPendienteVacio(total)
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

    private fun formatearFecha(fechaStr: String): String {
        return try {
            val date = LocalDate.parse(fechaStr.take(10))
            date.format(DateTimeFormatter.ofPattern("d 'de' MMMM", espanol))
        } catch (e: Exception) {
            fechaStr
        }
    }
}
