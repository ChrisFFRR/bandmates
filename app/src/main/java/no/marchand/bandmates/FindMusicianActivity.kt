package no.marchand.bandmates

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.*
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.database.*
import de.hdodenhof.circleimageview.CircleImageView


private const val ACTIVITY_NUM = 2


class FindMusicianActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var firebaseRecyclerAdapter: FirebaseRecyclerAdapter<Musician, MusicianViewHolder>
    private lateinit var loadingDialog: LoadingDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_find_musician)


        loadingDialog = LoadingDialog(this)

      /*
       *  Dette er ikke en bra implementering.
       *  Første gang vi laster dataen fra firebase, tar det litt tid å laste dataen, så jeg bruker en loading dialog
       *  som blir dismissed i onDataChanged metoden. Det fungerer helt fint første gangen, men om man navigerer seg rundt
       *  og tilbake til denne activitien, er dataen allerede hentet, og det tar mye kortere tid før data blir vist.
       *  Det fører til at loading dialogen bare blir vist i noen millisekunder, og det ser ikke bra ut.
       */
        loadingDialog.showDialog()
        /*
        *
        *
        *  Dette er en kode block som lytter til database endringer i firebase og returnerer eventuell ny data.
        *  Jeg tenkte først jeg kunne bruke dette til å vise i en recycleview, men jeg fikk ikke til å få det oppdatert
        *  via MusicanAdapter klassen min. Etter en del research fant jeg ut at Firebase har egne Recyclerview klasser
        *  som gjør akkurat det jeg ville, og jeg implementerte dem i stedet.
        *  Jeg velger å la koden være her, men kommenterer det ut for å vise hvordan jeg først prøvde å løse problemet.
        *
        *  Etter at jeg implementerte FirebaseRecyclerAdapter, trenger jeg ikke å bruke MusicianAdapter heller,
        *  men lar den klassen også være i prosjektet for å vise hvordan den er linket sammen med koden under.
        *
        *  Hvis koden under skulle ha vært brukt, hadde jeg hatt disse deklareringene som klassefelt:
        *
        *        private lateinit var adapter: MusicianAdapter
        *        private lateinit var firebaseDB: FirebaseDatabase
        *        private lateinit var usersReference: DatabaseReference
        *        private val usersFirebaseDb: MutableList<Musician?> = mutableListOf()
        *
        *
        *       firebaseDB = FirebaseDatabase.getInstance()
        *       usersReference = firebaseDB.reference.child("musicians")
        *
        *       usersReference.addListenerForSingleValueEvent(object : ValueEventListener {
        *           override fun onCancelled(dbErr: DatabaseError) {
        *               Log.d("Firebase userfetch cancelled: ", dbErr.toException().toString())
        *           }
        *
        *           override fun onDataChange(dbSnapshot: DataSnapshot) {
        *               Log.d("USERS IN DB: ", dbSnapshot.childrenCount.toString())
        *               if (dbSnapshot.exists()) {
        *
        *                   for (snapShot in dbSnapshot.children) {
        *                       val musician = snapShot.getValue(Musician::class.java)
        *                       usersFirebaseDb.add(musician)
        *                   }
        *               }
        *               adapter = MusicianAdapter(usersFirebaseDb)
        *               recyclerView.adapter = adapter
        *           }

        })

        */

        recyclerView = findViewById(R.id.recycler_view_musicians)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val dbRef = FirebaseDatabase.getInstance().reference
        val dbQuery = dbRef.child("musicians")


        val firebaseRecyclerOptions = FirebaseRecyclerOptions.Builder<Musician>()
            .setQuery(dbQuery, Musician::class.java)
            .build()

        firebaseRecyclerAdapter = object : FirebaseRecyclerAdapter<Musician, MusicianViewHolder>(firebaseRecyclerOptions) {
            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MusicianViewHolder {
                val view: View = LayoutInflater.from(parent.context).inflate(R.layout.musician_card_item, parent, false)
                Log.d("oncreateviewholder", "Triggered")

                return MusicianViewHolder(view)
            }

            override fun onBindViewHolder(musicianViewHolder: MusicianViewHolder, pos: Int, musician: Musician) {
                Log.d("onbindViewHolder: ", "Triggered")


                musicianViewHolder.setMusician(musician)
            }

            override fun onDataChanged() {
                loadingDialog.hideDialog()

            }
        }
        recyclerView.adapter = firebaseRecyclerAdapter



        setupBottomNavView()
    }

    override fun onStart() {
        super.onStart()
        firebaseRecyclerAdapter.startListening()
    }

    override fun onStop() {
        super.onStop()
        firebaseRecyclerAdapter.stopListening()


    }

    class MusicianViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val musicianName: TextView = itemView.findViewById(R.id.txtView_musician_name)
        private val musicianAge: TextView = itemView.findViewById(R.id.txtView_musician_age_info)
        private val musicianInstrument: TextView = itemView.findViewById(R.id.txtView_musician_instrument_info)
        private val musicianCity: TextView = itemView.findViewById(R.id.txtView_musician_city_info)
        private val musicianPhoto: CircleImageView = itemView.findViewById(R.id.musician_pic)

        fun setMusician(musician: Musician) {
            val musicianFirstname = musician.firstName
            val musicianLastname = musician.lastName
            val musicianAge = musician.age
            val musicianInstrument = musician.instrument
            val musicianCity = musician.city
            val musicianPhoto = musician.imgUrl

            this.musicianName.text = "$musicianFirstname $musicianLastname"
            this.musicianAge.text = musicianAge
            this.musicianInstrument.text = musicianInstrument
            this.musicianCity.text = musicianCity
            Glide.with(itemView)
                .load(musicianPhoto)
                .override(68,68)
                .into(this.musicianPhoto)
        }
    }

    private fun setupBottomNavView() {
        val bottomNav: BottomNavigationView = findViewById(R.id.bottom_nav_view_bar)
        NavBottomUtil.enableNavigation(this@FindMusicianActivity, bottomNav)
        val menu: Menu = bottomNav.menu
        val menuItem: MenuItem = menu.getItem(ACTIVITY_NUM)
        menuItem.isChecked = true
    }

    private fun showCustomLoadingDialog() {

        loadingDialog.showDialog()

        val handler = Handler()


        handler.postDelayed({ loadingDialog.hideDialog() }, 2000)

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
