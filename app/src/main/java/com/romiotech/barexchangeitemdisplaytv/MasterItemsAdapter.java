package com.romiotech.barexchangeitemdisplaytv;

import android.app.Activity;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class MasterItemsAdapter extends RecyclerView.Adapter<MasterItemsAdapter.MyViewHolder> {

    private List<Component> masterDrinksList;

    private Activity activity;
    private ArrayList<HashMap<String, String>> data;

    View view;

    DecimalFormat df = new DecimalFormat("#,##0.00");
    Date Start_time,Current_Time,One_To_Four_Time;

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView drinkCategoryTV, drinkItemNameTV, lowTV, highTV, priceTV;
        public ImageView lowHighIV;
        public RelativeLayout relativeLayout1, relativeLayout2;

        public MyViewHolder(View view) {
            super(view);

            drinkCategoryTV = view.findViewById(R.id.drinkCategoryTV);
            drinkItemNameTV = view.findViewById(R.id.drinkItemNameTV);
            lowTV = view.findViewById(R.id.lowTV);
            highTV = view.findViewById(R.id.highTV);
            priceTV = view.findViewById(R.id.priceTV);

            lowHighIV = view.findViewById(R.id.lowHighIV);

            relativeLayout1 = view.findViewById(R.id.relativeLayout1);
            relativeLayout2 = view.findViewById(R.id.relativeLayout2);
        }
    }

    public MasterItemsAdapter(List<Component> masterDrinksList) {

        this.masterDrinksList = masterDrinksList;
    }

    public MasterItemsAdapter(Activity activity, ArrayList<HashMap<String, String>> data) {

        this.activity = activity;
        this.data = data;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.row_vertical, viewGroup, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {

        Component component = masterDrinksList.get(i);

        myViewHolder.relativeLayout1.setVisibility(View.GONE);
        myViewHolder.relativeLayout2.setVisibility(View.VISIBLE);

        myViewHolder.drinkItemNameTV.setVisibility(View.VISIBLE);
        myViewHolder.lowTV.setVisibility(View.VISIBLE);
        myViewHolder.highTV.setVisibility(View.VISIBLE);
        myViewHolder.priceTV.setVisibility(View.VISIBLE);
        myViewHolder.lowHighIV.setVisibility(View.VISIBLE);

        myViewHolder.drinkItemNameTV.setText(component.getDrinkItemName());
        System.out.println("Drink Item Name: " + component.getDrinkItemName());

        System.out.println("Drink Item Current Rate: " + component.getDrinkItemCurrentRate());

        myViewHolder.lowTV.setText(df.format(Double.parseDouble(component.getDrinkItemMinRate())));
        myViewHolder.highTV.setText(df.format(Double.parseDouble(component.getDrinkItemMaxRate())));
        myViewHolder.priceTV.setText(df.format(Double.parseDouble(component.getDrinkItemCurrentRate())));

        if (i % 2 == 1) {
            myViewHolder.relativeLayout2.setBackgroundColor(Color.parseColor("#000000"));
            //myViewHolder.relativeLayout2.setBackgroundColor(Color.parseColor("#111745"));
            //myViewHolder.lowHighIV.setBackgroundResource(R.drawable.red_arrow_50);

        } else {
            myViewHolder.relativeLayout2.setBackgroundColor(Color.parseColor("#000000"));
            //myViewHolder.relativeLayout2.setBackgroundColor(Color.parseColor("#153612"));
            //myViewHolder.lowHighIV.setBackgroundResource(R.drawable.green_arrow_50);
        }


        String patternLO = "HH:mm:ss";
        SimpleDateFormat sdfLO = new SimpleDateFormat(patternLO);

        try {
            Start_time =  sdfLO.parse(component.getDrinkItemstarttime());
        } catch (ParseException e1) {
            e1.printStackTrace();
        }

        try {
            Current_Time = sdfLO.parse(component.getDrinkItemcurrenttime());
        } catch (ParseException e1) {
            e1.printStackTrace();
        }


        try {
            One_To_Four_Time = sdfLO.parse("06:00:00");
        } catch (ParseException e1) {
            e1.printStackTrace();
        }


        if (Current_Time.after(Start_time) || Current_Time.equals(Start_time) || Current_Time.before(One_To_Four_Time)) {

            if (Double.parseDouble(component.getDrinkItemCurrentRate()) > Double.parseDouble(component.getDrinkItemLastRate())) {

                myViewHolder.priceTV.setTextColor(Color.parseColor("#00FF00"));
                myViewHolder.lowHighIV.setBackgroundResource(R.drawable.green_arrow_50);
            } else if (Double.parseDouble(component.getDrinkItemCurrentRate()) <= Double.parseDouble(component.getDrinkItemLastRate())) {

                myViewHolder.priceTV.setTextColor(Color.parseColor("#FF0000"));
                myViewHolder.lowHighIV.setBackgroundResource(R.drawable.red_arrow_50);
            }

        }

        else {


            myViewHolder.priceTV.setTextColor(Color.parseColor("#00FF00"));
            myViewHolder.lowHighIV.setBackgroundResource(R.drawable.green_arrow_50);
        }

    }

    @Override
    public int getItemCount() {
        return masterDrinksList.size();
    }
}
