package ir.tanyar.app;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class Customers extends AppCompatActivity {

    private AppCompatActivity activity = Customers.this;
    private RecyclerView mycustomers;
    private List<CustomerModel> listcustomer;
    private CustomerAdapter coustomerAdapter;
    private CustomerSqliteHelper databaseHelper;

    EditText editTextsearch;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.customers);

        initViews();
        initObjects();

        editTextsearch = (EditText) findViewById(R.id.et_search);
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
        List<CustomerModel> filterdNames = new ArrayList<>();

        for (int i = 0; i < listcustomer.size(); i++) {

            final String n = listcustomer.get(i).getName().toLowerCase();
            final String m = listcustomer.get(i).getManager().toLowerCase();
            final String s = listcustomer.get(i).getState().toLowerCase();
            final String c = listcustomer.get(i).getCity().toLowerCase();
            final String a = listcustomer.get(i).getAdres().toLowerCase();
            if (n.contains(text) || m.contains(text) || s.contains(text) || c.contains(text) || a.contains(text)) {
                filterdNames.add(listcustomer.get(i));
            }
        }

        //calling a method of the adapter class and passing the filtered list
        coustomerAdapter.filterList(filterdNames);

    }


    /**
     * This method is to initialize views
     */
    private void initViews() {
        mycustomers = (RecyclerView) findViewById(R.id.rc_customers);
    }

    /**
     * This method is to initialize objects to be used
     */
    private void initObjects() {
        listcustomer = new ArrayList<>();
        coustomerAdapter = new CustomerAdapter(this,listcustomer);

         RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        //GridLayoutManager layoutManager = new GridLayoutManager(Customers.this, 3, GridLayoutManager.VERTICAL, false);
        mycustomers.setLayoutManager(mLayoutManager);
        mycustomers.setItemAnimator(new DefaultItemAnimator());
        mycustomers.setHasFixedSize(true);
        mycustomers.setAdapter(coustomerAdapter);
        databaseHelper = new CustomerSqliteHelper(activity);

        getDataFromSQLite();
    }


    private void getDataFromSQLite() {
        // AsyncTask is used that SQLite operation not blocks the UI Thread.
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                listcustomer.clear();
                listcustomer.addAll(databaseHelper.getMyCustomer());

                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                coustomerAdapter.notifyDataSetChanged();
            }
        }.execute();
    }



    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }


}