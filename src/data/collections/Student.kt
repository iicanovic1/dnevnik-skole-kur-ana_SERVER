package ba.unsa.etf.data.collections

import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId

data class Student (
    val name : String,
    val lastName : String,
    val content : String,
    val date : Long,
    val accessEmails : List<String>,
    val color: String,
    @BsonId
    val id : String = ObjectId().toString()
)