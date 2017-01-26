package com.example.erel_yonah.findatrip.controller;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ListView;
import android.widget.TextView;

import com.example.erel_yonah.findatrip.R;
import com.example.erel_yonah.findatrip.model.backend.DSManagerFactory;
import com.example.erel_yonah.findatrip.model.entities.Trip;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class TripFragment extends Fragment {

    View rootView;
    ExpandableListView lv;
    private String[] groups;
    private String[] showedGroups;
    private ArrayList<Trip>[] _children;
    private ArrayList<Trip> allTrips;
    public SearchView searchView;
    private ExpandableListAdapter adapter;


    public TripFragment() {
    }

    public void setSearchView(SearchView sv) {searchView = sv;}

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

       allTrips = DSManagerFactory.getDSManager("List").getTrips();

        ArrayList<String> _groups = new ArrayList<>();
        for(int i = 0; i < allTrips.size(); i++)
            _groups.add(allTrips.get(i).getCountry());
        _groups = deleteDuplicates(_groups);

        groups = new String[_groups.size()];
        groups = _groups.toArray(groups);
        showedGroups = groups;

        _children = (ArrayList<Trip>[]) new ArrayList[groups.length];
        for(int i = 0; i < groups.length; i++)
            _children[i] = new ArrayList<>();

        for(int i = 0; i < allTrips.size(); i++)
            for(int j = 0; j < groups.length; j++)
                if (allTrips.get(i).getCountry().equals(groups[j])) {
                    _children[j].add(allTrips.get(i));
                }

        searchView = new SearchView(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_trip_list, container, false);

        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        lv = (ExpandableListView) view.findViewById(R.id.expListView);
        lv.setAdapter(new ExpandableListAdapter(showedGroups, _children));
        lv.setGroupIndicator(null);

        if (lv.getAdapter() instanceof ExpandableListAdapter) {
            adapter = (ExpandableListAdapter) lv.getAdapter();

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

    public class ExpandableListAdapter extends BaseExpandableListAdapter implements Filterable {

        private final LayoutInflater inf;
        private String[] groups;
        private ArrayList<Trip>[] children;

        public ExpandableListAdapter(String[] groups, ArrayList<Trip>[] children) {
            this.groups = groups;
            this.children = children;
            inf = LayoutInflater.from(getActivity());
        }

        @Override
        public int getGroupCount() {
            return groups.length;
        }

        @Override
        public int getChildrenCount(int groupPosition) {
            return children[groupPosition].size();
        }

        @Override
        public Object getGroup(int groupPosition) {
            return groups[groupPosition];
        }

        @Override
        public Object getChild(int groupPosition, int childPosition) {
            return children[groupPosition].get(childPosition);
        }

        @Override
        public long getGroupId(int groupPosition) {
            return groupPosition;
        }

        @Override
        public long getChildId(int groupPosition, int childPosition) {
            return childPosition;
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }

        @Override
        public View getChildView(int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

            ViewHolder holder;
            if (convertView == null) {
                convertView = inf.inflate(R.layout.fragment_trip_item, parent, false);
                holder = new ViewHolder();

                holder.text = (TextView) convertView.findViewById(R.id.lblListItem);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            holder.text.setText(getChild(groupPosition, childPosition).toString());

            return convertView;
        }

        @Override
        public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
            ViewHolder holder;

            if (convertView == null) {
                convertView = inf.inflate(R.layout.fragment_trip_group, parent, false);

                holder = new ViewHolder();
                holder.text = (TextView) convertView.findViewById(R.id.lblListHeader);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            holder.text.setText(getGroup(groupPosition).toString());

            return convertView;
        }

        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition) {
            return true;
        }

        @Override
        public Filter getFilter() {
            Filter filter = new Filter() {

                @SuppressWarnings("unchecked")
                @Override
                protected void publishResults(CharSequence constraint, FilterResults results) {
                    showedGroups = (String[]) results.values;
                    notifyDataSetChanged();
                }


                @Override
                protected FilterResults performFiltering(CharSequence constraint) {

                    FilterResults results = new FilterResults();
                    ArrayList<String> FilteredArray = new ArrayList<>();

                    // perform your search here using the searchConstraint String.

                    if (constraint == null || constraint.length() == 0) {
                        results.count = groups.length;
                        results.values = groups;
                    } else {
                        String cs = constraint.toString().toLowerCase();
                        for (int i = 0; i < showedGroups.length; i++) {
                            String tmp = showedGroups[i];
                            if (tmp.toLowerCase().startsWith(cs))  {
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

        private class ViewHolder {
            TextView text;
        }
    }

    public ArrayList<String> deleteDuplicates(ArrayList<String> strs) {
        ArrayList<String> result = new ArrayList<>();

        int end = strs.size();
        Set<String> set = new HashSet<>();

        for(int i = 0; i < end; i++){
            set.add(strs.get(i));
        }

        Iterator it = set.iterator();
        Object tmp;
        while(it.hasNext()) {
            tmp = it.next();
            if (tmp instanceof String)
                result.add((String)tmp);
        }

        return result;
    }
}