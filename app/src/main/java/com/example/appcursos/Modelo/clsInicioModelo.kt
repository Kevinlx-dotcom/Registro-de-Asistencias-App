package com.example.appcursos.Modelo

class clsInicioModelo {
    suspend fun obtenerInicio(idTrabajador: Int): dataInicio {
        return RetrofitCliente.apiService.obtenerInicio(idTrabajador)
    }
}
