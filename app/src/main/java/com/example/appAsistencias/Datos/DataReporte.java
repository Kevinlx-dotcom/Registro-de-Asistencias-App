
package com.example.appAsistencias.Datos;

public class DataReporte {

    private int anio;
    private int mes;
    private int asistencias;
    private int retardos;
    private int faltas;
    private boolean generado;

    public DataReporte() {
    }

    public int getAnio() {
        return anio;
    }

    public int getMes() {
        return mes;
    }

    public int getAsistencias() {
        return asistencias;
    }

    public int getRetardos() {
        return retardos;
    }

    public int getFaltas() {
        return faltas;
    }

    public boolean isGenerado() {
        return generado;
    }
}
