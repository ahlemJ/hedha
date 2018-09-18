package com.example.ahlem.myapplication1;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.transition.Fade;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
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
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

import static com.bumptech.glide.request.RequestOptions.bitmapTransform;

public class Reservation extends AppCompatActivity {
private Button pop ;
ImageButton back,search;
   ImageView photo;
ListView temps;
List<String> list;
    FirebaseDatabase database;
    DatabaseReference myRef;
   Calendar myCalendar= null;
   String  date;
   static  String id1;
    String heure;
    String dateExact;
    static final private String ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    final private Random rng = new SecureRandom();
    Boolean conf = false ;
    String code ;
    Integer pos ;
     List<Integer> listPos = new ArrayList<Integer>();
    ChildEventListener valueEventListener;
    ArrayAdapter<String> arrayAdapter;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reservation);
        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setupWindowAnimations();
        photo=findViewById(R.id.profile_pic);
        final String position = getIntent().getStringExtra("pos");
        final String userID = getIntent().getStringExtra("UserID");
        final String photoUrl = getIntent().getStringExtra("photoUser");
        MultiTransformation multi = new MultiTransformation(
                new RoundedCornersTransformation(128, 0, RoundedCornersTransformation.CornerType.BOTTOM));
        Glide.with(this).load(photoUrl)
                .apply(bitmapTransform(multi))
                .into(photo);
        photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Reservation.this, FirstActivity.class);
                // i.putExtra("pos", userID);
                startActivity(i);
                finish();
            }
        });
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("users");


        //Intent intent = getIntent();

        id1 = position;

        //id1 =MainActivity.customers.get(pos).getId();

        pop = findViewById(R.id.pop);
        back = findViewById(R.id.back);
        search = findViewById(R.id.search1);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               myRef.child(id1).child("Newreservation").removeEventListener(valueEventListener);
                Intent i = new Intent(getBaseContext(), StadeProfile.class);
                i.putExtra("pos", id1);
                i.putExtra("userID", userID);
                i.putExtra("photoUser", photoUrl);
                startActivity(i);
                finish();
            }
        });
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myRef.child(id1).child("Newreservation").removeEventListener(valueEventListener);
                Intent i = new Intent(getBaseContext(), MainActivity.class);
                i.putExtra("userID", id1);
                i.putExtra("userID", userID);
                i.putExtra("photoUser", photoUrl);
                startActivity(i);
                finish();
            }
        });
        temps = findViewById(R.id.temps);
         list = new ArrayList<String>() {{
            add("10h-11h:30");
            add("11h:30-13h");
            add("13h-14h:30");
            add("14h:30-16h");
             add("10h-11h:30");
             add("11h:30-13h");
             add("13h-14h:30");
             add("14h:30-16h");
        }};
        myCalendar = Calendar.getInstance();
        date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());


       updateLabel();
        DateFormat df = new SimpleDateFormat("EEE, d MMM yyyy, HH:mm");
        dateExact = df.format(myCalendar.getTime());
        DateFormat df1 = new SimpleDateFormat("HH:mm");
        heure = df1.format(myCalendar.getTime());
        //pop.setText(date);





valueEventListener =new ChildEventListener() {
    @Override
    public void onChildAdded(DataSnapshot dataSnapshot, String s) {

       updateLabel();
      //  update();
    }

    @Override
    public void onChildChanged(DataSnapshot dataSnapshot, String s) {
updateLabel();
       // update();
    }

    @Override
    public void onChildRemoved(DataSnapshot dataSnapshot) {
  updateLabel();
       // update();
    }

    @Override
    public void onChildMoved(DataSnapshot dataSnapshot, String s) {

    }

    @Override
    public void onCancelled(DatabaseError databaseError) {

    }
};
        myRef.child(id1).child("Newreservation").addChildEventListener(valueEventListener);





       final  DatePickerDialog.OnDateSetListener date1 = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                //updateLabel();  //to update the date
                updateLabel();

            }

        };

        pop.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(Reservation.this, date1, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });


     temps.setOnItemClickListener(new AdapterView.OnItemClickListener() {
         @Override
         public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {


             AlertDialog.Builder builder1 = new AlertDialog.Builder(Reservation.this);
             builder1.setMessage("Voulez-vous effectuer une réservation pour cette heure?");
             builder1.setCancelable(true);

             builder1.setPositiveButton(
                     "Oui",
                     new DialogInterface.OnClickListener() {
                         public void onClick(DialogInterface dialog, int id) {
                             code = randomUUID(5,1,'-');
                            writeNewReservation(myRef,code,date,list.get(position), position, dateExact);
                            Toast.makeText(Reservation.this,"Bien enregistrée" , Toast.LENGTH_LONG).show();
                            updateLabel();
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
     });

    }





    private void updateLabel() {
        String myFormat = "yyyy-MM-dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
         date = sdf.format(myCalendar.getTime());
      //  updateList(date=sdf.format(myCalendar.getTime()));  //make firebase search query
        arrayAdapter = new ArrayAdapter<String>(Reservation.this,  android.R.layout.simple_list_item_1,
                list ){  int i;
            @Override
            public View getView(int position, View convertView, ViewGroup parent){
                // Get the current item from ListView
                View view = super.getView(position,convertView,parent);
                //parent.getChildAt(position).setEnabled(false);
                ((TextView) view).setTextColor(Color.GREEN);
                Query query=myRef.child(id1).child("Newreservation").orderByChild("date").equalTo(date);

                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        if(dataSnapshot.exists()) {
                            for (DataSnapshot data : dataSnapshot.getChildren()) {

                                NewReservation model = data.getValue(NewReservation.class);
                                String posi = model.getEtat();   //if the time is not avaible it return the position of the item in the list
                                 i =Integer.parseInt(posi);

                                ((TextView)temps.getChildAt(i)).setTextColor(Color.GRAY);  //make the item GRAY and non clickable

                                temps.getChildAt(i).setEnabled(false);
                                temps.getChildAt(i).setClickable(false);
                                temps.getChildAt(i).setOnClickListener(null);
                            }
                        }
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

                return view;
            }


        };
        temps.setAdapter(arrayAdapter);
        pop.setText(date);  //
        //edittext.setText(sdf.format(myCalendar.getTime()));


    }
    public static void writeNewReservation(DatabaseReference databaseReference, String code, String date, String heure, int etat, String DateDeDemande) {

    NewReservation newReservation = new NewReservation(code, date, heure, Integer.toString(etat),DateDeDemande);
    databaseReference.child(id1).child("Newreservation").push().setValue(newReservation);

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
    @TargetApi(21)
    private void setupWindowAnimations() {
        Fade fade = new Fade();
        fade.setDuration(1000);
        getWindow().setEnterTransition(fade);
    }
}
