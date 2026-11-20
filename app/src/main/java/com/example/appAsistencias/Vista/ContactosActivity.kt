
package com.example.appAsistencias.Vista

import android.os.Bundle
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.appAsistencias.Datos.DataContacto
import com.example.appAsistencias.Datos.DataContactosRespuesta
import com.example.appAsistencias.Presentador.clsContactosPresentador

class ContactosActivity : AppCompatActivity(),
    clsContactosPresentador.Vista {

    private lateinit var contenido: TextView
    private lateinit var presentador: clsContactosPresentador

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val contenedor = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(40, 48, 40, 24)
        }

        val titulo = TextView(this).apply {
            text = "Contactos institucionales"
            textSize = 26f
        }

        contenido = TextView(this).apply {
            text = "Cargando contactos..."
            textSize = 16f
            setPadding(0, 32, 0, 0)
        }

        contenedor.addView(titulo)
        contenedor.addView(contenido)

        setContentView(
            ScrollView(this).apply {
                addView(contenedor)
            }
        )

        presentador = clsContactosPresentador(this)
        presentador.cargarContactos()
    }

    override fun mostrarContactos(datos: DataContactosRespuesta) {
        if (isFinishing || isDestroyed) return

        val grupos = listOf(
            "Directivos" to datos.directivos,
            "Administración" to datos.administracion,
            "Contactos generales" to datos.contactos
        )

        val resultado = grupos
            .filter { it.second.isNotEmpty() }
            .joinToString("\n\n") { (titulo, contactos) ->
                titulo + "\n\n" +
                        contactos.joinToString("\n\n") {
                            mostrarContacto(it)
                        }
            }

        contenido.text = resultado.ifEmpty {
            "No hay contactos disponibles."
        }
    }

    override fun mostrarError(mensaje: String) {
        if (!isFinishing && !isDestroyed) {
            contenido.text = mensaje
        }
    }

    private fun mostrarContacto(contacto: DataContacto): String {
        return """
            ${contacto.nombre ?: "Sin nombre"}
            Cargo: ${contacto.cargo.orEmpty()}
            Teléfono: ${contacto.telefono.orEmpty()}
            Correo: ${contacto.correo.orEmpty()}
        """.trimIndent()
    }
}
