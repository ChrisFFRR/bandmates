package no.marchand.bandmates

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

class SignupFragment: Fragment() {

    fun newInstance(): LoginFragment {
        return LoginFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstance: Bundle?): View? {
        val view =  inflater.inflate(R.layout.fragment_signup,container,false)



        return view
    }
}