package com.objects.mylocation.mylocation.presenter.addaddress;

import android.app.Activity;
import android.app.NotificationManager;
import android.content.Context;
import android.content.IntentSender;
import android.location.Address;
import android.location.Geocoder;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.objects.mylocation.mylocation.MainActivity;
import com.objects.mylocation.mylocation.model.helpers.local.database.MyAppDB;
import com.objects.mylocation.mylocation.model.pojo.AddressPojo;
import com.objects.mylocation.mylocation.utils.NetworkUtilities;
import com.objects.mylocation.mylocation.utils.NotificationHelper;
import com.objects.mylocation.mylocation.view.ui.addaddress.AddAddressActivity;
import com.objects.mylocation.mylocation.view.ui.addaddress.AddAddressView;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import static android.content.ContentValues.TAG;

/**
 * Created by ayman on 2019-05-16.
 */

public class AddAddressPresenterImpl implements AddAddressPresenter {


    private AddAddressView view;
    private Context context;
    private NotificationHelper notificationHelper;

    public AddAddressPresenterImpl(AddAddressView view) {
        this.view = view;
        this.context = (Context) view;
        notificationHelper=new NotificationHelper(context);
        if (!NetworkUtilities.isOnline(context)) {
            Toast.makeText(context, "Please check your internet connection", Toast.LENGTH_LONG).show();
        }
    }
    public void getAddressName( double latitude ,  double longitude) {
        Geocoder geocoder;
        List<Address> addresses = null;
        String address = "";
        geocoder = new Geocoder(context, Locale.getDefault());
        try {
            addresses = geocoder.getFromLocation(latitude, longitude, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
            if (addresses.size() > 0 && addresses != null) {
                address = addresses.get(0).getAddressLine(0);
            } else {
                address = "address is not determined ";
            }
            view.setSearchText(address);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void saveAddressDetails(String addressName , String addressDesc, double longitude ,  double latitude){
        AddressPojo addressPojo = new AddressPojo(addressName, addressDesc, longitude
                , latitude);
        int addressId = (int) MyAppDB.getAppDatabase(context).addressDao().addAddress(addressPojo);
        notificationHelper.createNotification(addressPojo);

        Log.v("address id ", "" + addressId);

    }

    @Override
    public void displayLocationSettingsRequest() {
        GoogleApiClient googleApiClient = new GoogleApiClient.Builder(context)
                .addApi(LocationServices.API).build();
        googleApiClient.connect();

        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(10000);
        locationRequest.setFastestInterval(10000 / 2);

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder().addLocationRequest(locationRequest);
        builder.setAlwaysShow(true);

        PendingResult<LocationSettingsResult> result = LocationServices.SettingsApi.checkLocationSettings(googleApiClient, builder.build());
        result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
            @Override
            public void onResult(LocationSettingsResult result) {
                final Status status = result.getStatus();
                switch (status.getStatusCode()) {
                    case LocationSettingsStatusCodes.SUCCESS:
                        Log.i(TAG, "All location settings are satisfied.");
                        break;
                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                        Log.i(TAG, "Location settings are not satisfied. Show the user a dialog to upgrade location settings ");

                        try {
                            // Show the dialog by calling startResolutionForResult(), and check the result
                            // in onActivityResult().
                            status.startResolutionForResult((Activity) context, 111);
                        } catch (IntentSender.SendIntentException e) {
                            Log.i(TAG, "PendingIntent unable to execute request.");
                        }
                        break;
                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                        Log.i(TAG, "Location settings are inadequate, and cannot be fixed here. Dialog not created.");
                        break;
                }
            }
        });
    }
}
