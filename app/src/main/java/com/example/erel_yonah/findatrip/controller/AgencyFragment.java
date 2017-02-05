package com.example.erel_yonah.findatrip.controller;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.text.Editable;
import android.text.Layout;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.ListView;

import com.example.erel_yonah.findatrip.R;
import com.example.erel_yonah.findatrip.model.backend.DSManagerFactory;
import com.example.erel_yonah.findatrip.model.entities.Agency;

import static android.support.v7.recyclerview.R.attr.layoutManager;
import static com.example.erel_yonah.findatrip.R.menu.main;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class AgencyFragment extends Fragment {

    //activity's search view
    private SearchView searchView;

    //fragment variables
    private static final String ARG_COLUMN_COUNT = "column-count";
    private int mColumnCount = 1;

    //listener. Usually the calling activity - look in the onAttach function
    private OnListFragmentInteractionListener mListener;
    private MyAgencyRecyclerViewAdapter adapter;

    //default empty constructor. required.
    public AgencyFragment() {
    }

    //search view setter
    public void setSearchView (SearchView sv) {searchView = sv;}

    //create a new instance of the fragment
    public static AgencyFragment newInstance(int columnCount) {
        AgencyFragment fragment = new AgencyFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_agency_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            recyclerView.setAdapter(new MyAgencyRecyclerViewAdapter(DSManagerFactory.getDSManager("List").getAgencies(), mListener));
        }
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        //setting his context as listener
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onStart() {
        super.onStart();

        //setting the activity title
        getActivity().setTitle("Businesses");

        //doing a down cast to the view and the adapter of the view to MyAgencyRecyclerViewAdapter and saves it.
        if (getView() instanceof RecyclerView) {
            if (((RecyclerView) getView()).getAdapter() instanceof MyAgencyRecyclerViewAdapter) {
                adapter = (MyAgencyRecyclerViewAdapter) ((((RecyclerView) getView()).getAdapter()));

                //listeners for the search view that preforms filtering
                searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextSubmit(String query) {
                        return false;
                    }

                    @Override
                    public boolean onQueryTextChange(String newText) {
                        adapter.getFilter().filter(newText);
                        return false;
                    }
                });
                searchView.setOnCloseListener(new SearchView.OnCloseListener() {
                    @Override
                    public boolean onClose() {
                        adapter.getFilter().filter(null);
                        return false;
                    }
                });
            }
        }
    }

    //the interface that the calling activity should implement
    public interface OnListFragmentInteractionListener {
        void onListFragmentInteraction(Agency agency);
    }
}
