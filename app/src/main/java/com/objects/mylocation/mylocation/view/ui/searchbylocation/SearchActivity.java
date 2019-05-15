package com.objects.mylocation.mylocation.view.ui.searchbylocation;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.objects.mylocation.mylocation.R;

public class SearchActivity extends AppCompatActivity {

    PlaceAutocompleteFragment startPlaceAutocompleteFragment;
    private final static String API_KEY = "AIzaSyBLJv51oC0sfDw418dpUs2vu7xT4tMezyw";
    Double startLat ,startLng ,endLng ,endLat  ;
    String placeStartName ,placeDestination;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        startPlaceAutocompleteFragment = (PlaceAutocompleteFragment) getFragmentManager()
                .findFragmentById(R.id.place_autocomplete_fragment_from);
        initAutoComplete();
    }
    void initAutoComplete(){

       // Places.initialize(getApplicationContext(), API_KEY);

        startPlaceAutocompleteFragment = (PlaceAutocompleteFragment) getFragmentManager()
                .findFragmentById(R.id.place_autocomplete_fragment_from);
        AutocompleteFilter autocompleteFilter = new AutocompleteFilter.Builder().setTypeFilter(AutocompleteFilter.TYPE_FILTER_CITIES).build();
        startPlaceAutocompleteFragment.setFilter(autocompleteFilter);

        startPlaceAutocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                //Toast.makeText(getApplicationContext(),place.getName().toString(),Toast.LENGTH_SHORT).show();
                placeStartName = (String) place.getName();
                startLng = place.getLatLng().longitude;
                startLat = place.getLatLng().latitude;
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
