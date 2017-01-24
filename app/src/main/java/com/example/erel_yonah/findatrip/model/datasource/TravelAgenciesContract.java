package com.example.erel_yonah.findatrip.model.datasource;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;

import com.example.erel_yonah.findatrip.model.entities.Address;
import com.example.erel_yonah.findatrip.model.entities.Agency;
import com.example.erel_yonah.findatrip.model.entities.Trip;
import com.example.erel_yonah.findatrip.model.entities.TripType;

import java.util.ArrayList;
import java.util.GregorianCalendar;

/**
 * Created by אראל on 23 ינואר 2017.
 */

/**
 * A contract class for TravelAgenciesProvider.
 * Any action done on content provider not
 * according to the contract will result in
 * either an exception or null will be returned.
 *
 * <h1><u>HOW TO USE EXAMPLES</u></h1>
 * <p><h3>Inserting an agency:</h3>
 * <code>
 *     // create ContentValues with agency data <br>
 *     ContentValues cv = AgencyEntry.createContentValues(<br>
 *     1, "my name", "my country", "my city", "my street", "2222222", "myemail@mydomain.com", "mywebsite.com"<br>
 *     );
 *      <br>
 *     Uri uri = getContentResolver().insert(AgencyEntry.CONTENT_URI, cv); // execute insert<br><br>
 * </code>
 *
 * Variable uri will hold in it's ID either 0 or 1. 0 meaning failure, and 1 meaning successful.</p>
 *
 * <p><h3>Receiving a list of all agencies:</h3>
 * <code>
 *     // query the datasource for agencies <br>
 *     Cursor c = getContentResolver().query(AgencyEntry.CONTENT_URI, null, null, null, null);<br>
 *     ArrayList<Agency> list = AgencyEntry.cursorToList(c); // convert cursor to list <br><br>
 * </code>
 *
 * Be aware that method cursorToList throws exception.</p>
 *
 * @see AgencyEntry
 * @see TripEntry
 */
public final class TravelAgenciesContract {
    private TravelAgenciesContract() {}

    public static final String CONTENT_AUTHORITY = "com.example.java5777.travelagencies.TravelAgenciesProvider";

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final String PERMISSIONS = "com.example.java5777.travelagencies.permissions.ACCESS_DATA";

    public static final String PATH_AGENCY = "agency";
    public static final String PATH_TRIP = "trip";
    public static final String PATH_HASBEENUPDATED = "hasbeenupdated";


    // codes returned by content provider

    public static final int CODE_SUCCESS = 0;
    public static final int CODE_ERROR = 10; // general unknown error
    public static final int CODE_USER_EXISTS = 20;
    public static final int CODE_AGENCY_EXISTS = 30;
    public static final int CODE_AGENCY_NOT_FOUND = 31;




    /**
     * A class containing data needed to access the path for
     * HasBeenUpdated in content provider. Once cursor has been
     * returned, you may use the methods here to help you
     * seek the data you need from it.
     */
    public static final class HasBeenUpdatedEntry implements BaseColumns {
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_HASBEENUPDATED).build();

        public static final String COLUMN_HASBEENUPDATED = "hasBeenUpdated";

        public static final String[] COLUMNS = {
                COLUMN_HASBEENUPDATED
        };

        // Methods to help out once cursor has been returned

        public static boolean hasBeenUpdated(Cursor cur) {
            if (cur == null)
                return false;

            cur.moveToFirst(); // move cursor to start
            return cur.getInt( cur.getColumnIndex( HasBeenUpdatedEntry.COLUMN_HASBEENUPDATED ) ) > 0;
        }
    }

    /**
     * A class containing data used to access
     * the content provider for Agency-type data.
     *
     * There are two operation that can be done using this class:<br>
     * 1) Inserting a new agency.<br>
     * 2) Receiving a list of agencies. NOTE: Will contain all of the agencies.<br>
     *
     * <h1>Inserting a new agency:</h1>
     * <p>To insert a new agency you must call the
     * content provider's 'insert' method with two parameters:<br>
     * 1) <b>AgencyEntry.CONTENT_URI</b>.<br>
     * 2) A ContentValues object containing the correct data.
     * Can be created using AgencyEntry.createContentValues.<br><br>
     *
     * If successful a URI with id=1 will be returned.
     * Otherwise id=0 will be returned.</p>
     *
     * <h1>Receiving a list of agencies:</h1>
     * <p>To receive a list of agencies you must call
     * the content provider's 'query' method with every parameter
     * being null other than the URI which should be: <b>AgencyEntry.CONTENT_URI</b>.<br><br>
     * Use AgencyEntry.cursorToList to convert returned cursor
     * to list of agencies.</p>
     */
    public static final class AgencyEntry implements BaseColumns {
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_AGENCY).build();

        public static final String COLUMN_ID = "ID";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_COUNTRY = "country";
        public static final String COLUMN_CITY = "city";
        public static final String COLUMN_STREET = "street";
        public static final String COLUMN_PHONENUMBER = "phoneNumber";
        public static final String COLUMN_EMAIL = "email";
        public static final String COLUMN_WEBSITE = "website";

        public static final String[] COLUMNS = {
                COLUMN_ID,
                COLUMN_NAME,
                COLUMN_COUNTRY,
                COLUMN_CITY,
                COLUMN_STREET,
                COLUMN_PHONENUMBER,
                COLUMN_EMAIL,
                COLUMN_WEBSITE
        };

        /**
         * A method to convert cursor to list of agencies.
         * @param agencies Cursor.
         * @return List of agencies from cursor.
         * @throws Exception
         */
        public static ArrayList<Agency> cursorToList(Cursor agencies) throws Exception {
            if (agencies == null || agencies.getCount() == 0)
                return new ArrayList<>();

            ArrayList<Agency> res = new ArrayList<>();

            agencies.moveToFirst();

            do { // iterate through each result

                // retrieve agency data
                long ID = agencies.getLong( agencies.getColumnIndex( COLUMN_ID ) );
                String name = agencies.getString( agencies.getColumnIndex( COLUMN_NAME ) );
                String country = agencies.getString( agencies.getColumnIndex( COLUMN_COUNTRY ) );
                String city = agencies.getString( agencies.getColumnIndex( COLUMN_CITY ) );
                String street = agencies.getString( agencies.getColumnIndex( COLUMN_STREET ) );
                String phonenumber = agencies.getString( agencies.getColumnIndex( COLUMN_PHONENUMBER ) );
                String email = agencies.getString( agencies.getColumnIndex( COLUMN_EMAIL ) );
                String website = agencies.getString( agencies.getColumnIndex( COLUMN_WEBSITE ) );

                // create new agency and add it to list
                res.add(new Agency(ID, name, email, phonenumber, new Address(country, city, street), website));
            } while (agencies.moveToNext());

            return res;
        }

        /**
         * A method to convert Agency values to
         * Android ContentValues.
         * @param ID
         * @param name
         * @param country
         * @param city
         * @param street
         * @param phoneNumber
         * @param email
         * @param website
         * @return ContentValues containing values.
         */
        public static ContentValues createContentValues(long ID, String name, String country, String city, String street, String phoneNumber, String email, String website) {
            ContentValues res = new ContentValues();

            res.put(COLUMN_ID, ID);
            res.put(COLUMN_NAME, name);
            res.put(COLUMN_COUNTRY, country);
            res.put(COLUMN_CITY, city);
            res.put(COLUMN_STREET, street);
            res.put(COLUMN_PHONENUMBER, phoneNumber);
            res.put(COLUMN_EMAIL, email);
            res.put(COLUMN_WEBSITE, website);

            return res;
        }
    }


    /**
     * A class containing data used to access
     * the content provider for Trip-type data.
     *
     * There are two operation that can be done using this class:<br>
     * 1) Inserting a new trip.<br>
     * 2) Receiving a list of trip. NOTE: Will contain all of the agencies.<br>
     *
     * <h1>Inserting a new trip:</h1>
     * <p>To insert a new trip you must call the
     * content provider's 'insert' method with two parameters:<br>
     * 1) <b>TripEntry.CONTENT_URI</b>.<br>
     * 2) A ContentValues object containing the correct data.
     * Can be created using TripEntry.createContentValues.<br><br>
     *
     * If successful a URI with id=1 will be returned.
     * Otherwise id=0 will be returned.</p><br>
     *
     * <h1>Receiving a list of trips:</h1>
     * <p>To receive a list of trips you must call
     * the content provider's 'query' method with every parameter
     * being null other than the URI which should be: <b>TripEntry.CONTENT_URI</b>.<br>
     * Use TripEntry.cursorToList to convert returned cursor
     * to List of trips.</p>
     */
    public static final class TripEntry implements  BaseColumns {
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_TRIP).build();

        public static final String COLUMN_TYPE = "tripType";
        public static final String COLUMN_COUNTRY = "country";
        public static final String COLUMN_START = "startDate";
        public static final String COLUMN_END = "endDate";
        public static final String COLUMN_PRICE = "price";
        public static final String COLUMN_DESCRIPTION = "description";
        public static final String COLUMN_AGENCYID = "agencyID";

        public static final String[] COLUMNS = {
                COLUMN_TYPE,
                COLUMN_COUNTRY,
                COLUMN_START,
                COLUMN_END,
                COLUMN_PRICE,
                COLUMN_DESCRIPTION,
                COLUMN_AGENCYID
        };

        /**
         * A method to convert a cursor to a list
         * of trips stored in it.
         * @param trips Cursor.
         * @return ArrayList containing result.
         * @throws Exception
         */
        public static ArrayList<Trip> cursorToList(Cursor trips) throws Exception {
            if (trips == null || trips.getCount() == 0)
                return new ArrayList<>();

            ArrayList<Trip> res = new ArrayList<>();

            trips.moveToFirst();

            do { // iterate through each result

                // retrieve trip data
                TripType type = TripType.valueOf( trips.getString( trips.getColumnIndex( COLUMN_TYPE ) ) );
                String country = trips.getString( trips.getColumnIndex( COLUMN_COUNTRY ) );

                GregorianCalendar start = new GregorianCalendar();
                start.setTimeInMillis( trips.getLong( trips.getColumnIndex( COLUMN_START ) ) );

                GregorianCalendar end = new GregorianCalendar();
                end.setTimeInMillis( trips.getLong( trips.getColumnIndex( COLUMN_END ) )); ;

                Integer price = trips.getInt( trips.getColumnIndex( COLUMN_PRICE ) );
                String description = trips.getString( trips.getColumnIndex( COLUMN_DESCRIPTION ) );
                long agencyID = trips.getLong( trips.getColumnIndex( COLUMN_AGENCYID ) );


                // create new trip and add it to list
                res.add(new Trip(type, country, start, end, price, description, agencyID));
            } while (trips.moveToNext());

            return res;
        }


        /**
         * A method to convert Trip type values
         * to Android ContentValues.
         * @param type
         * @param country
         * @param start
         * @param end
         * @param price
         * @param description
         * @param agencyID
         * @return A ContentValues conatining all the values.
         */
        public static ContentValues createContentValues(TripType type, String country, GregorianCalendar start, GregorianCalendar end, Integer price, String description, long agencyID) {
            ContentValues res = new ContentValues();

            res.put(COLUMN_TYPE, type.toString());
            res.put(COLUMN_COUNTRY, country);
            res.put(COLUMN_START, start.getTimeInMillis());
            res.put(COLUMN_END, end.getTimeInMillis());
            res.put(COLUMN_PRICE, price);
            res.put(COLUMN_DESCRIPTION, description);
            res.put(COLUMN_AGENCYID, agencyID);

            return res;
        }
    }
}
