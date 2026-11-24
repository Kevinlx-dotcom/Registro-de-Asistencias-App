package com.example.appAsistencias.Modelo;

import com.example.appAsistencias.Datos.DataContactosRespuesta;
import retrofit2.Call;

public class clsContactosModelo {

    public Call<DataContactosRespuesta> obtenerContactos() {
        return com.example.appAsistencias.network.RetrofitContactosReportes.INSTANCE.getApiService().obtenerContactos();
    }
}
