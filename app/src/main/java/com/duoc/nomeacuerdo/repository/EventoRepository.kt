package com.duoc.nomeacuerdo.repository

import com.duoc.nomeacuerdo.data.model.EventoRemoto
import com.duoc.nomeacuerdo.data.remote.MygosRetrofit

class EventoRepository {

    suspend fun getEventos(): List<EventoRemoto> =
        MygosRetrofit.api.getEventos()
}
