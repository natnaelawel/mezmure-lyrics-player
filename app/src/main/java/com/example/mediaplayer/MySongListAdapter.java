package com.example.mediaplayer;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;

public class MySongListAdapter extends RecyclerView.Adapter<MySongListAdapter.MyViewHolder>{
    private static final int REQUEST_CALL = 12;
    Context ct;
    List<SingerModel> mSingersListData;
    int position;
    private List<SingerModel> mSingersCopyListData= new ArrayList<>();
//    private int position;
//    List<SingerModel> contactListFiltered;
//    List<SingerModel> names;



    public MySongListAdapter(Context ct, List<SingerModel> mSingersListData,int position) {
        this.ct = ct;
        this.mSingersListData = mSingersListData;
        this.position = position;
        mSingersCopyListData.addAll(mSingersListData);
    }


//    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v;
        if (position ==3){
            v= LayoutInflater.from(ct).inflate(R.layout.quote_view,viewGroup,false);

        }else {
            v = LayoutInflater.from(ct).inflate(R.layout.song_item_view, viewGroup, false);
        }
        return new MyViewHolder(v,ct);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder viewHolder, final int i) {
//        System.out.println("position is"+ viewPageAdapter.pos);
        final SingerModel singerModel = mSingersListData.get(i);
        viewHolder.song_title.setText(singerModel.getSongTitle());
        if (position == 0){
            viewHolder.albumVolume.setText(singerModel.getVolumeCounter());

        }
        if (position == 1){
            viewHolder.albumVolume.setText(singerModel.getVolumeCounter());

        }
        if (position == 2){
            viewHolder.albumVolume.setText(singerModel.getVolumeCounter());

        }
        if (position == 3){
            viewHolder.albumVolume.setText(singerModel.getVolumeCounter());

        }
        if (position == 4){
            viewHolder.albumVolume.setText(singerModel.getVolumeCounter());

        }
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                ViewContact(mContactData.get(i).getNumber());
                switch (position){
                    case 0:{
                        Fragment songsListFragment = new SongsListFragment();
                        Bundle bundle = new Bundle();
                        bundle.putString("albumName", singerModel.getSongTitle());
                        bundle.putString("singerName", AlbumListFragment.SingerName);
                        songsListFragment.setArguments(bundle);
//                FragmentTransaction fragmentTransaction = FragmentManager.
                        ((FragmentActivity)  v.getContext())
                                .getSupportFragmentManager()
                                .beginTransaction().replace(R.id.home_fragment_container,songsListFragment)
                                .addToBackStack(null)
                                .commit();

                        Toast.makeText(ct, "the selected position "+i, Toast.LENGTH_SHORT).show();
                    break;
                    }
                    case 1:{
                        Fragment lyrics_viewFragment = new Lyrics_viewFragment();
                        Bundle bundle = new Bundle();
                        bundle.putString("song_name", singerModel.getSongTitle());
                        bundle.putInt("lyrics_position", i);
                        lyrics_viewFragment.setArguments(bundle);
//                FragmentTransaction fragmentTransaction = FragmentManager.
                        ((FragmentActivity)  v.getContext())
                                .getSupportFragmentManager()
                                .beginTransaction().replace(R.id.home_fragment_container,lyrics_viewFragment)
                                .addToBackStack(null)
                                .commit();

                        Toast.makeText(ct, "the selected position "+i, Toast.LENGTH_SHORT).show();
                        break;
                    }
                    case 2:{
                        Fragment quoteDisplayFragment = new QuoteDisplayFragment();
                        Bundle bundle = new Bundle();
                        bundle.putString("QuoteCatagoryName", singerModel.getSongTitle());
                        quoteDisplayFragment.setArguments(bundle);
//                FragmentTransaction fragmentTransaction = FragmentManager.
                        ((FragmentActivity)  v.getContext())
                                .getSupportFragmentManager()
                                .beginTransaction().replace(R.id.home_fragment_container,quoteDisplayFragment)
                                .addToBackStack(null)
                                .commit();
                        Toast.makeText(ct, "the selected position "+i, Toast.LENGTH_SHORT).show();
                        break;
                    }
                    case 4:{
                        Fragment lyrics_viewFragment = new Lyrics_viewFragment();
                        Bundle bundle = new Bundle();
                        bundle.putString("song_name", singerModel.getSongTitle());
                        bundle.putString("fav_name", "check");
                        bundle.putInt("fav_position", i);
                        lyrics_viewFragment.setArguments(bundle);
//                FragmentTransaction fragmentTransaction = FragmentManager.
                        ((FragmentActivity)  v.getContext())
                                .getSupportFragmentManager()
                                .beginTransaction().replace(R.id.home_fragment_container,lyrics_viewFragment)
                                .addToBackStack(null)
                                .commit();
                        Toast.makeText(ct, "the selected position "+i, Toast.LENGTH_SHORT).show();
                        break;
                    }
                }
            }
        });
    }


    @Override
    public int getItemCount() {
        return mSingersListData.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView song_title,albumVolume;
        public MyViewHolder(@NonNull View itemView, final Context ct) {
            super(itemView);
            song_title = itemView.findViewById(R.id.song_title);
            if (position == 0){
                albumVolume = itemView.findViewById(R.id.song_artist_name);

            }
            if (position == 1){
                albumVolume = itemView.findViewById(R.id.song_artist_name);

            }
            if (position == 2){
                albumVolume = itemView.findViewById(R.id.song_artist_name);

            }
            if (position == 3){
                albumVolume = itemView.findViewById(R.id.song_artist_name);
            }
            if (position == 4){
                albumVolume = itemView.findViewById(R.id.song_artist_name);
            }
        }
    }

    public void updateList(List<String> newList){
        mSingersListData = new ArrayList<>();
        for (String name:newList){
            if (name!= null){
                int i = newList.indexOf(name);
                if ((i<mSingersCopyListData.size())){
                    mSingersListData.add(mSingersCopyListData.get(i));
                }
                else {
                    continue;
                }}
        }
        notifyDataSetChanged();
    }
    public void NoupdateList(){
        mSingersListData = new ArrayList<>();
        mSingersListData = mSingersCopyListData;
        notifyDataSetChanged();
    }
}
