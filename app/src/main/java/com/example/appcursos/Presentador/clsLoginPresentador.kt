package com.example.appcursos.Presentador

import com.example.appcursos.Modelo.clsLoginModelo
import com.example.appcursos.Vista.LoginActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class clsLoginPresentador(private val view: LoginActivity) {
    private val modelo = clsLoginModelo()

    fun realizarLogin(correo: String, clave: String) {
        if (correo.isEmpty() || clave.isEmpty()) {
            view.mostrarError("Por favor, ingresa correo y contraseña")
            return
        }

        CoroutineScope(Dispatchers.Main).launch {
            try {
                val respuesta = withContext(Dispatchers.IO) {
                    modelo.autenticarUsuario(correo, clave)
                }
                if (respuesta.exito) {
                    view.loginExitoso(respuesta.mensaje, respuesta.idTrabajador)
                } else {
                    view.mostrarError(respuesta.mensaje)
                }
            } catch (e: Exception) {
                view.mostrarError("Error de conexion:${e.localizedMessage ?: "Error desconocido"}")
            }
        }
    }
}
