package com.example.appAsistencias.Vista

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.appAsistencias.Presentador.clsLoginPresentador
import com.example.appAsistencias.R

class LoginActivity : AppCompatActivity() {
    private lateinit var presenter: clsLoginPresentador
    private lateinit var txtCorreo: EditText
    private lateinit var txtClave: EditText
    private lateinit var btnLogin: Button
    private lateinit var txtOlvideClave: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        //1.Enlazar componentes
        txtCorreo = findViewById(R.id.txtCorreo)
        txtClave = findViewById(R.id.txtClave)
        btnLogin = findViewById(R.id.btnLogin)
        txtOlvideClave = findViewById(R.id.txtOlvideClave)

        //2. inicializar presentador
        presenter = clsLoginPresentador(this)

        //3.Listener del botón
        btnLogin.setOnClickListener(this::ejecutarInicioSesion)
        txtOlvideClave.setOnClickListener(this::abrirRecuperar)
    }

    fun ejecutarInicioSesion(v: View) {
        val correo = txtCorreo.text.toString().trim()
        val clave = txtClave.text.toString().trim()
        presenter.realizarLogin(correo, clave)
    }

    fun abrirRecuperar(v: View) {
        val intent = Intent(this, RecuperarActivity::class.java)
        startActivity(intent)
    }

    fun loginExitoso(mensaje: String, idTrabajador: Int) {
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show()
        val intent = Intent(this, InicioActivity::class.java)
        intent.putExtra("ID_TRABAJADOR", idTrabajador)
        startActivity(intent)
        finish()
    }

    fun mostrarError(mensaje: String) {
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show()
    }
}
