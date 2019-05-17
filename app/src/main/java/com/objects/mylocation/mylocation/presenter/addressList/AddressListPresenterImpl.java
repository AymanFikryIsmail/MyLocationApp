package com.objects.mylocation.mylocation.presenter.addressList;

import android.content.Context;
import android.widget.Toast;

import com.objects.mylocation.mylocation.model.helpers.local.database.MyAppDB;
import com.objects.mylocation.mylocation.model.pojo.AddressPojo;
import com.objects.mylocation.mylocation.utils.NetworkUtilities;
import com.objects.mylocation.mylocation.view.ui.addressList.AddressListView;

import java.util.List;

/**
 * Created by ayman on 2019-05-16.
 */

public class AddressListPresenterImpl implements AddressListPresenter {


    private AddressListView view;
    private Context context;

    public AddressListPresenterImpl(AddressListView view) {
        this.view = view;
        this.context = (Context) view;
        if (view != null) {
            view.showProgress();
        }

    }

    @Override
    public void deleteAddress(AddressPojo deletedItem) {
        MyAppDB.getAppDatabase(context).addressDao().delete(deletedItem);
        getAllAddresses();
    }
    @Override
    public void getAllAddresses() {
        List<AddressPojo> addressPojoList;
        addressPojoList = MyAppDB.getAppDatabase(context).addressDao().getAllAddresses();
        if (addressPojoList.size()!=0) {
           view.hideEmptyView();
        }
        else{
            view.showEmptyView();
        }
        view.setDataToRecyclerView(addressPojoList);
        if (view != null) {
            view.hideProgress();
        }
    }

}
