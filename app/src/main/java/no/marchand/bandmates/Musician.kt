package no.marchand.bandmates

import com.google.firebase.database.PropertyName


data class Musician(
    @get:PropertyName("age")
    @set:PropertyName("age")
    var age: String = "",
    @get:PropertyName("city")
    @set:PropertyName("city")
    var city: String = "",
    @get:PropertyName("firstName")
    @set:PropertyName("firstName")
    var firstName: String = "",
    @get:PropertyName("lastName")
    @set:PropertyName("lastName")
    var lastName: String = "",
    @get:PropertyName("instrument")
    @set:PropertyName("instrument")
    var instrument: String = "",
    @get:PropertyName("id")
    @set:PropertyName("id")
    var id: String = "",
    @get:PropertyName("imgUrl")
    @set:PropertyName("imgUrl")
    var imgUrl:String = ""
) {
    constructor() : this("", "", "", "", "", "","")
}
