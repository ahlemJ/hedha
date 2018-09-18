package com.example.ahlem.myapplication1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class ListIndiv extends AppCompatActivity {

    ImageButton search, back;
    TextView nameprofile;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.evenement);

        search=findViewById(R.id.back);
        back=findViewById(R.id.search1);
        nameprofile=findViewById(R.id.namestade);

        final Integer position = getIntent().getIntExtra("pos",0);
        final  String id1 = MainActivity.customers.get(position).getId();
        nameprofile.setText(MainActivity.customers.get(position).getFirstName());
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(getBaseContext(), StadeProfile.class);
                i.putExtra("pos", position);
                startActivity(i);
                finish();
            }
        });
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getBaseContext(),MainActivity.class);
                i.putExtra("pos", position);
                startActivity(i);
                finish();
            }
        });

    }


}
