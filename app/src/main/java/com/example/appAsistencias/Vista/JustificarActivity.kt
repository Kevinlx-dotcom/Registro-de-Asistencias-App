package com.example.appAsistencias.Vista

import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.appAsistencias.Presentador.clsJustificarPresentador
import com.example.appAsistencias.R

class JustificarActivity : AppCompatActivity() {

    private lateinit var presenter: clsJustificarPresentador
    private lateinit var txtTipoFechaIncidencia: TextView
    private lateinit var txtHoraEntrada: TextView
    private lateinit var txtDoc1: TextView
    private lateinit var txtDoc2: TextView
    private lateinit var txtDoc3: TextView

    private var idTrabajador = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_justificar)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // 1. Enlazar componentes del XML
        txtTipoFechaIncidencia = findViewById(R.id.txtTipoFechaIncidencia)
        txtHoraEntrada = findViewById(R.id.txtHoraEntrada)
        txtDoc1 = findViewById(R.id.txtDoc1)
        txtDoc2 = findViewById(R.id.txtDoc2)
        txtDoc3 = findViewById(R.id.txtDoc3)

        // 2. Obtener el ID del trabajador enviado al iniciar esta Activity
        idTrabajador = intent.getIntExtra("ID_TRABAJADOR", 0)

        // 3. Inicializar el presentador y cargar las incidencias pendientes
        presenter = clsJustificarPresentador(this)
        presenter.obtenerPendientes(idTrabajador)
    }

    // Llamado por clsJustificarPresentador con la primera incidencia pendiente
    fun mostrarPendiente(tipoFecha: String, horaEntrada: String) {
        txtTipoFechaIncidencia.text = tipoFecha
        txtHoraEntrada.text = horaEntrada
    }

    // Llamado por clsJustificarPresentador con los documentos requeridos
    fun mostrarDocumentos(doc1: String, doc2: String, doc3: String) {
        txtDoc1.text = doc1
        txtDoc2.text = doc2
        txtDoc3.text = doc3
    }

    fun mostrarError(mensaje: String) {
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show()
    }
}