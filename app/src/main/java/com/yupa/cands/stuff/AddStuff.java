package com.yupa.cands.stuff;

import android.Manifest;
import android.app.Dialog;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.yupa.cands.MainActivity;
import com.yupa.cands.R;
import com.yupa.cands.db.DBController;
import com.yupa.cands.db.Stuff;
import com.yupa.cands.utils.ShowMessage;

import java.io.File;

public class AddStuff extends AppCompatActivity {

    private static final String TAG = "AddStuff_MAP";
    private static final String COARSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    private static final String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    private boolean mLocationPermissionGranted = false;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1234;

    private GoogleMap mMap;
    private Location mCurrentLocation;
    private final float DEFAULT_ZOOM = 15f;

    Button btnSubmit;
    //DB
    private DBController dbController;
    //UI
    EditText edtName, edtQuantitiy, edtTag, edtDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_stuff);
        setTitle("Add Stuff's Information");
        File mPic = (File) getIntent().getExtras().get("picFile");
        ImageView iv = findViewById(R.id.imageView);
        iv.setImageURI(Uri.fromFile(mPic));
        if (isServiceOK()) {
            getLocationPermission();
        }
        final String pPath = mPic.getAbsolutePath();
        btnSubmit = findViewById(R.id.btnSubmit);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // UI
                edtName = findViewById(R.id.edtTxtName);
                edtQuantitiy = findViewById(R.id.edtTxtQuantity);
                edtTag = findViewById(R.id.edtTxtTag);
                edtDescription = findViewById(R.id.edtDescription);
                //get all needed values
                String name = edtName.getText().toString();
                int quantity = Integer.parseInt(edtQuantitiy.getText().toString());
                String tag = edtTag.getText().toString();
                String description = edtDescription.getText().toString();

                if (TextUtils.isEmpty(name)) {
                    edtName.setError("Stuff Name field is empty/not valid");
                    return;
                }
                if (quantity <= 0 || TextUtils.isEmpty(edtQuantitiy.getText())) {
                    edtQuantitiy.setError("Quantitiy field is empty/not valid");
                    return;
                }
                if (TextUtils.isEmpty(tag)) {
                    edtTag.setError("tag field is empty/not valid");
                    return;
                }
                if (TextUtils.isEmpty(description)) {
                    edtDescription.setError("description field is empty/not valid");
                    return;
                }
                //init db controller
                dbController = new DBController(AddStuff.this);

                Stuff stuff = new Stuff();
                if (mCurrentLocation != null) {
                    stuff.set_laitude(mCurrentLocation.getAltitude());
                    stuff.set_longitude(mCurrentLocation.getLongitude());
                } else {// give default
                    stuff.set_laitude(0.00);
                    stuff.set_longitude(0.00);
                }
                stuff.set_description(description);
                stuff.set_picture(pPath);
                stuff.set_tag(tag);
                stuff.set_name(name);
                stuff.set_quantity(quantity);
                dbController.addStuff(stuff);
                Intent intent = new Intent(AddStuff.this, MainActivity.class);
                startActivity(intent);
                AddStuff.this.finish();

            }
        });


    }

    /**
     * detect google service available or not
     *
     */
    public boolean isServiceOK() {
        Log.d(TAG, "is ok?");
        int available = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(this);
        if (available == ConnectionResult.SUCCESS) {
            Log.d(TAG, "is ok");
            return true;
        } else if (GoogleApiAvailability.getInstance().isUserResolvableError(available)) {
            Log.d(TAG, "is not good:but we can fix it.");
            Dialog dialog = GoogleApiAvailability.getInstance().getErrorDialog(this, available, -1);
            dialog.show();
        } else {
            Toast.makeText(this, "we can't make map requests", Toast.LENGTH_SHORT).show();
        }
        return false;
    }

    /**
     * get device location
     */
    private void getDeviceLocation() {
        FusedLocationProviderClient mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        try {
            if (mLocationPermissionGranted) {

                Task location = mFusedLocationProviderClient.getLastLocation();
                location.addOnCompleteListener(this, new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "found location1");
                            mCurrentLocation = (Location) task.getResult();
                            moveCamera(new LatLng(mCurrentLocation.getLatitude(), mCurrentLocation.getLongitude()), DEFAULT_ZOOM);
                            mMap.setMyLocationEnabled(true);

                        } else {
                            ShowMessage.showCenter(AddStuff.this, "No Location information!!!");
                            Log.d(TAG, "failed location1");
                        }
                    }
                });
            }

        } catch (SecurityException e) {
            Log.e(TAG, "getDeviceLocation SecurityException" + e.getMessage());
        }
    }

    private  void  moveCamera(LatLng latLng,float zoom){
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,zoom));
    }
    /**
     * set up basic map
     */
    private void initMap() {
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                mMap = googleMap;
                getDeviceLocation();

            }
        });
    }

    private void getLocationPermission() {
        String[] permissions = {Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION};
        if (ContextCompat.checkSelfPermission(this.getApplicationContext(), COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(this.getApplicationContext(), FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            mLocationPermissionGranted = true;
            initMap();
        } else {
            ActivityCompat.requestPermissions(this, permissions, LOCATION_PERMISSION_REQUEST_CODE);
        }
    }

    /**
     * request permission
     *
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        mLocationPermissionGranted = false;
        switch (requestCode) {
            case LOCATION_PERMISSION_REQUEST_CODE: {
                if (grantResults.length > 0) {
                    for (int i : grantResults) {
                        if (i != PackageManager.PERMISSION_GRANTED) {
                            mLocationPermissionGranted = false;
                            return;
                        }
                    }
                    mLocationPermissionGranted = true;
                }
            }
        }
    }
}
