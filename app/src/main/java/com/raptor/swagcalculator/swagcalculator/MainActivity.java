package com.raptor.swagcalculator.swagcalculator;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.ThemeSingleton;
import com.mikepenz.iconics.typeface.FontAwesome;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.SectionDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

import static com.raptor.swagcalculator.swagcalculator.R.id.Answer;
import static com.raptor.swagcalculator.swagcalculator.R.id.nextsnapbackbutton;


public class MainActivity extends ActionBarActivity {
    private static final int[] IMAGES = {R.drawable.mf1, R.drawable.mf2,
            R.drawable.mf5, R.drawable.mf4, R.drawable.mf3,R.drawable.mf6,R.drawable.mf7,R.drawable.mf8,R.drawable.mf9,R.drawable.mf10,R.drawable.mf11, R.drawable.mf1};
    private ImageSwitcher mImageSwitcher;
    private int mPosition = 0;
    private int mClicked = 0;
    private static final int[] header = {R.layout.gifheader, R.layout.header};
    private int headerposition = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupdrawer();
        mImageSwitcher = (ImageSwitcher) findViewById(R.id.imageSwitcher);
        mImageSwitcher.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                ImageView imageView = new ImageView(MainActivity.this);
                return imageView;
            }
        });
        SharedPreferences prefs = getSharedPreferences("snapbacks", MODE_PRIVATE);
        mPosition = prefs.getInt("snapbacks", 1);
        mImageSwitcher.setImageResource(IMAGES[mPosition]);
        setcolor();
        LinearLayout cv = (LinearLayout) findViewById(R.id.contentcontainer);
        LayoutAnimationController controller = AnimationUtils.loadLayoutAnimation(this, R.anim.moveup);
        cv.setLayoutAnimation(controller);
    }

    @Override
    public void onResume() {
        super.onResume();
        setcolor();
        setupdrawer();
        SharedPreferences prefs = getSharedPreferences("snapbacks", MODE_PRIVATE);
        mPosition = prefs.getInt("snapbacks", 1);
        mImageSwitcher.setImageResource(IMAGES[mPosition]);

    }

    @Override
    public void onPause() {
        super.onPause();
        setupdrawer();
    }


    public void snaps(View view) {
        ImageSwitcher imageswitcher = (ImageSwitcher) findViewById(R.id.imageSwitcher);
        Animation scaleup = AnimationUtils.loadAnimation(this, R.anim.image_rotation);
        imageswitcher.startAnimation(scaleup);
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                SharedPreferences prefs = getSharedPreferences("snapbacks", MODE_PRIVATE);
                mPosition = prefs.getInt("snapbacks", 1);
                mPosition = (mPosition + 1);
                if (mPosition == 11) mPosition = (mPosition - 10);
                mImageSwitcher.setImageResource(IMAGES[mPosition]);
                SharedPreferences.Editor editor = getSharedPreferences("snapbacks", MODE_PRIVATE).edit();
                editor.putInt("snapbacks", mPosition);
                editor.apply();
            }
        }, 300);

    }

    public void addup(View view)


    {
        //Animation scaleup = AnimationUtils.loadAnimation(this, R.anim.button_animation);
        //view.startAnimation(scaleup);
        double n1, n2, sum;
        EditText e1 = (EditText) findViewById(R.id.Num1);
        EditText e2 = (EditText) findViewById(R.id.Num2);
        TextView t1 = (TextView) findViewById(Answer);
        String numb1 = e1.getText().toString();
        String numb2 = e2.getText().toString();
        if (numb1.trim().equals("")) {
            Toast.makeText(this, R.string.enteranumber, Toast.LENGTH_SHORT).show();
            return;
        } else {
            if (numb2.trim().equals("")) {
                Toast.makeText(this, R.string.enteranumber, Toast.LENGTH_SHORT).show();
                return;
            } else {
                n1 = Double.parseDouble(e1.getText().toString());
                n2 = Double.parseDouble(e2.getText().toString());

                if (n1 == 1 && n2 == 1) {

                    mClicked = (mClicked + 1);
                    if (mClicked == 7) {
                        mClicked = 0;
                        Intent intent = new Intent(MainActivity.this, image.class);
                        startActivity(intent);
                    }
                }
            }
            sum = n1 + n2;
            t1.setText(Double.toString(sum));
            answeranimation();
            SharedPreferences prefs = getSharedPreferences("sound", MODE_PRIVATE);
            int soundid = prefs.getInt("soundid", 1);
            if (soundid == 1) {
                if (sum <= 0) {
                    MediaPlayer lowswag = MediaPlayer.create(this, R.raw.sadtrombone);
                    lowswag.start();
                }
                if (sum > 0) {
                    MediaPlayer lowswag = MediaPlayer.create(this, R.raw.screaming_goat);
                    lowswag.start();
                }
            }
        }
    }

    public void minus(View view) {
        //Animation scaleup = AnimationUtils.loadAnimation(this, R.anim.button_animation);
        //view.startAnimation(scaleup);
        double n1, n2, sum;
        EditText e1 = (EditText) findViewById(R.id.Num1);
        EditText e2 = (EditText) findViewById(R.id.Num2);
        TextView t1 = (TextView) findViewById(Answer);
        String numb1 = e1.getText().toString();
        String numb2 = e2.getText().toString();
        if (numb1.trim().equals("")) {
            Toast.makeText(this, R.string.enteranumber, Toast.LENGTH_SHORT).show();
            return;
        } else {
            if (numb2.trim().equals("")) {
                Toast.makeText(this, R.string.enteranumber, Toast.LENGTH_SHORT).show();
                return;
            } else {
                n1 = Double.parseDouble(e1.getText().toString());
                n2 = Double.parseDouble(e2.getText().toString());
                sum = n1 - n2;
                t1.setText(Double.toString(sum));
                answeranimation();
                SharedPreferences prefs = getSharedPreferences("sound", MODE_PRIVATE);
                int soundid = prefs.getInt("soundid", 1);
                if (soundid == 1) {
                    if (sum <= 0) {
                        MediaPlayer lowswag = MediaPlayer.create(this, R.raw.sadtrombone);
                        lowswag.start();
                    }
                    if (sum > 0) {
                        MediaPlayer lowswag = MediaPlayer.create(this, R.raw.screaming_goat);
                        lowswag.start();
                    }
                }
            }
        }
    }


    public void divide(View view) {
        //Animation scaleup = AnimationUtils.loadAnimation(this, R.anim.button_animation);
        //view.startAnimation(scaleup);
        double n1, n2, sum;
        EditText e1 = (EditText) findViewById(R.id.Num1);
        EditText e2 = (EditText) findViewById(R.id.Num2);
        TextView t1 = (TextView) findViewById(Answer);
        String numb1 = e1.getText().toString();
        String numb2 = e2.getText().toString();
        if (numb1.trim().equals("")) {
            Toast.makeText(this, R.string.enteranumber, Toast.LENGTH_SHORT).show();
            return;
        } else {
            if (numb2.trim().equals("")) {
                Toast.makeText(this, R.string.enteranumber, Toast.LENGTH_SHORT).show();
                return;
            } else {
                n1 = Double.parseDouble(e1.getText().toString());
                n2 = Double.parseDouble(e2.getText().toString());
                sum = n1 / n2;
                t1.setText(Double.toString(sum));
                answeranimation();
                SharedPreferences prefs = getSharedPreferences("sound", MODE_PRIVATE);
                int soundid = prefs.getInt("soundid", 1);
                if (soundid == 1) {
                    if (sum <= 0) {
                        MediaPlayer lowswag = MediaPlayer.create(this, R.raw.sadtrombone);
                        lowswag.start();
                    }
                    if (sum > 0) {
                        MediaPlayer lowswag = MediaPlayer.create(this, R.raw.screaming_goat);
                        lowswag.start();
                    }
                }
            }
        }
    }


    public void times(View view)

    {
       // Animation scaleup = AnimationUtils.loadAnimation(this, R.anim.button_animation);
        //view.startAnimation(scaleup);
        double n1, n2, sum;
        EditText e1 = (EditText) findViewById(R.id.Num1);
        EditText e2 = (EditText) findViewById(R.id.Num2);
        TextView t1 = (TextView) findViewById(Answer);
        String numb1 = e1.getText().toString();
        String numb2 = e2.getText().toString();
        if (numb1.trim().equals("")) {
            Toast.makeText(this, R.string.enteranumber, Toast.LENGTH_SHORT).show();
            return;
        } else {
            if (numb2.trim().equals("")) {
                Toast.makeText(this, R.string.enteranumber, Toast.LENGTH_SHORT).show();
                return;
            } else {
                n1 = Double.parseDouble(e1.getText().toString());
                n2 = Double.parseDouble(e2.getText().toString());
                sum = n1 * n2;
                t1.setText(Double.toString(sum));
                answeranimation();
                SharedPreferences prefs = getSharedPreferences("sound", MODE_PRIVATE);
                int soundid = prefs.getInt("soundid", 1);
                if (soundid == 1) {
                    if (sum <= 0) {
                        MediaPlayer lowswag = MediaPlayer.create(this, R.raw.sadtrombone);
                        lowswag.start();
                    }
                    if (sum > 0) {
                        MediaPlayer lowswag = MediaPlayer.create(this, R.raw.screaming_goat);
                        lowswag.start();
                    }
                }
            }
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();


        return super.onOptionsItemSelected(item);
    }


    public void finish(View v) {
        Intent homeIntent = new Intent(Intent.ACTION_MAIN);
        homeIntent.addCategory(Intent.CATEGORY_HOME);
        homeIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(homeIntent);
    }


    public void openhome(View v) {
        return;
    }




    public void settings(View v) {
        Intent intent = new Intent(MainActivity.this, settings.class);
        startActivity(intent);

    }

    public void iconanimation(View view) {
        Animation scaleup = AnimationUtils.loadAnimation(this, R.anim.icon_animation);
        view.startAnimation(scaleup);
    }


    private void answeranimation() {
        TextView view = (TextView) findViewById(R.id.Answer);
        Animation scaleup = AnimationUtils.loadAnimation(this, R.anim.answer_animation);
        view.startAnimation(scaleup);
    }


    public void imageanimation(View view) {

        Animation scaleup = AnimationUtils.loadAnimation(this, R.anim.centered_rotation);
        view.startAnimation(scaleup);
    }

    public void openflashlight(View view) {
        Intent intent = new Intent(MainActivity.this, Flashlight.class);
        startActivity(intent);
    }

    private void setupdrawer() {
        SharedPreferences colors = getSharedPreferences("colors", settings.MODE_PRIVATE);
        int color = colors.getInt("swag", -26624);
        Toolbar toolbar;
        Drawer.Result result = null;
        SharedPreferences image = getSharedPreferences("image", settings.MODE_PRIVATE);
        Boolean imagebool = image.getBoolean("image", false);
if (!imagebool) {
    headerposition = 0;
}
        else {
    headerposition = 1;
        }
        toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        result = new Drawer()
                .withActivity(this)
                .withToolbar(toolbar)

                .withSelectedItem(0)
                .withDrawerWidthDp(320)
                .withHeader(header[headerposition])
                .addDrawerItems(

                        new PrimaryDrawerItem().withName(R.string.app_name).withIcon(FontAwesome.Icon.faw_calculator).withIdentifier(1).withSelectedTextColor(color).withSelectedIconColor(color),
                        new PrimaryDrawerItem().withName(R.string.title_activity_pythagorean).withIcon(FontAwesome.Icon.faw_chevron_right).withIdentifier(2),
                        new PrimaryDrawerItem().withName(R.string.title_activity_abc__formula).withIcon(FontAwesome.Icon.faw_sort_alpha_asc).withIdentifier(3),
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

                            Intent iinent = new Intent(MainActivity.this, pythagorean.class);
                            startActivity(iinent);
                            finish();
                        }
                        if (drawerItem.getIdentifier() == 3) {
                            Intent iinent = new Intent(MainActivity.this, ABC_Formula.class);
                            startActivity(iinent);
                            finish();
                        }
                        if (drawerItem.getIdentifier() == 4) {
                            Intent iinent = new Intent(MainActivity.this, Circle_Surface.class);
                            startActivity(iinent);
                            finish();
                        }
                        if (drawerItem.getIdentifier() == 5) {
                            Intent iinent = new Intent(MainActivity.this, settings.class);
                            startActivity(iinent);

                        }
                        if (drawerItem.getIdentifier() == 6) {
                            new MaterialDialog.Builder(MainActivity.this)
                                    .title("Help")
                                    .content(R.string.Help)
                                    .positiveText("OK")
                                    .iconRes(R.drawable.help)
                                    .show();
                            setupdrawer();

                        }
                        if (drawerItem.getIdentifier() == 7) {
                            new MaterialDialog.Builder(MainActivity.this)
                                    .title(R.string.about_title)
                                    .customView(R.layout.aboutcards, true)
                                    .positiveText("OK")

                                    .show();
                            setupdrawer();
                        }
                        if (drawerItem.getIdentifier() == 8) {
                            Intent iinent = new Intent(MainActivity.this, Flashlight.class);
                            startActivity(iinent);
                        }
                        if (drawerItem.getIdentifier() == 9) {
                            Intent iinent = new Intent(MainActivity.this, MathFunctions.class);
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

    public void getcolor(String color, Context context) {
        int colorInt = Color.parseColor(color);
        String colorstring = String.valueOf(colorInt);
        EditText t = (EditText) findViewById(R.id.Num1);
        t.setText(colorstring);

    }

    private void setcolor() {
        SharedPreferences buttons = getSharedPreferences("style", MODE_PRIVATE);
        Boolean oldbuttons = buttons.getBoolean("old", false);
        SharedPreferences colors = getSharedPreferences("colors", settings.MODE_PRIVATE);
        int color = colors.getInt("swag", -26624);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(color));
        ThemeSingleton.get().positiveColor = color;
        ThemeSingleton.get().neutralColor = color;
        ThemeSingleton.get().negativeColor = color;

        LinearLayout a = (LinearLayout) findViewById(R.id.answerbg);
        LinearLayout g = (LinearLayout) findViewById(R.id.buttonholder);
        a.setBackgroundDrawable(new ColorDrawable(color));
        Button b = (Button) findViewById(R.id.addup);
        Button c = (Button) findViewById(R.id.minus);
        Button d = (Button) findViewById(R.id.times);
        Button e = (Button) findViewById(R.id.divide);

        Button f = (Button) findViewById(nextsnapbackbutton);

        f.setBackgroundDrawable(new ColorDrawable(color));
        g.setBackgroundDrawable(new ColorDrawable(color));
        if (oldbuttons) {
            g.setBackgroundColor(Color.TRANSPARENT);
            b.setBackgroundDrawable(new ColorDrawable(color));
            c.setBackgroundDrawable(new ColorDrawable(color));
            d.setBackgroundDrawable(new ColorDrawable(color));
            e.setBackgroundDrawable(new ColorDrawable(color));
        }

    }
    public void androidwebsite(View view) {
        Uri uri = Uri.parse("http://developer.android.com/");
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
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


    public void color(MenuItem item) {
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
                setupdrawer();

            }
        });

    }


    public void clearall(MenuItem item) {
        EditText e1 = (EditText) findViewById(R.id.Num1);
        EditText e2 = (EditText) findViewById(R.id.Num2);
        e1.setText("");
        e2.setText("");
    }
}



















