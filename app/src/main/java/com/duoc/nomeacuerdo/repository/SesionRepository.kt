package com.duoc.nomeacuerdo.repository

import android.content.Context
import android.content.SharedPreferences

class SesionRepository(context: Context) {

    private val sesionPrefs: SharedPreferences =
        context.getSharedPreferences("mygos_session", Context.MODE_PRIVATE)

    fun guardarCorreoSesion(correo: String) {
        sesionPrefs.edit().putString("correo_logueado", correo).apply()
    }

    fun obtenerCorreoSesion(): String? {
        return sesionPrefs.getString("correo_logueado", null)
    }

    fun borrarSesion() {
        sesionPrefs.edit().remove("correo_logueado").apply()
    }
}