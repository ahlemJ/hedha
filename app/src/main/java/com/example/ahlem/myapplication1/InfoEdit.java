package com.example.ahlem.myapplication1;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class InfoEdit extends AppCompatActivity {
    ImageView back;
    EditText name, localisation, numero;
    Button modifName, modifnum, modifloc, enregistrer, annuler;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("simpleusers");
    Boolean changement=false;
    Boolean one=false;
    Boolean two=false;
    Boolean three=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile3);
        back = (ImageView) findViewById(R.id.back);
        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        Intent intent= getIntent();
        //final String  id = intent.getStringExtra("userID");
        //final String  photoUser = intent.getStringExtra("photoUser");
        final String id = FirstActivity.user.getId();
        final String photoUser = FirstActivity.user.get_filephoto();
        Toast.makeText(getApplicationContext(), id, Toast.LENGTH_SHORT).show();
        name=(EditText)findViewById(R.id.editname);
        localisation=(EditText)findViewById(R.id.editloc);
        numero=(EditText)findViewById(R.id.editnum);
        modifName= findViewById(R.id.namebutton);
        modifloc=findViewById(R.id.locbutton);
        modifnum=findViewById(R.id.numbutton);
        enregistrer=findViewById(R.id.enregitrer);
        annuler=findViewById(R.id.annuler);
        enregistrer.setVisibility(View.INVISIBLE);
        annuler.setVisibility(View.INVISIBLE);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //back.setColorFilter(R.color.colorPrimary);
                Intent intent = new Intent(InfoEdit.this,Parametre.class);
               // intent.putExtra("userID", id);
                //intent.putExtra("photoUser", photoUser);
                startActivity(intent);
                finish();
            }
        });
myRef.child(id).addListenerForSingleValueEvent(new ValueEventListener() {
    @Override
    public void onDataChange(DataSnapshot dataSnapshot) {
        name.setText(FirstActivity.user.get_name());
        numero.setText(FirstActivity.user.getMobile());
        localisation.setText(FirstActivity.user.getLocalisation());
       // name.setText(dataSnapshot.child("_name").getValue().toString());
        //numero.setText(dataSnapshot.child("Mobile").getValue().toString());
        //localisation.setText(dataSnapshot.child("localisation").getValue().toString());
    }

    @Override
    public void onCancelled(DatabaseError databaseError) {

    }
});
        //localisation.setText(FirstActivity.user.getLocalisation());
        //name.setText(FirstActivity.user.get_name());
        //numero.setText("+216 "+ FirstActivity.user.getMobile());
//localisation.setKeyListener(null);
//name.setKeyListener(null);
//numero.setKeyListener(null);
localisation.setEnabled(false);
name.setEnabled(false);
numero.setEnabled(false);

modifName.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        one=true;
        enregistrer.setVisibility(View.VISIBLE);
        annuler.setVisibility(View.VISIBLE);
        name.setEnabled(true);
       enregistrer.setEnabled(true);
       modifloc.setEnabled(false);
       modifloc.setTextColor(getResources().getColor(R.color.grey));
        modifnum.setTextColor(getResources().getColor(R.color.grey));
       modifnum.setEnabled(false);

    }
});
modifloc.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        two=true;
        enregistrer.setVisibility(View.VISIBLE);
        annuler.setVisibility(View.VISIBLE);
        localisation.setEnabled(true);
       enregistrer.setEnabled(true);
        modifName.setEnabled(false);
        modifnum.setEnabled(false);
        modifName.setTextColor(getResources().getColor(R.color.grey));
        modifnum.setTextColor(getResources().getColor(R.color.grey));
    }
});

modifnum.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        three=true;
        enregistrer.setVisibility(View.VISIBLE);
        annuler.setVisibility(View.VISIBLE);
        numero.setEnabled(true);
       enregistrer.setEnabled(true);
        modifloc.setEnabled(false);
        modifName.setEnabled(false);
        modifloc.setTextColor(getResources().getColor(R.color.grey));
        modifName.setTextColor(getResources().getColor(R.color.grey));
    }
});
       enregistrer.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               if(isNetworkAvailable()) {
                   if (one == true && name.getText().toString().length() > 0) {

                       if (!name.getText().toString().equals(FirstActivity.user.get_name())) {
                           myRef.child(id).child("_name").setValue(name.getText().toString());
                           Toast.makeText(getApplicationContext(), "Changement bien enregistré ", Toast.LENGTH_SHORT).show();
                           changement = true;
                       }
                   }

                   if (two == true && localisation.getText().toString().length() > 0 && !localisation.getText().toString().equals(FirstActivity.user.getLocalisation())) {
                       myRef.child(id).child("localisation").setValue(localisation.getText().toString());
                       Toast.makeText(getApplicationContext(), "Changement bien enregistré", Toast.LENGTH_SHORT).show();
                       changement = true;

                   }

                   if (three == true && numero.getText().toString().length() > 0 && !numero.getText().toString().equals(FirstActivity.user.getMobile())) {
                       myRef.child(id).child("Mobile").setValue(numero.getText().toString());
                       Toast.makeText(getApplicationContext(), "Changement bien enregistré", Toast.LENGTH_SHORT).show();
                       changement = true;

                   }


                   if (changement) {
                       localisation.setEnabled(false);
                       name.setEnabled(false);
                       numero.setEnabled(false);
                       enregistrer.setVisibility(View.INVISIBLE);
                       annuler.setVisibility(View.INVISIBLE);
                       changement = false;
                       one = false;
                       two = false;
                       three = false;
                       modifName.setEnabled(true);
                       modifnum.setEnabled(true);
                       modifloc.setEnabled(true);
                       modifName.setTextColor(getResources().getColor(R.color.colorPrimary));
                       modifloc.setTextColor(getResources().getColor(R.color.colorPrimary));
                       modifnum.setTextColor(getResources().getColor(R.color.colorPrimary));
                   }
               }
               else {Toast.makeText(InfoEdit.this, "Pas de connexion internet", Toast.LENGTH_LONG).show();}
           }
       });
       annuler.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               localisation.setEnabled(false);
               name.setEnabled(false);
               numero.setEnabled(false);
               enregistrer.setVisibility(View.INVISIBLE);
               annuler.setVisibility(View.INVISIBLE);
               changement=false;
               one=false ; two=false; three=false;
               modifName.setEnabled(true);
               modifnum.setEnabled(true);
               modifloc.setEnabled(true);
               modifName.setTextColor(getResources().getColor(R.color.colorPrimary));
               modifloc.setTextColor(getResources().getColor(R.color.colorPrimary));
               modifnum.setTextColor(getResources().getColor(R.color.colorPrimary));
           }
       });
    }
    private boolean isNetworkAvailable() {
        boolean haveConnectedWifi = false;
        boolean haveConnectedMobile = false;

        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo[] netInfo = cm.getAllNetworkInfo();

        for (NetworkInfo ni : netInfo) {
            if (ni.getTypeName().equalsIgnoreCase("WIFI"))
                if (ni.isConnected())
                    haveConnectedWifi = true;
            if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
                if (ni.isConnected())
                    haveConnectedMobile = true;
        }
        return haveConnectedWifi || haveConnectedMobile;
    }
}
