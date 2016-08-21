package com.blackdroidstudios.finkelme;

import android.app.Fragment;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by Armando on 6/29/2015.
 */
public class LoginFragment extends Fragment
{
    protected MainActivity ma;

    //Login Parameters
    protected EditText usernameET;
    protected EditText passwordET;
    protected Button loginBTN;
    protected Button registerBTN;
    protected TextView forgetTV;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.loginfragment, container, false);

        ma = (MainActivity)getActivity();

        usernameET = (EditText) view.findViewById(R.id.login_usernameET);
        passwordET = (EditText)view.findViewById(R.id.login_passwordET);
        loginBTN = (Button)view.findViewById(R.id.login_loginBTN);
        loginBTN.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                //Login method pending
                ma.Login();
            }
        });
        registerBTN = (Button)view.findViewById(R.id.login_registerBTN);
        registerBTN.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                ma.changeFrag(1);
            }
        });

        forgetTV = (TextView)view.findViewById(R.id.login_forgetTV);
        forgetTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                ma.recoverPassword();
            }
        });

        return view;
    }


}
