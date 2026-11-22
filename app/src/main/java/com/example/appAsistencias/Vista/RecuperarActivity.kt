package com.example.appAsistencias.Vista

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.appAsistencias.Presentador.clsRecuperarPresentador
import com.example.appAsistencias.R

class RecuperarActivity : AppCompatActivity() {
    private lateinit var presenter: clsRecuperarPresentador
    private lateinit var txtCorreo: EditText
    private lateinit var btnEnviarCodigo: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_recuperar)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        //1.Enlazar componentes
        txtCorreo = findViewById(R.id.txtCorreo)
        btnEnviarCodigo = findViewById(R.id.btnEnviarCodigo)

        //2. inicializar presentador
        presenter = clsRecuperarPresentador(this)

        //3.Listener del botón
        btnEnviarCodigo.setOnClickListener(this::ejecutarEnviarCodigo)
    }

    fun ejecutarEnviarCodigo(v: View) {
        presenter.enviarCodigo(txtCorreo.text.toString().trim())
    }

    fun codigoEnviado(mensaje: String) {
        Toast.makeText(this, mensaje, Toast.LENGTH_LONG).show()
        val intent = Intent(this, CambiarClaveActivity::class.java)
        intent.putExtra("CORREO", txtCorreo.text.toString().trim())
        startActivity(intent)
        finish()
    }

    fun mostrarError(mensaje: String) {
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show()
    }
}
