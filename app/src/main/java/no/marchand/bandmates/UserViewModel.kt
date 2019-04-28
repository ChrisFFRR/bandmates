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



    private val repository: UserRepository
   private lateinit var user: LiveData<User>
    val allUsers: LiveData<List<User>>

    private var parentJob = Job()
    private val coroutineContext: CoroutineContext
        get() = parentJob + Dispatchers.Main
    private val scope = CoroutineScope(coroutineContext)

    init {
        val userDao = AppDb.getDatabase(application.applicationContext).userDao()
        repository = UserRepository(userDao)
        allUsers = repository.getAllLive()

    }



    fun countAll() = scope.launch(Dispatchers.IO) {
        repository.countAll()
    }

    fun insert(user:User) = scope.launch(Dispatchers.IO) {

        repository.insert(user)
    }

    fun update(user: User) = scope.launch(Dispatchers.IO) {
        repository.update(user)
    }

    fun updateProfilePic(path: String?, id: Int ) = scope.launch(Dispatchers.IO) {
        repository.updateProfilePic(path, id)
    }
    fun updateBio(bio: String, id: Int) = scope.launch(Dispatchers.IO) {
        repository.updateUserBio(bio, id)
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

    fun getUserById(id : Int) = scope.launch(Dispatchers.IO) {
        repository.getById(id)
    }



    override fun onCleared() {
        super.onCleared()
        parentJob.cancel()
    }
}