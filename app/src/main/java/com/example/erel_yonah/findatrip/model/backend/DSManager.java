package com.example.erel_yonah.findatrip.model.backend;

/**
 * Created by yonah on 11/30/2016.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.example.erel_yonah.findatrip.model.entities.Agency;
import com.example.erel_yonah.findatrip.model.entities.Trip;

import java.util.ArrayList;

/**
 * An interface to implement a data
 * source manager.
 */
public interface DSManager {
    // Get data methods

    /**
     * @return A list containing all business's.
     */
    ArrayList<Agency> getAgencies();

    /**
     * @return A list containing all trips.
     */
    ArrayList<Trip> getTrips();

    /**
     * A method to update the datasource with newer data.
     */
    void update(Context c) throws Exception;
}
