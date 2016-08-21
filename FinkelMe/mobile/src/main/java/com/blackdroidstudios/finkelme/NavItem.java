package com.blackdroidstudios.finkelme;

import android.view.View;
import android.widget.ImageView;
import android.widget.TableRow;
import android.widget.TextView;

/**
 * Created by Armando on 7/5/2015.
 */
public class NavItem
{
    protected MainActivity ma;
    protected TableRow menuRow;
    protected ImageView menuIcon;
    protected TextView menuText;
    protected int fragIndex;

    public NavItem(MainActivity newMA, int newRow, int newIV, int newTV, int newIndex)
    {
        ma = newMA;
        menuRow = (TableRow)ma.findViewById(newRow);
        menuIcon = (ImageView)ma.findViewById(newIV);
        menuText = (TextView)ma.findViewById(newTV);
        fragIndex = newIndex;
        menuRow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeFragment(fragIndex);
            }
        });
    }

    protected void changeFragment(int newIndex)
    {
        ma.changeFrag(newIndex);
    }

    public void selectItem()
    {
        menuIcon.setColorFilter(ma.getResources().getColor(R.color.bright_green));
        menuText.setTextColor(ma.getResources().getColor(R.color.bright_green));
    }

    public void deselectItem()
    {
        menuIcon.setColorFilter(ma.getResources().getColor(R.color.light_grey));
        menuText.setTextColor(ma.getResources().getColor(R.color.light_grey));
    }




}
