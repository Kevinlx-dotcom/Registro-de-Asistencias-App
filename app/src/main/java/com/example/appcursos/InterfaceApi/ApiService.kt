package com.example.appcursos.InterfaceApi

import com.example.appcursos.Modelo.dataContacto
import com.example.appcursos.Modelo.dataDocumento
import com.example.appcursos.Modelo.dataChecada
import com.example.appcursos.Modelo.dataDescuento
import com.example.appcursos.Modelo.dataIncidencia
import com.example.appcursos.Modelo.dataInicio
import com.example.appcursos.Modelo.dataPendiente
import com.example.appcursos.Modelo.dataPermiso
import com.example.appcursos.Modelo.dataReporte
import com.example.appcursos.Modelo.dataReporteDetalle
import com.example.appcursos.Modelo.dataRespuesta
import com.example.appcursos.Modelo.dataRespuestaSimple
import com.example.appcursos.Modelo.dataResumenMes
import com.example.appcursos.Modelo.dataSesion
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query
import retrofit2.http.Streaming

interface ApiService {

    @FormUrlEncoded
    @POST("login.php")
    suspend fun iniciarSesion(
        @Field("correo") correo: String,
        @Field("contrasena") contrasena: String
    ): dataRespuesta<dataSesion>

    @GET("obtener_inicio.php")
    suspend fun obtenerInicio(): dataRespuesta<dataInicio>

    @GET("obtener_estadisticas.php")
    suspend fun obtenerEstadisticas(
        @Query("periodo") periodo: String
    ): dataRespuesta<dataResumenMes>

    @GET("obtener_entradas_salidas.php")
    suspend fun obtenerEntradasSalidas(
        @Query("periodo") periodo: String
    ): dataRespuesta<List<dataChecada>>

    @GET("obtener_incidencias.php")
    suspend fun obtenerIncidencias(
        @Query("periodo") periodo: String,
        @Query("tipo") tipo: String
    ): dataRespuesta<List<dataIncidencia>>

    @GET("obtener_descuentos.php")
    suspend fun obtenerDescuentos(
        @Query("periodo") periodo: String
    ): dataRespuesta<List<dataDescuento>>

    @GET("obtener_permisos.php")
    suspend fun obtenerPermisos(
        @Query("periodo") periodo: String
    ): dataRespuesta<List<dataPermiso>>

    @GET("obtener_pendientes.php")
    suspend fun obtenerPendientes(): dataRespuesta<List<dataPendiente>>

    @GET("obtener_documentos_justificar.php")
    suspend fun obtenerDocumentosJustificar(
        @Query("tipo_incidencia") tipoIncidencia: String
    ): dataRespuesta<List<dataDocumento>>

    @GET("obtener_reporte_detalle.php")
    suspend fun obtenerReporteDetalle(
        @Query("periodo") periodo: String
    ): dataRespuesta<dataReporteDetalle>

    @GET("obtener_contactos.php")
    suspend fun obtenerContactos(): dataRespuesta<List<dataContacto>>

    @GET("obtener_reportes.php")
    suspend fun obtenerReportes(
        @Query("periodo") periodo: String
    ): dataRespuesta<List<dataReporte>>

    @Streaming
    @GET("descargar_reporte.php")
    suspend fun descargarReporte(
        @Query("periodo") periodo: String,
        @Query("formato") formato: String
    ): Response<ResponseBody>
}
