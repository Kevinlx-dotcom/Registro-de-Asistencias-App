
package com.example.appcursos.Vista

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val contenedor = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(40, 80, 40, 40)
        }

        val btnContactos = Button(this).apply {
            text = "Ver contactos"

            setOnClickListener {
                val intent = Intent(
                    this@MainActivity,
                    ContactosActivity::class.java
                )
                startActivity(intent)
            }
        }

        val btnReportes = Button(this).apply {
            text = "Ver reportes"

            setOnClickListener {
                val intent = Intent(
                    this@MainActivity,
                    ReportesActivity::class.java
                )
                startActivity(intent)
            }
        }

        contenedor.addView(btnContactos)
        contenedor.addView(btnReportes)

        setContentView(contenedor)
    }
}
