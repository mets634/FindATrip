package com.example.erel_yonah.findatrip.controller;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ListView;
import android.widget.TextView;

import com.example.erel_yonah.findatrip.controller.AgencyFragment.OnListFragmentInteractionListener;
import com.example.erel_yonah.findatrip.R;
import com.example.erel_yonah.findatrip.model.entities.Agency;

import java.util.ArrayList;
import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link Agency} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 */
public class MyAgencyRecyclerViewAdapter extends RecyclerView.Adapter<MyAgencyRecyclerViewAdapter.ViewHolder> implements Filterable {

    //all items in the list that should be displayed
    private final ArrayList<Agency> mValues;

    //the items that are really showed, after filtering
    private ArrayList<Agency> dValues;

    //adapter listener. In this app it'll be the calling fragment listener.
    private final OnListFragmentInteractionListener mListener;

    //public constructor
    public MyAgencyRecyclerViewAdapter(ArrayList<Agency> items, OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
        dValues = mValues;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_agency, parent, false);
        return new ViewHolder(view);
    }

    //set content to the view holder items
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        //set content of the items
        holder.mItem = dValues.get(position);
        holder.mIdView.setText(Long.toString(holder.mItem.getID()));
        holder.mNameView.setText(holder.mItem.getName());
        holder.mAddressView.setText(holder.mItem.getAddress().getAddress());
        holder.mEmailView.setText(holder.mItem.getEmail());

        //set the on view click listener
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) mListener.onListFragmentInteraction(holder.mItem);
            }
        });
    }

    @Override
    public int getItemCount() {
        return dValues.size();
    }

    //a view holder inner class contains all the view grids
    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mIdView;
        public final TextView mNameView;
        public final TextView mAddressView;
        public final TextView mEmailView;
        public Agency mItem;

        //public constructor that initialize the view grids from the view
        public ViewHolder(View view) {
            super(view);
            mView = view;
            mNameView = (TextView) view.findViewById(R.id.name);
            mIdView = (TextView) view.findViewById(R.id.id);
            mAddressView = (TextView) view.findViewById(R.id.address);
            mEmailView = (TextView) view.findViewById(R.id.email);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mNameView.getText() + "'";
        }
    }

    public Filter getFilter() {

        Filter filter = new Filter() {

            @SuppressWarnings("unchecked")
            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                dValues = (ArrayList<Agency>) results.values;
                notifyDataSetChanged();
            }


            @Override
            protected FilterResults performFiltering(CharSequence constraint) {

                FilterResults results = new FilterResults();
                ArrayList<Agency> FilteredArray = new ArrayList<Agency>();


                if (constraint == null || constraint.length() == 0) {
                    results.count = mValues.size();
                    results.values = mValues;
                } else {
                    String cs = constraint.toString().toLowerCase();
                    for (int i = 0; i < mValues.size(); i++) {
                        Agency tmp = mValues.get(i);
                        if (tmp.getName().toLowerCase().startsWith(cs))  {
                            FilteredArray.add(tmp);
                        }
                    }

                    results.count = FilteredArray.size();
                    results.values = FilteredArray;
                }

                return results;
            }
        };

        return filter;
    }
}
