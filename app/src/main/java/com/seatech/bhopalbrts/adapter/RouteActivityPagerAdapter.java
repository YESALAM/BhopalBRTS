package com.seatech.bhopalbrts.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.seatech.bhopalbrts.datamodel.Stop;
import com.seatech.bhopalbrts.fragments.RouteDetail;
import com.seatech.bhopalbrts.fragments.RouteMap;

import java.util.ArrayList;

/**
 * Created by yesalam on 22-08-2015.
 */
public class RouteActivityPagerAdapter extends FragmentPagerAdapter {
    ArrayList<Stop> stopList;


    public RouteActivityPagerAdapter(FragmentManager fm, ArrayList<Stop> stopList) {
        super(fm);
        this.stopList = stopList ;
    }



    @Override
    public CharSequence getPageTitle(int position) {
        if(position == 0){
            return "Route Detail" ;
        } else {
            return "Route Map" ;
        }
    }

    @Override
    public Fragment getItem(int position) {
        if(position == 0) {
            RouteDetail fragment = new RouteDetail() ;
            fragment.setStopList(stopList);
            return fragment ;
        } else {
            RouteMap fragment = new RouteMap();
            fragment.setStoplist(stopList);
            return fragment ;
        }

    }

    @Override
    public int getCount() {
        return 2;
    }
}
