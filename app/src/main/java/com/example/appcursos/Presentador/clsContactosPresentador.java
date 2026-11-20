
package com.example.appcursos.Presentador;

import com.example.appcursos.Modelo.DataContactosRespuesta;
import com.example.appcursos.Modelo.clsContactosModelo;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class clsContactosPresentador {

    public interface Vista {
        void mostrarContactos(DataContactosRespuesta datos);
        void mostrarError(String mensaje);
    }

    private final clsContactosModelo modelo;
    private final Vista vista;

    public clsContactosPresentador(Vista vista) {
        this.vista = vista;
        this.modelo = new clsContactosModelo();
    }

    public void cargarContactos() {
        modelo.obtenerContactos().enqueue(
                new Callback<DataContactosRespuesta>() {

                    @Override
                    public void onResponse(
                            Call<DataContactosRespuesta> call,
                            Response<DataContactosRespuesta> response
                    ) {
                        DataContactosRespuesta datos = response.body();

                        if (response.isSuccessful()
                                && datos != null
                                && datos.isExito()) {
                            vista.mostrarContactos(datos);
                        } else {
                            vista.mostrarError("No se pudieron cargar los contactos");
                        }
                    }

                    @Override
                    public void onFailure(
                            Call<DataContactosRespuesta> call,
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

