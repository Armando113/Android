package com.blackdroidstudios.finkelme;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Armando on 7/4/2015.
 */
public class ProfileFragment extends Fragment
{
    //Fragment Parameters
    protected MainActivity ma;
    protected RecyclerView myCardsRV;
    protected TextView nameTV;
    protected TextView emailTV;
    protected TextView companyTV;
    protected TextView addressTV;
    protected TextView phoneTV;
    protected TextView professionTV;
    protected TextView websiteTV;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.profilefragment, container, false);

        //Get Main Activity
        ma = (MainActivity)getActivity();

        //Get the profile card parameters
        nameTV = (TextView)view.findViewById(R.id.profile_nameTV);
        addressTV = (TextView)view.findViewById(R.id.profile_addressTV);
        emailTV = (TextView)view.findViewById(R.id.profile_emailTV);
        phoneTV = (TextView)view.findViewById(R.id.profile_phoneTV);
        companyTV = (TextView)view.findViewById(R.id.profile_companyTV);
        professionTV = (TextView)view.findViewById(R.id.profile_professionTV);
        websiteTV = (TextView)view.findViewById(R.id.profile_websiteTV);

        return view;
    }
}
