package no.marchand.bandmates

import android.app.Activity
import android.app.Dialog
import android.view.Window
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.DrawableImageViewTarget
import com.bumptech.glide.request.target.Target


/*
 * https://medium.com/mindorks/stop-implementing-the-default-loading-dialog-in-you-android-apps-43c3a85036d6
 *
 *
 * OBS les dokumentasjon
 */

class LoadingDialog(private var activity: Activity) {

    private lateinit var dialog: Dialog

    fun showDialog() {
        dialog = Dialog(activity)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.dialog_loading)

        val loadingImgview: ImageView = dialog.findViewById(R.id.custom_loading_imageView)

        val imageViewTarget: DrawableImageViewTarget = DrawableImageViewTarget(loadingImgview)

        Glide.with(activity)
            .load(R.drawable.loadingmetronome)
            .placeholder(R.drawable.loadingmetronome)
            .centerCrop()
            .into(imageViewTarget)

        dialog.show()
    }

    fun hideDialog() {
        if(dialog.isShowing) {
            dialog.dismiss()
        }
    }
}