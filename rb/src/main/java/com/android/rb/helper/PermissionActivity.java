package com.android.rb.helper;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

/**
 * Created by ${Chetan Raval} on ${08/10/2017
 * }.
 */
public class PermissionActivity extends Activity {

    public static PermissionHandler permissionHandler;
    public static String[] perms;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setFinishOnTouchOutside(false);
        getWindow().setStatusBarColor(0);

        Log.e("TAG", perms.length + "");
        requestPermissions(perms, 100);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 100:
                if (isGrantedResult(grantResults)) {
                    permissionHandler.onGranted();
                    finish();
                } else {
                    permissionHandler.onDenied();
                    finish();
                }
                break;
        }
    }

    private boolean isGrantedResult(int[] grantResults) {
        for (int grantResult : grantResults)
            if (grantResult != PackageManager.PERMISSION_GRANTED) return false;
        return true;
    }
}
