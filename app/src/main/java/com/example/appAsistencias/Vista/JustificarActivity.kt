package com.example.appAsistencias.Vista

import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.appAsistencias.Datos.dataDocumento
import com.example.appAsistencias.Datos.dataPendiente
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

        // 3. Inicializar el presentador
        presenter = clsJustificarPresentador(this)
    }

    override fun onResume() {
        super.onResume()
        // Cargar las incidencias pendientes de este trabajador
        presenter.obtenerPendientes(idTrabajador)
    }

    // Llamado por clsJustificarPresentador al recibir datos del API
    fun mostrarPendientes(lista: List<dataPendiente>) {
        if (lista.isNotEmpty()) {
            val primero = lista[0]
            txtTipoFechaIncidencia.text = "${primero.tipo} · ${primero.fecha}"
            txtHoraEntrada.text = "Entrada ${primero.entrada ?: "--:--"}"

            // Cargar los documentos requeridos para este tipo de incidencia (Retardo o Falta)
            presenter.obtenerDocumentosJustificar(primero.tipo)
        }
    }

    // Llamado por clsJustificarPresentador al recibir los documentos requeridos
    fun mostrarDocumentos(lista: List<dataDocumento>) {
        if (lista.isNotEmpty()) {
            txtDoc1.text = "${lista[0].nombre}\n${lista[0].descripcion ?: ""}"
        }
        if (lista.size > 1) {
            txtDoc2.text = "${lista[1].nombre}\n${lista[1].descripcion ?: ""}"
        }
        if (lista.size > 2) {
            txtDoc3.text = "${lista[2].nombre}\n${lista[2].descripcion ?: ""}"
        }
    }

    fun mostrarError(mensaje: String) {
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show()
    }
}
