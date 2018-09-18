package com.example.ahlem.myapplication1;

import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

class CodeDisplay   extends AppCompatActivity {
EditText codeView ;
    EditText phoneView ;
    String phoneNumber ;
Button sortir ;
Button envoyer;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.codedisplay);
        Intent intent = getIntent();
        final Integer position = intent.getIntExtra("pos",0);
        final String code = intent.getStringExtra("code");

        codeView = findViewById(R.id.code);
        phoneView = findViewById(R.id.tlf);
        sortir = findViewById(R.id.sortir);
        envoyer = findViewById(R.id.envoyer);


       envoyer.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               phoneNumber =phoneView.getText().toString();

           }
       });
        sortir.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(CodeDisplay.this, CodeDisplay.class);
            intent.putExtra("pos", position);
            startActivity(intent);
            finish();
        }
    });
    }




}
