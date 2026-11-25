package com.example.appAsistencias.Presentador

import com.example.appAsistencias.Datos.dataFilaAsistencia
import com.example.appAsistencias.Modelo.clsAsistenciasModelo
import com.example.appAsistencias.Vista.AsistenciasActivity
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.Locale
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class clsAsistenciasPresentador(private val view: AsistenciasActivity) {
    private val modelo = clsAsistenciasModelo()
    private val espanol = Locale.forLanguageTag("es-MX")
    private var mes = YearMonth.now()

    // 0 = Entradas/Salidas, 1 = Retardos, 2 = Faltas, 3 = Descuentos
    private var pestana = 0

    fun getAnio(): Int = mes.year
    fun getMes(): Int = mes.monthValue

    fun cambiarMes(cantidad: Long, idTrabajador: Int) {
        mes = mes.plusMonths(cantidad)
        cargarAsistencias(idTrabajador)
    }

    fun cambiarPestana(numero: Int, idTrabajador: Int) {
        pestana = numero
        view.marcarPestana(numero)
        cargarAsistencias(idTrabajador)
    }

    fun cargarAsistencias(idTrabajador: Int) {
        val titulo = mes.format(DateTimeFormatter.ofPattern("MMMM yyyy", espanol))
            .replaceFirstChar { it.uppercase() }
        view.mostrarMes(titulo)

        CoroutineScope(Dispatchers.Main).launch {
            try {
                val anio = mes.year
                val numeroMes = mes.monthValue

                val respuesta = withContext(Dispatchers.IO) {
                    modelo.obtenerEstadisticas(idTrabajador, anio, numeroMes)
                }
                if (!respuesta.exito) {
                    view.mostrarError(respuesta.mensaje)
                    return@launch
                }
                val numeros = respuesta.estadisticas
                view.mostrarResumen(
                    "${numeros.asistencias} asistencias · ${numeros.retardos} retardos · ${numeros.faltas} faltas"
                )

                val filas = withContext(Dispatchers.IO) {
                    armarFilas(idTrabajador, anio, numeroMes)
                }
                view.mostrarLista(filas)
                if (filas.isEmpty()) {
                    view.mostrarError("No hay registros en este mes")
                }
            } catch (e: Exception) {
                view.mostrarError("Error de conexion:${e.localizedMessage ?: "Error desconocido"}")
            }
        }
    }

    private suspend fun armarFilas(idTrabajador: Int, anio: Int, numeroMes: Int): List<dataFilaAsistencia> {
        val filas = ArrayList<dataFilaAsistencia>()

        if (pestana == 0) {
            val respuesta = modelo.obtenerEntradasSalidas(idTrabajador, anio, numeroMes)
            for (dia in respuesta.dias) {
                val entrada = if (dia.entrada.isEmpty()) "Entrada sin registro"
                else "Entrada " + dia.entrada.take(5) + " h"
                val salida = if (dia.salida.isEmpty()) "Salida sin registro"
                else "Salida " + dia.salida.take(5) + " h"
                val puntual = dia.estatus == "a_tiempo"
                filas.add(
                    dataFilaAsistencia(
                        diaCorto(dia.fecha), mesCorto(dia.fecha),
                        entrada, salida, textoEstatus(dia.estatus), puntual, dia.pendientejustificar
                    )
                )
            }
        } else if (pestana == 1 || pestana == 2) {
            val tipo = if (pestana == 1) "retardo" else "falta"
            val respuesta = modelo.obtenerIncidencias(idTrabajador, anio, numeroMes, tipo)
            for (incidencia in respuesta.incidencias) {
                val entrada = if (incidencia.entrada.isEmpty()) "Sin registro de entrada"
                else "Entrada " + incidencia.entrada.take(5) + " h"
                filas.add(
                    dataFilaAsistencia(
                        diaCorto(incidencia.fecha), mesCorto(incidencia.fecha),
                        entrada,
                        if (incidencia.justificada) incidencia.justificacion else "Sin justificar",
                        if (incidencia.justificada) "Justificada" else incidencia.tipo,
                        incidencia.justificada, !incidencia.justificada
                    )
                )
            }
        } else {
            val respuesta = modelo.obtenerDescuentos(idTrabajador, anio, numeroMes)
            for (descuento in respuesta.descuentos) {
                filas.add(
                    dataFilaAsistencia(
                        diaCorto(descuento.fecha), mesCorto(descuento.fecha),
                        descuento.concepto,
                        "Días descontados: ${descuento.diasdescontados}",
                        "$" + descuento.monto, false, false
                    )
                )
            }
        }
        return filas
    }

    private fun textoEstatus(estatus: String): String {
        if (estatus == "a_tiempo") return "A tiempo"
        if (estatus == "retardo") return "Retardo"
        if (estatus == "falta") return "Falta"
        return "Sin horario"
    }

    private fun diaCorto(fecha: String): String {
        val dia = LocalDate.parse(fecha.take(10))
        val nombre = dia.dayOfWeek.getDisplayName(TextStyle.SHORT, espanol)
            .removeSuffix(".").replaceFirstChar { it.uppercase() }
        return nombre + " " + dia.dayOfMonth
    }

    private fun mesCorto(fecha: String): String {
        val dia = LocalDate.parse(fecha.take(10))
        return dia.month.getDisplayName(TextStyle.SHORT, espanol).removeSuffix(".")
    }
}
