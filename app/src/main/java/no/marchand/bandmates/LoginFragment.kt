package no.marchand.bandmates

import android.content.Intent
import android.os.Bundle


import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.FragmentTransaction
import com.google.firebase.auth.FirebaseAuth


class LoginFragment: Fragment() {



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstance: Bundle?): View? {
        val view =  inflater.inflate(R.layout.fragment_login,container,false)


        val emailInput: EditText = view.findViewById(R.id.editText_email)
        val passwordInput: EditText = view.findViewById(R.id.editText_pwd)


        val loginBtn: Button = view.findViewById(R.id.btn_Login)




        loginBtn.setOnClickListener {
           val mapActivity = Intent(activity, MapsActivity::class.java)
            startActivity(mapActivity)
        }

        /*

        signupBtn.setOnClickListener {
            val signupFragment: Fragment = SignupFragment()

            val transaction: FragmentTransaction = fragmentManager!!.beginTransaction()

            transaction.replace(R.id.fragment_container, signupFragment)
            transaction.commit()
        }
        */



        Log.d("LOGIN FRAGMENT: ", "email: ${emailInput.text}")
        Log.d("LOGIN FRAGMENT: ", "password: ${passwordInput.text}")




        return view

    }
}
