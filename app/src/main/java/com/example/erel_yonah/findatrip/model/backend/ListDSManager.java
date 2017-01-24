package com.example.erel_yonah.findatrip.model.backend;

/**
 * Created by yonah on 11/30/2016.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.MatrixCursor;

import com.example.erel_yonah.findatrip.model.datasource.ListDS;
import com.example.erel_yonah.findatrip.model.datasource.TravelAgenciesContract;
import com.example.erel_yonah.findatrip.model.entities.Agency;
import com.example.erel_yonah.findatrip.model.entities.Trip;
import com.example.erel_yonah.findatrip.model.datasource.ListDS;
import com.example.erel_yonah.findatrip.model.datasource.TravelAgenciesContract;
import com.example.erel_yonah.findatrip.model.datasource.TravelAgenciesContract.AgencyEntry;
import com.example.erel_yonah.findatrip.model.datasource.TravelAgenciesContract.TripEntry;


import java.util.ArrayList;
import java.util.GregorianCalendar;

/**
 * A class implementing the data
 * source manager interface
 * using lists as it's data.
 * @see com.example.erel_yonah.findatrip.model.datasource.ListDS
 */
public class ListDSManager implements DSManager {
    // implementation of DSManager interface


    @Override
    public ArrayList<Agency> getAgencies() {
        return ListDS.cloneAgencyArrayList();
    }

    @Override
    public ArrayList<Trip> getTrips() {
        return ListDS.cloneTripArrayList();
    }

    @Override
    public void update(Context c) throws Exception {
        // get data from content provider

        // get agencies
        Cursor res1 = c.getContentResolver().query(
                TravelAgenciesContract.AgencyEntry.CONTENT_URI,
                null,
                null,
                null,
                null);

        // get trips
        Cursor res2 = c.getContentResolver().query(
                TravelAgenciesContract.AgencyEntry.CONTENT_URI,
                null,
                null,
                null,
                null);

        // convert cursors to lists
        ArrayList<Agency> agencies = TravelAgenciesContract.AgencyEntry.cursorToList(res1);
        ArrayList<Trip> trips = TravelAgenciesContract.TripEntry.cursorToList(res2);

        // commit update
        ListDS.update(agencies, trips);
    }
}
