package com.example.ahlem.myapplication1;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public  class MotDePass  extends AppCompatActivity {
    TextInputEditText pass1;
    TextInputEditText pass2;
    TextInputEditText oldpasss;
    ImageView back;
    Button confirmer;
    FirebaseUser user;
    Boolean changement = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.motdepass);
        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        back=(ImageView)findViewById(R.id.back);
        pass1=(TextInputEditText)findViewById(R.id.mdp1);
        pass2=(TextInputEditText)findViewById(R.id.mdp2);
        oldpasss=(TextInputEditText)findViewById(R.id.oldmdp);
        confirmer = findViewById(R.id.confirmer);
        Intent intent= getIntent();
        //final String  userID = intent.getStringExtra("userID");
        //final String  photoUser = intent.getStringExtra("photoUser");
        final String userID = FirstActivity.user.getId();
        final String photoUser = FirstActivity.user.get_filephoto();
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                back.setColorFilter(R.color.colorPrimary);
                Intent intent = new Intent(MotDePass.this,Parametre.class);
               // intent.putExtra("userID", userID);
                //intent.putExtra("photoUser", photoUser);
                startActivity(intent);
                finish();
            }
        });


        confirmer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pass1.getText().toString().length()>0 && pass2.getText().toString().length()>0) {
                    if (!pass1.getText().toString().equals(pass2.getText().toString())) {
                        Toast.makeText(getApplicationContext(), "Erreur dans l'ecriture du mot de passe", Toast.LENGTH_SHORT).show();

                    } else if ((pass1.getText().toString().length() < 6)) {
                        Toast.makeText(getApplicationContext(), R.string.minimum_password, Toast.LENGTH_SHORT).show();
                    } else {
                        user = FirebaseAuth.getInstance().getCurrentUser();
                        AuthCredential credential = EmailAuthProvider
                                .getCredential(user.getEmail(), oldpasss.getText().toString());

// Prompt the user to re-provide their sign-in credentials
                        user.reauthenticate(credential)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            user.updatePassword(pass1.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if (task.isSuccessful()) {
                                                        Toast.makeText(getApplicationContext(), "mot de pass bien changé", Toast.LENGTH_SHORT).show();


                                                    } else {
                                                        Toast.makeText(getApplicationContext(), "Erreur changement non enregistré ", Toast.LENGTH_SHORT).show();

                                                    }
                                                }
                                            });
                                        } else {
                                            Toast.makeText(getApplicationContext(), "Erreur authentification", Toast.LENGTH_SHORT).show();

                                        }
                                    }
                                });
                        changement = true;

                    }
                }
                if(changement){
                    Intent intent = new Intent(MotDePass.this,Parametre.class);
                    //intent.putExtra("userID", userID);
                    //intent.putExtra("photoUser", photoUser);
                    startActivity(intent);
                    finish();

                }
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
