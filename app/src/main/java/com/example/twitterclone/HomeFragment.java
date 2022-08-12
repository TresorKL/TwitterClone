package com.example.twitterclone;

import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.annotation.NonNull;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.twitterclone.Fleet.Fleet;
import com.example.twitterclone.adapters.FleetAdapter;
import com.example.twitterclone.adapters.TrendAdapter;
import com.example.twitterclone.processor.Processor;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
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
    CircleImageView addStoryBtn;
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
        Drawable image3 = getResources().getDrawable(R.drawable.ronaldo);
        Drawable image4 = getResources().getDrawable(R.drawable.ktl);
        List<Drawable> fleeImages = new ArrayList<>();
        fleeImages.add(image);
        fleeImages.add(image2);
        fleeImages.add(image3);
        fleeImages.add(image4);

        Fleet fleetOne = new Fleet();
        fleetOne.setUserProfile(image2);
        fleetOne.setUserName("TresorKL");
        fleetOne.setFleetImages(fleeImages);


        Fleet fleetTwo = new Fleet();
        fleetTwo.setUserProfile(image);
        fleetTwo.setUserName("Enock");
        List<Drawable> fleeImages2 = new ArrayList<>();
        fleeImages2.add(image2);
        fleeImages2.add(image3);
        fleetTwo.setFleetImages(fleeImages2);


        Fleet fleetThree = new Fleet();
        fleetThree.setUserProfile(image4);
        fleetThree.setUserName("David");
        List<Drawable> fleeImages3 = new ArrayList<>();
        fleeImages3.add(image4);
        fleetThree.setFleetImages(fleeImages3);


        Fleet fleetFour = new Fleet();
        fleetFour.setUserProfile(image3);
        fleetFour.setUserName("Norbert");
        List<Drawable> fleeImages4 = new ArrayList<>();
        fleeImages4.add(image2);
        fleeImages4.add(image3);
        fleeImages4.add(image4);
        fleetFour.setFleetImages(fleeImages4);

        List<Fleet> fleets = new ArrayList<>();
        fleets.add(fleetOne);
        fleets.add(fleetTwo);
        fleets.add(fleetFour);
        fleets.add(fleetThree);


        RecyclerView myRecyclerView = (RecyclerView) view.findViewById(R.id.fleetRecycler);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);


        FleetAdapter adapter = new FleetAdapter(fleets, getContext(), getActivity().getWindowManager());
        myRecyclerView.setLayoutManager(layoutManager);

        myRecyclerView.setAdapter(adapter);


        // AddStoryRESULT_LOAD_IMG
        addStoryBtn = view.findViewById(R.id.AddStory);
        addStoryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, 2);


            }
        });

        return view;
    }

    @Override
    public void onActivityResult(int reqCode, int resultCode, Intent data) {
        super.onActivityResult(reqCode, resultCode, data);


        if (resultCode == RESULT_OK) {
            try {
                final Uri imageUri = data.getData();
                final InputStream imageStream = getContext().getContentResolver().openInputStream(imageUri);
                final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);


                ///-------------------------------------------------------
                ///---------------EXTRACT IMAGE NAME -------------------
                ///-------------------------------------------------------
                String[] filePathColumn = {MediaStore.Images.Media.DATA};
                Cursor cursor = getContext().getContentResolver().query(imageUri,
                        filePathColumn, null, null, null);
                cursor.moveToFirst();
                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                String picturePath = cursor.getString(columnIndex);
                cursor.close();
                File f = new File(picturePath);
                String imageName = f.getName();

                Processor processor = new Processor(getContext());
                processor.uploadImage(imageName, imageUri);
                // store image


                Toast.makeText(getContext(), "Successfully added: " + imageName, Toast.LENGTH_LONG).show();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                Toast.makeText(getContext(), "Something went wrong", Toast.LENGTH_LONG).show();
            } catch (IOException e) {
                e.printStackTrace();
            }

        } else {
            Toast.makeText(getContext(), "You haven't picked Image", Toast.LENGTH_LONG).show();
        }
    }


}