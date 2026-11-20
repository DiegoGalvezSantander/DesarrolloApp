package com.duoc.nomeacuerdo.data.remote

import com.duoc.nomeacuerdo.data.model.EventoRemoto
import com.duoc.nomeacuerdo.data.model.ResenaRemota
import com.duoc.nomeacuerdo.data.model.UsuarioRemoto
import retrofit2.http.*

interface MygosApi {
    @GET("usuario")
    suspend fun loginUsuario(
        @Query("correo") correo: String,
        @Query("password") password: String
    ): List<UsuarioRemoto>

    @GET("usuario")
    suspend fun getUsuarioPorCorreo(@Query("correo") correo: String): List<UsuarioRemoto>

    @GET("usuario")
    suspend fun getUsuarios(): List<UsuarioRemoto>

    @POST("usuario")
    suspend fun registrarUsuario(@Body usuario: UsuarioRemoto): UsuarioRemoto

    @PUT("usuario/{id}")
    suspend fun actualizarUsuario(@Path("id") id: Int, @Body usuario: UsuarioRemoto): UsuarioRemoto

    @GET("resena")
    suspend fun getTodasLasResenas(
        @Query("usuario_correo") usuarioCorreo: String? = null,
        @Query("nombre_evento") nombreEvento: String? = null
    ): List<ResenaRemota>

    @POST("resena")
    suspend fun crearResena(@Body resena: ResenaRemota): ResenaRemota

    @DELETE("resena/{id}")
    suspend fun borrarResena(@Path("id") id: Int)

    @GET("evento")
    suspend fun getEventos(): List<EventoRemoto>
}