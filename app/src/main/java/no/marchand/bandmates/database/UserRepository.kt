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
    fun getById(id: Int): LiveData<User> {
       return userDao.getUserById(id)
    }

    @WorkerThread
     fun update(user: User) {
        userDao.updateUser(user)
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