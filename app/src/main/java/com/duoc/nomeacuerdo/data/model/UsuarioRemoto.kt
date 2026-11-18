package com.duoc.nomeacuerdo.data.model

data class UsuarioRemoto(
    val id: Int = 0,
    val nombre_completo: String,
    val correo: String,
    val password: String,
    val foto_perfil_uri: String? = null
)