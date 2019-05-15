package com.objects.mylocation.mylocation.view.ui.addressList;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.objects.mylocation.mylocation.R;
import com.objects.mylocation.mylocation.model.helpers.local.database.MyAppDB;
import com.objects.mylocation.mylocation.model.pojo.AddressPojo;
import com.objects.mylocation.mylocation.view.adapter.AddressListAdapter;
import com.objects.mylocation.mylocation.view.ui.addaddress.AddAddressActivity;

import java.util.ArrayList;
import java.util.List;

public class AddressListActivity extends AppCompatActivity {

    private RecyclerView addressListRecyclerView;
    private List<AddressPojo> addressPojoList = new ArrayList<>();

    private AddressListAdapter addressListAdapter;
    private Button addAddressBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address_list);
        addressListRecyclerView = findViewById(R.id.addressListId);
        addAddressBtn = findViewById(R.id.addAddressBtn);
        addAddressBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AddressListActivity.this, AddAddressActivity.class);
                startActivity(intent);
            }
        });


        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        addressListRecyclerView.setLayoutManager(mLayoutManager);
        addressListAdapter = new AddressListAdapter(this, addressPojoList);
        addressPojoList=null;
        getAddresses();
    }

    public void getAddresses(){



    }
    public void addAddress(){
     //   int tripId= (int) MyAppDB.getAppDatabase(context).tripDao().addTrip(tripDTO);
    }
    public void getAllAddresses(){
        //  tripDTOArrayList= MyAppDB.getAppDatabase(getContext()).tripDao().getAllTrips("waited", prefManager.getUserId());
    }
    public void delete(){
       // MyAppDB.getAppDatabase(this).addressDao().delete(null);
    }
}
