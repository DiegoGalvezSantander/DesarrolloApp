package com.duoc.nomeacuerdo.repository

import com.duoc.nomeacuerdo.data.model.ResenaRemota
import com.duoc.nomeacuerdo.data.model.UsuarioRemoto
import com.duoc.nomeacuerdo.data.remote.MygosRetrofit

class MatchRepository {

    suspend fun registrarMatch(resena: ResenaRemota) {
        MygosRetrofit.api.crearResena(resena)
    }

    suspend fun obtenerMatches(correo: String): List<ResenaRemota> {
        val todasLasResenas = MygosRetrofit.api.getTodasLasResenas(usuarioCorreo = correo)
        val correoLimpio = correo.trim().lowercase()
        return todasLasResenas.filter { it.usuario_correo.trim().lowercase() == correoLimpio }
    }

    suspend fun obtenerParticipantes(nombreEvento: String): List<UsuarioRemoto> {
        val todasLasResenas = MygosRetrofit.api.getTodasLasResenas(nombreEvento = nombreEvento)
        val eventoLimpio = nombreEvento.trim().lowercase()

        val resenasDelEvento = todasLasResenas.filter { it.nombre_evento.trim().lowercase() == eventoLimpio }
        val correosUnicos = resenasDelEvento.map { it.usuario_correo.trim().lowercase() }.distinct()

        if (correosUnicos.isEmpty()) return emptyList()

        val todosLosUsuarios = MygosRetrofit.api.getUsuarios()
        return todosLosUsuarios.filter { it.correo.trim().lowercase() in correosUnicos }
    }

    suspend fun borrarMatch(id: Int) {
        MygosRetrofit.api.borrarResena(id)
    }
}