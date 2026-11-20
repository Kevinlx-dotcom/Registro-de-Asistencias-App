
package com.example.appcursos.Modelo;

import java.util.Collections;
import java.util.List;

public class DataContactosRespuesta {

    private boolean exito;
    private String mensaje;
    private List<DataContacto> contactos;
    private List<DataContacto> administracion;
    private List<DataContacto> directivos;

    public boolean isExito() {
        return exito;
    }

    public String getMensaje() {
        return mensaje;
    }

    public List<DataContacto> getContactos() {
        return contactos != null
                ? contactos : Collections.emptyList();
    }

    public List<DataContacto> getAdministracion() {
        return administracion != null
                ? administracion : Collections.emptyList();
    }

    public List<DataContacto> getDirectivos() {
        return directivos != null
                ? directivos : Collections.emptyList();
    }
}
