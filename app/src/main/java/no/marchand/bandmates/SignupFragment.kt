package no.marchand.bandmates

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import kotlinx.android.synthetic.main.age_dialog.*
import kotlinx.android.synthetic.main.age_dialog.view.*
import java.lang.RuntimeException

class SignupFragment : Fragment() {


    private var listOfInstruments = arrayOf("Guitar", "Bass", "Drums", "Keyboard", "Vocal")

    private var mAge: String = ""
    private var mInstrument: String = ""
    private var mListener: OnFragmentLoginListener? = null

    interface OnFragmentLoginListener {
        fun onLoginInputs(first: String, last: String, email: String, age: String, instrument: String, pw: String)
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstance: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_signup, container, false)

        val loginBtn: TextView = view.findViewById(R.id.textView_login)
        val signUpBtn: Button = view.findViewById(R.id.btn_signUp)
        val ageOption: TextView = view.findViewById(R.id.txtView_age)
        val instrumentOption: TextView = view.findViewById(R.id.txtView_instrument)
        val cityOption: TextView = view.findViewById(R.id.txtView_city)

        val editTxtFirstName: EditText = view.findViewById(R.id.editTxt_firstName)
        val editTxtLastName: EditText = view.findViewById(R.id.editTxt_lastname)
        val editTxtEmail: EditText = view.findViewById(R.id.editTxt_email)
        val editTxtPwd: EditText = view.findViewById(R.id.editTxt_pwd)


        val inputIds: IntArray =
            intArrayOf(R.id.editTxt_firstName, R.id.editTxt_lastname, R.id.editTxt_email, R.id.editTxt_pwd)






        loginBtn.setOnClickListener {
            val loginFragment: Fragment = LoginFragment()

            val transaction: FragmentTransaction = fragmentManager!!.beginTransaction()

            transaction.replace(R.id.fragment_container, loginFragment)
            transaction.commit()
        }

        signUpBtn.setOnClickListener {
            val firstName = editTxtFirstName.text.toString()
            val lastName = editTxtLastName.text.toString()
            val email = editTxtEmail.text.toString()
            val pwd = editTxtPwd.text.toString()


            if (!validateInputs(inputIds)) {
                mListener?.onLoginInputs(firstName, lastName, email, mAge, mInstrument, pwd)
            } else {
                Toast.makeText(activity, "Fill in all blank fields", Toast.LENGTH_SHORT).show()
            }
        }

        ageOption.setOnClickListener {
            val mAgeDialogView = LayoutInflater.from(activity).inflate(R.layout.age_dialog, null)
            val numberPicker = mAgeDialogView.findViewById<NumberPicker>(R.id.numberPicker)


            val mBuilder = AlertDialog.Builder(activity).setView(mAgeDialogView).setTitle("Enter Age")

            val mAgeDialog = mBuilder.show()

            mAgeDialog.numberPicker.minValue = 16
            mAgeDialog.numberPicker.maxValue = 99
            mAgeDialog.numberPicker.wrapSelectorWheel = true

            mAgeDialog.btn_save.setOnClickListener {
                mAgeDialog.dismiss()
                var age = numberPicker.value.toString()
                mAge = age
            }

            mAgeDialogView.btn_cancel.setOnClickListener {
                mAgeDialog.dismiss()
            }
        }

        instrumentOption.setOnClickListener {
            val mInstrumentDialogView = LayoutInflater.from(activity).inflate(R.layout.instrument_dialog,null)
            val spinner = mInstrumentDialogView.findViewById<Spinner>(R.id.spinner_instrument)
            spinner.adapter = ArrayAdapter(activity, R.layout.instrument_spinner_item, listOfInstruments )
            spinner.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {


                override fun onItemSelected(parent: AdapterView<*>?, view: View?, pos: Int, id: Long) {
                   Toast.makeText(parent?.context, "Selected: " + parent?.getItemAtPosition(pos).toString(), Toast.LENGTH_SHORT).show()
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {
                }

            }
            val mBuilder = AlertDialog.Builder(activity).setView(mInstrumentDialogView).setTitle("Choose instrument")

            val mInstrumentDialog = mBuilder.show()

            mInstrumentDialog.btn_save.setOnClickListener {
                mInstrument = spinner.selectedItem.toString()
                mInstrumentDialog.dismiss()
            }

        }


        return view
    }



    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFragmentLoginListener) {
            mListener = context
        } else {
            throw RuntimeException(context.toString() + "must implement OnFragmentLoginListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        mListener = null
    }

    private fun validateInputs(ids: IntArray): Boolean {
        var isEmpty = false

        for (id in ids) {
            var editText: EditText? = view?.findViewById(id)
            if (TextUtils.isEmpty(editText?.text.toString())) {
                editText?.error = "Must insert value"
                isEmpty = true
            }
        }

        return isEmpty
    }




    private fun showAgeDialog() {

//https://tutorialwing.com/android-numberpicker-tutorial-with-example/

    }

    private fun showInstrumentDialog() {
        //https://www.mkyong.com/android/android-spinner-drop-down-list-example/
    }


}

