package com.example.appcursos.Modelo

import com.example.appcursos.InterfaceApi.ApiService
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import java.lang.reflect.Type

object RetrofitCliente {
    private const val BASE_URL = "https://cbta5.4cuthh.com/cbta_api/"

    private val jsonConfig = Json {
        ignoreUnknownKeys = true
        coerceInputValues = true
        isLenient = true
    }

    private val gson = GsonConverterFactory.create()

    // Gson solamente para nuestras respuestas Java; Kotlinx para el resto.
    private val conversorModelosJava = object : Converter.Factory() {
        override fun responseBodyConverter(
            type: Type,
            annotations: Array<Annotation>,
            retrofit: Retrofit
        ): Converter<ResponseBody, *>? {
            return if (
                type == DataContactosRespuesta::class.java ||
                type == DataReportesRespuesta::class.java
            ) {
                gson.responseBodyConverter(type, annotations, retrofit)
            } else {
                null
            }
        }
    }

    // Mismo servicio de siempre, sin crear otro RetrofitCliente.
    val apiService: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(conversorModelosJava)
            .addConverterFactory(
                jsonConfig.asConverterFactory("application/json".toMediaType())
            )
            .build()
            .create(ApiService::class.java)
    }
}
