package com.objects.mylocation.mylocation.view.ui.addressList;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.objects.mylocation.mylocation.R;
import com.objects.mylocation.mylocation.model.helpers.local.database.MyAppDB;
import com.objects.mylocation.mylocation.model.pojo.AddressPojo;
import com.objects.mylocation.mylocation.utils.RecyclerItemTouchHelper;
import com.objects.mylocation.mylocation.view.adapter.AddressListAdapter;
import com.objects.mylocation.mylocation.view.ui.addaddress.AddAddressActivity;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class AddressListActivity extends AppCompatActivity
        implements RecyclerItemTouchHelper.RecyclerItemTouchHelperListener {

    private RecyclerView addressListRecyclerView;
    private List<AddressPojo> addressPojoList = new ArrayList<>();

    private AddressListAdapter addressListAdapter;
    private Button addAddressBtn;
    private TextView emptyText;
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
        emptyText = findViewById(R.id.emptyViewId);



        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        addressListRecyclerView.setLayoutManager(mLayoutManager);
        addressListAdapter = new AddressListAdapter(this, addressPojoList);
        ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new RecyclerItemTouchHelper(0,
                ItemTouchHelper.LEFT, this);
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(addressListRecyclerView);

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
    public void delete(AddressPojo deletedItem){
        MyAppDB.getAppDatabase(this).addressDao().delete(deletedItem);
    }

    @Override
    protected void onStart() {
        super.onStart();
         addressPojoList= MyAppDB.getAppDatabase(this).addressDao().getAllAddresses();
        addressListAdapter.updateList(addressPojoList);
        addressListRecyclerView.setAdapter(addressListAdapter);
        if (addressPojoList.size()!=0) {
            emptyText.setVisibility(View.GONE);
        }
        else{
            emptyText.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position) {
        // backup of removed item for undo purpose
        final AddressPojo deletedItem = addressPojoList.get(viewHolder.getAdapterPosition());
        // remove the item from recycler view
        addressListAdapter.removeItem(viewHolder.getAdapterPosition());
        delete(deletedItem);
    }
}
