package com.duoc.nomeacuerdo.repository

import com.duoc.nomeacuerdo.model.Evento

object EventoRepository {
    val listaEventos = listOf(
        Evento(
            "Deporte",
            "12:10",
            "Únete a nuestro taller de deportes al aire libre. Conoce gente nueva, ejercítate y pasa un buen momento.",
            "Av. Vicuña Mackenna 4860, Macul"
        ),
        Evento(
            "Cine",
            "15:15",
            "Disfruta de una tarde de cine con los clásicos del momento. Incluye entrada, palomitas y bebida.",
            "Costanera Center, Providencia"
        ),
        Evento(
            "Conciertos",
            "20:10",
            "Vive la mejor música en vivo con bandas locales y artistas invitados. Un espacio para bailar y compartir.",
            "Parque O'Higgins, Santiago"
        )
    )
}