package no.marchand.bandmates

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val loginFragment: Fragment = LoginFragment()
        this.setDefaultFragment(loginFragment)
    }


   private fun setDefaultFragment(defaultFragment: Fragment) {
      val manager: FragmentManager = this.supportFragmentManager
       val transaction: FragmentTransaction = manager.beginTransaction()

       transaction.replace(R.id.fragment_container, defaultFragment)
       transaction.commit()
    }
}
