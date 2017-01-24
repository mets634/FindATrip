package com.example.erel_yonah.findatrip;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
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

import com.example.erel_yonah.findatrip.model.backend.DSManagerFactory;
import com.example.erel_yonah.findatrip.model.entities.Agency;

import static java.lang.System.exit;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, AgencyFragment.OnListFragmentInteractionListener {

    public FragmentManager fragmentManager = getSupportFragmentManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //open main fragment
        fragmentManager.beginTransaction().add(R.id.fragment_container,new MainFragment()).commit();

        //init database
        initializeDB();

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
            setTitle("Find Your Trip!");
        } else if (id == R.id.nav_businesses) {
            setTitle("Businesses");
        } else if (id == R.id.nav_trips) {
            setTitle("Trips");
        } else if (id == R.id.nav_exit) {
            exit(0);
        } else if (id == R.id.nav_share) {
            setTitle("Share");
        } else if (id == R.id.nav_send) {
            setTitle("Send");
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

    protected void initializeDB() {
        try {
            DSManagerFactory.getDSManager("List").update(this);
        }
        catch (Exception e) {
            Toast toast = Toast.makeText(this, "can't load data", Toast.LENGTH_SHORT);
            toast.show();
        }
    }
}
