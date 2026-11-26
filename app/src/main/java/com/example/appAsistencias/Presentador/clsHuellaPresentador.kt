package com.example.appAsistencias.Presentador

import com.example.appAsistencias.Modelo.clsHuellaModelo
import com.example.appAsistencias.Vista.LoginActivity
import javax.crypto.Cipher

class clsHuellaPresentador(private val view: LoginActivity) {
    private val modelo = clsHuellaModelo(view)
    private val login = clsLoginPresentador(view)   // se reutiliza el login normal
    private var idTrabajador = 0
    private var entrandoConHuella = false

    // Después de un login correcto: ofrece activar la huella o entra directo
    fun despuesDelLogin(id: Int) {
        entrandoConHuella = false
        idTrabajador = id
        if (modelo.puedeUsarHuella() && !modelo.estaActivada()) {
            view.preguntarActivarHuella()
        } else {
            view.abrirInicio(idTrabajador)
        }
    }

    // El usuario dijo "Activar": pide la huella para cifrar la contraseña
    fun activarHuella() {
        try {
            view.pedirHuella(modelo.cipherParaCifrar(), true, "Confirma tu huella para activarla")
        } catch (e: Exception) {
            view.mostrarError("No se pudo activar la huella")
            view.abrirInicio(idTrabajador)
        }
    }

    // El usuario dijo "Ahora no"
    fun omitirHuella() {
        view.abrirInicio(idTrabajador)
    }

    // Botón "Entrar con huella digital"
    fun entrarConHuella() {
        if (!modelo.estaActivada()) {
            view.mostrarError("Primero inicia sesión con tu contraseña y activa la huella")
            return
        }
        try {
            view.pedirHuella(modelo.cipherParaDescifrar(), false, "Entra con tu huella")
        } catch (e: Exception) {
            // La llave se invalidó (cambiaron las huellas del teléfono): se borra todo
            modelo.desactivar()
            view.mostrarError("Cambió la huella del teléfono. Inicia sesión con tu contraseña y actívala de nuevo")
        }
    }

    // La huella fue correcta: o se guarda la contraseña, o se entra con ella
    fun huellaCorrecta(cipher: Cipher, paraActivar: Boolean, correo: String, clave: String) {
        try {
            if (paraActivar) {
                modelo.guardar(correo, clave, cipher)
                view.abrirInicio(idTrabajador)
            } else {
                entrandoConHuella = true
                login.realizarLogin(modelo.obtenerCorreo(), modelo.descifrarClave(cipher))
            }
        } catch (e: Exception) {
            view.mostrarError("No se pudo usar la huella, inicia sesión con tu contraseña")
        }
    }

    // El servidor rechazó el login: si venía de la huella, la contraseña guardada ya no sirve
    fun loginFallido(mensaje: String) {
        if (entrandoConHuella) {
            entrandoConHuella = false
            modelo.desactivar()
            view.mostrarError("Tu contraseña cambió. Inicia sesión con ella y activa la huella de nuevo")
        } else {
            view.mostrarError(mensaje)
        }
    }

    // El usuario canceló o la huella falló
    fun huellaError(mensaje: String, paraActivar: Boolean) {
        if (paraActivar) {
            view.abrirInicio(idTrabajador)
        } else {
            view.mostrarError(mensaje)
        }
    }
}
