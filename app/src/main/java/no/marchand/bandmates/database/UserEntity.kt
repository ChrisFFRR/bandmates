package no.marchand.bandmates.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_table")

data class User(
    @PrimaryKey (autoGenerate = true) var id: Long = 0,
    @ColumnInfo (name = "profile_pic_path") var profile_pic_path: String?,
    @ColumnInfo (name = "first_name") var firstName: String?,
    @ColumnInfo (name = "last_name") var lastName: String?,
    @ColumnInfo (name = "city") var city: String?,
    @ColumnInfo (name = "instrument") var instrument: String?,
    @ColumnInfo (name = "age") var age: String?,
    @ColumnInfo (name= "bio") var bio: String?
) {
    constructor() : this(0,"","","","","","", "")
}


