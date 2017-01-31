package com.example.erel_yonah.findatrip.controller;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.erel_yonah.findatrip.R;
import com.example.erel_yonah.findatrip.model.entities.Address;

import org.w3c.dom.Text;

import static android.content.Intent.EXTRA_EMAIL;
import static android.content.Intent.EXTRA_SUBJECT;

public class AgencyActivity extends AppCompatActivity {

    String name;
    String address;
    String email;
    String phoneNumber;
    String website;
    Long businessId;

    TextView emailText;
    TextView websiteText;
    TextView phoneNumberText;
    TextView addressText;
    TextView nameText;
    TextView businessID;
    FloatingActionButton navButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agency);

        Intent intent = getIntent();
        name = (String) intent.getExtras().get("AGENCY_NAME");
        address = (String) intent.getExtras().get("AGENCY_ADDRESS");
        email = (String) intent.getExtras().get("AGENCY_EMAIL");
        phoneNumber = (String) intent.getExtras().get("AGENCY_PHONENUMBER");
        website = (String) intent.getExtras().get("AGENCY_WEBSITE");
        businessId = (Long) intent.getExtras().get("AGENCY_ID");

        setTitle(name.toUpperCase());

        emailText = (TextView) findViewById(R.id.email);
        websiteText = (TextView) findViewById(R.id.website);
        phoneNumberText = (TextView) findViewById(R.id.phonenumber);
        addressText = (TextView) findViewById(R.id.address);
        nameText = (TextView) findViewById(R.id.name);
        navButton = (FloatingActionButton) findViewById(R.id.navButton);
        businessID = (TextView) findViewById(R.id.id);

        emailText.setText(email);
        websiteText.setText(website);
        phoneNumberText.setText(phoneNumber);
        addressText.setText(address);
        nameText.setText(name);
        businessID.setText(Long.toString(businessId));

        emailText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendEmail();
            }
        });
        websiteText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openWebAddress();
            }
        });
        phoneNumberText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialPhoneNumber();
            }
        });
        addressText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openNavigationApp();
            }
        });
        navButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openNavigationApp();
            }
        });
    }

    private void dialPhoneNumber() {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:"+phoneNumber));
        startActivity(intent);
    }

    private void openWebAddress() {
        Toast.makeText(getApplication(), "Will be implemented shortly", Toast.LENGTH_SHORT);
    }

    private void openNavigationApp() {
        Intent intent = new Intent(Intent.ACTION_VIEW,
                Uri.parse("geo:0,0?q=" + Uri.encode(address)));
        startActivity(intent);
    }

    private void sendEmail() {
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.setType("text/plain");
        emailIntent.putExtra(EXTRA_EMAIL, new String[]{email});
        emailIntent.putExtra(EXTRA_SUBJECT, "I want to have the perfect trip!");
        startActivity(emailIntent);
    }
}
