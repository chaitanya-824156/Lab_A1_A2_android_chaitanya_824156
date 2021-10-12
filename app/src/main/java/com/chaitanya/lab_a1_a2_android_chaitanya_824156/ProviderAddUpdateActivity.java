package com.chaitanya.lab_a1_a2_android_chaitanya_824156;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.chaitanya.lab_a1_a2_android_chaitanya_824156.dao.ProviderDao;
import com.chaitanya.lab_a1_a2_android_chaitanya_824156.database.AppDatabase;
import com.chaitanya.lab_a1_a2_android_chaitanya_824156.database.DbClient;
import com.chaitanya.lab_a1_a2_android_chaitanya_824156.model.Provider;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class ProviderAddUpdateActivity extends AppCompatActivity implements View.OnClickListener, OnMapReadyCallback, GoogleMap.OnMapClickListener {
    private EditText etName, etPhoneNumber, etEmailAddress;
    private ProviderDao providerDao;
    private int providerId = -1;
    private GoogleMap mGoogleMap;
    private LatLng mLatLng;
    private Provider provider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_provider_add_update);
        etName = findViewById(R.id.providerName);
        etPhoneNumber = findViewById(R.id.providerPhone);
        etEmailAddress = findViewById(R.id.providerEmail);
        AppDatabase database = DbClient.getInstance(this).getAppDb();
        providerDao = database.providerDao();
        Button addUpdate = findViewById(R.id.addProvider);
        addUpdate.setOnClickListener(this);
        FragmentManager fm = getSupportFragmentManager();
        SupportMapFragment mapFragment = (SupportMapFragment) fm.findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        if (getIntent() != null) {
            providerId = getIntent().getIntExtra("providerId", -1);
        }
        if (providerId > 0) {
            addUpdate.setText("Update Provider");
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (providerId > 0) {
            provider = providerDao.getProviderByIds(providerId);
            etName.setText(provider.getProvider_name());
            etPhoneNumber.setText(provider.getProvider_phone());
            etEmailAddress.setText(provider.getProvider_email());
            createMarker();
        }
    }

    @Override
    public void onClick(View v) {
        String name = etName.getText().toString().trim();
        String phone = etPhoneNumber.getText().toString().trim();
        String mail = etEmailAddress.getText().toString().trim();
        if (TextUtils.isEmpty(name)) {
            Toast.makeText(this, "Enter Name", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(phone) || phone.length() < 10) {
            Toast.makeText(this, "Enter 10 Digit Phone Number", Toast.LENGTH_SHORT).show();

        } else if (TextUtils.isEmpty(mail) || !Patterns.EMAIL_ADDRESS.matcher(mail).matches()) {
            Toast.makeText(this, "Enter Valid Mail", Toast.LENGTH_SHORT).show();

        } else if (mLatLng == null) {
            Toast.makeText(this, "Select Location From Map", Toast.LENGTH_SHORT).show();

        } else {
            Provider newProvider = new Provider(name, mail, phone, mLatLng.latitude, mLatLng.longitude);
            if (providerId > 0) {
                newProvider.setProvider_id(providerId);
                providerDao.updateProvider(newProvider);
            } else {
                providerDao.insertProvider(newProvider);
            }
            finish();
        }
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mGoogleMap = googleMap;
        mGoogleMap.setOnMapClickListener(this);
        createMarker();
//        LatLng sydney = new LatLng(-34, 151);
//        mGoogleMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
//        mGoogleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }

    private void createMarker() {
        if (provider != null && mGoogleMap != null) {
            mGoogleMap.clear();
            LatLng latLng = new LatLng(provider.getProvider_lat(), provider.getProvider_lng());
            mLatLng = latLng;
            mGoogleMap.addMarker(new MarkerOptions()
                    .position(latLng)
                    .title(provider.getProvider_name()));
            mGoogleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        }
    }

    @Override
    public void onMapClick(@NonNull LatLng latLng) {
        mGoogleMap.clear();
        mLatLng = latLng;
        mGoogleMap.addMarker(new MarkerOptions()
                .position(latLng)
                .title(provider != null ? provider.getProvider_name() : etName.getText().toString()));
    }
}