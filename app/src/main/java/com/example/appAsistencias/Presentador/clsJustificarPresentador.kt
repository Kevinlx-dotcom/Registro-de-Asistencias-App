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
                val listaPendiente = justificarModelo.obtenerPendientes(idTrabajador)
                withContext(Dispatchers.Main) {
                    vista.mostrarPendientes(listaPendiente)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun obtenerDocumentosJustificar(tipoIncidencia: String) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val listaDocumentos = justificarModelo.obtenerDocumentosJustificar(tipoIncidencia)
                withContext(Dispatchers.Main) {
                    vista.mostrarDocumentos(listaDocumentos)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
