package com.example.appAsistencias.Modelo

import com.example.appAsistencias.Datos.ContactosResponse
import com.example.appAsistencias.network.RetrofitCliente

class clsContactosModelo {
    suspend fun obtenerContactos(): ContactosResponse {
        return RetrofitCliente.apiService.obtenerContactos()
    }
}
