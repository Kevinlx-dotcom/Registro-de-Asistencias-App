package com.example.appAsistencias.Modelo

import com.example.appAsistencias.Datos.dataEntradasSalidas
import com.example.appAsistencias.Datos.dataEstadisticas
import com.example.appAsistencias.network.RetrofitCliente

class clsDetalleReporteModelo {

    suspend fun obtenerEstadisticas(periodo: String): dataEstadisticas {
        return RetrofitCliente.apiService.obtenerEstadisticas(periodo)
    }

    suspend fun obtenerEntradasSalidas(periodo: String): List<dataEntradasSalidas> {
        return RetrofitCliente.apiService.obtenerEntradasSalidas(periodo)
    }
}
