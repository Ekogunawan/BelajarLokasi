package com.gmail.akakom16.eko.belajarlokasi;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
//TODO 1 : Yaitu program diatas adalah package yang sudah tersedia dilam class java dan kita tinggal memenggil package tersebut
public class MainActivity extends AppCompatActivity implements LocationListener, SensorEventListener {
    private TextView latituteField;
    private TextView longitudeField;
    private LocationManager locationManager;
    private String provider;
    private ImageView image;
    private float currentDegree = 0f;
    private SensorManager mSensorManager;
    TextView tvHeading;
//TODO 2 : Yaitu program diatas adalah perintah untuk mendeklarasikan variabel apa saja yang ada di class xml dan mendeklarasikan variabel baru
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//TODO 3 : Yaitu program diatas mendeklarasikan method oncreat yaitu untuk ketika aplikasi pertama di ajalankan
        image = (ImageView) findViewById(R.id.imageViewCompass);
        tvHeading = (TextView) findViewById(R.id.tvHeading);
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
//TODO 4 : Yaitu program diatas memanggil variabel yang sudah dideklarasikan dengan mengambil nilai dari class R

        latituteField = (TextView) findViewById(R.id.TextView02);
        longitudeField = (TextView) findViewById(R.id.TextView04);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        provider = locationManager.getBestProvider(criteria, false);
        //TODO 5 : Yaitu program diatas adalah memanggil fungsi dari lokasi service yang sudah di sediakan di class java
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Location location = locationManager.getLastKnownLocation(provider);
        if (location != null) {
            System.out.println("Provider " + provider + " has been selected.");
            onLocationChanged(location);
        } else {
            latituteField.setText("Location not available");
            longitudeField.setText("Location not available");
        }
    }
    //TODO 6 : Yaitu program diatas adalah membuat suatu kondisi if else , yang dimana untuk menenentukan acces lokasi yang di masukkan

    @Override
    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(this,
                mSensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION),
                SensorManager.SENSOR_DELAY_GAME);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        locationManager.requestLocationUpdates(provider, 400, 1, this);
        //TODO 7 : Yaitu program diatas mendeklarasikan method onResume yaitu untuk mendeklarasikan isi sensor dari sensore manager
    }
    @Override
    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this);
        locationManager.removeUpdates(this);
        //TODO 8 : Yaitu program diatas mendeklarasikan method onPause yaitu untuk menhentikan ketika perintah dalam program yaitu yang dihentikan adalah senseor manager dan locasi manager
    }
    @Override
    public void onLocationChanged(Location location) {
        double lat = (location.getLatitude());
        double lng = (location.getLongitude());
        latituteField.setText(String.valueOf(lat));
        longitudeField.setText(String.valueOf(lng));
        //TODO 9 : Yaitu program diatas mendeklarasikan method onlocationChanged yang gunanya utnuk menentukan lokasi
    }
    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
    }
    //TODO 10 : Yaitu program diatas mendeklarasikan method onstatus chaned yang menentukan status yang digunakan
    @Override
    public void onProviderEnabled(String provider) {
        Toast.makeText(this, "Enabled new provider " + provider,
                Toast.LENGTH_SHORT).show();
        //TODO 11 : Yaitu program diatas mendeklarasikan method onlocationChanged
    }
    @Override
    public void onProviderDisabled(String provider) {
        Toast.makeText(this, "Disabled provider " + provider,
                Toast.LENGTH_SHORT).show();
        //TODO 12 : Yaitu program diatas mendeklarasikan method onproviderdisable denan mengambil niali dari panjang data
    }
    @Override
    public void onSensorChanged(SensorEvent event) {
        float degree = Math.round(event.values[0]);

        tvHeading.setText("Heading: " + Float.toString(degree) + " degrees");
        RotateAnimation ra = new RotateAnimation(
                currentDegree, -degree,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
                0.5f);

        ra.setDuration(210);
        ra.setFillAfter(true);

        image.startAnimation(ra);
        currentDegree = -degree;
        //TODO 13 : Yaitu program diatas mendeklarasikan method onsensorchanged, yaitu untuk menentukan sensor akan bargerak kemana sesuai arah yang sudah di tentukan
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {     } }

