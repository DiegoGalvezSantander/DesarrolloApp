package com.duoc.nomeacuerdo.repository

import com.duoc.nomeacuerdo.data.remote.MygosApi
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import retrofit2.HttpException
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class EventoRepositoryTest {

    private lateinit var servidor: MockWebServer
    private lateinit var repositorio: EventoRepository

    @Before
    fun preparar() {
        servidor = MockWebServer()
        servidor.start()
        val api = Retrofit.Builder()
            .baseUrl(servidor.url("/"))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MygosApi::class.java)
        repositorio = EventoRepositoryImpl(api)
    }

    @After
    fun limpiar() {
        servidor.shutdown()
    }

    @Test
    fun `consulta el endpoint de eventos y convierte la respuesta`() = runTest {
        println("INICIO: comprobar la solicitud y la conversion del JSON.")
        println("PREPARACION: el servidor local simulado respondera con un concierto.")
        servidor.enqueue(
            MockResponse().setBody(
                """[{"id":1,"titulo":"Concierto Rock","hora":"20:00","descripcion":"Banda local","direccion":"Av. Siempre Viva 123","imagen":"concierto"}]"""
            )
        )

        println("ACCION: el repositorio llama al servidor local, no al servicio real.")
        val resultado = repositorio.getEventos()

        println("COMPROBACION: el JSON debe convertirse en un evento y usar GET /evento.")
        assertEquals(1, resultado.size)
        assertEquals("Concierto Rock", resultado[0].titulo)
        assertEquals("20:00", resultado[0].hora)
        assertEquals("concierto", resultado[0].imagen)

        val solicitud = servidor.takeRequest()
        assertEquals("GET", solicitud.method)
        assertEquals("/evento", solicitud.path)
        println("CORRECTO: la solicitud y el evento son los esperados.")
    }

    @Test
    fun `propaga errores http del servidor`() = runTest {
        println("INICIO: comprobar la propagacion de errores HTTP.")
        println("PREPARACION: el servidor local simulado respondera con HTTP 500.")
        servidor.enqueue(MockResponse().setResponseCode(500))

        println("ACCION: solicitar eventos al servidor local.")
        try {
            repositorio.getEventos()
            throw AssertionError("Se esperaba un HttpException")
        } catch (error: HttpException) {
            println("COMPROBACION: el error recibido debe tener codigo 500.")
            assertEquals(500, error.code())
        }
        println("CORRECTO: el repositorio propago el error HTTP 500.")
    }
}