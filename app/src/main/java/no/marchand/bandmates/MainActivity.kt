package no.marchand.bandmates

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.google.firebase.auth.FirebaseAuth


class MainActivity : AppCompatActivity(), SignupFragment.OnFragmentLoginListener {


    private lateinit var mFirstName: String
    private lateinit var mLastName: String
    private lateinit var mEmail: String
    private lateinit var mPwd: String
    private lateinit var mAge: String
    private lateinit var mInstrument: String




    private lateinit var mAuth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.d("D/", "MAIN ACTIVITY")

        mAuth = FirebaseAuth.getInstance()


        val signupFragment: Fragment = SignupFragment()
        this.setDefaultFragment(signupFragment)
    }

    override fun onStart() {
        super.onStart()
        val currentUser = mAuth.currentUser

    }

    override fun onLoginInputs(first: String, last: String, email: String, age: String, instrument: String, pw: String) {

        mFirstName = first
        Log.d("first name: ", mFirstName)
        mLastName = last
        mEmail = email
        mPwd = pw
        mAge = age
        mInstrument = instrument

        Log.d("Instrument: ", mInstrument)


    }




    private fun setDefaultFragment(defaultFragment: Fragment) {
        val manager: FragmentManager = this.supportFragmentManager
        val transaction: FragmentTransaction = manager.beginTransaction()

        transaction.replace(R.id.fragment_container, defaultFragment)
        transaction.commit()
    }

    private fun registerUser() {

    }
}
