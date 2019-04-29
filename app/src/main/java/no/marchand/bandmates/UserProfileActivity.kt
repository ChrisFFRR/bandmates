package no.marchand.bandmates

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_user_profile.*
import kotlinx.android.synthetic.main.dialog_bio.view.*
import java.io.ByteArrayOutputStream

private const val ACTIVITY_NUM = 1
private const val EXTERNAL_STORAGE_REQ_CODE = 2
private const val WRITE_EXT_STORAGE_CODE = 6
private const val GALLERY_REQ_CODE = 3
private const val CAMERA_REQ_CODE = 4



class UserProfileActivity : AppCompatActivity() {

    private lateinit var userViewModel: UserViewModel
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_profile)


        userViewModel = UserViewModel(application)
        firebaseAuth = FirebaseAuth.getInstance()

        val signoutBtn: Button = findViewById(R.id.btn_signOut)
        val cameraBtn: ImageButton = findViewById(R.id.choosePic_btn)
        val nameTxtView: TextView = findViewById(R.id.textview_display_name)
        val ageTxtView: TextView = findViewById(R.id.textview_display_age)
        val instrumentTxtView: TextView = findViewById(R.id.textview_display_instrument)
        val cityTxtView: TextView = findViewById(R.id.textview_display_city)
        val editBioBtn: Button = findViewById(R.id.btn_editBio)
        val bioTxtView: TextView = findViewById(R.id.txtView_Bio)


        setupBottomNavView()


        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                EXTERNAL_STORAGE_REQ_CODE
            )
        }
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                WRITE_EXT_STORAGE_CODE
            )
        }
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA), CAMERA_REQ_CODE)
        }


        userViewModel.allUsers.observe(this, Observer {

            nameTxtView.text = "${it[0].firstName} ${it[0].lastName}"
            ageTxtView.text = it[0].age
            instrumentTxtView.text = it[0].instrument
            cityTxtView.text = it[0].city
            bioTxtView.text = it[0].bio


                var pickPath = it[0].profile_pic_path
                var picURI = Uri.parse(pickPath)

                loadImageFromURI(picURI)

                plus_sign.visibility = View.GONE
        })

        signoutBtn.setOnClickListener {
            firebaseAuth.signOut()
            startActivity(Intent(this@UserProfileActivity, MainActivity::class.java))
        }

        editBioBtn.setOnClickListener {
            val mBioDialogView = LayoutInflater.from(this).inflate(R.layout.dialog_bio, null)
            val mEditBio = mBioDialogView.findViewById<EditText>(R.id.editTxt_dialog)

            val mBuilder = AlertDialog.Builder(this).setView(mBioDialogView).setTitle("Write bio")
            val mBioDialog = mBuilder.show()

            mBioDialogView.btn_save.setOnClickListener {
                var bio = mEditBio.text.toString()
                mBioDialog.dismiss()
                bioTxtView.text = bio
                userViewModel.updateBio(bio, 1)

            }

            mBioDialogView.btn_cancel.setOnClickListener {
                mBioDialog.dismiss()
            }
        }
        cameraBtn.setOnClickListener {
            showPictureDialog()
        }
    }


    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when (requestCode) {
            2 -> {
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {

                } else {
                    Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT)
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                GALLERY_REQ_CODE -> {
                    var selectedImgURI = data?.data as Uri
                    var selectedImg = selectedImgURI.path

                    loadImageFromURI(selectedImgURI)

                   // profile_pic_display.setImageURI(selectedImgURI)

                    userViewModel.updateProfilePic(selectedImg, 1)

                }
                CAMERA_REQ_CODE -> {
                    val cameraImg = data?.extras?.get("data") as Bitmap
                    val cameraImgUri = getImageUriFromBitmap(this, cameraImg)
                    val cameraImgUriString = cameraImgUri.toString()
                    userViewModel.updateProfilePic(cameraImgUriString, 1)

                    Glide.with(this).asBitmap().load(cameraImg).into(profile_pic_display)
                    //profile_pic_display.setImageBitmap(cameraImg)
                }
            }

            plus_sign.visibility = View.GONE
        }
    }

    /*
     * REF: https://stackoverflow.com/a/15432979/8505425
     */
    private fun getImageUriFromBitmap(context: Context, bitmap: Bitmap): Uri {
        val bytes = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        val path = MediaStore.Images.Media.insertImage(context.contentResolver, bitmap,"Title", null)
        return Uri.parse(path)
    }

    private fun setupBottomNavView() {
        val bottomNav: BottomNavigationView = findViewById(R.id.bottom_nav_view_bar)
        NavBottomUtil.enableNavigation(this@UserProfileActivity, bottomNav)
        val menu: Menu = bottomNav.menu
        val menuItem: MenuItem = menu.getItem(ACTIVITY_NUM)
        menuItem.isChecked = true

    }

    private fun showPictureDialog() {
        val picDialogItems = arrayOf("Select photo from gallery", "Capture photo from camera")

        val choosePicDialog = AlertDialog.Builder(this)
        choosePicDialog.setTitle("Choose profile picture")
        choosePicDialog.setItems(picDialogItems) { _: DialogInterface, item: Int ->
            when (item) {
                0 -> choosePicFromGallery()
                1 -> takePhotoFromCamera()
            }
        }
        choosePicDialog.show()
    }

    private fun takePhotoFromCamera() {
        val i = Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(i, CAMERA_REQ_CODE)

    }

    private fun choosePicFromGallery() {
        val mimeTypes = arrayOf("image/jpeg", "image/png")
        val i = Intent(Intent.ACTION_PICK)
        i.type = "image/*"
        intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes)

        startActivityForResult(i, GALLERY_REQ_CODE)
    }

    private fun loadImageFromURI(path: Uri) {

        Glide
            .with(this)
            .load(path.path)
            .into(profile_pic_display)
    }

}
