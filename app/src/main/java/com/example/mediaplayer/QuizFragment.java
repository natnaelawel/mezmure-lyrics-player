package com.example.mediaplayer;


import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static android.content.Context.MODE_PRIVATE;


/**
 * A simple {@link Fragment} subclass.
 */
public class QuizFragment extends Fragment implements View.OnClickListener {
    private static final long START_TIME_IN_MILLIS = 120000;
    private CountDownTimer mCountDownTimer;
    private boolean mTimerRunning;
    private long mTimeLeftInMillis = START_TIME_IN_MILLIS;
    private long mEndTime;View rootView;
    int CorrectAnsPosition;
    int questionIndexCounter;
    int selectedQuestion;
    BottomNavigationView bottomNavigationView;
    TextView remainingTimeTxtView,question_display_textView,choice1,choice2,choice3,choice4;
    ArrayList<Integer> questionedIndex =new ArrayList<>();
//    AsyncTasks  asyncTasks = new AsyncTasks();
    String[][] question = {
            {"How many days did it take God to create the universe?",
                    "7","6","99","1","6",
                    "1.) In the beginning God created the heaven and the earth.\n" +
                            "\n" +
                            "2.) And the earth was without form, and void; and darkness was upon the face of the deep. And the Spirit of God moved upon the face of the waters.\n" +
                            "\n" +
                            "3.) And God said, Let there be light: and there was light.\n" +
                            "\n" +
                            "4.) And God saw the light, that it was good: and God divided the light from the darkness.\n" +
                            "\n" +
                            "5.) And God called the light Day, and the darkness he called Night. And the evening and the morning were the first day.\n" +
                            "\n" +
                            "6.) And God said, Let there be a firmament in the midst of the waters, and let it divide the waters from the waters.\n" +
                            "\n" +
                            "7.) And God made the firmament, and divided the waters which were under the firmament from the waters which were above the firmament: and it was so.\n" +
                            "\n" +
                            "8.) And God called the firmament Heaven. And the evening and the morning were the second day.\n" +
                            "\n" +
                            "9.) And God said, Let the waters under the heaven be gathered together unto one place, and let the dry land appear: and it was so.\n" +
                            "\n" +
                            "10.) And God called the dry land Earth; and the gathering together of the waters called he Seas: and God saw that it was good.\n" +
                            "\n" +
                            "11.) And God said, Let the earth bring forth grass, the herb yielding seed, and the fruit tree yielding fruit after his kind, whose seed is in itself, upon the earth: and it was so.\n" +
                            "\n" +
                            "12.) And the earth brought forth grass, and herb yielding seed after his kind, and the tree yielding fruit, whose seed was in itself, after his kind: and God saw that it was good.\n" +
                            "\n" +
                            "13.) And the evening and the morning were the third day.\n" +
                            "\n" +
                            "14.) And God said, Let there be lights in the firmament of the heaven to divide the day from the night; and let them be for signs, and for seasons, and for days, and years:\n" +
                            "\n" +
                            "15.) And let them be for lights in the firmament of the heaven to give light upon the earth: and it was so.\n" +
                            "\n" +
                            "16.) And God made two great lights; the greater light to rule the day, and the lesser light to rule the night: he made the stars also.\n" +
                            "\n" +
                            "17.) And God set them in the firmament of the heaven to give light upon the earth,\n" +
                            "\n" +
                            "18.) And to rule over the day and over the night, and to divide the light from the darkness: and God saw that it was good.\n" +
                            "\n" +
                            "19.) And the evening and the morning were the fourth day.\n" +
                            "\n" +
                            "20.) And God said, Let the waters bring forth abundantly the moving creature that hath life, and fowl that may fly above the earth in the open firmament of heaven.\n" +
                            "\n" +
                            "21.) And God created great whales, and every living creature that moveth, which the waters brought forth abundantly, after their kind, and every winged fowl after his kind: and God saw that it was good.\n" +
                            "\n" +
                            "22.) And God blessed them, saying, Be fruitful, and multiply, and fill the waters in the seas, and let fowl multiply in the earth.\n" +
                            "\n" +
                            "23.) And the evening and the morning were the fifth day.\n" +
                            "\n" +
                            "24.) And God said, Let the earth bring forth the living creature after his kind, cattle, and creeping thing, and beast of the earth after his kind: and it was so.\n" +
                            "\n" +
                            "25.) And God made the beast of the earth after his kind, and cattle after their kind, and every thing that creepeth upon the earth after his kind: and God saw that it was good.\n" +
                            "\n" +
                            "26.) And God said, Let us make man in our image, after our likeness: and let them have dominion over the fish of the sea, and over the fowl of the air, and over the cattle, and over all the earth, and over every creeping thing that creepeth upon the earth.\n" +
                            "\n" +
                            "27.) So God created man in his own image, in the image of God created he him; male and female created he them.\n" +
                            "\n" +
                            "28.) And God blessed them, and God said unto them, Be fruitful, and multiply, and replenish the earth, and subdue it: and have dominion over the fish of the sea, and over the fowl of the air, and over every living thing that moveth upon the earth.\n" +
                            "\n" +
                            "29.) And God said, Behold, I have given you every herb bearing seed, which is upon the face of all the earth, and every tree, in the which is the fruit of a tree yielding seed; to you it shall be for meat.\n" +
                            "\n" +
                            "30.) And to every beast of the earth, and to every fowl of the air, and to every thing that creepeth upon the earth, wherein there is life, I have given every green herb for meat: and it was so.\n" +
                            "\n" +
                            "31.) And God saw every thing that he had made, and, behold, it was very good. And the evening and the morning were the sixth day.\n" +
                            "\n"},
            {"What did God create in the first day?",
                    "Water","Air","Celestial bodies (planets, moons, stars, etc.)","Light","Light",
                    "3.) And God said, Let there be light: and there was light.\n" +
                            "\n" +
                            "4.) And God saw the light, that it was good: and God divided the light from the darkness.\n" +
                            "\n" +
                            "5.) And God called the light Day, and the darkness he called Night. And the evening and the morning were the first day.\n" +
                            "\n"},
            {"What did God create on the second day?",
                    "Animals","The firmament (the sky)","Land","Plants","The firmament (the sky)",
                    "Genesis 1:6-8\n" +
                            "6.) And God said, Let there be a firmament in the midst of the waters, and let it divide the waters from the waters.\n" +
                            "\n" +
                            "7.) And God made the firmament, and divided the waters which were under the firmament from the waters which were above the firmament: and it was so.\n" +
                            "\n" +
                            "8.) And God called the firmament Heaven. And the evening and the morning were the second day."},
            {"What did God create on the third day?",
                    "Plant Life","The seas","Land","All of these","All of these",
                    "Genesis 1:9-13\n" +
                            "9.) And God said, Let the waters under the heaven be gathered together unto one place, and let the dry land appear: and it was so.\n" +
                            "\n" +
                            "10.) And God called the dry land Earth; and the gathering together of the waters called he Seas: and God saw that it was good.\n" +
                            "\n" +
                            "11.) And God said, Let the earth bring forth grass, the herb yielding seed, and the fruit tree yielding fruit after his kind, whose seed is in itself, upon the earth: and it was so.\n" +
                            "\n" +
                            "12.) And the earth brought forth grass, and herb yielding seed after his kind, and the tree yielding fruit, whose seed was in itself, after his kind: and God saw that it was good.\n" +
                            "\n" +
                            "13.) And the evening and the morning were the third day.\n" +
                            "\n"},
            {"What did God create on the fourth day?",
                    "Animals","The waters","Celestial bodies (planets, moons, stars, etc.)","Plants","Celestial bodies (planets, moons, stars, etc.)",
                    "Genesis 1:14-19\n" +
                            "14.) And God said, Let there be lights in the firmament of the heaven to divide the day from the night; and let them be for signs, and for seasons, and for days, and years:\n" +
                            "\n" +
                            "15.) And let them be for lights in the firmament of the heaven to give light upon the earth: and it was so.\n" +
                            "\n" +
                            "16.) And God made two great lights; the greater light to rule the day, and the lesser light to rule the night: he made the stars also.\n" +
                            "\n" +
                            "17.) And God set them in the firmament of the heaven to give light upon the earth,\n" +
                            "\n" +
                            "18.) And to rule over the day and over the night, and to divide the light from the darkness: and God saw that it was good.\n" +
                            "\n" +
                            "19.) And the evening and the morning were the fourth day."},
            {"What did God create on the fifth day?",
                    "All animals except those that live in the ocean","All animals","All animals except those that live on the land","All animals except those that live in the sky","All animals except those that live on the land",
                    "Genesis 1:20-23\n" +
                            "20.) And God said, Let the waters bring forth abundantly the moving creature that hath life, and fowl that may fly above the earth in the open firmament of heaven.\n" +
                            "\n" +
                            "21.) And God created great whales, and every living creature that moveth, which the waters brought forth abundantly, after their kind, and every winged fowl after his kind: and God saw that it was good.\n" +
                            "\n" +
                            "22.) And God blessed them, saying, Be fruitful, and multiply, and fill the waters in the seas, and let fowl multiply in the earth.\n" +
                            "\n" +
                            "23.) And the evening and the morning were the fifth day."},
            {"Which did God NOT create on the sixth day?",
                    "Man","Some Animals","Woman","Birds","Birds",
                    "Genesis 1:24-27\n" +
                            "24.) And God said, Let the earth bring forth the living creature after his kind, cattle, and creeping thing, and beast of the earth after his kind: and it was so.\n" +
                            "\n" +
                            "25.) And God made the beast of the earth after his kind, and cattle after their kind, and every thing that creepeth upon the earth after his kind: and God saw that it was good.\n" +
                            "\n" +
                            "26.) And God said, Let us make man in our image, after our likeness: and let them have dominion over the fish of the sea, and over the fowl of the air, and over the cattle, and over all the earth, and over every creeping thing that creepeth upon the earth.\n" +
                            "\n" +
                            "27.) So God created man in his own image, in the image of God created he him; male and female created he them."},
            {"What did God create on the seventh day?",
                    "Nothing","Man","Language","Woman","Nothing",
                    "Genesis 2:2\n" +
                            "2.) And on the seventh day God ended his work which he had made; and he rested on the seventh day from all his work which he had made."},
            {"What did God call the firmament?",
                    "Space","Cloud","Earth","Heaven","Heaven",
                    "Genesis 1:8\n" +
                            "8.) And God called the firmament Heaven. And the evening and the morning were the second day."},
            {"What did God call the dry land and the waters?",
                    "Valleys; Lakes","Mountains; Oceans","Earth; Seas","Stone; Water","Earth; Seas",
                    "Genesis 1:20\n" +
                            "20.) And God said, Let the waters bring forth abundantly the moving creature that hath life, and fowl that may fly above the earth in the open firmament of heaven.\n" +
                            "\n"}
    };
    private boolean sureToQuit=false;
    TextView questionCounter;
    public QuizFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        rootView = inflater.inflate(R.layout.fragment_quiz, container, false);

        remainingTimeTxtView = rootView.findViewById(R.id.remainingTime);
        questionCounter = rootView.findViewById(R.id.question_counter);
        question_display_textView = rootView.findViewById(R.id.question_display_textview);
        choice1 = rootView.findViewById(R.id.choice1);
        choice2 = rootView.findViewById(R.id.choice2);
        choice3 = rootView.findViewById(R.id.choice3);
        choice4 = rootView.findViewById(R.id.choice4);
        choice1.setOnClickListener(this);
        choice2.setOnClickListener(this);
        choice3.setOnClickListener(this);
        choice4.setOnClickListener(this);

        bottomNavigationView = rootView.findViewById(R.id.bottom_navigation);
//        bottomNavigationView.setItemBackground(getResources().getDrawable(R.attr.themePrimary));

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                switch (menuItem.getItemId()){
                    case R.id.prev:{
                        if (questionIndexCounter>0) {
                            fetchPrevQuestion();
                            questionCounter.setText((questionIndexCounter+1)+"/"+question.length);
                            choice1.setBackgroundColor(Color.WHITE);
                            choice2.setBackgroundColor(Color.WHITE);
                            choice3.setBackgroundColor(Color.WHITE);
                            choice4.setBackgroundColor(Color.WHITE);
                        }
                        else{
                            Toast.makeText(getActivity(), "No Previous Question", Toast.LENGTH_SHORT).show();
                        }
                        break;
                    }
                    case R.id.hint:{
                        Fragment hintshowFragment = new HintShowFragment();
                        Bundle bundle = new Bundle();
                        bundle.putString("hint",question[CorrectAnsPosition][6]);
                        hintshowFragment.setArguments(bundle);
                        getActivity().getSupportFragmentManager().beginTransaction()
                                .add(R.id.quiz_fragment_container,hintshowFragment)
                                .addToBackStack(null)
                                .commit();

                        break;
                    }
                    case R.id.next:{
                        if (questionIndexCounter<question.length-1) {
                            fetchQuestion();
                            questionCounter.setText((questionIndexCounter+1)+"/"+question.length);
                            choice1.setBackgroundColor(Color.WHITE);
                            choice2.setBackgroundColor(Color.WHITE);
                            choice3.setBackgroundColor(Color.WHITE);
                            choice4.setBackgroundColor(Color.WHITE);
                        }else{
                            Toast.makeText(getActivity(), "No More Question is Available", Toast.LENGTH_SHORT).show();
                        }
                        break;
                    }
                }
                return true;
            }
        });
//        if (asyncTasks==null) {
//            startcount();
//        }else {
//            asyncTasks.selfRestart();
//            startcount();
//        }
        if (savedInstanceState != null){
            mTimeLeftInMillis = savedInstanceState.getLong("millisLeft");
            mTimerRunning = savedInstanceState.getBoolean("timerRunning");
            questionIndexCounter = savedInstanceState.getInt("questionIndexCounter");
            selectedQuestion = savedInstanceState.getInt("selectedQuestion");
            questionedIndex = savedInstanceState.getIntegerArrayList("questionedIndex");
            updateCountDownText();

            if (mTimerRunning) {
                questionCounter.setText((questionIndexCounter+1)+"/"+question.length);
                mEndTime = savedInstanceState.getLong("endTime");
                mTimeLeftInMillis = mEndTime - System.currentTimeMillis();
                startTimer();

                question_display_textView.setText(question[selectedQuestion][0]);
                choice1.setText(question[selectedQuestion][1]);
                choice2.setText(question[selectedQuestion][2]);
                choice3.setText(question[selectedQuestion][3]);
                choice4.setText(question[selectedQuestion][4]);
                CorrectAnsPosition = selectedQuestion;
        }




        }else {
            fetchQuestion();
            startTimer();
        }
        return rootView;
    }

    private void saveScore(int score){
        SharedPreferences sharedPreferences =getContext().getSharedPreferences("Score",MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
//        int previousHighScore = getSharedPreferences("Score",MODE_PRIVATE).getInt("HighScore",0);
        editor.putInt("HighScore", score);
        editor.apply();

    }

//    @Override
//    public void onDetach() {
//        Toast.makeText(getActivity(), "on detach", Toast.LENGTH_SHORT).show();
//        if (alertDialog()){
//
//        }else {
//            super.onDetach();
//        }
//
//
//    }

    public int nextValue(){
//        Random r = new Random();
//        user_answer.setText("", TextView.BufferType.EDITABLE);

       int selectedQuestion = new Random().nextInt(question.length);
        boolean notFound = true;
       do {
           if (questionedIndex.indexOf(selectedQuestion) == -1){
               notFound = false;

           } else {
               selectedQuestion = new Random().nextInt(question.length);

           }
       }
       while (notFound);
       questionedIndex.add(selectedQuestion);
       return selectedQuestion;

    }
//    public void fetchQuestion(){
//        int previousIndex;
//        if (questionedIndex.size()<1) {
//            Random random = new Random();
//            selectedQuestion = random.nextInt((question.length -1));
//            Log.e("Question", "fetchQuestion:  question Arraylist length is "+question.length);
//            questionedIndex.add(selectedQuestion);
//            Log.e("index 1", "fetchQuestion:  selected" +
//                    "Question Index"+selectedQuestion+"\n"+questionedIndex );
////            questionIndexCounter++;
//        }else {
//            if (questionIndexCounter<questionedIndex.size()-1){
//                selectedQuestion = questionIndexCounter;
//            }else {
//                selectedQuestion = nextValue();
//            }
//
//            questionIndexCounter++;
//            Log.e("index second", "fetchQuestion:  selected" +
//                    "Question Index"+selectedQuestion+"\n"+questionedIndex+" question index counter is " + questionIndexCounter);
//
//        }
//        question_display_textView.setText(question[selectedQuestion][0]);
//        choice1.setText(question[selectedQuestion][1]);
//        choice2.setText(question[selectedQuestion][2]);
//        choice3.setText(question[selectedQuestion][3]);
//        choice4.setText(question[selectedQuestion][4]);
//        CorrectAnsPosition = selectedQuestion;
//
//    }
    public void fetchPrevQuestion(){
        int previouseQuestion=0;
        if (questionedIndex.size()<1) {

            Log.e("Question", "No question are in previous");

        }else {

            previouseQuestion = questionedIndex.get(questionIndexCounter-1);
            questionIndexCounter--;
            Log.e("index second", "fetchQuestion:  selected" +
                    "Question Index"+previouseQuestion+"\n"+questionedIndex +" question index counter is " + questionIndexCounter);


        }
        question_display_textView.setText(question[previouseQuestion][0]);
        choice1.setText(question[previouseQuestion][1]);
        choice2.setText(question[previouseQuestion][2]);
        choice3.setText(question[previouseQuestion][3]);
        choice4.setText(question[previouseQuestion][4]);
        CorrectAnsPosition = previouseQuestion;


    }

    public boolean alertDialog(){
        new AlertDialog.Builder(getActivity())
                .setTitle("Bible Quiz")
                .setMessage("Are You Sure You Want to Quit the Quiz?")
                .setIcon(R.drawable.ic_launcher_background)
                .setCancelable(false)
                .setPositiveButton("OK!!!",
                        new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                sureToQuit = true;
                            }
                        })
                .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        sureToQuit = false;
                    }
                }).show();
        return sureToQuit;

    }

//    public void startcount() {
////        Toast.makeText(getApplicationContext(),"download amount is"+amount,Toast.LENGTH_SHORT).show();
////        nextValue();
////        start_btn.setClickable(false);
//
//        asyncTasks.execute();
//    }

//    public boolean checkAnswer(){
//
//        int result;
//        switch (operator){
//            case 0:{
//                result = left_num + right_num;
//                if (user_answer_num ==result){
////                    isCorrect = true;
//                }
//                break;
//            }
//            case 1:{
//                result = left_num - right_num;
//                if (user_answer_num ==result){
////                    isCorrect = true;
//                }
//                break;
//            }
//            case 2:{
//                result = left_num * right_num;
//                if (user_answer_num ==result){
////                    isCorrect = true;
//                }
//                break;
//            }
//
//            default:{
//                return false;
//            }
//        }
//        return false;
////        return isCorrect;
//    }

    @Override
    public void onClick(View v) {
        v.setBackgroundColor(Color.RED);
        TextView t = ((TextView)v);
        String str = t.getText().toString();
        switch (v.getId()){
            case R.id.choice1:{
                if (question[CorrectAnsPosition][5].equals(str)){
                    choice1.setBackgroundColor(Color.GREEN);
                }

                break;
            }
            case R.id.choice2:{
                if (question[CorrectAnsPosition][5].equals(str)){
                    choice2.setBackgroundColor(Color.GREEN);
                }
                break;
            }
            case R.id.choice3:{
                if (question[CorrectAnsPosition][5].equals(str)){
                    choice3.setBackgroundColor(Color.GREEN);
                }
                break;
            }
            case R.id.choice4:{
                if (question[CorrectAnsPosition][5].equals(str)){
                    choice4.setBackgroundColor(Color.GREEN);
                }
                break;
            }
            default:{

            }
        }

    }

//    class AsyncTasks extends android.os.AsyncTask<Void,String,Void>{
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//        }
//
//        @Override
//        protected void onPostExecute(Void aVoid) {
//            super.onPostExecute(aVoid);
//        }
//
//        @Override
//        protected void onProgressUpdate(String... values) {
//            super.onProgressUpdate(values);
////            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//////                Toast.makeText(getApplicationContext(),"download Progress "+values,Toast.LENGTH_SHORT).show();
////                numberTime.setText(values[0]);
//////                Log.e("progress", "onProgressUpdate: "+values[0] );
////            }
//            String remainingTime = values[0];
//            if (remainingTime.startsWith("0")){
//
//                remainingTimeTxtView.setTextColor(Color.RED);
//            }
//            else {
//                remainingTimeTxtView.setTextColor(Color.BLACK);
//            }
//            remainingTimeTxtView.setText(values[0]);
//        }
//
//        @Override
//        protected Void doInBackground(Void... voids) {
//
//            for (int i = 10*60;i>=0;i--){
//                publishProgress(timer(i*1000));
//                try {
//                    Thread.sleep(1000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//            return null;
//        }
//
//        public void selfRestart(){
//            asyncTasks = new AsyncTasks();
//        }
//    }

    public String timer(long milliSec){
        String finalTimerString = "";
        String secondString = "";
        int hours =(int) (milliSec/(1000*60*60));
        int minutes =(int) (milliSec%(1000*60*60))/(1000*60);
        int seconds = (int)((milliSec%(1000*60*60))%(1000*60)/1000);

        if (hours>0){
            finalTimerString = hours+":";
        }
        if (seconds<10){
            secondString= "0"+seconds;
        }
        else {
            secondString= ""+seconds;
        }
        finalTimerString = finalTimerString+ minutes+":"+secondString;

        return finalTimerString;
    }
//    public String timer(long milliSec){
//        String finalTimerString = "";
//        String secondString = "";
//        int hours =(int) (milliSec/(1000*60*60));
//        int minutes =(int) (milliSec%(1000*60*60))/(1000*60);
//        int seconds = (int)((milliSec%(1000*60*60))%(1000*60)/1000);
//
//        if (hours>0){
//            finalTimerString = hours+":";
//        }
//        if (seconds<10){
//            secondString= "0"+seconds;
//        }
//        else {
//            secondString= ""+seconds;
//        }
//        finalTimerString = finalTimerString+ minutes+":"+secondString;
//
//        return finalTimerString;
//    }

    @Override
    public void onDetach() {
        super.onDetach();
//        asyncTasks.cancel(true);
//        getActivity().getSupportFragmentManager().popBackStack();

//        asyncTasks.selfRestart();
//        Log.e("ondetach", "onDetach: is on detach"+asyncTasks.isCancelled()  );
    }
//
//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//        Log.e("on Attach", "onDetach: is on detach"  );
////        asyncTasks.execute();
//
//
//    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putLong("millisLeft", mTimeLeftInMillis);
        outState.putBoolean("timerRunning", mTimerRunning);
        outState.putLong("endTime", mEndTime);

        outState.putInt("questionIndexCounter",questionIndexCounter);
        outState.putInt("selectedQuestion",selectedQuestion);
        outState.putIntegerArrayList("questionedIndex",questionedIndex);


    }

    public void fetchQuestion(){
        int previousIndex;
        if (questionedIndex.size()<1) {
            Random random = new Random();
            selectedQuestion = random.nextInt((question.length -1));
            Log.e("Question", "fetchQuestion:  question Arraylist length is "+question.length);
            questionedIndex.add(selectedQuestion);
            Log.e("index 1", "fetchQuestion:  selected" +
                    "Question Index"+selectedQuestion+"\n"+questionedIndex );
//            questionIndexCounter++;
        }else {
            if (questionIndexCounter<questionedIndex.size()-1){
                selectedQuestion = questionIndexCounter;
            }else {
                selectedQuestion = nextValue();
            }

            questionIndexCounter++;
            Log.e("index second", "fetchQuestion:  selected" +
                    "Question Index"+selectedQuestion+"\n"+questionedIndex+" question index counter is " + questionIndexCounter);

        }
        question_display_textView.setText(question[selectedQuestion][0]);
        choice1.setText(question[selectedQuestion][1]);
        choice2.setText(question[selectedQuestion][2]);
        choice3.setText(question[selectedQuestion][3]);
        choice4.setText(question[selectedQuestion][4]);
        CorrectAnsPosition = selectedQuestion;

    }

//    @Override
//    public void onRestoreInstanceState(Bundle sa) {
//        super.onSaveInstanceState(outState);
//        outState.putLong("millisLeft", mTimeLeftInMillis);
//        outState.putBoolean("timerRunning", mTimerRunning);
//        outState.putLong("endTime", mEndTime);
//    }

//    @Override
//    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
//        super.onViewStateRestored(savedInstanceState);
//        mTimeLeftInMillis = savedInstanceState.getLong("millisLeft");
//        mTimerRunning = savedInstanceState.getBoolean("timerRunning");
//        updateCountDownText();
//
//        if (mTimerRunning) {
//            mEndTime = savedInstanceState.getLong("endTime");
//            mTimeLeftInMillis = mEndTime - System.currentTimeMillis();
//            startTimer();
//        }
//    }
    private void startTimer() {
        mEndTime = System.currentTimeMillis() + mTimeLeftInMillis;
        mCountDownTimer = new CountDownTimer(mTimeLeftInMillis+1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                mTimeLeftInMillis = millisUntilFinished;
                updateCountDownText();

            }

            @Override
            public void onFinish() {
                mTimerRunning = false;
                Log.e("onFinish", "onFinish: is finished" );
            }
        }.start();

        mTimerRunning = true;
    }


//    @Override
//    public void onRestoreInstanceState(Bundle savedInstanceState) {
//        super.onRestoreInstanceState(savedInstanceState);
//
//        mTimeLeftInMillis = savedInstanceState.getLong("millisLeft");
//        mTimerRunning = savedInstanceState.getBoolean("timerRunning");
//        updateCountDownText();
//        updateButtons();
//
//        if (mTimerRunning) {
//            mEndTime = savedInstanceState.getLong("endTime");
//            mTimeLeftInMillis = mEndTime - System.currentTimeMillis();
//            startTimer();
//        }
//    }






    private void updateCountDownText() {
        if (mTimeLeftInMillis<60000) {
            int themeId =getActivity().getSharedPreferences("mySPreference",0).getInt("ThemeId",R.style.AppTheme_NoActionBar_Theme1);

            if (themeId == R.style.AppTheme_NoActionBar_Theme8){
                remainingTimeTxtView.setTextColor(Color.BLACK);

            }else {
            remainingTimeTxtView.setTextColor(Color.RED);
        }
        }
        else {
            remainingTimeTxtView.setTextColor(Color.WHITE);

        }
        remainingTimeTxtView.setText(timer(mTimeLeftInMillis));
    }
    }
