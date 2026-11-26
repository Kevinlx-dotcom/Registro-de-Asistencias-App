package com.example.appAsistencias.Presentador

import com.example.appAsistencias.Modelo.clsInicioModelo
import com.example.appAsistencias.Modelo.clsSesionModelo
import com.example.appAsistencias.Vista.InicioActivity
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class clsInicioPresentador(private val view: InicioActivity) {
    private val modelo = clsInicioModelo()
    private val sesion = clsSesionModelo(view)

    fun mostrarFechaYMes() {
        val hoy = LocalDate.now()
        val espanol = Locale.forLanguageTag("es-MX")
        val fecha = hoy.format(DateTimeFormatter.ofPattern("d 'de' MMMM 'de' yyyy", espanol))
        val mes = hoy.format(DateTimeFormatter.ofPattern("MMMM yyyy", espanol))
            .replaceFirstChar { it.uppercase() }
        view.mostrarFecha(fecha, "Entradas/Salidas · $mes", "Retardos · $mes")
    }

    // Decide qué hacer con la opción que se eligió en el menú de los 3 puntos
    fun elegirOpcionMenu(opcion: String) {
        if (opcion == "Cerrar sesión") {
            sesion.cerrar()
            view.abrirLogin()
        } else {
            view.abrirReportes()
        }
    }

    fun cargarInicio(idTrabajador: Int) {
        CoroutineScope(Dispatchers.Main).launch {
            try {
                val respuesta = withContext(Dispatchers.IO) {
                    modelo.obtenerInicio(idTrabajador)
                }
                if (!respuesta.exito) {
                    view.mostrarError(respuesta.mensaje)
                    return@launch
                }
                view.mostrarBienvenida("Bienvenido(a), " + respuesta.usuario.nombrecompleto)

                val cantidad = respuesta.incidenciaspendientes
                if (cantidad == 0) {
                    view.ocultarAviso()
                } else if (cantidad == 1) {
                    view.mostrarAviso("1 incidencia pendiente\npor justificar")
                } else {
                    view.mostrarAviso("$cantidad incidencias pendientes\npor justificar")
                }

                for (checada in respuesta.ultimaschecadas) {
                    val hora = checada.registro.take(5) + " h"
                    if (checada.tiporegistro == "entrada") {
                        view.mostrarEntrada(hora, checada.estatus, checada.estatus == "Puntual")
                    } else {
                        view.mostrarSalida(hora)
                    }
                }
            } catch (e: Exception) {
                view.mostrarError("Error de conexion:${e.localizedMessage ?: "Error desconocido"}")
            }
        }
    }
}
