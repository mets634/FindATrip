package com.example.erel_yonah.findatrip.model.services;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import com.example.erel_yonah.findatrip.model.entities.Address;

/**
 * Created by yonah on 1/24/17.
 */

/**
 * A class to give access to the
 * Waze api
 */
public class WazeAccess {
    private WazeAccess() {}

    public static String BASE_URI = "waze://?q=";

    public static String MARKET_URI = "market://details?id=com.waze";

    /**
     * Method to start the waze activity with
     * a given address as the search query.
     * @param c Current context.
     * @param a Address to query.
     */
    public static void openWaze(Context c, Address a) {
        try
        {
            String address = a.street + a.city + a.country;
            Intent intent = new Intent( Intent.ACTION_VIEW, Uri.parse( BASE_URI + address ) );
            c.startActivity( intent );
        }
        catch ( ActivityNotFoundException ex  )
        {
            Intent intent =
                    new Intent( Intent.ACTION_VIEW, Uri.parse( MARKET_URI ) );
            c.startActivity(intent);
        }
    }
}
