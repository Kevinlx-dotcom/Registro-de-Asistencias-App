package com.example.appAsistencias.Modelo

import com.example.appAsistencias.Datos.LoginRequest
import com.example.appAsistencias.Datos.LoginResponse
import com.example.appAsistencias.network.RetrofitCliente

class clsLoginModelo {
    suspend fun autenticarUsuario(correo: String, clave: String): LoginResponse {
        val request = LoginRequest(correo = correo, passusuario = clave)
        return RetrofitCliente.apiService.iniciarSesion(request)
    }
}
