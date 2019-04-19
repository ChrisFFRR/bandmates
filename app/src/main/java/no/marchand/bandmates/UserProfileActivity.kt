package no.marchand.bandmates

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer

class UserProfileActivity : AppCompatActivity() {

    lateinit var userViewModel: UserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_profile)

        userViewModel = UserViewModel(application)
        val adapter = UserAdapter(this)


        userViewModel.allUsers.observe(this, Observer { users ->
            users.forEach { _ ->
                adapter.setUsers(users)
            }
        })



    }
}
