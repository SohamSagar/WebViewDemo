package com.example.webviewdemo;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.webkit.PermissionRequest;
import android.webkit.WebChromeClient;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import com.example.webviewdemo.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        EdgeToEdge.enable(this);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //here we set javascript enable and load our url also ask for camera permission if not granted
        binding.wv.loadUrl("https://va93n.ciroue.com");
        binding.wv.getSettings().setJavaScriptEnabled(true);
        checkCameraPermissions();

        //here web chrome client will help to get permission request call back so that we can handle that thing our side
        binding.wv.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onPermissionRequest(PermissionRequest request) {
                final String[] requestedResources = request.getResources();
                for (String r : requestedResources) {
                    //if permission asked for take pic then this will grant if we had camera permission
                    if (r.equals(PermissionRequest.RESOURCE_VIDEO_CAPTURE)) {
                        request.grant(new String[]{PermissionRequest.RESOURCE_VIDEO_CAPTURE});
                        break;
                    }
                }
            }

        });

    }

    public void checkCameraPermissions(){
        if (ContextCompat.checkSelfPermission(MainActivity.this, android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[] { android.Manifest.permission.CAMERA }, 100);
        }
    }
}