package ba.unsa.etf.data.collections

import org.bson.types.ObjectId

data class Answer (
    val type: AnswerType,
    val section: Section,
    val chapter: Chapter,
    val sentenceMin : Int?,
    val sentenceMax : Int?,
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

data class Chapter (var chapterName : String, val numberOfSentences : Int?) {

    override fun toString(): String {
        return chapterName
    }
}

data class Section (val sectionName : String, val sectionNumber : Int, val chapters : List<Chapter>) {

    override fun toString(): String {
        return sectionName
    }

}