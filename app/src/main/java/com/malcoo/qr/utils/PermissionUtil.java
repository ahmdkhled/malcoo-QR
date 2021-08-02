package com.malcoo.qr.utils;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;

import androidx.core.app.ActivityCompat;

public class PermissionUtil {

    public static final int PERMISSION_ID = 120;

    public void requestCameraPermission(Activity activity){

            String[] permissions = {
                    Manifest.permission.CAMERA,

            };

            ActivityCompat.requestPermissions(
                    activity, permissions
                    , PERMISSION_ID
            );
        }


    public void showDialog(Context context, Activity activity){
        AlertDialog dialog=new AlertDialog.Builder(context)
                .setCancelable(false)
                .setTitle("Permission needed")
                .setMessage("location permission is needed for completing in app, so please accept permission")
                .setPositiveButton("accept permission", (dialog1, which) -> {
                    requestCameraPermission(activity);

                }).setNegativeButton("exit", (dialog12, which) -> {
                    activity.finish();
                }).create();
        dialog.show();

    }

}
