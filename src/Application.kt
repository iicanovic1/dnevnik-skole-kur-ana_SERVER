package ba.unsa.etf

import ba.unsa.etf.data.collections.User
import ba.unsa.etf.data.registerUser
import ba.unsa.etf.routes.registerRoute
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.gson.*
import io.ktor.response.*
import io.ktor.request.*
import io.ktor.routing.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused") // Referenced in application.conf
@kotlin.jvm.JvmOverloads
fun Application.module(testing: Boolean = false) {
    install(DefaultHeaders) // presreče zahtjeve i dodaje osnovne Headere u njih
    install(CallLogging) // loguje sve zahtjeve i odgovore sa/prema serveru
    install(ContentNegotiation) { // definiše u kojem tipu podatka se nalazi odgovor sa servera
        gson {
            setPrettyPrinting() // hoćemo JSON
        }
    }
    install(Routing){// definisanje URL pristupnih tačaka
        registerRoute()
    }

}

