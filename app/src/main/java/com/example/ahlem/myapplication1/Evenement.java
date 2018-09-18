package com.example.ahlem.myapplication1;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.MultiTransformation;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

import static com.bumptech.glide.request.RequestOptions.bitmapTransform;

public  class Evenement extends AppCompatActivity {
    ImageButton search, back;
    TextView nameprofile;
    FirebaseDatabase database;
    DatabaseReference myRef;
    ListView publications;
    ImageView image;
    ArrayList<Publication> pub= new ArrayList<>();
    ArrayAdapter<Publication> adapter;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.evenement);
        image = findViewById(R.id.profile_pic);
        search=findViewById(R.id.search1);
        back=findViewById(R.id.back);
        nameprofile=findViewById(R.id.namestade);
        publications = findViewById(R.id.pub);
        adapter = new PublicationAdapter(this, pub);
        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
       Intent intent = getIntent();
        final String id =intent.getStringExtra("pos");
       // final String photoUrl = intent.getStringExtra("photoUser");

        //final String  userID = intent.getStringExtra("userID");
       // final  String id1 = MainActivity.customers.get(position).getId();
        //nameprofile.setText(MainActivity.customers.get(position).getFirstName());
        MultiTransformation multi = new MultiTransformation(
                new RoundedCornersTransformation(128, 0, RoundedCornersTransformation.CornerType.BOTTOM));
        Glide.with(this).load(FirstActivity.user.get_filephoto())
                .apply(bitmapTransform(multi))
                .into(image);
        nameprofile.setText(StadeProfile.profile);
        database = FirebaseDatabase.getInstance();
        myRef=database.getReference("users");
        myRef.child(id).child("publication").addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                pub.clear();
              if(dataSnapshot.exists()) {

                  for (DataSnapshot data : dataSnapshot.getChildren()) {
                      pub.add( data.getValue(Publication.class));
                      }

                  Collections.reverse(pub);
              }

          publications.setAdapter(adapter);

              }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(getBaseContext(), StadeProfile.class);
                i.putExtra("pos", id);
               // i.putExtra("photoUser", photoUrl);
                //i.putExtra("userID", userID);
                startActivity(i);
                finish();
            }
        });
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getBaseContext(),MainActivity.class);

                //i.putExtra("photoUser", photoUrl);
                //i.putExtra("userID", userID);
                startActivity(i);
                finish();
            }
        });

    }

}
