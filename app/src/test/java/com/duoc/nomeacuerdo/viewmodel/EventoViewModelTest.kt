package com.duoc.nomeacuerdo.viewmodel

import com.duoc.nomeacuerdo.data.model.EventoRemoto
import com.duoc.nomeacuerdo.repository.EventoRepository
import java.io.IOException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class EventoViewModelTest {

    private val dispatcher = StandardTestDispatcher()

    @Before
    fun preparar() {
        Dispatchers.setMain(dispatcher)
    }

    @After
    fun limpiar() {
        Dispatchers.resetMain()
    }

    @Test
    fun `carga los eventos y publica el resultado`() = runTest {
        println("INICIO: comprobar la carga de eventos.")
        println("PREPARACION: el repositorio falso devolvera un concierto; no se usa internet.")
        val eventos = listOf(
            EventoRemoto(1, "Concierto Rock", "20:00", "Banda local", "Av. Siempre Viva 123", "concierto")
        )
        val viewModel = EventoViewModel(FakeEventoRepository { eventos })

        println("ACCION: ejecutar las corrutinas pendientes del init.")
        advanceUntilIdle()

        println("COMPROBACION: el estado debe contener el evento cargado.")
        assertEquals(eventos, viewModel.eventos.value)
        println("CORRECTO: se publicaron los eventos esperados.")
    }

    @Test
    fun `una lista vacia se publica como resultado vacio`() = runTest {
        println("INICIO: comprobar una lista vacia.")
        println("PREPARACION: el repositorio falso no devolvera eventos.")
        val viewModel = EventoViewModel(FakeEventoRepository { emptyList() })

        println("ACCION: ejecutar la carga.")
        advanceUntilIdle()

        println("COMPROBACION: la lista debe quedar vacia y sin error.")
        assertEquals(emptyList<EventoRemoto>(), viewModel.eventos.value)
        println("CORRECTO: se publico una lista vacia sin errores.")
    }

    @Test
    fun `un error de red se maneja sin propagar excepcion`() = runTest {
        println("INICIO: comprobar el manejo de un error de red.")
        println("PREPARACION: el repositorio falso lanzara IOException.")
        val viewModel = EventoViewModel(FakeEventoRepository {
            throw IOException("Sin conexion")
        })

        println("ACCION: ejecutar la carga.")
        advanceUntilIdle()

        println("COMPROBACION: la lista debe quedar vacia, sin crashear.")
        assertEquals(emptyList<EventoRemoto>(), viewModel.eventos.value)
        println("CORRECTO: el error de red se manejo correctamente.")
    }

    private class FakeEventoRepository(
        private val respuesta: suspend () -> List<EventoRemoto>
    ) : EventoRepository {
        override suspend fun getEventos(): List<EventoRemoto> = respuesta()
    }
}