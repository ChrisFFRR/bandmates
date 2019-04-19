package no.marchand.bandmates.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface UserDao {

    @Query("SELECT * FROM user_table")
    fun getAll(): LiveData<List<User>>

    @Query( "SELECT * FROM user_table WHERE id IN (:userId)")
    fun getUserById(userId: Int): LiveData<User>

    @Insert
    fun insertUser(users: User)

    @Delete
    fun deleteUser(user: User)

    @Update
    fun updateUser(User: User)

    @Query("DELETE FROM user_table")
    fun deleteAllUsers()
}