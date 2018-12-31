package ir.tanyar.app;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.DexterError;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.PermissionRequestErrorListener;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.util.List;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class LoginPage extends AppCompatActivity implements View.OnClickListener {
    private final AppCompatActivity activity = LoginPage.this;


    private TextInputEditText textInputEditTextName;
    private TextInputEditText textInputEditTextPassword;

    private AppCompatButton appCompatButtonLogin;
    private UserSqliteHelper databaseHelper;

    private UserSqliteHelper dbh;
    private User user;

    private CoordinatorLayout coordinatorLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_page);

        if (UserSharedPref.getInstance(this).isLoggedIn()) {
            finish();
            startActivity(new Intent(this, Home.class));
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            StoragePermission();

        }else{

            dbh = new UserSqliteHelper(getApplicationContext());
            dbh.deleteAlluser();
            addUSERS();

        }

        initViews();
        initListeners();
        initObjects();

    }

    /**
     * This method is to initialize views
     */
    private void initViews() {

        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinatorLayout);

        textInputEditTextName = (TextInputEditText) findViewById(R.id.textInputEditTextName);
        textInputEditTextPassword = (TextInputEditText) findViewById(R.id.textInputEditTextPassword);

        appCompatButtonLogin = (AppCompatButton) findViewById(R.id.appCompatButtonLogin);


    }

    /**
     * This method is to initialize listeners
     */
    private void initListeners() {
        appCompatButtonLogin.setOnClickListener(this);
    }

    /**
     * This method is to initialize objects to be used
     */
    private void initObjects() {
        databaseHelper = new UserSqliteHelper(activity);

    }

    /**
     * This implemented method is to listen the click on view
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.appCompatButtonLogin:
                verifyFromSQLite();
                break;
        }
    }

    /**
     * This method is to validate the input text fields and verify login credentials from SQLite
     */
    private void verifyFromSQLite() {

        if (TextUtils.isEmpty(textInputEditTextName.getText().toString())) {
            Toast.makeText(getApplicationContext(),"نام کاربری را وارد کنید", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(textInputEditTextPassword.getText().toString())) {
            Toast.makeText(getApplicationContext(),"رمز عبور را وارد کنید", Toast.LENGTH_SHORT).show();
            return;
        }

        if (databaseHelper.checkUser(textInputEditTextName.getText().toString().trim()
                , textInputEditTextPassword.getText().toString().trim())) {

            //creating a new user object
            UserData user = new UserData(
                    textInputEditTextName.getText().toString().trim()
            );

            UserSharedPref.getInstance(getApplicationContext()).userLogin(user);

            Intent accountsIntent = new Intent(activity, Home.class);
            startActivity(accountsIntent);


        } else {
            // Snack Bar to show success message that record is wrong
            Toast.makeText(getApplicationContext(),"نام کاربری یا رمز عبور اشتباه است", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * This method is to empty all input edit text
     */
    private void emptyInputEditText() {
        textInputEditTextName.setText(null);
        textInputEditTextPassword.setText(null);
    }

    //add darmani1 product
    public void addUSERS (){
        try {
            File yourFile = new File(Environment.getExternalStorageDirectory(), "Tanyar/Data/user.json");
            FileInputStream stream = new FileInputStream(yourFile);
            String jsonStr = null;
            try {
                FileChannel fc = stream.getChannel();
                MappedByteBuffer bb = fc.map(FileChannel.MapMode.READ_ONLY, 0, fc.size());

                jsonStr = Charset.defaultCharset().decode(bb).toString();
            }
            catch(Exception e){
                e.printStackTrace();
            }
            finally {
                stream.close();
            }

            JSONObject obj = new JSONObject(jsonStr);
            JSONArray m_jArry = obj.getJSONArray("user");
            dbh = new UserSqliteHelper(getApplicationContext());
            //dbh.deleteAllProduct();

            for (int i = 0; i < m_jArry.length(); i++) {
                JSONObject jo_inside = m_jArry.getJSONObject(i);

                String name = jo_inside.getString("name");
                String username = jo_inside.getString("username");
                String password = jo_inside.getString("password");

                user = new User();
                user.setName(name);
                user.setUsername(username);
                user.setPassword(password);

                dbh.addUser(user);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void StoragePermission() {
        Dexter.withActivity(this)
                .withPermissions(
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                        )
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        // check if all permissions are granted
                        if (report.areAllPermissionsGranted()) {
                            //Toast.makeText(getApplicationContext(), "All permissions are granted!", Toast.LENGTH_SHORT).show();
                            dbh = new UserSqliteHelper(getApplicationContext());
                            dbh.deleteAlluser();
                            addUSERS();

                        }

                        // check for permanent denial of any permission
                        if (report.isAnyPermissionPermanentlyDenied()) {
                            // show alert dialog navigating to Settings

                            permission();

                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).
                withErrorListener(new PermissionRequestErrorListener() {
                    @Override
                    public void onError(DexterError error) {
                        Toast.makeText(getApplicationContext(), "Error occurred! ", Toast.LENGTH_SHORT).show();
                    }
                })
                .onSameThread()
                .check();
    }


    public void permission(){
        Snackbar snackbar = Snackbar
                .make(coordinatorLayout, "لطفا دسترسی به حافظه را از بخش تنظیمات تایید کنید", Snackbar.LENGTH_INDEFINITE)
                .setAction("تنظیمات", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        Uri uri = Uri.fromParts("package", getPackageName(), null);
                        intent.setData(uri);
                        startActivityForResult(intent, 101);

                    }
                });

        // Changing message text color
        snackbar.setActionTextColor(Color.WHITE);
        // Changing action button text color
        View sbView = snackbar.getView();
        sbView.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.colorAccent));
        TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(Color.WHITE);
        snackbar.show();

    }



    @Override
    public void onBackPressed() {

        moveTaskToBack(true);
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(0);

    }


    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }


}