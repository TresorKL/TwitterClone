package com.example.twitterclone;

import android.os.Bundle;

import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.twitterclone.Trend.Trend;
import com.example.twitterclone.adapters.TrendAdapter;
import com.google.android.material.navigation.NavigationView;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SearchFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SearchFragment extends Fragment {
    CircleImageView menu;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SearchFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SearchFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SearchFragment newInstance(String param1, String param2) {
        SearchFragment fragment = new SearchFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_search, container, false);

        Trend trendOne=new Trend("Trending", "Kanye", "29.6k Tweets");
        Trend trendTwo=new Trend("Sport Trending", "Ronaldo", "140k Tweets");
        Trend trendThree=new Trend("Sport Trending", "Kaizer Chiefs", "3.257 Tweets");
        Trend trendFour=new Trend("Politics Trending", "Gaza", "567k Tweets");
        Trend trendFive=new Trend("Trending in South Africa", "LinkedIn", "43.5k");
        Trend trendSix=new Trend("Trending in Football", "Ten Hag", "118k");
        Trend trendSeven=new Trend("Trending", "State House", "12.2k");

        Trend[] trends = {trendOne,trendSix,trendSeven,trendTwo,trendThree,trendFour,trendOne,trendTwo,trendFive,trendSix,trendSeven};
        RecyclerView myRecyclerView = view.findViewById(R.id.trendRecycler);

        TrendAdapter adapter = new TrendAdapter(trends);
        myRecyclerView.setHasFixedSize(true);
        myRecyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        myRecyclerView.setAdapter(adapter);


        DrawerLayout drawerLayout = view.findViewById(R.id.homeDrawer);
        menu = view.findViewById(R.id.user);
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavigationView nav = view.findViewById(R.id.myMenu);

                drawerLayout.openDrawer(GravityCompat.START);

            }
        });
        return view;
    }
}