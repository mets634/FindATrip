package com.example.erel_yonah.findatrip.controller;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.erel_yonah.findatrip.R;

import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;

public class TripActivity extends AppCompatActivity {

    String country;
    GregorianCalendar startDate;
    GregorianCalendar endDate;
    String description;
    Integer price;
    Long businessId;

    TextView countryText;
    TextView startDateText;
    TextView endDateText;
    TextView descriptionText;
    TextView priceText;
    TextView businessID;
    FloatingActionButton gotoBusiness;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip);

        Intent intent = getIntent();
        country = (String) intent.getExtras().get("TRIP_COUNTRY");
        startDate = (GregorianCalendar) intent.getExtras().get("TRIP_START");
        endDate = (GregorianCalendar) intent.getExtras().get("TRIP_END");
        description = (String) intent.getExtras().get("TRIP_DESCRIPTION");
        price = (Integer) intent.getExtras().get("TRIP_PRICE");
        businessId = (long) intent.getExtras().get("TRIP_AGENCY_ID");

        setTitle(country.toUpperCase());

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

        countryText = (TextView) findViewById(R.id.country);
        startDateText = (TextView) findViewById(R.id.start);
        endDateText = (TextView) findViewById(R.id.end);
        descriptionText = (TextView) findViewById(R.id.desc);
        priceText = (TextView) findViewById(R.id.price);
        businessID = (TextView) findViewById(R.id.id);
        gotoBusiness = (FloatingActionButton) findViewById(R.id.gotoBusiness);


        countryText.setText(country);
        startDateText.setText(dateFormat.format(startDate.getTime()));
        endDateText.setText(dateFormat.format(endDate.getTime()));
        descriptionText.setText(description);
        priceText.setText(price.toString() + " ₪");
        businessID.setText(businessId.toString());

        gotoBusiness.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO:filter agencies by their id
            }
        });

        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
