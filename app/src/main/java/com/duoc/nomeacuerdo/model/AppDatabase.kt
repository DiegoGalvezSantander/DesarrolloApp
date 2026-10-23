package com.duoc.nomeacuerdo.model

import androidx.room3.Database
import androidx.room3.RoomDatabase

@Database(entities = [Match::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun matchDao(): MatchDao
}