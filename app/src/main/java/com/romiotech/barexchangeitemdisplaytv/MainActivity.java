package com.romiotech.barexchangeitemdisplaytv;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<Component> drinksList;
    private List<Component> masterDrinksList;
    private RecyclerView recyclerViewVertical, recyclerViewHorizontal;
    private DrinkAdapter drinkAdapter;
    private MasterItemsAdapter masterItemsAdapter;
    private DrinkAdapterHorizontal drinkAdapterHorizontal;
    Toolbar toolbar;

    //public static String Outlet_Url = "http://192.168.1.66/BarExchange";
    //public static String Outlet_Url = "http://192.168.1.35/barexchange1";
    //public static String Outlet_Url = "http://barexchangeservices.skipthequeue.in";
    //public static String Outlet_Url = "http://192.168.1.159/barexchange1";

    static final String URL = "/services.ashx?SUD=0";
    // XML node keys
    static final String KEY_SONG = "Table"; // parent node
    static final String KEY_DEPT_ID = "id";
    static final String KEY_DEPT_TITLE = "dept";
    static final String KEY_DEPT_THUMB_URL = "ImagePath";

    //static String URL1 = "/services.ashx?SUB_ID=";
 //   static String URL1 = "/services.ashx?SUB_ID=0&IsShowAllItems=0";
    static String URL1 = "/services.ashx?SUB_ID=0&IsShowAllItems=0&IsTicker=0&Display_name=";
    static final String KEY_ITEM = "Table"; // parent node
    static final String KEY_ID_ITEM = "I_Code";
    static final String KEY_NAME = "i_name";
    static final String KEY_COST = "Rate";
    static final String KEY_TAX_SLOT = "Tax_Slot";
    static final String KEY_SERVICE_TAX = "Service_tax";
    static final String KEY_UNIT_NAME = "UnitName";
    static final String KEY_COLOR = "Color";
    static final String KEY_QTY = "Qty";
    static final String KEY_COMMENT = "Comment";
    static final String KEY_TAX = "Tax";
    static final String KEY_ID_DEPT = "Dept_ID";
    static final String KEY_ITEM_DESC = "Item_Descp";
    static final String KEY_DISCOUNT_CODE = "Discount";
    static final String KEY_ITEM_IMAGE = "Item_Image";
    static final String KEY_OUTLET_ID = "outlet_id";
    static final String KEY_IS_DEAL = "IsDeal";
    static final String KEY_OPTIONAL = "Optional";
    static final String KEY_IS_HAPPY_HOUR = "IsHappy_hour";
    static final String KEY_HAPPY_HOUR_RATE = "happy_hour_rate";
    static final String KEY_MIN_RATE = "Min_Rate";
    static final String KEY_MAX_RATE = "Max_Rate";
    static final String KEY_CURRENT_RATE = "CURRENT_RATE";
    static final String KEY_LAST_RATE = "Last_Rate";
    static final String KEY_START_TIME = "Start_Time";
    static final String KEY_CURRENT_TIME = "current_Time";

    static final String FLAG_KEY_ITEM = "Table1"; // parent node
    static final String TOP_LOOSERS_FLAG = "I_Code";
    static final String MARKET_CRASHED_FLAG = "I_Code";

    String xml = "";
    XMLParsingDOM parser, parser1, parser2;
    Document doc, doc1, doc2;
    NodeList nl, nl1, nl2, nl3;
    Element e, e1, e2, e3;

    Component component;

    static ArrayList<HashMap<String, String>> songsList, songsList1, masterItemsList;
    HashMap<String, String> map1, map2;

    DecimalFormat df = new DecimalFormat("#,###.00");
    ProgressDialog progressDialogForLoading;
    Handler handler;

    final int speedScroll = 5000;
    final Handler handlerV = new Handler();
    final Handler handlerH = new Handler();
    Runnable runnable;
    int countV = 0;
    boolean flagV = true;
    Runnable runnableH;
    int countH = 0;
    boolean flagH = true;


    RecyclerView.LayoutManager layoutManagerVertical;
    RecyclerView.LayoutManager layoutManagerHorizontal;

    Runnable myRunnable;

    int Local_Network_not_Avilable = 0;
    int Local_Network_Avilable = 0;
    String local_ip;

    String local_server_address, display_tv_number;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("");





        recyclerViewVertical = findViewById(R.id.recyclerViewVertical);
        recyclerViewHorizontal = findViewById(R.id.recyclerViewHorizontal);

        songsList = new ArrayList<HashMap<String, String>>();
        songsList1 = new ArrayList<HashMap<String, String>>();
        masterItemsList = new ArrayList<HashMap<String, String>>();

        drinksList = new ArrayList<>();
        masterDrinksList = new ArrayList<>();

        masterItemsList.clear();
        masterDrinksList.clear();


        if(!isOnline()) {
            showAlert();
        }
        else {
            try {


                try{
                 //   countH = 0;
                    recyclerViewVertical.setHasFixedSize(true);
                    recyclerViewHorizontal.setHasFixedSize(true);

                    layoutManagerVertical = new LinearLayoutManager(getApplicationContext());

                    layoutManagerHorizontal = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);

                    recyclerViewVertical.setLayoutManager(layoutManagerVertical);
                    recyclerViewHorizontal.setLayoutManager(layoutManagerHorizontal);

                    // adding inbuilt divider line
                    recyclerViewVertical.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));

                    // adding custom divider line with padding 16dp
                    recyclerViewHorizontal.addItemDecoration(new MyDividerItemDecoration(this, LinearLayoutManager.HORIZONTAL, 16));

                    recyclerViewVertical.setItemAnimator(new DefaultItemAnimator());
                    recyclerViewHorizontal.setItemAnimator(new DefaultItemAnimator());

                }
                catch(Exception e){
                    e.printStackTrace();
                }

                /*drinkAdapter = new DrinkAdapter(drinksList);
                drinkAdapterHorizontal = new DrinkAdapterHorizontal(drinksList);

                DrinksTask drinksTask = new DrinksTask(MainActivity.this);
                drinksTask.execute();*/


                /*masterItemsAdapter = new MasterItemsAdapter(masterDrinksList);
                drinkAdapterHorizontal = new DrinkAdapterHorizontal(masterDrinksList);*/


                /*MasterItemsTask masterItemsTask = new MasterItemsTask(MainActivity.this);
                masterItemsTask.execute();*/

                Static_Variable.Outlet_Url = SaveSharedPreference.getLocalServerAddress(MainActivity.this);
                Static_Variable.Display_TV_Number = SaveSharedPreference.getDisplayTvNumber(MainActivity.this);

                System.out.println("Static_Variable.Outlet_Url: " + Static_Variable.Outlet_Url);
                System.out.println("Static_Variable.Display_TV_Number: " + Static_Variable.Display_TV_Number);

                if(Static_Variable.Outlet_Url.equals("")){

                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setMessage("Alert")
                            .setMessage("Please, enter your local server address.")
                            .setCancelable(false)
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {

                                }
                            });

                    AlertDialog alert = builder.create();
                    alert.show();
                }
                else {
                    if(Static_Variable.Display_TV_Number.equals("")){

                        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                        builder.setMessage("Alert")
                                .setMessage("Please, enter display TV number.")
                                .setCancelable(false)
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {

                                    }
                                });

                        AlertDialog alert = builder.create();
                        alert.show();
                    }
                    else {
                        try {
                            String Server_Address_Ping = Static_Variable.Outlet_Url;
                            String[] words = Server_Address_Ping.split("/");
                            String hedder = words[0];
                            local_ip = words[2];

                            /*GetXMLTask1 getXMLTask1 = new GetXMLTask1(MainActivity.this);
                            getXMLTask1.execute();*/

                            Bar_Exchange_Item_GetXMLTask3 Task = new Bar_Exchange_Item_GetXMLTask3(MainActivity.this);
                            Task.execute();


                        }
                        catch (Exception e){
                            e.printStackTrace();

                            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                            builder.setMessage("Local Server Address Not Match!")
                                    .setCancelable(false)
                                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {

                                        }
                                    });

                            AlertDialog alert = builder.create();
                            alert.show();
                        }
                    }
                }
            }
            catch(Exception e){
                e.printStackTrace();
            }
        }



       /* // row click listener
        recyclerViewVertical.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerViewVertical, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {


                Component component = masterDrinksList.get(position);

                          }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        // row click listener
        recyclerViewHorizontal.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerViewHorizontal, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {

                Component component = masterDrinksList.get(position);

            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));*/


        /*final Runnable runnable = new Runnable() {
            *//*int count = 0;
            boolean flag = true;*//*
            @Override
            public void run() {

                *//*if(countV < drinkAdapter.getItemCount()){
                    if(countV==drinkAdapter.getItemCount()-1){
                        flagV = false;
                    }else if(countV == 0){
                        flagV = true;
                    }
                    if(flagV) countV++;
                    else countV--;

                    recyclerViewVertical.smoothScrollToPosition(countV);
                    handlerV.postDelayed(this, speedScroll);
                }*//*

                System.out.println("masterItemsAdapter.getItemCount(): " + masterItemsAdapter.getItemCount());

                if(countV == masterItemsAdapter.getItemCount()){
                    countV = 0;
                }

                if(countV < masterItemsAdapter.getItemCount()){

                    *//*if(countV==masterItemsAdapter.getItemCount()-1){
                        flagV = false;
                    }else if(countV == 0){
                        flagV = true;
                    }
                    if(flagV) countV++;
                    else countV--;*//*

                    System.out.println("countV: " + countV);

                    //recyclerViewVertical.smoothScrollToPosition(++countV);
                    recyclerViewVertical.smoothScrollToPosition(countV++);
                    handlerV.postDelayed(this, speedScroll);
                }
            }
        };
        handlerV.postDelayed(runnable,speedScroll);*/


        /*final Runnable runnableH = new Runnable() {
            *//*int count = 0;
            boolean flag = true;*//*
            @Override
            public void run() {


                if(countH == drinkAdapterHorizontal.getItemCount()){
                    countH = 0;
                }

                if(countH < drinkAdapterHorizontal.getItemCount()){

                    *//*if(countH==drinkAdapterHorizontal.getItemCount()-1){
                        flagH = false;
                    }else if(countH == 0){
                        flagH = true;
                    }
                    if(flagH) countH++;
                    else countH--;*//*

                    recyclerViewHorizontal.smoothScrollToPosition(++countH);
                    handlerH.postDelayed(this, speedScroll);
                }
            }
        };
        handlerH.postDelayed(runnableH,speedScroll);*/

        //autoRefreshActivity();

        //updateListSchedule();

    }

    public void horizontalScrolling(){



        try {
            runnableH = new Runnable() {
                @Override
                public void run() {
                    System.out.println("drinkAdapterHorizontal.getItemCount() horizontalScrolling: " +
                            countH);
                    System.out.println("drinkAdapterHorizontal.getItemCount() horizontalScroll: " +
                            drinkAdapterHorizontal.getItemCount());

                    try {

                        System.out.println(" Print ExampleNotificationReceivedHandler" + ExampleNotificationReceivedHandler.notificationID);

                        if (!ExampleNotificationReceivedHandler.notificationID.equals(""))
                        {
                            for(int i = 0; i<=5; i++) {

                                Context context1 = MainActivity.this;
                                Toast toast = Toast.makeText(context1, "MARKET CRASHED", Toast.LENGTH_LONG);
                                LinearLayout toastLayout = (LinearLayout) toast.getView();
                               // toastLayout.setGravity(Gravity.CENTER_VERTICAL);
                                toast.setGravity(Gravity.CENTER, 0, 0);
                                TextView toastTV = (TextView) toastLayout.getChildAt(0);
                                toastTV.setTextSize(50);
                                toastTV.setTextColor(Color.BLUE);
                                toastTV.setGravity(Gravity.CENTER);
                               // toastLayout.setBackgroundColor(Color.WHITE);
                                toast.show();

                            }

                          //  Thread.sleep(20000);

                            ExampleNotificationReceivedHandler.notificationID = "";

                            Bar_Exchange_Item_GetXMLTask3 Task = new Bar_Exchange_Item_GetXMLTask3(MainActivity.this);
                            Task.execute();

                        }





                    }

                    catch(Exception e)
                    {

                    }


                    if(countH == drinkAdapterHorizontal.getItemCount()){

                        countH = 0;
                        if (isOnline()) {
                            /*Intent i = new Intent(MainActivity.this, MainActivity.class);

                            startActivity(i);
*/
                            Bar_Exchange_Item_GetXMLTask3 Task = new Bar_Exchange_Item_GetXMLTask3(MainActivity.this);
                            Task.execute();
                        }
                    }

                    if(countH < drinkAdapterHorizontal.getItemCount()){

                        try {
                            countH += 1;

                            recyclerViewVertical.smoothScrollToPosition(countH);
                            recyclerViewHorizontal.smoothScrollToPosition(countH);


                            handlerH.postDelayed(this, speedScroll);
                        }


                        catch (Exception e)
                        {

                        }

                    }
                }
            };
            countH = 0;
            handlerH.postDelayed(runnableH, speedScroll);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public void autoRefreshActivity () {
        if (handler == null) {
            handler = new Handler(getApplicationContext().getMainLooper());
        }
        handler.postDelayed ( new Runnable() {
            public void run() {
                //drinkAdapter.notifyDataSetChanged();
                masterItemsAdapter.notifyDataSetChanged();
                drinkAdapterHorizontal.notifyDataSetChanged();
                autoRefreshActivity();

                try{
                    DrinksTask drinksTask = new DrinksTask(MainActivity.this);
                    drinksTask.execute();
                }
                catch(Exception e){
                    e.printStackTrace();
                }

                countV = 0;
                flagV = false;
                countH = 0;
                flagH = false;

            }
        }, 60000);
    }

    public void updateListSchedule() {

        handler=  new Handler();


        myRunnable = new Runnable() {
            public void run() {

                //drinkAdapter.notifyDataSetChanged();
                //masterItemsAdapter.notifyDataSetChanged();
                //drinkAdapterHorizontal.notifyDataSetChanged();

                updateListSchedule();

                MasterItemsTask masterItemsTask = new MasterItemsTask(MainActivity.this);
                masterItemsTask.execute();

            }
        };

        handler.postDelayed(myRunnable,60000);

    }

    private class GetXMLTask1 extends AsyncTask<String, Void, Void> {

        private Activity context;
        ProgressDialog progressDialogForLoading;

        public GetXMLTask1(Activity context) {
            this.context = context;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            /*try {
                progressDialogForLoading = new ProgressDialog(MainActivity.this);
                progressDialogForLoading.setTitle("Please wait");
                progressDialogForLoading.setMessage("Please wait...");
                progressDialogForLoading.setCancelable(false);
                progressDialogForLoading.show();

            } catch (Exception e) {
                e.printStackTrace();
            }*/
        }

        @Override
        protected Void doInBackground(String... params) {

            if(!isOnline()){
                publishProgress();
            }
            else {
                try {
                    java.net.URL url = new URL("http://" + local_ip);   // Change to "http://google.com" for www  test.
                    HttpURLConnection urlc = (HttpURLConnection) url.openConnection();
                    urlc.setConnectTimeout(10 * 1000);          // 10 s.
                    urlc.connect();

                    System.out.println("urlc.getResponseCode(): " + urlc.getResponseCode());

                    if (urlc.getResponseCode() == 200) {        // 200 = "OK" code (http connection is fine).

                        Local_Network_not_Avilable = 0;
                    }
                }
                catch (IOException e) {
                    e.printStackTrace();
                    Local_Network_not_Avilable = 1;
                }
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);

            /*if (progressDialogForLoading != null) {

                progressDialogForLoading.dismiss();
                progressDialogForLoading = null;
            }

            showAlert();*/
        }

        @Override
        protected void onPostExecute(Void result) {
            //progressDialogForLoading.dismiss();
            super.onPostExecute(result);

            /*if (progressDialogForLoading != null) {

                progressDialogForLoading.dismiss();
                progressDialogForLoading = null;
            }*/

            if(!isOnline()){

                /*if (progressDialogForLoading != null) {

                    progressDialogForLoading.dismiss();
                    progressDialogForLoading = null;
                }*/

                showAlert();
            }
            else {
                if(Local_Network_not_Avilable == 1)
                {
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setMessage("Local Area Network Not Avilable!")
                            .setCancelable(false)
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {

                                    /*if (progressDialogForLoading != null) {

                                        progressDialogForLoading.dismiss();
                                        progressDialogForLoading = null;
                                    }*/

                                }
                            });

                    AlertDialog alert = builder.create();
                    alert.show();
                }
                else {
                    MasterItemsTask masterItemsTask = new MasterItemsTask(MainActivity.this);
                    masterItemsTask.execute();
                }
            }
        }
    }

    private class DrinksTask extends AsyncTask<String, Void, Void> {

        Intent i;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialogForLoading = new ProgressDialog(MainActivity.this);
            progressDialogForLoading.setMessage("Please wait...");
            progressDialogForLoading.setCancelable(false);
            progressDialogForLoading.show();
            System.out.println("Inside onPreExecute method.");
        }

        private Activity context;

        public DrinksTask(Activity context) {
            this.context = context;
        }

        @Override
        protected Void doInBackground(String... urls) {

            if(!isOnline()){

                //showAlert();
                publishProgress();
            }

            if(isOnline()) {

                parser = new XMLParsingDOM();

                String xml = "";

                try {
                    xml = parser.getXmlFromUrl(Static_Variable.Outlet_Url + URL); // getting XML from URL

                    System.out.println("URL: " + Static_Variable.Outlet_Url + URL);
                    System.out.println("XML: " + xml);
                }
                catch (Exception e) {
                    e.printStackTrace();
                }

                try {
                    doc = parser.getDomElement(xml); // getting DOM element

                    nl = doc.getElementsByTagName(KEY_SONG);
                }
                catch (Exception e){
                    e.printStackTrace();
                }

                // looping through all song nodes <song>
                for (int i = 0; i < nl.getLength(); i++) {

                    e = (Element) nl.item(i);

                    // creating new HashMap
                    try {
                        HashMap<String, String> map = new HashMap<String, String>();

                        // adding each child node to HashMap key => value
                        map.put(KEY_DEPT_ID, parser.getValue(e, KEY_DEPT_ID));
                        map.put(KEY_DEPT_TITLE, parser.getValue(e, KEY_DEPT_TITLE));
                        map.put(KEY_DEPT_THUMB_URL, parser.getValue(e, KEY_DEPT_THUMB_URL));

                        System.out.println("KEY_DEPT_ID: " + parser.getValue(e, KEY_DEPT_ID));
                        System.out.println("KEY_DEPT_TITLE: " + parser.getValue(e, KEY_DEPT_TITLE));
                        System.out.println("KEY_DEPT_THUMB_URL: " + parser.getValue(e, KEY_DEPT_THUMB_URL));

                        songsList.add(map);
                        songsList1.add(map);


                        parser1 = new XMLParsingDOM();

                        String xml1 = parser1.getXmlFromUrl(Static_Variable.Outlet_Url + URL1 + parser.getValue(e, KEY_DEPT_ID)); // getting XML from URL

                        System.out.println("URL1: " + Static_Variable.Outlet_Url + URL1 + parser.getValue(e, KEY_DEPT_ID));
                        System.out.println("XML1: " + xml1);

                        doc1 = parser1.getDomElement(xml1); // getting DOM element

                        nl1 = doc1.getElementsByTagName(KEY_ITEM);

                        // looping through all song nodes <song>
                        for (int i1 = 0; i1 < nl1.getLength(); i1++) {
                            // creating new HashMap
                            map1 = new HashMap<String, String>();

                            e1 = (Element) nl1.item(i1);

                            // adding each child node to HashMap key => value
                            map1.put(KEY_ID_ITEM, parser1.getValue(e1, KEY_ID_ITEM));
                            map1.put(KEY_NAME, parser1.getValue(e1, KEY_NAME));
                            map1.put(KEY_COST, parser1.getValue(e1, KEY_COST));

                            System.out.println("KEY_ID_ITEM: " + parser1.getValue(e1, KEY_ID_ITEM));
                            System.out.println("KEY_NAME: " + parser1.getValue(e1, KEY_NAME));
                            System.out.println("KEY_COST: " + parser1.getValue(e1, KEY_COST));

                            songsList1.add(map1);

                            if(i1 == 0) {

                                /*component = new Component(parser.getValue(e, KEY_DEPT_TITLE),
                                        parser1.getValue(e1, KEY_NAME),
                                        parser1.getValue(e1, KEY_COST));*/

                                component = new Component(parser.getValue(e, KEY_DEPT_ID),
                                        parser.getValue(e, KEY_DEPT_TITLE),
                                        parser1.getValue(e1, KEY_ID_ITEM),
                                        parser1.getValue(e1, KEY_NAME),
                                        parser1.getValue(e1, KEY_COST),
                                        parser1.getValue(e1, KEY_TAX_SLOT),
                                        parser1.getValue(e1, KEY_SERVICE_TAX),
                                        parser1.getValue(e1, KEY_UNIT_NAME),
                                        parser1.getValue(e1, KEY_COLOR),
                                        parser1.getValue(e1, KEY_QTY),
                                        parser1.getValue(e1, KEY_COMMENT),
                                        parser1.getValue(e1, KEY_TAX),
                                        parser1.getValue(e1, KEY_ID_DEPT),
                                        parser1.getValue(e1, KEY_ITEM_DESC),
                                        parser1.getValue(e1, KEY_DISCOUNT_CODE),
                                        parser1.getValue(e1, KEY_ITEM_IMAGE),
                                        parser1.getValue(e1, KEY_OUTLET_ID),
                                        parser1.getValue(e1, KEY_IS_DEAL),
                                        parser1.getValue(e1, KEY_OPTIONAL),
                                        parser1.getValue(e1, KEY_IS_HAPPY_HOUR),
                                        parser1.getValue(e1, KEY_HAPPY_HOUR_RATE),
                                        parser1.getValue(e1, KEY_MIN_RATE),
                                        parser1.getValue(e1, KEY_MAX_RATE),
                                        parser1.getValue(e1, KEY_CURRENT_RATE),
                                        parser1.getValue(e1, KEY_LAST_RATE),
                                        parser2.getValue(e2, KEY_START_TIME),
                                        parser2.getValue(e2, KEY_CURRENT_TIME));

                                drinksList.add(component);
                            }
                            else {

                                /*component = new Component("",
                                        parser1.getValue(e1, KEY_NAME),
                                        parser1.getValue(e1, KEY_COST));*/

                                component = new Component("",
                                        "",
                                        parser1.getValue(e1, KEY_ID_ITEM),
                                        parser1.getValue(e1, KEY_NAME),
                                        parser1.getValue(e1, KEY_COST),
                                        parser1.getValue(e1, KEY_TAX_SLOT),
                                        parser1.getValue(e1, KEY_SERVICE_TAX),
                                        parser1.getValue(e1, KEY_UNIT_NAME),
                                        parser1.getValue(e1, KEY_COLOR),
                                        parser1.getValue(e1, KEY_QTY),
                                        parser1.getValue(e1, KEY_COMMENT),
                                        parser1.getValue(e1, KEY_TAX),
                                        parser1.getValue(e1, KEY_ID_DEPT),
                                        parser1.getValue(e1, KEY_ITEM_DESC),
                                        parser1.getValue(e1, KEY_DISCOUNT_CODE),
                                        parser1.getValue(e1, KEY_ITEM_IMAGE),
                                        parser1.getValue(e1, KEY_OUTLET_ID),
                                        parser1.getValue(e1, KEY_IS_DEAL),
                                        parser1.getValue(e1, KEY_OPTIONAL),
                                        parser1.getValue(e1, KEY_IS_HAPPY_HOUR),
                                        parser1.getValue(e1, KEY_HAPPY_HOUR_RATE),
                                        parser1.getValue(e1, KEY_MIN_RATE),
                                        parser1.getValue(e1, KEY_MAX_RATE),
                                        parser1.getValue(e1, KEY_CURRENT_RATE),
                                        parser1.getValue(e1, KEY_LAST_RATE),
                                        parser2.getValue(e2, KEY_START_TIME),
                                        parser2.getValue(e2, KEY_CURRENT_TIME));

                                drinksList.add(component);
                            }

                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);

            if (progressDialogForLoading != null) {

                progressDialogForLoading.dismiss();
                progressDialogForLoading = null;
            }
            showAlert();
        }

        protected void onPostExecute(Void result) {

            super.onPostExecute(result);

            if(isOnline()) {

                try {
                    // notify adapter about data set changes
                    // so that it will render the list with new data
                    drinkAdapter.notifyDataSetChanged();
                    drinkAdapterHorizontal.notifyDataSetChanged();

                    drinkAdapter = new DrinkAdapter(drinksList);
                    //drinkAdapter = new DrinkAdapter(MainActivity.this, songsList1);


                    drinkAdapterHorizontal = new DrinkAdapterHorizontal(drinksList);

                    if (progressDialogForLoading != null) {

                        progressDialogForLoading.dismiss();
                        progressDialogForLoading = null;
                    }


                    /*// Automatic scrolling of ListView items
                    new Thread(new Runnable() {

                        @Override
                        public void run() {
                            int listViewSize = list.getAdapter().getCount();

                            for (int index = 0; index < listViewSize ; index++) {

                                list.smoothScrollToPositionFromTop(list.getLastVisiblePosition() + 100, 0, 50000);

                                try {
                                    // it helps scrolling to stay smooth as possible (by experiment)
                                    Thread.sleep(500);
                                } catch (InterruptedException e) {

                                }
                            }
                        }
                    }).start();*/


                } catch (Exception e) {
                    e.printStackTrace();
                    if (progressDialogForLoading != null) {

                        progressDialogForLoading.dismiss();
                        progressDialogForLoading = null;
                    }
                }

            }
            else {
                if (progressDialogForLoading != null) {

                    progressDialogForLoading.dismiss();
                    progressDialogForLoading = null;
                }
                showAlert();
            }
        }
    }


    private class MasterItemsTask extends AsyncTask<String, Void, Void> {

        private Activity context;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialogForLoading = new ProgressDialog(MainActivity.this);
            progressDialogForLoading.setTitle("Please wait...");
            progressDialogForLoading.setMessage("Drinks are getting updated...");
            progressDialogForLoading.setCancelable(false);
            progressDialogForLoading.show();

            masterItemsList.clear();
            masterDrinksList.clear();


        }

        public MasterItemsTask(Activity context) {
            this.context = context;
        }

        @Override
        protected Void doInBackground(String... urls) {

            if(!isOnline()){

                //showAlert();
                publishProgress();
            }

            if(isOnline()) {

                parser2 = new XMLParsingDOM();

                //String xml = "";

                try {
                    xml = parser2.getXmlFromUrl(Static_Variable.Outlet_Url + URL1+Static_Variable.Display_TV_Number); // getting XML from URL

                    System.out.println("URL: " + Static_Variable.Outlet_Url + URL1+Static_Variable.Display_TV_Number);
                    System.out.println("XML: " + xml);
                }
                catch (Exception e) {
                    e.printStackTrace();
                }

                /*try {
                    doc2 = parser2.getDomElement(xml); // getting DOM element

                    nl2 = doc2.getElementsByTagName(KEY_SONG);
                }
                catch (Exception e){
                    e.printStackTrace();
                }

                if(nl2 == null){
                    System.out.println("nl2 is: " + nl2);
                }

                try {
                    // looping through all song nodes <song>
                    for (int i = 0; i < nl2.getLength(); i++) {

                        e2 = (Element) nl2.item(i);

                        // creating new HashMap
                        map2 = new HashMap<String, String>();

                        // adding each child node to HashMap key => value
                        map2.put(KEY_ID_ITEM, parser2.getValue(e2, KEY_ID_ITEM));
                        map2.put(KEY_NAME, parser2.getValue(e2, KEY_NAME));
                        map2.put(KEY_COST, parser2.getValue(e2, KEY_COST));

                        System.out.println("KEY_ID_ITEM: " + parser2.getValue(e2, KEY_ID_ITEM));
                        System.out.println("KEY_NAME: " + parser2.getValue(e2, KEY_NAME));
                        //System.out.println("KEY_MIN_RATE: " + parser2.getValue(e2, KEY_MIN_RATE));
                        //System.out.println("KEY_MAX_RATE: " + parser2.getValue(e2, KEY_MAX_RATE));
                        System.out.println("KEY_CURRENT_RATE: " + parser2.getValue(e2, KEY_CURRENT_RATE));
                        //System.out.println("KEY_LAST_RATE: " + parser2.getValue(e2, KEY_LAST_RATE));

                        masterItemsList.add(map2);

                        component = new Component("",
                                "",
                                parser2.getValue(e2, KEY_ID_ITEM),
                                parser2.getValue(e2, KEY_NAME),
                                parser2.getValue(e2, KEY_COST),
                                parser2.getValue(e2, KEY_TAX_SLOT),
                                parser2.getValue(e2, KEY_SERVICE_TAX),
                                parser2.getValue(e2, KEY_UNIT_NAME),
                                parser2.getValue(e2, KEY_COLOR),
                                parser2.getValue(e2, KEY_QTY),
                                parser2.getValue(e2, KEY_COMMENT),
                                parser2.getValue(e2, KEY_TAX),
                                parser2.getValue(e2, KEY_ID_DEPT),
                                parser2.getValue(e2, KEY_ITEM_DESC),
                                parser2.getValue(e2, KEY_DISCOUNT_CODE),
                                parser2.getValue(e2, KEY_ITEM_IMAGE),
                                parser2.getValue(e2, KEY_OUTLET_ID),
                                parser2.getValue(e2, KEY_IS_DEAL),
                                parser2.getValue(e2, KEY_OPTIONAL),
                                parser2.getValue(e2, KEY_IS_HAPPY_HOUR),
                                parser2.getValue(e2, KEY_HAPPY_HOUR_RATE),
                                parser2.getValue(e2, KEY_MIN_RATE),
                                parser2.getValue(e2, KEY_MAX_RATE),
                                parser2.getValue(e2, KEY_CURRENT_RATE),
                                parser2.getValue(e2, KEY_LAST_RATE));

                        masterDrinksList.add(component);

                    }
                }
                catch (Exception e) {
                    e.printStackTrace();
                }*/
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);

            if (progressDialogForLoading != null) {

                progressDialogForLoading.dismiss();
                progressDialogForLoading = null;
            }
            showAlert();
        }



        protected void onPostExecute(Void result) {

            super.onPostExecute(result);

            if(xml.equals("") || xml.equals(null)){

                if (progressDialogForLoading != null) {

                    progressDialogForLoading.dismiss();
                    progressDialogForLoading = null;
                }

                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Information...")
                        .setMessage("Server is not responding.")
                        .setCancelable(false)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();
            }
            else if(xml.equals("<NewDataSet/>") || xml.equals("<NewDataSet />")){

                if (progressDialogForLoading != null) {

                    progressDialogForLoading.dismiss();
                    progressDialogForLoading = null;
                }

                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Information...")
                        .setMessage("There is no data for this.")
                        .setCancelable(false)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();
            }
            else {
                if(!isOnline()){
                    if (progressDialogForLoading != null) {

                        progressDialogForLoading.dismiss();
                        progressDialogForLoading = null;
                    }
                    showAlert();
                }

                if(isOnline()) {
                    try {
                        doc2 = parser2.getDomElement(xml); // getting DOM element

                        nl2 = doc2.getElementsByTagName(KEY_SONG);
                    }
                    catch (Exception e){
                        e.printStackTrace();
                    }

                    if(nl2 == null){
                        System.out.println("nl2 is: " + nl2);
                    }
                    else {
                        try {
                            // looping through all song nodes <song>
                            for (int i = 0; i < nl2.getLength(); i++) {

                                e2 = (Element) nl2.item(i);

                                // creating new HashMap
                                map2 = new HashMap<String, String>();

                                // adding each child node to HashMap key => value
                                map2.put(KEY_ID_ITEM, parser2.getValue(e2, KEY_ID_ITEM));
                                map2.put(KEY_NAME, parser2.getValue(e2, KEY_NAME));
                                map2.put(KEY_COST, parser2.getValue(e2, KEY_COST));
                                map2.put(KEY_START_TIME, parser2.getValue(e2, KEY_START_TIME));
                                map2.put(KEY_CURRENT_TIME, parser2.getValue(e2, KEY_CURRENT_TIME));



                                System.out.println("KEY_ID_ITEM: " + parser2.getValue(e2, KEY_ID_ITEM));
                                System.out.println("KEY_NAME: " + parser2.getValue(e2, KEY_NAME));
                                //System.out.println("KEY_MIN_RATE: " + parser2.getValue(e2, KEY_MIN_RATE));
                                //System.out.println("KEY_MAX_RATE: " + parser2.getValue(e2, KEY_MAX_RATE));
                                System.out.println("KEY_CURRENT_RATE: " + parser2.getValue(e2, KEY_CURRENT_RATE));
                                //System.out.println("KEY_LAST_RATE: " + parser2.getValue(e2, KEY_LAST_RATE));

                                masterItemsList.add(map2);

                                component = new Component("",
                                        "",
                                        parser2.getValue(e2, KEY_ID_ITEM),
                                        parser2.getValue(e2, KEY_NAME),
                                        parser2.getValue(e2, KEY_COST),
                                        parser2.getValue(e2, KEY_TAX_SLOT),
                                        parser2.getValue(e2, KEY_SERVICE_TAX),
                                        parser2.getValue(e2, KEY_UNIT_NAME),
                                        parser2.getValue(e2, KEY_COLOR),
                                        parser2.getValue(e2, KEY_QTY),
                                        parser2.getValue(e2, KEY_COMMENT),
                                        parser2.getValue(e2, KEY_TAX),
                                        parser2.getValue(e2, KEY_ID_DEPT),
                                        parser2.getValue(e2, KEY_ITEM_DESC),
                                        parser2.getValue(e2, KEY_DISCOUNT_CODE),
                                        parser2.getValue(e2, KEY_ITEM_IMAGE),
                                        parser2.getValue(e2, KEY_OUTLET_ID),
                                        parser2.getValue(e2, KEY_IS_DEAL),
                                        parser2.getValue(e2, KEY_OPTIONAL),
                                        parser2.getValue(e2, KEY_IS_HAPPY_HOUR),
                                        parser2.getValue(e2, KEY_HAPPY_HOUR_RATE),
                                        parser2.getValue(e2, KEY_MIN_RATE),
                                        parser2.getValue(e2, KEY_MAX_RATE),
                                        parser2.getValue(e2, KEY_CURRENT_RATE),
                                        parser2.getValue(e2, KEY_LAST_RATE),
                                        parser2.getValue(e2, KEY_START_TIME),
                                        parser2.getValue(e2, KEY_CURRENT_TIME));

                                masterDrinksList.add(component);

                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        try {
                            // notify adapter about data set changes
                            // so that it will render the list with new data

                            masterItemsAdapter = new MasterItemsAdapter(masterDrinksList);
                            drinkAdapterHorizontal = new DrinkAdapterHorizontal(masterDrinksList);

                            masterItemsAdapter.notifyDataSetChanged();
                            drinkAdapterHorizontal.notifyDataSetChanged();

                            recyclerViewVertical.setAdapter(masterItemsAdapter);
                            recyclerViewHorizontal.setAdapter(drinkAdapterHorizontal);

                            horizontalScrolling();

                           /* masterItemsAdapter = new MasterItemsAdapter(masterDrinksList);
                            drinkAdapterHorizontal = new DrinkAdapterHorizontal(masterDrinksList);*/

                            //progressDialogForLoading.dismiss();

                            /*// Automatic scrolling of ListView items
                            new Thread(new Runnable() {

                                @Override
                                public void run() {
                                    int listViewSize = list.getAdapter().getCount();

                                    for (int index = 0; index < listViewSize ; index++) {

                                        list.smoothScrollToPositionFromTop(list.getLastVisiblePosition() + 100, 0, 50000);

                                        try {
                                            // it helps scrolling to stay smooth as possible (by experiment)
                                            Thread.sleep(500);
                                        } catch (InterruptedException e) {

                                        }
                                    }
                                }
                            }).start();*/

                        } catch (Exception e) {
                            e.printStackTrace();
                            //progressDialogForLoading.dismiss();
                        }
                    }
                    if (progressDialogForLoading != null) {

                        progressDialogForLoading.dismiss();
                        progressDialogForLoading = null;
                    }
                }
                else {
                    if (progressDialogForLoading != null) {

                        progressDialogForLoading.dismiss();
                        progressDialogForLoading = null;
                    }
                    showAlert();
                }
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.local_server_address:
                //Toast.makeText(this, "You have selected Local Server Address", Toast.LENGTH_SHORT).show();

                /*Intent	intent = new Intent(Login.this, LocalServerAddress.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);*/

                LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                final View layout = layoutInflater.inflate(R.layout.local_server_address, null);
                getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this)
                        .setView(layout);


                final AutoCompleteTextView local_server_address_ACTV = layout.findViewById(R.id.local_server_address_ACTV);
                final AutoCompleteTextView display_tv_number_ACTV = layout.findViewById(R.id.display_tv_number_ACTV);

                Button save_address_Button = layout.findViewById(R.id.save_address_Button);


                final AlertDialog alertDialog = builder.create();

                try {
                    Static_Variable.Outlet_Url = SaveSharedPreference.getLocalServerAddress(MainActivity.this);
                    Static_Variable.Display_TV_Number = SaveSharedPreference.getDisplayTvNumber(MainActivity.this);

                    System.out.println("Static_Variable.Outlet_Url: " + Static_Variable.Outlet_Url);
                    System.out.println("Static_Variable.Display_TV_Number: " + Static_Variable.Display_TV_Number);

                    local_server_address_ACTV.setText(Static_Variable.Outlet_Url);
                    display_tv_number_ACTV.setText(Static_Variable.Display_TV_Number);

                    save_address_Button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            local_server_address = local_server_address_ACTV.getText().toString();
                            display_tv_number = display_tv_number_ACTV.getText().toString();
                            System.out.println("local_server_address New: " + local_server_address);
                            System.out.println("display_tv_number New: " + display_tv_number);

                            if (local_server_address.equals("")){
                                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                                builder.setTitle("Alert!!!")
                                        .setMessage("Please, enter local server address.")
                                        .setCancelable(false)
                                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {

                                            }
                                        });

                                AlertDialog alert = builder.create();
                                alert.show();
                            }
                            else {
                                if(display_tv_number.equals("")){
                                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                                    builder.setMessage("Alert")
                                            .setMessage("Please, enter display TV number.")
                                            .setCancelable(false)
                                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int id) {

                                                }
                                            });

                                    AlertDialog alert = builder.create();
                                    alert.show();
                                }
                                else {
                                    try {
                                        //String Server_Address_Ping = Static_Variable.local_address;
                                        String Server_Address_Ping = local_server_address;
                                        String[] words = Server_Address_Ping.split("/");
                                        String hedder = words[0];
                                        local_ip = words[2];


                                        LocalServerAddressTask localServerAddressTask = new LocalServerAddressTask(MainActivity.this);
                                        localServerAddressTask.execute();

                                    } catch (Exception e) {
                                        e.printStackTrace();

                                        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                                        builder.setMessage("Local Server Address Not Match!")
                                                .setCancelable(false)
                                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                    public void onClick(DialogInterface dialog, int id) {

                                                    }
                                                });

                                        AlertDialog alert = builder.create();
                                        alert.show();
                                    }
                                }
                            }

                            alertDialog.cancel();
                        }
                    });
                }
                catch (Exception e){
                    e.printStackTrace();
                }

                alertDialog.show();


                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private class LocalServerAddressTask extends AsyncTask<String, Void, Void> {

        private Activity context;
        ProgressDialog progressDialogForLoading;

        public LocalServerAddressTask(Activity context) {
            this.context = context;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            if(!isOnline()){
                showAlert();
            }
            else {
                try {
                    progressDialogForLoading = new ProgressDialog(MainActivity.this);
                    progressDialogForLoading.setTitle("Please wait");
                    progressDialogForLoading.setMessage("Please wait...");
                    progressDialogForLoading.setCancelable(false);
                    progressDialogForLoading.show();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        @Override
        protected Void doInBackground(String... params) {

            if(!isOnline()){
                publishProgress();
            }
            else {
                try {
                    java.net.URL url = new URL("http://" + local_ip);   // Change to "http://google.com" for www  test.
                    HttpURLConnection urlc = (HttpURLConnection) url.openConnection();
                    urlc.setConnectTimeout(10 * 1000);          // 10 s.
                    urlc.connect();

                    System.out.println("urlc.getResponseCode(): " + urlc.getResponseCode());

                    if (urlc.getResponseCode() == 200) {        // 200 = "OK" code (http connection is fine).

                        Local_Network_not_Avilable = 0;

                    } else {

                    }
                }
                catch (IOException e) {
                    Local_Network_not_Avilable = 1;
                }
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);

            if (progressDialogForLoading != null) {

                progressDialogForLoading.dismiss();
                progressDialogForLoading = null;
            }
            showAlert();
        }

        @Override
        protected void onPostExecute(Void result) {
            //progressDialogForLoading.dismiss();
            super.onPostExecute(result);

            if(!isOnline()){
                if (progressDialogForLoading != null) {

                    progressDialogForLoading.dismiss();
                    progressDialogForLoading = null;
                }
                showAlert();
            }
            else {
                if(Local_Network_not_Avilable == 1)
                {
                    /*SaveSharedPreference.setLocalServerAddress(MainActivity.this, "");
                    Static_Variable.Outlet_Url = SaveSharedPreference.getLocalServerAddress(MainActivity.this);

                    SaveSharedPreference.setDisplayTvNumber(MainActivity.this, "");
                    Static_Variable.Display_TV_Number= SaveSharedPreference.getDisplayTvNumber(MainActivity.this);*/

                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setMessage("Local Area Network Not Avilable!")
                            .setCancelable(false)
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {

                                    if (progressDialogForLoading != null) {

                                        progressDialogForLoading.dismiss();
                                        progressDialogForLoading = null;
                                    }

                                }
                            });

                    AlertDialog alert = builder.create();
                    alert.show();
                }
                else {
                    if (progressDialogForLoading != null) {

                        progressDialogForLoading.dismiss();
                        progressDialogForLoading = null;
                    }

                    SaveSharedPreference.setLocalServerAddress(MainActivity.this, local_server_address);
                    Static_Variable.Outlet_Url = SaveSharedPreference.getLocalServerAddress(MainActivity.this);

                    SaveSharedPreference.setDisplayTvNumber(MainActivity.this, display_tv_number);
                    Static_Variable.Display_TV_Number= SaveSharedPreference.getDisplayTvNumber(MainActivity.this);

                    //updateListSchedule();

                    MasterItemsTask masterItemsTask = new MasterItemsTask(MainActivity.this);
                    masterItemsTask.execute();
                }
            }
        }
    }

    public boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    public void showAlert(){
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Information...")
                .setMessage("Network Connection Not Available! Do you want to try again?")
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        if (!isOnline()) {
                            finish();
                       //     return;
                        } else {
                            Intent i = new Intent(MainActivity.this, MainActivity.class);
                            startActivity(i);
                        }
                    }
                })
                .setNegativeButton("NO", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                  //      return;
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }




    private class Bar_Exchange_Item_GetXMLTask3 extends AsyncTask<String, Void, Void>  {

        ArrayList<HashMap<String, String>> songsList = new ArrayList<HashMap<String, String>>();

        DecimalFormat df = new DecimalFormat("#,###.00");

        ProgressDialog progressDialogForLoading;
        Intent i;

        @Override
        protected void onPreExecute() {

            super.onPreExecute();
            progressDialogForLoading = new ProgressDialog(MainActivity.this);
            progressDialogForLoading.setMessage("Please wait...");
            progressDialogForLoading.setCancelable(false);
            // try{

            progressDialogForLoading.show();

            masterItemsList.clear();
            masterDrinksList.clear();

          //  System.out.println("Address in report show");
        }

        // catch (Exception e)
        // {}
        // }
        private Activity context;

        public Bar_Exchange_Item_GetXMLTask3(Activity context) {
            this.context = context;
        }

        // }

        @Override
        protected Void doInBackground(String... urls) {

            if(isOnline()==true){
                try {
                    URL url = new URL("http://"+local_ip);   // Change to "http://google.com" for www  test.
                    HttpURLConnection urlc = (HttpURLConnection) url.openConnection();
                    urlc.setConnectTimeout(10 * 1000);          // 10 s.
                    urlc.connect();
                    if (urlc.getResponseCode() == 200) {        // 200 = "OK" code (http connection is fine).
                        // Log.wtf("Connection", "Success !");
                        // Toast.makeText(getApplicationContext(), "This Connection Avilable ", Toast.LENGTH_LONG).show();

                        //  ****************  Local Network Avilable Than Display Data Start


                        //String xml = "";

                        try {
                            parser2 = new XMLParsingDOM();
                            xml = parser2.getXmlFromUrl(Static_Variable.Outlet_Url + URL1+Static_Variable.Display_TV_Number); // getting XML from URL

                            System.out.println("URL: " + Static_Variable.Outlet_Url + URL1+Static_Variable.Display_TV_Number);
                            System.out.println("XML: " + xml);
                        }
                        catch (Exception e) {
                            e.printStackTrace();
                        }



                        try {
                            doc2 = parser2.getDomElement(xml); // getting DOM element

                            nl2 = doc2.getElementsByTagName(KEY_SONG);
                            nl3 = doc2.getElementsByTagName(FLAG_KEY_ITEM);
                        }
                        catch (Exception e){
                            e.printStackTrace();
                        }



                        if(nl2 == null){
                            System.out.println("nl2 is: " + nl2);
                        }
                        else {
                            try {
                                // looping through all song nodes <song>
                                for (int i = 0; i < nl2.getLength(); i++) {

                                    e2 = (Element) nl2.item(i);

                                    // creating new HashMap
                                    map2 = new HashMap<String, String>();

                                    // adding each child node to HashMap key => value
                                    map2.put(KEY_ID_ITEM, parser2.getValue(e2, KEY_ID_ITEM));
                                    map2.put(KEY_NAME, parser2.getValue(e2, KEY_NAME));
                                    map2.put(KEY_COST, parser2.getValue(e2, KEY_COST));
                                    map2.put(KEY_START_TIME, parser2.getValue(e2, KEY_START_TIME));
                                    map2.put(KEY_CURRENT_TIME, parser2.getValue(e2, KEY_CURRENT_TIME));



                                    System.out.println("KEY_ID_ITEM: " + parser2.getValue(e2, KEY_ID_ITEM));
                                    System.out.println("KEY_NAME: " + parser2.getValue(e2, KEY_NAME));
                                    //System.out.println("KEY_MIN_RATE: " + parser2.getValue(e2, KEY_MIN_RATE));
                                    //System.out.println("KEY_MAX_RATE: " + parser2.getValue(e2, KEY_MAX_RATE));
                                    System.out.println("KEY_CURRENT_RATE: " + parser2.getValue(e2, KEY_CURRENT_RATE));
                                    //System.out.println("KEY_LAST_RATE: " + parser2.getValue(e2, KEY_LAST_RATE));

                                    masterItemsList.add(map2);

                                    component = new Component("",
                                            "",
                                            parser2.getValue(e2, KEY_ID_ITEM),
                                            parser2.getValue(e2, KEY_NAME),
                                            parser2.getValue(e2, KEY_COST),
                                            parser2.getValue(e2, KEY_TAX_SLOT),
                                            parser2.getValue(e2, KEY_SERVICE_TAX),
                                            parser2.getValue(e2, KEY_UNIT_NAME),
                                            parser2.getValue(e2, KEY_COLOR),
                                            parser2.getValue(e2, KEY_QTY),
                                            parser2.getValue(e2, KEY_COMMENT),
                                            parser2.getValue(e2, KEY_TAX),
                                            parser2.getValue(e2, KEY_ID_DEPT),
                                            parser2.getValue(e2, KEY_ITEM_DESC),
                                            parser2.getValue(e2, KEY_DISCOUNT_CODE),
                                            parser2.getValue(e2, KEY_ITEM_IMAGE),
                                            parser2.getValue(e2, KEY_OUTLET_ID),
                                            parser2.getValue(e2, KEY_IS_DEAL),
                                            parser2.getValue(e2, KEY_OPTIONAL),
                                            parser2.getValue(e2, KEY_IS_HAPPY_HOUR),
                                            parser2.getValue(e2, KEY_HAPPY_HOUR_RATE),
                                            parser2.getValue(e2, KEY_MIN_RATE),
                                            parser2.getValue(e2, KEY_MAX_RATE),
                                            parser2.getValue(e2, KEY_CURRENT_RATE),
                                            parser2.getValue(e2, KEY_LAST_RATE),
                                            parser2.getValue(e2, KEY_START_TIME),
                                            parser2.getValue(e2, KEY_CURRENT_TIME));

                                    masterDrinksList.add(component);

                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }


                        }

                        Local_Network_not_Avilable = 0;
                        Local_Network_Avilable = 1;

                        //  ****************  Local Network Avilable Than Display Data End

                    } else {

                    }
                }
                catch (IOException e) {

                    //  ****************  Global Network Avilable Than Display Data Start

                    Local_Network_not_Avilable = 1;
                    Local_Network_Avilable = 0;

                    //  ****************  Global Network Avilable Than Display Data End

                }
            }

            return null;
        }

        protected void onPostExecute(Void result) {

            super.onPostExecute(result);
            progressDialogForLoading.dismiss();


            if(Local_Network_not_Avilable == 1)
            {

                showAlert();
            }

            else {
                if(isOnline()==true) {
                    try {
                        // notify adapter about data set changes
                        // so that it will render the list with new data

                        masterItemsAdapter = new MasterItemsAdapter(masterDrinksList);
                        drinkAdapterHorizontal = new DrinkAdapterHorizontal(masterDrinksList);

                        masterItemsAdapter.notifyDataSetChanged();
                        drinkAdapterHorizontal.notifyDataSetChanged();

                        recyclerViewVertical.setAdapter(masterItemsAdapter);
                        recyclerViewHorizontal.setAdapter(drinkAdapterHorizontal);

                        horizontalScrolling();

                    } catch (Exception e) {
                        e.printStackTrace();
                        //progressDialogForLoading.dismiss();
                    }
                }

            }
        }
    }


}
