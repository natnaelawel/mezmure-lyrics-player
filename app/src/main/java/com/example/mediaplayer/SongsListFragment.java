package com.example.mediaplayer;


import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class SongsListFragment extends Fragment {
    View rootView;
    RecyclerView song_list_recyclerView;
    private MySongListAdapter mySongListAdapter;
    private List<SingerModel> songs_model_list;
    public static List<List<String>> getData;
    private SearchView searchView;

    public SongsListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_songs_list, container, false);
        song_list_recyclerView = rootView.findViewById(R.id.song_list_recyclerView);
        getActivity().setTitle("መዝሙሮች(Songs)");

        mySongListAdapter = new MySongListAdapter(getContext(),songs_model_list,1);
        song_list_recyclerView.setItemAnimator(new DefaultItemAnimator());
        song_list_recyclerView.setAdapter(mySongListAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(song_list_recyclerView.getContext(),
                linearLayoutManager.getOrientation());
        song_list_recyclerView.addItemDecoration(dividerItemDecoration);
//        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        song_list_recyclerView.setLayoutManager(linearLayoutManager);
        return rootView;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        songs_model_list = new ArrayList<>();
        DatabaseAccess db = DatabaseAccess.getInstance(getContext());
        String albumName = getArguments().getString("albumName");
        String singerName = getArguments().getString("singerName");
        getData = db.getSongAndLyricsList(singerName,albumName);
        int volumeCounter=1;
        for (List<String> data:getData)
        {
            songs_model_list.add(new SingerModel(" "+volumeCounter,data.get(0)));
            volumeCounter++;
        }
        super.onCreate(savedInstanceState);setHasOptionsMenu(true);
        setHasOptionsMenu(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.search_bar) {

        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.home,menu);

//        SearchManager searchManager = (SearchManager)getActivity().getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.search_bar)
                .getActionView();
//        searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));
//        searchView.setMaxWidth(Integer.MAX_VALUE);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
//                MySingerListAdapter.filter(s);
//                MySingerListAdapter.notifyDataSetChanged();

                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                String userInput = s.trim();
                if (userInput!= ""){
                    List<String> newNameList = new ArrayList<>();
                    List<String> NameList = new ArrayList<>();
                    for (SingerModel model : songs_model_list) {
                        newNameList.add(model.getSongTitle());
                    }
                    for (String name : newNameList) {
                        if (name!=null){
                            String   Newname = name.replace("(","")
                                    .replace(")","")
                                    .toLowerCase();
                            if (Newname.contains(userInput)) {
                                NameList.add(Newname);
                            } else {
                                NameList.add(null);
                            }}
                    }
                    mySongListAdapter.updateList(NameList);
                }
                else{
                    mySongListAdapter.NoupdateList();
                }
                return true;
            }
        });
    }
}
