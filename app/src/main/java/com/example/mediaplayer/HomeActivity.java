package com.example.mediaplayer;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.design.widget.NavigationView;
import android.support.v4.app.TaskStackBuilder;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final int REQUEST_ID_MULTIPLE_PERMISSIONS = 11;
    private Dialog myDialog;
    private Dialog mySecondDialog;

    @Override
    public Resources.Theme getTheme() {
        int themeId =this.getSharedPreferences("mySPreference",0).getInt("ThemeId",R.style.AppTheme_NoActionBar_Theme1);

        Resources.Theme theme = super.getTheme();
        if (true){
            theme.applyStyle(themeId,true);
        }
        return theme;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        int themeId =this.getSharedPreferences("mySPreference",0).getInt("ThemeId",R.style.AppTheme);
////        setTheme(themeId);
//
////        setTheme(R.style.AppTheme);
        //////////////////////////////////
//        this.recreate();

        //////////////////////////////
        checkUserPermission();

        setContentView(R.layout.activity_home);
        Toolbar toolbar =  findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

            quoteOfTheDay();

            Fragment singersFragment = new SingersFragment();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.home_fragment_container,singersFragment)
//                .addToBackStack(null)
                    .commit();

//        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView =  findViewById(R.id.nav_view);
        navigationView.setItemIconTintList(null);
        navigationView.setNavigationItemSelectedListener(this);
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer =  findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onPause() {

        super.onPause();
        if (mySecondDialog!= null){
            mySecondDialog.dismiss();
//        HomeActivity.
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.quiz_menu) {
            Intent quizIntent = new Intent(this,QuizActivity.class);
            startActivity(quizIntent);
            // Handle the camera action
        } else if (id == R.id.quotes_menu) {

            Fragment quoteListFragment = new QuoteListFragment();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.home_fragment_container,quoteListFragment)
                    .addToBackStack(null)
                    .commit();

        } else if (id == R.id.quote_of_the_day) {
            quoteOfTheDay();

        } else if (id == R.id.favourites_menu) {
            Fragment favListFragment = new FavListFragment();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.home_fragment_container,favListFragment)
                    .addToBackStack(null)
                    .commit();

        } else if (id == R.id.settings_menu) {

                changeTheme();
        }
        else if (id == R.id.nav_help_menu) {
            String helpTextBody="<!DOCTYPE html>\n" +
                    "<html lang=\"en\">\n" +
                    "<head>\n" +
                    "    <meta charset=\"UTF-8\">\n" +
                    "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                    "    <meta http-equiv=\"X-UA-Compatible\" content=\"ie=edge\">\n" +
                    "    <title>help</title>\n" +
                    "</head>\n" +
                    "<body>\n" +
                    "    <h3 style=\"color:blue;font-family: 'Courier New', Courier, monospace\"> Help</h3>\n" +
                    "    <p>This Application gives you </p>\n" +
                    "    <ul style=\"color:lightseagreen\">\n" +
                    "            <li>Song Lyrics With Your Own Song</li>\n" +
                    "            <li>Bible Quiz</li>\n" +
                    "            <li>Bible Verses</li>\n" +
                    "            <li>You Can Make Your Favourite List of Lyrics</li>\n" +
                    "            <li>You Can Change the Theme </li>\n" +
                    "            <li>You Can set verses to Home Screen Widget </li>\n" +
                    "    </ul>\n" +
                    "    <p>Steps to Use the Lyrics App</p>\n" +
                    "    <ol style=\"color:lightseagreen\">\n" +
                    "        <li>Choose Singer(Artist) You Want</li>\n" +
                    "        <li>Choose Which Album You Want</li>\n" +
                    "        <li>Choose Choose Which Song You Want</li>\n" +
                    "        <li>Finally You Can Use the Lyrics</li>\n" +
                    "        <li>To Choose the Song Click the Icon With a sound icon Which is in Top Bar</li>\n" +
                    "        <li>Then If you Want to Make the Lyrics In TO Favourite list Click the Star Icon <br>If it Is fullWhite it is in the Favourite List\n" +
                    "             But if it's Border only white It is n't in the Favourite list</li>\n" +
                    "\n" +
                    "    </ol>\n" +
                    "    <p>Steps to make the verses into HomeScreen</p>\n" +
                    "    <ol style=\"color:lightseagreen\">\n" +
                    "        <li>long Tap on HomeScreen</li>\n" +
                    "        <li>When the Options Came Click the Widget Option</li>\n" +
                    "        <li>Search for these App</li>\n" +
                    "        <li>Then Drag into HomeScreen</li>\n" +
                    "        <li>To Change Another Click The Next Button</li>\n" +
                    "    \n" +
                    "\n" +
                    "    </ol>\n" +
                    "    <strong>For Another App <a href=\"http://www.nathaniel.awel.ga\">Click Here</a></strong>\n" +
                    "    <h4> Thank Your For Using The App Please Rate This App on Google Play Store <a href=\"https://www.google.com\">Rate Here</a> </h4>\n" +
                    "</body>\n" +
                    "</html>";

            Bundle bundle = new Bundle();
            bundle.putString("help", helpTextBody);
            Fragment helpShowFragment = new HintShowFragment();
            helpShowFragment.setArguments(bundle);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.home_fragment_container,helpShowFragment)
                    .addToBackStack(null)
                    .commit();


        }else if (id == R.id.nav_exit_menu) {
            finish();
        }

        DrawerLayout drawer =  findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    public void quoteOfTheDay(){
//        this.recreate();
        mySecondDialog = new Dialog(this);
        mySecondDialog.setContentView(R.layout.quote_of_the_day_view);
        mySecondDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        DatabaseAccess db = DatabaseAccess.getInstance(this);

        TextView quote_text_view = mySecondDialog.findViewById(R.id.quote_text_view);
        TextView got_it = mySecondDialog.findViewById(R.id.got_it);
        quote_text_view.setText(db.getQuoteOfTheDayList(String.valueOf(new Random().nextInt(852-1+1)+1)));

        got_it.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(HomeActivity.this,"you clicked got it",Toast.LENGTH_SHORT).show();
                mySecondDialog.dismiss();
            }
        });
        mySecondDialog.setCancelable(true);
        mySecondDialog.show();



    }
//    public boolean RequestPermission(){
//        int readStoragePermission = 0;
//        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
//            readStoragePermission = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
//        }
//        int wakeLockPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.WAKE_LOCK);
//        List<String> listPermissionsNeeded = new ArrayList<>();
//        if (readStoragePermission != PackageManager.PERMISSION_GRANTED) {
//            listPermissionsNeeded.add(Manifest.permission.READ_PHONE_STATE);
//        }
//        if (wakeLockPermission != PackageManager.PERMISSION_GRANTED) {
//            listPermissionsNeeded.add(Manifest.permission.READ_PHONE_STATE);
//        }
//        if (!listPermissionsNeeded.isEmpty()) {
//            ActivityCompat.requestPermissions(this, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]),REQUEST_ID_MULTIPLE_PERMISSIONS);
//
//            return false;
//        }
//        return true;
//    }

    private boolean checkUserPermission(){
        if(Build.VERSION.SDK_INT>=23){
            if (ContextCompat.checkSelfPermission(this,Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,}, 121);
        }
            if (ContextCompat.checkSelfPermission(this,Manifest.permission.WAKE_LOCK)!= PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WAKE_LOCK,}, 121);
            }
        }
        return true;
    }

    public void changeTheme(){
//        this.recreate();
        SharedPreferences sharedPreferences =getSharedPreferences("mySPreference",MODE_PRIVATE);
        final SharedPreferences.Editor editor = sharedPreferences.edit();

        myDialog = new Dialog(this);
        myDialog.setContentView(R.layout.change_theme_view);
//        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        CardView Theme1 = myDialog.findViewById(R.id.theme1);
        CardView Theme2 = myDialog.findViewById(R.id.theme2);
        CardView Theme3 = myDialog.findViewById(R.id.theme3);
        CardView Theme4 = myDialog.findViewById(R.id.theme4);
        CardView Theme5 = myDialog.findViewById(R.id.theme5);
        CardView Theme6 = myDialog.findViewById(R.id.theme6);
        CardView Theme7 = myDialog.findViewById(R.id.theme7);
        CardView Theme8 = myDialog.findViewById(R.id.theme8);
        CardView ThemeDefault = myDialog.findViewById(R.id.theme_default);

        Theme1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.putInt("ThemeId",R.style.AppTheme_NoActionBar_Theme1);
                editor.apply();
//                this.recreate();
                TaskStackBuilder.create(HomeActivity.this)
                        .addNextIntent(new Intent(HomeActivity.this, HomeActivity.class))
                        .addNextIntent(HomeActivity.this.getIntent())
                        .startActivities();
                myDialog.dismiss();
            }
        });

        Theme2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.putInt("ThemeId",R.style.AppTheme_NoActionBar_Theme2);
                editor.apply();
//                this.recreate();
                TaskStackBuilder.create(HomeActivity.this)
                        .addNextIntent(new Intent(HomeActivity.this, HomeActivity.class))
                        .addNextIntent(HomeActivity.this.getIntent())
                        .startActivities();
                myDialog.dismiss();
            }
        });

        Theme3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.putInt("ThemeId",R.style.AppTheme_NoActionBar_Theme3);
                editor.apply();
//                this.recreate();
                TaskStackBuilder.create(HomeActivity.this)
                        .addNextIntent(new Intent(HomeActivity.this, HomeActivity.class))
                        .addNextIntent(HomeActivity.this.getIntent())
                        .startActivities();
                myDialog.dismiss();
            }
        });
        Theme4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.putInt("ThemeId",R.style.AppTheme_NoActionBar_Theme4);
                editor.apply();
//                this.recreate();
                TaskStackBuilder.create(HomeActivity.this)
                        .addNextIntent(new Intent(HomeActivity.this, HomeActivity.class))
                        .addNextIntent(HomeActivity.this.getIntent())
                        .startActivities();
                myDialog.dismiss();
            }
        });
        Theme5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.putInt("ThemeId",R.style.AppTheme_NoActionBar_Theme5);
                editor.apply();
//                this.recreate();
                TaskStackBuilder.create(HomeActivity.this)
                        .addNextIntent(new Intent(HomeActivity.this, HomeActivity.class))
                        .addNextIntent(HomeActivity.this.getIntent())
                        .startActivities();
                myDialog.dismiss();
            }
        });
        Theme6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.putInt("ThemeId",R.style.AppTheme_NoActionBar_Theme6);
                editor.apply();
//                this.recreate();
                TaskStackBuilder.create(HomeActivity.this)
                        .addNextIntent(new Intent(HomeActivity.this, HomeActivity.class))
                        .addNextIntent(HomeActivity.this.getIntent())
                        .startActivities();
                myDialog.dismiss();
            }
        });
        Theme7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.putInt("ThemeId",R.style.AppTheme_NoActionBar_Theme7);
                editor.apply();
//                this.recreate();
                TaskStackBuilder.create(HomeActivity.this)
                        .addNextIntent(new Intent(HomeActivity.this, HomeActivity.class))
                        .addNextIntent(HomeActivity.this.getIntent())
                        .startActivities();
                myDialog.dismiss();
            }
        });
        Theme8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.putInt("ThemeId",R.style.AppTheme_NoActionBar_Theme8);
                editor.apply();
//                this.recreate();
                TaskStackBuilder.create(HomeActivity.this)
                        .addNextIntent(new Intent(HomeActivity.this, HomeActivity.class))
                        .addNextIntent(HomeActivity.this.getIntent())
                        .startActivities();
                myDialog.dismiss();
            }
        });
        ThemeDefault.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.putInt("ThemeId",R.style.AppTheme_NoActionBar_Default);
                editor.apply();
//                this.recreate();
                TaskStackBuilder.create(HomeActivity.this)
                        .addNextIntent(new Intent(HomeActivity.this, HomeActivity.class))
                        .addNextIntent(HomeActivity.this.getIntent())
                        .startActivities();
                myDialog.dismiss();
            }
        });

        myDialog.show();
        myDialog.setCancelable(true);
    }


}
