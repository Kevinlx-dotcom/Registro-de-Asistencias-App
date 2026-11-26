package com.example.appAsistencias.Vista

import android.content.Intent
import android.os.Bundle
import android.view.View
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
    private lateinit var txtTituloPendientes: TextView
    private lateinit var txtTipoFechaIncidencia: TextView
    private lateinit var txtHoraEntrada: TextView
    private lateinit var txtTituloDocumentos: TextView
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
        txtTituloPendientes = findViewById(R.id.txtTituloPendientes)
        txtTipoFechaIncidencia = findViewById(R.id.txtTipoFechaIncidencia)
        txtHoraEntrada = findViewById(R.id.txtHoraEntrada)
        txtTituloDocumentos = findViewById(R.id.txtTituloDocumentos)
        txtDoc1 = findViewById(R.id.txtDoc1)
        txtDoc2 = findViewById(R.id.txtDoc2)
        txtDoc3 = findViewById(R.id.txtDoc3)

        // Listeners de la barra de navegación inferior
        findViewById<View>(R.id.navInicio).setOnClickListener {
            val intent = Intent(this, InicioActivity::class.java)
            intent.putExtra("ID_TRABAJADOR", idTrabajador)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
            startActivity(intent)
            finish()
        }
        findViewById<View>(R.id.navAsistencia).setOnClickListener {
            val intent = Intent(this, AsistenciasActivity::class.java)
            intent.putExtra("ID_TRABAJADOR", idTrabajador)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
            startActivity(intent)
            finish()
        }

        // 2. Obtener el ID del trabajador enviado al iniciar esta Activity
        idTrabajador = intent.getIntExtra("ID_TRABAJADOR", 0)

        // 3. Inicializar el presentador y cargar las incidencias pendientes
        presenter = clsJustificarPresentador(this)
        presenter.obtenerPendientes(idTrabajador)
    }

    // Llamado por clsJustificarPresentador con la incidencia pendiente
    fun mostrarPendiente(total: Int, tipoFecha: String, horaEntrada: String, tipo: String) {
        txtTituloPendientes.text = "PENDIENTES POR JUSTIFICAR ($total)"
        txtTipoFechaIncidencia.text = tipoFecha
        txtHoraEntrada.text = horaEntrada
        txtTituloDocumentos.text = "DOCUMENTOS PARA JUSTIFICAR UN ${tipo.uppercase()}"
    }

    fun mostrarPendienteVacio(total: Int) {
        txtTituloPendientes.text = "PENDIENTES POR JUSTIFICAR ($total)"
        txtTipoFechaIncidencia.text = "Sin pendientes"
        txtHoraEntrada.text = "No tienes incidencias por justificar"
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
