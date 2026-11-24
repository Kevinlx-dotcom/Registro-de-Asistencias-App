package com.example.appAsistencias.Vista

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.appAsistencias.Datos.dataFilaAsistencia
import com.example.appAsistencias.R

class AsistenciaAdapter(private val filas: List<dataFilaAsistencia>) :
    RecyclerView.Adapter<AsistenciaAdapter.FilaViewHolder>() {

    class FilaViewHolder(vista: View) : RecyclerView.ViewHolder(vista) {
        val txtDia: TextView = vista.findViewById(R.id.tvDay)
        val txtMes: TextView = vista.findViewById(R.id.tvMonth)
        val txtLinea1: TextView = vista.findViewById(R.id.tvEntrada)
        val txtLinea2: TextView = vista.findViewById(R.id.tvSalida)
        val txtJustificar: TextView = vista.findViewById(R.id.tvJustificarAhora)
        val txtEstatus: TextView = vista.findViewById(R.id.tvStatus)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilaViewHolder {
        val vista = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_asistencia, parent, false)
        return FilaViewHolder(vista)
    }

    override fun onBindViewHolder(holder: FilaViewHolder, position: Int) {
        val fila = filas[position]
        holder.txtDia.text = fila.dia
        holder.txtMes.text = fila.mes
        holder.txtLinea1.text = fila.linea1
        holder.txtLinea2.text = fila.linea2
        holder.txtEstatus.text = fila.estatus
        holder.txtEstatus.setTextColor(
            holder.itemView.context.getColor(
                if (fila.esPuntual) R.color.green_primary else R.color.orange_warning
            )
        )
        holder.txtJustificar.visibility = if (fila.puedeJustificar) View.VISIBLE else View.GONE
    }

    override fun getItemCount(): Int = filas.size
}
