package ewingta.domesticlogistic.driver.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;

import ewingta.domesticlogistic.driver.listeners.SmsListener;

public class SmsReceiver extends BroadcastReceiver {

    private static SmsListener smsListener;

    public static void bindListener(SmsListener listener) {
        smsListener = listener;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle data = intent.getExtras();

        if (data != null) {
            Object[] pDus = (Object[]) data.get("pdus");

            if (pDus != null) {
                for (Object pdu : pDus) {
                    SmsMessage smsMessage = SmsMessage.createFromPdu((byte[]) pdu);

                    String sender = smsMessage.getDisplayOriginatingAddress();
                    //You must check here if the sender is your provider and not another one with same text.

                    String messageBody = smsMessage.getMessageBody();

                    //Pass on the text to our listener.
                    if (smsListener != null) {
                        smsListener.messageReceived(messageBody);
                    }
                }
            }
        }
    }
}