package com.example.ahlem.myapplication1;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.CursorJoiner;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.MultiTransformation;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.security.SecureRandom;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Random;
import java.util.Vector;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

import static com.bumptech.glide.request.RequestOptions.bitmapTransform;


public class Calendrier extends AppCompatActivity  {
    private TextView mDisplayDate;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    String date;
    static  String id ;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("users");
   DatabaseReference myRef1 = database.getReference("simpleusers");
    Vector<Button> ids=new Vector<Button>();
    Calendar myCalendar=null;
    ImageView back,refresh;
    String heure;
    String dateExact, code;
    ImageView profile_pic;
    static final private String ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    final private Random rng = new SecureRandom();
    SwipeRefreshLayout swipeRefreshLayout;
    @RequiresApi(api = Build.VERSION_CODES.M)
    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calendrier);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        profile_pic=findViewById(R.id.profile_pic);
        //Intent intent = getIntent();
       //id = intent.getStringExtra("id");
        final  String userID = getIntent().getStringExtra("userID");
        final String photoUrl = getIntent().getStringExtra("photoUser");
        final String position = getIntent().getStringExtra("pos");
        Toast.makeText(Calendrier.this, userID , Toast.LENGTH_SHORT).show();
        Toast.makeText(Calendrier.this, photoUrl , Toast.LENGTH_SHORT).show();
        id=position;
        //id =MainActivity.customers.get(position).getId();
        back=findViewById(R.id.back);
        swipeRefreshLayout=findViewById(R.id.swipe);
        MultiTransformation multi = new MultiTransformation(
                new RoundedCornersTransformation(128, 0, RoundedCornersTransformation.CornerType.BOTTOM));
        Glide.with(this).load(photoUrl)
                .apply(bitmapTransform(multi))
                .into(profile_pic);
        profile_pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Calendrier.this, FirstActivity.class);
                intent.putExtra("userID", userID);
                intent.putExtra("photoUser", photoUrl);
                // i.putExtra("pos", userID);
                startActivity(intent);
                finish();
            }
        });

swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
    @Override
    public void onRefresh() {
        (new Handler()).postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(Calendrier.this,Calendrier.class);
                intent.putExtra("pos", position);
                intent.putExtra("userID", userID);
                intent.putExtra("photoUser", photoUrl);


            // intent.putExtra("Email",Email);
                startActivity(intent);
                finish();
            }
        }, 1500);
    }

});
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                back.setColorFilter(R.color.colorPrimaryDark);
                Intent intent = new Intent(Calendrier.this,StadeProfile.class);
                intent.putExtra("pos", position);
                intent.putExtra("userID", userID);
                intent.putExtra("photoUser", photoUrl);
                //intent.putExtra("Email",Email);
                startActivity(intent);
                finish();
            }
        });
        refresh=findViewById(R.id.refresh);
        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                back.setColorFilter(R.color.colorPrimaryDark);
                Intent intent = new Intent(Calendrier.this,Calendrier.class);
                intent.putExtra("pos", position);
                intent.putExtra("userID", userID);
                intent.putExtra("photoUser", photoUrl);
               // intent.putExtra("Email",Email);
                startActivity(intent);
                finish();
            }
        });

        ids.add((Button)findViewById( R.id.a));
        ids.add((Button)findViewById(R.id.b));
        ids.add((Button)findViewById(R.id.c));
        ids.add((Button)findViewById(R.id.d));
        ids.add((Button)findViewById(R.id.e));
        ids.add((Button)findViewById(R.id.f));
        ids.add((Button)findViewById(R.id.g));
        ids.add((Button)findViewById(R.id.h));
        ids.add((Button)findViewById(R.id.i));
        ids.add((Button)findViewById(R.id.j));
        ids.add((Button)findViewById(R.id.k));
        ids.add((Button)findViewById(R.id.l));
        ids.add((Button)findViewById(R.id.m));
        ids.add((Button)findViewById(R.id.n));
        ids.add((Button)findViewById(R.id.o));
        ids.add((Button)findViewById(R.id.p));
        restart();


      for(int i=0;i<ids.size();i++){

        final int finalI = i;
        ids.get(i).setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(date==null){
                Toast.makeText(getApplicationContext(), "Prière de choisir une date ", Toast.LENGTH_SHORT).show();

            }else if (ids.get(finalI).getCurrentTextColor()==R.color.devider)
            {

                Toast.makeText(getApplicationContext(), "Non disponible, Désactivé par l'administrateur", Toast.LENGTH_SHORT).show(); }


            else{
                AlertDialog.Builder builder1 = new AlertDialog.Builder(Calendrier.this);
                builder1.setMessage("Voulez-vous effectuer une réservation pour cette heure?");
                builder1.setCancelable(true);

                builder1.setPositiveButton(
                        "Oui",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                code = randomUUID(5,1,'-');
                                myCalendar = Calendar.getInstance();
                                DateFormat df = new SimpleDateFormat("EEE, d MMM yyyy, HH:mm");
                                dateExact = df.format(myCalendar.getTime());
                                DateFormat df1 = new SimpleDateFormat("HH:mm");
                                heure = df1.format(myCalendar.getTime());
                                writeNewReservation(myRef,myRef1,userID, code,date,ids.get(finalI).getText().toString(), finalI, dateExact);
                                Toast.makeText(Calendrier.this,"Bien enregistrée" , Toast.LENGTH_LONG).show();

                                //Intent i = new Intent(getBaseContext(), StadeProfile.class);
                                //i.putExtra("pos", pos);
                                //startActivity(i);
                                //finish();
                                dialog.cancel();


                            }
                        });

                builder1.setNegativeButton(
                        "Non",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

                AlertDialog alert11 = builder1.create();
                alert11.show();
            }
        }
        });
}
        mDisplayDate = (TextView) findViewById(R.id.tvDate);
        mDisplayDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        Calendrier.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mDateSetListener,
                        year,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                Log.d("Calendrier", "onDateSet: mm-dd-yyy: " + day + "-" + month + "-" + year);

                date = year+"-"+"0"+month+"-"+day;

                mDisplayDate.setText(date);
                restart();
                adapterlist();



            }
        };





    }
    public  void  adapterlist(){
        final Button[] b = new Button[1];
       Query query=myRef.child(id).child("Newreservation").orderByChild("date").equalTo(date);


/*query.addListenerForSingleValueEvent(new ValueEventListener() {
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onDataChange(DataSnapshot dataSnapshot) {


      if(dataSnapshot.exists()) {
            for (DataSnapshot data : dataSnapshot.getChildren()) {

    b[0] = ids.get(Integer.parseInt(data.child("etat").getValue().toString()));

    b[0].setBackground(getResources().getDrawable(R.drawable.testlistgreen));

                b[0].setEnabled(false);
    b[0].setClickable(false);


            }
        }


    }

    @Override
    public void onCancelled(DatabaseError databaseError) {

    }
});*/
query.addChildEventListener(new ChildEventListener() {
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
        b[0] = ids.get(Integer.parseInt(dataSnapshot.child("etat").getValue().toString()));

        b[0].setBackgroundColor(getResources().getColor(R.color.colorPrimary));

        b[0].setEnabled(false);
        b[0].setClickable(false);

    }

    @Override
    public void onChildChanged(DataSnapshot dataSnapshot, String s) {

    }

    @Override
    public void onChildRemoved(DataSnapshot dataSnapshot) {
        b[0] = ids.get(Integer.parseInt(dataSnapshot.child("etat").getValue().toString()));

        b[0].setBackgroundColor(Color.WHITE);

        b[0].setEnabled(true);
        b[0].setClickable(true);


    }

    @Override
    public void onChildMoved(DataSnapshot dataSnapshot, String s) {

    }

    @Override
    public void onCancelled(DatabaseError databaseError) {

    }
});
        myRef.child(id).child("tempBloquer").child(date).addChildEventListener(new ChildEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
               // Toast.makeText(getApplicationContext(), dataSnapshot.toString(), Toast.LENGTH_SHORT).show();

                if(dataSnapshot.exists()) {
                    //Toast.makeText(getApplicationContext(), dataSnapshot.toString(), Toast.LENGTH_SHORT).show();


                        int i = Integer.parseInt(dataSnapshot.getKey());
                       //Toast.makeText(getApplicationContext(), data.toString(), Toast.LENGTH_SHORT).show();
                        ids.get(i).setBackgroundColor(getResources().getColor(R.color.devider));
                        ids.get(i).setEnabled(false);
                        ids.get(i).setClickable(false);

                }

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {



            }
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                int i = Integer.parseInt(dataSnapshot.getKey());
                    ids.get(i).setBackgroundColor(getResources().getColor(R.color.even));
                    ids.get(i).setEnabled(true);
                    ids.get(i).setClickable(true);



            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
       /* myRef.child(id).child("tempBloquer").child(date).addListenerForSingleValueEvent(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()) {
                    for (DataSnapshot data : dataSnapshot.getChildren()) {
                         int i = Integer.parseInt(data.getKey());
                        ids.get(i).setBackgroundColor(getResources().getColor(R.color.devider));
                        ids.get(i).setEnabled(false);
                        ids.get(i).setClickable(false);
                    }
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });*/

    }


    public  void restart(){
        for(int i=0;i<ids.size();i++){

                ids.get(i).setBackgroundColor(Color.WHITE);

            ids.get(i).setEnabled(true);
            ids.get(i).setClickable(true);

        }
    }

public static void writeNewReservation(final  DatabaseReference databaseReference,DatabaseReference myRef, String userID,String code, String date, String heure, int etat, String DateDeDemande) {

      //  NewReservation newReservation = new NewReservation(code, date, heure, Integer.toString(etat),DateDeDemande, userID);
        //NewReservation newReservation1 = new NewReservation(code, date, heure, Integer.toString(etat),DateDeDemande, id);
        String key = databaseReference.child(id).child("Newreservation").push().getKey().toString();

   //databaseReference.child(id).child("Newreservation").child(key).setValue(newReservation);
    //myRef.child(userID).child("Myreservation").child(key).setValue(newReservation1);



    databaseReference.child(id).addListenerForSingleValueEvent(new ValueEventListener() {
       @Override
       public void onDataChange(DataSnapshot dataSnapshot) {
           String ii=dataSnapshot.child("numberres").getValue().toString();
          int i = Integer.parseInt( ii);
       if(ii!=null) { databaseReference.child(id).child("numberres").setValue(i+1);}
       else{databaseReference.child(id).child("numberres").setValue("1");}

       }

       @Override
       public void onCancelled(DatabaseError databaseError) {

       }
   });
        }

        char randomChar(){
        return ALPHABET.charAt(rng.nextInt(ALPHABET.length()));
        }

        String randomUUID(int length, int spacing, char spacerChar){
        StringBuilder sb = new StringBuilder();
        int spacer = 0;
        while(length > 0){
        if(spacer == spacing){
        sb.append(spacerChar);
        spacer = 0;
        }
        length--;
        spacer++;
        sb.append(randomChar());
        }
        return sb.toString();
        }
    @Override
    protected void onPause() {
        super.onPause();

        ActivityManager activityManager = (ActivityManager) getApplicationContext()
                .getSystemService(Context.ACTIVITY_SERVICE);

        activityManager.moveTaskToFront(getTaskId(), 0);
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // Do nothing or catch the keys you want to block
        return false;
    }
}
