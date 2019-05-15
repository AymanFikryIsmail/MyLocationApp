package com.objects.mylocation.mylocation.view.adapter;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.objects.mylocation.mylocation.R;
import com.objects.mylocation.mylocation.model.pojo.AddressPojo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by ayman on 2019-05-15.
 */

public class AddressListAdapter extends RecyclerView.Adapter<AddressListAdapter.MyViewHolder>{

    private Context context;
    private List<AddressPojo> addressPojos =  new ArrayList<>();

    public AddressListAdapter(Context context,List<AddressPojo> associationsTitle){
        this.context = context;
        this.addressPojos = associationsTitle;

    }

    @Override
    public AddressListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.address_list_row, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(AddressListAdapter.MyViewHolder holder, int position) {
        holder.bind(addressPojos.get(position));
    }


    @Override
    public int getItemCount() {
        return addressPojos.size();
    }


    //----------------------------------View Holder Class-------------------------------------------
    public class MyViewHolder extends RecyclerView.ViewHolder{

        public TextView cellTv , address_txt;

        public MyViewHolder(View itemView) {
            super(itemView);
//            cellTv = itemView.findViewById(R.id.cellTvCustomGridId);
//            address_txt = itemView.findViewById(R.id.address_id);
        }

        public void bind(final AddressPojo addressPojo){

            cellTv.setText(addressPojo.getRegionName());
            Geocoder geocoder;
            List<Address> addresses;
            geocoder = new Geocoder(context, Locale.getDefault());

            try {
                addresses = geocoder.getFromLocation(addressPojo.getLat(), addressPojo.getLng(), 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
                if (addresses.size()!=0){
                    String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
                    address_txt.setText(!TextUtils.isEmpty(address)?address: " خط العرض : "+addressPojo.getLat()+" خط الطول : "+addressPojo.getLng());
                }else {
                    address_txt.setText("العنوان لم يحدد");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public void updateList(List<AddressPojo> newlist) {
        addressPojos=newlist;
        this.notifyDataSetChanged();
    }

}
