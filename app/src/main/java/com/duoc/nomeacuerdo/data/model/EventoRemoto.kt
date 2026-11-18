package com.duoc.nomeacuerdo.data.model

data class EventoRemoto(
    val id: Int = 0,
    val titulo: String,
    val hora: String,
    val descripcion: String,
    val direccion: String,
    val imagen: String
)
