package ir.tanyar.app;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class DarmaniOfferiWithPrice extends Fragment {

    private RecyclerView darmani1;
    private List<DarmaniModel> listPRO;
    private DarmaniOfferiWithPriceAdapter darmaniOfeeriWithPriceAdapter;
    private ProSqliteHelper databaseHelper;

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

    private RadioGroup offerGroup;

    public static int asli,prize;

    public static SharedPreferences offertype;

    public DarmaniOfferiWithPrice() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        final View view = inflater.inflate(R.layout.darmani_offeri_with_price, container, false);

        offertype =getActivity().getSharedPreferences("offerkey", 0);

        offerGroup = (RadioGroup) view.findViewById(R.id.rd_offertype);

        final String[] OFFERTYPE = Home.offer.split(",");
        for (String s: OFFERTYPE) {

            RadioButton rdbtn = new RadioButton(getActivity());
            Typeface font = Typeface.createFromAsset(getActivity().getAssets(), "Yekan.ttf");
            rdbtn.setTypeface(font);
            rdbtn.setPadding(20,10,0,10);
            rdbtn.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            if (ViewCompat.getLayoutDirection(rdbtn)== ViewCompat.LAYOUT_DIRECTION_LTR){
                ViewCompat.setLayoutDirection(rdbtn,ViewCompat.LAYOUT_DIRECTION_RTL);
            }else{
                ViewCompat.setLayoutDirection(rdbtn,ViewCompat.LAYOUT_DIRECTION_LTR);
            }
            //rdbtn.setId(OFFERTYPE);
            //rdbtn.setText(s);
           //Arrays.asList(OFFERTYPE).indexOf(s);

            int index = -1;
            for (int i=0;i<OFFERTYPE.length;i++) {
                if (OFFERTYPE[i].equals(s)) {
                    index = i+1;
                    rdbtn.setText("آفر "+index);
                    break;
                }
            }

            offerGroup.addView(rdbtn);
            if(s.startsWith("2")){
                rdbtn.setChecked(true);
                asli=2;prize=1;
                SharedPreferences.Editor editor = offertype.edit();
                editor.putInt("aslikey", 2);
                editor.putInt("prizekey", 1);
                editor.commit();

            }
        }


        offerGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup arg0, int id) {

                // get selected radio button from radioGroup
                id = arg0.getCheckedRadioButtonId();

                // find the radiobutton by returned id
                RadioButton radioButton = (RadioButton) view.findViewById(id);
                //String text=radioButton.getText().toString();
                String text= OFFERTYPE[id-1];

               // Toast.makeText(getActivity(),text, Toast.LENGTH_SHORT).show();
                String[] THISOFFER = text.split("-");
                asli=Integer.valueOf(THISOFFER[0]);
                prize=Integer.valueOf(THISOFFER[1]);

                SharedPreferences.Editor editor = offertype.edit();
                editor.putInt("aslikey", asli);
                editor.putInt("prizekey", prize);
                editor.commit();

            }
        });

        editTextsearch = (EditText) view.findViewById(R.id.et_search);

        darmani1 = (RecyclerView) view.findViewById(R.id.rc_darmani1);
        initObjects();

        factor = (Button) view.findViewById(R.id.btn_factor);

        factor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getActivity(), FinalOrder.class);
                startActivity(intent);

            }
        });

        DecimalFormat formatter = new DecimalFormat("#,###,###");
        OrderSqliteHelper dbh1 = new OrderSqliteHelper(getActivity());
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



        return  view;

    }

    private void filter(String text) {
        //new array list that will hold the filtered data
        List<DarmaniModel> filterdNames = new ArrayList<>();

        for (int i = 0; i < listPRO.size(); i++) {

            final String c = listPRO.get(i).getCode();
            final String g = listPRO.get(i).getName();
            if (persianDigits(c).contains(text) || g.contains(text)) {
                filterdNames.add(listPRO.get(i));
            }
        }

        //calling a method of the adapter class and passing the filtered list
        darmaniOfeeriWithPriceAdapter.filterList(filterdNames);

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



    private void initObjects() {
        listPRO = new ArrayList<>();
        darmaniOfeeriWithPriceAdapter = new DarmaniOfferiWithPriceAdapter(getActivity(),listPRO);

        // RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 3, GridLayoutManager.VERTICAL, false);
        darmani1.setLayoutManager(layoutManager);
        darmani1.setItemAnimator(new DefaultItemAnimator());
        darmani1.setHasFixedSize(true);
        darmani1.setAdapter(darmaniOfeeriWithPriceAdapter);
        databaseHelper = new ProSqliteHelper(getActivity());

        getDataFromSQLite();
    }


    /**
     * This method is to fetch all user records from SQLite
     */
    private void getDataFromSQLite() {
        // AsyncTask is used that SQLite operation not blocks the UI Thread.
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                listPRO.clear();
                listPRO.addAll(databaseHelper.getAlldarmani1());

                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                darmaniOfeeriWithPriceAdapter.notifyDataSetChanged();
            }
        }.execute();
    }



}
