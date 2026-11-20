package com.example.appcursos.Modelo;

import okhttp3.ResponseBody;
import retrofit2.Call;

public class clsReportesModelo {

    public Call<DataReportesRespuesta> obtenerReportes(int idTrabajador) {
        return RetrofitCliente.INSTANCE.getApiService()
                .obtenerReportes(idTrabajador);
    }

    public Call<ResponseBody> descargarReporte(
            int idTrabajador, int anio, int mes, String formato) {
        return RetrofitCliente.INSTANCE.getApiService()
                .solicitarDescargaReporte(idTrabajador, anio, mes, formato);
    }
}
