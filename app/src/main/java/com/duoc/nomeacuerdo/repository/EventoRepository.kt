package com.duoc.nomeacuerdo.repository

import com.duoc.nomeacuerdo.data.model.EventoRemoto
import com.duoc.nomeacuerdo.data.remote.MygosApi
import com.duoc.nomeacuerdo.data.remote.MygosRetrofit

interface EventoRepository {
    suspend fun getEventos(): List<EventoRemoto>
}

class EventoRepositoryImpl(
    private val api: MygosApi = MygosRetrofit.api
) : EventoRepository {
    override suspend fun getEventos(): List<EventoRemoto> = api.getEventos()
}