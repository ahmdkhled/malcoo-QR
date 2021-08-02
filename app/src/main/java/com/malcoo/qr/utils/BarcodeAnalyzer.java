package com.malcoo.qr.utils;

import android.annotation.SuppressLint;
import android.media.Image;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.camera.core.ImageAnalysis;
import androidx.camera.core.ImageProxy;

import com.google.mlkit.vision.barcode.Barcode;
import com.google.mlkit.vision.barcode.BarcodeScanner;
import com.google.mlkit.vision.common.InputImage;

@SuppressLint("UnsafeOptInUsageError")

public class BarcodeAnalyzer implements ImageAnalysis.Analyzer {
    private static final String TAG = "BarcodeAnalyzer";

    BarcodeScanner scanner;

    public BarcodeAnalyzer(BarcodeScanner scanner) {
        this.scanner = scanner;
    }

    @Override
    public void analyze(@NonNull ImageProxy imageProxy) {
        Image mediaImage = imageProxy.getImage();
        if (mediaImage != null) {
            InputImage image = InputImage.fromMediaImage(mediaImage, imageProxy.getImageInfo().getRotationDegrees());
            scanner.process(image)
                    .addOnSuccessListener(barcodes -> {
                        Log.d(TAG, "analyze: success "+barcodes);
                        for (Barcode barcode:barcodes) {
                            Log.d(TAG, "got one : "+barcode.getRawValue());
                        }
                    })
                    .addOnFailureListener(e -> {
                        Log.d(TAG, "analyze: "+e.getMessage());
                    })
                    .addOnCompleteListener(task -> imageProxy.close()) ;
        }
    }

    interface OnBarcodeScanned{
        void oBarcodeScanned(Barcode barcode);
    }
}
