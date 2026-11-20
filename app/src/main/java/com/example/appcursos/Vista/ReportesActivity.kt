
package com.example.appcursos.Vista

import android.os.Bundle
import android.text.InputType
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.appcursos.Modelo.DataReportesRespuesta
import com.example.appcursos.Presentador.clsReportesPresentador

class ReportesActivity : AppCompatActivity(),
    clsReportesPresentador.Vista {

    private lateinit var resultado: TextView
    private lateinit var presentador: clsReportesPresentador

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val contenedor = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(40, 48, 40, 24)
        }

        val titulo = TextView(this).apply {
            text = "Reportes de asistencia"
            textSize = 26f
        }

        val idTrabajador = EditText(this).apply {
            hint = "ID del trabajador"
            inputType = InputType.TYPE_CLASS_NUMBER
        }

        resultado = TextView(this).apply {
            text = "Consulta tus reportes de asistencia."
            textSize = 16f
            setPadding(0, 32, 0, 0)
        }

        presentador = clsReportesPresentador(this)

        val boton = Button(this).apply {
            text = "Consultar reportes"

            setOnClickListener {
                val id = idTrabajador.text.toString().toIntOrNull()

                if (id == null || id <= 0) {
                    resultado.text = "Ingresa un ID válido."
                } else {
                    resultado.text = "Cargando reportes..."
                    presentador.cargarReportes(id)
                }
            }
        }

        contenedor.addView(titulo)
        contenedor.addView(idTrabajador)
        contenedor.addView(boton)
        contenedor.addView(resultado)

        setContentView(
            ScrollView(this).apply {
                addView(contenedor)
            }
        )
    }

    override fun mostrarReportes(datos: DataReportesRespuesta) {
        if (isFinishing || isDestroyed) return

        val reportes = datos.reportes

        if (reportes.isEmpty()) {
            resultado.text = "No hay reportes disponibles."
            return
        }

        resultado.text = reportes.joinToString("\n\n") {
            """
            Periodo: ${it.mes}/${it.anio}
            Asistencias: ${it.asistencias}
            Retardos: ${it.retardos}
            Faltas: ${it.faltas}
            Generado: ${if (it.isGenerado) "Sí" else "No"}
            """.trimIndent()
        }
    }

    override fun mostrarError(mensaje: String) {
        if (!isFinishing && !isDestroyed) {
            resultado.text = mensaje
        }
    }
}
