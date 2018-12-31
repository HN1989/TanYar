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

public class OrderSqliteHelper extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "ordersdb";

    // User table name
    private static final String TABLE_KAFSH_ORDER = "kafshorder";
    private static final String TABLE_WITHPRICE_ORDER = "withpriceorder";
    private static final String TABLE_NOPRICE_ORDER = "nopriceorder";
    private static final String TABLE_OFFER_ORDER = "offerorder";

    // kafshorder Table Columns names
    private static final String COLUMN_KAFSH_ID = "kafsh_id";
    private static final String COLUMN_KAFSH_ROW = "kafsh_row";
    private static final String COLUMN_KAFSH_CODE = "kafsh_code";
    private static final String COLUMN_KAFSH_NAME= "kafsh_name";
    private static final String COLUMN_KAFSH_SELL = "kafsh_sell";
    private static final String COLUMN_KAFSH_COUNT = "kafsh_count";
    private static final String COLUMN_KAFSH_SIZE = "kafsh_size";
    private static final String COLUMN_KAFSH_PRICE = "kafsh_price";
    private static final String COLUMN_KAFSH_TOTAL = "kafsh_total";

    // with price Table Columns names
    private static final String COLUMN_WITHPRICE_ID = "withprice_id";
    private static final String COLUMN_WITHPRICE_ROW = "withprice_row";
    private static final String COLUMN_WITHPRICE_CODE = "withprice_code";
    private static final String COLUMN_WITHPRICE_NAME= "withprice_name";
    private static final String COLUMN_WITHPRICE_COUNT = "withprice_count";
    private static final String COLUMN_WITHPRICE_SIZE = "withprice_size";
    private static final String COLUMN_WITHPRICE_PRICE = "withprice_price";
    private static final String COLUMN_WITHPRICE_TOTAL = "withprice_total";

    // no price Table Columns names
    private static final String COLUMN_NOPRICE_ID = "noprice_id";
    private static final String COLUMN_NOPRICE_ROW = "noprice_row";
    private static final String COLUMN_NOPRICE_CODE = "noprice_code";
    private static final String COLUMN_NOPRICE_NAME= "noprice_name";
    private static final String COLUMN_NOPRICE_COUNT = "noprice_count";
    private static final String COLUMN_NOPRICE_SIZE = "noprice_size";
    private static final String COLUMN_NOPRICE_PRICE = "noprice_price";
    private static final String COLUMN_NOPRICE_TOTAL = "noprice_total";

    // offer Table Columns names
    private static final String COLUMN_OFFER_ID = "offer_id";
    private static final String COLUMN_OFFER_ROW = "offer_row";
    private static final String COLUMN_OFFER_CODE = "offer_code";
    private static final String COLUMN_OFFER_NAME= "offer_name";
    private static final String COLUMN_OFFER_COUNT = "offer_count";
    private static final String COLUMN_OFFER_SIZE = "offer_size";
    private static final String COLUMN_OFFER_PRICE = "offer_price";
    private static final String COLUMN_OFFER_TOTAL = "offer_total";

    // create table sql query
    private String CREATE_KAFSH_TABLE = "CREATE TABLE " + TABLE_KAFSH_ORDER + "("
            + COLUMN_KAFSH_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_KAFSH_ROW + " INTEGER,"
            + COLUMN_KAFSH_CODE + " TEXT,"
            + COLUMN_KAFSH_NAME + " TEXT,"
            + COLUMN_KAFSH_SELL + " TEXT,"
            + COLUMN_KAFSH_COUNT + " TEXT,"
            + COLUMN_KAFSH_SIZE + " TEXT,"
            + COLUMN_KAFSH_PRICE + " TEXT,"
            + COLUMN_KAFSH_TOTAL + " TEXT" + ")";

    // create table sql query
    private String CREATE_WITHPRICE_TABLE = "CREATE TABLE " + TABLE_WITHPRICE_ORDER + "("
            + COLUMN_WITHPRICE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_WITHPRICE_ROW + " INTEGER,"
            + COLUMN_WITHPRICE_CODE + " TEXT,"
            + COLUMN_WITHPRICE_NAME + " TEXT,"
            + COLUMN_WITHPRICE_COUNT + " TEXT,"
            + COLUMN_WITHPRICE_SIZE + " TEXT,"
            + COLUMN_WITHPRICE_PRICE + " TEXT,"
            + COLUMN_WITHPRICE_TOTAL + " TEXT" + ")";

    // create table sql query
    private String CREATE_NOPRICE_TABLE = "CREATE TABLE " + TABLE_NOPRICE_ORDER + "("
            + COLUMN_NOPRICE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_NOPRICE_ROW + " INTEGER,"
            + COLUMN_NOPRICE_CODE + " TEXT,"
            + COLUMN_NOPRICE_NAME + " TEXT,"
            + COLUMN_NOPRICE_COUNT + " TEXT,"
            + COLUMN_NOPRICE_SIZE + " TEXT,"
            + COLUMN_NOPRICE_PRICE + " TEXT,"
            + COLUMN_NOPRICE_TOTAL + " TEXT" + ")";


    // create table sql query
    private String CREATE_OFFER_TABLE = "CREATE TABLE " + TABLE_OFFER_ORDER + "("
            + COLUMN_OFFER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_OFFER_ROW + " INTEGER,"
            + COLUMN_OFFER_CODE + " TEXT,"
            + COLUMN_OFFER_NAME + " TEXT,"
            + COLUMN_OFFER_COUNT + " TEXT,"
            + COLUMN_OFFER_SIZE + " TEXT,"
            + COLUMN_OFFER_PRICE + " TEXT,"
            + COLUMN_OFFER_TOTAL + " TEXT" + ")";

    // drop table sql query
    private String DROP_KAFSH_TABLE = "DROP TABLE IF EXISTS " + TABLE_KAFSH_ORDER;
    private String DROP_WITHPRICE_TABLE = "DROP TABLE IF EXISTS " + TABLE_WITHPRICE_ORDER;
    private String DROP_NOPRICE_TABLE = "DROP TABLE IF EXISTS " + TABLE_NOPRICE_ORDER;
    private String DROP_OFFER_TABLE = "DROP TABLE IF EXISTS " + TABLE_OFFER_ORDER;

    /**
     * Constructor
     *
     * @param context
     */

    public OrderSqliteHelper(final Context context) {
        super(context, Environment.getExternalStorageDirectory()
                + File.separator + "Tanyar/DB"
                + File.separator + DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_KAFSH_TABLE);
        db.execSQL(CREATE_WITHPRICE_TABLE);
        db.execSQL(CREATE_NOPRICE_TABLE);
        db.execSQL(CREATE_OFFER_TABLE);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        //Drop User Table if exist
        db.execSQL(DROP_KAFSH_TABLE);
        db.execSQL(DROP_WITHPRICE_TABLE);
        db.execSQL(DROP_NOPRICE_TABLE);
        db.execSQL(DROP_OFFER_TABLE);
        // Create tables again
        onCreate(db);

    }


    //KAFSH ORDER METHOD
    public void addKafshOrder(KafshOrderModel kafsh) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_KAFSH_ROW, kafsh.getRow());
        values.put(COLUMN_KAFSH_CODE, kafsh.getCode());
        values.put(COLUMN_KAFSH_NAME, kafsh.getName());
        values.put(COLUMN_KAFSH_SELL, kafsh.getSell());
        values.put(COLUMN_KAFSH_COUNT, kafsh.getCount());
        values.put(COLUMN_KAFSH_SIZE, kafsh.getSize());
        values.put(COLUMN_KAFSH_PRICE, kafsh.getPrice());
        values.put(COLUMN_KAFSH_TOTAL, kafsh.getTotal());

        // Inserting Row
        db.insert(TABLE_KAFSH_ORDER, null, values);
        db.close();
    }

    /**
     * This method is to fetch all product and return the list of user records
     *
     * @return list
     */
    public List<KafshOrderModel> getAllOrder() {
        // array of columns to fetch
        String[] columns = {
                COLUMN_KAFSH_ID,
                COLUMN_KAFSH_ROW,
                COLUMN_KAFSH_CODE,
                COLUMN_KAFSH_NAME,
                COLUMN_KAFSH_SELL,
                COLUMN_KAFSH_COUNT,
                COLUMN_KAFSH_SIZE,
                COLUMN_KAFSH_PRICE,
                COLUMN_KAFSH_TOTAL
        };
        // sorting orders
        String sortOrder =
                COLUMN_KAFSH_ROW + " ASC";
        List<KafshOrderModel> proList = new ArrayList<KafshOrderModel>();

        SQLiteDatabase db = this.getReadableDatabase();

        // query the user table
        /**
         * Here query function is used to fetch records from user table this function works like we use sql query.
         * SQL query equivalent to this query function is
         * SELECT user_id,user_name,user_email,user_password FROM user ORDER BY user_name;
         */
        Cursor cursor = db.query(TABLE_KAFSH_ORDER, //Table to query
                columns,    //columns to return
                null,        //columns for the WHERE clause
                null,        //The values for the WHERE clause
                null,       //group the rows
                null,       //filter by row groups
                sortOrder); //The sort order


        // Traversing through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                KafshOrderModel pro = new KafshOrderModel();
                pro.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_KAFSH_ID))));
                pro.setRow(cursor.getString(cursor.getColumnIndex(COLUMN_KAFSH_ROW)));
                pro.setCode(cursor.getString(cursor.getColumnIndex(COLUMN_KAFSH_CODE)));
                pro.setName(cursor.getString(cursor.getColumnIndex(COLUMN_KAFSH_NAME)));
                pro.setSell(cursor.getString(cursor.getColumnIndex(COLUMN_KAFSH_SELL)));
                pro.setCount(cursor.getString(cursor.getColumnIndex(COLUMN_KAFSH_COUNT)));
                pro.setSize(cursor.getString(cursor.getColumnIndex(COLUMN_KAFSH_SIZE)));
                pro.setPrice(cursor.getString(cursor.getColumnIndex(COLUMN_KAFSH_PRICE)));
                pro.setTotal(cursor.getString(cursor.getColumnIndex(COLUMN_KAFSH_TOTAL)));
                // Adding user record to list
                proList.add(pro);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        // return user list
        return proList;
    }

    public void deleteAllOrder() {
        SQLiteDatabase db = this.getWritableDatabase();

        db.execSQL("delete from "+ TABLE_KAFSH_ORDER);
        db.execSQL("DELETE FROM SQLITE_SEQUENCE WHERE NAME = '" + TABLE_KAFSH_ORDER + "'");
        db.close();
    }

    public int getsumkafshOrder() {
        SQLiteDatabase db = this.getWritableDatabase();
         int total=0;
        Cursor cursor = db.rawQuery("SELECT SUM(" + COLUMN_KAFSH_TOTAL + ") as Total FROM " + TABLE_KAFSH_ORDER, null);
        if (cursor.moveToFirst()) {
            total = cursor.getInt(cursor.getColumnIndex("Total"));
        }
        return total;
    }

    public int getcountkafshOrder() {
        SQLiteDatabase db = this.getWritableDatabase();
        int total=0;
        Cursor cursor = db.rawQuery("SELECT SUM(" + COLUMN_KAFSH_COUNT + ") as Total FROM " + TABLE_KAFSH_ORDER, null);
        if (cursor.moveToFirst()) {
            total = cursor.getInt(cursor.getColumnIndex("Total"));
        }
        return total;
    }


    //WITHPRICE ORDER METHOD
    public void addWithpriceOrder(DarmaniOrderModel withprice) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_WITHPRICE_ROW, withprice.getRow());
        values.put(COLUMN_WITHPRICE_CODE, withprice.getCode());
        values.put(COLUMN_WITHPRICE_NAME, withprice.getName());
        values.put(COLUMN_WITHPRICE_COUNT, withprice.getCount());
        values.put(COLUMN_WITHPRICE_SIZE, withprice.getSize());
        values.put(COLUMN_WITHPRICE_PRICE, withprice.getPrice());
        values.put(COLUMN_WITHPRICE_TOTAL, withprice.getTotal());

        // Inserting Row
        db.insert(TABLE_WITHPRICE_ORDER, null, values);
        db.close();
    }

    /**
     * This method is to fetch all product and return the list of user records
     *
     * @return list
     */
    public List<DarmaniOrderModel> getAllWithpriceOrder() {
        // array of columns to fetch
        String[] columns = {
                COLUMN_WITHPRICE_ID,
                COLUMN_WITHPRICE_ROW,
                COLUMN_WITHPRICE_CODE,
                COLUMN_WITHPRICE_NAME,
                COLUMN_WITHPRICE_COUNT,
                COLUMN_WITHPRICE_SIZE,
                COLUMN_WITHPRICE_PRICE,
                COLUMN_WITHPRICE_TOTAL
        };
        // sorting orders
        String sortOrder =
                COLUMN_WITHPRICE_ROW + " ASC";
        List<DarmaniOrderModel> proList = new ArrayList<DarmaniOrderModel>();

        SQLiteDatabase db = this.getReadableDatabase();

        // query the user table
        /**
         * Here query function is used to fetch records from user table this function works like we use sql query.
         * SQL query equivalent to this query function is
         * SELECT user_id,user_name,user_email,user_password FROM user ORDER BY user_name;
         */
        Cursor cursor = db.query(TABLE_WITHPRICE_ORDER, //Table to query
                columns,    //columns to return
                null,        //columns for the WHERE clause
                null,        //The values for the WHERE clause
                null,       //group the rows
                null,       //filter by row groups
                sortOrder); //The sort order


        // Traversing through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                DarmaniOrderModel pro = new DarmaniOrderModel();
                pro.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_WITHPRICE_ID))));
                pro.setRow(cursor.getString(cursor.getColumnIndex(COLUMN_WITHPRICE_ROW)));
                pro.setCode(cursor.getString(cursor.getColumnIndex(COLUMN_WITHPRICE_CODE)));
                pro.setName(cursor.getString(cursor.getColumnIndex(COLUMN_WITHPRICE_NAME)));
                pro.setCount(cursor.getString(cursor.getColumnIndex(COLUMN_WITHPRICE_COUNT)));
                pro.setSize(cursor.getString(cursor.getColumnIndex(COLUMN_WITHPRICE_SIZE)));
                pro.setPrice(cursor.getString(cursor.getColumnIndex(COLUMN_WITHPRICE_PRICE)));
                pro.setTotal(cursor.getString(cursor.getColumnIndex(COLUMN_WITHPRICE_TOTAL)));
                // Adding user record to list
                proList.add(pro);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        // return user list
        return proList;
    }

    public void deleteAllWithpriceOrder() {
        SQLiteDatabase db = this.getWritableDatabase();

        db.execSQL("delete from "+ TABLE_WITHPRICE_ORDER);
        db.execSQL("DELETE FROM SQLITE_SEQUENCE WHERE NAME = '" + TABLE_WITHPRICE_ORDER + "'");
        db.close();
    }

    public int getsumWithpriceOrder() {
        SQLiteDatabase db = this.getWritableDatabase();
        int total=0;
        Cursor cursor = db.rawQuery("SELECT SUM(" + COLUMN_WITHPRICE_TOTAL + ") as Total FROM " + TABLE_WITHPRICE_ORDER, null);
        if (cursor.moveToFirst()) {
            total = cursor.getInt(cursor.getColumnIndex("Total"));
        }
        return total;
    }

    public int getcountWithpriceOrder() {
        SQLiteDatabase db = this.getWritableDatabase();
        int total=0;
        Cursor cursor = db.rawQuery("SELECT SUM(" + COLUMN_WITHPRICE_COUNT + ") as Total FROM " + TABLE_WITHPRICE_ORDER, null);
        if (cursor.moveToFirst()) {
            total = cursor.getInt(cursor.getColumnIndex("Total"));
        }
        return total;
    }

    //NOPRICE ORDER METHOD
    public void addNOpriceOrder(DarmaniOrderModel noprice) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_NOPRICE_ROW, noprice.getRow());
        values.put(COLUMN_NOPRICE_CODE, noprice.getCode());
        values.put(COLUMN_NOPRICE_NAME, noprice.getName());
        values.put(COLUMN_NOPRICE_COUNT, noprice.getCount());
        values.put(COLUMN_NOPRICE_SIZE, noprice.getSize());
        values.put(COLUMN_NOPRICE_PRICE, noprice.getPrice());
        values.put(COLUMN_NOPRICE_TOTAL, noprice.getTotal());

        // Inserting Row
        db.insert(TABLE_NOPRICE_ORDER, null, values);
        db.close();
    }

    /**
     * This method is to fetch all product and return the list of user records
     *
     * @return list
     */
    public List<DarmaniOrderModel> getAllNOpriceOrder() {
        // array of columns to fetch
        String[] columns = {
                COLUMN_NOPRICE_ID,
                COLUMN_NOPRICE_ROW,
                COLUMN_NOPRICE_CODE,
                COLUMN_NOPRICE_NAME,
                COLUMN_NOPRICE_COUNT,
                COLUMN_NOPRICE_SIZE,
                COLUMN_NOPRICE_PRICE,
                COLUMN_NOPRICE_TOTAL
        };
        // sorting orders
        String sortOrder =
                COLUMN_NOPRICE_ROW + " ASC";
        List<DarmaniOrderModel> proList = new ArrayList<DarmaniOrderModel>();

        SQLiteDatabase db = this.getReadableDatabase();

        // query the user table
        /**
         * Here query function is used to fetch records from user table this function works like we use sql query.
         * SQL query equivalent to this query function is
         * SELECT user_id,user_name,user_email,user_password FROM user ORDER BY user_name;
         */
        Cursor cursor = db.query(TABLE_NOPRICE_ORDER, //Table to query
                columns,    //columns to return
                null,        //columns for the WHERE clause
                null,        //The values for the WHERE clause
                null,       //group the rows
                null,       //filter by row groups
                sortOrder); //The sort order


        // Traversing through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                DarmaniOrderModel pro = new DarmaniOrderModel();
                pro.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_NOPRICE_ID))));
                pro.setRow(cursor.getString(cursor.getColumnIndex(COLUMN_NOPRICE_ROW)));
                pro.setCode(cursor.getString(cursor.getColumnIndex(COLUMN_NOPRICE_CODE)));
                pro.setName(cursor.getString(cursor.getColumnIndex(COLUMN_NOPRICE_NAME)));
                pro.setCount(cursor.getString(cursor.getColumnIndex(COLUMN_NOPRICE_COUNT)));
                pro.setSize(cursor.getString(cursor.getColumnIndex(COLUMN_NOPRICE_SIZE)));
                pro.setPrice(cursor.getString(cursor.getColumnIndex(COLUMN_NOPRICE_PRICE)));
                pro.setTotal(cursor.getString(cursor.getColumnIndex(COLUMN_NOPRICE_TOTAL)));
                // Adding user record to list
                proList.add(pro);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        // return user list
        return proList;
    }

    public void deleteAllNOpriceOrder() {
        SQLiteDatabase db = this.getWritableDatabase();

        db.execSQL("delete from "+ TABLE_NOPRICE_ORDER);
        db.execSQL("DELETE FROM SQLITE_SEQUENCE WHERE NAME = '" + TABLE_NOPRICE_ORDER + "'");
        db.close();
    }

    public int getsumNOpriceOrder() {
        SQLiteDatabase db = this.getWritableDatabase();
        int total=0;
        Cursor cursor = db.rawQuery("SELECT SUM(" + COLUMN_NOPRICE_TOTAL + ") as Total FROM " + TABLE_NOPRICE_ORDER, null);
        if (cursor.moveToFirst()) {
            total = cursor.getInt(cursor.getColumnIndex("Total"));
        }
        return total;
    }

    public int getcountNOpriceOrder() {
        SQLiteDatabase db = this.getWritableDatabase();
        int total=0;
        Cursor cursor = db.rawQuery("SELECT SUM(" + COLUMN_NOPRICE_COUNT + ") as Total FROM " + TABLE_NOPRICE_ORDER, null);
        if (cursor.moveToFirst()) {
            total = cursor.getInt(cursor.getColumnIndex("Total"));
        }
        return total;
    }


    //OFFER ORDER METHOD
    public void addOfferOrder(DarmaniOrderModel offer) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_OFFER_ROW, offer.getRow());
        values.put(COLUMN_OFFER_CODE, offer.getCode());
        values.put(COLUMN_OFFER_NAME, offer.getName());
        values.put(COLUMN_OFFER_COUNT, offer.getCount());
        values.put(COLUMN_OFFER_SIZE, offer.getSize());
        values.put(COLUMN_OFFER_PRICE, offer.getPrice());
        values.put(COLUMN_OFFER_TOTAL, offer.getTotal());

        // Inserting Row
        db.insert(TABLE_OFFER_ORDER, null, values);
        db.close();
    }

    /**
     * This method is to fetch all product and return the list of user records
     *
     * @return list
     */
    public List<DarmaniOrderModel> getAllOfferOrder() {
        // array of columns to fetch
        String[] columns = {
                COLUMN_OFFER_ID,
                COLUMN_OFFER_ROW,
                COLUMN_OFFER_CODE,
                COLUMN_OFFER_NAME,
                COLUMN_OFFER_COUNT,
                COLUMN_OFFER_SIZE,
                COLUMN_OFFER_PRICE,
                COLUMN_OFFER_TOTAL
        };
        // sorting orders
        String sortOrder =
                COLUMN_OFFER_ROW + " ASC";
        List<DarmaniOrderModel> proList = new ArrayList<DarmaniOrderModel>();

        SQLiteDatabase db = this.getReadableDatabase();

        // query the user table
        /**
         * Here query function is used to fetch records from user table this function works like we use sql query.
         * SQL query equivalent to this query function is
         * SELECT user_id,user_name,user_email,user_password FROM user ORDER BY user_name;
         */
        Cursor cursor = db.query(TABLE_OFFER_ORDER, //Table to query
                columns,    //columns to return
                null,        //columns for the WHERE clause
                null,        //The values for the WHERE clause
                null,       //group the rows
                null,       //filter by row groups
                sortOrder); //The sort order


        // Traversing through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                DarmaniOrderModel pro = new DarmaniOrderModel();
                pro.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_OFFER_ID))));
                pro.setRow(cursor.getString(cursor.getColumnIndex(COLUMN_OFFER_ROW)));
                pro.setCode(cursor.getString(cursor.getColumnIndex(COLUMN_OFFER_CODE)));
                pro.setName(cursor.getString(cursor.getColumnIndex(COLUMN_OFFER_NAME)));
                pro.setCount(cursor.getString(cursor.getColumnIndex(COLUMN_OFFER_COUNT)));
                pro.setSize(cursor.getString(cursor.getColumnIndex(COLUMN_OFFER_SIZE)));
                pro.setPrice(cursor.getString(cursor.getColumnIndex(COLUMN_OFFER_PRICE)));
                pro.setTotal(cursor.getString(cursor.getColumnIndex(COLUMN_OFFER_TOTAL)));
                // Adding user record to list
                proList.add(pro);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        // return user list
        return proList;
    }

    public void deleteAllOfferOrder() {
        SQLiteDatabase db = this.getWritableDatabase();

        db.execSQL("delete from "+ TABLE_OFFER_ORDER);
        db.execSQL("DELETE FROM SQLITE_SEQUENCE WHERE NAME = '" + TABLE_OFFER_ORDER + "'");
        db.close();
    }

    public int getsumOfferOrder() {
        SQLiteDatabase db = this.getWritableDatabase();
        int total=0;
        Cursor cursor = db.rawQuery("SELECT SUM(" + COLUMN_OFFER_TOTAL + ") as Total FROM " + TABLE_OFFER_ORDER, null);
        if (cursor.moveToFirst()) {
            total = cursor.getInt(cursor.getColumnIndex("Total"));
        }
        return total;
    }

    public int getcountOfferOrder() {
        SQLiteDatabase db = this.getWritableDatabase();
        int total=0;
        Cursor cursor = db.rawQuery("SELECT SUM(" + COLUMN_OFFER_COUNT + ") as Total FROM " + TABLE_OFFER_ORDER, null);
        if (cursor.moveToFirst()) {
            total = cursor.getInt(cursor.getColumnIndex("Total"));
        }
        return total;
    }

    public void deletekafshorder(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from "+TABLE_KAFSH_ORDER+" where kafsh_id='"+id+"'");
    }

    public void deletewithpriceorder(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from "+TABLE_WITHPRICE_ORDER+" where withprice_id='"+id+"'");
    }

    public void deletenopriceorder(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from "+TABLE_NOPRICE_ORDER+" where noprice_id='"+id+"'");
    }

    //public void deleteofferorder(String code,String count) {
       // SQLiteDatabase db = this.getWritableDatabase();
       // db.execSQL("delete from "+TABLE_OFFER_ORDER+" where offer_code='"+code+"' and offer_count='"+count+"'");
    //}

    public void deleteofferorder(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from "+TABLE_OFFER_ORDER+" where offer_id='"+id+"'");
    }

}