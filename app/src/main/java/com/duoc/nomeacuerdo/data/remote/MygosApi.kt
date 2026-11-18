package com.duoc.nomeacuerdo.data.remote
import com.duoc.nomeacuerdo.data.model.ResenaRemota
import com.duoc.nomeacuerdo.data.model.EventoRemoto
import com.duoc.nomeacuerdo.data.model.UsuarioRemoto
import retrofit2.http.*

interface MygosApi {
    @GET("usuario")
    suspend fun getUsuarios(): List<UsuarioRemoto>

    @POST("usuario")
    suspend fun registrarUsuario(
        @Body usuario: UsuarioRemoto
    ): UsuarioRemoto

    @PUT("usuario/{id}")
    suspend fun actualizarUsuario(
        @Path("id") id: Int,
        @Body usuario: UsuarioRemoto
    ): UsuarioRemoto

    @GET("evento")
    suspend fun getEventos(): List<EventoRemoto>

    @GET("resena")
    suspend fun getResenas(): List<ResenaRemota>

    @POST("resena")
    suspend fun crearResena(
        @Body resena: ResenaRemota
    ): ResenaRemota
}

