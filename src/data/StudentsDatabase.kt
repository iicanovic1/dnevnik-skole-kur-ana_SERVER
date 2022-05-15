package ba.unsa.etf.data

import ba.unsa.etf.data.collections.Student
import ba.unsa.etf.data.collections.User
import org.litote.kmongo.coroutine.coroutine
import org.litote.kmongo.eq
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