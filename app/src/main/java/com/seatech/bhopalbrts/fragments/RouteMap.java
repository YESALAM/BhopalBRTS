package com.seatech.bhopalbrts.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.seatech.bhopalbrts.R;
import com.seatech.bhopalbrts.datamodel.Stop;

import java.util.ArrayList;

/**
 * Created by yesalam on 22-08-2015.
 */
public class RouteMap extends Fragment{
    private final String LOG_TAG = RouteMap.class.getSimpleName() ;
    MapView mapView;
    GoogleMap map;
    ArrayList<Stop> stoplist;
    MarkerOptions[] optionses;
    PolylineOptions[] polylineOptionses;
    Marker[] markers ;


    int junction_stop = 0 ;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    public RouteMap(){}
    public void setStoplist(ArrayList<Stop> stoplist){
        this.stoplist = stoplist ;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_card_detail,menu);
    }

    @Override
    public void onStart() {
        super.onStart();
        map.getUiSettings().setMyLocationButtonEnabled(true);
        map.setMyLocationEnabled(true);

        // Needs to call MapsInitializer before doing any CameraUpdateFactory calls
        MapsInitializer.initialize(this.getActivity());
        initializeMarker();
        initializePolyline();
        // Updates the location and zoom of the MapView
        int mid = (stoplist.size())/2 ;
        LatLng bhopal = new LatLng(stoplist.get(mid).getLattitude(), stoplist.get(mid).getLongitude());
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(bhopal, 12);
        map.animateCamera(cameraUpdate);


        for(int i=0 ;i<stoplist.size();i++){
            markers[i] =  map.addMarker(optionses[i]);
            if(i==0) markers[i].showInfoWindow();
            if(junction_stop > 0 && i==junction_stop){
                markers[i].showInfoWindow();
            }

        }

        for(int j=0;j< polylineOptionses.length;j++){
            Polyline line = map.addPolyline(polylineOptionses[j]) ;
            line.setColor(Color.GREEN);
            line.setWidth(15);
            if(junction_stop > 0){
                junction_stop = 0 ;
            } else {
                line.setColor(Color.BLUE);
                break;
            }
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_route_map,container,false);

        // Gets the MapView from the XML layout and creates it
        mapView = (MapView) v.findViewById(R.id.mapview);
        mapView.onCreate(savedInstanceState);

        // Gets to GoogleMap from the MapView and does initialization stuff
        map = mapView.getMap();

        Log.e(LOG_TAG, getTag());

        return v;
    }

    private void initializeMarker(){
        optionses = new MarkerOptions[stoplist.size()];
        markers = new Marker[stoplist.size()] ;
        int i=0;
        for(Stop stop:stoplist){
            Log.e(stop.getStop(),stop.getLattitude()+" : "+ stop.getLongitude());
            optionses[i] = new MarkerOptions();
            if(i%2 == 0){
                optionses[i].title(stop.getStop())
                        .position(new LatLng(stop.getLattitude(),stop.getLongitude())).icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_place_white_24dp)) ;
            } else{
                optionses[i].title(stop.getStop())
                        .position(new LatLng(stop.getLattitude(),stop.getLongitude())).icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_place_white_24dp)) ;
            }
            if(i==0) optionses[i].snippet("Origin of journey").icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_directions_bus_black_36dp));
            if(stop.isJunction()) {
                optionses[i].snippet("Change your buse here").icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_directions_bus_black_36dp)) ;
                junction_stop = i ;
            }
            if(i==stoplist.size()-1) optionses[i].snippet("Destination of your journey").icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_directions_bus_black_36dp));

            i++ ;
        }

    }

    private void initializePolyline(){
        polylineOptionses = new PolylineOptions[2] ;
        int i=0;
        boolean haveJunction = false ;
        polylineOptionses[0] = new PolylineOptions();
        for(Stop stop:stoplist){
            polylineOptionses[0].add(new LatLng(stop.getLattitude(), stop.getLongitude())) ;
            if(stop.isJunction()) {
                haveJunction = true;
                break;
            }
            i++ ;
        }
        if(haveJunction){
            junction_stop = i ;
            polylineOptionses[1] = new PolylineOptions();
            for(int j=i;j<stoplist.size();j++){
                Stop stop = stoplist.get(j);
                polylineOptionses[1].add(new LatLng(stop.getLattitude(), stop.getLongitude()));
            }
        }
    }

    @Override
    public void onResume() {
        mapView.onResume();
        super.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }


    public void showInfoWindow(int position){
        markers[position].showInfoWindow();
        LatLng bhopal = markers[position].getPosition();
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(bhopal, 15);
        map.animateCamera(cameraUpdate);
    }



}
