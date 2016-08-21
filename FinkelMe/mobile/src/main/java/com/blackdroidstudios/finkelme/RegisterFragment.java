package com.blackdroidstudios.finkelme;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by Armando on 6/30/2015.
 */
public class RegisterFragment extends Fragment
{
    //Register Fragment parameters
    protected MainActivity ma;

    protected EditText emailET;
    protected EditText passwordET;
    protected EditText repasswordET;
    protected TextView eulaTV;
    protected CheckBox eulaCBX;
    protected Button confirmBTN;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.registerfragment, container, false);

        ma = (MainActivity)getActivity();

        //Get parameters
        emailET = (EditText)view.findViewById(R.id.register_emailET);
        passwordET = (EditText)view.findViewById(R.id.register_passwordET);
        repasswordET = (EditText)view.findViewById(R.id.register_repassET);
        eulaTV = (TextView)view.findViewById(R.id.register_eulaTV);
        eulaCBX = (CheckBox)view.findViewById(R.id.register_eulaCBX);
        confirmBTN = (Button)view.findViewById(R.id.register_confirmBTN);
        confirmBTN.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                ma.registerNewUser();
            }
        });

        return view;
    }
}
