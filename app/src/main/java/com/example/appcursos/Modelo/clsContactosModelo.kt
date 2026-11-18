
package com.example.appcursos.Modelo

class clsContactosModelo {

    suspend fun obtenerContactos(): dataContactosRespuesta {
        return RetrofitCliente.apiService.obtenerContactos()
    }
}