package com.blackdroidstudios.finkelme;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Armando on 7/13/2015.
 */
public class SearchFragment extends Fragment
{
    //Fragment Properties
    private MainActivity ma;
    private String mySearchQuery;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.searchfragment, container, false);

        ma = (MainActivity)getActivity();
        mySearchQuery = ma.searchQuery;

        return view;
    }

    public void setSearchQuery(String newQuery)
    {
        mySearchQuery = newQuery;
    }
}
