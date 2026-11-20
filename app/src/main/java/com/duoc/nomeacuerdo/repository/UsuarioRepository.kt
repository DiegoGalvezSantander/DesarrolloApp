package com.duoc.nomeacuerdo.repository

import com.duoc.nomeacuerdo.data.model.UsuarioRemoto
import com.duoc.nomeacuerdo.data.remote.MygosRetrofit
import retrofit2.HttpException

class UsuarioRepository {

    suspend fun registrarUsuario(usuario: UsuarioRemoto): UsuarioRemoto {
        val usuarioParaCrear = usuario.copy(id = 0, foto_perfil_uri = null)
        return MygosRetrofit.api.registrarUsuario(usuarioParaCrear)
    }

    suspend fun login(correo: String, password: String): UsuarioRemoto? {
        return try {
            MygosRetrofit.api.loginUsuario(correo, password).firstOrNull()
        } catch (e: Exception) {
            null
        }
    }

    suspend fun obtenerUsuarioPorCorreo(correo: String): UsuarioRemoto? {
        return try {
            MygosRetrofit.api.getUsuarioPorCorreo(correo).firstOrNull()
        } catch (e: Exception) {
            null
        }
    }

    suspend fun actualizarUsuario(usuario: UsuarioRemoto): UsuarioRemoto =
        MygosRetrofit.api.actualizarUsuario(usuario.id, usuario)
}