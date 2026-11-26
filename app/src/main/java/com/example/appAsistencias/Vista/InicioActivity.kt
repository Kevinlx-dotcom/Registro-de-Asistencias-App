package com.example.appAsistencias.Vista

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.PopupMenu
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.appAsistencias.Presentador.clsInicioPresentador
import com.example.appAsistencias.R

class InicioActivity : AppCompatActivity() {
    private lateinit var presenter: clsInicioPresentador
    private lateinit var txtBienvenida: TextView
    private lateinit var txtFecha: TextView
    private lateinit var vwAviso: View
    private lateinit var txtAviso: TextView
    private lateinit var txtJustificarAhora: TextView
    private lateinit var txtEntradaHora: TextView
    private lateinit var txtEntradaEstatus: TextView
    private lateinit var txtSalidaHora: TextView
    private lateinit var txtSalidaEstatus: TextView
    private lateinit var txtEntradasSalidas: TextView
    private lateinit var txtRetardos: TextView
    private lateinit var txtMenu: TextView
    private var idTrabajador = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_inicio)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        //1.Enlazar componentes
        txtBienvenida = findViewById(R.id.txtBienvenida)
        txtFecha = findViewById(R.id.txtFecha)
        vwAviso = findViewById(R.id.vwAviso)
        txtAviso = findViewById(R.id.txtAviso)
        txtJustificarAhora = findViewById(R.id.txtJustificarAhora)
        txtEntradaHora = findViewById(R.id.txtEntradaHora)
        txtEntradaEstatus = findViewById(R.id.txtEntradaEstatus)
        txtSalidaHora = findViewById(R.id.txtSalidaHora)
        txtSalidaEstatus = findViewById(R.id.txtSalidaEstatus)
        txtEntradasSalidas = findViewById(R.id.txtEntradasSalidas)
        txtRetardos = findViewById(R.id.txtRetardos)
        txtMenu = findViewById(R.id.txtMenu)

        //2. id que llega desde el login
        idTrabajador = intent.getIntExtra("ID_TRABAJADOR", 0)

        //3. inicializar presentador
        presenter = clsInicioPresentador(this)

        //4.Listeners
        configurarListeners()
    }

    private fun configurarListeners() {
        txtMenu.setOnClickListener(this::ejecutarMenu)
        findViewById<View>(R.id.txtContactos).setOnClickListener { abrirContactos("contacto") }
        findViewById<View>(R.id.txtAdministracion).setOnClickListener { abrirContactos("administracion") }
        findViewById<View>(R.id.txtDirectivos).setOnClickListener { abrirContactos("directivo") }
        findViewById<View>(R.id.navAsistencia).setOnClickListener { abrirAsistencias(0) }
        findViewById<View>(R.id.navJustificar).setOnClickListener { abrirJustificar() }
        txtJustificarAhora.setOnClickListener { abrirJustificar() }
        txtEntradasSalidas.setOnClickListener { abrirAsistencias(0) }
        txtRetardos.setOnClickListener { abrirAsistencias(1) }
    }

    override fun onResume() {
        super.onResume()
        presenter.mostrarFechaYMes()
        presenter.cargarInicio(idTrabajador)
    }

    private fun abrirContactos(categoria: String) {
        startActivity(Intent(this, ContactosActivity::class.java).apply {
            putExtra("ID_TRABAJADOR", idTrabajador)
            putExtra("CATEGORIA", categoria)
        })
    }

    // Muestra el menú debajo de los 3 puntos
    fun ejecutarMenu(v: View) {
        val menu = PopupMenu(this, v)
        menu.menu.add("Reportes de asistencia")
        menu.menu.add("Cerrar sesión")
        menu.setOnMenuItemClickListener { opcion ->
            presenter.elegirOpcionMenu(opcion.title.toString())
            true
        }
        menu.show()
    }

    fun abrirReportes() {
        val intent = Intent(this, ReportesActivity::class.java)
        intent.putExtra("ID_TRABAJADOR", idTrabajador)
        startActivity(intent)
    }

    fun mostrarFecha(fecha: String, entradasSalidas: String, retardos: String) {
        txtFecha.text = fecha
        txtEntradasSalidas.text = entradasSalidas
        txtRetardos.text = retardos
    }

    fun mostrarBienvenida(texto: String) {
        txtBienvenida.text = texto
    }

    fun mostrarAviso(texto: String) {
        txtAviso.text = texto
        vwAviso.visibility = View.VISIBLE
        txtAviso.visibility = View.VISIBLE
        txtJustificarAhora.visibility = View.VISIBLE
    }

    fun ocultarAviso() {
        vwAviso.visibility = View.GONE
        txtAviso.visibility = View.GONE
        txtJustificarAhora.visibility = View.GONE
    }

    fun mostrarEntrada(hora: String, estatus: String, esPuntual: Boolean) {
        txtEntradaHora.text = hora
        txtEntradaEstatus.text = estatus
        txtEntradaEstatus.setTextColor(
            getColor(if (esPuntual) R.color.green_primary else R.color.orange_warning)
        )
    }

    fun mostrarSalida(hora: String) {
        txtSalidaHora.text = hora
        txtSalidaEstatus.text = "Registrada"
    }

    fun mostrarError(mensaje: String) {
        Toast.makeText(this, mensaje, Toast.LENGTH_LONG).show()
    }

    fun abrirAsistencias(pestana: Int) {
        val intent = Intent(this, AsistenciasActivity::class.java)
        intent.putExtra("ID_TRABAJADOR", idTrabajador)
        intent.putExtra("TAB", pestana)
        startActivity(intent)
    }

    fun abrirJustificar() {
        val intent = Intent(this, JustificarActivity::class.java)
        intent.putExtra("ID_TRABAJADOR", idTrabajador)
        startActivity(intent)
    }

    fun abrirLogin() {
        val intent = Intent(this, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }
}
