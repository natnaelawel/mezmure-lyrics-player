package com.example.mediaplayer;

import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ArrayList<SongInfo> _songs = new ArrayList<>();;
    RecyclerView recyclerView;
    SeekBar seekBar;
    TextView currentDuration, totalDuration;
    SongAdapter songAdapter;
    ImageButton play_pause_btn;
    MediaPlayer mediaPlayer;
    private Handler myHandler = new Handler();;
    private Runnable runnabled;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView =  findViewById(R.id.recyclerView);
        seekBar =  findViewById(R.id.seekBar);
        songAdapter = new SongAdapter(this,_songs);
        recyclerView.setAdapter(songAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                linearLayoutManager.getOrientation());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addItemDecoration(dividerItemDecoration);
        play_pause_btn = findViewById(R.id.play_pause_btn);
        play_pause_btn.setOnClickListener(this);
        currentDuration = findViewById(R.id.current_duration);
        totalDuration = findViewById(R.id.total_duration);

        songAdapter.setOnItemClickListener(new SongAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(final Button b, View view, final SongInfo obj, int position) {
                if(b.getText().equals("Stop")){
                    mediaPlayer.stop();
                    mediaPlayer.reset();
                    mediaPlayer.release();
                    mediaPlayer = null;
                    b.setText("Play");

                }else {

                    if (mediaPlayer != null && mediaPlayer.isPlaying()){
                        mediaPlayer.getCurrentPosition();
                        mediaPlayer.stop();
                        mediaPlayer.reset();
                        mediaPlayer.release();
                        mediaPlayer = null;
                        b.setText("Play");
                    }
                    try {
                        mediaPlayer = new MediaPlayer();
                        mediaPlayer.setDataSource(obj.getSongUrl());
                        mediaPlayer.prepareAsync();
                        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                            @Override
                            public void onPrepared(MediaPlayer mp) {
                                seekBar.setMax(mp.getDuration());

                                mp.start();
//                                Log.e("total duration", "onItemClick:" +mediaPlayer.getDuration()+" the current duration"+mediaPlayer.getCurrentPosition() );

                                totalDuration.setText(timer(mp.getDuration()));
                                changeSeekbar();
                            }
                        });
                        b.setText("Stop");

                        /////////////////////////////
                        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                            @Override
                            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                                if (fromUser){
                                    mediaPlayer.seekTo(progress);
                                    changeSeekbar();
                                }
                            }

                            @Override
                            public void onStartTrackingTouch(SeekBar seekBar) {
//                                mediaPlayer.stop();
                            }

                            @Override
                            public void onStopTrackingTouch(SeekBar seekBar) {
//                                        mediaPlayer.seekTo(getSeekToProgress());
//                                mediaPlayer.start();
                            }
                        });

                    }catch (Exception e){}
                }
            }
        });
        checkUserPermission();

    }

    private void changeSeekbar() {
        if (mediaPlayer!=null){
        seekBar.setProgress(mediaPlayer.getCurrentPosition());
        currentDuration.setText(timer(mediaPlayer.getCurrentPosition()));
//            Log.e("total duration", "onItemClick: duration" + timer(mediaPlayer.getDuration())+" the current duration"+timer(mediaPlayer.getCurrentPosition()) );

            if (mediaPlayer.isPlaying()){
            runnabled = new Runnable() {
                @Override
                public void run() {
                    changeSeekbar();
                }
            };
            myHandler.postDelayed(runnabled,1000);
        }
    }
    }

    private void checkUserPermission(){
        if(Build.VERSION.SDK_INT>=23){
            if(ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED){
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},123);
                return;
            }
            if(ActivityCompat.checkSelfPermission(this, Manifest.permission.WAKE_LOCK)
                    != PackageManager.PERMISSION_GRANTED){
                requestPermissions(new String[]{Manifest.permission.WAKE_LOCK},12);
                return;
            }
        }
        loadSongs();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case 123:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    loadSongs();
                }else{
                    Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
                    checkUserPermission();
                }
                break;
            case 12:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    loadSongs();
                }else{
                    Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
                    checkUserPermission();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        }

    }

    private void loadSongs(){
        Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        String selection = MediaStore.Audio.Media.IS_MUSIC+"!=0";
        Cursor cursor = getContentResolver().query(uri,null,selection,null,null);
        if(cursor != null){
            if(cursor.moveToFirst()){
                do{
                    String name = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DISPLAY_NAME));
                    String artist = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST));
                    String url = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA));

                    SongInfo s = new SongInfo(name,artist,url);
                    _songs.add(s);

                }while (cursor.moveToNext());
            }

            cursor.close();
            songAdapter = new SongAdapter(MainActivity.this,_songs);

        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.play_pause_btn:{
                if (mediaPlayer.isPlaying()){
                    play_pause_btn.setImageDrawable(getResources().getDrawable(R.drawable.ic_pause));
                    mediaPlayer.pause();
                    currentDuration.setText(timer(mediaPlayer.getCurrentPosition()));

                }
                else{
                    play_pause_btn.setImageDrawable(getResources().getDrawable(R.drawable.ic_play_arrow));
                    mediaPlayer.start();
                    changeSeekbar();

                }
                break;
            }
//                case R.id.play_prev:{
//                    if (mediaPlayer.isPlaying()){
////                        play_pause_btn.setImageDrawable(getResources().getDrawable(R.drawable.ic_pause));
//                        mediaPlayer.stop();
//                        mediaPlayer.release();
//                        mediaPlayer = null;
//                    }
//                    try {
//                        SongInfo songInfo = new SongInfo();
//                        String path = songInfo.getSongUrl();
//                        mediaPlayer.setDataSource( songAdapter.prev_or_next("prev").getSongUrl());
//
//                    }catch (Exception e){
//                        e.printStackTrace();
//                    }
//                        play_pause_btn.setImageDrawable(getResources().getDrawable(R.drawable.ic_play_arrow));
//                        mediaPlayer.start();
////                        changeSeekbar();
//
//                    break;
//                }
//                case R.id.play_next:{
//                    int position = mediaPlayer.getCurrentPosition()+1;
//                    if (position>_songs.size()-1){
//                        position = 0;
//                    }
//                    Log.e("when play next", "onClick: error "+position );
//                    seeker(_songs.get(position).getSongUrl());
//
//                    break;
//                }
        }
    }

    public String timer(int milliSec){
        String finalTimerString = "";
        String secondString = "";
        int hours = (int) (milliSec/(1000*60*60));
        int minutes = (int) (milliSec%(1000*60*60))/(1000*60);
        int seconds = (int) ((milliSec%(1000*60*60))%(1000*60)/1000);

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

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


//    public void seeker(String resource){
//        try{
//            if (mediaPlayer.isPlaying()){
////                        play_pause_btn.setImageDrawable(getResources().getDrawable(R.drawable.ic_pause));
//                mediaPlayer.stop();
//                mediaPlayer.release();
//                mediaPlayer = null;
//            }
//        mediaPlayer = new MediaPlayer();
//        mediaPlayer.setDataSource(resource);
//        mediaPlayer.prepareAsync();
//        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
//            @Override
//            public void onPrepared(MediaPlayer mp) {
//                seekBar.setMax(mp.getDuration());
//                mp.start();
//                play_pause_btn.setImageDrawable(getResources().getDrawable(R.drawable.ic_play_arrow));
//                changeSeekbar();
//            }
//        });
//    }catch (Exception e){
//            e.printStackTrace();
//        }}
}


//
//
//                    Runnable runnable = new Runnable() {
//                        @Override
//                        public void run() {
//                            try {
//                                mediaPlayer = new MediaPlayer();
//                                mediaPlayer.setDataSource(obj.getSongUrl());
//                                mediaPlayer.prepareAsync();
//                                mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
//                                    @Override
//                                    public void onPrepared(MediaPlayer mp) {
//                                        mp.start();
////                                        seekBar.setProgress(0);
////                                        seekBar.setMax(mediaPlayer.getDuration());
////                                        Log.d("Prog", "run: " + mediaPlayer.getDuration());
////                                        Thread thread2 =new thread2();
////                                        thread2.start();
//                                        changeSeekbar();
////
//                                    }
//                                });
//                                b.setText("Stop");
//
//
//                                /////////////////////////////
//
//
//                                ///////////////////////
//
//
//
//                            }catch (Exception e){}
//                        }
//                    };
//                    myHandler.postDelayed(runnable,100);




//
//    public class runThread extends Thread {
//
//
//        @Override
//        public void run() {
//            while (true) {
//
//
//
//                try {
//                    Thread.sleep(1000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//                Log.d("Runwa", "run: " + 1);
//                if (mediaPlayer != null) {
//                    seekBar.post(new Runnable() {
//                        @Override
//                        public void run() {

//                            seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
//                                @Override
//                                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
//                                    mediaPlayer.seekTo(progress);
//                                }
//
//                                @Override
//                                public void onStartTrackingTouch(SeekBar seekBar) {
//
//                                }
//
//                                @Override
//                                public void onStopTrackingTouch(SeekBar seekBar) {
//
//                                }
//                            });
//
//
//                            seekBar.setProgress(mediaPlayer.getCurrentPosition());
////                            mediaPlayer.setOnSeekCompleteListener(new MediaPlayer.OnSeekCompleteListener() {
////                                @Override
////                                public void onSeekComplete(MediaPlayer mp) {
////                                    seekBar.set
////                                }
////                            });
//
//                        }
//                    });
//
//                    Log.d("Runwa", "run: " + mediaPlayer.getCurrentPosition());
//                }
//            }
//        }
//
//    }
//    public  class thread2 extends  Thread{
//        @Override
//        public void run() {
//
//            seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
//                    @Override
//                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
////                        mediaPlayer.seekTo(progress);
//                    }
//
//                    @Override
//                    public void onStartTrackingTouch(SeekBar seekBar) {
//                        mediaPlayer.stop();
//
//                    }
//
//                    @Override
//                    public void onStopTrackingTouch(SeekBar seekBar) {
//                        mediaPlayer.seekTo(getSeekToProgress());
//                        mediaPlayer.start();
//
//                    }
//                });
//        }
//    }