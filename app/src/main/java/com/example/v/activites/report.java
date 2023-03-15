package com.example.v.activites;


import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;


import com.example.v.R;

import java.io.File;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class  report extends AppCompatActivity implements LocationListener {
    private static final int pic_id = 123;

    private Button getfoto, btlocation, bsend;
    private ImageView hasilfoto;
    private EditText iNama, iJenis, iLaporan;
    private TextView textView_location;
    LocationManager locationManager;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        getfoto = findViewById(R.id.ambil_foto);
        hasilfoto = findViewById(R.id.hasil_foto);

        iNama = findViewById(R.id.inputNama);
        iJenis = findViewById(R.id.inputJenis);
        iLaporan = findViewById(R.id.inputLaporan);

        textView_location = findViewById(R.id.text_location);
        btlocation = findViewById(R.id.btn_location);

        bsend = findViewById(R.id.btn_send);


        if (ContextCompat.checkSelfPermission(report.this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(report.this, new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION
            }, 100);
        }
        bsend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveUser(createRequest());
            }
        });


        btlocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getLocation();
            }
        });
        getfoto.setOnClickListener(v -> {
            Intent camera_intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(camera_intent, pic_id);
        });

    }


    @SuppressLint("MissingPermission")
    private void getLocation() {
        try {
            locationManager = (LocationManager) getApplicationContext().getSystemService(LOCATION_SERVICE);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 5, report.this);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == pic_id) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            hasilfoto.setImageBitmap(photo);
        }
    }


    @Override
    public void onLocationChanged(@NonNull Location location) {
        Toast.makeText(this, "" + location.getLatitude() + "," + location.getLongitude(), Toast.LENGTH_SHORT).show();

        try {
            Geocoder geocoder = new Geocoder(report.this, Locale.getDefault());
            List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
            String address = addresses.get(0).getAddressLine(0);

            textView_location.setText(address);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        LocationListener.super.onStatusChanged(provider, status, extras);
    }

    @Override
    public void onProviderEnabled(@NonNull String provider) {
        LocationListener.super.onProviderEnabled(provider);
    }

    @Override
    public void onProviderDisabled(@NonNull String provider) {
        LocationListener.super.onProviderDisabled(provider);
    }

    public UserRequest createRequest() {
        UserRequest userRequest = new UserRequest();
        userRequest.setiNama(iNama.getText().toString());
        userRequest.setiJenis(iJenis.getText().toString());
        userRequest.setiLaporan(iLaporan.getText().toString());
        userRequest.setTextView_location(textView_location.getText().toString());

        return userRequest;
    }
    public void saveUser(UserRequest userRequest) {

        Call<UserResponse> userResponseCall = ApiClient.getUserService().saveUser(userRequest);
        userResponseCall.enqueue(new Callback<UserResponse>() {

            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {

                if (response.isSuccessful()){
                    Toast.makeText(report.this, "Saved Successfully", Toast.LENGTH_SHORT).show();

                }else{
                    Toast.makeText(report.this, "Request failed", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                Toast.makeText(report.this, "Request failed"+t.getLocalizedMessage(),Toast.LENGTH_SHORT).show();

            }

        });

    }
}


















