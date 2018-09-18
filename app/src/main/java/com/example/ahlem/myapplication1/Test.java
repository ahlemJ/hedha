package com.example.ahlem.myapplication1;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.transition.Explode;
import android.transition.Slide;
import android.transition.TransitionInflater;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;

public class Test extends AppCompatActivity {
    Button exit ;
AutoCompleteTextView autoCompleteTextView;
@TargetApi(21)
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notification_item);
    TextView text = findViewById(R.id.textVieww);
    text.setText(Html.fromHtml("Votre réservation dans " + "</b>Terrain foot</b>" +" pour le 08/05/2018 18h-19h:30 a été" + "</b> <font color=\"#4caf50\"> confirmée</b>."));
       // autoCompleteTextView = findViewById(R.id.autoCompleteTextView1);
        //exit = findViewById(R.id.reser);
        Explode explode = new Explode();
        getWindow().setExitTransition(explode);

setupWindowAnimations();
   /* TabLayout tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
    tabLayout.addTab(tabLayout.newTab().setText("Tab 1"));
    tabLayout.addTab(tabLayout.newTab().setText("Tab 2"));
    tabLayout.addTab(tabLayout.newTab().setText("Tab 3"));
    tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);*/

    }
    @TargetApi(21)
    private void setupWindowAnimations() {
        Slide slide = new Slide();
        slide.setDuration(1000);
        getWindow().setExitTransition(slide);
    }

}
