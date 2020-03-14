package com.romiotech.barexchangeitemdisplaytv;

import android.app.Application;

import com.onesignal.OneSignal;

public class ApplicationClass extends Application {

    private static ApplicationClass instance;

    @Override
    public void onCreate() {
        instance = this;
        super.onCreate();

        //OneSignal.setLogLevel(OneSignal.LOG_LEVEL.DEBUG, OneSignal.LOG_LEVEL.DEBUG);

        // OneSignal Initialization
        OneSignal.startInit(this)
                .setNotificationReceivedHandler(new ExampleNotificationReceivedHandler())
                .setNotificationOpenedHandler(new ExampleNotificationOpenedHandler(this))
                .inFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification)
                .unsubscribeWhenNotificationsAreDisabled(true)
                .init();

        /*
        If you collect emails from users, you can set a user's email with the setEmail method.
        This enables OneSignal Email Messaging, which allows you to send emails in addition to push.
         */
        //OneSignal.setEmail("alert.romio@gmail.com");

        /*
        If you have a backend server, we strongly recommend using Identity Verification with your users.
        Your backend can generate an email authentication token and send it to your app or website.
         */
        /*String email = "alert.romio@gmail.com";
        String emailAuthHash = "..."; // Email auth hash generated from your server
        OneSignal.setEmail(email, emailAuthHash, new OneSignal.EmailUpdateHandler() {
            @Override
            public void onSuccess() {
                // Email successfully synced with OneSignal
                //Toast.makeText(ApplicationClass.this, "Email sent successfully.", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(OneSignal.EmailUpdateError error) {
                // Error syncing email, check error.getType() and error.getMessage() for details
                //Toast.makeText(ApplicationClass.this, "Email sending failed.", Toast.LENGTH_SHORT).show();
            }
        });*/
    }

    public static ApplicationClass getInstance() {
        return instance;
    }

}
