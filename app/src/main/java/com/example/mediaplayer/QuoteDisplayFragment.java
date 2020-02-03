package com.example.mediaplayer;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import static com.example.mediaplayer.AlbumListFragment.SingerName;


/**
 * A simple {@link Fragment} subclass.
 */
public class QuoteDisplayFragment extends Fragment {

    View rootView;
    RecyclerView quote_list_recyclerView;
    private MySongListAdapter mySongListAdapter;
    private List<SingerModel> songs_model_list;
    public static List<List<String>> getData;
    private String QuoteCatagoryName="";

    public QuoteDisplayFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_quote_display, container, false);
        quote_list_recyclerView = rootView.findViewById(R.id.quote_display_recyclerView);
        getActivity().setTitle(QuoteCatagoryName);
        mySongListAdapter = new MySongListAdapter(getContext(),songs_model_list,3);
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
        QuoteCatagoryName = getArguments().getString("QuoteCatagoryName");
        DatabaseAccess db = DatabaseAccess.getInstance(getContext());
        List<String> getData = db.getDisplayQuoteList(QuoteCatagoryName);
        int volumeCounter=1;
        for (String data:getData)
        {
            songs_model_list.add(new SingerModel(""+volumeCounter,data));
            volumeCounter++;
        }
        super.onCreate(savedInstanceState);
//        setHasOptionsMenu(true);
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
        super.onCreateOptionsMenu(menu, inflater);
    }
}
