package com.example.appAsistencias.Modelo

import com.example.appAsistencias.Datos.dataDocumentosRespuesta
import com.example.appAsistencias.Datos.dataPendienteRespuesta
import com.example.appAsistencias.network.RetrofitCliente

class clsJustificarModelo {
    suspend fun obtenerPendientes(idTrabajador: Int): dataPendienteRespuesta {
        return RetrofitCliente.apiService.obtenerPendientes(idTrabajador)
    }

    suspend fun obtenerDocumentosJustificar(tipoIncidencia: String): dataDocumentosRespuesta {
        return RetrofitCliente.apiService.obtenerDocumentosJustificar(tipoIncidencia)
    }
}