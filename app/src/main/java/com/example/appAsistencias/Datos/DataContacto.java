package com.example.appAsistencias.Datos;

public class DataContacto {
    private String nombre;
    private String cargo;
    private String telefono;
    private String correo;

    public DataContacto() {
        this.nombre = null;
        this.cargo = null;
        this.telefono = null;
        this.correo = null;
    }

    public DataContacto(String nombre, String cargo, String telefono, String correo) {
        this.nombre = nombre;
        this.cargo = cargo;
        this.telefono = telefono;
        this.correo = correo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }
}
