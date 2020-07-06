package com.android.rb.helper;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

/**
 * Created by ${Chetan Raval} on ${08/10/2017
 * }.
 */
public class PermissionUtil {

    public static void permission(AppCompatActivity mContext, String[] perms, PermissionHandler permissionHandler) {
        PermissionActivity.permissionHandler = null;
        if (Build.VERSION.SDK_INT >= 23) {
            if (isPermissionGranted(perms, mContext)) {
                sendRequestPermission(mContext, permissionHandler, perms);
            } else {
                permissionHandler.onGranted();
            }
        } else {
            permissionHandler.onGranted();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private static void sendRequestPermission(AppCompatActivity mContext, PermissionHandler permissionHandler, String[] perms) {
        PermissionActivity.permissionHandler = permissionHandler;
        PermissionActivity.perms = perms;
        Intent intent = new Intent(mContext, PermissionActivity.class);
        intent.putExtra("EXTRA_PERMISSION_LIST", perms);
        mContext.startActivity(new Intent(mContext, PermissionActivity.class));
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private static boolean isPermissionGranted(String[] perms, Context mContext) {
        for (String perm : perms)
            if (mContext.checkSelfPermission(perm) != PackageManager.PERMISSION_GRANTED)
                return true;
        return false;
    }
}
