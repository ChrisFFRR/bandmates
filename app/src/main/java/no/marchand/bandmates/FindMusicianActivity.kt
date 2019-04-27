package no.marchand.bandmates

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.database.*


private const val ACTIVITY_NUM = 2

class FindMusicianActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var adapter: MusicianAdapter
    private lateinit var firebaseDB: FirebaseDatabase
    private lateinit var usersReference: DatabaseReference
    private val usersFirebaseDb: MutableList<Musician?> = mutableListOf()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_find_musician)



        firebaseDB = FirebaseDatabase.getInstance()
        usersReference = firebaseDB.reference.child("musicians")

        usersReference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(dbErr: DatabaseError) {
                Log.d("Firebase userfetch cancelled: ", dbErr.toException().toString())
            }

            override fun onDataChange(dbSnapshot: DataSnapshot) {
                Log.d("USERS IN DB: ", dbSnapshot.childrenCount.toString())
                if (dbSnapshot.exists()) {

                    for (snapShot in dbSnapshot.children) {
                        val musician = snapShot.getValue(Musician::class.java)
                        usersFirebaseDb.add(musician)
                    }
                }
            }

        })

        adapter = MusicianAdapter(this)

        recyclerView = findViewById(R.id.recycler_view_musicians)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter



        setupBottomNavView()
    }

    private fun setupBottomNavView() {
        val bottomNav: BottomNavigationView = findViewById(R.id.bottom_nav_view_bar)
        NavBottomUtil.enableNavigation(this@FindMusicianActivity, bottomNav)
        val menu: Menu = bottomNav.menu
        val menuItem: MenuItem = menu.getItem(ACTIVITY_NUM)
        menuItem.isChecked = true

    }

    /*
    *                                       ..Ikke bruk..
    *
    * Denne funksjonen var bare en "onetime use" for å populere firebase databasen med brukere
    * sånn at jeg slapp å gjøre det manuelt i firebase consoll. Lot den likevel være for å vise fremgangsmåten.
    *
    */

    fun loadFirebaseDb(firebaseData: DatabaseReference) {
        val users: List<Musician> = mutableListOf(
            Musician("28", "Oslo", "Joel", "Silver", "Vokal"),
            Musician("32", "Oslo", "Pia", "Hove", "Gitar"),
            Musician("41", "Bergen", "Ravn", "Harr", "Trommer"),
            Musician("23", "Stavanger", "Liam", "Nilson", "Bass"),
            Musician("26", "Asker", "Tom", "Araya", "Vokal"),
            Musician("38", "Sandvika", "Zakk", "Wylde", "Guitar"),
            Musician("32", "Drammen", "Adam", "Driver", "Keyboard"),
            Musician("31", "Lillestrøm", "Nicky", "Sixx", "Guitar")
        )
        users.forEach {
            val key = firebaseData.child("musicians").push().key
            it.id = key!!

            firebaseData.child("musicians").child(key).setValue(it)

        }
    }
}
