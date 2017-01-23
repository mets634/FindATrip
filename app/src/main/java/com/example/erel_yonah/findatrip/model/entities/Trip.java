package com.example.erel_yonah.findatrip.model.entities;

/**
 * Created by אראל on 23 ינואר 2017.
 */

import java.util.GregorianCalendar;

import com.example.erel_yonah.findatrip.model.datasource.TravelAgenciesContract.TripEntry;

/**
 * A class to represent a trip on the application.
 */
public class Trip {
    /**
     * The constructor for class Trip.
     * @param type Type of trip.
     * @param country Country which trip takes place in.
     * @param start Starting date.
     * @param end Ending date.
     * @param price Trip price.
     * @param description Trip description.
     * @param agencyID ID of business sponsoring the trip.
     */
    public Trip(TripType type, String country, GregorianCalendar start, GregorianCalendar end, Integer price, String description, long agencyID) {
        this.type = type;
        this.country = country;
        this.start = start;
        this.end = end;
        setPrice(price);
        this.description = description;
        this.agencyID = agencyID;
    }

    @Override
    public String toString() {
        return "Trip{" +
                "type=" + type +
                ", country='" + country + '\'' +
                ", start=" + ((Long) start.getTimeInMillis()).toString() +
                ", end=" + ((Long) end.getTimeInMillis()).toString() +
                ", price=" + price +
                ", description='" + description + '\'' +
                ", agencyID='" + agencyID + '\'' +
                '}';
    }




    // Getters and setters

    public TripType getType() {
        return type;
    }

    public void setType(TripType type) {
        this.type = type;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public GregorianCalendar getStart() {
        return start;
    }

    public void setStart(GregorianCalendar start) {
        this.start = start;
    }

    public GregorianCalendar getEnd() {
        return end;
    }

    public void setEnd(GregorianCalendar end) {
        this.end = end;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        if (price < 0)
            throw new IllegalArgumentException("Price must be positive");
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getAgencyID() {
        return agencyID;
    }

    public void setAgencyID(long agencyID) {
        this.agencyID = agencyID;
    }


    // Attributes

    private TripType type;
    private String country;
    private GregorianCalendar start;
    private GregorianCalendar end;
    private Integer price;
    private String description;
    private long agencyID;
}
