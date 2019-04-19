package no.marchand.bandmates

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import no.marchand.bandmates.database.AppDb
import no.marchand.bandmates.database.User
import no.marchand.bandmates.database.UserRepository
import kotlin.coroutines.CoroutineContext

class UserViewModel(application: Application) : AndroidViewModel(application) {

    private var parentJob = Job()
    private val coroutineContext: CoroutineContext
        get() = parentJob + Dispatchers.Main
    private val scope = CoroutineScope(coroutineContext)

    private val repository: UserRepository
      val allUsers: LiveData<List<User>>

    init {
        val userDao = AppDb.getDatabase(application).userDao()
        repository = UserRepository(userDao)
        allUsers = repository.allUsers
    }

    fun insert(user:User) = scope.launch(Dispatchers.IO) {
        repository.insert(user)
    }

    fun update(user: User) = scope.launch(Dispatchers.IO) {
        repository.update(user)
    }

    fun deleteUser(user:User) = scope.launch(Dispatchers.IO) {
        repository.delete(user)
    }

    fun deleteAll() = scope.launch(Dispatchers.IO) {
        repository.deleteAll()
    }

    fun getallUsers() = scope.launch(Dispatchers.IO) {
        repository.getAll()
    }

    override fun onCleared() {
        super.onCleared()
        parentJob.cancel()
    }
}