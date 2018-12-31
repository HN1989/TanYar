package ir.tanyar.app;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class CustomerSqliteHelper extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "customers";

    // User table name
    private static final String TABLE_CUSTOMER = "customer";

    // User Table Columns names
    private static final String COLUMN_CUSTOMER_ID = "customer_id";
    private static final String COLUMN_CUSTOMER_TYPE = "customer_type";
    private static final String COLUMN_CUSTOMER_NAME = "customer_name";
    private static final String COLUMN_CUSTOMER_MANAGER = "customer_manager";
    private static final String COLUMN_CUSTOMER_STATE = "customer_state";
    private static final String COLUMN_CUSTOMER_CITY = "customer_city";
    private static final String COLUMN_CUSTOMER_AREA = "customer_area";
    private static final String COLUMN_CUSTOMER_ADRES = "customer_adres";
    private static final String COLUMN_CUSTOMER_ZIPCODE = "customer_zipcode";
    private static final String COLUMN_CUSTOMER_PHONE = "customer_phone";
    private static final String COLUMN_CUSTOMER_MOBILE = "customer_mobile";
    private static final String COLUMN_CUSTOMER_NETWORKER = "customer_networker";

    // create table sql query
    private String CREATE_CUSTOMER_TABLE = "CREATE TABLE " + TABLE_CUSTOMER + "("
            + COLUMN_CUSTOMER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_CUSTOMER_TYPE + " TEXT,"
            + COLUMN_CUSTOMER_NAME + " TEXT,"
            + COLUMN_CUSTOMER_MANAGER + " TEXT,"
            + COLUMN_CUSTOMER_STATE + " TEXT,"
            + COLUMN_CUSTOMER_CITY + " TEXT,"
            + COLUMN_CUSTOMER_AREA + " TEXT,"
            + COLUMN_CUSTOMER_ADRES + " TEXT,"
            + COLUMN_CUSTOMER_ZIPCODE + " TEXT,"
            + COLUMN_CUSTOMER_PHONE + " TEXT,"
            + COLUMN_CUSTOMER_MOBILE + " TEXT,"
            + COLUMN_CUSTOMER_NETWORKER + " TEXT" + ")";

    // drop table sql query
    private String DROP_CUSTOMER_TABLE = "DROP TABLE IF EXISTS " + TABLE_CUSTOMER;

    /**
     * Constructor
     *
     * @param context
     */

    public CustomerSqliteHelper(final Context context) {
        super(context, Environment.getExternalStorageDirectory()
                + File.separator + "Tanyar/Customer"
                + File.separator + DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_CUSTOMER_TABLE);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        //Drop User Table if exist
        db.execSQL(DROP_CUSTOMER_TABLE);

        // Create tables again
        onCreate(db);

    }

    /**
     * This method is to create user record
     *
     * @param customer
     */
    public void addCustomer(CustomerModel customer) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_CUSTOMER_TYPE, customer.getType());
        values.put(COLUMN_CUSTOMER_NAME, customer.getName());
        values.put(COLUMN_CUSTOMER_MANAGER, customer.getManager());
        values.put(COLUMN_CUSTOMER_STATE, customer.getState());
        values.put(COLUMN_CUSTOMER_CITY, customer.getCity());
        values.put(COLUMN_CUSTOMER_AREA, customer.getArea());
        values.put(COLUMN_CUSTOMER_ADRES, customer.getAdres());
        values.put(COLUMN_CUSTOMER_ZIPCODE, customer.getZipcode());
        values.put(COLUMN_CUSTOMER_PHONE, customer.getPhone());
        values.put(COLUMN_CUSTOMER_MOBILE, customer.getMobile());
        values.put(COLUMN_CUSTOMER_NETWORKER, customer.getNetworker());

        // Inserting Row
        db.insert(TABLE_CUSTOMER, null, values);
        db.close();
    }

    /**
     * This method is to create user record
     *
     * @param customer
     */
    public void updateCustomer(CustomerModel customer,int id) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_CUSTOMER_TYPE, customer.getType());
        values.put(COLUMN_CUSTOMER_NAME, customer.getName());
        values.put(COLUMN_CUSTOMER_MANAGER, customer.getManager());
        values.put(COLUMN_CUSTOMER_STATE, customer.getState());
        values.put(COLUMN_CUSTOMER_CITY, customer.getCity());
        values.put(COLUMN_CUSTOMER_AREA, customer.getArea());
        values.put(COLUMN_CUSTOMER_ADRES, customer.getAdres());
        values.put(COLUMN_CUSTOMER_ZIPCODE, customer.getZipcode());
        values.put(COLUMN_CUSTOMER_PHONE, customer.getPhone());
        values.put(COLUMN_CUSTOMER_MOBILE, customer.getMobile());
        values.put(COLUMN_CUSTOMER_NETWORKER, customer.getNetworker());

        // Inserting Row
        db.update(TABLE_CUSTOMER, values, "customer_id="+id, null);
        db.close();
    }



    /**
     * This method is to fetch all user and return the list of user records
     *
     * @return list
     */
    public List<CustomerModel> getMyCustomer() {
        // array of columns to fetch
        String[] columns = {
                COLUMN_CUSTOMER_ID,
                COLUMN_CUSTOMER_TYPE,
                COLUMN_CUSTOMER_NAME,
                COLUMN_CUSTOMER_MANAGER,
                COLUMN_CUSTOMER_STATE,
                COLUMN_CUSTOMER_CITY,
                COLUMN_CUSTOMER_AREA,
                COLUMN_CUSTOMER_ADRES,
                COLUMN_CUSTOMER_ZIPCODE,
                COLUMN_CUSTOMER_PHONE,
                COLUMN_CUSTOMER_MOBILE,
                COLUMN_CUSTOMER_NETWORKER
        };
        // sorting orders
        String sortOrder =
                COLUMN_CUSTOMER_ID + " ASC";
        List<CustomerModel> userList = new ArrayList<CustomerModel>();

        SQLiteDatabase db = this.getReadableDatabase();

        // query the user table
        /**
         * Here query function is used to fetch records from user table this function works like we use sql query.
         * SQL query equivalent to this query function is
         * SELECT user_id,user_name,user_email,user_password FROM user ORDER BY user_name;
         */
        Cursor cursor = db.query(TABLE_CUSTOMER, //Table to query
                columns,    //columns to return
                null,        //columns for the WHERE clause
                null,        //The values for the WHERE clause
                null,       //group the rows
                null,       //filter by row groups
                sortOrder); //The sort order


        // Traversing through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                CustomerModel user = new CustomerModel();
                user.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_CUSTOMER_ID))));
                user.setType(cursor.getString(cursor.getColumnIndex(COLUMN_CUSTOMER_TYPE)));
                user.setName(cursor.getString(cursor.getColumnIndex(COLUMN_CUSTOMER_NAME)));
                user.setManager(cursor.getString(cursor.getColumnIndex(COLUMN_CUSTOMER_MANAGER)));
                user.setState(cursor.getString(cursor.getColumnIndex(COLUMN_CUSTOMER_STATE)));
                user.setCity(cursor.getString(cursor.getColumnIndex(COLUMN_CUSTOMER_CITY)));
                user.setArea(cursor.getString(cursor.getColumnIndex(COLUMN_CUSTOMER_AREA)));
                user.setAdres(cursor.getString(cursor.getColumnIndex(COLUMN_CUSTOMER_ADRES)));
                user.setZipcode(cursor.getString(cursor.getColumnIndex(COLUMN_CUSTOMER_ZIPCODE)));
                user.setPhone(cursor.getString(cursor.getColumnIndex(COLUMN_CUSTOMER_PHONE)));
                user.setMobile(cursor.getString(cursor.getColumnIndex(COLUMN_CUSTOMER_MOBILE)));
                user.setNetworker(cursor.getString(cursor.getColumnIndex(COLUMN_CUSTOMER_NETWORKER)));

                // Adding user record to list
                userList.add(user);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        // return user list
        return userList;
    }


    public void deleteCustomer(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from "+TABLE_CUSTOMER+" where customer_id='"+id+"'");
    }
}