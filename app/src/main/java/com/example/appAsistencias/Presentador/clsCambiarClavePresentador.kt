package com.example.appAsistencias.Presentador

import com.example.appAsistencias.Modelo.clsRecuperarModelo
import com.example.appAsistencias.Vista.CambiarClaveActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class clsCambiarClavePresentador(private val view: CambiarClaveActivity) {
    private val modelo = clsRecuperarModelo()

    fun cambiarClave(correo: String, codigo: String, clave: String, confirmar: String) {
        if (codigo.isEmpty() || clave.isEmpty() || confirmar.isEmpty()) {
            view.mostrarError("Por favor, completa todos los campos")
            return
        }
        if (clave.length < 8) {
            view.mostrarError("La contraseña debe tener al menos 8 caracteres")
            return
        }
        if (clave != confirmar) {
            view.mostrarError("Las contraseñas no coinciden")
            return
        }

        CoroutineScope(Dispatchers.Main).launch {
            try {
                val respuesta = withContext(Dispatchers.IO) {
                    modelo.cambiarClave(correo, codigo, clave)
                }
                if (respuesta.exito) {
                    view.claveCambiada(respuesta.mensaje)
                } else {
                    view.mostrarError(respuesta.mensaje)
                }
            } catch (e: Exception) {
                view.mostrarError("Error de conexion:${e.localizedMessage ?: "Error desconocido"}")
            }
        }
    }
}
