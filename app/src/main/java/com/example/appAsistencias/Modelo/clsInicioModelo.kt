package com.example.appAsistencias.Modelo

import com.example.appAsistencias.Datos.dataInicio
import com.example.appAsistencias.network.RetrofitCliente

class clsInicioModelo {
    suspend fun obtenerInicio(idTrabajador: Int): dataInicio {
        return RetrofitCliente.apiService.obtenerInicio(idTrabajador)
    }
}
