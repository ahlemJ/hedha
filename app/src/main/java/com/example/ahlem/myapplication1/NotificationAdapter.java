package com.example.ahlem.myapplication1;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class NotificationAdapter extends ArrayAdapter<NewReservation> {
    FirebaseDatabase database;
  //  DatabaseReference myRef, myRef1;
    String name;
    TextView stade;
    String key , key1;

    ArrayList<NewReservation> users = new ArrayList<>();
    public NotificationAdapter(Context context, ArrayList<NewReservation> users) {
        super(context, 0, users);
        this.users=users;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        database = FirebaseDatabase.getInstance();
       // myRef = database.getReference("users");
        //myRef1 = database.getReference("simpleusers");
        NewReservation user = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.notification_item, parent, false);
        }
        // Lookup view for data population
        // = (TextView) convertView.findViewById(R.id.textView);

        TextView temps = (TextView) convertView.findViewById(R.id.textVieww);
      //  TextView nomTerrain = (TextView) convertView.findViewById(R.id.textViewww);
      //  final TextView etat = (TextView) convertView.findViewById(R.id.etat);
        // Populate the data into the template view using the data object

        //nomTerrain.setText(user.getDate() +" à "+" Terrain foot");
        //temps.setText(user.getHeure());
        //dateDereservation.setText(user.getHeure());
        String etat1 = user.getEtat();
        //etat.setText(etat1);

       if (user.getEtat().equals("confirmée")) {    temps.setText(Html.fromHtml("Votre réservation dans " + "</b> Terrain foot</b>" +" pour le "+ user.getDate() +" "+ user.getHeure()+" a été " + " <font color=\"#4caf50\"> "+ user.getEtat()+"."));
       }
        else {   temps.setText(Html.fromHtml("Votre réservation dans " + " Terrain foot" +" pour le "+ user.getDate() +" "+ user.getHeure()+" a été " + " <font color=\"#f44336\"> "+ user.getEtat()+"."));
       }
//nomTerrain.setText("Terrain foot");
       // key= user.getIdTerrain();
        //  key1= user.getId();
       /* myRef.child(key).child("firstName").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                etat.setText(dataSnapshot.getValue().toString());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
*/

        return convertView;
    }
    @Override
    public boolean isEnabled(int position)
    {
        return true;
    }

}
