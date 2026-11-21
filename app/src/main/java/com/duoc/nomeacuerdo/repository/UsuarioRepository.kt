package com.duoc.nomeacuerdo.repository

import com.duoc.nomeacuerdo.data.model.UsuarioRemoto
import com.duoc.nomeacuerdo.data.remote.MygosRetrofit

class UsuarioRepository {

    suspend fun registrarUsuario(usuario: UsuarioRemoto): UsuarioRemoto {
        val usuarioParaCrear = usuario.copy(id = 0, foto_perfil_uri = null)
        return MygosRetrofit.api.registrarUsuario(usuarioParaCrear)
    }

    suspend fun login(correo: String, password: String): UsuarioRemoto? {
        return try {
            val listaUsuarios = MygosRetrofit.api.loginUsuario(correo, password)

            val correoLimpio = correo.trim().lowercase()

            listaUsuarios.find {
                it.correo.trim().lowercase() == correoLimpio && it.password == password
            }
        } catch (e: Exception) {
            null
        }
    }

    suspend fun obtenerUsuarioPorCorreo(correo: String): UsuarioRemoto? {
        return try {
            val listaUsuarios = MygosRetrofit.api.getUsuarioPorCorreo(correo)
            val correoLimpio = correo.trim().lowercase()
            listaUsuarios.find { it.correo.trim().lowercase() == correoLimpio }
        } catch (e: Exception) {
            null
        }
    }

    suspend fun actualizarUsuario(usuario: UsuarioRemoto): UsuarioRemoto =
        MygosRetrofit.api.actualizarUsuario(usuario.id, usuario)
}