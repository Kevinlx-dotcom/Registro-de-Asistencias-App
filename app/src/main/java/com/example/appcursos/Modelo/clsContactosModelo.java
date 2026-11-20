package com.example.appcursos.Modelo;

import retrofit2.Call;

public class clsContactosModelo {

    public Call<DataContactosRespuesta> obtenerContactos() {
        return RetrofitCliente.INSTANCE.getApiService().obtenerContactos();
    }
}
