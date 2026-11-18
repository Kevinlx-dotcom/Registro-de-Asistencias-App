
package com.example.appcursos.Modelo

class clsReportesModelo {

    suspend fun obtenerReportes(
        idTrabajador: Int
    ): dataReportesRespuesta {
        return RetrofitCliente.apiService.obtenerReportes(idTrabajador)
    }

    suspend fun descargarReporte(
        idTrabajador: Int,
        anio: Int,
        mes: Int,
        formato: String
    ) = RetrofitCliente.apiService.descargarReporte(
        idTrabajador, anio, mes, formato
    )
}