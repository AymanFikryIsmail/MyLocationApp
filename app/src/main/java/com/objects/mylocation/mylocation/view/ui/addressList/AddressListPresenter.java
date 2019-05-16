package com.objects.mylocation.mylocation.view.ui.addressList;

import com.objects.mylocation.mylocation.model.pojo.AddressPojo;

import java.util.List;

/**
 * Created by ayman on 2019-05-16.
 */

public interface AddressListPresenter {
     void deleteAddress(AddressPojo deletedItem);
     void getAllAddresses() ;

    }
