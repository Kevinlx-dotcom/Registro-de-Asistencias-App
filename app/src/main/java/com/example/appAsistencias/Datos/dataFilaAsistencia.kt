package com.example.appAsistencias.Datos

data class dataFilaAsistencia(
    val dia: String,
    val mes: String,
    val linea1: String,
    val linea2: String,
    val estatus: String,
    val esPuntual: Boolean,
    val puedeJustificar: Boolean
)
