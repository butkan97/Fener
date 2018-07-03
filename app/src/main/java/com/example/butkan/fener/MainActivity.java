package com.example.butkan.fener;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.hardware.Camera;
import android.provider.MediaStore;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.BackgroundColorSpan;
import android.text.style.ImageSpan;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.ToggleButton;

public class MainActivity extends AppCompatActivity {
    Camera camera;
    Camera.Parameters parameters;
    boolean flashAcik = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        class RequestUserPermission {

            private Activity activity;
            private static final int REQUEST_EXTERNAL_STROGE = 1;
            private String[] PERMISSIONS_STROGE = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.CAMERA};

            public RequestUserPermission(Activity activity) {
                this.activity = activity;
            }

            public void veriftyStoragePermissisions() {
                int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);

                if (permission != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(activity, PERMISSIONS_STROGE, REQUEST_EXTERNAL_STROGE);
                }

            }
        }
        RequestUserPermission requestUserPermission = new RequestUserPermission(this);
        requestUserPermission.veriftyStoragePermissisions();

        boolean flashvarmi = getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);
        if (!flashvarmi) {
            Toast.makeText(this, "Flash bulunamadı uygulama kapatılacaktır.", Toast.LENGTH_LONG).show();
            finish();
        }

        final ImageView toggleButton = findViewById(R.id.imageView);
        toggleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                camera = Camera.open();
                parameters = camera.getParameters();


                if (flashAcik) {
                    flashAcik = false;
                    parameters.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
                    camera.setParameters(parameters);
                    camera.stopPreview();
                    toggleButton.setImageDrawable(ContextCompat.getDrawable(MainActivity.this, R.drawable.kapali));


                    Log.d("Mesaj", "Açıldı");
                } else {
                    flashAcik = true;


                    parameters.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
                    camera.setParameters(parameters);
                    camera.startPreview();
                    toggleButton.setImageDrawable(ContextCompat.getDrawable(MainActivity.this, R.drawable.acik1));

                    Log.d("Mesaj", "Kapatıldı");
                }
            }
        });

    }
}
