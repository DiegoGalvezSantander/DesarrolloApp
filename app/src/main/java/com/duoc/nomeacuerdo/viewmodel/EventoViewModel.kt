package com.duoc.nomeacuerdo.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.duoc.nomeacuerdo.data.model.EventoRemoto
import com.duoc.nomeacuerdo.repository.EventoRepository
import com.duoc.nomeacuerdo.repository.EventoRepositoryImpl
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class EventoViewModel(
    private val repository: EventoRepository = EventoRepositoryImpl()
) : ViewModel() {

    private val _eventos = MutableStateFlow<List<EventoRemoto>>(emptyList())
    val eventos: StateFlow<List<EventoRemoto>> = _eventos

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    init {
        cargarEventos()
    }

    fun cargarEventos(forzarRecarga: Boolean = false) {
        if (!forzarRecarga && _eventos.value.isNotEmpty()) return

        viewModelScope.launch {
            try {
                _eventos.value = repository.getEventos()
                _error.value = null
            } catch (e: Exception) {
                if (_eventos.value.isEmpty()) {
                    _error.value = "Error de red al cargar eventos."
                }
            }
        }
    }
}