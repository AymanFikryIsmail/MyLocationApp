package com.objects.mylocation.mylocation.model.helpers.local.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.objects.mylocation.mylocation.model.pojo.AddressPojo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ayman on 2019-02-18.
 */

@Dao
public interface AddressDao {
    @Insert
    long addAddress(AddressPojo tripDTO);
    @Query("SELECT * FROM address  ")//
    List<AddressPojo> getAllAddresses();

    @Update
    int updateTrip(AddressPojo address);

    @Insert
    void insertAll(ArrayList<AddressPojo> addressDTOArrayList);

    @Delete
    void delete(AddressPojo address);

}
