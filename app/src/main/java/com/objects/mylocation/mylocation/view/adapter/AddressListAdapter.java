package com.objects.mylocation.mylocation.view.adapter;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
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
    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView regionNameTv, addressDesctxt;
        public RelativeLayout viewBackground, viewForeground;

        public MyViewHolder(View itemView) {
            super(itemView);
            regionNameTv = itemView.findViewById(R.id.regionNameId);
            addressDesctxt = itemView.findViewById(R.id.regionDescId);
            viewBackground = itemView.findViewById(R.id.view_background);
            viewForeground = itemView.findViewById(R.id.view_foreground);
        }

        public void bind(final AddressPojo addressPojo) {

            regionNameTv.setText(addressPojo.getRegionName());
            addressDesctxt.setText(addressPojo.getAddressDesc());
        }
    }
    public void removeItem(int position) {
        addressPojos.remove(position);
        // notify the item removed by position
        // to perform recycler view delete animations
        // NOTE: don't call notifyDataSetChanged()
        notifyItemRemoved(position);
    }
    public void updateList(List<AddressPojo> newlist) {
        addressPojos=newlist;
        this.notifyDataSetChanged();
    }

}
