package ba.unsa.etf.data.requests

import ba.unsa.etf.data.collections.Answer

data class AddAnswerRequest(
    val studentID : String,
    val answer: Answer
)
