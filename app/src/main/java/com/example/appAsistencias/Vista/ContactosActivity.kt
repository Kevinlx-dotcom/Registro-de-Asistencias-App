
package com.example.appAsistencias.Vista

import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.net.Uri
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.appAsistencias.Datos.DataContacto
import com.example.appAsistencias.Datos.DataContactosRespuesta
import com.example.appAsistencias.Presentador.clsContactosPresentador

class ContactosActivity : AppCompatActivity(),
    clsContactosPresentador.Vista {

    private val verde = Color.rgb(20, 118, 62)
    private val oscuro = Color.rgb(35, 43, 51)
    private val gris = Color.rgb(110, 116, 125)

    private lateinit var scroll: ScrollView
    private lateinit var lista: LinearLayout
    private lateinit var presentador: clsContactosPresentador

    private val cabeceras = mutableMapOf<String, View>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window.statusBarColor = Color.WHITE
        window.decorView.systemUiVisibility =
            View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR

        val raiz = ConstraintLayout(this).apply {
            setBackgroundColor(Color.WHITE)
        }

        val barra = LinearLayout(this).apply {
            id = View.generateViewId()
            orientation = LinearLayout.HORIZONTAL
            gravity = Gravity.CENTER_VERTICAL
            setPadding(dp(12), 0, dp(20), 0)
            setBackgroundColor(Color.WHITE)
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
            text = "Contactos"
            textSize = 21f
            setTypeface(null, Typeface.BOLD)
            setTextColor(oscuro)
        })

        raiz.addView(
            barra,
            ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.MATCH_PARENT,
                dp(57)
            ).apply {
                topToTop = ConstraintLayout.LayoutParams.PARENT_ID
            }
        )

        val divisor = View(this).apply {
            id = View.generateViewId()
            setBackgroundColor(Color.rgb(235, 237, 239))
        }

        raiz.addView(
            divisor,
            ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.MATCH_PARENT,
                dp(1)
            ).apply {
                topToBottom = barra.id
            }
        )

        lista = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(dp(24), dp(10), dp(24), dp(30))
        }

        scroll = ScrollView(this).apply {
            isFillViewport = true
            addView(lista)
        }

        raiz.addView(
            scroll,
            ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.MATCH_PARENT,
                0
            ).apply {
                topToBottom = divisor.id
                bottomToBottom =
                    ConstraintLayout.LayoutParams.PARENT_ID
            }
        )

        setContentView(raiz)

        mensaje("Cargando contactos...")

        presentador = clsContactosPresentador(this)
        presentador.cargarContactos()
    }

    override fun mostrarContactos(datos: DataContactosRespuesta) {
        if (isFinishing || isDestroyed) return

        lista.removeAllViews()
        cabeceras.clear()

        grupo(
            "ADMINISTRACIÓN",
            "administracion",
            datos.administracion
        )

        grupo(
            "DIRECTIVOS",
            "directivo",
            datos.directivos
        )

        grupo(
            "CONTACTOS",
            "contacto",
            datos.contactos
        )

        if (
            datos.administracion.isEmpty() &&
            datos.directivos.isEmpty() &&
            datos.contactos.isEmpty()
        ) {
            mensaje("No hay contactos disponibles.")
        }

        val categoria = intent
            .getStringExtra("CATEGORIA")
            ?.lowercase()
            ?: ""

        cabeceras[categoria]?.let { destino ->
            scroll.post {
                scroll.smoothScrollTo(0, destino.top)
            }
        }
    }

    override fun mostrarError(mensaje: String) {
        if (!isFinishing && !isDestroyed) {
            lista.removeAllViews()
            mensaje(mensaje)
        }
    }

    private fun grupo(
        titulo: String,
        categoria: String,
        contactos: List<DataContacto>
    ) {
        if (contactos.isEmpty()) return

        val tituloGrupo = TextView(this).apply {
            text = titulo
            textSize = 13f
            setTypeface(null, Typeface.BOLD)
            setTextColor(gris)
            setPadding(0, dp(20), 0, dp(14))
        }

        cabeceras[categoria] = tituloGrupo
        lista.addView(tituloGrupo)

        contactos.forEach { contacto ->

            val fila = LinearLayout(this).apply {
                orientation = LinearLayout.HORIZONTAL
                gravity = Gravity.CENTER_VERTICAL
                setPadding(0, dp(10), 0, dp(12))
            }

            val informacion = LinearLayout(this).apply {
                orientation = LinearLayout.VERTICAL
            }

            informacion.addView(TextView(this).apply {
                text = contacto.nombre
                    ?.takeIf { it.isNotBlank() }
                    ?: "Sin nombre"

                textSize = 16f
                setTypeface(null, Typeface.BOLD)
                setTextColor(oscuro)
            })

            informacion.addView(TextView(this).apply {
                text = contacto.cargo ?: ""
                textSize = 14f
                setTextColor(gris)
                setPadding(0, dp(3), 0, 0)
            })

            fila.addView(
                informacion,
                LinearLayout.LayoutParams(
                    0,
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    1f
                )
            )

            val telefono = contacto.telefono?.trim().orEmpty()
            val correo = contacto.correo?.trim().orEmpty()

            fila.addView(
                accion(
                    "☎",
                    "Llamar a $telefono",
                    telefono.isNotBlank()
                ) {
                    abrir(
                        Intent(
                            Intent.ACTION_DIAL,
                            Uri.parse("tel:${Uri.encode(telefono)}")
                        )
                    )
                }
            )

            fila.addView(
                accion(
                    "✉",
                    "Enviar correo a $correo",
                    correo.isNotBlank()
                ) {
                    abrir(
                        Intent(
                            Intent.ACTION_SENDTO,
                            Uri.parse("mailto:${Uri.encode(correo)}")
                        )
                    )
                }
            )

            lista.addView(fila)

            lista.addView(View(this).apply {
                setBackgroundColor(Color.rgb(238, 239, 240))

                layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    dp(1)
                )
            })
        }
    }

    private fun accion(
        simbolo: String,
        descripcion: String,
        habilitada: Boolean,
        alPulsar: () -> Unit
    ): TextView {
        return TextView(this).apply {
            text = simbolo
            textSize = 24f

            setTextColor(
                if (habilitada) verde else Color.LTGRAY
            )

            gravity = Gravity.CENTER
            contentDescription = descripcion
            isEnabled = habilitada

            setOnClickListener {
                alPulsar()
            }

            layoutParams = LinearLayout.LayoutParams(
                dp(40),
                dp(45)
            )
        }
    }

    private fun mensaje(texto: String) {
        lista.addView(TextView(this).apply {
            text = texto
            textSize = 16f
            setTextColor(gris)
            setPadding(0, dp(20), 0, 0)
        })
    }

    private fun abrir(intent: Intent) {
        try {
            startActivity(intent)
        } catch (_: ActivityNotFoundException) {
            Toast.makeText(
                this,
                "No hay una aplicación disponible",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun dp(numero: Int): Int {
        return (numero * resources.displayMetrics.density).toInt()
    }
}
