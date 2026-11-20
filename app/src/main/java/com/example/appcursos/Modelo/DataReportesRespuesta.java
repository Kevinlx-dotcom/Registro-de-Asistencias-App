
package com.example.appcursos.Modelo;

import java.util.Collections;
import java.util.List;

public class DataReportesRespuesta {

    private boolean exito;
    private String mensaje;
    private List<DataReporte> reportes;

    public DataReportesRespuesta() {
    }

    public boolean isExito() {
        return exito;
    }

    public String getMensaje() {
        return mensaje;
    }

    public List<DataReporte> getReportes() {
        return reportes != null
                ? reportes : Collections.emptyList();
    }
}

