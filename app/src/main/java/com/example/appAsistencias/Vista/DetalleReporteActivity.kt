package com.example.appAsistencias.Vista

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.appAsistencias.Datos.dataEntradasSalidas
import com.example.appAsistencias.Datos.dataEstadisticas
import com.example.appAsistencias.Presentador.clsDetalleReportePresentador
import com.example.appAsistencias.R

class DetalleReporteActivity : AppCompatActivity() {

    private lateinit var presenter: clsDetalleReportePresentador
    private lateinit var txtTituloDetalle: TextView
    private lateinit var txtNumAsistencias: TextView
    private lateinit var txtNumEntradas: TextView
    private lateinit var txtNumSalidas: TextView
    private lateinit var txtNumRetardos: TextView
    private lateinit var txtNumFaltas: TextView
    private lateinit var txtNumReportes: TextView
    private lateinit var btnDescargarPdf: Button
    private lateinit var btnDescargarExcel: Button

    private var idTrabajador = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_detalle_reporte)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // 1. Enlazar componentes del XML
        txtTituloDetalle = findViewById(R.id.txtTituloDetalle)
        txtNumAsistencias = findViewById(R.id.txtNumAsistencias)
        txtNumEntradas = findViewById(R.id.txtNumEntradas)
        txtNumSalidas = findViewById(R.id.txtNumSalidas)
        txtNumRetardos = findViewById(R.id.txtNumRetardos)
        txtNumFaltas = findViewById(R.id.txtNumFaltas)
        txtNumReportes = findViewById(R.id.txtNumReportes)
        btnDescargarPdf = findViewById(R.id.btnDescargarPdf)
        btnDescargarExcel = findViewById(R.id.btnDescargarExcel)

        // 2. Obtener el ID del trabajador enviado
        idTrabajador = intent.getIntExtra("ID_TRABAJADOR", 0)

        // 3. Inicializar el presentador
        presenter = clsDetalleReportePresentador(this)
    }

    override fun onResume() {
        super.onResume()
        presenter.obtenerDetalleReporte("2026-10")
    }

    fun mostrarDetalleReporte(estadisticas: dataEstadisticas, entradasSalidas: List<dataEntradasSalidas>) {
        txtNumAsistencias.text = estadisticas.asistencias.toString()
        txtNumEntradas.text = estadisticas.asistencias.toString()
        txtNumSalidas.text = estadisticas.asistencias.toString()
        txtNumRetardos.text = estadisticas.retardos.toString()
        txtNumFaltas.text = estadisticas.faltas.toString()
        txtNumReportes.text = estadisticas.descuentos.toString()
    }

    fun mostrarError(mensaje: String) {
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show()
    }
}
