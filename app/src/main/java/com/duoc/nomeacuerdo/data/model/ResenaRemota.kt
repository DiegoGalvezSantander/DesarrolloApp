package com.duoc.nomeacuerdo.data.model

data class ResenaRemota(
    val id: Int = 0,
    val nombre_evento: String,
    val hora_evento: String,
    val direccion_evento: String,
    val usuario_correo: String,
    val comentario: String?
)