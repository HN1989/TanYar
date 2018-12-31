package ir.tanyar.app;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class DarmaniNoPrice extends Fragment {

    private RecyclerView darmani2;
    private List<DarmaniModel> listPRO;
    private DarmaniNoPriceAdapter darmaniNoPriceAdapter;
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

    public DarmaniNoPrice() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        final View view = inflater.inflate(R.layout.darmani_no_price, container, false);



        darmani2 = (RecyclerView) view.findViewById(R.id.rc_darmani2);
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

        editTextsearch = (EditText) view.findViewById(R.id.et_search);

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
        darmaniNoPriceAdapter.filterList(filterdNames);

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
        darmaniNoPriceAdapter = new DarmaniNoPriceAdapter(getActivity(),listPRO);

        // RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 3, GridLayoutManager.VERTICAL, false);
        darmani2.setLayoutManager(layoutManager);
        darmani2.setItemAnimator(new DefaultItemAnimator());
        darmani2.setHasFixedSize(true);
        darmani2.setAdapter(darmaniNoPriceAdapter);
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
                listPRO.addAll(databaseHelper.getAlldarmani2());

                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                darmaniNoPriceAdapter.notifyDataSetChanged();
            }
        }.execute();
    }



}
