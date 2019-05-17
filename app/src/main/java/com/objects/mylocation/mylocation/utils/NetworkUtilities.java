package com.objects.mylocation.mylocation.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;



/**
 * Created by ayman on 2018-01-15.
 */

public final class NetworkUtilities {

    public static void networkConnectionFailure(Context context){
        if (context!= null){
            Toast.makeText(context, "networkConnectionFailure ", Toast.LENGTH_SHORT).show();
        }
    }

    //check if mobile is connected to network or not
    public static boolean isOnline(Context context) {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    public static void onResponseFailure(Context context,String errorMsg){
        if (context!= null){
            Toast.makeText(context, errorMsg, Toast.LENGTH_SHORT).show();
        }
    }

}
