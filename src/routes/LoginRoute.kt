package ba.unsa.etf.routes

import ba.unsa.etf.data.checkPasswordForEmail
import ba.unsa.etf.data.requests.AccountRequest
import ba.unsa.etf.data.responses.SimpleResponse
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*

fun Route.loginRoute(){
    route("/login"){
        post {
            val request = try {
                call.receive<AccountRequest>() // zaprima tijelo zahtjeva i ubacuje iz Json u AccountRequest
            }catch (e: ContentTransformationException){// ako je pogrešan format zahtjeva
                call.respond(HttpStatusCode.BadRequest)
                return@post
            }
            val isPasswordCorrect = checkPasswordForEmail(request.email, request.password)
            if(isPasswordCorrect){
                call.respond(HttpStatusCode.OK, SimpleResponse(true,"Uspješna prijava!"))
            }else{
                call.respond(HttpStatusCode.OK, SimpleResponse(false,"E-mail ili lozinka nisu ispravni!"))
            }
        }
    }
}