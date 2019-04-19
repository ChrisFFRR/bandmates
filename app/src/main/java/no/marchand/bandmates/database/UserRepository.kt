package no.marchand.bandmates.database

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData

class UserRepository(private val userDao: UserDao) {

    val allUsers: LiveData<List<User>> = userDao.getAll()

    @WorkerThread
     fun insert(user: User) {
        userDao.insertUser(user)
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
    fun getAll() {
        userDao.getAll()
    }
}