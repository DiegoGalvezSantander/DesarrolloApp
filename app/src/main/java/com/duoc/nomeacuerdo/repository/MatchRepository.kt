package com.duoc.nomeacuerdo.repository

import com.duoc.nomeacuerdo.data.model.ResenaRemota
import com.duoc.nomeacuerdo.data.model.UsuarioRemoto
import com.duoc.nomeacuerdo.data.remote.MygosRetrofit

class MatchRepository {
    suspend fun registrarMatch(resena: ResenaRemota) {
        MygosRetrofit.api.crearResena(resena)
    }

    suspend fun obtenerMatches(correo: String): List<ResenaRemota> =
        MygosRetrofit.api.getResenas().filter { it.usuario_correo == correo }

    suspend fun obtenerParticipantes(nombreEvento: String): List<UsuarioRemoto> {
        val resenas = MygosRetrofit.api.getResenas().filter { it.nombre_evento == nombreEvento }
        val correos = resenas.map { it.usuario_correo }
        return MygosRetrofit.api.getUsuarios().filter { it.correo in correos }
    }
}