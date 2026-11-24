package com.example.appAsistencias.network

import com.example.appAsistencias.Datos.CambiarClaveRequest
import com.example.appAsistencias.Datos.dataDescuentos
import com.example.appAsistencias.Datos.dataEntradasSalidas
import com.example.appAsistencias.Datos.dataEstadisticas
import com.example.appAsistencias.Datos.LoginRequest
import com.example.appAsistencias.Datos.LoginResponse
import com.example.appAsistencias.Datos.dataIncidencias
import com.example.appAsistencias.Datos.dataInicio
import com.example.appAsistencias.Datos.dataPermisos
import com.example.appAsistencias.Datos.RecuperarRequest
import com.example.appAsistencias.Datos.RecuperarResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

import com.example.appAsistencias.Datos.DataContactosRespuesta
import com.example.appAsistencias.Datos.DataReportesRespuesta
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Streaming
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

    // login
    @POST("login.php")
    suspend fun iniciarSesion(@Body request: LoginRequest): LoginResponse

    // inicio
    @GET("obtener_inicio.php")
    suspend fun obtenerInicio(
        @Query("idTrabajador") idTrabajador: Int
    ): dataInicio

    // recuperar contraseña
    @POST("solicitar_recuperacion.php")
    suspend fun solicitarRecuperacion(@Body request: RecuperarRequest): RecuperarResponse

    @POST("cambiar_contrasena.php")
    suspend fun cambiarContrasena(@Body request: CambiarClaveRequest): RecuperarResponse

    // Contactos y Reportes
    @GET("obtener_contactos.php")
    fun obtenerContactos(): Call<DataContactosRespuesta>

    @GET("obtener_reportes.php")
    fun obtenerReportes(
        @Query("idTrabajador") idTrabajador: Int
    ): Call<DataReportesRespuesta>

    @Streaming
    @GET("descargar_reporte.php")
    fun solicitarDescargaReporte(
        @Query("idTrabajador") idTrabajador: Int,
        @Query("anio") anio: Int,
        @Query("mes") mes: Int,
        @Query("formato") formato: String
    ): Call<ResponseBody>
}
