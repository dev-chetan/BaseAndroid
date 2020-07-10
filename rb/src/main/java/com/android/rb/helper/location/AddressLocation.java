package com.android.rb.helper.location;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.util.List;
import java.util.Locale;

public class AddressLocation {
    private static final String TAG = "LocationAddress";

    public static void getAddressFromLocation(final String locationName,
                                              final Context context, final Handler handler) {
        Thread thread = new Thread() {
            @Override
            public void run() {
                Geocoder geocoder = new Geocoder(context, Locale.getDefault());
                String result = null;
                try {
                    List<Address> a = geocoder.getFromLocationName(locationName, 15);
                    for (int i = 0; i < a.size(); i++) {
                        double latitude = a.get(i).getLatitude();
                        double longitude = a.get(i).getLongitude();
                        String city = a.get(i).getLocality();
                        String country = a.get(i).getCountryName();
                        result = a.get(i).getAddressLine(0) + "@" + latitude + "@" + longitude;
                    }

                    Message message = Message.obtain();
                    message.setTarget(handler);
                    if (result != null) {
                        message.what = 1;
                        Bundle bundle = new Bundle();
                        bundle.putString("address", result);
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