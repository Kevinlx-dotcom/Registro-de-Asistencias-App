package com.example.appAsistencias.Vista

import android.os.Bundle
import android.view.View
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

        //2. id que llega desde el login
        idTrabajador = intent.getIntExtra("ID_TRABAJADOR", 0)

        //3. inicializar presentador
        presenter = clsInicioPresentador(this)
    }

    override fun onResume() {
        super.onResume()
        presenter.mostrarFechaYMes()
        presenter.cargarInicio(idTrabajador)
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
}
