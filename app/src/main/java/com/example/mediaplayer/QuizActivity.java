package com.example.mediaplayer;

import android.content.DialogInterface;
import android.content.res.Resources;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class QuizActivity extends AppCompatActivity {
    boolean sureToQuit=false;

    @Override
    public Resources.Theme getTheme() {
        int themeId =this.getSharedPreferences("mySPreference",0).getInt("ThemeId",R.style.AppTheme_NoActionBar_Default);

        Resources.Theme theme = super.getTheme();
        if (true){
            theme.applyStyle(themeId,true);
        }
        return theme;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        int themeId =this.getSharedPreferences("mySPreference",0).getInt("ThemeId",R.style.AppTheme);
//        setTheme(themeId);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
    }

//    @Override
//    public void onBackPressed() {
//        super.onBackPressed();
//
//    }

    @Override
    protected void onDestroy() {
        QuizFragment   quizFragment = new QuizFragment();
//        quizFragment.asyncTasks=null;
//        quizFragment.asyncTasks = new QuizFragment().AsyncTasks;
        super.onDestroy();
    }

    public void StartBtnonClick(View view) {

        Fragment quizFragment = new QuizFragment();

        getSupportFragmentManager().beginTransaction()
                .add(R.id.quiz_fragment_container,quizFragment)
                .addToBackStack(null)
                .commit();


    }
//
//    @Override
//    public void onBackPressed() {
//        Fragment fragment = getSupportFragmentManager().getFragments().get(getSupportFragmentManager().getBackStackEntryCount()-1);
//        if (fragment instanceof OnBackPressed){
//            ((OnBackPressed) fragment).onBackPressed();
//                    Toast.makeText(this, "the back stack "+fragment, Toast.LENGTH_SHORT).show();
//            if (alertDialog()){
//                Toast.makeText(this, "yes Exit ", Toast.LENGTH_SHORT).show();
//
//                Fragment quizFragment = new SingersFragment();
//                getSupportFragmentManager().beginTransaction()
//                        .add(R.id.quiz_fragment_container,quizFragment)
//                        .addToBackStack(null)
//                        .commit();
//                getSupportFragmentManager().popBackStack();
//                super.onBackPressed();
//
//            }else {
//
//            }
//        }


//
//        int count = getSupportFragmentManager().getBackStackEntryCount();
//        Toast.makeText(this, "the back stack "+count, Toast.LENGTH_SHORT).show();
//
//        if (count== 0){
//            super.onBackPressed();
//        }else {
//            Toast.makeText(this, "on back pressed", Toast.LENGTH_SHORT).show();
//            if (alertDialog()){
//                Fragment quizFragment = new QuizFragment();
//                getSupportFragmentManager().beginTransaction()
//                        .add(R.id.quiz_fragment_container,quizFragment)
//                        .addToBackStack(null)
//                        .commit();
//            }else{
//                super.onBackPressed();
//            }
//
//        }
//    }

//    public boolean alertDialog(){
//        new AlertDialog.Builder(this)
//                .setTitle("Bible Quiz")
//                .setMessage("Are You Sure You Want to Quit the Quiz?")
//                .setIcon(R.drawable.ic_launcher_background)
//                .setCancelable(false)
//                .setPositiveButton("OK!!!",
//                        new DialogInterface.OnClickListener() {
//
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                sureToQuit = true;
//                            }
//                        })
//                .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int id) {
//                        dialog.cancel();
//                        sureToQuit = false;
//                    }
//                }).show();
//        return sureToQuit;
//
//    }

}
