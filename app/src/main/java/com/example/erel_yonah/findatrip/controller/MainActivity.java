package com.example.erel_yonah.findatrip.controller;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.SearchView;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.example.erel_yonah.findatrip.R;
import com.example.erel_yonah.findatrip.model.backend.DSManagerFactory;
import com.example.erel_yonah.findatrip.model.entities.Agency;
import com.example.erel_yonah.findatrip.model.entities.Trip;

import java.text.SimpleDateFormat;
import java.util.Random;

import static java.lang.System.exit;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        AgencyFragment.OnListFragmentInteractionListener, TripFragment.OnListFragmentInteractionListener {

    public FragmentManager fragmentManager = getSupportFragmentManager();
    public Menu menu;
    protected Intent serviceIntent;
    private final static String ACTION = "ACTION_UPDATE";
    //private final static String EXTRA = "EXTRA";

    //private static Integer counter = 0;
    private BroadcastReceiver _refreshReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            /*String text = intent.getExtras().getString(EXTRA);
            ( (TextView) findViewById( R.id.showBroadcast ) ).setText(counter.toString() + ": " + text);
            counter++;*/
            updateDB();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //open main fragment
        fragmentManager.beginTransaction().add(R.id.fragment_container,new MainFragment()).commit();

        //init database
        updateDB();

        //start service
        serviceIntent = activateService();

        //register service
        registerReceiver(_refreshReceiver, new IntentFilter(ACTION));

        //hold the toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //set floating button
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, generateSentence(), Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

       //set components in the toolbar
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        //set the navigation bar
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        //close the broadcast reciever
        unregisterReceiver(_refreshReceiver);

        //stop service
        stopService(serviceIntent);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            //if drawer is open
            drawer.closeDrawer(GravityCompat.START); //close navigation bar
        } else if (fragmentManager.getBackStackEntryCount() > 0) {
            //if it's not the last fragment
            fragmentManager.popBackStack(); //go back to the last fragment
        } else {
            super.onBackPressed(); //go back normally
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);

        //catch the search view
        this.menu = menu;

        //determine what happens when search view is opened or closed
        MenuItemCompat.setOnActionExpandListener(menu.findItem(R.id.action_search), new MenuItemCompat.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                Fragment curfFrag = fragmentManager.findFragmentById(R.id.fragment_container);
                if (curfFrag instanceof MainFragment) {
                    TripFragment fragment = new TripFragment();
                    SearchView search = (SearchView) item.getActionView();
                    fragment.setSearchView(search);

                    FragmentTransaction transaction = fragmentManager.beginTransaction();
                    transaction.replace(R.id.fragment_container,fragment);
                    transaction.addToBackStack(null);
                    transaction.commit();

                } else {
                    fragmentManager.beginTransaction().addToBackStack(null).commit();
                }
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                fragmentManager.popBackStack();
                return true;
            }
        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        // Handle navigation view item clicks here.
        int id = item.getItemId();

        Fragment fragment = new MainFragment();

        if (id == R.id.nav_mainFragment) {
            fragment = new MainFragment();
        } else if (id == R.id.nav_businesses) {
            fragment = AgencyFragment.newInstance(1);
        } else if (id == R.id.nav_trips) {
            fragment = new TripFragment();
        } else if (id == R.id.nav_exit) {
            exitApplication();
        } else if (id == R.id.erel) {

        } else if (id == R.id.yonah) {

        }

        //send search view to fragment in order to preform filtering
        SearchView search = (SearchView) menu.findItem(R.id.action_search).getActionView();
        if (fragment instanceof AgencyFragment) ((AgencyFragment) fragment).setSearchView(search);
        if (fragment instanceof TripFragment) ((TripFragment) fragment).setSearchView(search);

        //switch fragment content
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fragment_container,fragment);
        transaction.addToBackStack(null);
        transaction.commit();

        //close navigation drawer
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void onListFragmentInteraction(Agency agency) {
        //Toast toast = Toast.makeText(this, agency.getName() + "   " + agency.getPhoneNumber(), Toast.LENGTH_SHORT);
        //toast.show();

        Intent intent = new Intent(this,AgencyActivity.class);
        intent.putExtra("AGENCY_NAME", agency.getName());
        intent.putExtra("AGENCY_ADDRESS",agency.getAddress().getAddress());
        intent.putExtra("AGENCY_EMAIL", agency.getEmail());
        intent.putExtra("AGENCY_PHONENUMBER", agency.getPhoneNumber());
        intent.putExtra("AGENCY_WEBSITE", agency.getWebsite());
        intent.putExtra("AGENCY_ID", agency.getID());
        startActivity(intent);
    }

    public void onListFragmentInteraction(Trip trip) {
        //Toast toast = Toast.makeText(this, agency.getName() + "   " + agency.getPhoneNumber(), Toast.LENGTH_SHORT);
        //toast.show();

        Intent intent = new Intent(this,TripActivity.class);
        intent.putExtra("TRIP_AGENCY_ID", trip.getAgencyID());
        intent.putExtra("TRIP_COUNTRY",trip.getCountry());
        intent.putExtra("TRIP_DESCRIPTION", trip.getDescription());
        intent.putExtra("TRIP_PRICE", trip.getPrice());
        intent.putExtra("TRIP_START", trip.getStart());
        intent.putExtra("TRIP_END", trip.getEnd());
        startActivity(intent);
    }

    protected Intent activateService() {
        ComponentName componentName = new ComponentName("com.example.java5777.travelagencies",".model.Service.CheckUpdatesService");
        Intent intent = new Intent();
        intent.setComponent(componentName);
        startService(intent);
        return intent;
    }

    protected void updateDB() {
        try {
            DSManagerFactory.getDSManager("List").update(this);
        }
        catch (Exception e) {
            Toast toast = Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    protected void exitApplication() {
        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Exit Application")
                .setMessage("Are you sure you want to exit?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }

                })
                .setNegativeButton("No", null)
                .show();
    }

    protected String generateSentence() {
        Random rand = new Random();
        String[] strings = {"You're very handsome today!", "You're my favorite user", "I love you so much...", "You're a lizard, Harry.", "Have a nice trip!", "Make America great again!", "Choose your trip carefully...", "Always remember you're unique, just like everyone else.",};
        return strings[rand.nextInt(strings.length)];
    }
}
