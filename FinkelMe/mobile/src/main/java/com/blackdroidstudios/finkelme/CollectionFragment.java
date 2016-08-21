package com.blackdroidstudios.finkelme;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Armando on 7/5/2015.
 */
public class CollectionFragment extends Fragment
{
    //Fragment Parameters
    protected MainActivity ma;
    protected RecyclerView collectionRV;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.collectionfragment, container, false);

        ma = (MainActivity)getActivity();

        return view;
    }

}
