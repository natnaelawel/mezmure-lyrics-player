package com.example.mediaplayer;

import android.Manifest;
import android.app.Activity;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MySingerListAdapter extends RecyclerView.Adapter<MySingerListAdapter.MyViewHolder>{
    private static final int REQUEST_CALL = 12;
    Context ct;
    List<SingerModel> mSingersListData;
    List<SingerModel> mSingersCopyListData=new ArrayList<>();


    public MySingerListAdapter(Context ct, List<SingerModel> mSingersListData) {
        this.ct = ct;
        this.mSingersListData = mSingersListData;
        mSingersCopyListData.addAll(mSingersListData);
    }

//    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v;
        v= LayoutInflater.from(ct).inflate(R.layout.singer_list_item,viewGroup,false);
        return new MyViewHolder(v,ct);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder viewHolder, final int i) {
//        System.out.println("position is"+ viewPageAdapter.pos);
        final SingerModel singerModel = mSingersListData.get(i);

        viewHolder.engName.setText(singerModel.getEngName());
        viewHolder.amhName.setText(singerModel.getAmhName());
        viewHolder.singerPhoto.setImageResource(singerModel.getAlbumPhoto());

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                ViewContact(mContactData.get(i).getNumber());

                Fragment albumListFragment = new AlbumListFragment();
                Bundle bundle = new Bundle();
                bundle.putString("SingerName", singerModel.getEngName());
                albumListFragment.setArguments(bundle);
//                FragmentTransaction fragmentTransaction = FragmentManager.
                ((FragmentActivity)  v.getContext())
                        .getSupportFragmentManager()
                        .beginTransaction().replace(R.id.home_fragment_container,albumListFragment)
                        .addToBackStack(null)
                        .commit();

                Toast.makeText(ct, "the selected position "+i, Toast.LENGTH_SHORT).show();
            }
        });

    }


    @Override
    public int getItemCount() {
        return mSingersListData.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        ImageView singerPhoto;
        TextView engName, amhName;
        public MyViewHolder(@NonNull View itemView, final Context ct) {
            super(itemView);

                engName = itemView.findViewById(R.id.singerAmhName);
                amhName = itemView.findViewById(R.id.singerEngName);
                singerPhoto = itemView.findViewById(R.id.singerPhoto);
        }

    }

//    public void filter(String entryText){
//        List<SingerModel> mSingersCopyListData= mSingersListData;
//        mSingersListData.clear();
//        if (entryText.isEmpty()){
//            mSingersListData.addAll(mSingersCopyListData);
//
//        }else {
//            String loweredEntryText = entryText.toLowerCase();
//            for (SingerModel singerList :mSingersCopyListData){
//                if(singerList.getEngName().toLowerCase().contains(loweredEntryText)||singerList.getAmhName().toLowerCase().contains(loweredEntryText)){
//                    mSingersListData.add(singerList);
//                }
//            }
//        }
//        notifyDataSetChanged();
//    }
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



//    public void updateContactList(List<String> newList){
//
//        mSingersListData = new ArrayList<>();
//        for (String name:newList){
//            if (name!= null){
//                int i = newList.indexOf(name);
//                if ((i<names.size())){
//                    System.out.println("names"+names.size()+"i is"+i);
//
//                    mSingersListData.add(names.get(i));
//
//                    System.out.println("mlogdata is inside"+mSingersListData);
//                }
//                else {
//                    continue;
//                }}
//        }
//        System.out.println("mlogdata is"+mSingersListData);
//        notifyDataSetChanged();
//    }
//    public void NoupdateContactList(){
//        mSingersListData = new ArrayList<>();
//        mSingersListData = names;
//        notifyDataSetChanged();
//    }


}
