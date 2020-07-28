package com.studytrial.synrgypermission

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.bumptech.glide.Glide
import com.google.android.gms.location.LocationServices
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        this.supportActionBar?.hide()

        // Akses gambar dari internet ke imageView
        Glide.with(this)
            .load("https://p0.piqsels.com/preview/327/1023/585/graffiti-art-of-an-astronaut-traveling-through-a-colorful-galaxy.jpg")
            .into(iv1)

        var longitude: Double? = 0.0
        var latitude: Double? = 0.0

        // Akses Lokasi
        btn_location.setOnClickListener {
            val fusedLocation = LocationServices.getFusedLocationProviderClient(this)
            if (ActivityCompat.checkSelfPermission(
                    this,
                    android.Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                    this,
                    android.Manifest.permission.ACCESS_COARSE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                if (shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_COARSE_LOCATION)) {
                    Log.d("Binar", "Location Permission Denied")
                    Toast.makeText(
                        applicationContext,
                        "Location Permission Denied",
                        Toast.LENGTH_SHORT
                    ).show()

                    Handler(Looper.getMainLooper()).postDelayed({
                        requestPermissions(
                            arrayOf(
                                Manifest.permission.ACCESS_FINE_LOCATION,
                                Manifest.permission.ACCESS_COARSE_LOCATION
                            ),
                            PackageManager.PERMISSION_GRANTED
                        )
                        Log.d("Binar", "Asked Permission")
                    }, 1500)
                } else {
                    requestPermissions(
                        arrayOf(
                            Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_COARSE_LOCATION
                        ),
                        PackageManager.PERMISSION_GRANTED
                    )
                    Log.d("Binar", "Asked Permission")
                    Toast.makeText(
                        applicationContext,
                        "Asked Permission",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            } else {
                fusedLocation.lastLocation
                    .addOnSuccessListener(this) { location ->
                        longitude = location?.longitude
                        latitude = location?.latitude
                        Toast.makeText(
                            applicationContext,
                            "Longitude dan Latitude didapatkan $longitude, $latitude. Silakan Cek Logcat",
                            Toast.LENGTH_SHORT
                        ).show()
                        Log.d("Binar", "Longitude: $longitude, Latitude: $latitude.")
                    }
            }
        }
    }
}