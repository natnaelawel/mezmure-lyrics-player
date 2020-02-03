package com.example.mediaplayer;

import android.content.Context;
import android.media.MediaPlayer;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class AdapterForSongList extends RecyclerView.Adapter<AdapterForSongList.SongHolder> {

    private List<SingerModel> _songs = new ArrayList<>();
    private Context context;
    private OnItemClickListener mOnItemClickListener;
    public int pos;
    SingerModel s = null;
    ChooseSongFragment dialogFragment = new ChooseSongFragment();;
    public AdapterForSongList(Context context, List<SingerModel> songs) {
        this.context = context;
        this._songs = songs;
    }

    public interface OnItemClickListener {
        void onItemClick(ChooseSongFragment  chooseSongFragment , View view, SingerModel obj, int position);
    }

    public void setOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mOnItemClickListener = mItemClickListener;
    }


    @Override
    public SongHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View myView = LayoutInflater.from(context).inflate(R.layout.list_for_song_item,viewGroup,false);
        return new SongHolder(myView);
    }

    @Override
    public void onBindViewHolder(final SongHolder songHolder, final int i) {
        s = _songs.get(i);
        pos = i;
        songHolder.SongName.setText(_songs.get(i).getSongName());
        songHolder.SongArtist.setText(_songs.get(i).getSongArtistName());
        songHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(context, "the "+(mOnItemClickListener != null)+" clicked one is "+i, Toast.LENGTH_SHORT).show();

                if (mOnItemClickListener != null) {
                    MediaPlayer mediaPlayer = new MediaPlayer();
                    if (mediaPlayer.isPlaying()){
                        mediaPlayer.stop();
                        mediaPlayer.reset();
                        mediaPlayer.release();
                        mediaPlayer = null;

                    }
                    mOnItemClickListener.onItemClick(dialogFragment,v, s, i);

                }



            }
        });
    }

    @Override
    public int getItemCount() {
        return _songs.size();
    }

    public class SongHolder extends RecyclerView.ViewHolder {
        TextView SongName,SongArtist;
//        Button btnAction;
        public SongHolder(View itemView) {
            super(itemView);
            SongName =  itemView.findViewById(R.id.song_title);
            SongArtist = itemView.findViewById(R.id.song_artist_name);
//            btnAction =  itemView.findViewById(R.id.btnPlay);
        }
    }
//    public SongInfo prev_or_next(String action){
//        switch (action){
//            case "prev":{
//                if (pos <_songs.size()) {
//                    s = _songs.get(pos - 1);
//                }
//                break;
//            }
//            case "next":{
//                if (pos >= 1) {
//                    s = _songs.get(pos - 1);
//                }
//                break;
//            }
//            default:{
//                s = _songs.get(0);
//            }
//        }
//        return s;
//    }
}
