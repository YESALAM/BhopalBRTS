package com.seatech.bhopalbrts.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.seatech.bhopalbrts.R;
import com.seatech.bhopalbrts.datamodel.Stop;
import com.seatech.bhopalbrts.util.Util;

import java.util.List;

/**
 * Created by yesalam on 22-08-2015.
 */
public class RouteDetailAdapter extends ArrayAdapter<Stop> {
    Context context ;
    List<Stop> stopList ;


    public RouteDetailAdapter(Context context, List<Stop> stops){
        super(context, R.layout.list_item_route_detail,stops);
        this.context = context ;
        stopList = stops ;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(R.layout.list_item_route_detail, parent, false);
        TextView sh = (TextView) itemView.findViewById(R.id.stopholder);
        TextView flag = (TextView) itemView.findViewById(R.id.ord);
        TextView dh = (TextView) itemView.findViewById(R.id.distholder) ;
        ImageView icon = (ImageView) itemView.findViewById(R.id.stopicon);
        if(position == 0){
            flag.setText("Origin");
            icon.setVisibility(View.VISIBLE);
        } else if(position == stopList.size()-1){
            flag.setText("Destination");
            icon.setVisibility(View.VISIBLE);
        } else if(stopList.get(position).isJunction()){
            flag.setText("Change Bus Here");
            icon.setVisibility(View.VISIBLE);
        }  else {
            flag.setText("");
            icon.setVisibility(View.GONE);
        }

        sh.setText(stopList.get(position).getStop());
        dh.setText(Util.twoDigitPrecision(stopList.get(position).getDist())+" Km.");

        return itemView ;
    }
}

