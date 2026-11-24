package com.example.appAsistencias.Presentador

import com.example.appAsistencias.Modelo.clsDetalleReporteModelo
import com.example.appAsistencias.Vista.DetalleReporteActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class clsDetalleReportePresentador(private val vista: DetalleReporteActivity) {

    private val detalleReporteModelo = clsDetalleReporteModelo()

    fun obtenerDetalleReporte(periodo: String) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val estadisticas = detalleReporteModelo.obtenerEstadisticas(periodo)
                val entradasSalidas = detalleReporteModelo.obtenerEntradasSalidas(periodo)
                withContext(Dispatchers.Main) {
                    vista.mostrarDetalleReporte(estadisticas, entradasSalidas)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
