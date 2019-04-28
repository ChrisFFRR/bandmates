package no.marchand.bandmates.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface UserDao {

    @Query("SELECT * FROM user_table")
    fun getAllLive(): LiveData<List<User>>

    @Query("SELECT * FROM user_table")
    fun getAll(): List<User>

    @Query( "SELECT * FROM user_table WHERE id IN (:userId)")
    fun getUserById(userId: Int): LiveData<User>

    @Query(" SELECT COUNT(*) FROM user_TABLE")
    fun countAll(): Int

    @Insert
    fun insertUser(users: User)

    @Delete
    fun deleteUser(user: User)

    @Update
    fun updateUser(User: User)

    @Query ("UPDATE user_table SET profile_pic_path = (:path) WHERE id IN (:userId)")
    fun updateProfilePic(path: String?, userId:Int)

    @Query ( "UPDATE user_table SET bio = (:bio) WHERE id IN (:userId)")
    fun updateUserBio(bio: String, userId: Int)

    @Query("DELETE FROM user_table")
    fun deleteAllUsers()
}