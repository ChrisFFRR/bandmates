package no.marchand.bandmates

import android.content.Context
import android.os.Bundle
import android.text.TextUtils


import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import java.lang.RuntimeException


class LoginFragment : Fragment() {

    private var mListener: OnFragmentInputListener? = null


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstance: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_login, container, false)

        val inputIds: IntArray = intArrayOf(R.id.editText_email, R.id.editText_pwd)


        val emailInput: EditText = view.findViewById(R.id.editText_email)
        val passwordInput: EditText = view.findViewById(R.id.editText_pwd)


        val loginBtn: Button = view.findViewById(R.id.btn_Login)


        loginBtn.setOnClickListener {

            val email = emailInput.text.toString()
            val pwd = passwordInput.text.toString()

            if (!validateInputs(inputIds)) {
                mListener?.onLoginInputs(email, pwd)
            } else {
                Toast.makeText(activity, "Fill in all blank fields", Toast.LENGTH_SHORT).show()
            }
            /*
            val userProfile = Intent(activity, UserProfileActivity::class.java)
            startActivity(userProfile)
            */


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

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFragmentInputListener) {
            mListener = context
        } else {
            throw RuntimeException(context.toString() + "must implement OnFragmentInputListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        mListener = null
    }

    private fun validateInputs(ids: IntArray): Boolean {
        var isEmpty = false
        for (id in ids) {
            val editText: EditText? = view?.findViewById(id)
            if (TextUtils.isEmpty(editText?.text.toString())) {
                editText?.error = "Must insert value"
                isEmpty = true
            }
        }

        return isEmpty
    }
}

