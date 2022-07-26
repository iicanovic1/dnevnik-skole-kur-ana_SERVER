package ba.unsa.etf.routes

import ba.unsa.etf.data.*
import ba.unsa.etf.data.collections.Student
import ba.unsa.etf.data.requests.AddAccessRequest
import ba.unsa.etf.data.requests.DeleteStudentRequest
import ba.unsa.etf.data.responses.SimpleResponse
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.http.HttpStatusCode.Companion.BadRequest
import io.ktor.http.HttpStatusCode.Companion.Conflict
import io.ktor.http.HttpStatusCode.Companion.OK
import io.ktor.request.*
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
    route("/addStudent"){
        authenticate {
            post {
                val student =  try {
                    call.receive<Student>()
                }catch (e: ContentTransformationException){
                    call.respond(BadRequest)
                    return@post
                }
                if(saveStudent(student)) {
                    call.respond(OK)
                }else{
                    call.respond(Conflict)
                }
            }
        }
    }
    route("/deleteStudent"){
        authenticate {
            post {
                val email = call.principal<UserIdPrincipal>()!!.name
                val request = try {
                    call.receive<DeleteStudentRequest>()
                }catch (e : ContentTransformationException){
                    call.respond(BadRequest)
                    return@post
                }
                if(deleteStudentForUser(email, request.id)){
                    call.respond(OK)
                }else{
                    call.respond(Conflict)
                }
            }
        }
    }
    route("/addAccessToStudent"){
        authenticate {
            post{
                val request = try {
                    call.receive<AddAccessRequest>()
                }catch (e : ContentTransformationException){
                    call.respond(BadRequest)
                    return@post
                }
                if(!checkIfUserExists(request.access.email)){
                    call.respond(
                        OK, SimpleResponse(false,"Ne postoji korisnik sa ovim podacima!")
                    )
                    return@post
                }
                if(hasAccessToStudent(request.studentID,request.access)){
                    call.respond(
                        OK,SimpleResponse(false,"Korisnik već ima pristup!")
                    )
                    return@post
                }
                if(addAccessToStudent(request.studentID,request.access)){
                    call.respond(
                        OK,
                        SimpleResponse(true, "${request.access.email} sada ima pristup!")
                    )
                }else{
                    call.respond(Conflict)
                }
            }
        }
    }


}