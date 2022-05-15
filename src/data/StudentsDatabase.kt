package ba.unsa.etf.data

import ba.unsa.etf.data.collections.Access
import ba.unsa.etf.data.collections.Student
import ba.unsa.etf.data.collections.User
import org.litote.kmongo.*
import org.litote.kmongo.MongoOperator.or
import org.litote.kmongo.coroutine.coroutine
import org.litote.kmongo.reactivestreams.KMongo

private val client = KMongo.createClient().coroutine // objašnjava da bazi pristupamo sa korutinama
private val database = client.getDatabase("DnevnikSkoleKurAnaDatabase") // kreira bazu
private val users = database.getCollection<User>() // ako nema kolekcije korisnika ono je kreira
private val students = database.getCollection<Student>()

suspend fun registerUser(user: User) : Boolean{ // ubacuje novog korisnika u bazu
    return users.insertOne(user).wasAcknowledged() // ubacuje korisnika i vraća da li je uspješno
}

suspend fun checkIfUserExists(email : String) :Boolean{ // testira potojanje korisnika u bazi
    return users.findOne(User::email eq email) != null
}

suspend fun checkPasswordForEmail(email: String, passwordToCheck : String ) : Boolean {
    val actualPassword = users.findOne(User::email eq email)?.password?: return false
    return passwordToCheck == actualPassword // ako ima taj korisnik provjeri je li tačan poslani password
}

suspend fun getStudentsForUser(email : String) : List<Student> { // dobavlja studenata za prijavljenog korisnika
    return students.find(Student::accessEmails / Access::email eq email).toList()
}

suspend fun saveStudent (student: Student) : Boolean{
    val studentExists = students.findOneById(student.id) != null
    return if(studentExists){ // ako već postoji samo update
        students.updateOneById(student.id, student).wasAcknowledged()
    }else{ // ako ne postoji dodaj novog studenta
        students.insertOne(student).wasAcknowledged()
    }
}

suspend fun deleteStudentForUser (email: String, studentID : String) : Boolean{
    val student = students.findOne(Student::id eq studentID, Student::accessEmails / Access::email eq email)
    student?.let { student ->
        if(student.accessEmails.size > 1){
            // ima više pristupa samo brišemo email iz Liste pristupa
            val studentAccess = student.accessEmails.find { access -> access.email == email }
            val newAccesEmails = student.accessEmails - studentAccess
            val updateResult = students.updateOne(Student::id eq student.id, setValue(Student::accessEmails, newAccesEmails))
            return updateResult.wasAcknowledged()
        }
        return students.deleteOneById(student.id).wasAcknowledged()
    }?: return false
}

