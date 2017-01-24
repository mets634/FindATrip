package com.example.erel_yonah.findatrip.model.backend;

/**
 * Created by yonah on 11/30/2016.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.net.Uri;
import android.os.AsyncTask;

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
import java.util.concurrent.Executors;

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

        Cursor res1 = runAsyncTask(
                new CPAsyncTask(c, AgencyEntry.CONTENT_URI)
        );

        Cursor res2 = runAsyncTask(
                new CPAsyncTask(c, TripEntry.CONTENT_URI)
        );

        // convert cursors to lists
        ArrayList<Agency> agencies = TravelAgenciesContract.AgencyEntry.cursorToList(res1);
        ArrayList<Trip> trips = TravelAgenciesContract.TripEntry.cursorToList(res2);

        // commit update
        ListDS.update(agencies, trips);
    }

    /**
     * A method to run the asynctask
     * and when finished to return result.
     * @param cp The asynctask
     * @return Cursor containing result.
     */
    private static Cursor runAsyncTask(CPAsyncTask cp) {
        cp.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

        while (!cp.isSet); // not ready ... keep waiting
        return cp.getResult();
    }

    /**
     * A class to access content provider with asynctask.
     */
    class CPAsyncTask extends AsyncTask<Void, Void, Void> {
        public CPAsyncTask(Context c, Uri url) {
            this.c = c;
            this.url = url;

            isSet = false;
        }

        // data used in asynctask
        private Uri url;
        private Context c;

        // results
        private Cursor res;
        private boolean isSet;

        public Cursor getResult() {
            return res;
        }


        @Override
        protected Void doInBackground(Void... params) {
            // get data
            try {
                res = c.getContentResolver().acquireContentProviderClient(url).query(
                        url,
                        null,
                        null,
                        null,
                        null);
            }
            catch (Exception e) {

            }
            finally {
                isSet = true;
            }
            return null;
        }
    }
}
