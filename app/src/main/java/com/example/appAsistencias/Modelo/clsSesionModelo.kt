package com.example.appAsistencias.Modelo

import android.content.Context

class clsSesionModelo(context: Context) {
    private val prefs = context.getSharedPreferences("sesion", Context.MODE_PRIVATE)

    // Guarda el id del trabajador que inició sesión
    fun guardarId(idTrabajador: Int) {
        prefs.edit().putInt("ID_TRABAJADOR", idTrabajador).apply()
    }

    // Devuelve 0 si no hay sesión guardada
    fun obtenerId(): Int {
        return prefs.getInt("ID_TRABAJADOR", 0)
    }

    fun cerrar() {
        prefs.edit().clear().apply()
    }
}
