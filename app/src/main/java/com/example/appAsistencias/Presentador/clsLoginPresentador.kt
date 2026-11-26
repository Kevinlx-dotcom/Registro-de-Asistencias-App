package com.example.appAsistencias.Presentador

import com.example.appAsistencias.Modelo.clsLoginModelo
import com.example.appAsistencias.Modelo.clsSesionModelo
import com.example.appAsistencias.Vista.LoginActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class clsLoginPresentador(private val view: LoginActivity) {
    private val modelo = clsLoginModelo()
    private val sesion = clsSesionModelo(view)

    // Al abrir la app: si ya hay sesión guardada, entra directo a Inicio
    fun revisarSesion() {
        val id = sesion.obtenerId()
        if (id != 0) {
            view.abrirInicio(id)
        }
    }

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
                    sesion.guardarId(respuesta.idTrabajador)
                    view.loginExitoso(respuesta.mensaje, respuesta.idTrabajador)
                } else {
                    view.loginFallido(respuesta.mensaje)
                }
            } catch (e: Exception) {
                view.mostrarError("Error de conexion:${e.localizedMessage ?: "Error desconocido"}")
            }
        }
    }
}
