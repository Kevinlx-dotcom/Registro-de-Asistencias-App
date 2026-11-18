package com.example.appcursos.InterfaceApi

import com.example.appcursos.Modelo.dataDescuentos
import com.example.appcursos.Modelo.dataEntradasSalidas
import com.example.appcursos.Modelo.dataEstadisticas
import com.example.appcursos.Modelo.dataIncidencias
import com.example.appcursos.Modelo.dataPermisos
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("obtener_estadisticas.php")
    suspend fun obtenerEstadisticas(
        @Query("periodo") periodo: String
    ): dataEstadisticas

    @GET("obtener_entradas_salidas.php")
    suspend fun obtenerEntradasSalidas(
        @Query("periodo") periodo: String
    ): List<dataEntradasSalidas>

    @GET("obtener_incidencias.php")
    suspend fun obtenerIncidencias(
        @Query("periodo") periodo: String,
        @Query("tipo") tipo: String
    ): List<dataIncidencias>

    @GET("obtener_descuentos.php")
    suspend fun obtenerDescuentos(
        @Query("periodo") periodo: String
    ): List<dataDescuentos>

    @GET("obtener_permisos.php")
    suspend fun obtenerPermisos(
        @Query("periodo") periodo: String
    ): List<dataPermisos>
}
