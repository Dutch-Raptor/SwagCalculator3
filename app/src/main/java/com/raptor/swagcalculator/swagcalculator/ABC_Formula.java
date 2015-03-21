package com.raptor.swagcalculator.swagcalculator;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.ThemeSingleton;
import com.mikepenz.iconics.typeface.FontAwesome;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.SectionDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;


public class ABC_Formula extends ActionBarActivity {
    private Toolbar toolbar;
    private static final int[] header = {R.layout.gifheader, R.layout.header};
    private int headerposition = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_abc__formula);
        setupdrawer();
        setcolor();
        final SwipeRefreshLayout swiptorefreshlayout = (SwipeRefreshLayout) findViewById(R.id.swiptorefresh);

        swiptorefreshlayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        swiptorefreshlayout.setRefreshing(false);
                        calculate();
                    }
                }, 900);
            }
        });
        RelativeLayout cv = (RelativeLayout) findViewById(R.id.contentcontainer);

        LayoutAnimationController controller = AnimationUtils.loadLayoutAnimation(this, R.anim.moveup);
        cv.setLayoutAnimation(controller);
        FrameLayout fl = (FrameLayout) findViewById(R.id.abcactionbar);
        Animation scaleup = AnimationUtils.loadAnimation(this, R.anim.slidedown);
        fl.startAnimation(scaleup);
    }

    @Override
    public void onPause() {
        super.onPause();
        setupdrawer();
    }

    @Override
    public void onResume() {
        super.onResume();
        setcolor();
        setupdrawer();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_abc__formula, menu);
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

    public void calculate(View view) {
        calculate();
    }


    private void calculate() {

        String a2, b3, c4;
        EditText a1 = (EditText) findViewById(R.id.Aedittext);
        EditText b2 = (EditText) findViewById(R.id.Bedittext);
        EditText c3 = (EditText) findViewById(R.id.Cedittext);
        TextView x1 = (TextView) findViewById(R.id.x1);
        TextView x2 = (TextView) findViewById(R.id.x2);
        TextView top = (TextView) findViewById(R.id.toplinetext);
        TextView bottom = (TextView) findViewById(R.id.bottomlinetext);

        a2 = a1.getText().toString();
        b3 = b2.getText().toString();
        c4 = c3.getText().toString();

        if (a2.matches("") ^ b3.matches("") ^ c4.matches("")) {
            Toast.makeText(getApplicationContext(), getString(R.string.enter3numbers), Toast.LENGTH_SHORT).show();
            return;
        } else {
            double a = Double.valueOf(a1.getText().toString());
            double b = Double.valueOf(b2.getText().toString());
            double c = Double.valueOf(c3.getText().toString());
            double sum1 = ((-b + (Math.sqrt((b * b) - (4 * a * c)))) / (2 * a));
            String ans1 = String.valueOf(sum1);
            x1.setText("X1 = " + ans1);
            double sum2 = ((-b - (Math.sqrt((b * b) - (4 * a * c)))) / (2 * a));
            String ans2 = String.valueOf(sum2);
            x2.setText("X2 = " + ans2);

            top.setText("-" + b + "±√(" + b + "²-(4.0×" + a + "×" + c + ")");
            bottom.setText("2×" + a);
        }
    }

    public void clearall(MenuItem item) {
        EditText a1 = (EditText) findViewById(R.id.Aedittext);
        EditText b2 = (EditText) findViewById(R.id.Bedittext);
        EditText c3 = (EditText) findViewById(R.id.Cedittext);
        a1.setText("");
        b2.setText("");
        c3.setText("");
    }

    private void setupdrawer() {
        SharedPreferences colors = getSharedPreferences("colors", settings.MODE_PRIVATE);
        int color = colors.getInt("abc", -145758885);
        Toolbar toolbar;
        Drawer.Result result = null;
        toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        SharedPreferences image = getSharedPreferences("image", settings.MODE_PRIVATE);
        Boolean imagebool = image.getBoolean("image", false);
        if (!imagebool) {
            headerposition = 0;
        } else {
            headerposition = 1;
        }

        result = new Drawer()
                .withActivity(this)
                .withToolbar(toolbar)

                .withSelectedItem(2)
                .withHeader(header[headerposition])
                .withDrawerWidthDp(320)
                .addDrawerItems(

                        new PrimaryDrawerItem().withName(R.string.app_name).withIcon(FontAwesome.Icon.faw_calculator).withIdentifier(1),
                        new PrimaryDrawerItem().withName(R.string.title_activity_pythagorean).withIcon(FontAwesome.Icon.faw_chevron_right).withIdentifier(2),
                        new PrimaryDrawerItem().withName(R.string.title_activity_abc__formula).withIcon(FontAwesome.Icon.faw_sort_alpha_asc).withIdentifier(3).withSelectedTextColor(color).withSelectedIconColor(color),
                        new PrimaryDrawerItem().withName(R.string.title_activity_circle__surface).withIcon(FontAwesome.Icon.faw_circle_thin).withIdentifier(4),
                        new PrimaryDrawerItem().withName(R.string.flashlight).withIcon(FontAwesome.Icon.faw_flash).withIdentifier(8),
                        new PrimaryDrawerItem().withName(R.string.title_activity_math_functions).withIcon(FontAwesome.Icon.faw_subscript).withIdentifier(9),
                        new SectionDrawerItem().withName("Other"),
                        new SecondaryDrawerItem().withName(R.string.title_activity_settings).withIcon(FontAwesome.Icon.faw_cog).withIdentifier(5),
                        new SecondaryDrawerItem().withName("Help").withIcon(FontAwesome.Icon.faw_question).withIdentifier(6),
                        new SecondaryDrawerItem().withName(R.string.about_title).withIcon(FontAwesome.Icon.faw_info).withIdentifier(7)
                )
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {

                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id, IDrawerItem drawerItem) {
                        if (drawerItem == null) {
                            setupdrawer();
                            return;

                        }
                        if (drawerItem.getIdentifier() == 2) {
                            Intent iinent = new Intent(ABC_Formula.this, pythagorean.class);
                            startActivity(iinent);
                            finish();
                        }
                        if (drawerItem.getIdentifier() == 1) {
                            Intent iinent = new Intent(ABC_Formula.this, MainActivity.class);
                            startActivity(iinent);
                            finish();
                        }
                        if (drawerItem.getIdentifier() == 4) {
                            Intent iinent = new Intent(ABC_Formula.this, Circle_Surface.class);
                            startActivity(iinent);
                            finish();
                        }
                        if (drawerItem.getIdentifier() == 5) {
                            Intent iinent = new Intent(ABC_Formula.this, settings.class);
                            startActivity(iinent);

                        }
                        if (drawerItem.getIdentifier() == 6) {


                            MaterialDialog dialog = new MaterialDialog.Builder(ABC_Formula.this)
                                    .title("Help")
                                    .customView(R.layout.abc_explanation, true)
                                    .positiveText("OK")
                                    .iconRes(R.drawable.help)

                                    .callback(new MaterialDialog.ButtonCallback() {
                                        @Override
                                        public void onPositive(MaterialDialog dialog) {

                                        }

                                        @Override
                                        public void onNegative(MaterialDialog dialog) {
                                        }
                                    }).build();
                            dialog.show();
                            setupdrawer();
                        }
                        if (drawerItem.getIdentifier() == 7) {
                            new MaterialDialog.Builder(ABC_Formula.this)
                                    .title(R.string.about_title)
                                    .customView(R.layout.aboutcards, true)
                                    .positiveText("OK")

                                    .show();
                            setupdrawer();
                        }
                        if (drawerItem.getIdentifier() == 8) {
                            Intent iinent = new Intent(ABC_Formula.this, Flashlight.class);
                            startActivity(iinent);
                        }
                        if (drawerItem.getIdentifier() == 9) {
                            Intent iinent = new Intent(getApplicationContext(), MathFunctions.class);
                            startActivity(iinent);
                            finish();
                        }

                    }
                })
                .withOnDrawerListener(new Drawer.OnDrawerListener() {
                    @Override
                    public void onDrawerOpened(View drawerView) {
                        InputMethodManager inputManager = (InputMethodManager)
                                getSystemService(Context.INPUT_METHOD_SERVICE);

                        inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                                InputMethodManager.HIDE_NOT_ALWAYS);
                    }

                    @Override
                    public void onDrawerClosed(View drawerView) {

                    }
                })


                .build();
        setcolor();
    }

    private void setcolor() {
        SharedPreferences colors = getSharedPreferences("colors", settings.MODE_PRIVATE);
        int color = colors.getInt("abc", -145758885);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(color));
        ThemeSingleton.get().positiveColor = color;
        ThemeSingleton.get().neutralColor = color;
        ThemeSingleton.get().negativeColor = color;

        FrameLayout l = (FrameLayout) findViewById(R.id.abcactionbar);
        l.setBackgroundDrawable(new ColorDrawable(color));
        final SwipeRefreshLayout swiptorefreshlayout = (SwipeRefreshLayout) findViewById(R.id.swiptorefresh);
        swiptorefreshlayout.setColorSchemeColors(color);
    }

    public void slidenerdwebsite(View view) {
        {
            Uri uri = Uri.parse("http://slidenerd.com/");
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
        }
    }

    public void derekbanaswebsite(View view) {
        {
            Uri uri = Uri.parse("https://www.youtube.com/user/derekbanas");
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
        }
    }

    public void stackoverflowwebsite(View view) {
        {
            Uri uri = Uri.parse("http://stackoverflow.com/");
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
        }
    }

    public void donate(View view) {
        Uri uri = Uri.parse("https://www.paypal.com/cgi-bin/webscr?cmd=_s-xclick&hosted_button_id=5HJFNHLUP6Z96");
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }

    public void imageanimation(View view) {

        Animation scaleup = AnimationUtils.loadAnimation(this, R.anim.centered_rotation);
        view.startAnimation(scaleup);
    }

    public void color(MenuItem item) {
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
                setupdrawer();
            }
        });
    }
}


