package com.example.erel_yonah.findatrip.model.backend;

import java.util.HashMap;

/**
 * Created by yonah on 11/30/2016.
 */

/**
 * A class implementing the factory pattern for the
 * interface DSManager.
 * @see DSManager
 */
public class DSManagerFactory {
    /**
     * Variable holding an instance of each type
     * of data source manager.
     */
    private static HashMap<String, DSManager> dsManagers;

    public final static String LIST = "List";

    /**
     * Initializing the dsManagers variable.
     */
    static {
        dsManagers = new HashMap<>();

        dsManagers.put(LIST, new ListDSManager());
    }


    /**
     * Factory method to return the correct type of
     * DSManager given in the parameter.
     * @param dsManagerType Type of DSManager to select.
     * @return The correct DSManager.
     */
    public static DSManager getDSManager(String dsManagerType) {
        if (!dsManagers.containsKey(dsManagerType)) // unrecognized DSManager type
            throw new IllegalArgumentException("Unrecognized DSManager type");

        return dsManagers.get(dsManagerType); // return matching DSManager
    }
}
