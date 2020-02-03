package com.example.mediaplayer;


import android.Manifest;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class Lyrics_viewFragment extends Fragment implements OnBackPressed {

    private static final int REQUEST_ID_MULTIPLE_PERMISSIONS = 11;
    private ArrayList<SongInfo> _songs = new ArrayList<>();;
    RecyclerView recyclerView;
    SeekBar seekBar;
    TextView currentDuration, totalDuration,lyrics_textView;
    AdapterForSongList songAdapter;
    ImageButton play_pause_btn;
    static MediaPlayer mediaPlayer;
    private Handler myHandler = new Handler();
    private View rootView;
    private List<SingerModel> songs_model_list;
    int  position;
    private int lyrics_position;
    private String songData;
    boolean isInFav;
    boolean comeFromFav ;
    ScrollView lyricsScrollView;

    public Lyrics_viewFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        checkUserPermission();
        songs_model_list= new ArrayList<>();
        songs_model_list = loadSongsList();
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.choose_song:{
            final FragmentManager fragmentManager =getActivity().getSupportFragmentManager();
            final DialogFragment chooseSongFragment = new ChooseSongFragment();
            chooseSongFragment.setTargetFragment(Lyrics_viewFragment.this,1);
            String data = lyrics_textView.getText().toString();
            Bundle bundle = new Bundle();
            bundle.putString("fromLyricsData",data);
            bundle.putString("fromSongData",songData);
            chooseSongFragment.setArguments(bundle);
//            getActivity().getSupportFragmentManager().beginTransaction().add(chooseSongFragment,"choose_song_tag").commit();
    //            chooseSongFragment.setStyle(DialogFragment.STYLE_NORMAL, R.style.Dialog_FullScreen);
            chooseSongFragment.show(fragmentManager,"choose_song_tag");
            break;
            }
            case R.id.is_fav:{
                Toast.makeText(getActivity(), "fav is clicked", Toast.LENGTH_SHORT).show();
                DatabaseAccess db = DatabaseAccess.getInstance(getContext());

                if (isInFav){
                    db.deleteFav(songData);
                    Toast.makeText(getActivity(), songData+" is in Fav", Toast.LENGTH_SHORT).show();
                    item.setIcon(R.drawable.ic_star_border);
                    isInFav=false;

                }else {
                    db.insertIntoFav(songData);
                    Toast.makeText(getActivity(), songData+" is Not in Fav", Toast.LENGTH_SHORT).show();
                    item.setIcon(R.drawable.ic_star_black);
                    isInFav=true;


                }
            }
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.lyrics_options,menu);
        boolean isfav = DatabaseAccess.getInstance(getActivity()).checkIsFav(songData);
        Toast.makeText(getActivity(), "is fav"+isfav+"  "+songData, Toast.LENGTH_LONG).show();
        if (isfav){

            menu.findItem(R.id.is_fav).setIcon(R.drawable.ic_star_black);
            isInFav = true;
        }else {
            menu.findItem(R.id.is_fav).setIcon(R.drawable.ic_star_border);
            isInFav = false;

        }
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (checkUserPermission()){


        // Inflate the layout for this fragment
        getActivity().setTitle("ግጥም(Lyrics)");
        rootView = inflater.inflate(R.layout.fragment_lyrics_view, container, false);
        songAdapter = new AdapterForSongList(getActivity(),songs_model_list);
        lyrics_textView= rootView.findViewById(R.id.lyrics_textView);
        String data = getArguments().getString("toLyricsData","");
        if (data.isEmpty()){
            lyrics_textView.setText(fetchLyrics());

        }else {
            lyrics_textView.setText(data);
            songData = getArguments().getString("toSongData");
        }


        currentDuration = rootView.findViewById(R.id.current_duration);
        totalDuration = rootView.findViewById(R.id.total_duration);
        seekBar = rootView.findViewById(R.id.seekBar);
        play_pause_btn = rootView.findViewById(R.id.play_pause_btn);
        lyricsScrollView = rootView.findViewById(R.id.lyricsScrollView);
        play_pause_btn.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {
            switch (v.getId()){
                case R.id.play_pause_btn:{
                    if (mediaPlayer.isPlaying()){
                        Drawable pause = getResources().getDrawable(R.drawable.ic_round_pause_btn);
                        pause.setTint(R.attr.themePrimary);
                        play_pause_btn.setBackground(pause);
                        play_pause_btn.setScaleType(ImageView.ScaleType.FIT_XY);

//                        play_pause_btn.setImageDrawable(getResources().getDrawable(R.drawable.ic_pause_button));
//                        play_pause_btn.getDrawable().setTint(getResources().getColor(R.color.colorTheme6Primary));
//                        play_pause_btn.getBackground().setTintList(ColorStateList.valueOf(getResources().getColor(R.attr.themePrimary)));
                        mediaPlayer.pause();
                        currentDuration.setText(timer(mediaPlayer.getCurrentPosition()));
                    }
                    else{
                        play_pause_btn.setBackground(null);
                        Drawable pause = getResources().getDrawable(R.drawable.ic_play_button);
                        pause.setTint(R.attr.themePrimary);
                        play_pause_btn.setBackground(pause);
//                        play_pause_btn.setImageDrawable(getResources().getDrawable(R.drawable.ic_play_button));
//                        play_pause_btn.getDrawable().setTintList(getResources().getColor(R.attr.themePrimary));
//                        play_pause_btn.getba(ColorStateList.valueOf(R.attr.themePrimary))
                        mediaPlayer.start();
                        changeSeekbar();
                    }
                    break;
                }
                }
            }
        });

            Fragment prev = getActivity().getSupportFragmentManager().findFragmentByTag("choose_song_tag");
            if (prev != null) {
                DialogFragment df = (DialogFragment) prev;
                df.dismiss();
            }

            position = getArguments().getInt("position");
        Log.e("position","onCreateView position is: "+position );
        Log.e("position","onCreateView position is: "+(mediaPlayer==null) );

                if (mediaPlayer != null){
                    mediaPlayer.getCurrentPosition();
                    mediaPlayer.stop();
                    mediaPlayer.reset();
                    mediaPlayer.release();
                    mediaPlayer = null;
                    }
                    try {
                        mediaPlayer = new MediaPlayer();
                        Log.e("position","the onCreateView position is: "+(mediaPlayer==null) );

                        mediaPlayer.setDataSource(songs_model_list.get(position).getSongUrl());
                        mediaPlayer.prepareAsync();
                        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                            @Override
                            public void onPrepared(MediaPlayer mp) {
                                seekBar.setMax(mp.getDuration());
                                mp.start();
                                totalDuration.setText(timer(mp.getDuration()));
                                changeSeekbar();
                            }
                        });

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

                    }catch (Exception e){
                        checkUserPermission();}


        }
        return rootView;

    }

    private void changeSeekbar() {
        if (mediaPlayer!=null){
//            int am = lyricsScrollView.getMaxScrollAmount();
            scrollLyricsView(mediaPlayer.getCurrentPosition(),mediaPlayer.getDuration());
//                }
//            }
//            if (mediaPlayer.getDuration()/4<mediaPlayer.getCurrentPosition()){
//                lyricsScrollView.smoothScrollTo(0,am/4);
//            }else if (mediaPlayer.getDuration()/3<mediaPlayer.getCurrentPosition()){
//                lyricsScrollView.smoothScrollTo(0,am/3);
//
//            }
//            else if (mediaPlayer.getDuration()/2<mediaPlayer.getCurrentPosition()){
//                lyricsScrollView.smoothScrollTo(0,am/2);
//
//            }
//            else if (mediaPlayer.getDuration()<mediaPlayer.getCurrentPosition()){
//                lyricsScrollView.smoothScrollTo(0,am);
//
//            }
            seekBar.setProgress(mediaPlayer.getCurrentPosition());
            currentDuration.setText(timer(mediaPlayer.getCurrentPosition()));
//            Log.e("total duration", "onItemClick: duration" + timer(mediaPlayer.getDuration())+" the current duration"+timer(mediaPlayer.getCurrentPosition()) );

            if (mediaPlayer.isPlaying()){
                Runnable runnabled = new Runnable() {
                    @Override
                    public void run() {
                        changeSeekbar();
                    }
                };
                myHandler.postDelayed(runnabled,1000);
            }
        }
    }

    private boolean checkUserPermission(){
        if(Build.VERSION.SDK_INT>=23){
            if(ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED){
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},123);
                return false;
            }
            if(ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.WAKE_LOCK)
                    != PackageManager.PERMISSION_GRANTED){
                requestPermissions(new String[]{Manifest.permission.WAKE_LOCK},12);
                return false;
            }
        }
        if (ContextCompat.checkSelfPermission(getActivity(),Manifest.permission.CALL_PHONE)!= PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CALL_PHONE,}, 121);
        }

        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case 123:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    final FragmentManager fragmentManager =getActivity().getSupportFragmentManager();
                    final ChooseSongFragment chooseSongFragment = new ChooseSongFragment();
                    //            chooseSongFragment.setStyle(DialogFragment.STYLE_NORMAL, R.style.Dialog_FullScreen);
                    chooseSongFragment.show(fragmentManager,"choose_song_tag");
                }else{
                    Toast.makeText(getActivity(), "Permission Denied", Toast.LENGTH_SHORT).show();
                    checkUserPermission();
                }
                break;
            case 12:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    final FragmentManager fragmentManager =getActivity().getSupportFragmentManager();
                    final ChooseSongFragment chooseSongFragment = new ChooseSongFragment();
                    //            chooseSongFragment.setStyle(DialogFragment.STYLE_NORMAL, R.style.Dialog_FullScreen);
                    chooseSongFragment.show(fragmentManager,"choose_song_tag");
                }else{
                    Toast.makeText(getActivity(), "Permission Denied", Toast.LENGTH_SHORT).show();
                    checkUserPermission();
                }
                break;
            case 121:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    final FragmentManager fragmentManager =getActivity().getSupportFragmentManager();
                    final ChooseSongFragment chooseSongFragment = new ChooseSongFragment();
                    //            chooseSongFragment.setStyle(DialogFragment.STYLE_NORMAL, R.style.Dialog_FullScreen);
                    chooseSongFragment.show(fragmentManager,"choose_song_tag");
                }else{
                    Toast.makeText(getActivity(), "Permission Denied", Toast.LENGTH_SHORT).show();
                    checkUserPermission();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        }

    }

//    public void loadSongs() {
//        Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
//        String selection = MediaStore.Audio.Media.IS_MUSIC + "!=0";
//        Cursor cursor = getActivity().getContentResolver().query(uri, null, selection, null, null);
//        if (cursor != null) {
//            if (cursor.moveToFirst()) {
//                do {
//                    String name = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DISPLAY_NAME));
//                    String artist = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST));
//                    String url = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA));
//
//                    SongInfo s = new SongInfo(name, artist, url);
//                    _songs.add(s);
//
//                } while (cursor.moveToNext());
//            }
//
//            cursor.close();
//            songAdapter = new AdapterForSongList(getActivity(), loadSongsList());
//
//        }
//    }


    public String fetchLyrics(){
        String lyricsData = "";
        lyrics_position = getArguments().getInt("lyrics_position", -1);
        String  song_title = getArguments().getString("fav_name","");
        if (song_title.equals("")&& !comeFromFav){
            lyrics_position = getArguments().getInt("lyrics_position", 0);
            song_title = getArguments().getString("song_name", "");
//            DatabaseAccess db = DatabaseAccess.getInstance(getContext());
            List<List<String>> getData = SongsListFragment.getData;
//            lyricsData = getData.get(lyrics_position).get(1);
//            songData = getData.get(lyrics_position).get(0);
            for (List<String> singleOne: getData){
                Log.e("the list ", "fetchLyrics: alllist is "+singleOne+" "+singleOne.get(0) );
                if (singleOne.get(0).equals(song_title)){
                    lyricsData = singleOne.get(1);
                    songData = singleOne.get(0);
                    break;
                }
                else {
                    lyricsData = getData.get(lyrics_position).get(1);
                    songData = getData.get(lyrics_position).get(0);
                }
            }
            comeFromFav = false;
        }else {
            lyrics_position = getArguments().getInt("fav_position", 0);
            song_title = getArguments().getString("song_name", "");
//            DatabaseAccess db = DatabaseAccess.getInstance(getContext());
            List<List<String>> getData = FavListFragment.getData;
            for (List<String> singleOne: getData){
                if (singleOne.get(1).equals(song_title)){
                    lyricsData = singleOne.get(2);
                    songData = singleOne.get(0);
                    break;
                }
                else {
                    lyricsData = getData.get(lyrics_position).get(2);
                    songData = getData.get(lyrics_position).get(0);
                }
            }
            comeFromFav = true;
            setHasOptionsMenu(false);
        }
        return lyricsData;
    }

    public void scrollLyricsView(int current,int total){
        int am = lyricsScrollView.getHeight();
        int dif = total - current;
        double div =(double) dif/(double) total;
        int difTimeam =(int)((div)*am);
        int ratio =(am-difTimeam);
//        Log.e("check Scroll", dif+" is dif "+ div+" is Div  difTimeam is "+difTimeam+" changeSeekbar: the am = "+dif+" ratio = "+ratio+" \n"+mediaPlayer.getDuration()+" "+mediaPlayer.getCurrentPosition() );
//            lyricsScrollView.smoothScrollTo(0,mediaPlayer.getCurrentPosition());
//            for (int i = 10; i >= 1;i-- ){
//                if (mediaPlayer.getDuration()/i<mediaPlayer.getCurrentPosition()){
        if (ratio>200){
        lyricsScrollView.scrollTo(0,ratio-200);
    }}
    //    @Override
//    public void onClick(View v) {
//        switch (v.getId()){
//            case R.id.play_pause_btn:{
//                if (mediaPlayer.isPlaying()){
//                    play_pause_btn.setImageDrawable(getResources().getDrawable(R.drawable.ic_pause_button));
//                    mediaPlayer.pause();
//                    currentDuration.setText(timer(mediaPlayer.getCurrentPosition()));
//
//                }
//                else{
//                    play_pause_btn.setImageDrawable(getResources().getDrawable(R.drawable.ic_play_button));
//                    mediaPlayer.start();
//                    changeSeekbar();
//
//                }
//                break;
//            }
////                case R.id.play_prev:{
////                    if (mediaPlayer.isPlaying()){
//////                        play_pause_btn.setImageDrawable(getResources().getDrawable(R.drawable.ic_pause));
////                        mediaPlayer.stop();
////                        mediaPlayer.release();
////                        mediaPlayer = null;
////                    }
////                    try {
////                        SongInfo songInfo = new SongInfo();
////                        String path = songInfo.getSongUrl();
////                        mediaPlayer.setDataSource( songAdapter.prev_or_next("prev").getSongUrl());
////
////                    }catch (Exception e){
////                        e.printStackTrace();
////                    }
////                        play_pause_btn.setImageDrawable(getResources().getDrawable(R.drawable.ic_play_arrow));
////                        mediaPlayer.start();
//////                        changeSeekbar();
////
////                    break;
////                }
////                case R.id.play_next:{
////                    int position = mediaPlayer.getCurrentPosition()+1;
////                    if (position>_songs.size()-1){
////                        position = 0;
////                    }
////                    Log.e("when play next", "onClick: error "+position );
////                    seeker(_songs.get(position).getSongUrl());
////
////                    break;
////                }
//        }
//    }

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
    public List<SingerModel> loadSongsList() {
        songs_model_list = new ArrayList<>();
        Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        String selection = MediaStore.Audio.Media.IS_MUSIC + "!=0";
        Cursor cursor = getActivity().getContentResolver().query(uri, null, selection, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    String name = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DISPLAY_NAME));
                    String artist = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST));
                    String url = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA));

                    SingerModel s = new SingerModel(name, artist, url);
                    songs_model_list.add(s);

                } while (cursor.moveToNext());
            }

            cursor.close();
//            songAdapter = new AdapterForSongList(getActivity(), songs_model_list);

        }
        return songs_model_list;
    }


    @Override
    public boolean onBackPressed() {
        getActivity().getSupportFragmentManager().popBackStack();
        return true;
    }
}

