package com.chaitanya.lab_a1_a2_android_chaitanya_824156;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import com.chaitanya.lab_a1_a2_android_chaitanya_824156.dao.ProviderDao;
import com.chaitanya.lab_a1_a2_android_chaitanya_824156.database.DbClient;
import com.chaitanya.lab_a1_a2_android_chaitanya_824156.model.Provider;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnInfoWindowClickListener {

    private GoogleMap mMap;
    private ProviderDao providerDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_maps);
        providerDao = DbClient.getInstance(this).getAppDb().providerDao();
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setOnInfoWindowClickListener(this);
        List<Provider> providerList = providerDao.getAllProviders();
        createProviderMarkers(providerList);
    }

    private void createProviderMarkers(List<Provider> providerList) {
        mMap.clear();
        for(Provider provider:providerList){
            LatLng providerLocation = new LatLng(provider.getProvider_lat(),provider.getProvider_lng());
            mMap.addMarker(new MarkerOptions().position(providerLocation).title(provider.getProvider_name()).icon(bitmapDescriptorFromVector())).setTag(provider.getProvider_id());
            mMap.moveCamera(CameraUpdateFactory.newLatLng(providerLocation));
        }
    }
    private BitmapDescriptor bitmapDescriptorFromVector() {
        Drawable vectorDrawable = ContextCompat.getDrawable(this, R.drawable.ic_baseline_account_circle_24);
        vectorDrawable.setBounds(0, 0, vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight());
        Bitmap bitmap = Bitmap.createBitmap(vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        vectorDrawable.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }
    public void passProviderIdToProductsActivity(int  id) {
        Intent productIntent = new Intent(this, ProviderProductsActivity.class);
        productIntent.putExtra("provider_id", id);
        startActivity(productIntent);
    }
    @Override
    public void onInfoWindowClick(@NonNull Marker marker) {
       passProviderIdToProductsActivity((Integer) marker.getTag());
    }
}