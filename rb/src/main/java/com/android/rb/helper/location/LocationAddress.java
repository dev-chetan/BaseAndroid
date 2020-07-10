package com.android.rb.helper.location;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.google.gson.Gson;

import java.util.List;
import java.util.Locale;

public class LocationAddress {
    private static final String TAG = "LocationAddress";

    public static void getAddressFromLocation(final double latitude, final double longitude,
                                              final Context context, final Handler handler) {
        Thread thread = new Thread() {
            @Override
            public void run() {
                Geocoder geocoder = new Geocoder(context, Locale.getDefault());
                String result = null;
                try {
                    List<Address> addressList = geocoder.getFromLocation(
                            latitude, longitude, 1);
                    Log.e(TAG, "Address : " + addressList.get(0).getAddressLine(0));
                    result = addressList.get(0).getAddressLine(0);

                    Message message = Message.obtain();
                    message.setTarget(handler);
                    if (result != null) {
                        message.what = 1;
                        Bundle bundle = new Bundle();
                        bundle.putString("address", result);
                        bundle.putString("json", new Gson().toJson(addressList));
                        message.setData(bundle);
                    }
                    message.sendToTarget();
                } catch (Exception e) {
                    Log.e(TAG, "Unable connect to Geocoder", e);
                }
            }
        };
        thread.start();
    }
}