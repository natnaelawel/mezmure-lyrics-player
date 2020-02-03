package com.example.mediaplayer;


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
public class QuoteListFragment extends Fragment {
    View rootView;
    RecyclerView quote_list_recyclerView;
    private MySongListAdapter mySongListAdapter;
    private List<SingerModel> songs_model_list;
    public static List<List<String>> getData;

    public QuoteListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_quote_list, container, false);
        quote_list_recyclerView = rootView.findViewById(R.id.quote_list_recyclerView);
        getActivity().setTitle("ጥቅሶች(Verses)");

        mySongListAdapter = new MySongListAdapter(getContext(),songs_model_list,2);
        quote_list_recyclerView.setItemAnimator(new DefaultItemAnimator());
        quote_list_recyclerView.setAdapter(mySongListAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(quote_list_recyclerView.getContext(),
                linearLayoutManager.getOrientation());
        quote_list_recyclerView.addItemDecoration(dividerItemDecoration);
//        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        quote_list_recyclerView.setLayoutManager(linearLayoutManager);
        return rootView;

}


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        songs_model_list = new ArrayList<>();
        DatabaseAccess db = DatabaseAccess.getInstance(getContext());
        getData = db.getQuoteList();
        int volumeCounter=1;
        for (List<String> data:getData)
        {
            songs_model_list.add(new SingerModel(volumeCounter+", "+data.get(1),data.get(0)));
            volumeCounter++;
        }

        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.choose_song) {

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
        //        SearchManager searchManager = (SearchManager)getActivity().getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.search_bar)
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
                        newNameList.add(model.getVolumeCounter());
                    }

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
        super.onCreateOptionsMenu(menu, inflater);
    }
}
