package com.malcoo.qr.utils;

import android.content.Context;

import androidx.camera.core.Camera;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.Preview;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.camera.view.PreviewView;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.LifecycleOwner;

import com.google.common.util.concurrent.ListenableFuture;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;

public class CameraUtil {
    private  static CameraUtil cameraUtil;

    public static CameraUtil getInstance(){
        return cameraUtil==null?cameraUtil=new CameraUtil():cameraUtil;
    }

    public void startCamera(Context context, LifecycleOwner owner, PreviewView previewView){

        try {
            Preview preview = new Preview.Builder().build();
            ListenableFuture<ProcessCameraProvider> cameraProviderFuture = ProcessCameraProvider.getInstance(context);

// used to bind the lifecycle of camera to the lifecycle owner
            ProcessCameraProvider cameraProvider = cameraProviderFuture.get();


// add a listener to the cameraProviderFuture
            Executor cameraExecutor = ContextCompat.getMainExecutor(context);
            cameraProviderFuture.addListener(() -> {

            }, cameraExecutor);


            int backCamera = CameraSelector.LENS_FACING_BACK;
            int frontCamera = CameraSelector.LENS_FACING_FRONT;
            CameraSelector cameraSelector = new CameraSelector.Builder()
                    .requireLensFacing(backCamera).build();

            cameraProviderFuture.addListener(() -> {
                cameraProvider.unbindAll();

                Camera camera = cameraProvider.bindToLifecycle(
                        owner,
                        cameraSelector,
                        preview
                );

                preview.setSurfaceProvider(previewView.getSurfaceProvider());
            }, cameraExecutor);

        }catch (ExecutionException | InterruptedException e){

        }
    }
}
