package ba.unsa.etf.data.collections

import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId

data class Student (
    val name : String,
    val lastName : String,
    val content : String,
    val date : Long,
    val accessEmails : List<Access>,
    val color: String,
    val answers : List<Answer>,
    val average : Float,
    @BsonId
    val id : String = ObjectId().toString()
)