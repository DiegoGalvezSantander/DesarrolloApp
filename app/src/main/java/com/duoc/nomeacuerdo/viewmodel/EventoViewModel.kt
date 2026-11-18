
package com.duoc.nomeacuerdo.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.duoc.nomeacuerdo.data.model.EventoRemoto
import com.duoc.nomeacuerdo.repository.EventoRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class EventoViewModel : ViewModel() {

    private val repository = EventoRepository()

    private val _eventos = MutableStateFlow<List<EventoRemoto>>(emptyList())
    val eventos: StateFlow<List<EventoRemoto>> = _eventos

    init {
        cargarEventos()
    }

    fun cargarEventos() {
        viewModelScope.launch {
            try {
                _eventos.value = repository.getEventos()
            } catch (e: Exception) {
                _eventos.value = emptyList()
            }
        }
    }
}
