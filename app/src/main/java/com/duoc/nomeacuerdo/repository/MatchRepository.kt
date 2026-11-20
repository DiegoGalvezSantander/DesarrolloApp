package com.duoc.nomeacuerdo.repository

import com.duoc.nomeacuerdo.data.model.ResenaRemota
import com.duoc.nomeacuerdo.data.model.UsuarioRemoto
import com.duoc.nomeacuerdo.data.remote.MygosRetrofit

class MatchRepository {

    suspend fun registrarMatch(resena: ResenaRemota) {
        MygosRetrofit.api.crearResena(resena)
    }

    suspend fun obtenerMatches(correo: String): List<ResenaRemota> =
        MygosRetrofit.api.getResenasPorCorreo(correo)

    suspend fun obtenerParticipantes(nombreEvento: String): List<UsuarioRemoto> {
        val resenasDelEvento = MygosRetrofit.api.getResenasPorEvento(nombreEvento)

        val correosUnicos = resenasDelEvento.map { it.usuario_correo }.distinct()

        if (correosUnicos.isEmpty()) return emptyList()

        val todosLosUsuarios = MygosRetrofit.api.getUsuarios()

        return todosLosUsuarios.filter { it.correo in correosUnicos }
    }

    suspend fun borrarMatch(id: Int) {
        MygosRetrofit.api.borrarResena(id)
    }
}