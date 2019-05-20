package com.objects.mylocation.mylocation.braocasts;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

public class NetwrokBroadCast extends BroadcastReceiver {
    private Handler handler = new Handler();
    public static String state;
    private Context netcontext;
    public void onReceive(Context context, Intent intent)
    {
        netcontext = context;
        NetworkInfo info = intent.getParcelableExtra(ConnectivityManager.EXTRA_NETWORK_INFO);

        if(null != info)
        {
            state = getNetworkStateString(info.getState());
            Log.i("----------Network State",state);
        }
    }

    private String getNetworkStateString(NetworkInfo.State state)
    {
        String stateString = "Unknown";
        switch(state)
        {
            case CONNECTED:         stateString = "Connected";      break;

            case CONNECTING:        stateString = "Connecting";     break;

            case DISCONNECTED:

                stateString = "Disconnected";
                handler.removeCallbacks(sendUpdatesToUI);
                handler.post(sendUpdatesToUI);

                break;

            case DISCONNECTING:     stateString = "Disconnecting";  break;

            case SUSPENDED:         stateString = "Suspended";      break;

            default:                stateString = "Unknown";        break;
        }
        return stateString;
    }


    private Runnable sendUpdatesToUI = new Runnable()
    {
        public void run()
        {
            Toast.makeText(netcontext,"Please Check Your Internet Connection",Toast.LENGTH_SHORT).show();
        }
    }; }