package com.example.appAsistencias.Vista

import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.appAsistencias.Datos.dataFilaAsistencia
import com.example.appAsistencias.Presentador.clsAsistenciasPresentador
import com.example.appAsistencias.R

class AsistenciasActivity : AppCompatActivity() {
    private lateinit var presenter: clsAsistenciasPresentador
    private lateinit var txtMes: TextView
    private lateinit var txtResumen: TextView
    private lateinit var rvAsistencias: RecyclerView
    private lateinit var vwIndicadorTab: View
    private lateinit var pestanas: List<TextView>
    private var idTrabajador = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_asistencias)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.headerContainer)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(v.paddingLeft, systemBars.top + 16, v.paddingRight, v.paddingBottom)
            insets
        }

        //1.Enlazar componentes
        txtMes = findViewById(R.id.tvMonthYear)
        txtResumen = findViewById(R.id.tvSummary)
        rvAsistencias = findViewById(R.id.rvAsistencias)
        vwIndicadorTab = findViewById(R.id.vwIndicadorTab)
        pestanas = listOf(
            findViewById(R.id.txtTabEntradasSalidas),
            findViewById(R.id.tabRetardos),
            findViewById(R.id.tabFaltas),
            findViewById(R.id.tabDescuentos)
        )

        //2. id que llega desde el inicio
        idTrabajador = intent.getIntExtra("ID_TRABAJADOR", 0)

        //3. inicializar presentador
        presenter = clsAsistenciasPresentador(this)

        //4.Listeners
        findViewById<View>(R.id.btnPrevMonth).setOnClickListener { presenter.cambiarMes(-1, idTrabajador) }
        findViewById<View>(R.id.btnNextMonth).setOnClickListener { presenter.cambiarMes(1, idTrabajador) }
        findViewById<View>(R.id.tabEntradasSalidas).setOnClickListener { presenter.cambiarPestana(0, idTrabajador) }
        findViewById<View>(R.id.tabRetardos).setOnClickListener { presenter.cambiarPestana(1, idTrabajador) }
        findViewById<View>(R.id.tabFaltas).setOnClickListener { presenter.cambiarPestana(2, idTrabajador) }
        findViewById<View>(R.id.tabDescuentos).setOnClickListener { presenter.cambiarPestana(3, idTrabajador) }
        findViewById<View>(R.id.navInicio).setOnClickListener { finish() }
    }

    override fun onResume() {
        super.onResume()
        // desde el inicio se puede abrir directo en Retardos (TAB = 1)
        presenter.cambiarPestana(intent.getIntExtra("TAB", 0), idTrabajador)
        intent.removeExtra("TAB")
    }

    fun mostrarMes(texto: String) {
        txtMes.text = texto
    }

    fun mostrarResumen(texto: String) {
        txtResumen.text = texto
    }

    fun mostrarLista(filas: List<dataFilaAsistencia>) {
        rvAsistencias.adapter = AsistenciaAdapter(filas)
    }

    // Pinta de verde la pestaña activa
    fun marcarPestana(numero: Int) {
        for (i in pestanas.indices) {
            pestanas[i].setTextColor(
                getColor(if (i == numero) R.color.green_primary else R.color.text_secondary)
            )
        }
        vwIndicadorTab.visibility = if (numero == 0) View.VISIBLE else View.INVISIBLE
    }

    fun mostrarError(mensaje: String) {
        Toast.makeText(this, mensaje, Toast.LENGTH_LONG).show()
    }
}
