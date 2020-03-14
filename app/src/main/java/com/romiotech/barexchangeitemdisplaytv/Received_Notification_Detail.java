package com.romiotech.barexchangeitemdisplaytv;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.TextView;

public class Received_Notification_Detail extends Activity {
    TextView Titel, Notification_Detail;
    String openURL,title,body;

    Typeface typeface;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.received__notification__detail);

         Titel = findViewById(R.id.titel);
         Notification_Detail = findViewById(R.id.description);

         openURL = getIntent().getStringExtra("openURL");
        title = getIntent().getStringExtra("title");
        body = getIntent().getStringExtra("body");
         Titel.setText(title);
         Notification_Detail.setText(body);
        /*typeface = Typeface.createFromAsset(Received_Notification_Detail.this.getAssets(),"MontserratAlternates_MediumItalic.ttf");
        if (typeface != null)
        {

        }*/

        Titel.setTypeface(typeface);
        Notification_Detail.setTypeface(typeface);


    }
}
