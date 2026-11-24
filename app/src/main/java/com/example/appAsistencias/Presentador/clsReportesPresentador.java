
package com.example.appAsistencias.Presentador;

import com.example.appAsistencias.Datos.DataReportesRespuesta;
import com.example.appAsistencias.Modelo.clsReportesModelo;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class clsReportesPresentador {

    public interface Vista {
        void mostrarReportes(DataReportesRespuesta datos);
        void mostrarError(String mensaje);
    }

    private final clsReportesModelo modelo;
    private final Vista vista;

    public clsReportesPresentador(Vista vista) {
        this.vista = vista;
        this.modelo = new clsReportesModelo();
    }

    public void cargarReportes(int idTrabajador) {
        modelo.obtenerReportes(idTrabajador).enqueue(
                new Callback<DataReportesRespuesta>() {
                    @Override
                    public void onResponse(
                            Call<DataReportesRespuesta> call,
                            Response<DataReportesRespuesta> response
                    ) {
                        DataReportesRespuesta datos = response.body();

                        if (response.isSuccessful()
                                && datos != null
                                && datos.isExito()) {
                            vista.mostrarReportes(datos);
                        } else {
                            vista.mostrarError("No se pudieron cargar los reportes");
                        }
                    }

                    @Override
                    public void onFailure(
                            Call<DataReportesRespuesta> call,
                            Throwable error
                    ) {
                        if (!call.isCanceled()) {
                            vista.mostrarError("Error de conexión");
                        }
                    }
                }
        );
    }
}
