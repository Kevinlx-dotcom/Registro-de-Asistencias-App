package com.example.appAsistencias.Vista

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.appAsistencias.Datos.dataDiaEntradaSalida
import com.example.appAsistencias.R

class DetalleAdapter(private val listaDias: List<dataDiaEntradaSalida>) :
    RecyclerView.Adapter<DetalleAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val txtFechaChecada: TextView = itemView.findViewById(R.id.txtFechaChecada)
        val txtHorasChecada: TextView = itemView.findViewById(R.id.txtHorasChecada)
        val txtEstatusChecada: TextView = itemView.findViewById(R.id.txtEstatusChecada)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val vista = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_detalle, parent, false)
        return ViewHolder(vista)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val dia = listaDias[position]
        holder.txtFechaChecada.text = dia.fecha
        val entrada = dia.entrada.ifEmpty { "--:--" }
        val salida = dia.salida.ifEmpty { "--:--" }
        holder.txtHorasChecada.text = "Entrada $entrada · Salida $salida"
        holder.txtEstatusChecada.text = dia.estatus
    }

    override fun getItemCount(): Int = listaDias.size
}
