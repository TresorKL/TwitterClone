package com.example.twitterclone;

import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.twitterclone.Fleet.Fleet;
import com.example.twitterclone.adapters.FleetAdapter;
import com.example.twitterclone.adapters.TrendAdapter;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {
    CircleImageView menu;
    RecyclerView.LayoutManager RecyclerViewLayoutManager;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HomeFragment() {
        // Required empty public constructor
    }


    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
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

        View view = inflater.inflate(R.layout.fragment_home, container, false);

        DrawerLayout drawerLayout = view.findViewById(R.id.homeDrawer);
        menu = view.findViewById(R.id.user);
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavigationView nav = view.findViewById(R.id.myMenu);

                drawerLayout.openDrawer(GravityCompat.START);

            }
        });

        Drawable image = getResources().getDrawable(R.drawable.story);
        Drawable image2 = getResources().getDrawable(R.drawable.trezor);

        Fleet fleetOne = new Fleet();
        fleetOne.setUserProfile(image2);
        fleetOne.setUserName("TresorKL");

        Fleet fleetTwo = new Fleet();
        fleetTwo.setUserProfile(image);
        fleetTwo.setUserName("Enock");

        Fleet fleetThree = new Fleet();
        fleetThree.setUserProfile(image);
        fleetThree.setUserName("David");


        Fleet fleetFour = new Fleet();
        fleetFour.setUserProfile(image2);
        fleetFour.setUserName("Norbert");


        List<Fleet> fleets = new ArrayList<>();
        fleets.add(fleetOne);
        fleets.add(fleetTwo);
        fleets.add(fleetTwo);
        fleets.add(fleetTwo);
        fleets.add(fleetThree);
        fleets.add(fleetFour);
        fleets.add(fleetThree);
        fleets.add(fleetFour);
        fleets.add(fleetThree);
        fleets.add(fleetFour);


        RecyclerView myRecyclerView = (RecyclerView) view.findViewById(R.id.fleetRecycler);
//        LinearLayoutManager horizontalLayoutManagaer = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
      //  layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
       //  RecyclerView myList = (RecyclerView) findViewById(R.id.my_recycler_view);


        FleetAdapter adapter = new FleetAdapter(fleets);
        myRecyclerView.setLayoutManager(layoutManager);
        //myRecyclerView.setHasFixedSize(true);
       // myRecyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        myRecyclerView.setAdapter(adapter);


        return view;
    }
}