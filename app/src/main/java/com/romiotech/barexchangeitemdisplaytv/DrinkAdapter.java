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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DrinkAdapter extends RecyclerView.Adapter<DrinkAdapter.MyViewHolder> {

    private List<Component> drinksList;

    private Activity activity;
    private ArrayList<HashMap<String, String>> data;

    View view;

    DecimalFormat df = new DecimalFormat("#,##0.00");


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

    public DrinkAdapter(List<Component> drinksList) {

        this.drinksList = drinksList;
    }

    public DrinkAdapter(Activity activity, ArrayList<HashMap<String, String>> data) {

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

        Component component = drinksList.get(i);

        System.out.println("Drink Category Name: " + component.getDrinkCategoryName());

        // Setting all values in RecyclerView
        if (component.getDrinkCategoryName() != "") {

            myViewHolder.drinkCategoryTV.setText(component.getDrinkCategoryName());
            myViewHolder.drinkCategoryTV.setVisibility(View.VISIBLE);

            myViewHolder.drinkItemNameTV.setVisibility(View.VISIBLE);
            myViewHolder.lowTV.setVisibility(View.VISIBLE);
            myViewHolder.highTV.setVisibility(View.VISIBLE);
            myViewHolder.priceTV.setVisibility(View.VISIBLE);
            myViewHolder.lowHighIV.setVisibility(View.VISIBLE);

            myViewHolder.relativeLayout1.setVisibility(View.VISIBLE);
            myViewHolder.relativeLayout2.setVisibility(View.VISIBLE);

            myViewHolder.drinkItemNameTV.setText(component.getDrinkItemName());
            System.out.println("Drink Item Name: " + component.getDrinkItemName());

            myViewHolder.lowTV.setText(df.format(Double.parseDouble(component.getDrinkItemMinRate())));
            myViewHolder.highTV.setText(df.format(Double.parseDouble(component.getDrinkItemMaxRate())));
            myViewHolder.priceTV.setText(df.format(Double.parseDouble(component.getDrinkItemCurrentRate())));

            if (i % 2 == 1) {
                myViewHolder.relativeLayout2.setBackgroundColor(Color.parseColor("#454C82"));
                //myViewHolder.lowHighIV.setBackgroundResource(R.drawable.red_arrow_50);

            } else {
                myViewHolder.relativeLayout2.setBackgroundColor(Color.parseColor("#8C3F2A"));
                //myViewHolder.lowHighIV.setBackgroundResource(R.drawable.green_arrow_50);
            }

            if(Double.parseDouble(component.getDrinkItemCurrentRate()) >= Double.parseDouble(component.getDrinkItemLastRate())) {

                myViewHolder.lowHighIV.setBackgroundResource(R.drawable.green_arrow_50);
            }
            else if (Double.parseDouble(component.getDrinkItemCurrentRate()) < Double.parseDouble(component.getDrinkItemLastRate())){

                myViewHolder.lowHighIV.setBackgroundResource(R.drawable.red_arrow_50);
            }

        } else {

            myViewHolder.drinkCategoryTV.setVisibility(View.GONE);

            myViewHolder.drinkItemNameTV.setText(component.getDrinkItemName());
            System.out.println("Drink Item Name: " + component.getDrinkItemName());
            myViewHolder.drinkItemNameTV.setVisibility(View.VISIBLE);

            myViewHolder.lowTV.setVisibility(View.VISIBLE);
            myViewHolder.highTV.setVisibility(View.VISIBLE);
            myViewHolder.priceTV.setVisibility(View.VISIBLE);
            myViewHolder.lowHighIV.setVisibility(View.VISIBLE);

            myViewHolder.relativeLayout1.setVisibility(View.GONE);
            myViewHolder.relativeLayout2.setVisibility(View.VISIBLE);

            myViewHolder.lowTV.setText(df.format(Double.parseDouble(component.getDrinkItemMinRate())));
            myViewHolder.highTV.setText(df.format(Double.parseDouble(component.getDrinkItemMaxRate())));
            myViewHolder.priceTV.setText(df.format(Double.parseDouble(component.getDrinkItemCurrentRate())));

            if (i % 2 == 1) {
                myViewHolder.relativeLayout2.setBackgroundColor(Color.parseColor("#454C82"));
                //myViewHolder.lowHighIV.setBackgroundResource(R.drawable.red_arrow_50);

            } else {
                myViewHolder.relativeLayout2.setBackgroundColor(Color.parseColor("#8C3F2A"));
                //myViewHolder.lowHighIV.setBackgroundResource(R.drawable.green_arrow_50);
            }

            if(Double.parseDouble(component.getDrinkItemCurrentRate()) >= Double.parseDouble(component.getDrinkItemLastRate())) {

                myViewHolder.lowHighIV.setBackgroundResource(R.drawable.green_arrow_50);
            }
            else if (Double.parseDouble(component.getDrinkItemCurrentRate()) < Double.parseDouble(component.getDrinkItemLastRate())){

                myViewHolder.lowHighIV.setBackgroundResource(R.drawable.red_arrow_50);
            }
        }
    }

    @Override
    public int getItemCount() {
        return drinksList.size();
    }
}
