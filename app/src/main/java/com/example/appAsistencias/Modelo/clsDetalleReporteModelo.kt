package com.example.appAsistencias.Modelo

import com.example.appAsistencias.Datos.dataEntradasSalidas
import com.example.appAsistencias.Datos.dataEstadisticas
import com.example.appAsistencias.network.RetrofitCliente

class clsDetalleReporteModelo {

    suspend fun obtenerEstadisticas(idTrabajador: Int, anio: Int, mes: Int): dataEstadisticas {
        return RetrofitCliente.apiService.obtenerEstadisticas(idTrabajador, anio, mes)
    }

    suspend fun obtenerEntradasSalidas(idTrabajador: Int, anio: Int, mes: Int): dataEntradasSalidas {
        return RetrofitCliente.apiService.obtenerEntradasSalidas(idTrabajador, anio, mes)
    }
}