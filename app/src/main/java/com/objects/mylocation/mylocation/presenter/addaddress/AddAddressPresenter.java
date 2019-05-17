package com.objects.mylocation.mylocation.presenter.addaddress;

import android.content.Context;

import com.objects.mylocation.mylocation.model.pojo.AddressPojo;

/**
 * Created by ayman on 2019-05-16.
 */

public interface AddAddressPresenter {
     void getAddressName( double latitude ,  double longitude);

     void saveAddressDetails(String addressName , String addressDesc, double longitude ,  double latitude);
    void displayLocationSettingsRequest();
    }
