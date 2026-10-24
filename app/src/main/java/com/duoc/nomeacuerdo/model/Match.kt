package com.duoc.nomeacuerdo.model

import androidx.room3.Entity
import androidx.room3.PrimaryKey

@Entity(tableName = "matches")
data class Match(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val nombreEvento: String,
    val horaEvento: String,
    val direccionEvento: String,
    val usuarioCorreo: String
)