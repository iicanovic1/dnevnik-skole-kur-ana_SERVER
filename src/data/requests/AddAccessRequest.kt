package ba.unsa.etf.data.requests

import ba.unsa.etf.data.collections.Access

data class AddAccessRequest (
    val studentID : String,
    val access : Access
        )