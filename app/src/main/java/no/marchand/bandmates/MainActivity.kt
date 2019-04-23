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


class MainActivity : AppCompatActivity(), OnFragmentInputListener {


    private val mAuth = FirebaseAuth.getInstance()
    private var currentUser: FirebaseUser? = null

    private lateinit var userModel: UserViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        userModel = UserViewModel(application)
    }

    override fun onStart() {
        super.onStart()
        currentUser = mAuth.currentUser
        if (currentUser != null) {
            val i = Intent(this, UserProfileActivity::class.java)
            startActivity(i)

        } else {
            val loginFragment: Fragment = LoginFragment()
            this.switchFragment(loginFragment)
        }
    }

    override fun onRegisterInputs(user: User, pw: String, email: String) {

        registerUser(pw, email)
        userModel.insert(user)

    }

    override fun onLoginInputs(email: String, pw: String) {
        mAuth.signInWithEmailAndPassword(email, pw).addOnCompleteListener(this) { task ->
            if (task.isComplete) {

                Log.d("CURRENT USER: ", currentUser.toString())
                startActivity(Intent(this, UserProfileActivity::class.java))
            } else {
                Toast.makeText(this@MainActivity, task.exception?.message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun switchFragment(defaultFragment: Fragment) {
        val manager: FragmentManager = this.supportFragmentManager
        val transaction: FragmentTransaction = manager.beginTransaction()

        transaction.replace(R.id.fragment_container, defaultFragment)
        transaction.commit()
    }


    private fun registerUser(email: String, pwd: String) {
        mAuth.createUserWithEmailAndPassword(email, pwd).addOnCompleteListener(this) { task ->
            if (task.isSuccessful) {
                // Sign in success, update UI with the signed-in user's information

                //this.currentUser = mAuth.currentUser


                Log.d("SUCCESS: ", "signInWithEmail:success")
                Toast.makeText(this@MainActivity, "Account created", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, UserProfileActivity::class.java))
                /*
                val loginFragment: Fragment = LoginFragment()
                this.switchFragment(loginFragment)
                */

            } else {
                // If sign in fails, display a message to the user.
                Log.w("FAIL: ", "signInWithEmail:failure", task.exception)
                Toast.makeText(
                    this@MainActivity, task.exception?.message,
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

    }

}

