package ba.unsa.etf.routes

import ba.unsa.etf.data.checkIfUserExists
import ba.unsa.etf.data.collections.User
import ba.unsa.etf.data.registerUser
import ba.unsa.etf.data.requests.AccountRequest
import ba.unsa.etf.data.responses.SimpleResponse
import ba.unsa.etf.security.getHashWithSalt
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*

fun Route.registerRoute (){
    route("/register"){
        post{
            val request = try {
                call.receive<AccountRequest>() // zaprima tijelo zahtjeva i ubacuje iz Json u AccountRequest
            }catch (e: ContentTransformationException){// ako je pogrešan format zahtjeva
                call.respond(HttpStatusCode.BadRequest)
                return@post
            }
            val userExists = checkIfUserExists(request.email)
            if(!userExists){
                if(registerUser(User(request.email, getHashWithSalt(request.password)))){
                    call.respond(HttpStatusCode.OK, SimpleResponse(true,"Uspješno kreiran račun!"))
                }else{
                    call.respond(HttpStatusCode.OK,SimpleResponse(false,"Nepoznata greška!"))
                }
            }else{
                call.respond(HttpStatusCode.OK,SimpleResponse(false,"Korisnik sa tim podacima već postoji!"))
            }
        }

    }
}