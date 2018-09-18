package com.example.ahlem.myapplication1;

import android.app.ActivityManager;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Vector;


public  class AnnulerReservation  extends AppCompatActivity {
    TextView info;
    EditText code;
    Button valider;
    FirebaseDatabase database;
    DatabaseReference myRef;
    String id1;
    String codeSaisit;
    String key ;
    Button annuler;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.annulerreservation);

        info = findViewById(R.id.infoResr);
        code = findViewById(R.id.code);
        valider = findViewById(R.id.valider);

        final Integer position = getIntent().getIntExtra("pos", 0);
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("users");
        id1 = MainActivity.customers.get(position).getId();


        valider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                codeSaisit = code.getText().toString();


                Query query=myRef.child(id1).child("Newreservation").orderByChild("code").equalTo(codeSaisit);

                query.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                    if(dataSnapshot.exists()) {
                        for (DataSnapshot data : dataSnapshot.getChildren()) {

                            key= data.getKey();

                            NewReservation model = data.getValue(NewReservation.class);

                            String date = model.getDate();
                            String heure = model.getHeure();

                            String dateDEdDemande = model.getDateDeDemande();

                            info.setText(Html.fromHtml("Reservation pour le : "+date +" <br> pendant la durée : " + heure + " <br> a été effectué le :" + dateDEdDemande));



                        }
                    }
                        else {info.setText("Code non valide");}

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

            }
        });

        annuler = findViewById(R.id.annuler);
        annuler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder1 = new AlertDialog.Builder(AnnulerReservation.this);
                builder1.setMessage("Voulez-vous confirmer l'annulation de cette réservation ?");
                builder1.setCancelable(true);

                builder1.setPositiveButton(
                        "Oui",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                myRef.child(id1).child("Newreservation").child(key).removeValue();
                                dialog.cancel();
                                Toast.makeText(getBaseContext(), "Annulation effectuée", Toast.LENGTH_LONG).show();
                                Intent i = new Intent(AnnulerReservation.this, StadeProfile.class);
                                i.putExtra("pos", position);
                                startActivity(i);

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