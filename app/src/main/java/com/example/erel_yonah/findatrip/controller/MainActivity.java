package com.example.erel_yonah.findatrip.controller;

import android.content.BroadcastReceiver;
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
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.erel_yonah.findatrip.R;
import com.example.erel_yonah.findatrip.model.backend.DSManagerFactory;
import com.example.erel_yonah.findatrip.model.entities.Agency;

import static java.lang.System.exit;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, AgencyFragment.OnListFragmentInteractionListener {

    public FragmentManager fragmentManager = getSupportFragmentManager();
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
                Snackbar.make(view, "You're very handsome", Snackbar.LENGTH_LONG)
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

        unregisterReceiver(_refreshReceiver);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {  //if drawer is open
            drawer.closeDrawer(GravityCompat.START); //close navigation bar
        } else if (fragmentManager.getBackStackEntryCount() > 0) { //if it's not the last fragment
            fragmentManager.popBackStack(); //go back to the last fragment
        } else {
            super.onBackPressed(); //go back normally
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

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
            fragment = new AgencyFragment();
        } else if (id == R.id.nav_trips) {

        } else if (id == R.id.nav_exit) {
            exitApplication();
        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fragment_container,fragment);
        transaction.addToBackStack(null);
        transaction.commit();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void onListFragmentInteraction(Agency agency) {
        //DisplayMetrics metrics = getResources().getDisplayMetrics();

        //String message = Float.toString(metrics.density);

        Toast toast = Toast.makeText(this, agency.getName(), Toast.LENGTH_SHORT);
        toast.show();

        /*Intent intent = new Intent(this,DeviceBTCardActivity.class);
        intent.putExtra("DEVICE_NAME", item.name);
        intent.putExtra("DEVICE_ADDRESS",item.address);
        startActivity(intent);*/
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
}
