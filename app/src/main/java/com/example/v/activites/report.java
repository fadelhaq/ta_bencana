package com.example.v.activites;


import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;


import com.example.v.R;
import com.example.v.activites.api.ApiClient;
import com.example.v.activites.bencana.Bencana;
import com.example.v.activites.bencana.BencanaResponse;
import com.example.v.activites.bencana.BencanaService;
import com.example.v.activites.laporan.Laporan;
import com.example.v.activites.laporan.LaporanResponse;
import com.example.v.activites.laporan.LaporanService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class  report extends AppCompatActivity implements LocationListener {

    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private BencanaService bencanaService;
    private LaporanService laporanService;

    private Button getfoto, btlocation, bsend;
    private ImageView hasilfoto;
    private EditText iNama, iLaporan;
    private TextView textView_location;
    private Spinner spinner;
    private ArrayList<String> arrayBencana;
    Map<String, Integer> bencanaMap = new HashMap<>();


    LocationManager locationManager;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        getfoto = findViewById(R.id.ambil_foto);
        hasilfoto = findViewById(R.id.hasil_foto);
        iNama = findViewById(R.id.inputNama);
        spinner = findViewById(R.id.inputJenis);
        iLaporan = findViewById(R.id.inputLaporan);
        textView_location = findViewById(R.id.text_location);
        btlocation = findViewById(R.id.btn_location);
        bsend = findViewById(R.id.btn_send);

        bencanaService = ApiClient.getClient().create(BencanaService.class);
        laporanService = ApiClient.getClient().create(LaporanService.class);

        arrayBencana = new ArrayList<>();
        bencanaMap = new HashMap<>();

        getBencana();

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item,arrayBencana);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getApplicationContext(), adapter.getItem(i), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        if (ContextCompat.checkSelfPermission(report.this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(report.this, new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION
            }, 100);
        }

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[] { Manifest.permission.CAMERA }, 100);
        }

        getfoto.setOnClickListener(v -> {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
        });

        btlocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getLocation();
            }
        });

        bsend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendDatas();
            }
        });

    }

    private void sendDatas(){

        BitmapDrawable drawable = (BitmapDrawable) hasilfoto.getDrawable();
        Bitmap bitmap = drawable.getBitmap();
        String foto = BitmapUtils.getStringFromBitmap(bitmap);
        String namaPelapor = iNama.getText().toString().trim();
        Integer idJenisBencana = getKeyByValue(bencanaMap, spinner.getSelectedItem().toString());
        String lokasi = textView_location.getText().toString().trim();
        String keterangan = iLaporan.getText().toString().trim();

        Laporan laporan = new Laporan(namaPelapor, idJenisBencana, foto, lokasi, keterangan);

        Call<LaporanResponse> call = laporanService.sendLaporan(laporan);
        call.enqueue(new Callback<LaporanResponse>() {
            @Override
            public void onResponse(Call<LaporanResponse> call, Response<LaporanResponse> response) {
                if (response.isSuccessful()) {
                    LaporanResponse laporanResponse = response.body();
                    if (laporanResponse != null) {
                        String message = laporanResponse.getMessage();
                        Toast.makeText(report.this, message, Toast.LENGTH_SHORT).show();
                    }
                } else {
                    // Menampilkan pesan kesalahan
                    String errorMessage = "Gagal mengirim data: " + response.code() + " " + response.message();
                    Toast.makeText(report.this, errorMessage, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LaporanResponse> call, Throwable t) {
                Toast.makeText(report.this, "Gagal Terhubung Kw Server", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getBencana() {

        Call<BencanaResponse> call = bencanaService.getBencana();
        call.enqueue(new Callback<BencanaResponse>() {
            public void onResponse(Call<BencanaResponse> call, Response<BencanaResponse> response) {
                if (response.isSuccessful()) {
                    List<Bencana> bencanaList = response.body().getData();

                    Map<Integer, String> bencanaMap = new HashMap<>();
                    for (Bencana bencana : bencanaList) {
                        bencanaMap.put(bencana.getId(), bencana.getNama());
                        arrayBencana.add(bencana.getNama());
                    }

                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item,arrayBencana);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner.setAdapter(adapter);
                } else {
                    Toast.makeText(report.this, "Gagal mengambil data jenis bencana", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<BencanaResponse> call, Throwable t) {
                Toast.makeText(report.this, "Gagal mengambil data jenis bencana", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private Integer getKeyByValue(Map<String, Integer> map, String value) {
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            if (Objects.equals(value, entry.getKey())) {
                return entry.getValue();
            }
        }
        return null;
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

        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bitmap imageBitmap = (Bitmap) data.getExtras().get("data");
            hasilfoto.setImageBitmap(imageBitmap);
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
}