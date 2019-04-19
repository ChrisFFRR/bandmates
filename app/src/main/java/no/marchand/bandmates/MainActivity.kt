package no.marchand.bandmates

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import no.marchand.bandmates.database.User


class MainActivity : AppCompatActivity(), SignupFragment.OnFragmentLoginListener {

    private lateinit var userPojo: User
    private lateinit var mEmail: String
    private lateinit var mPwd: String

    private lateinit var mAuth: FirebaseAuth
    private var currentUser: FirebaseUser? = null

    private lateinit var userModel: UserViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        userModel = UserViewModel(application)
        Log.d("D/", "MAIN ACTIVITY")


        mAuth = FirebaseAuth.getInstance()

        if (currentUser == null) {
            val signupFragment: Fragment = SignupFragment()
            this.switchFragment(signupFragment)
        } else {
            val loginFragment: Fragment = LoginFragment()
            this.switchFragment(loginFragment)
        }
    }

    override fun onStart() {
        super.onStart()
        if (currentUser != null) {
            val loginFragment: Fragment = LoginFragment()
            this.switchFragment(loginFragment)
        }


    }

    override fun onRegisterInputs(
        first: String,
        last: String,
        email: String,
        age: String,
        instrument: String,
        city: String,
        pw: String
    ) {

        userPojo = User()

        userPojo.firstName = first
        userPojo.lastName = last
        mEmail = email
        mPwd = pw
        userPojo.age = age
        userPojo.instrument = instrument
        userPojo.city = city

        registerUser()


        userModel.insert(userPojo)

        Log.d("first name: ", userPojo.firstName)
        Log.d("last name: ", userPojo.lastName)
        Log.d("Email: ", mEmail)
        Log.d("PWD: ", mPwd)
        Log.d("AGE: ", userPojo.age)
        Log.d("Instrument: ", userPojo.instrument)
        Log.d("City: ", userPojo.city)

    }


    private fun switchFragment(defaultFragment: Fragment) {
        val manager: FragmentManager = this.supportFragmentManager
        val transaction: FragmentTransaction = manager.beginTransaction()

        transaction.replace(R.id.fragment_container, defaultFragment)
        transaction.commit()
    }

    private fun registerUser() {
        mAuth.createUserWithEmailAndPassword(mEmail, mPwd).addOnCompleteListener(this) { task ->
            if (task.isSuccessful) {
                // Sign in success, update UI with the signed-in user's information
                Log.d("SUCCESS: ", "signInWithEmail:success")

                Toast.makeText(this@MainActivity, "Account created", Toast.LENGTH_SHORT).show()

                this.currentUser = mAuth.currentUser
                val loginFragment: Fragment = LoginFragment()
                this.switchFragment(loginFragment)

            } else {
                // If sign in fails, display a message to the user.
                Log.w("FAIL: ", "signInWithEmail:failure", task.exception)
                Toast.makeText(
                    this@MainActivity, "Authentication failed.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

    }

}

