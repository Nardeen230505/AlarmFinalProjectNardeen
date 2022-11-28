package ow.nardeen.alarmfinalprojectnardeen;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

//
public class myReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle=intent.getExtras();
        if (intent.getAction().equalsIgnoreCase("com.example.Broadcast"))
        {
            String msg = bundle.getString("msg");
            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
        }

        if (intent.getAction().equalsIgnoreCase("android.provider.Telephony.SMS_RECEIVED"))
        // sms received
        {
            Toast.makeText(context, "sms received", Toast.LENGTH_SHORT).show();

        }

    }
}
