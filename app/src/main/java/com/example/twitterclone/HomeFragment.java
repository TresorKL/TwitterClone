package com.example.twitterclone;

import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;

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

import com.example.twitterclone.fleet.Fleet;
import com.example.twitterclone.adapters.FleetAdapter;
import com.example.twitterclone.processor.Processor;
import com.google.android.material.navigation.NavigationView;

import java.io.File;
import java.io.FileNotFoundException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


public class HomeFragment extends Fragment {
    CircleImageView menu;
    SharedPreferences fleetsPref;
    RecyclerView.LayoutManager RecyclerViewLayoutManager;
    CircleImageView addStoryBtn;
    Fleet dynamicUser = new Fleet();

    List<Fleet> fleets = new ArrayList<>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        RecyclerView myRecyclerView = (RecyclerView) view.findViewById(R.id.fleetRecycler);
        List<Drawable> userImages = new ArrayList<>();
        fleets.clear();
        userImages.clear();
        DrawerLayout drawerLayout = view.findViewById(R.id.homeDrawer);
        menu = view.findViewById(R.id.user);

        //----------------------------------
        // Open sidebar
        //----------------------------------
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavigationView nav = view.findViewById(R.id.myMenu);

                drawerLayout.openDrawer(GravityCompat.START);

            }
        });


        Drawable userProfile = getResources().getDrawable(R.drawable.dynamic);


        Processor processor = new Processor(getContext());

        dynamicUser.setUserProfile(userProfile);
        dynamicUser.setUserName("Your Story");
        processor.retrieveImage(userImages);
        //userImages=mainP.retrieveFleets(fleetsPref);

        //Toast.makeText(getContext(),userImages.get(0).toString(),Toast.LENGTH_LONG).show();

        dynamicUser.setFleetImages(userImages);


        fleets = processor.getStaticFleets();
        fleets.add(0,dynamicUser);

        List<Fleet> validFleets = new ArrayList<>();
        for (int i = 0; i < fleets.size(); i++) {
            if (fleets.get(i).fleetImages != null) {
                //if(!fleets.get(i).fleetImages.isEmpty()) {
                    validFleets.add(fleets.get(i));
               // }
            }
        }



        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);


        FleetAdapter adapter = new FleetAdapter(validFleets, getContext(), getActivity().getWindowManager());
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

                // getActivity().finish();
                startActivity(getActivity().getIntent());


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
                // Drawable imageD = new BitmapDrawable(getContext().getResources(), selectedImage);

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
                ///-------------------------------------------------------
                ///---------------UPLOAD FLEET IMAGES  -------------------
                ///-------------------------------------------------------
                List<Drawable> userImages = new ArrayList<>();
                processor.uploadImage(imageName, imageUri);
                processor.retrieveImage(userImages);
                dynamicUser.setFleetImages(userImages);
                fleets.set(0,dynamicUser);


                List<Fleet> validFleets = new ArrayList<>();
                for (int i = 0; i < fleets.size(); i++) {
                    if (fleets.get(i).fleetImages != null) {
                        //if(!fleets.get(i).fleetImages.isEmpty()) {
                        validFleets.add(fleets.get(i));
                        // }
                    }
                }

                RecyclerView myRecyclerView = (RecyclerView)getView().findViewById(R.id.fleetRecycler);

                LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);


                FleetAdapter adapter = new FleetAdapter(validFleets, getContext(), getActivity().getWindowManager());
                myRecyclerView.setLayoutManager(layoutManager);

                myRecyclerView.setAdapter(adapter);



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