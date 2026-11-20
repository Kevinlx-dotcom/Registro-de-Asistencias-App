package com.example.appcursos.Modelo

class clsLoginModelo {
    suspend fun autenticarUsuario(correo: String, clave: String): LoginResponse {
        val request = LoginRequest(correo = correo, passusuario = clave)
        return RetrofitCliente.apiService.iniciarSesion(request)
    }
}
