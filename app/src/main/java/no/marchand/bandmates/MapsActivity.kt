package no.marchand.bandmates


import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import android.os.Build
import android.provider.Settings
import android.util.Log
import android.view.Menu
import android.view.MenuItem

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.bottomnavigation.BottomNavigationView

private const val PERMISSION_REQUEST = 10
private const val ACTIVITY_NUM = 0


class MapsActivity : AppCompatActivity(), OnMapReadyCallback {


    //tilganger nødvendig for lokasjon
    private var permissions = arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION)

    private lateinit var mMap: GoogleMap
    private lateinit var locationManager: LocationManager
    private var locationGps: Location? = null
    private var locationNetwork: Location? = null
    private  var currentPos: LatLng = LatLng(0.0,0.0)


    private var hasGps = false
    private var hasNetwork = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)

        setupBottomNavView()


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkPermission(permissions)) {
                getLocation()
            } else {
                requestPermissions(permissions, PERMISSION_REQUEST)
            }
            getLocation()
        }
        Log.d("OBS", "ON CREATE")

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)



    }

    override fun onMapReady(googleMap: GoogleMap) {

        mMap = googleMap


        mMap.run {
            addMarker(MarkerOptions().position(currentPos).title("You"))
            animateCamera(CameraUpdateFactory.newLatLngZoom(currentPos, 10f))
        }
    }


    @SuppressLint("MissingPermission")
    private fun getLocation() {
        locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        hasGps = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
        hasNetwork = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
        var lat = 0.0
        var long = 0.0

        if (hasGps || hasNetwork) {
            if (hasGps) {
                locationManager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER,
                    5000,
                    0F,
                    object : LocationListener {
                        override fun onLocationChanged(location: Location?) {
                            if (location != null) {
                                locationGps = location
                                lat = locationGps!!.latitude
                                long = locationGps!!.longitude
                            }
                                currentPos = LatLng(lat, long)

                        }

                        override fun onStatusChanged(p0: String?, p1: Int, p2: Bundle?) {

                        }

                        override fun onProviderEnabled(p0: String?) {

                        }

                        override fun onProviderDisabled(p0: String?) {

                        }

                    })

                val localGpsLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
                if (localGpsLocation != null) {
                    locationGps = localGpsLocation
                }

            }
            if (hasNetwork) {
                locationManager.requestLocationUpdates(
                    LocationManager.NETWORK_PROVIDER,
                    5000,
                    0F,
                    object : LocationListener {
                        override fun onLocationChanged(location: Location?) {
                            if (location != null) {
                                locationNetwork = location
                                 lat = locationNetwork!!.latitude
                                 long = locationNetwork!!.longitude
                            }
                            currentPos = LatLng(lat, long)
                        }

                        override fun onStatusChanged(p0: String?, p1: Int, p2: Bundle?) {

                        }

                        override fun onProviderEnabled(p0: String?) {

                        }

                        override fun onProviderDisabled(p0: String?) {
                        }

                    })

                val localNetworkLocation = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
                if (localNetworkLocation != null) {
                    locationNetwork = localNetworkLocation
                }

                if (locationGps != null && locationNetwork != null) {

                    if (locationGps!!.accuracy > locationNetwork!!.accuracy) {
                        lat = locationNetwork!!.latitude
                        long = locationNetwork!!.longitude
                    } else {
                        lat = locationGps!!.latitude
                        long = locationGps!!.longitude
                    }
                    currentPos = LatLng(lat, long)
                } else {
                    startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))
                }
            }
        }
    }

    private fun checkPermission(permissionArray: Array<String>): Boolean {
        var allSuccess = true
        for (i in permissionArray.indices) {
            if (checkCallingOrSelfPermission(permissionArray[i]) == PackageManager.PERMISSION_DENIED)
                allSuccess = false
        }
        return allSuccess
    }

    private fun setupBottomNavView() {
        val bottomNav: BottomNavigationView = findViewById(R.id.bottom_nav_view_bar)
        NavBottomUtil.enableNavigation(this, bottomNav)
        val menu: Menu = bottomNav.menu
        val menuItem: MenuItem = menu.getItem(ACTIVITY_NUM)
        menuItem.isChecked = true
    }

}
