package com.duoc.nomeacuerdo.model

import androidx.room3.Entity
import androidx.room3.PrimaryKey

@Entity(tableName = "matches")
data class Match(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val nombreEvento: String,
    val fechaMatch: String,
    val usuarioCorreo: String
)