package com.example.erel_yonah.findatrip.model.entities;

/**
 * Created by אראל on 23 ינואר 2017.
 */

import com.example.erel_yonah.findatrip.model.datasource.TravelAgenciesContract.AgencyEntry;

/**
 * A class to represent an address
 * by its country, city, and street name.
 */
public class Address {
    /**
     * Class Address's constructor.
     * @param country
     * @param city
     * @param street
     */
    public Address(String country, String city, String street) {
        this.country = country;
        this.city = city;
        this.street = street;
    }

    @Override
    public String toString() {
        return "Address{" +
                "country='" + country + '\'' +
                ", city='" + city + '\'' +
                ", street='" + street + '\'' +
                '}';
    }

    public final String country;
    public final String city;
    public final String street;

}
