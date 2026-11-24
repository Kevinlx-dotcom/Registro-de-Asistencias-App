
package com.example.appAsistencias.Vista

import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.GradientDrawable
import android.net.Uri
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.appAsistencias.Datos.DataReporte
import com.example.appAsistencias.Datos.DataReportesRespuesta
import com.example.appAsistencias.Presentador.clsReportesPresentador

class ReportesActivity : AppCompatActivity(),
    clsReportesPresentador.Vista {

    private val verde = Color.rgb(20, 118, 62)
    private val oscuro = Color.rgb(35, 43, 51)
    private val gris = Color.rgb(110, 116, 125)

    private lateinit var contenido: LinearLayout
    private lateinit var presentador: clsReportesPresentador

    private var idTrabajador = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window.statusBarColor = Color.WHITE
        window.decorView.systemUiVisibility =
            View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR

        idTrabajador = intent.getIntExtra("ID_TRABAJADOR", 0)

        val raiz = ConstraintLayout(this).apply {
            setBackgroundColor(Color.WHITE)
        }

        val barra = LinearLayout(this).apply {
            id = View.generateViewId()
            orientation = LinearLayout.HORIZONTAL
            gravity = Gravity.CENTER_VERTICAL
            setPadding(dp(12), 0, dp(16), 0)
        }

        val regresar = TextView(this).apply {
            text = "‹"
            textSize = 36f
            setTextColor(oscuro)
            gravity = Gravity.CENTER
            contentDescription = "Regresar"
            setOnClickListener { finish() }
        }

        barra.addView(
            regresar,
            LinearLayout.LayoutParams(dp(45), dp(55))
        )

        barra.addView(TextView(this).apply {
            text = "Reportes de asistencia"
            textSize = 20f
            setTypeface(null, Typeface.BOLD)
            setTextColor(oscuro)
        })

        raiz.addView(
            barra,
            ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.MATCH_PARENT,
                dp(58)
            ).apply {
                topToTop = ConstraintLayout.LayoutParams.PARENT_ID
            }
        )

        val divisorSuperior = View(this).apply {
            id = View.generateViewId()
            setBackgroundColor(Color.rgb(235, 237, 239))
        }

        raiz.addView(
            divisorSuperior,
            ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.MATCH_PARENT,
                dp(1)
            ).apply {
                topToBottom = barra.id
            }
        )

        contenido = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(dp(24), dp(18), dp(24), dp(30))
        }

        val scroll = ScrollView(this).apply {
            isFillViewport = true
            addView(contenido)
        }

        raiz.addView(
            scroll,
            ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.MATCH_PARENT,
                0
            ).apply {
                topToBottom = divisorSuperior.id
                bottomToBottom =
                    ConstraintLayout.LayoutParams.PARENT_ID
            }
        )

        setContentView(raiz)

        presentador = clsReportesPresentador(this)

        mostrarIntroduccion()

        if (idTrabajador > 0) {
            mensaje("Cargando reportes...")
            presentador.cargarReportes(idTrabajador)
        } else {
            mensaje(
                "No se recibió el ID del trabajador. " +
                        "Abre Reportes desde tu sesión."
            )
        }
    }

    override fun mostrarReportes(datos: DataReportesRespuesta) {
        if (isFinishing || isDestroyed) return

        contenido.removeAllViews()
        mostrarIntroduccion()

        val reportes = datos.reportes.sortedWith(
            compareByDescending<DataReporte> { it.anio }
                .thenByDescending { it.mes }
        )

        if (reportes.isEmpty()) {
            mensaje("No hay reportes disponibles.")
            return
        }

        reportes.forEach { reporte ->
            mostrarMes(reporte)
        }
    }

    override fun mostrarError(mensaje: String) {
        if (isFinishing || isDestroyed) return

        contenido.removeAllViews()
        mostrarIntroduccion()
        mensaje(mensaje)
    }

    private fun mostrarIntroduccion() {
        contenido.addView(TextView(this).apply {
            text = "Selecciona un mes para verlo o descargarlo."
            textSize = 15f
            setTextColor(gris)
            setPadding(0, 0, 0, dp(18))
        })
    }

    private fun mostrarMes(reporte: DataReporte) {
        val periodo = nombreMes(reporte.mes) + " " + reporte.anio

        val titulo = TextView(this).apply {
            text = periodo
            textSize = 18f
            setTypeface(null, Typeface.BOLD)
            setTextColor(oscuro)
        }

        contenido.addView(titulo)

        val resumen = TextView(this).apply {
            text = "${reporte.asistencias} asistencias · " +
                    "${reporte.retardos} retardos · " +
                    "${reporte.faltas} faltas"

            textSize = 14f
            setTextColor(gris)
            setPadding(0, dp(4), 0, dp(12))
        }

        contenido.addView(resumen)

        val botones = LinearLayout(this).apply {
            orientation = LinearLayout.HORIZONTAL
            gravity = Gravity.CENTER_VERTICAL
        }

        botones.addView(
            boton("Ver reporte", true) {
                verReporte(reporte)
            },
            LinearLayout.LayoutParams(
                0, dp(44), 2f
            ).apply {
                marginEnd = dp(8)
            }
        )

        botones.addView(
            boton("PDF", false) {
                descargar(reporte, "pdf")
            },
            LinearLayout.LayoutParams(
                0, dp(44), 1f
            ).apply {
                marginEnd = dp(8)
            }
        )

        botones.addView(
            boton("Excel", false) {
                descargar(reporte, "excel")
            },
            LinearLayout.LayoutParams(
                0, dp(44), 1f
            )
        )

        contenido.addView(botones)

        contenido.addView(View(this).apply {
            setBackgroundColor(Color.rgb(234, 236, 238))

            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                dp(1)
            ).apply {
                topMargin = dp(18)
                bottomMargin = dp(18)
            }
        })
    }

    private fun boton(
        titulo: String,
        principal: Boolean,
        accion: () -> Unit
    ): TextView {
        val fondo = GradientDrawable().apply {
            cornerRadius = dp(8).toFloat()
            setColor(
                if (principal) verde else Color.WHITE
            )

            if (!principal) {
                setStroke(dp(1), verde)
            }
        }

        return TextView(this).apply {
            text = titulo
            textSize = 14f
            setTypeface(null, Typeface.BOLD)

            setTextColor(
                if (principal) Color.WHITE else verde
            )

            gravity = Gravity.CENTER
            background = fondo
            isClickable = true
            isFocusable = true

            setOnClickListener {
                accion()
            }
        }
    }

    private fun verReporte(reporte: DataReporte) {
        val periodo = nombreMes(reporte.mes) + " " + reporte.anio

        AlertDialog.Builder(this)
            .setTitle("Reporte · $periodo")
            .setMessage(
                "Asistencias: ${reporte.asistencias}\n" +
                        "Retardos: ${reporte.retardos}\n" +
                        "Faltas: ${reporte.faltas}\n\n" +
                        "Este es el resumen del mes."
            )
            .setPositiveButton("Aceptar", null)
            .show()
    }

    private fun descargar(
        reporte: DataReporte,
        formato: String
    ) {
        if (idTrabajador <= 0) {
            mensajeEmergente("No se encontró el trabajador.")
            return
        }

        val url = Uri.parse(
            "https://cbta5.4cuthh.com/cbta_api/descargar_reporte.php"
        ).buildUpon()
            .appendQueryParameter(
                "idTrabajador",
                idTrabajador.toString()
            )
            .appendQueryParameter(
                "anio",
                reporte.anio.toString()
            )
            .appendQueryParameter(
                "mes",
                reporte.mes.toString()
            )
            .appendQueryParameter("formato", formato)
            .build()

        try {
            startActivity(
                Intent(Intent.ACTION_VIEW, url)
            )
        } catch (_: ActivityNotFoundException) {
            mensajeEmergente(
                "No hay una aplicación para abrir la descarga."
            )
        }
    }

    private fun nombreMes(mes: Int): String {
        val meses = listOf(
            "Enero", "Febrero", "Marzo", "Abril",
            "Mayo", "Junio", "Julio", "Agosto",
            "Septiembre", "Octubre", "Noviembre",
            "Diciembre"
        )

        return meses.getOrNull(mes - 1) ?: "Mes $mes"
    }

    private fun mensaje(texto: String) {
        contenido.addView(TextView(this).apply {
            text = texto
            textSize = 15f
            setTextColor(gris)
            setPadding(0, dp(12), 0, 0)
        })
    }

    private fun mensajeEmergente(texto: String) {
        Toast.makeText(
            this,
            texto,
            Toast.LENGTH_LONG
        ).show()
    }

    private fun dp(numero: Int): Int {
        return (
                numero * resources.displayMetrics.density
                ).toInt()
    }
}
