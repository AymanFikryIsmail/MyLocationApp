package com.objects.mylocation.mylocation.presenter.addaddress;

import android.app.NotificationManager;
import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.util.Log;

import com.objects.mylocation.mylocation.model.helpers.local.database.MyAppDB;
import com.objects.mylocation.mylocation.model.pojo.AddressPojo;
import com.objects.mylocation.mylocation.utils.NotificationHelper;
import com.objects.mylocation.mylocation.view.ui.addaddress.AddAddressActivity;
import com.objects.mylocation.mylocation.view.ui.addaddress.AddAddressView;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

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
}
