package com.duoc.nomeacuerdo.model

import androidx.room3.Dao
import androidx.room3.Insert
import androidx.room3.Query

@Dao
interface MatchDao {
    @Insert
    suspend fun insertarMatch(match: Match)

    @Query("SELECT * FROM matches WHERE usuarioCorreo = :correo")
    suspend fun obtenerMatchesPorUsuario(correo: String): List<Match>
}