package com.example.appAsistencias.Modelo

import com.example.appAsistencias.Datos.dataDocumento
import com.example.appAsistencias.Datos.dataPendiente
import com.example.appAsistencias.network.RetrofitCliente

class clsJustificarModelo {
    suspend fun obtenerPendientes(idTrabajador: Int): List<dataPendiente> {
        return RetrofitCliente.apiService.obtenerPendientes(idTrabajador)
    }

    suspend fun obtenerDocumentosJustificar(tipoIncidencia: String): List<dataDocumento> {
        return RetrofitCliente.apiService.obtenerDocumentosJustificar(tipoIncidencia)
    }
}
