package ir.tanyar.app;

/**
 * Created by zaraksis on 11/09/2017.
 */

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Belal on 9/5/2017.
 */

//here for this class we are using a singleton pattern

public class InfoSharedPref {

    //the constants
    private static final String SHARED_PREF_NAME = "customersharedpref";
    private static final String KEY_TYPE = "keytype";
    private static final String KEY_NAME = "keyname";
    private static final String KEY_MANAGER = "keymanager";
    private static final String KEY_STATE = "keystate";
    private static final String KEY_CITY = "keycity";
    private static final String KEY_AREA = "keyarea";
    private static final String KEY_ADRES = "keyadres";
    private static final String KEY_ZIPCODE = "keyzipcode";
    private static final String KEY_PHONE= "keyphone";
    private static final String KEY_MOBILE = "keymobile";
    private static final String KEY_DESCRIP = "keydescrip";
    private static final String KEY_NETWORKER = "keynetworker";

    private static InfoSharedPref mInstance;
    private static Context mCtx;

    private InfoSharedPref(Context context) {
        mCtx = context;
    }

    public static synchronized InfoSharedPref getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new InfoSharedPref(context);
        }
        return mInstance;
    }

    //method to let the user login
    //this method will store the user data in shared preferences
    public void currentcustomer(CustomerData customer) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_TYPE, customer.getType());
        editor.putString(KEY_NAME, customer.getName());
        editor.putString(KEY_MANAGER, customer.getManager());
        editor.putString(KEY_STATE, customer.getState());
        editor.putString(KEY_CITY, customer.getCity());
        editor.putString(KEY_AREA, customer.getArea());
        editor.putString(KEY_ADRES, customer.getAdres());
        editor.putString(KEY_ZIPCODE, customer.getZipcode());
        editor.putString(KEY_PHONE, customer.getPhone());
        editor.putString(KEY_MOBILE, customer.getMobile());
        editor.putString(KEY_DESCRIP, customer.getDescrip());
        editor.putString(KEY_NETWORKER, customer.getNetworker());
        editor.apply();
    }


    //this method will give the logged in user
    public CustomerData getCustomer() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return new CustomerData(
                sharedPreferences.getString(KEY_TYPE, ""),
                sharedPreferences.getString(KEY_NAME, ""),
                sharedPreferences.getString(KEY_MANAGER, ""),
                sharedPreferences.getString(KEY_STATE, ""),
                sharedPreferences.getString(KEY_CITY, ""),
                sharedPreferences.getString(KEY_AREA, ""),
                sharedPreferences.getString(KEY_ADRES, ""),
                sharedPreferences.getString(KEY_ZIPCODE, ""),
                sharedPreferences.getString(KEY_PHONE, ""),
                sharedPreferences.getString(KEY_MOBILE, ""),
                sharedPreferences.getString(KEY_DESCRIP, ""),
                sharedPreferences.getString(KEY_NETWORKER, "")
        );
    }

    //this method will logout the user
    public void clearCustomer() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
        //mCtx.startActivity(new Intent(mCtx, MainActivity.class));
        //Toast.makeText(mCtx, "با موفقیت خارج شدید", Toast.LENGTH_SHORT).show();
    }
}
