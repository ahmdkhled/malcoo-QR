package com.malcoo.qr;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.camera.core.AspectRatio;
import androidx.camera.core.Camera;
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
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.common.util.concurrent.ListenableFuture;
import com.google.mlkit.vision.barcode.Barcode;
import com.google.mlkit.vision.barcode.BarcodeScanner;
import com.google.mlkit.vision.barcode.BarcodeScanning;
import com.malcoo.qr.databinding.ActivityMainBinding;
import com.malcoo.qr.utils.BarcodeAnalyzer;
import com.malcoo.qr.utils.CameraUtil;
import com.malcoo.qr.utils.PermissionUtil;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    PermissionUtil permissionUtil;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(this,R.layout.activity_main);
        permissionUtil=new PermissionUtil();

        permissionUtil.requestCameraPermission(this);

        CameraUtil.getInstance().setOnBarcodeScannedListener(barcode -> {
            Log.d("BAR_CODE", "result : "+barcode.getRawValue());
            binding.barcodeValue.setText(barcode.getRawValue());
        });




    }



    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PermissionUtil.PERMISSION_ID) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                CameraUtil.getInstance().startCamera(this,this,binding.previewView);
            }else{
                permissionUtil.showDialog(this,this);
            }
        }
    }
}