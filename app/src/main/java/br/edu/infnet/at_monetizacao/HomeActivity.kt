package br.edu.infnet.at_monetizacao

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast

class HomeActivity : AppCompatActivity() {

    private lateinit var imgBtnFoto: ImageButton
    private lateinit var textViewLocation: TextView
    private lateinit var btnCheckLocation: Button

    private val REQUEST_CAMERA = 100
    private val REQUEST_LOCATION = 200
    private val PERMISSION_LOCATION = Manifest.permission_group.LOCATION
    //private val PERMISSION_COARSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        supportActionBar?.title = "Home"

        imgBtnFoto = findViewById(R.id.imgBtnFoto)
        textViewLocation = findViewById(R.id.textViewLocation)
        btnCheckLocation = findViewById(R.id.btnCheckLocation)

        btnCheckLocation.setOnClickListener {
            if(checkSelfPermission(PERMISSION_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                getCurrentLocation()
//                checkLocation()
            } else {
                if(shouldShowRequestPermissionRationale(Manifest.permission_group.LOCATION)) {
                    imprimeToast("É necessário o recurso de localização")
                }
                requestPermissions(
                    arrayOf(
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.ACCESS_FINE_LOCATION
                    ), REQUEST_LOCATION)
            }
        }

        imgBtnFoto.setOnClickListener {
            if(checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {

            } else {
                if (shouldShowRequestPermissionRationale(Manifest.permission.CAMERA)) {
                    imprimeToast("É necessário o uso da câmera para esta funcionalidade.")
                }
                requestPermissions(arrayOf(Manifest.permission.CAMERA), REQUEST_CAMERA)
            }
        }
    }

    private fun getCurrentLocation() {
        val locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        val GPS_PROVIDER = LocationManager.GPS_PROVIDER
        val NETWORK_PROVIDER = LocationManager.NETWORK_PROVIDER

        val isGpsHabilitado = locationManager.isProviderEnabled(GPS_PROVIDER)
        val isNetworkHabilitado = locationManager.isProviderEnabled(NETWORK_PROVIDER)

        when {
            isGpsHabilitado -> {getCurrentLocationByProvider(locationManager, GPS_PROVIDER)}
            else -> {imprimeToast("GPS Offline")}
        }
    }

    private val locationListener = object : LocationListener {
        override fun onLocationChanged(location: Location) {
            findViewById<TextView>(R.id.textViewLocation).text = "latitude: ${location.latitude} \n longitude: ${location.longitude}"
        }

        override fun onProviderDisabled(provider: String) {
            imprimeToast("provider off")
        }

        override fun onProviderEnabled(provider: String) {
            imprimeToast("provider on")
        }
    }

    private fun getCurrentLocationByProvider(locationManager: LocationManager, provider: String) {
        try {
            locationManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER,
                2000L, 0f, locationListener
            )
        } catch (e: SecurityException) {
            imprimeToast("deu merda")
        }
    }

    fun actionLocalizacao(view: View) {
        getCurrentLocation()
    }

    private fun checkLocation() {
        textViewLocation.text = "Verificando localização..."
    }

    private fun imprimeToast(msg: String) {
        Toast.makeText(
            this,
            msg,
            Toast.LENGTH_LONG
        ).show()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when(requestCode) {
            REQUEST_CAMERA -> {
                if (grantResults.isNotEmpty()
                    && permissions[0] == Manifest.permission.CAMERA
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    imprimeToast("Executando recurso de câmera.")
                } else {
                    imprimeToast("Recurso de câmera negado.")
                }
            }
            REQUEST_LOCATION -> {
                if (grantResults.isNotEmpty()) {
                    if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
                        getCurrentLocation()
//                        checkLocation()
                    } else {imprimeToast("Recurso de GPS negado.")}
                } else {
                    imprimeToast("Erro.")
                }
            }
            else -> {
                imprimeToast("Request code não identificado.")
            }
        }
    }
}