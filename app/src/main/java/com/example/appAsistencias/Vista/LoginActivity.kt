package com.example.appAsistencias.Vista

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.appAsistencias.Presentador.clsHuellaPresentador
import com.example.appAsistencias.Presentador.clsLoginPresentador
import com.example.appAsistencias.R
import javax.crypto.Cipher

class LoginActivity : AppCompatActivity() {
    private lateinit var presenter: clsLoginPresentador
    private lateinit var huellaPresenter: clsHuellaPresentador
    private lateinit var txtCorreo: EditText
    private lateinit var txtClave: EditText
    private lateinit var btnLogin: Button
    private lateinit var btnHuella: Button
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
        btnHuella = findViewById(R.id.btnHuella)
        txtOlvideClave = findViewById(R.id.txtOlvideClave)

        //2. inicializar presentadores
        presenter = clsLoginPresentador(this)
        huellaPresenter = clsHuellaPresentador(this)

        //3.Listeners de los botones
        btnLogin.setOnClickListener(this::ejecutarInicioSesion)
        btnHuella.setOnClickListener(this::ejecutarHuella)
        txtOlvideClave.setOnClickListener(this::abrirRecuperar)
    }

    fun ejecutarInicioSesion(v: View) {
        presenter.realizarLogin(txtCorreo.text.toString().trim(), txtClave.text.toString().trim())
    }

    fun ejecutarHuella(v: View) {
        huellaPresenter.entrarConHuella()
    }

    fun abrirRecuperar(v: View) {
        val intent = Intent(this, RecuperarActivity::class.java)
        startActivity(intent)
    }

    fun loginExitoso(mensaje: String, idTrabajador: Int) {
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show()
        huellaPresenter.despuesDelLogin(idTrabajador)
    }

    fun abrirInicio(idTrabajador: Int) {
        val intent = Intent(this, InicioActivity::class.java)
        intent.putExtra("ID_TRABAJADOR", idTrabajador)
        startActivity(intent)
        finish()
    }

    // Pregunta si quiere activar la huella después de su primer login
    fun preguntarActivarHuella() {
        AlertDialog.Builder(this)
            .setTitle("Entrar con huella")
            .setMessage("¿Quieres activar la huella para entrar más rápido la próxima vez?")
            .setCancelable(false)
            .setPositiveButton("Activar") { _, _ -> huellaPresenter.activarHuella() }
            .setNegativeButton("Ahora no") { _, _ -> huellaPresenter.omitirHuella() }
            .show()
    }

    // Muestra el diálogo de huella de Android (sirve para activar y para entrar)
    fun pedirHuella(cipher: Cipher, paraActivar: Boolean, titulo: String) {
        val callback = object : BiometricPrompt.AuthenticationCallback() {
            override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                // el cipher ya viene desbloqueado por la huella
                huellaPresenter.huellaCorrecta(
                    result.cryptoObject!!.cipher,
                    paraActivar,
                    txtCorreo.text.toString().trim(),
                    txtClave.text.toString().trim()
                )
            }

            override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                huellaPresenter.huellaError(errString.toString(), paraActivar)
            }
        }

        val info = BiometricPrompt.PromptInfo.Builder()
            .setTitle(titulo)
            .setNegativeButtonText("Cancelar")
            .setAllowedAuthenticators(BiometricManager.Authenticators.BIOMETRIC_STRONG)
            .build()

        BiometricPrompt(this, ContextCompat.getMainExecutor(this), callback)
            .authenticate(info, BiometricPrompt.CryptoObject(cipher))
    }

    fun mostrarError(mensaje: String) {
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show()
    }
}
