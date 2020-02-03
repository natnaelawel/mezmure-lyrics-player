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
public class AlbumListFragment extends Fragment {
    View rootView;
    RecyclerView album_list_recyclerView;
    private MySongListAdapter mySongListAdapter;
    private List<SingerModel> songs_model_list;
    public static String SingerName;
    private SearchView searchView;

    public AlbumListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        rootView = inflater.inflate(R.layout.fragment_album_list, container, false);
        getActivity().setTitle("አልበሞች(Albums)");
        album_list_recyclerView = rootView.findViewById(R.id.album_list_recyclerView);

        mySongListAdapter = new MySongListAdapter(getContext(),songs_model_list,0);
        album_list_recyclerView.setItemAnimator(new DefaultItemAnimator());
        album_list_recyclerView.setAdapter(mySongListAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(album_list_recyclerView.getContext(),
                linearLayoutManager.getOrientation());
        album_list_recyclerView.addItemDecoration(dividerItemDecoration);
//        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        album_list_recyclerView.setLayoutManager(linearLayoutManager);
        return rootView;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        songs_model_list = new ArrayList<>();
        SingerName = getArguments().getString("SingerName");
        DatabaseAccess db = DatabaseAccess.getInstance(getContext());
        List<String> getData = db.getAlbumList(SingerName);
        int volumeCounter=1;
        for (String data:getData)
        {
            songs_model_list.add(new SingerModel("Album "+volumeCounter,data));
            volumeCounter++;
//            singer_model_list.add(new SingerModel(data.get(1)));
        }


//        songs_model_list.add(new SingerModel("Hana 1 "));
//        songs_model_list.add(new SingerModel("Hana 2 "));
//        songs_model_list.add(new SingerModel("Hana 3 "));
//        songs_model_list.add(new SingerModel("Hana 4 "));
//        songs_model_list.add(new SingerModel("Hana 5 "));
//        songs_model_list.add(new SingerModel("Hana 6 "));
//        songs_model_list.add(new SingerModel("Hana 7 "));
//        songs_model_list.add(new SingerModel("Hana 8 "));
//        songs_model_list.add(new SingerModel("Hana 9 "));

        super.onCreate(savedInstanceState);setHasOptionsMenu(true);
        setHasOptionsMenu(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.search_bar) {

//            final FragmentManager fragmentManager =getActivity().getSupportFragmentManager();
//            final ChooseSongFragment chooseSongFragment = new ChooseSongFragment();
//            //            chooseSongFragment.setStyle(DialogFragment.STYLE_NORMAL, R.style.Dialog_FullScreen);
//            chooseSongFragment.show(fragmentManager,"choose_song_tag");
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
//                MySingerListAdapter.filter(s);
//                MySingerListAdapter.notifyDataSetChanged();
                String userInput = s.trim();
                if (userInput!= ""){
                    List<SingerModel> newList = new ArrayList<>();
                    List<String> newNameList = new ArrayList<>();
                    List<String> NameList = new ArrayList<>();

                    for (SingerModel model : songs_model_list) {
                        newNameList.add(model.getEngName());
                    }
                    Log.e("thenewList", "onQueryTextChange: the new list is"+newNameList);

                    for (String name : newNameList) {
                        if (name!=null){
                            String   Newname = name.replace("(","")
                                    .replace(")","")
                                    .replace(" ","")
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
