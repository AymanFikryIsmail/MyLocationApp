package com.objects.mylocation.mylocation.view.ui.addressList;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.objects.mylocation.mylocation.R;
import com.objects.mylocation.mylocation.model.pojo.AddressPojo;
import com.objects.mylocation.mylocation.presenter.addressList.AddressListPresenter;
import com.objects.mylocation.mylocation.presenter.addressList.AddressListPresenterImpl;
import com.objects.mylocation.mylocation.utils.RecyclerItemTouchHelper;
import com.objects.mylocation.mylocation.view.adapter.AddressListAdapter;
import com.objects.mylocation.mylocation.view.ui.addaddress.AddAddressActivity;

import java.util.ArrayList;
import java.util.List;

public class AddressListActivity extends AppCompatActivity
        implements RecyclerItemTouchHelper.RecyclerItemTouchHelperListener, AddressListView {

    private RecyclerView addressListRecyclerView;
    private List<AddressPojo> addressPojoList = new ArrayList<>();
    private AddressListAdapter addressListAdapter;
    private LinearLayoutManager mLayoutManager;
    private Button addAddressBtn;
    private TextView emptyText;
    private ProgressBar pbLoading;

    AddressListPresenter addressListPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address_list);
        initView();
        setListeners();
        addressListPresenter = new AddressListPresenterImpl(this);

    }

    public void initView() {
        addressListRecyclerView = findViewById(R.id.addressListId);
        addAddressBtn = findViewById(R.id.addAddressBtn);
        emptyText = findViewById(R.id.emptyViewId);
        pbLoading = findViewById(R.id.pb_loading);

        mLayoutManager = new LinearLayoutManager(this);
        addressListRecyclerView.setLayoutManager(mLayoutManager);
        addressListAdapter = new AddressListAdapter(this, addressPojoList);
        ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new RecyclerItemTouchHelper(0,
                ItemTouchHelper.LEFT, this);
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(addressListRecyclerView);
        addressPojoList = null;

    }

    public void setListeners() {
        addAddressBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddressListActivity.this, AddAddressActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void showProgress() {

        pbLoading.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {

        pbLoading.setVisibility(View.GONE);
    }

    @Override
    public void showEmptyView() {
        addressListRecyclerView.setVisibility(View.GONE);
        emptyText.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideEmptyView() {
        addressListRecyclerView.setVisibility(View.VISIBLE);
        emptyText.setVisibility(View.GONE);
    }

    @Override
    public void setDataToRecyclerView(List<AddressPojo> addressList) {
        addressPojoList = addressList;//addressListPresenter.getAllAddresses();
        addressListAdapter.updateList(addressPojoList);
        addressListRecyclerView.setAdapter(addressListAdapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        addressListPresenter.getAllAddresses();
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position) {
        // backup of removed item for undo purpose
        final AddressPojo deletedItem = addressPojoList.get(viewHolder.getAdapterPosition());
        // remove the item from recycler view
        addressListAdapter.removeItem(viewHolder.getAdapterPosition());
        addressListPresenter.deleteAddress(deletedItem);
    }
}
