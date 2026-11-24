package com.example.appAsistencias.Presentador

import com.example.appAsistencias.Modelo.clsDetalleReporteModelo
import com.example.appAsistencias.Vista.DetalleReporteActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class clsDetalleReportePresentador(private val vista: DetalleReporteActivity) {

    private val detalleReporteModelo = clsDetalleReporteModelo()

    fun obtenerDetalleReporte(idTrabajador: Int, anio: Int, mes: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val estadisticas = detalleReporteModelo.obtenerEstadisticas(idTrabajador, anio, mes)
                val entradasSalidas = detalleReporteModelo.obtenerEntradasSalidas(idTrabajador, anio, mes)
                withContext(Dispatchers.Main) {
                    if (!estadisticas.exito) {
                        vista.mostrarError(estadisticas.mensaje)
                    } else if (!entradasSalidas.exito) {
                        vista.mostrarError(entradasSalidas.mensaje)
                    } else {
                        vista.mostrarDetalleReporte(estadisticas, entradasSalidas)
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
                withContext(Dispatchers.Main) {
                    vista.mostrarError("No se pudo cargar el detalle del reporte")
                }
            }
        }
    }
}