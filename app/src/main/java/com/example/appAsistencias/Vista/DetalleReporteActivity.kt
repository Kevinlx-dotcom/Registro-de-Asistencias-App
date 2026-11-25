package com.example.appAsistencias.Vista

import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.appAsistencias.Datos.dataEntradasSalidas
import com.example.appAsistencias.Datos.dataEstadisticas
import com.example.appAsistencias.Presentador.clsDetalleReportePresentador
import com.example.appAsistencias.R
import java.time.YearMonth

class DetalleReporteActivity : AppCompatActivity() {

    private lateinit var presenter: clsDetalleReportePresentador
    private lateinit var txtTituloDetalle: TextView
    private lateinit var txtNumAsistencias: TextView
    private lateinit var txtNumEntradas: TextView
    private lateinit var txtNumSalidas: TextView
    private lateinit var txtNumRetardos: TextView
    private lateinit var txtNumFaltas: TextView
    private lateinit var txtNumReportes: TextView
    private lateinit var rvDetalleMes: RecyclerView

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
        rvDetalleMes = findViewById(R.id.rvDetalleMes)

        rvDetalleMes.layoutManager = LinearLayoutManager(this)

        // Acción para el botón de regresar
        txtTituloDetalle.setOnClickListener { finish() }

        // 2. Obtener los datos dinámicos enviados desde la pantalla de Asistencias
        idTrabajador = intent.getIntExtra("ID_TRABAJADOR", 0)
        anio = intent.getIntExtra("ANIO", YearMonth.now().year)
        mes = intent.getIntExtra("MES", YearMonth.now().monthValue)

        // Nombre dinámico del mes para el encabezado
        val mesesNombre = arrayOf("", "Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre")
        val nombreMes = if (mes in 1..12) mesesNombre[mes] else "Noviembre"
        txtTituloDetalle.text = "Reporte · $nombreMes $anio"

        // 3. Inicializar el presentador y cargar los datos reales del mes seleccionado
        presenter = clsDetalleReportePresentador(this)
        presenter.obtenerDetalleReporte(idTrabajador, anio, mes)
    }

    fun mostrarDetalleReporte(estadisticas: dataEstadisticas, entradasSalidas: dataEntradasSalidas) {
        val e = estadisticas.estadisticas
        txtNumAsistencias.text = e.asistencias.toString()
        txtNumEntradas.text = e.entradas.toString()
        txtNumSalidas.text = e.salidas.toString()
        txtNumRetardos.text = e.retardos.toString()
        txtNumFaltas.text = e.faltas.toString()
        txtNumReportes.text = e.reportes.toString()

        rvDetalleMes.adapter = DetalleAdapter(entradasSalidas.dias)
    }

    fun mostrarError(mensaje: String) {
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show()
    }
}
