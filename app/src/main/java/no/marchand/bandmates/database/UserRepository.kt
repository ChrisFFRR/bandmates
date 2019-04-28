package no.marchand.bandmates.database

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData


class UserRepository(private val userDao: UserDao) {

    val allUsersLive: LiveData<List<User>> = userDao.getAllLive()
    val user: LiveData<User> = userDao.getUserById(1)


    @WorkerThread
     fun insert(user: User) {
        userDao.insertUser(user)
    }

    @WorkerThread
    fun countAll() {
        userDao.countAll()
    }

    @WorkerThread
    fun getById(id: Int): LiveData<User> {
       return userDao.getUserById(id)
    }

    @WorkerThread
     fun update(user: User) {
        userDao.updateUser(user)
    }
    @WorkerThread
    fun updateProfilePic(path: String?, id: Int) {
        userDao.updateProfilePic(path, id)
    }

    @WorkerThread
    fun updateUserBio(bio: String, id: Int) {
        userDao.updateUserBio(bio, id)
    }


    @WorkerThread
     fun delete(user: User) {
        userDao.deleteUser(user)
    }

    @WorkerThread
    fun deleteAll() {
        userDao.deleteAllUsers()
    }

    @WorkerThread
    fun getAllLive(): LiveData<List<User>> {
       return userDao.getAllLive()
    }

    @WorkerThread
    fun getAll(): List<User> {
        return userDao.getAll()
    }
}