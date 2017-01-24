package com.example.erel_yonah.findatrip.model.datasource;

import com.example.erel_yonah.findatrip.model.entities.Agency;
import com.example.erel_yonah.findatrip.model.entities.Trip;

import java.util.ArrayList;

/**
 * Created by yonah on 11/29/2016.
 */

/**
 * A class implementing a data source via lists.
 */
public class ListDS {
    // Data source management methods

    /**
     * @see Agency
     * @return Copy of business's in the data source.
     */
    public static ArrayList<Agency> cloneAgencyArrayList() {
        return (ArrayList<Agency>) agencyArrayList.clone();
    }

    /**
     * @see Trip
     * @return Copy of trips in the data source.
     */
    public static ArrayList<Trip> cloneTripArrayList() {
        return (ArrayList<Trip>) tripArrayList.clone();
    }

    /**
     * A method to update lists to new values.
     * @param agencies
     * @param trips
     */
    public static void update(ArrayList<Agency> agencies, ArrayList<Trip> trips) {
        agencyArrayList = agencies;
        tripArrayList = trips;
    }

    // Data source's lists

    private static ArrayList<Agency> agencyArrayList = new ArrayList<>();
    private static ArrayList<Trip> tripArrayList = new ArrayList<>();
}
