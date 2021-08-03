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
    OnBarcodeScannedListener onBarcodeScannedListener;

    public BarcodeAnalyzer(BarcodeScanner scanner) {
        this.scanner = scanner;
    }

    public BarcodeAnalyzer(BarcodeScanner scanner, OnBarcodeScannedListener onBarcodeScannedListener) {
        this.scanner = scanner;
        this.onBarcodeScannedListener = onBarcodeScannedListener;
    }

    @Override
    public void analyze(@NonNull ImageProxy imageProxy) {
        Image mediaImage = imageProxy.getImage();
        if (mediaImage != null) {
            InputImage image = InputImage.fromMediaImage(mediaImage, imageProxy.getImageInfo().getRotationDegrees());
            scanner.process(image)
                    .addOnSuccessListener(barcodes -> {
                        Log.d(TAG, "analyze: success "+barcodes);
                        if (!barcodes.isEmpty()){
                            onBarcodeScannedListener.oBarcodeScanned(barcodes.get(0));
                            Log.d(TAG, "got one : "+barcodes.get(0).getRawValue());
                        }



                    })
                    .addOnFailureListener(e -> {
                        Log.d(TAG, "analyze: "+e.getMessage());
                    })
                    .addOnCompleteListener(task -> imageProxy.close()) ;
        }
    }

    public interface OnBarcodeScannedListener {
        void oBarcodeScanned(Barcode barcode);
    }
}
