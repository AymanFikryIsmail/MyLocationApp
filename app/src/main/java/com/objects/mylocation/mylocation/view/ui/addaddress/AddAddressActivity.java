package com.objects.mylocation.mylocation.view.ui.addaddress;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.LocationManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.objects.mylocation.mylocation.R;
import com.objects.mylocation.mylocation.model.helpers.local.database.MyAppDB;
import com.objects.mylocation.mylocation.model.pojo.AddressPojo;
import com.objects.mylocation.mylocation.utils.GPSTracker;
import com.objects.mylocation.mylocation.view.ui.searchbylocation.SearchActivity;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class AddAddressActivity extends FragmentActivity
        implements OnMapReadyCallback ,
        GoogleMap.OnMarkerDragListener,
        GoogleMap.OnMapLongClickListener {
    private Button saveLocation;
    private EditText regionNameEditTextId ,searchEditTextId;

    private static final String MYTAG = "MYTAG";
    public static final int REQUEST_ID_ACCESS_COURSE_FINE_LOCATION = 100;
    double latitude;
    double longitude;
    private GoogleMap mMap;
    String address = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_address);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        regionNameEditTextId = findViewById(R.id.regionNameEditTextId);
        searchEditTextId = findViewById(R.id.searchEditTextId);
        searchEditTextId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Intent intent = new Intent(AddAddressActivity.this, SearchActivity.class);
                startActivityForResult(intent, 11);
            }
        });
        saveLocation = findViewById(R.id.submitBtnMapId);
        saveLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getAddressName();
                AddressPojo addressPojo = new AddressPojo(regionNameEditTextId.getText().toString(),address,longitude
                        ,latitude);
                int addressId= (int) MyAppDB.getAppDatabase(AddAddressActivity.this).addressDao().addAddress(addressPojo);

                Log.v("address id " ,""+ addressId);
                finish();
            }
        });
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
        // Sét OnMapLoadedCallback Listener.
        googleMap.setOnMapLoadedCallback(new GoogleMap.OnMapLoadedCallback() {
            @Override
            public void onMapLoaded() {
                askPermissionsAndShowMyLocation();
            }
        });
//        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
//                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
//            mMap.setMyLocationEnabled(true);
//            // return;
//        }
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            //User has previously accepted this permission
            if (ActivityCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                mMap.setMyLocationEnabled(true);
            }
        } else {
            //Not in api-23, no need to prompt
            mMap.setMyLocationEnabled(true);
        }

//        getCurrentLocation();
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        mMap.getUiSettings().setZoomControlsEnabled(true);


        mMap.setOnMarkerDragListener(new GoogleMap.OnMarkerDragListener() {

            @Override
            public void onMarkerDrag(Marker marker) {
                LatLng newLocation = marker.getPosition();
                latitude = newLocation.latitude;
                longitude = newLocation.longitude;
                mMap.clear();
                mMap.addMarker(new MarkerOptions().position(newLocation).draggable(true).title("my loc1")).showInfoWindow();
                mMap.moveCamera(CameraUpdateFactory.newLatLng(newLocation));
            }

            @Override
            public void onMarkerDragEnd(Marker marker) {
                LatLng newLocationLatLng = marker.getPosition();
                getAddressName();
                mMap.clear();
                mMap.addMarker(new MarkerOptions().position(newLocationLatLng).draggable(true).title("my loc"+address)).showInfoWindow();
                mMap.moveCamera(CameraUpdateFactory.newLatLng(newLocationLatLng));
            }

            @Override
            public void onMarkerDragStart(Marker marker) {
                LatLng newLocation = marker.getPosition();
            }

        });
    }
    public void getAddressName(){
        Geocoder geocoder;
        List<Address> addresses = null;
        geocoder = new Geocoder(AddAddressActivity.this, Locale.getDefault());
        try {
            addresses = geocoder.getFromLocation(latitude, longitude, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
            if (addresses.size() > 0 && addresses != null) {
                address = addresses.get(0).getAddressLine(0);
            } else {
                address = "address is not determined ";
            }
            searchEditTextId.setText(address);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==11&&data!=null){
            if(resultCode == Activity.RESULT_OK) {
                latitude = data.getDoubleExtra("latitude",0);
                longitude = data.getDoubleExtra("longitude",0);
               address= data.getStringExtra("placeName");
                searchEditTextId.setText(TextUtils.isEmpty(address)?"":address);
                moveMap();
            } else {
                Toast.makeText(this, "open gps and try again ", Toast.LENGTH_LONG).show();

            }
        }
    }
    private void askPermissionsAndShowMyLocation() {
        // With API> = 23, you have to ask the user for permission to view their location.
        boolean isGPSEnabled = false;
        // flag for network status
        boolean isNetworkEnabled = false;
        // flag for GPS status
        boolean canGetLocation = false;
        LocationManager locationManager;

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        // getting GPS status
        isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

        // getting network status
        isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        if (Build.VERSION.SDK_INT >= 23) {
            // flag for GPS status
            if (!isGPSEnabled && !isNetworkEnabled) {
                // no network provider is enabled
                final Intent data = new Intent();
                data.putExtra("latitude", latitude);
                data.putExtra("longitude", longitude);

                setResult(Activity.RESULT_CANCELED, data);
                finish();
            } else {
//            if (accessCoarsePermission != PackageManager.PERMISSION_GRANTED
//                    || accessFinePermission != PackageManager.PERMISSION_GRANTED)

                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                        ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // The Permissions to ask user.
                    String[] permissions = new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,
                            Manifest.permission.ACCESS_FINE_LOCATION};
                    // Show a dialog asking the user to allow the above permissions.
                    ActivityCompat.requestPermissions(this, permissions,
                            REQUEST_ID_ACCESS_COURSE_FINE_LOCATION);

                    return;
                }
                this.showMyLocation();
            }

        } else {
            if (!isGPSEnabled && !isNetworkEnabled) {
                // no network provider is enabled
                final Intent data = new Intent();
                data.putExtra("latitude", latitude);
                data.putExtra("longitude", longitude);

                setResult(Activity.RESULT_CANCELED, data);
                finish();
            } else {
                this.showMyLocation();
            }


        }
        // Show current location on Map.
    }

    // When you have the request results.
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //
        switch (requestCode) {
            case REQUEST_ID_ACCESS_COURSE_FINE_LOCATION: {

                // Note: If request is cancelled, the result arrays are empty.
                // Permissions granted (read/write).
                if (grantResults.length > 1
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED
                        && grantResults[1] == PackageManager.PERMISSION_GRANTED) {

                    Toast.makeText(this, "Permission granted!", Toast.LENGTH_LONG).show();
                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                            && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                        mMap.setMyLocationEnabled(true);

                    }

                    // Show current location on Map.
                    this.showMyLocation();
                }
                // Cancelled or denied.
                else {
                    Toast.makeText(this, "Permission denied!", Toast.LENGTH_LONG).show();
                }
                break;
            }
        }
    }
    private void showMyLocation() {
        getCurrentLocation();
    }
    //Function to move the map
    private void moveMap() {
        //String to display current latitude and longitude
        String msg = latitude + ", "+longitude;
        //Creating a LatLng Object to store Coordinates
        LatLng latLng = new LatLng(latitude, longitude);
        getAddressName();

        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(latLng)             // Sets the center of the map to location user
                .zoom(15)                   // Sets the zoom
                .bearing(0)                // Sets the orientation of the camera to east
                .tilt(40)                   // Sets the tilt of the camera to 30 degrees
                .build();                   // Creates a CameraPosition from the builder
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));


        // Add Marker to Map
        MarkerOptions option = new MarkerOptions();
        option.title(" Current Location");
        option.snippet("....");
        option.position(latLng);
        option   .draggable(true) ;//Making the marker draggable

        Marker currentMarker = mMap.addMarker(option);
        currentMarker.showInfoWindow();
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();

    }
    //Getting current location
    private void getCurrentLocation() {

        mMap.clear();
        GPSTracker gps;
        gps = new GPSTracker(getApplicationContext());
        // check if GPS enabled
        if(gps.canGetLocation()){
            latitude =gps.getLatitude();//31.25841263
            longitude =gps.getLongitude();//29.98027325
            //   Toast.makeText(getApplicationContext(), "Your Location is - \nLat: " + latitude + "\nLong: " + longitude, Toast.LENGTH_LONG).show();
            moveMap();
        }else{

        }
    }

    @Override
    public void onMapLongClick(LatLng latLng) {

    }

    @Override
    public void onMarkerDragStart(Marker marker) {

    }

    @Override
    public void onMarkerDrag(Marker marker) {
        latitude = marker.getPosition().latitude;
        longitude = marker.getPosition().longitude;
    }

    @Override
    public void onMarkerDragEnd(Marker marker)
    {
        //Getting the coordinates
        latitude = marker.getPosition().latitude;
        longitude = marker.getPosition().longitude;

    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
//        googleApiClient.disconnect();
        super.onStop();
    }


}

