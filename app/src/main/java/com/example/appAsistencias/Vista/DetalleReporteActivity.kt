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
    private var anio = 0
    private var mes = 0

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

        // 2. Obtener los datos enviados desde Reportes
        idTrabajador = intent.getIntExtra("ID_TRABAJADOR", 0)
        anio = intent.getIntExtra("ANIO", 0)
        mes = intent.getIntExtra("MES", 0)

        // 3. Inicializar el presentador y cargar el detalle
        presenter = clsDetalleReportePresentador(this)
        presenter.obtenerDetalleReporte(idTrabajador, anio, mes)
    }

    fun mostrarDetalleReporte(estadisticas: dataEstadisticas, entradasSalidas: dataEntradasSalidas) {
        txtNumAsistencias.text = estadisticas.estadisticas.asistencias.toString()
        txtNumEntradas.text = estadisticas.estadisticas.entradas.toString()
        txtNumSalidas.text = estadisticas.estadisticas.salidas.toString()
        txtNumRetardos.text = estadisticas.estadisticas.retardos.toString()
        txtNumFaltas.text = estadisticas.estadisticas.faltas.toString()
        txtNumReportes.text = estadisticas.estadisticas.reportes.toString()
    }

    fun mostrarError(mensaje: String) {
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show()
    }
}