package com.raptor.swagcalculator.swagcalculator;

import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.afollestad.materialdialogs.ThemeSingleton;

public class settings extends ActionBarActivity {
    Boolean expanded = false;
    Boolean expandedscreen = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        Toolbar toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        CheckBox cb1 = (CheckBox) findViewById(R.id.soundcheckBox);
        CheckBox cb3 = (CheckBox) findViewById(R.id.imagecheckbox);
        SharedPreferences prefs = getSharedPreferences("sound", MODE_PRIVATE);
        savestartactivity();
        int soundid = prefs.getInt("soundid", 1);
        if (soundid == 0) {
            cb1.setChecked(false);
        }
        CheckBox cb2 = (CheckBox) findViewById(R.id.buttoncheckbox);
        SharedPreferences buttons = getSharedPreferences("style", MODE_PRIVATE);

        Boolean oldbuttons = buttons.getBoolean("old", false);
        if (oldbuttons) {
            cb2.setChecked(true);
        }
        SharedPreferences imagepref = getSharedPreferences("image", MODE_PRIVATE);

        Boolean image = imagepref.getBoolean("image", false);
        if (image) {
            cb3.setChecked(true);
        }

        SharedPreferences sp = getSharedPreferences("start", settings.MODE_PRIVATE);
        int startid = sp.getInt("startid", -1);

        RadioButton rb1 = (RadioButton) findViewById(R.id.rb1);
        RadioButton rb2 = (RadioButton) findViewById(R.id.rb2);
        RadioButton rb3 = (RadioButton) findViewById(R.id.rb3);
        RadioButton rb4 = (RadioButton) findViewById(R.id.rb4);
        RadioButton rb5 = (RadioButton) findViewById(R.id.rb5);

        if (startid == 0) {
            rb1.setChecked(true);
            rb2.setChecked(false);
            rb3.setChecked(false);
            rb4.setChecked(false);
            rb5.setChecked(false);
        }
        if (startid == 1) {
            rb2.setChecked(true);
            rb1.setChecked(false);
            rb3.setChecked(false);
            rb4.setChecked(false);
            rb5.setChecked(false);
        }
        if (startid == 2) {
            rb3.setChecked(true);
            rb1.setChecked(false);
            rb2.setChecked(false);
            rb4.setChecked(false);
            rb5.setChecked(false);
        }
        if (startid == 3) {
            rb4.setChecked(true);
            rb1.setChecked(false);
            rb2.setChecked(false);
            rb3.setChecked(false);
            rb5.setChecked(false);
        }
        if (startid == 4) {
            rb5.setChecked(true);
            rb1.setChecked(false);
            rb2.setChecked(false);
            rb3.setChecked(false);
            rb4.setChecked(false);

        }
        SharedPreferences colors = getSharedPreferences("colors", settings.MODE_PRIVATE);
        int color = colors.getInt("settings", -145758885);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(color));
        ThemeSingleton.get().positiveColor = color;
        ThemeSingleton.get().neutralColor = color;
        ThemeSingleton.get().negativeColor = color;


    }

    @Override
    protected void onStart() {
        super.onStart();
        LinearLayout cv = (LinearLayout) findViewById(R.id.viewholder_settings);

        LayoutAnimationController controller = AnimationUtils.loadLayoutAnimation(this, R.anim.moveup);
        cv.setLayoutAnimation(controller);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_settings, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:

                finish();


                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onPause() {
        super.onPause();

    }

    @Override
    public void onStop() {
        super.onStop();


    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }

    public void soundcheckbox(View v) {
        CheckBox checkBox = (CheckBox) v;
        if (checkBox.isChecked()) {
            SharedPreferences.Editor editor = getSharedPreferences("sound", MODE_PRIVATE).edit();
            editor.putString("sound", "true");
            editor.putInt("soundid", 1);
            editor.apply();
            Toast.makeText(this, getString(R.string.soundon), Toast.LENGTH_SHORT).show();
        } else {
            SharedPreferences.Editor editor = getSharedPreferences("sound", MODE_PRIVATE).edit();
            editor.putString("sound", "false");
            editor.putInt("soundid", 0);
            editor.apply();
            Toast.makeText(this, getString(R.string.soundoff), Toast.LENGTH_SHORT).show();
        }
    }

    private void savestartactivity() {


        final RadioGroup rg1 = (RadioGroup) findViewById(R.id.rg1);


        rg1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int pos = rg1.indexOfChild(findViewById(checkedId));
                SharedPreferences sp = getSharedPreferences("start", settings.MODE_PRIVATE);
                SharedPreferences.Editor editor = sp.edit();
                editor.putInt("startid", pos);
                editor.apply();
            }
        });


    }


    public void settingscolor(View view) {
        SharedPreferences colors = getSharedPreferences("colors", com.raptor.swagcalculator.swagcalculator.settings.MODE_PRIVATE);
        int selectedColorIndex = colors.getInt("settingsindex", -1);
        new ColorChooserDialog().show(this, selectedColorIndex, new ColorChooserDialog.Callback() {
            @Override
            public void onColorSelection(int index, int color, int darker) {
                SharedPreferences colors = getSharedPreferences("colors", com.raptor.swagcalculator.swagcalculator.settings.MODE_PRIVATE);
                int selectedColorIndex;
                selectedColorIndex = index;
                getSupportActionBar().setBackgroundDrawable(new ColorDrawable(color));
                ThemeSingleton.get().positiveColor = color;
                ThemeSingleton.get().neutralColor = color;
                ThemeSingleton.get().negativeColor = color;
                SharedPreferences.Editor editor = colors.edit();
                editor.putInt("settings", color);
                editor.putInt("settingsindex", selectedColorIndex);
                editor.apply();
            }
        });
    }

    public void swagcolor(View view) {
        SharedPreferences colors = getSharedPreferences("colors", com.raptor.swagcalculator.swagcalculator.settings.MODE_PRIVATE);
        int selectedColorIndex = colors.getInt("swagindex", -1);
        new ColorChooserDialog().show(this, selectedColorIndex, new ColorChooserDialog.Callback() {
            @Override
            public void onColorSelection(int index, int color, int darker) {
                SharedPreferences colors = getSharedPreferences("colors", com.raptor.swagcalculator.swagcalculator.settings.MODE_PRIVATE);
                int selectedColorIndex;
                selectedColorIndex = index;
                SharedPreferences.Editor editor = colors.edit();
                editor.putInt("swag", color);
                editor.putInt("swagindex", selectedColorIndex);
                editor.apply();

            }
        });
    }

    public void circlecolor(View view) {
        SharedPreferences colors = getSharedPreferences("colors", com.raptor.swagcalculator.swagcalculator.settings.MODE_PRIVATE);
        int selectedColorIndex = colors.getInt("circleindex", -1);
        new ColorChooserDialog().show(this, selectedColorIndex, new ColorChooserDialog.Callback() {
            @Override
            public void onColorSelection(int index, int color, int darker) {
                SharedPreferences colors = getSharedPreferences("colors", com.raptor.swagcalculator.swagcalculator.settings.MODE_PRIVATE);
                int selectedColorIndex;
                selectedColorIndex = index;
                SharedPreferences.Editor editor = colors.edit();
                editor.putInt("circle", color);
                editor.putInt("circleindex", selectedColorIndex);
                editor.apply();
            }
        });
    }

    public void pythagoreancolor(View view) {
        SharedPreferences colors = getSharedPreferences("colors", com.raptor.swagcalculator.swagcalculator.settings.MODE_PRIVATE);
        int selectedColorIndex = colors.getInt("pythindex", -1);
        new ColorChooserDialog().show(this, selectedColorIndex, new ColorChooserDialog.Callback() {
            @Override
            public void onColorSelection(int index, int color, int darker) {
                SharedPreferences colors = getSharedPreferences("colors", com.raptor.swagcalculator.swagcalculator.settings.MODE_PRIVATE);
                int selectedColorIndex;
                selectedColorIndex = index;
                SharedPreferences.Editor editor = colors.edit();
                editor.putInt("pyth", color);
                editor.putInt("pythindex", selectedColorIndex);
                editor.apply();
            }
        });
    }

    public void abccolor(View view) {
        SharedPreferences colors = getSharedPreferences("colors", com.raptor.swagcalculator.swagcalculator.settings.MODE_PRIVATE);
        int selectedColorIndex = colors.getInt("abcindex", -1);
        new ColorChooserDialog().show(this, selectedColorIndex, new ColorChooserDialog.Callback() {
            @Override
            public void onColorSelection(int index, int color, int darker) {
                SharedPreferences colors = getSharedPreferences("colors", com.raptor.swagcalculator.swagcalculator.settings.MODE_PRIVATE);
                int selectedColorIndex;
                selectedColorIndex = index;
                SharedPreferences.Editor editor = colors.edit();
                editor.putInt("abc", color);
                editor.putInt("abcindex", selectedColorIndex);
                editor.apply();
            }
        });
    }

    public void flashlightcolor(View view) {
        SharedPreferences colors = getSharedPreferences("colors", com.raptor.swagcalculator.swagcalculator.settings.MODE_PRIVATE);
        int selectedColorIndex = colors.getInt("flashindex", -1);
        new ColorChooserDialog().show(this, selectedColorIndex, new ColorChooserDialog.Callback() {
            @Override
            public void onColorSelection(int index, int color, int darker) {
                SharedPreferences colors = getSharedPreferences("colors", com.raptor.swagcalculator.swagcalculator.settings.MODE_PRIVATE);
                int selectedColorIndex;
                selectedColorIndex = index;
                SharedPreferences.Editor editor = colors.edit();
                editor.putInt("flash", color);
                editor.putInt("flashindex", selectedColorIndex);
                editor.apply();
            }
        });
    }

    public void mathcolor(View view) {
        SharedPreferences colors = getSharedPreferences("colors", com.raptor.swagcalculator.swagcalculator.settings.MODE_PRIVATE);
        int selectedColorIndex = colors.getInt("mathindex", -1);
        new ColorChooserDialog().show(this, selectedColorIndex, new ColorChooserDialog.Callback() {
            @Override
            public void onColorSelection(int index, int color, int darker) {
                SharedPreferences colors = getSharedPreferences("colors", com.raptor.swagcalculator.swagcalculator.settings.MODE_PRIVATE);
                int selectedColorIndex;
                selectedColorIndex = index;
                SharedPreferences.Editor editor = colors.edit();
                editor.putInt("math", color);
                editor.putInt("mathindex", selectedColorIndex);
                editor.apply();
            }
        });
    }

    public void expandcolorselection(View view) {

        LinearLayout l = (LinearLayout) findViewById(R.id.colorbuttons);
        Button b = (Button) findViewById(R.id.expandbutton);
        if (!expanded) {
            l.setVisibility(View.VISIBLE);
            b.setText(R.string.taptohide);
            expanded = true;
            return;
        }
        if (expanded) {
            l.setVisibility(View.GONE);
            b.setText(R.string.taptoexpand);
            expanded = false;
            return;
        }


    }

    public void expandscreenselection(View view) {
        RadioGroup l = (RadioGroup) findViewById(R.id.rg1);
        Button b = (Button) findViewById(R.id.expandscreenbutton);
        if (!expandedscreen) {
            l.setVisibility(View.VISIBLE);
            b.setText(R.string.taptohide);
            expandedscreen = true;
            return;
        }
        if (expandedscreen) {
            l.setVisibility(View.GONE);
            b.setText(R.string.taptoexpand);
            expandedscreen = false;
            return;
        }
    }

    public void oldbuttonstyle(View view) {

            CheckBox checkBox = (CheckBox) view;
            if (checkBox.isChecked()) {
                SharedPreferences.Editor editor = getSharedPreferences("style", MODE_PRIVATE).edit();
                editor.putBoolean("old", true);
                editor.apply();
                Toast.makeText(this, getString(R.string.oldstyleon), Toast.LENGTH_SHORT).show();
            } else {
                SharedPreferences.Editor editor = getSharedPreferences("style", MODE_PRIVATE).edit();
                editor.putBoolean("old", false);
                editor.apply();
                Toast.makeText(this, getString(R.string.oldstyleoff), Toast.LENGTH_SHORT).show();

        }
    }

    public void color(MenuItem item) {
        SharedPreferences colors = getSharedPreferences("colors", com.raptor.swagcalculator.swagcalculator.settings.MODE_PRIVATE);
        int selectedColorIndex = colors.getInt("settingsindex", -1);
        new ColorChooserDialog().show(this, selectedColorIndex, new ColorChooserDialog.Callback() {
            @Override
            public void onColorSelection(int index, int color, int darker) {
                SharedPreferences colors = getSharedPreferences("colors", com.raptor.swagcalculator.swagcalculator.settings.MODE_PRIVATE);
                int selectedColorIndex;
                selectedColorIndex = index;
                getSupportActionBar().setBackgroundDrawable(new ColorDrawable(color));
                ThemeSingleton.get().positiveColor = color;
                ThemeSingleton.get().neutralColor = color;
                ThemeSingleton.get().negativeColor = color;
                SharedPreferences.Editor editor = colors.edit();
                editor.putInt("settings", color);
                editor.putInt("settingsindex", selectedColorIndex);
                editor.apply();
            }
        });
    }

    public void imagecheckbox(View view) {
        CheckBox checkBox = (CheckBox) view;
        if (checkBox.isChecked()) {
            SharedPreferences.Editor editor = getSharedPreferences("image", MODE_PRIVATE).edit();
            editor.putBoolean("image", true);
            editor.apply();

        } else {
            SharedPreferences.Editor editor = getSharedPreferences("image", MODE_PRIVATE).edit();
            editor.putBoolean("image", false);
            editor.apply();


        }
}}