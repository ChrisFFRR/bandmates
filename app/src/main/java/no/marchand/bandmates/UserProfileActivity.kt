package no.marchand.bandmates

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.room.Room
import androidx.room.RoomDatabase
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import no.marchand.bandmates.database.AppDb
import no.marchand.bandmates.database.User

private const val ACTIVITY_NUM = 1
class UserProfileActivity : AppCompatActivity() {



    lateinit var userViewModel: UserViewModel
    lateinit var firebaseAuth: FirebaseAuth
/*
    private val mOnNavItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when(item.itemId) {
            R.id.ic_map -> {
                startActivity(Intent(this@UserProfileActivity, MapsActivity::class.java))
                return@OnNavigationItemSelectedListener true
            }
            R.id.ic_user -> {
                 startActivity(Intent(this@UserProfileActivity, UserProfileActivity::class.java))
                return@OnNavigationItemSelectedListener true
            }
            R.id.ic_search -> {
                 startActivity(Intent(this@UserProfileActivity, FindMusicianActivity::class.java))
                return@OnNavigationItemSelectedListener true
            }
        }
        return@OnNavigationItemSelectedListener false
    }
    */


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_profile)

        userViewModel = UserViewModel(application)
        firebaseAuth = FirebaseAuth.getInstance()

        val signoutBtn: Button = findViewById(R.id.btn_signOut)
        val cameraBtn: ImageButton = findViewById(R.id.choosePic_btn)
        val nameTxtView: TextView = findViewById(R.id.textview_display_name)
        val ageTxtView: TextView = findViewById(R.id.textview_display_age)
        val instrumentTxtView: TextView = findViewById(R.id.textview_display_instrument)
        val cityTxtView: TextView = findViewById(R.id.textview_display_city)


        setupBottomNavView()





        userViewModel.allUsers.observe(this, Observer { user ->
            nameTxtView.text = user[0].firstName + " " + user[0].lastName
            ageTxtView.text = user[0].age
            instrumentTxtView.text = user[0].instrument
            cityTxtView.text = user[0].city
        })

        signoutBtn.setOnClickListener {
            firebaseAuth.signOut()
            startActivity(Intent(this@UserProfileActivity, MainActivity::class.java))
        }

/*
For recyclerview liste
        userViewModel.allUsers.observe(this, Observer { users ->
            users.forEach { _ ->
                adapter.setUsers(users)
            }
        })
*/

        cameraBtn.setOnClickListener {
            showPictureDialog()
        }



    }

    private fun setupBottomNavView() {
        val bottomNav: BottomNavigationView = findViewById(R.id.bottom_nav_view_bar)
        NavBottomUtil.enableNavigation(this@UserProfileActivity, bottomNav)
        val menu: Menu = bottomNav.menu
        val menuItem: MenuItem = menu.getItem(ACTIVITY_NUM)
        menuItem.isChecked = true

    }

    private fun showPictureDialog() {

    }
}
