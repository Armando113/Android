package com.blackdroidstudios.finkelme;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Armando on 7/12/2015.
 */
public class InboxFragment extends Fragment
{
    //Parameters
    protected MainActivity ma;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.inboxfragment, container, false);

        ma = (MainActivity)getActivity();

        return view;
    }
}
