package com.duoc.nomeacuerdo.repository

import com.duoc.nomeacuerdo.model.Usuario
import com.duoc.nomeacuerdo.model.UsuarioDao

class UsuarioRepository(private val usuarioDao: UsuarioDao) {
    suspend fun registrarUsuario(usuario: Usuario) { usuarioDao.insertarUsuario(usuario) }
    suspend fun login(correo: String, password: String): Usuario? { return usuarioDao.login(correo, password) }
    suspend fun obtenerUsuarioPorCorreo(correo: String): Usuario? { return usuarioDao.obtenerUsuarioPorCorreo(correo) }
    suspend fun actualizarUsuario(usuario: Usuario) { usuarioDao.actualizarUsuario(usuario) }

    suspend fun actualizarEstadoSesion(estado: Boolean) { usuarioDao.actualizarEstadoSesion(estado) }
    suspend fun obtenerUsuarioLogueado(): Usuario? { return usuarioDao.obtenerUsuarioLogueado() }
}