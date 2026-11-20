package com.duoc.nomeacuerdo.repository

import com.duoc.nomeacuerdo.data.model.UsuarioRemoto
import com.duoc.nomeacuerdo.data.remote.MygosRetrofit

class UsuarioRepository {

    suspend fun registrarUsuario(usuario: UsuarioRemoto): UsuarioRemoto =
        MygosRetrofit.api.registrarUsuario(usuario)

    suspend fun login(correo: String, password: String): UsuarioRemoto? =
        MygosRetrofit.api.loginUsuario(correo, password).firstOrNull()

    suspend fun obtenerUsuarioPorCorreo(correo: String): UsuarioRemoto? =
        MygosRetrofit.api.getUsuarioPorCorreo(correo).firstOrNull()

    suspend fun actualizarUsuario(usuario: UsuarioRemoto): UsuarioRemoto =
        MygosRetrofit.api.actualizarUsuario(usuario.id, usuario)
}