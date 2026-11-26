package com.example.appAsistencias.Presentador

import com.example.appAsistencias.Modelo.clsReportesModelo
import com.example.appAsistencias.Vista.ReportesActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class clsReportesPresentador(private val view: ReportesActivity) {
    private val modelo = clsReportesModelo()

    fun cargarReportes(idTrabajador: Int) {
        CoroutineScope(Dispatchers.Main).launch {
            try {
                val respuesta = withContext(Dispatchers.IO) {
                    modelo.obtenerReportes(idTrabajador)
                }
                if (respuesta.exito) {
                    view.mostrarReportes(respuesta)
                } else {
                    view.mostrarError("No se pudieron cargar los reportes")
                }
            } catch (e: Exception) {
                view.mostrarError("Error de conexión")
            }
        }
    }
}
