package com.example.mediaplayer;


import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.DefaultItemAnimator;
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
public class SingersFragment extends Fragment {
    View rootView;
    RecyclerView singer_list_recyclerView;
    private MySingerListAdapter MySingerListAdapter;
    private List<SingerModel> singer_model_list;
    private SearchView searchView;

    public SingersFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        getActivity().setTitle("መዘምራን(Singers)");
        rootView = inflater.inflate(R.layout.fragment_singers, container, false);
        singer_list_recyclerView = rootView.findViewById(R.id.singer_list_recyclerView);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        singer_list_recyclerView.setLayoutManager(layoutManager);
        MySingerListAdapter = new MySingerListAdapter(getContext(),singer_model_list);
        singer_list_recyclerView.setItemAnimator(new DefaultItemAnimator());
        singer_list_recyclerView.setAdapter(MySingerListAdapter);
        return rootView;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        singer_model_list = new ArrayList<>();
        DatabaseAccess db = DatabaseAccess.getInstance(getContext());
        List<List<String>> getData = db.getSingerList();

        for (List<String> data:getData)
        {
            singer_model_list.add(new SingerModel(data.get(0),data.get(1),R.drawable.hana_tekle));
//            singer_model_list.add(new SingerModel(data.get(1)));
        }
//
//        singer_model_list.add(new SingerModel("Hana Tekle ", "hana tekle", R.drawable.ic_singer_photo));
//        singer_model_list.add(new SingerModel("Hana Tekle ", "hana tekle", R.drawable.ic_singer_photo));
//        singer_model_list.add(new SingerModel("Hana Tekle ", "hana tekle", R.drawable.ic_singer_photo));
//        singer_model_list.add(new SingerModel("Hana Tekle ", "hana tekle", R.drawable.ic_singer_photo));
//        singer_model_list.add(new SingerModel("Hana Tekle ", "hana tekle", R.drawable.ic_singer_photo));
//        singer_model_list.add(new SingerModel("Hana Tekle ", "hana tekle", R.drawable.ic_singer_photo));
//        singer_model_list.add(new SingerModel("Hana Tekle ", "hana tekle", R.drawable.ic_singer_photo));
//        singer_model_list.add(new SingerModel("Hana Tekle ", "hana tekle", R.drawable.ic_singer_photo));
//        singer_model_list.add(new SingerModel("Hana Tekle ", "hana tekle", R.drawable.ic_singer_photo));

        super.onCreate(savedInstanceState);setHasOptionsMenu(true);
        setHasOptionsMenu(true);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.search_bar) {

            final FragmentManager fragmentManager =getActivity().getSupportFragmentManager();
            final ChooseSongFragment chooseSongFragment = new ChooseSongFragment();
            //            chooseSongFragment.setStyle(DialogFragment.STYLE_NORMAL, R.style.Dialog_FullScreen);
            chooseSongFragment.show(fragmentManager,"choose_song_tag");
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.home,menu);

        searchView = (SearchView) menu.findItem(R.id.search_bar)
                .getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {

                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                String userInput = s.trim();
                if (userInput!= ""){
                    List<String> newNameList = new ArrayList<>();
                    List<String> NameList = new ArrayList<>();
                    for (SingerModel model : singer_model_list) {
                        newNameList.add(model.getEngName());
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
                    MySingerListAdapter.updateList(NameList);
                }
                else{
                    MySingerListAdapter.NoupdateList();
                }
                return true;
            }
        });
    }
}
