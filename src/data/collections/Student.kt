package ba.unsa.etf.data.collections

import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId

data class Student (
    val name : String,
    val lastName : String,
    val content : String,
    var date : Long,
    val accessEmails : List<Access>,
    val color: String,
    var answers : List<Answer>,
    var sumOfMarks : Int = 0,
    @BsonId
    val id : String = ObjectId().toString()
)