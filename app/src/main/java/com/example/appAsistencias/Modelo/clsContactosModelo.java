package com.example.appAsistencias.Modelo;

import com.example.appAsistencias.network.RetrofitCliente;

import retrofit2.Call;

public class clsContactosModelo {

    public Call<DataContactosRespuesta> obtenerContactos() {
        return RetrofitCliente.INSTANCE.getApiService().obtenerContactos();
    }
}
