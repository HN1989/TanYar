package ir.tanyar.app;

/**
 * Created by zaraksis on 11/09/2017.
 */

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

/**
 * Created by Belal on 9/5/2017.
 */

//here for this class we are using a singleton pattern

public class UserSharedPref {

    //the constants
    private static final String SHARED_PREF_NAME = "usersharedpref";
    private static final String KEY_USERNAME = "keyusername";

    private static UserSharedPref mInstance;
    private static Context mCtx;

    private UserSharedPref(Context context) {
        mCtx = context;
    }

    public static synchronized UserSharedPref getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new UserSharedPref(context);
        }
        return mInstance;
    }

    //method to let the user login
    //this method will store the user data in shared preferences
    public void userLogin(UserData user) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_USERNAME, user.getUsername());
        editor.apply();
    }

    //this method will checker whether user is already logged in or not
    public boolean isLoggedIn() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_USERNAME, null) != null;
    }

    //this method will give the logged in user
    public UserData getUser() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return new UserData(
                sharedPreferences.getString(KEY_USERNAME, null)
        );
    }

    //this method will logout the user
    public void logout() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
        //mCtx.startActivity(new Intent(mCtx, MainActivity.class));
        Toast.makeText(mCtx, "با موفقیت خارج شدید", Toast.LENGTH_SHORT).show();
    }
}
