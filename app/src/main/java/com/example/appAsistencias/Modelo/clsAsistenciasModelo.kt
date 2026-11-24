package com.example.appAsistencias.Modelo

import com.example.appAsistencias.Datos.dataDescuentos
import com.example.appAsistencias.Datos.dataEntradasSalidas
import com.example.appAsistencias.Datos.dataEstadisticas
import com.example.appAsistencias.Datos.dataIncidencias
import com.example.appAsistencias.network.RetrofitCliente

class clsAsistenciasModelo {
    suspend fun obtenerEstadisticas(idTrabajador: Int, anio: Int, mes: Int): dataEstadisticas {
        return RetrofitCliente.apiService.obtenerEstadisticas(idTrabajador, anio, mes)
    }

    suspend fun obtenerEntradasSalidas(idTrabajador: Int, anio: Int, mes: Int): dataEntradasSalidas {
        return RetrofitCliente.apiService.obtenerEntradasSalidas(idTrabajador, anio, mes)
    }

    suspend fun obtenerIncidencias(idTrabajador: Int, anio: Int, mes: Int, tipo: String): dataIncidencias {
        return RetrofitCliente.apiService.obtenerIncidencias(idTrabajador, anio, mes, tipo)
    }

    suspend fun obtenerDescuentos(idTrabajador: Int, anio: Int, mes: Int): dataDescuentos {
        return RetrofitCliente.apiService.obtenerDescuentos(idTrabajador, anio, mes)
    }
}
