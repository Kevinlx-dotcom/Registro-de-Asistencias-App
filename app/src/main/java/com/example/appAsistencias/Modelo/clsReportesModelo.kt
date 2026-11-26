package com.example.appAsistencias.Modelo

import com.example.appAsistencias.Datos.ReportesResponse
import com.example.appAsistencias.network.RetrofitCliente

class clsReportesModelo {
    suspend fun obtenerReportes(idTrabajador: Int): ReportesResponse {
        return RetrofitCliente.apiService.obtenerReportes(idTrabajador)
    }
}
