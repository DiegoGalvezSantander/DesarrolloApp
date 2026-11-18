package com.duoc.nomeacuerdo.repository

import com.duoc.nomeacuerdo.data.model.UsuarioRemoto
import com.duoc.nomeacuerdo.data.remote.MygosRetrofit

class UsuarioRepository {
    suspend fun registrarUsuario(usuario: UsuarioRemoto): UsuarioRemoto = MygosRetrofit.api.registrarUsuario(usuario)
    suspend fun login(correo: String, password: String): UsuarioRemoto? = MygosRetrofit.api.getUsuarios().find { it.correo == correo && it.password == password }
    suspend fun obtenerUsuarioPorCorreo(correo: String): UsuarioRemoto? = MygosRetrofit.api.getUsuarios().find { it.correo == correo }
    suspend fun actualizarUsuario(usuario: UsuarioRemoto): UsuarioRemoto = MygosRetrofit.api.actualizarUsuario(usuario.id, usuario)
}