package com.romiotech.barexchangeitemdisplaytv;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class SaveSharedPreference {

    static final String PREF_USER_NAME = "username";
    static final String CUSTOMER_ID = "customerId";
    static final String PREF_LOCAL_SERVER_ADDRESS = "local_server_address";
    static final String PREF_DISPLAY_TV_NUMBER = "display_tv_number";

    static SharedPreferences getSharedPreferences(Context ctx) {
        return PreferenceManager.getDefaultSharedPreferences(ctx);
    }

    public static void setUserName(Context ctx, String userName)
    {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(PREF_USER_NAME, userName);
        editor.commit();
    }

    public static void setCustomerId(Context ctx, String customerId)
    {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(CUSTOMER_ID, customerId);
        editor.commit();
    }

    public static String getUserName(Context ctx)
    {
        return getSharedPreferences(ctx).getString(PREF_USER_NAME, "");
    }

    public static String getCustometId(Context ctx) {
        return getSharedPreferences(ctx).getString(CUSTOMER_ID, "");
    }

    public static void setLocalServerAddress(Context ctx, String localServerAddress)
    {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(PREF_LOCAL_SERVER_ADDRESS, localServerAddress);
        editor.commit();
    }

    public static String getLocalServerAddress(Context ctx)
    {
        return getSharedPreferences(ctx).getString(PREF_LOCAL_SERVER_ADDRESS, "");
    }

    public static void setDisplayTvNumber(Context ctx, String displayTvNumber)
    {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(PREF_DISPLAY_TV_NUMBER, displayTvNumber);
        editor.commit();
    }

    public static String getDisplayTvNumber(Context ctx)
    {
        return getSharedPreferences(ctx).getString(PREF_DISPLAY_TV_NUMBER, "");
    }
}
