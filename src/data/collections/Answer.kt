package ba.unsa.etf.data.collections

import ba.unsa.etf.data.collections.Surah.*
import org.bson.types.ObjectId
import java.util.*

data class Answer (
    val type: AnswerType,
    val juz: Juz,
    val surah: Surah,
    val ajehMin : Int?,
    val ajehMax : Int?,
    val date: Long,
    val mark: Int,
    val id: String = ObjectId().toString(),
    val revision : Boolean = false
        )

data class AnswerType (val typeName : String) {

    override fun toString(): String {
        return typeName
    }
}

data class Surah (var surahName : String, val numberOfAjat : Int?) {

    override fun toString(): String {
        return surahName
    }
}

data class Juz (val juzName : String, val juzNumber : Int,  val surahs : List<Surah>) {

    override fun toString(): String {
        return juzName
    }

}