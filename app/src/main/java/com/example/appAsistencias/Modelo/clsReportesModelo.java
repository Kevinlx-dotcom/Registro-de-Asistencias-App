package com.example.appAsistencias.Modelo;

import okhttp3.ResponseBody;
import com.example.appAsistencias.Datos.DataReportesRespuesta;
import retrofit2.Call;

public class clsReportesModelo {

    public Call<DataReportesRespuesta> obtenerReportes(int idTrabajador) {
        return com.example.appAsistencias.network.RetrofitContactosReportes.INSTANCE.getApiService()
                .obtenerReportes(idTrabajador);
    }

    public Call<ResponseBody> descargarReporte(
            int idTrabajador, int anio, int mes, String formato) {
        return com.example.appAsistencias.network.RetrofitContactosReportes.INSTANCE.getApiService()
                .solicitarDescargaReporte(idTrabajador, anio, mes, formato);
    }
}
