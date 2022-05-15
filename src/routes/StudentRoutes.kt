package ba.unsa.etf.routes

import ba.unsa.etf.data.getStudentsForUser
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.http.*
import io.ktor.http.HttpStatusCode.Companion.OK
import io.ktor.response.*
import io.ktor.routing.*

fun Route.studentRoutes() {
    route("/getStudents") {
        authenticate {
            get {
                val email = call.principal<UserIdPrincipal>()!!.name
                val studenti = getStudentsForUser(email)
                call.respond(OK, studenti)
            }
        }
    }
}