package com.example.appAsistencias.Vista

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.appAsistencias.Presentador.clsCambiarClavePresentador
import com.example.appAsistencias.R

class CambiarClaveActivity : AppCompatActivity() {
    private lateinit var presenter: clsCambiarClavePresentador
    private lateinit var txtCodigo: EditText
    private lateinit var txtClaveNueva: EditText
    private lateinit var txtClaveConfirmar: EditText
    private lateinit var btnCambiarClave: Button
    private var correo = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_cambiar_clave)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        //1.Enlazar componentes
        txtCodigo = findViewById(R.id.txtCodigo)
        txtClaveNueva = findViewById(R.id.txtClaveNueva)
        txtClaveConfirmar = findViewById(R.id.txtClaveConfirmar)
        btnCambiarClave = findViewById(R.id.btnCambiarClave)

        //2. correo que llega desde la pantalla anterior
        correo = intent.getStringExtra("CORREO") ?: ""

        //3. inicializar presentador
        presenter = clsCambiarClavePresentador(this)

        //4.Listener del botón
        btnCambiarClave.setOnClickListener(this::ejecutarCambiarClave)
    }

    fun ejecutarCambiarClave(v: View) {
        presenter.cambiarClave(
            correo,
            txtCodigo.text.toString().trim(),
            txtClaveNueva.text.toString().trim(),
            txtClaveConfirmar.text.toString().trim()
        )
    }

    fun claveCambiada(mensaje: String) {
        Toast.makeText(this, mensaje, Toast.LENGTH_LONG).show()
        finish()
    }

    fun mostrarError(mensaje: String) {
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show()
    }
}
