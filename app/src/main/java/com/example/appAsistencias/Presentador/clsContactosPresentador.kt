package com.example.appAsistencias.Presentador

import com.example.appAsistencias.Modelo.clsContactosModelo
import com.example.appAsistencias.Vista.ContactosActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class clsContactosPresentador(private val view: ContactosActivity) {
    private val modelo = clsContactosModelo()

    fun cargarContactos() {
        CoroutineScope(Dispatchers.Main).launch {
            try {
                val respuesta = withContext(Dispatchers.IO) {
                    modelo.obtenerContactos()
                }
                if (!respuesta.isExito) {
                    view.mostrarError("No se pudieron cargar los contactos")
                    return@launch
                }
                view.mostrarContactos(respuesta)
            } catch (e: Exception) {
                view.mostrarError("Error de conexion:${e.localizedMessage ?: "Error desconocido"}")
            }
        }
    }
}
