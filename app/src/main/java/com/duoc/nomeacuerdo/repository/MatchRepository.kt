package com.duoc.nomeacuerdo.repository

import com.duoc.nomeacuerdo.model.Match
import com.duoc.nomeacuerdo.model.MatchDao

class MatchRepository(private val matchDao: MatchDao) {
    suspend fun registrarMatch(match: Match) { matchDao.insertarMatch(match) }
    suspend fun obtenerMatches(correo: String): List<Match> { return matchDao.obtenerMatchesPorUsuario(correo) }
}