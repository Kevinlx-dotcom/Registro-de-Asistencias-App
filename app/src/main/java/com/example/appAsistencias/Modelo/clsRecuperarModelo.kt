package com.example.appAsistencias.Modelo

import com.example.appAsistencias.Datos.CambiarClaveRequest
import com.example.appAsistencias.Datos.RecuperarRequest
import com.example.appAsistencias.Datos.RecuperarResponse
import com.example.appAsistencias.network.RetrofitCliente

class clsRecuperarModelo {
    suspend fun enviarCodigo(correo: String): RecuperarResponse {
        val request = RecuperarRequest(correo = correo)
        return RetrofitCliente.apiService.solicitarRecuperacion(request)
    }

    suspend fun cambiarClave(correo: String, codigo: String, nuevaClave: String): RecuperarResponse {
        val request = CambiarClaveRequest(correo = correo, codigo = codigo, nuevaClave = nuevaClave)
        return RetrofitCliente.apiService.cambiarContrasena(request)
    }
}
