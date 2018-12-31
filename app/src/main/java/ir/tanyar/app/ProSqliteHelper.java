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

public class ProSqliteHelper extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "allproducts";

    // table name
    private static final String TABLE_KAFSH = "kafsh";
    private static final String TABLE_DARMANI1 = "darmani1";
    private static final String TABLE_DARMANI2 = "darmani2";


    // kafsh Table Columns names
    private static final String COLUMN_KAFSH_ID = "kafsh_id";
    private static final String COLUMN_KAFSH_RADIF = "kafsh_radif";
    private static final String COLUMN_KAFSH_GROUP = "kafsh_group";
    private static final String COLUMN_KAFSH_CODE = "kafsh_code";
    private static final String COLUMN_KAFSH_COLOR = "kafsh_color";
    private static final String COLUMN_KAFSH_PROPERTY = "kafsh_property";
    private static final String COLUMN_KAFSH_BOX = "kafsh_box";
    private static final String COLUMN_KAFSH_HULFBOX = "kafsh_hulfbox";
    private static final String COLUMN_KAFSH_JUR = "kafsh_jur";
    private static final String COLUMN_KAFSH_NAJUR = "kafsh_najur";
    private static final String COLUMN_KAFSH_BRAND1NAME = "kafsh_b1name";
    private static final String COLUMN_KAFSH_BRAND1PRICE = "kafsh_b1price";
    private static final String COLUMN_KAFSH_BRAND2NAME = "kafsh_b2name";
    private static final String COLUMN_KAFSH_BRAND2PRICE = "kafsh_b2price";
    private static final String COLUMN_KAFSH_BRAND3NAME = "kafsh_b3name";
    private static final String COLUMN_KAFSH_BRAND3PRICE = "kafsh_b3price";
    private static final String COLUMN_KAFSH_BRAND4NAME = "kafsh_b4name";
    private static final String COLUMN_KAFSH_BRAND4PRICE = "kafsh_b4price";
    private static final String COLUMN_KAFSH_SIZE = "kafsh_size";
    private static final String COLUMN_KAFSH_SELLTYPE = "kafsh_selltype";

    // darmani1 Table Columns names
    private static final String COLUMN_DARMANI1_ID = "darmani1_id";
    private static final String COLUMN_DARMANI1_ROW = "darmani1_row";
    private static final String COLUMN_DARMANI1_CODE = "darmani1_code";
    private static final String COLUMN_DARMANI1_NAME = "darmani1_name";
    private static final String COLUMN_DARMANI1_SIZE = "darmani1_size";
    private static final String COLUMN_DARMANI1_PROPERTY = "darmani1_property";
    private static final String COLUMN_DARMANI1_BUYER = "darmani1_buyer";
    private static final String COLUMN_DARMANI1_CONSUMER = "darmani1_consumer";
    private static final String COLUMN_DARMANI1_BRAND1NAME = "darmani1_b1name";
    private static final String COLUMN_DARMANI1_BRAND1PRICE = "darmani1_b1price";
    private static final String COLUMN_DARMANI1_BRAND2NAME = "darmani1_b2name";
    private static final String COLUMN_DARMANI1_BRAND2PRICE = "darmani1_b2price";
    private static final String COLUMN_DARMANI1_BRAND3NAME = "darmani1_b3name";
    private static final String COLUMN_DARMANI1_BRAND3PRICE = "darmani1_b3price";
    private static final String COLUMN_DARMANI1_BRAND4NAME = "darmani1_b4name";
    private static final String COLUMN_DARMANI1_BRAND4PRICE = "darmani1_b4price";

    // darmani2 Table Columns names
    private static final String COLUMN_DARMANI2_ID = "darmani2_id";
    private static final String COLUMN_DARMANI2_ROW = "darmani2_row";
    private static final String COLUMN_DARMANI2_CODE = "darmani2_code";
    private static final String COLUMN_DARMANI2_NAME = "darmani2_name";
    private static final String COLUMN_DARMANI2_SIZE = "darmani2_size";
    private static final String COLUMN_DARMANI2_PROPERTY = "darmani2_property";
    private static final String COLUMN_DARMANI2_BUYER = "darmani2_buyer";
    private static final String COLUMN_DARMANI2_CONSUMER = "darmani2_consumer";
    private static final String COLUMN_DARMANI2_BRAND1NAME = "darmani2_b1name";
    private static final String COLUMN_DARMANI2_BRAND1PRICE = "darmani2_b1price";
    private static final String COLUMN_DARMANI2_BRAND2NAME = "darmani2_b2name";
    private static final String COLUMN_DARMANI2_BRAND2PRICE = "darmani2_b2price";
    private static final String COLUMN_DARMANI2_BRAND3NAME = "darmani2_b3name";
    private static final String COLUMN_DARMANI2_BRAND3PRICE = "darmani2_b3price";
    private static final String COLUMN_DARMANI2_BRAND4NAME = "darmani2_b4name";
    private static final String COLUMN_DARMANI2_BRAND4PRICE = "darmani2_b4price";

    // create table sql query
    private String CREATE_KAFSH_TABLE = "CREATE TABLE " + TABLE_KAFSH + "("
            + COLUMN_KAFSH_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_KAFSH_RADIF + " INTEGER,"
            + COLUMN_KAFSH_GROUP + " TEXT,"
            + COLUMN_KAFSH_CODE + " TEXT,"
            + COLUMN_KAFSH_COLOR + " TEXT,"
            + COLUMN_KAFSH_PROPERTY + " TEXT,"
            + COLUMN_KAFSH_BOX + " TEXT,"
            + COLUMN_KAFSH_HULFBOX + " TEXT,"
            + COLUMN_KAFSH_JUR + " TEXT,"
            + COLUMN_KAFSH_NAJUR + " TEXT,"
            + COLUMN_KAFSH_BRAND1NAME + " TEXT,"
            + COLUMN_KAFSH_BRAND1PRICE + " TEXT,"
            + COLUMN_KAFSH_BRAND2NAME + " TEXT,"
            + COLUMN_KAFSH_BRAND2PRICE + " TEXT,"
            + COLUMN_KAFSH_BRAND3NAME + " TEXT,"
            + COLUMN_KAFSH_BRAND3PRICE + " TEXT,"
            + COLUMN_KAFSH_BRAND4NAME + " TEXT,"
            + COLUMN_KAFSH_BRAND4PRICE + " TEXT,"
            + COLUMN_KAFSH_SIZE + " TEXT,"
            + COLUMN_KAFSH_SELLTYPE + " TEXT" + ")";


    // create table sql query
    private String CREATE_DARMANI1_TABLE = "CREATE TABLE " + TABLE_DARMANI1 + "("
            + COLUMN_DARMANI1_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_DARMANI1_ROW + " INTEGER,"
            + COLUMN_DARMANI1_CODE + " TEXT,"
            + COLUMN_DARMANI1_NAME + " TEXT,"
            + COLUMN_DARMANI1_SIZE + " TEXT,"
            + COLUMN_DARMANI1_PROPERTY + " TEXT,"
            + COLUMN_DARMANI1_BUYER + " TEXT,"
            + COLUMN_DARMANI1_CONSUMER + " TEXT,"
            + COLUMN_DARMANI1_BRAND1NAME + " TEXT,"
            + COLUMN_DARMANI1_BRAND1PRICE + " TEXT,"
            + COLUMN_DARMANI1_BRAND2NAME + " TEXT,"
            + COLUMN_DARMANI1_BRAND2PRICE + " TEXT,"
            + COLUMN_DARMANI1_BRAND3NAME + " TEXT,"
            + COLUMN_DARMANI1_BRAND3PRICE + " TEXT,"
            + COLUMN_DARMANI1_BRAND4NAME + " TEXT,"
            + COLUMN_DARMANI1_BRAND4PRICE + " TEXT" + ")";

    // create table sql query
    private String CREATE_DARMANI2_TABLE = "CREATE TABLE " + TABLE_DARMANI2 + "("
            + COLUMN_DARMANI2_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_DARMANI2_ROW + " INTEGER,"
            + COLUMN_DARMANI2_CODE + " TEXT,"
            + COLUMN_DARMANI2_NAME + " TEXT,"
            + COLUMN_DARMANI2_SIZE + " TEXT,"
            + COLUMN_DARMANI2_PROPERTY + " TEXT,"
            + COLUMN_DARMANI2_BUYER + " TEXT,"
            + COLUMN_DARMANI2_CONSUMER + " TEXT,"
            + COLUMN_DARMANI2_BRAND1NAME + " TEXT,"
            + COLUMN_DARMANI2_BRAND1PRICE + " TEXT,"
            + COLUMN_DARMANI2_BRAND2NAME + " TEXT,"
            + COLUMN_DARMANI2_BRAND2PRICE + " TEXT,"
            + COLUMN_DARMANI2_BRAND3NAME + " TEXT,"
            + COLUMN_DARMANI2_BRAND3PRICE + " TEXT,"
            + COLUMN_DARMANI2_BRAND4NAME + " TEXT,"
            + COLUMN_DARMANI2_BRAND4PRICE + " TEXT" + ")";

    // drop table sql query
    private String DROP_KAFSH_TABLE = "DROP TABLE IF EXISTS " + TABLE_KAFSH;
    private String DROP_DARMANI1_TABLE = "DROP TABLE IF EXISTS " + TABLE_DARMANI1;
    private String DROP_DARMANI2_TABLE = "DROP TABLE IF EXISTS " + TABLE_DARMANI2;

    /**
     * Constructor
     *
     * @param context
     */


    public ProSqliteHelper(final Context context) {
        super(context, Environment.getExternalStorageDirectory()
                + File.separator + "Tanyar/DB"
                + File.separator + DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_KAFSH_TABLE);
        db.execSQL(CREATE_DARMANI1_TABLE);
        db.execSQL(CREATE_DARMANI2_TABLE);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        //Drop User Table if exist
        db.execSQL(DROP_KAFSH_TABLE);
        db.execSQL(DROP_DARMANI1_TABLE);
        db.execSQL(DROP_DARMANI2_TABLE);
        // Create tables again
        onCreate(db);

    }


    public void addKafsh(KafshModel kafsh) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_KAFSH_RADIF, kafsh.getRadif());
        values.put(COLUMN_KAFSH_GROUP, kafsh.getGroup());
        values.put(COLUMN_KAFSH_CODE, kafsh.getCode());
        values.put(COLUMN_KAFSH_COLOR, kafsh.getColor());
        values.put(COLUMN_KAFSH_PROPERTY, kafsh.getProperty());
        values.put(COLUMN_KAFSH_BOX, kafsh.getBox());
        values.put(COLUMN_KAFSH_HULFBOX, kafsh.getHulfbox());
        values.put(COLUMN_KAFSH_JUR, kafsh.getJur());
        values.put(COLUMN_KAFSH_NAJUR, kafsh.getNajur());
        values.put(COLUMN_KAFSH_BRAND1NAME, kafsh.getB1name());
        values.put(COLUMN_KAFSH_BRAND1PRICE, kafsh.getB1price());
        values.put(COLUMN_KAFSH_BRAND2NAME, kafsh.getB2name());
        values.put(COLUMN_KAFSH_BRAND2PRICE, kafsh.getB2price());
        values.put(COLUMN_KAFSH_BRAND3NAME, kafsh.getB3name());
        values.put(COLUMN_KAFSH_BRAND3PRICE, kafsh.getB3price());
        values.put(COLUMN_KAFSH_BRAND4NAME, kafsh.getB4name());
        values.put(COLUMN_KAFSH_BRAND4PRICE, kafsh.getB4price());
        values.put(COLUMN_KAFSH_SIZE, kafsh.getSize());
        values.put(COLUMN_KAFSH_SELLTYPE, kafsh.getSelltype());


        // Inserting Row
        db.insert(TABLE_KAFSH, null, values);
        db.close();
    }

    /**
     * This method is to fetch all product and return the list of user records
     *
     * @return list
     */
    public List<KafshModel> getAllPro() {
        // array of columns to fetch
        String[] columns = {
                COLUMN_KAFSH_ID,
                COLUMN_KAFSH_RADIF,
                COLUMN_KAFSH_GROUP,
                COLUMN_KAFSH_CODE,
                COLUMN_KAFSH_COLOR,
                COLUMN_KAFSH_PROPERTY,
                COLUMN_KAFSH_BOX,
                COLUMN_KAFSH_HULFBOX,
                COLUMN_KAFSH_JUR,
                COLUMN_KAFSH_NAJUR,
                COLUMN_KAFSH_BRAND1NAME,
                COLUMN_KAFSH_BRAND1PRICE,
                COLUMN_KAFSH_BRAND2NAME,
                COLUMN_KAFSH_BRAND2PRICE,
                COLUMN_KAFSH_BRAND3NAME,
                COLUMN_KAFSH_BRAND3PRICE,
                COLUMN_KAFSH_BRAND4NAME,
                COLUMN_KAFSH_BRAND4PRICE,
                COLUMN_KAFSH_SIZE,
                COLUMN_KAFSH_SELLTYPE
        };
        // sorting orders
        String sortOrder =
                COLUMN_KAFSH_RADIF + " ASC";
        List<KafshModel> proList = new ArrayList<KafshModel>();

        SQLiteDatabase db = this.getReadableDatabase();

        // query the user table
        /**
         * Here query function is used to fetch records from user table this function works like we use sql query.
         * SQL query equivalent to this query function is
         * SELECT user_id,user_name,user_email,user_password FROM user ORDER BY user_name;
         */
        Cursor cursor = db.query(TABLE_KAFSH, //Table to query
                columns,    //columns to return
                null,        //columns for the WHERE clause
                null,        //The values for the WHERE clause
                null,       //group the rows
                null,       //filter by row groups
                sortOrder); //The sort order


        // Traversing through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                KafshModel pro = new KafshModel();
                pro.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_KAFSH_ID))));
                pro.setRadif(cursor.getString(cursor.getColumnIndex(COLUMN_KAFSH_RADIF)));
                pro.setGroup(cursor.getString(cursor.getColumnIndex(COLUMN_KAFSH_GROUP)));
                pro.setCode(cursor.getString(cursor.getColumnIndex(COLUMN_KAFSH_CODE)));
                pro.setColor(cursor.getString(cursor.getColumnIndex(COLUMN_KAFSH_COLOR)));
                pro.setProperty(cursor.getString(cursor.getColumnIndex(COLUMN_KAFSH_PROPERTY)));
                pro.setBox(cursor.getString(cursor.getColumnIndex(COLUMN_KAFSH_BOX)));
                pro.setHulfbox(cursor.getString(cursor.getColumnIndex(COLUMN_KAFSH_HULFBOX)));
                pro.setJur(cursor.getString(cursor.getColumnIndex(COLUMN_KAFSH_JUR)));
                pro.setNajur(cursor.getString(cursor.getColumnIndex(COLUMN_KAFSH_NAJUR)));
                pro.setB1name(cursor.getString(cursor.getColumnIndex(COLUMN_KAFSH_BRAND1NAME)));
                pro.setB1price(cursor.getString(cursor.getColumnIndex(COLUMN_KAFSH_BRAND1PRICE)));
                pro.setB2name(cursor.getString(cursor.getColumnIndex(COLUMN_KAFSH_BRAND2NAME)));
                pro.setB2price(cursor.getString(cursor.getColumnIndex(COLUMN_KAFSH_BRAND2PRICE)));
                pro.setB3name(cursor.getString(cursor.getColumnIndex(COLUMN_KAFSH_BRAND3NAME)));
                pro.setB3price(cursor.getString(cursor.getColumnIndex(COLUMN_KAFSH_BRAND3PRICE)));
                pro.setB4name(cursor.getString(cursor.getColumnIndex(COLUMN_KAFSH_BRAND4NAME)));
                pro.setB4price(cursor.getString(cursor.getColumnIndex(COLUMN_KAFSH_BRAND4PRICE)));
                pro.setSize(cursor.getString(cursor.getColumnIndex(COLUMN_KAFSH_SIZE)));
                pro.setSelltype(cursor.getString(cursor.getColumnIndex(COLUMN_KAFSH_SELLTYPE)));
                // Adding user record to list
                proList.add(pro);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        // return user list
        return proList;
    }

    public void deleteAllKafsh() {
        SQLiteDatabase db = this.getWritableDatabase();

        db.execSQL("delete from "+ TABLE_KAFSH);
        db.execSQL("DELETE FROM SQLITE_SEQUENCE WHERE NAME = '" + TABLE_KAFSH + "'");
        db.close();
    }


    public void addDarmani1(DarmaniModel darmani1) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_DARMANI1_ROW, darmani1.getRow());
        values.put(COLUMN_DARMANI1_CODE, darmani1.getCode());
        values.put(COLUMN_DARMANI1_NAME, darmani1.getName());
        values.put(COLUMN_DARMANI1_SIZE, darmani1.getSize());
        values.put(COLUMN_DARMANI1_PROPERTY, darmani1.getProperty());
        values.put(COLUMN_DARMANI1_BUYER, darmani1.getBuyer());
        values.put(COLUMN_DARMANI1_CONSUMER, darmani1.getConsumer());
        values.put(COLUMN_DARMANI1_BRAND1NAME, darmani1.getB1name());
        values.put(COLUMN_DARMANI1_BRAND1PRICE, darmani1.getB1price());
        values.put(COLUMN_DARMANI1_BRAND2NAME, darmani1.getB2name());
        values.put(COLUMN_DARMANI1_BRAND2PRICE, darmani1.getB2price());
        values.put(COLUMN_DARMANI1_BRAND3NAME, darmani1.getB3name());
        values.put(COLUMN_DARMANI1_BRAND3PRICE, darmani1.getB3price());
        values.put(COLUMN_DARMANI1_BRAND4NAME, darmani1.getB4name());
        values.put(COLUMN_DARMANI1_BRAND4PRICE, darmani1.getB4price());

        // Inserting Row
        db.insert(TABLE_DARMANI1, null, values);
        db.close();
    }


    /**
     * This method is to fetch all product and return the list of user records
     *
     * @return list
     */
    public List<DarmaniModel> getAlldarmani1() {
        // array of columns to fetch
        String[] columns = {
                COLUMN_DARMANI1_ID,
                COLUMN_DARMANI1_ROW,
                COLUMN_DARMANI1_CODE,
                COLUMN_DARMANI1_NAME,
                COLUMN_DARMANI1_SIZE,
                COLUMN_DARMANI1_PROPERTY,
                COLUMN_DARMANI1_BUYER,
                COLUMN_DARMANI1_CONSUMER,
                COLUMN_DARMANI1_BRAND1NAME,
                COLUMN_DARMANI1_BRAND1PRICE,
                COLUMN_DARMANI1_BRAND2NAME,
                COLUMN_DARMANI1_BRAND2PRICE,
                COLUMN_DARMANI1_BRAND3NAME,
                COLUMN_DARMANI1_BRAND3PRICE,
                COLUMN_DARMANI1_BRAND4NAME,
                COLUMN_DARMANI1_BRAND4PRICE
        };
        // sorting orders
        String sortOrder =
                COLUMN_DARMANI1_ROW + " ASC";
        List<DarmaniModel> proList = new ArrayList<DarmaniModel>();

        SQLiteDatabase db = this.getReadableDatabase();

        // query the user table
        /**
         * Here query function is used to fetch records from user table this function works like we use sql query.
         * SQL query equivalent to this query function is
         * SELECT user_id,user_name,user_email,user_password FROM user ORDER BY user_name;
         */
        Cursor cursor = db.query(TABLE_DARMANI1, //Table to query
                columns,    //columns to return
                null,        //columns for the WHERE clause
                null,        //The values for the WHERE clause
                null,       //group the rows
                null,       //filter by row groups
                sortOrder); //The sort order


        // Traversing through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                DarmaniModel pro = new DarmaniModel();
                pro.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_DARMANI1_ID))));
                pro.setRow(cursor.getString(cursor.getColumnIndex(COLUMN_DARMANI1_ROW)));
                pro.setCode(cursor.getString(cursor.getColumnIndex(COLUMN_DARMANI1_CODE)));
                pro.setName(cursor.getString(cursor.getColumnIndex(COLUMN_DARMANI1_NAME)));
                pro.setSize(cursor.getString(cursor.getColumnIndex(COLUMN_DARMANI1_SIZE)));
                pro.setProperty(cursor.getString(cursor.getColumnIndex(COLUMN_DARMANI1_PROPERTY)));
                pro.setBuyer(cursor.getString(cursor.getColumnIndex(COLUMN_DARMANI1_BUYER)));
                pro.setConsumer(cursor.getString(cursor.getColumnIndex(COLUMN_DARMANI1_CONSUMER)));
                pro.setB1name(cursor.getString(cursor.getColumnIndex(COLUMN_DARMANI1_BRAND1NAME)));
                pro.setB1price(cursor.getString(cursor.getColumnIndex(COLUMN_DARMANI1_BRAND1PRICE)));
                pro.setB2name(cursor.getString(cursor.getColumnIndex(COLUMN_DARMANI1_BRAND2NAME)));
                pro.setB2price(cursor.getString(cursor.getColumnIndex(COLUMN_DARMANI1_BRAND2PRICE)));
                pro.setB3name(cursor.getString(cursor.getColumnIndex(COLUMN_DARMANI1_BRAND3NAME)));
                pro.setB3price(cursor.getString(cursor.getColumnIndex(COLUMN_DARMANI1_BRAND3PRICE)));
                pro.setB4name(cursor.getString(cursor.getColumnIndex(COLUMN_DARMANI1_BRAND4NAME)));
                pro.setB4price(cursor.getString(cursor.getColumnIndex(COLUMN_DARMANI1_BRAND4PRICE)));
                // Adding user record to list
                proList.add(pro);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        // return user list
        return proList;
    }

    public void deleteAlldarmani1() {
        SQLiteDatabase db = this.getWritableDatabase();

        db.execSQL("delete from "+ TABLE_DARMANI1);
        db.execSQL("DELETE FROM SQLITE_SEQUENCE WHERE NAME = '" + TABLE_DARMANI1 + "'");
        db.close();
    }


    public void addDarmani2(DarmaniModel darmani2) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_DARMANI2_ROW, darmani2.getRow());
        values.put(COLUMN_DARMANI2_CODE, darmani2.getCode());
        values.put(COLUMN_DARMANI2_NAME, darmani2.getName());
        values.put(COLUMN_DARMANI2_SIZE, darmani2.getSize());
        values.put(COLUMN_DARMANI2_PROPERTY, darmani2.getProperty());
        values.put(COLUMN_DARMANI2_BUYER, darmani2.getBuyer());
        values.put(COLUMN_DARMANI2_CONSUMER, darmani2.getConsumer());
        values.put(COLUMN_DARMANI2_BRAND1NAME, darmani2.getB1name());
        values.put(COLUMN_DARMANI2_BRAND1PRICE, darmani2.getB1price());
        values.put(COLUMN_DARMANI2_BRAND2NAME, darmani2.getB2name());
        values.put(COLUMN_DARMANI2_BRAND2PRICE, darmani2.getB2price());
        values.put(COLUMN_DARMANI2_BRAND3NAME, darmani2.getB3name());
        values.put(COLUMN_DARMANI2_BRAND3PRICE, darmani2.getB3price());
        values.put(COLUMN_DARMANI2_BRAND4NAME, darmani2.getB4name());
        values.put(COLUMN_DARMANI2_BRAND4PRICE, darmani2.getB4price());

        // Inserting Row
        db.insert(TABLE_DARMANI2, null, values);
        db.close();
    }


    /**
     * This method is to fetch all product and return the list of user records
     *
     * @return list
     */
    public List<DarmaniModel> getAlldarmani2() {
        // array of columns to fetch
        String[] columns = {
                COLUMN_DARMANI2_ID,
                COLUMN_DARMANI2_ROW,
                COLUMN_DARMANI2_CODE,
                COLUMN_DARMANI2_NAME,
                COLUMN_DARMANI2_SIZE,
                COLUMN_DARMANI2_PROPERTY,
                COLUMN_DARMANI2_BUYER,
                COLUMN_DARMANI2_CONSUMER,
                COLUMN_DARMANI2_BRAND1NAME,
                COLUMN_DARMANI2_BRAND1PRICE,
                COLUMN_DARMANI2_BRAND2NAME,
                COLUMN_DARMANI2_BRAND2PRICE,
                COLUMN_DARMANI2_BRAND3NAME,
                COLUMN_DARMANI2_BRAND3PRICE,
                COLUMN_DARMANI2_BRAND4NAME,
                COLUMN_DARMANI2_BRAND4PRICE
        };
        // sorting orders
        String sortOrder =
                COLUMN_DARMANI2_ROW + " ASC";
        List<DarmaniModel> proList = new ArrayList<DarmaniModel>();

        SQLiteDatabase db = this.getReadableDatabase();

        // query the user table
        /**
         * Here query function is used to fetch records from user table this function works like we use sql query.
         * SQL query equivalent to this query function is
         * SELECT user_id,user_name,user_email,user_password FROM user ORDER BY user_name;
         */
        Cursor cursor = db.query(TABLE_DARMANI2, //Table to query
                columns,    //columns to return
                null,        //columns for the WHERE clause
                null,        //The values for the WHERE clause
                null,       //group the rows
                null,       //filter by row groups
                sortOrder); //The sort order


        // Traversing through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                DarmaniModel pro = new DarmaniModel();
                pro.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_DARMANI2_ID))));
                pro.setRow(cursor.getString(cursor.getColumnIndex(COLUMN_DARMANI2_ROW)));
                pro.setCode(cursor.getString(cursor.getColumnIndex(COLUMN_DARMANI2_CODE)));
                pro.setName(cursor.getString(cursor.getColumnIndex(COLUMN_DARMANI2_NAME)));
                pro.setSize(cursor.getString(cursor.getColumnIndex(COLUMN_DARMANI2_SIZE)));
                pro.setProperty(cursor.getString(cursor.getColumnIndex(COLUMN_DARMANI2_PROPERTY)));
                pro.setBuyer(cursor.getString(cursor.getColumnIndex(COLUMN_DARMANI2_BUYER)));
                pro.setConsumer(cursor.getString(cursor.getColumnIndex(COLUMN_DARMANI2_CONSUMER)));
                pro.setB1name(cursor.getString(cursor.getColumnIndex(COLUMN_DARMANI2_BRAND1NAME)));
                pro.setB1price(cursor.getString(cursor.getColumnIndex(COLUMN_DARMANI2_BRAND1PRICE)));
                pro.setB2name(cursor.getString(cursor.getColumnIndex(COLUMN_DARMANI2_BRAND2NAME)));
                pro.setB2price(cursor.getString(cursor.getColumnIndex(COLUMN_DARMANI2_BRAND2PRICE)));
                pro.setB3name(cursor.getString(cursor.getColumnIndex(COLUMN_DARMANI2_BRAND3NAME)));
                pro.setB3price(cursor.getString(cursor.getColumnIndex(COLUMN_DARMANI2_BRAND3PRICE)));
                pro.setB4name(cursor.getString(cursor.getColumnIndex(COLUMN_DARMANI2_BRAND4NAME)));
                pro.setB4price(cursor.getString(cursor.getColumnIndex(COLUMN_DARMANI2_BRAND4PRICE)));
                // Adding user record to list
                proList.add(pro);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        // return user list
        return proList;
    }

    public void deleteAlldarmani2() {
        SQLiteDatabase db = this.getWritableDatabase();

        db.execSQL("delete from "+ TABLE_DARMANI2);
        db.execSQL("DELETE FROM SQLITE_SEQUENCE WHERE NAME = '" + TABLE_DARMANI2 + "'");
        db.close();
    }

}