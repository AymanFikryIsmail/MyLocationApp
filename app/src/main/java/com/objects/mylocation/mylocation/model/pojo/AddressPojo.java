package com.objects.mylocation.mylocation.model.pojo;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import java.io.Serializable;

/**
 * Created by ayman on 2019-05-15.
 */

@Entity(tableName = "address")
public class AddressPojo implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "regionName")
    private String regionName;
    @ColumnInfo(name = "addressDesc")
    private String addressDesc;
    @ColumnInfo(name = "lng")
    private Double lng;
    @ColumnInfo(name = "lat")
    private Double lat;
    public AddressPojo() {
    }

    @Ignore
    public AddressPojo(String regionName, String addressDesc) {
        this.regionName = regionName;
        this.addressDesc = addressDesc;
    }

    public int getId() {
        return id;
    }

    public Double getLng() {
        return lng;
    }

    public Double getLat() {
        return lat;
    }

    public String getRegionName() {
        return regionName;
    }

    public String getAddressDesc() {
        return addressDesc;
    }


    public void setId(int id) {
        this.id = id;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    public void setAddressDesc(String addressDesc) {
        this.addressDesc = addressDesc;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }
}
