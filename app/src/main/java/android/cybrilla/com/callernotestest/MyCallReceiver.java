package android.cybrilla.com.callernotestest;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;


/**
 * Created by sakshiagarwal on 29/01/16.
 */

public class MyCallReceiver extends BroadcastReceiver
{
    private static int lastState = TelephonyManager.CALL_STATE_IDLE;
    private static boolean isIncoming;
    private static String savedNumber;

    @Override
    public void onReceive(Context context, Intent intent)
    {
        try
        {
            if (intent.getAction().equals("android.intent.action.NEW_OUTGOING_CALL"))
            {
                savedNumber = intent.getExtras().getString("android.intent.extra.PHONE_NUMBER");
            }
            else
            {
                String stateStr = intent.getExtras().getString(TelephonyManager.EXTRA_STATE);
                Toast.makeText(context, stateStr, Toast.LENGTH_LONG).show();
                Log.e("Different States:    " , "       " +stateStr);
                String number = intent.getExtras().getString(TelephonyManager.EXTRA_INCOMING_NUMBER);
                int state = 0;
                if(stateStr.equals(TelephonyManager.EXTRA_STATE_RINGING) || stateStr.equals(TelephonyManager.CALL_STATE_RINGING) || stateStr.equals(TelephonyManager.EXTRA_STATE_OFFHOOK) || stateStr.equals(TelephonyManager.CALL_STATE_OFFHOOK))
                {
                    Toast.makeText(context, "Toast here is working in ringing mode", Toast.LENGTH_LONG);
                    Log.e("Ringing || Offhook", "     " +stateStr);
                    state = TelephonyManager.CALL_STATE_RINGING;
                }
                else if(stateStr.equals(TelephonyManager.EXTRA_STATE_IDLE) || stateStr.equals(TelephonyManager.CALL_STATE_IDLE))
                {
                    state = TelephonyManager.CALL_STATE_IDLE;
                    Log.e("Call state", +state+ "    " +stateStr+"   TelephonyManager.EXTRA_STATE_IDLE ");
                }

                Log.e("Mycallreceiver", "state is...:   " +state);
                Log.e("Mycallreceiver", "stateStr is...:   " +stateStr);
                Log.e("Mycallreceiver", "number is...:   " + number);
                onCallStateChanged(context, state, number);
            }

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public void onCallStateChanged(Context context, int state, String number)
    {
        if(lastState == state)
        {
            return;
        }
        switch (state)
        {
            case TelephonyManager.CALL_STATE_RINGING:
                isIncoming = true;
                savedNumber = number;
                Log.e("Inside MycallReceiver", "OnIncomingCallStarted");
                final Intent intent = new Intent(context, StandOutExampleActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                context.startActivity(intent);
                break;

            case TelephonyManager.CALL_STATE_IDLE:
                if(isIncoming)
                {
                  // do nothing for now
                }
                break;
        }
        lastState = state;
    }
}