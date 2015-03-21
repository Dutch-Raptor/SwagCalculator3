package com.raptor.swagcalculator.swagcalculator;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;


public class Launchactivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launchactivity);

        SharedPreferences sp = getSharedPreferences("start", settings.MODE_PRIVATE);
        int startid = sp.getInt("startid", 0);
        if (startid == 0) {
            Intent intent = new Intent(Launchactivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
        if (startid == 1) {
            Intent intent = new Intent(Launchactivity.this, pythagorean.class);
            startActivity(intent);
            finish();
        }
        if (startid == 2) {
            Intent intent = new Intent(Launchactivity.this, ABC_Formula.class);
            startActivity(intent);
            finish();
        }
        if (startid == 3) {
            Intent intent = new Intent(Launchactivity.this, Circle_Surface.class);
            startActivity(intent);
            finish();
        }
        if (startid == 4) {
            Intent intent = new Intent(Launchactivity.this, Flashlight.class);
            startActivity(intent);
            finish();
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_launchactivity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void openswag(View view) {
        Intent intent = new Intent(Launchactivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
