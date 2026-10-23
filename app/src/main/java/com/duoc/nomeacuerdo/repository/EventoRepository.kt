package com.duoc.nomeacuerdo.repository

import com.duoc.nomeacuerdo.model.Evento

object EventoRepository {
    val listaEventos = listOf(
        Evento("Deporte", "12:10", "Únete a nuestro taller de deportes al aire libre."),
        Evento("Cine", "15:15", "Disfruta de una tarde de cine con los clásicos del momento."),
        Evento("Conciertos", "20:10", "Vive la mejor música en vivo con bandas locales.")
    )
}