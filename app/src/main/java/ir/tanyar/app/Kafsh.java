package ir.tanyar.app;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class Kafsh extends AppCompatActivity {

    private AppCompatActivity activity = Kafsh.this;
    private RecyclerView recyclerViewUsers;
    private List<KafshModel> listPRO;
    private KafshAdapter kafshAdapter;
    private ProSqliteHelper databaseHelper;

    private Toolbar toolbar;

    EditText editTextsearch;

    public static Button factor;

    private static final Pattern DIGIT_OR_LINK_PATTERN =
            Pattern.compile("(\\d|https?:[\\w_/+%?=&.]+)");
// Pattern:          (dig|link                 )

    private static final Map<String, String> PERSIAN_DIGITS = new HashMap<>();
    static {
        PERSIAN_DIGITS.put("0", "۰");
        PERSIAN_DIGITS.put("1", "۱");
        PERSIAN_DIGITS.put("2", "۲");
        PERSIAN_DIGITS.put("3", "۳");
        PERSIAN_DIGITS.put("4", "۴");
        PERSIAN_DIGITS.put("5", "۵");
        PERSIAN_DIGITS.put("6", "۶");
        PERSIAN_DIGITS.put("7", "۷");
        PERSIAN_DIGITS.put("8", "۸");
        PERSIAN_DIGITS.put("9", "۹");
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.kafsh);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);


        editTextsearch = (EditText) findViewById(R.id.et_search);

       // addproducts();
        initViews();
        initObjects();

        factor = (Button) findViewById(R.id.btn_factor);

        factor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Kafsh.this, FinalOrder.class);
                startActivity(intent);

            }
        });

        DecimalFormat formatter = new DecimalFormat("#,###,###");
        OrderSqliteHelper dbh1 = new OrderSqliteHelper(this);
        int total=dbh1.getsumkafshOrder()+dbh1.getsumWithpriceOrder()+dbh1.getsumNOpriceOrder();
        final String totalprice= formatter.format(total);
        factor.setText("مشاهده فاکتور ("+totalprice+" ریال )");


        editTextsearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                //after the change calling the method and passing the search input
                filter(editable.toString());
            }
        });

    }

    private void filter(String text) {
        //new array list that will hold the filtered data
        List<KafshModel> filterdNames = new ArrayList<>();

        String name="";

        for (int i = 0; i < listPRO.size(); i++) {

            final String c = listPRO.get(i).getCode();

            if(c.startsWith("2")){name="صندل زنانه";}
            if(c.startsWith("3")){name="صندل مردانه";}
            if(c.startsWith("4")){name="کفش زنانه";}
            if(c.startsWith("5")){name="کفش مردانه";}

            if (persianDigits(c).contains(text) || name.contains(text)) {
                filterdNames.add(listPRO.get(i));
            }
        }

        //calling a method of the adapter class and passing the filtered list
        kafshAdapter.filterList(filterdNames);

    }

    public static String persianDigits(String s) {
        StringBuffer sb = new StringBuffer();
        Matcher m = DIGIT_OR_LINK_PATTERN.matcher(s);
        while (m.find()) {
            String t = m.group(1);
            if (t.length() == 1) {
                // Digit.
                t = PERSIAN_DIGITS.get(t);
            }
            m.appendReplacement(sb, t);
        }
        m.appendTail(sb);
        return sb.toString();
    }


    /**
     * This method is to initialize views
     */
    private void initViews() {
        recyclerViewUsers = (RecyclerView) findViewById(R.id.recyclerViewUsers);
    }

    /**
     * This method is to initialize objects to be used
     */
    private void initObjects() {
        listPRO = new ArrayList<>();
        kafshAdapter = new KafshAdapter(this,listPRO);

       // RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        GridLayoutManager layoutManager = new GridLayoutManager(Kafsh.this, 3, GridLayoutManager.VERTICAL, false);
        recyclerViewUsers.setLayoutManager(layoutManager);
        recyclerViewUsers.setItemAnimator(new DefaultItemAnimator());
        recyclerViewUsers.setHasFixedSize(true);
        recyclerViewUsers.setAdapter(kafshAdapter);
        databaseHelper = new ProSqliteHelper(activity);

        getDataFromSQLite();
    }




    /**
    private void addproducts() {

        try {
            JSONObject obj = new JSONObject(loadJSONFromAsset());
            JSONArray m_jArry = obj.getJSONArray("kafsh");
            // ArrayList<HashMap<String, String>> formList = new ArrayList<HashMap<String, String>>();
            // HashMap<String, String> m_li;
            dbh = new ProSqliteHelper(activity);
            //dbh.deleteAllProduct();

            for (int i = 0; i < m_jArry.length(); i++) {
                JSONObject jo_inside = m_jArry.getJSONObject(i);

                String radif = jo_inside.getString("radif");
                String group = jo_inside.getString("group");
                String code = jo_inside.getString("code");
                String color = jo_inside.getString("color");
                String property = jo_inside.getString("property");
                String box = jo_inside.getString("box");
                String hulfbox = jo_inside.getString("hulfbox");
                String jur = jo_inside.getString("jur");
                String najur = jo_inside.getString("najur");

                pro = new KafshModel();
                pro.setRadif(radif);
                pro.setGroup(group);
                pro.setCode(code);
                pro.setColor(color);
                pro.setProperty(property);
                pro.setBox(box);
                pro.setHulfbox(hulfbox);
                pro.setJur(jur);
                pro.setNajur(najur);
                dbh.addKafsh(pro);

                // Toast.makeText(getApplicationContext(),State, Toast.LENGTH_SHORT).show();

                //Add your values in your `ArrayList` as below:
                // m_li = new HashMap<String, String>();
                // m_li.put("formule", formula_value);
                // m_li.put("url", url_value);

                // formList.add(m_li);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

  **/

    /**
     * This method is to fetch all user records from SQLite
     */
    private void getDataFromSQLite() {
        // AsyncTask is used that SQLite operation not blocks the UI Thread.
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                listPRO.clear();
                listPRO.addAll(databaseHelper.getAllPro());

                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                kafshAdapter.notifyDataSetChanged();
            }
        }.execute();
    }

    public String loadJSONFromAsset() {
        String json = null;
        try {
            InputStream is = getAssets().open("kafsh.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    public class FormatHelper {

        private String[] persianNumbers = new String[]{"۰", "۱", "۲", "۳", "۴", "۵", "۶", "۷", "۸", "۹"};

        public String toPersianNumber(String text) {
            if (text.isEmpty())
                return "";
            String out = "";
            int length = text.length();
            for (int i = 0; i < length; i++) {
                char c = text.charAt(i);
                if ('0' <= c && c <= '9') {
                    int number = Integer.parseInt(String.valueOf(c));
                    out += persianNumbers[number];
                } else if (c == '٫') {
                    out += '،';
                } else {
                    out += c;
                }
            }
            return out;
        }
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

}