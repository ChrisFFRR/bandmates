package no.marchand.bandmates

import android.content.Context
import android.content.Intent
import com.google.android.material.bottomnavigation.BottomNavigationView

 object NavBottomUtil {

    fun enableNavigation(context: Context, bottomNav: BottomNavigationView) {
       bottomNav.setOnNavigationItemSelectedListener { item ->
            when(item.itemId) {
                R.id.ic_map -> {
                    context.startActivity(Intent(context, MapsActivity::class.java))
                     true
                }
                R.id.ic_user -> {
                    context.startActivity(Intent(context, UserProfileActivity::class.java))
                    true
                }
                R.id.ic_search -> {
                    context.startActivity(Intent(context, FindMusicianActivity::class.java))
                     true
                }
            }
             false
        }
    }
}