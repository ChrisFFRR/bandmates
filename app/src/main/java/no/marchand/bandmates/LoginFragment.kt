package no.marchand.bandmates

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.FragmentTransaction


class LoginFragment: Fragment() {

    fun newInstance(): LoginFragment {
        return LoginFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstance: Bundle?): View? {
        val view =  inflater.inflate(R.layout.fragment_login,container,false)


        val emailInput: EditText = view.findViewById(R.id.editText_email)
        val passwordInput: EditText = view.findViewById(R.id.editText_pwd)

        val signupBtn: TextView = view.findViewById(R.id.textView_Signup)

        signupBtn.setOnClickListener {
            var signupFragment: Fragment = SignupFragment()
            var loginFragment: Fragment? = fragmentManager?.findFragmentById(R.id.login_fragment)
            var loginTag = loginFragment?.tag.toString()

            Log.d("TAG: ", loginTag)


            val transaction: FragmentTransaction = fragmentManager!!.beginTransaction()

            transaction.replace(R.id.fragment_container, signupFragment)
            transaction.commit()
        }




        Log.d("LOGIN FRAGMENT: ", "email: ${emailInput.text}")
        Log.d("LOGIN FRAGMENT: ", "password: ${passwordInput.text}")




        return view

    }
}
