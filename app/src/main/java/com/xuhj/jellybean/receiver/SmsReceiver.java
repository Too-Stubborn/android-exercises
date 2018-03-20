package com.xuhj.jellybean.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;

/**
 * 短信接收监听
 */
public class SmsReceiver extends BroadcastReceiver {

    private OnResult onResult;

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals("android.provider.Telephony.SMS_RECEIVED")) {
            Bundle bundle = intent.getExtras();
            SmsMessage msg = null;
            if (bundle != null) {
                Object[] smsObj = (Object[]) bundle.get("pdus");
                for (Object object : smsObj) {
                    msg = SmsMessage.createFromPdu((byte[]) object);
                    // Date date = new Date(msg.getTimestampMillis());// ʱ��
                    // SimpleDateFormat format = new SimpleDateFormat(
                    // "yyyy-MM-dd HH:mm:ss");
                    // String receiveTime = format.format(date);
                    // System.out.println("number:"
                    // + msg.getOriginatingAddress() + "   body:"
                    // + msg.getDisplayMessageBody() + "  time:"
                    // + msg.getTimestampMillis());

                    onResult.onResult(msg);

                }
            }
        }
    }

    public void onResult(OnResult result) {
        onResult = result;
    }

    public interface OnResult {
        void onResult(SmsMessage msg);
    }
}