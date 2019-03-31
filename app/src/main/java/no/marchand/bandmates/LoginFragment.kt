package no.marchand.bandmates

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText


class LoginFragment: Fragment() {

    fun newInstance(): LoginFragment {
        return LoginFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstance: Bundle?): View? {
        val view =  inflater.inflate(R.layout.fragment_login,container,false)


        val emailInput: EditText = view.findViewById(R.id.editText_email)
        val passwordInput: EditText = view.findViewById(R.id.editText_pwd)





        Log.d("LOGIN FRAGMENT: ", "email: ${emailInput.text}")
        Log.d("LOGIN FRAGMENT: ", "password: ${passwordInput.text}")





        return view

    }
}
