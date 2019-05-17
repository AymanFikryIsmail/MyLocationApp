package com.objects.mylocation.mylocation.view.ui.searchbylocation;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.libraries.places.api.Places;
import com.objects.mylocation.mylocation.R;

public class SearchActivity extends AppCompatActivity {

    PlaceAutocompleteFragment startPlaceAutocompleteFragment;
    private final static String API_KEY = "AIzaSyCeYHDhDctqGmb5APIdyWrd-imDO2DkQHc";//"AIzaSyBLJv51oC0sfDw418dpUs2vu7xT4tMezyw";
    Double latitude ,longitude   ;
    String placeName ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        initUI();
        setListeners();
    }
    public void initUI(){
        startPlaceAutocompleteFragment = (PlaceAutocompleteFragment) getFragmentManager()
                .findFragmentById(R.id.place_autocomplete_fragment_from);
    }
    public void setListeners(){
        Places.initialize(getApplicationContext(), API_KEY);
        startPlaceAutocompleteFragment = (PlaceAutocompleteFragment) getFragmentManager()
                .findFragmentById(R.id.place_autocomplete_fragment_from);
        AutocompleteFilter autocompleteFilter = new AutocompleteFilter.Builder().setTypeFilter(AutocompleteFilter.TYPE_FILTER_CITIES).build();
        startPlaceAutocompleteFragment.setFilter(autocompleteFilter);
        startPlaceAutocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                placeName = (String) place.getName();
                longitude = place.getLatLng().longitude;
                latitude = place.getLatLng().latitude;

                final Intent data = new Intent();
                data.putExtra("latitude", latitude);
                data.putExtra("longitude", longitude);
                data.putExtra("placeName", placeName);
                setResult(Activity.RESULT_OK, data);
                finish();
            }
            @Override
            public void onError(Status status) {
                Toast.makeText(getApplicationContext(),status.toString(),Toast.LENGTH_SHORT).show();

            }
        });



        AutocompleteFilter typeFilter1 = new AutocompleteFilter.Builder()
                .setCountry("EG")
                .build();
        startPlaceAutocompleteFragment.setFilter(typeFilter1);
    }
}
