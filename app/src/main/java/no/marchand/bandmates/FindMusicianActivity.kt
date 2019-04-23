package no.marchand.bandmates

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.google.android.material.bottomnavigation.BottomNavigationView

private const val ACTIVITY_NUM = 2

class FindMusicianActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_find_musician)

        setupBottomNavView()
    }


    private fun setupBottomNavView() {
        val bottomNav: BottomNavigationView = findViewById(R.id.bottom_nav_view_bar)
        NavBottomUtil.enableNavigation(this@FindMusicianActivity, bottomNav)
        val menu: Menu = bottomNav.menu
        val menuItem: MenuItem = menu.getItem(ACTIVITY_NUM)
        menuItem.isChecked = true

    }
}
