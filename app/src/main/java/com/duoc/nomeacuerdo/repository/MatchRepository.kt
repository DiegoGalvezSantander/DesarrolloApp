package com.duoc.nomeacuerdo.repository

import com.duoc.nomeacuerdo.data.model.ResenaRemota
import com.duoc.nomeacuerdo.data.remote.MygosRetrofit

class MatchRepository {

    suspend fun registrarMatch(resena: ResenaRemota) {
        MygosRetrofit.api.crearResena(resena)
    }

    suspend fun obtenerMatches(correo: String): List<ResenaRemota> =
        MygosRetrofit.api.getResenas().filter {
            it.usuario_correo == correo
        }
}

