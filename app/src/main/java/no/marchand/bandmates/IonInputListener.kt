package no.marchand.bandmates

import no.marchand.bandmates.database.User

interface OnFragmentInputListener {
    fun onRegisterInputs(user: User, email: String, pw: String)
    fun onLoginInputs(email: String, pw:String)
}