package com.example.mediaplayer;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class FavListFragment extends Fragment {
    View rootView;
    RecyclerView fav_list_recyclerView;
    private MySongListAdapter mySongListAdapter;
    private List<SingerModel> songs_model_list;
    public static List<List<String>> getData;

    public FavListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_fav_list, container, false);
        fav_list_recyclerView = rootView.findViewById(R.id.fav_list_recyclerView);
        getActivity().setTitle("ጥቅሶች(Favourites)");

        mySongListAdapter = new MySongListAdapter(getContext(),songs_model_list,4);
        fav_list_recyclerView.setItemAnimator(new DefaultItemAnimator());
        fav_list_recyclerView.setAdapter(mySongListAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(fav_list_recyclerView.getContext(),
                linearLayoutManager.getOrientation());
        fav_list_recyclerView.addItemDecoration(dividerItemDecoration);
//        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        fav_list_recyclerView.setLayoutManager(linearLayoutManager);
        return rootView;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        songs_model_list = new ArrayList<>();
        DatabaseAccess db = DatabaseAccess.getInstance(getContext());
        getData = db.getAllFavList();
        int volumeCounter=1;
        for (List<String> data:getData)
        {
//            List<String> singleData = data.get(0);
            songs_model_list.add(new SingerModel(" "+volumeCounter+" "+data.get(0)+" "+data.get(3),data.get(1)));
            volumeCounter++;
//            singer_model_list.add(new SingerModel(data.get(1)));
        }

        super.onCreate(savedInstanceState);
    }
}
