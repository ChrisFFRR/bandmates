package no.marchand.bandmates

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.text.TextUtils
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
    private var listOfCities = arrayOf("Oslo", "Bergen", "Trondheim", "Stavanger")

    private var mAge = ""
    private var mInstrument = ""
    private var mCity = ""
    private var mListener: OnFragmentLoginListener? = null

    interface OnFragmentLoginListener {
        fun onRegisterInputs(first: String, last: String, email: String, age: String, instrument: String, city: String, pw: String)
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstance: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_signup, container, false)

        val loginBtn: TextView = view.findViewById(R.id.textView_login)
        val signUpBtn: Button = view.findViewById(R.id.btn_signUp)
        val ageOption: Button = view.findViewById(R.id.btn_age)
        val instrumentOption: Button = view.findViewById(R.id.btn_instrument)
        val cityOption: Button = view.findViewById(R.id.btn_city)

        val editTxtFirstName: EditText = view.findViewById(R.id.editTxt_firstName)
        val editTxtLastName: EditText = view.findViewById(R.id.editTxt_lastname)
        val editTxtEmail: EditText = view.findViewById(R.id.editTxt_email)
        val editTxtPwd: EditText = view.findViewById(R.id.editTxt_pwd)
        val txtViewDisplayAge: TextView = view.findViewById(R.id.txtViewDisplay_age)
        val txtViewDisplayInstrument: TextView = view.findViewById(R.id.txtViewDisplay_instrument)
        val txtViewDisplayCity: TextView = view.findViewById(R.id.txtViewDisplay_city)


        val inputIds: IntArray =
            intArrayOf(R.id.editTxt_firstName, R.id.editTxt_lastname, R.id.editTxt_email)
        val btnInputId: IntArray =
            intArrayOf(R.id.editTxt_pwd, R.id.txtViewDisplay_instrument,R.id.txtViewDisplay_age, R.id.txtViewDisplay_city)



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



            if (!validateInputs(inputIds, btnInputId)) {
                mListener?.onRegisterInputs(firstName, lastName, email, mAge, mInstrument, mCity, pwd)
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
                var age = numberPicker.value.toString()
                mAge = age
                mAgeDialog.dismiss()
                txtViewDisplayAge.text = mAge

            }

            mAgeDialogView.btn_cancel.setOnClickListener {
                mAgeDialog.dismiss()
            }
        }

        instrumentOption.setOnClickListener {
            val mInstrumentDialogView = LayoutInflater.from(activity).inflate(R.layout.spinner_dialog,null)
            val spinner = mInstrumentDialogView.findViewById<Spinner>(R.id.spinner)
            spinner.adapter = ArrayAdapter(activity, R.layout.spinner_item, listOfInstruments )
            spinner.onItemSelectedListener = spinnerOnItemSelectedListener()

            val mBuilder = AlertDialog.Builder(activity).setView(mInstrumentDialogView).setTitle("Choose instrument")

            val mInstrumentDialog = mBuilder.show()

            mInstrumentDialog.btn_save.setOnClickListener {
                mInstrument = spinner.selectedItem.toString()
                mInstrumentDialog.dismiss()
                txtViewDisplayInstrument.text = mInstrument
            }

        }

        cityOption.setOnClickListener{
            val mCityDialogView = LayoutInflater.from(activity).inflate(R.layout.spinner_dialog, null)
            val spinner = mCityDialogView.findViewById<Spinner>(R.id.spinner)
            spinner.adapter = ArrayAdapter(activity, R.layout.spinner_item, listOfCities)
            spinner.onItemSelectedListener = spinnerOnItemSelectedListener()

            val mBuilder = AlertDialog.Builder(activity).setView(mCityDialogView).setTitle("Choose City")

            val mCityDialog = mBuilder.show()

            mCityDialog.btn_save.setOnClickListener{
                mCity = spinner.selectedItem.toString()
                mCityDialog.dismiss()
                txtViewDisplayCity.text = mCity
            }

        }


        return view
    }

    private fun spinnerOnItemSelectedListener(): AdapterView.OnItemSelectedListener {
        return object : AdapterView.OnItemSelectedListener {


            override fun onItemSelected(parent: AdapterView<*>?, view: View?, pos: Int, id: Long) {
                Toast.makeText(
                    parent?.context,
                    "Selected: " + parent?.getItemAtPosition(pos).toString(),
                    Toast.LENGTH_SHORT
                ).show()
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
            }

        }
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

    private fun validateInputs(ids: IntArray, btnIds: IntArray): Boolean {
        var isEmpty = false

        for (id in ids) {
            var editText: EditText? = view?.findViewById(id)
            if (TextUtils.isEmpty(editText?.text.toString())) {
                editText?.error = "Must insert value"
                isEmpty = true
            }
        }
        for (id in btnIds) {
            var txtViews: TextView? = view?.findViewById(id)
            if (TextUtils.isEmpty(txtViews?.text.toString())) {
                txtViews?.error = "Must insert value"
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

