package com.malcoo.qr;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.camera.core.AspectRatio;
import androidx.camera.core.CameraProvider;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.CameraX;
import androidx.camera.core.CameraXConfig;
import androidx.camera.core.Preview;
import androidx.camera.core.impl.PreviewConfig;
import androidx.camera.core.internal.ThreadConfig;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.camera.view.PreviewView;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Toast;

import com.google.common.util.concurrent.ListenableFuture;
import com.malcoo.qr.databinding.ActivityMainBinding;
import com.malcoo.qr.utils.PermissionUtil;

import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    PermissionUtil permissionUtil;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(this,R.layout.activity_main);
        permissionUtil=new PermissionUtil();




        permissionUtil.requestCameraPermission(this);


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PermissionUtil.PERMISSION_ID) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "hi", Toast.LENGTH_SHORT).show();
            }else{
                permissionUtil.showDialog(this,this);
            }
        }
    }
}