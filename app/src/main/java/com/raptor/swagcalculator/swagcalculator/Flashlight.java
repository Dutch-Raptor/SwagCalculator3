package com.raptor.swagcalculator.swagcalculator;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.afollestad.materialdialogs.ThemeSingleton;


public class Flashlight extends ActionBarActivity {

    ImageView btnSwitch;

    private Camera camera;
    private boolean isFlashOn;
    private boolean hasFlash;
    Camera.Parameters params;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flashlight);


        btnSwitch = (ImageView) findViewById(R.id.togglebutton);
setcolor();

        ImageView reveal = (ImageView) findViewById(R.id.reveal);
        reveal.setVisibility(View.INVISIBLE);
        turnOffFlash();


        hasFlash = getApplicationContext().getPackageManager()
                .hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);
        if (!hasFlash) {
            AlertDialog alert = new AlertDialog.Builder(Flashlight.this)
                    .create();
            alert.setTitle(getString(R.string.error));
            alert.setMessage(getString(R.string.sorrynoflashlight));
            alert.setButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    Intent intent = new Intent(Flashlight.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            });
            alert.show();
            return;
        }

        btnSwitch.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (isFlashOn) {
                    turnOffFlash();
                } else {
                    turnOnFlash();
                }
            }
        });


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        super.onPause();
        turnOffFlash();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onStart() {
        super.onStart();
        getCamera();
        setcolor();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (camera != null) {
            camera.release();
            camera = null;
        }
    }

    private void getCamera() {
        if (camera == null) {
            try {
                camera = Camera.open();
                params = camera.getParameters();
            } catch (RuntimeException e) {
                Log.e("Camera Error. ", e.getMessage());
            }
        }
    }

    private void turnOnFlash() {


        if (!isFlashOn) {
            if (camera == null || params == null) {
                return;
            }


            params = camera.getParameters();
            params.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
            camera.setParameters(params);
            camera.startPreview();
            isFlashOn = true;
            toggleButtonImage();

        }

    }


    private void turnOffFlash() {
        if (isFlashOn) {
            if (camera == null || params == null) {
                return;
            }

            if (hasFlash) {

                params = camera.getParameters();
                params.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
                camera.setParameters(params);
                camera.stopPreview();
                isFlashOn = false;
                toggleButtonImage();
            }
        }
    }

    private void toggleButtonImage() {
        if (isFlashOn) {

            ImageView reveal = (ImageView) findViewById(R.id.reveal);
            Animation scaleup = AnimationUtils.loadAnimation(this, R.anim.scale_up);
            reveal.startAnimation(scaleup);
            reveal.setVisibility(View.VISIBLE);
            buttondown();
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    btnSwitch.setImageResource(R.drawable.flashlight_icon_grey_mask);
                    buttonup();
                }
            }, 200);
        } else {

            ImageView reveal = (ImageView) findViewById(R.id.reveal);
            Animation scaleup = AnimationUtils.loadAnimation(this, R.anim.scale_down);
            reveal.startAnimation(scaleup);
            reveal.setVisibility(View.INVISIBLE);
            buttondown();
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    btnSwitch.setImageResource(R.drawable.flashlight_icon_grey_off_mask);
                    buttonup();
                }
            }, 200);
        }


    }

    private void buttondown() {
        ImageView button = (ImageView) findViewById(R.id.togglebutton);
        Animation btnanimup = AnimationUtils.loadAnimation(this, R.anim.button_scale_down);
        button.startAnimation(btnanimup);
    }

    private void buttonup() {
        ImageView button = (ImageView) findViewById(R.id.togglebutton);
        Animation btnanimup = AnimationUtils.loadAnimation(this, R.anim.button_scale_up2);
        button.startAnimation(btnanimup);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement


        return super.onOptionsItemSelected(item);
    }


    public void backtohome(View view) {
        Intent intent = new Intent(Flashlight.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
    private void setcolor() {
        SharedPreferences colors = getSharedPreferences("colors", settings.MODE_PRIVATE);
        int color = colors.getInt("flash", -10453621);
        ThemeSingleton.get().positiveColor = color;
        ThemeSingleton.get().neutralColor = color;
        ThemeSingleton.get().negativeColor = color;


    }
}
