package com.example.appAsistencias.Modelo

import com.example.appAsistencias.Datos.DataContactosRespuesta
import com.example.appAsistencias.network.RetrofitCliente

class clsContactosModelo {
    suspend fun obtenerContactos(): DataContactosRespuesta {
        return RetrofitCliente.apiService.obtenerContactos()
    }
}
