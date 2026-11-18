package com.duoc.nomeacuerdo.data.remote

import com.duoc.nomeacuerdo.data.model.ResenaRemota
import com.duoc.nomeacuerdo.data.model.UsuarioRemoto
import retrofit2.http.*

interface MygosApi {
    @GET("usuario")
    suspend fun getUsuarios(): List<UsuarioRemoto>

    @GET("resena")
    suspend fun getResenas(): List<ResenaRemota>

    @POST("resena")
    suspend fun crearResena(@Body resena: ResenaRemota): ResenaRemota

    @DELETE("resena/{id}")
    suspend fun borrarResena(@Path("id") id: Int)
}