package com.example.mediaplayer;


import android.database.Cursor;
import android.graphics.Point;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.SeekBar;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class ChooseSongFragment extends DialogFragment {
    View rootView;
    RecyclerView album_list_recyclerView;
    private AdapterForSongList mySongListAdapter;
    Lyrics_viewFragment lyrics_viewFragment = new Lyrics_viewFragment();
//    private SongAdapter mySongAdapter;
    private List<SingerModel> songs_model_list;
//    private List<SongInfo> songs_model_list;

    public ChooseSongFragment() {
        // Required empty public constructor
    }

    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public void onResume() {
        this.getDialog().setTitle("መዝሙሮች(Choose Song)");
        Window window = getDialog().getWindow();
        Point size = new Point();
        // Store dimensions of the screen in `size`
        Display display = window.getWindowManager().getDefaultDisplay();
        display.getSize(size);
        // Set the width of the dialog proportional to 75% of the screen width
        window.setLayout((int) (size.x), WindowManager.LayoutParams.WRAP_CONTENT);
        window.setGravity(Gravity.CENTER);
//         Call super onResume after sizing
        super.onResume();
//        ViewGroup.LayoutParams params = getDialog().getWindow().getAttributes();
//        // Assign window properties to fill the parent
//        params.width = WindowManager.LayoutParams.MATCH_PARENT;
//        params.height = WindowManager.LayoutParams.MATCH_PARENT;
//        getDialog().getWindow().setAttributes((android.view.WindowManager.LayoutParams) params);
        // Call super onResume after sizing
//        super.onResume();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        rootView = inflater.inflate(R.layout.fragment_choose_song, container, false);
        album_list_recyclerView = rootView.findViewById(R.id.choose_song_recyclerView);
        mySongListAdapter = new AdapterForSongList(getActivity(),songs_model_list);
//        mySongAdapter = new SongAdapter(getContext(),songs_model_list);
        album_list_recyclerView.setItemAnimator(new DefaultItemAnimator());
        album_list_recyclerView.setAdapter(mySongListAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(album_list_recyclerView.getContext(),
                linearLayoutManager.getOrientation());
        album_list_recyclerView.addItemDecoration(dividerItemDecoration);
//        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        album_list_recyclerView.setLayoutManager(linearLayoutManager);

        mySongListAdapter.setOnItemClickListener(new AdapterForSongList.OnItemClickListener() {
            @Override
            public void onItemClick(ChooseSongFragment chooseSongFragment, View view, SingerModel obj, int position) {
//                if(position<0){
//                    mediaPlayer.stop();
//                    mediaPlayer.reset();
//                    mediaPlayer.release();
//                    mediaPlayer = null;
////                    b.setText("Play");
//
//                }else {

                Toast.makeText(getActivity(), "the clicked one is "+position, Toast.LENGTH_SHORT).show();
//                new ChooseSongFragment().getDialog().dismiss();
//                if (mediaPlayer != null && mediaPlayer.isPlaying()){
//                    mediaPlayer.getCurrentPosition();
//                    mediaPlayer.stop();
//                    mediaPlayer.reset();
//                    mediaPlayer.release();
//                    mediaPlayer = null;
////                        b.setText("Play");
//                }
//                try {
//                    mediaPlayer = new MediaPlayer();
//                    mediaPlayer.setDataSource(obj.getSongUrl());
//                    mediaPlayer.prepareAsync();
//                    mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
//                        @Override
//                        public void onPrepared(MediaPlayer mp) {
//                            seekBar.setMax(mp.getDuration());
//                            mp.start();
////                                Log.e("total duration", "onItemClick:" +mediaPlayer.getDuration()+" the current duration"+mediaPlayer.getCurrentPosition() );
//                            totalDuration.setText(timer(mp.getDuration()));
//                            changeSeekbar();
//                        }
//                    });
////                        b.setText("Stop");
//
//                    /////////////////////////////
//                    seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
//                        @Override
//                        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
//                            if (fromUser){
//                                mediaPlayer.seekTo(progress);
//                                changeSeekbar();
//                            }
//                        }
//
//                        @Override
//                        public void onStartTrackingTouch(SeekBar seekBar) {
////                                mediaPlayer.stop();
//                        }
//
//                        @Override
//                        public void onStopTrackingTouch(SeekBar seekBar) {
////                                        mediaPlayer.seekTo(getSeekToProgress());
////                                mediaPlayer.start();
//                        }
//                    });
//
//                }catch (Exception e){}

                Bundle bundle = getArguments();
                String data1 = bundle.getString("fromLyricsData");
                String data2 = bundle.getString("fromSongData");
                Log.e("checkCOnnection", "onItemClick: get Argument "+data1 );
                Bundle bundle1 = new Bundle();
                bundle1.putString("toLyricsData",data1);
                bundle1.putString("toSongData",data2);
                bundle1.putInt("position",position);
                lyrics_viewFragment.setArguments(bundle1);

                ((FragmentActivity)  view.getContext())
                        .getSupportFragmentManager()
                        .beginTransaction().replace(R.id.home_fragment_container,lyrics_viewFragment)
//                        .addToBackStack(null)
                        .commit();

//                ((FragmentActivity)  view.getContext())
//                        .getSupportFragmentManager().findFragmentByTag("dialog")
//
//                        .commit();

//                FragmentTransaction ft =((FragmentActivity)  view.getContext()).getSupportFragmentManager().beginTransaction();
//                Fragment prev =((FragmentActivity) view.getContext()).getSupportFragmentManager().findFragmentByTag("choose_song_tag");
//                if (prev != null) {
//                    ft.remove(prev);
//                }
//                ft.addToBackStack(null);
//                chooseSongFragment.getDialog().dismiss();
            }



        });
        return rootView;

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
//        loadSongs();
        songs_model_list = new ArrayList<>();
        songs_model_list = loadSongs();
        songs_model_list.add(new SingerModel("music 1 "));

        super.onCreate(savedInstanceState);setHasOptionsMenu(true);
    }

    public List<SingerModel> loadSongs() {
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
//            songs_model_list = new SongAdapter(getActivity(), songs_model_list);

        }
        return songs_model_list;
    }
}
