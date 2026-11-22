package com.example.appAsistencias.Presentador

import com.example.appAsistencias.Modelo.clsRecuperarModelo
import com.example.appAsistencias.Vista.RecuperarActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class clsRecuperarPresentador(private val view: RecuperarActivity) {
    private val modelo = clsRecuperarModelo()

    fun enviarCodigo(correo: String) {
        if (correo.isEmpty()) {
            view.mostrarError("Por favor, ingresa tu correo")
            return
        }

        CoroutineScope(Dispatchers.Main).launch {
            try {
                val respuesta = withContext(Dispatchers.IO) {
                    modelo.enviarCodigo(correo)
                }
                if (respuesta.exito) {
                    view.codigoEnviado(respuesta.mensaje)
                } else {
                    view.mostrarError(respuesta.mensaje)
                }
            } catch (e: Exception) {
                view.mostrarError("Error de conexion:${e.localizedMessage ?: "Error desconocido"}")
            }
        }
    }
}
