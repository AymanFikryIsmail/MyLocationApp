package com.objects.mylocation.mylocation.view.ui.addressList;

import com.objects.mylocation.mylocation.model.pojo.AddressPojo;

import java.util.List;

/**
 * Created by ayman on 2019-05-16.
 */

public interface AddressListView {
    void showProgress();
    void hideProgress();
    void showEmptyView();
    void hideEmptyView();
    void setDataToRecyclerView(List<AddressPojo> addressList);
}
