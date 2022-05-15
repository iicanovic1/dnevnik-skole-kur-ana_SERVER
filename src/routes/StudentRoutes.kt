package ba.unsa.etf.routes

import ba.unsa.etf.data.collections.Student
import ba.unsa.etf.data.deleteStudentForUser
import ba.unsa.etf.data.getStudentsForUser
import ba.unsa.etf.data.requests.DeleteNoteRequest
import ba.unsa.etf.data.saveStudent
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.http.*
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
                    call.receive<DeleteNoteRequest>()
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
}