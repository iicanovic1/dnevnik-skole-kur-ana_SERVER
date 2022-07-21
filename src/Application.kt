package ba.unsa.etf

import ba.unsa.etf.data.checkPasswordForEmail
import ba.unsa.etf.data.collections.User
import ba.unsa.etf.data.registerUser
import ba.unsa.etf.routes.loginRoute
import ba.unsa.etf.routes.registerRoute
import ba.unsa.etf.routes.studentRoutes
import io.ktor.application.*
import io.ktor.auth.*
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
    install(Authentication){
        configureAuth() // konfiguriše autentifikaciju
    }
    install(DefaultHeaders) // presreče zahtjeve i dodaje osnovne Headere u njih
    install(CallLogging) // loguje sve zahtjeve i odgovore sa/prema serveru, korisno kod Debugginga
    install(ContentNegotiation) { // definiše u kojem tipu podatka se nalazi odgovor sa servera
        gson {
            setPrettyPrinting() // odabiramo JSON
        }
    }
    install(Routing){// definisanje URL pristupnih tačaka koje Clijent kontaktira
        registerRoute()
        loginRoute()
        studentRoutes()
    }

}

private fun Authentication.Configuration.configureAuth(){
    basic {
        realm = "Dnevnik Skole Kur'ana" // ime servera prilikom skočnog prozora
        validate { credentials -> // ono što je korisnik unio da se pokuša prijaviti
            val email = credentials.name
            val password = credentials.password
            if(checkPasswordForEmail(email,password)){
                UserIdPrincipal(email)  // ovo se poslije prikači na requestove da se potvrdi autentifikacija
            }else null
        }
    }
}


