package ir.tanyar.app;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class CustomerInfo extends AppCompatActivity {

    private Toolbar toolbar;

    private Button next,add,edit;

    private RadioGroup typeGroup;


    //Spinner SPgender;
   // ArrayList<String> stringArrayGender;

    EditText ETname,ETmanager,ETstate,ETcity,ETarea,ETadres,ETzipcode,ETphone,ETmobile,ETdescrip;

    String type;
    String id;

    static String order="";

    String TYPE,NAME,MANAGER,STATE,CITY,AREA,ADRES,ZIPCODE,PHONE,MOBILE;

    private CustomerSqliteHelper databaseHelper;
    private CustomerModel customer;

    private UserSqliteHelper db2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.customer_info);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        id = getIntent().getStringExtra("ID");

        TYPE = getIntent().getStringExtra("TYPE");
        NAME = getIntent().getStringExtra("NAME");
        MANAGER = getIntent().getStringExtra("MANAGER");
        STATE = getIntent().getStringExtra("STATE");
        CITY = getIntent().getStringExtra("CITY");
        AREA = getIntent().getStringExtra("AREA");
        ADRES = getIntent().getStringExtra("ADRES");
        ZIPCODE = getIntent().getStringExtra("ZIPCODE");
        PHONE = getIntent().getStringExtra("PHONE");
        MOBILE = getIntent().getStringExtra("MOBILE");

        type="داروخانه";

        add = (Button) findViewById(R.id.btn_add);
        next = (Button) findViewById(R.id.btn_next);
        edit = (Button) findViewById(R.id.btn_edit);

        ETname = (EditText) findViewById(R.id.et_name);
        ETmanager = (EditText) findViewById(R.id.et_manager);
        ETstate = (EditText) findViewById(R.id.et_state);
        ETcity = (EditText) findViewById(R.id.et_city);
        ETarea = (EditText) findViewById(R.id.et_area);
        ETadres = (EditText) findViewById(R.id.et_adres);
        ETzipcode = (EditText) findViewById(R.id.et_zipcode);
        ETphone = (EditText) findViewById(R.id.et_phone);
        ETmobile = (EditText) findViewById(R.id.et_mobile);
        ETdescrip = (EditText) findViewById(R.id.et_descrip);

        final UserData current = UserSharedPref.getInstance(getApplicationContext()).getUser();
        final String username=String.valueOf(current.getUsername());
        db2 = new UserSqliteHelper(getApplicationContext());
        final String networker=db2.logeduser(username);

        if(!TextUtils.isEmpty(TYPE)) {
            ETname.setText(NAME);
            ETmanager.setText(MANAGER);
            ETstate.setText(STATE);
            ETcity.setText(CITY);
            ETarea.setText(AREA);
            ETadres.setText(ADRES);
            ETzipcode.setText(ZIPCODE);
            ETphone.setText(PHONE);
            ETmobile.setText(MOBILE);
            add.setVisibility(View.GONE);
            edit.setVisibility(View.VISIBLE);

            RadioButton mrdical = (RadioButton) findViewById(R.id.rd_mrdical);
            RadioButton pharmacy = (RadioButton) findViewById(R.id.rd_pharmacy);
            RadioButton shop = (RadioButton) findViewById(R.id.rd_shop);

            if (TYPE.equals("کالای پزشکی")) {
                mrdical.setChecked(true);
                pharmacy.setChecked(false);
                shop.setChecked(false);
                type="کالای پزشکی";
            }
            if (TYPE.equals("داروخانه")) {
                mrdical.setChecked(false);
                pharmacy.setChecked(true);
                shop.setChecked(false);
                type="داروخانه";
            }
            if (TYPE.equals("فروشگاه")) {
                mrdical.setChecked(false);
                pharmacy.setChecked(false);
                shop.setChecked(true);
                type="فروشگاه";
            }
        }

        typeGroup = (RadioGroup) findViewById(R.id.rd_type);
        typeGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup arg0, int id) {
                switch (id) {
                    case -1:
                        //Toast.makeText(getApplicationContext(),"null", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.rd_mrdical:
                        //Toast.makeText(getApplicationContext(),"کالای پزشکی", Toast.LENGTH_SHORT).show();
                        type="کالای پزشکی";
                        break;
                    case R.id.rd_pharmacy:
                        //Toast.makeText(getApplicationContext(),"داروخانه", Toast.LENGTH_SHORT).show();
                        type="داروخانه";
                        break;
                    case R.id.rd_shop:
                        //Toast.makeText(getApplicationContext(),"داروخانه", Toast.LENGTH_SHORT).show();
                        type="فروشگاه";
                        break;
                }
            }
        });

        ETmanager.setClickable(true);
        ETmanager.setFocusable(false);
        ETmanager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(CustomerInfo.this);
                LayoutInflater inflater =LayoutInflater.from(CustomerInfo.this);
                View dialogView = inflater.inflate(R.layout.gender_dialog, null);
                dialogBuilder.setView(dialogView);
                final AlertDialog alertDialog = dialogBuilder.create();
                alertDialog.show();

                alertDialog.setCanceledOnTouchOutside(false);

                final EditText ETname=(EditText) dialogView.findViewById(R.id.et_name);

                dialogView.findViewById(R.id.btn_male).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {


                        String name=ETname.getText().toString();

                        if (TextUtils.isEmpty(name)) {
                            Toast.makeText(getApplicationContext(), "نام مسئول را وارد کنید", Toast.LENGTH_SHORT).show();
                            return;
                        }


                        ETmanager.setText("آقای "+name);

                        alertDialog.dismiss();

                    }
                });

                dialogView.findViewById(R.id.btn_female).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        String name=ETname.getText().toString();

                        if (TextUtils.isEmpty(name)) {
                            Toast.makeText(getApplicationContext(), "نام مسئول را وارد کنید", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        ETmanager.setText("خانم "+name);
                        alertDialog.dismiss();

                    }
                });


            }
        });


        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String phonecheck = "^[0-9]{11}$";
                String zipcodecheck = "^[0-9]{10}$";

                final String name=ETname.getText().toString();
                final String manager=ETmanager.getText().toString();
                final String state=ETstate.getText().toString();
                final String city=ETcity.getText().toString();
                final String area=ETarea.getText().toString();
                final String adres=ETadres.getText().toString();
                final String zipcode=ETzipcode.getText().toString();
                final String phone=ETphone.getText().toString();
                final String mobile=ETmobile.getText().toString();

                if(TextUtils.isEmpty(name) ||
                        TextUtils.isEmpty(manager) ||
                        TextUtils.isEmpty(state) ||
                        TextUtils.isEmpty(city) ||
                        TextUtils.isEmpty(adres) ||
                        TextUtils.isEmpty(zipcode) ||
                        TextUtils.isEmpty(phone) ||
                        TextUtils.isEmpty(mobile)
                        ) {

                    Toast.makeText(getApplicationContext(),"اطلاعات را کامل کنید", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!phone.startsWith("0")) {

                    Toast.makeText(getApplicationContext(),"تلفن ثابت صحیح نمی باشد", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!mobile.matches(phonecheck) || !mobile.startsWith("09")) {

                    Toast.makeText(getApplicationContext(),"شماره موبایل صحیح نمی باشد", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!zipcode.matches(zipcodecheck)) {

                    Toast.makeText(getApplicationContext(),"کد پستی 10 رقمی وارد کنید", Toast.LENGTH_SHORT).show();
                    return;
                }

                    databaseHelper = new CustomerSqliteHelper(getApplicationContext());
                    customer = new CustomerModel();

                    customer.setType(type);
                    customer.setName(name);
                    customer.setManager(manager);
                    customer.setState(state);
                    customer.setCity(city);
                    customer.setArea(area);
                    customer.setAdres(adres);
                    customer.setZipcode(zipcode);
                    customer.setPhone(phone);
                    customer.setMobile(mobile);
                    customer.setNetworker(networker);

                    databaseHelper.addCustomer(customer);

                Toast.makeText(getApplicationContext(),"مشتری ثبت شد", Toast.LENGTH_SHORT).show();

            }
        });


        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String phonecheck = "^[0-9]{11}$";
                String zipcodecheck = "^[0-9]{10}$";

                final String name=ETname.getText().toString();
                final String manager=ETmanager.getText().toString();
                final String state=ETstate.getText().toString();
                final String city=ETcity.getText().toString();
                final String area=ETarea.getText().toString();
                final String adres=ETadres.getText().toString();
                final String zipcode=ETzipcode.getText().toString();
                final String phone=ETphone.getText().toString();
                final String mobile=ETmobile.getText().toString();

                if(TextUtils.isEmpty(name) ||
                        TextUtils.isEmpty(manager) ||
                        TextUtils.isEmpty(state) ||
                        TextUtils.isEmpty(city) ||
                        TextUtils.isEmpty(adres) ||
                        TextUtils.isEmpty(zipcode) ||
                        TextUtils.isEmpty(phone) ||
                        TextUtils.isEmpty(mobile)
                        ) {

                    Toast.makeText(getApplicationContext(),"اطلاعات را کامل کنید", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!phone.startsWith("0")) {

                    Toast.makeText(getApplicationContext(),"تلفن ثابت صحیح نمی باشد", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!mobile.matches(phonecheck) || !mobile.startsWith("09")) {

                    Toast.makeText(getApplicationContext(),"شماره موبایل صحیح نمی باشد", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!zipcode.matches(zipcodecheck)) {

                    Toast.makeText(getApplicationContext(),"کد پستی 10 رقمی وارد کنید", Toast.LENGTH_SHORT).show();
                    return;
                }

                databaseHelper = new CustomerSqliteHelper(getApplicationContext());
                customer = new CustomerModel();

                customer.setType(type);
                customer.setName(name);
                customer.setManager(manager);
                customer.setState(state);
                customer.setCity(city);
                customer.setArea(area);
                customer.setAdres(adres);
                customer.setZipcode(zipcode);
                customer.setPhone(phone);
                customer.setMobile(mobile);
                customer.setNetworker(networker);

                if(!TextUtils.isEmpty(id)){databaseHelper.updateCustomer(customer,Integer.valueOf(id));}

                Toast.makeText(getApplicationContext(),"اطلاعات مشتری ویرایش شد", Toast.LENGTH_SHORT).show();

            }
        });



        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                OrderSqliteHelper db = new OrderSqliteHelper(getApplicationContext());
                db.deleteAllWithpriceOrder();
                db.deleteAllNOpriceOrder();
                db.deleteAllOfferOrder();
                db.deleteAllOrder();

                String phonecheck = "^[0-9]{11}$";
                String zipcodecheck = "^[0-9]{10}$";

                final String name=ETname.getText().toString();
                final String manager=ETmanager.getText().toString();
                final String state=ETstate.getText().toString();
                final String city=ETcity.getText().toString();
                final String area=ETarea.getText().toString();
                final String adres=ETadres.getText().toString();
                final String zipcode=ETzipcode.getText().toString();
                final String phone=ETphone.getText().toString();
                final String mobile=ETmobile.getText().toString();
                final String descrip=ETdescrip.getText().toString();

                if(TextUtils.isEmpty(name) ||
                   TextUtils.isEmpty(manager) ||
                        TextUtils.isEmpty(state) ||
                        TextUtils.isEmpty(city) ||
                        TextUtils.isEmpty(adres) ||
                        TextUtils.isEmpty(zipcode) ||
                        TextUtils.isEmpty(phone) ||
                        TextUtils.isEmpty(mobile) ||
                        TextUtils.isEmpty(descrip)) {

                    Toast.makeText(getApplicationContext(),"اطلاعات را کامل کنید", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!phone.startsWith("0")) {

                    Toast.makeText(getApplicationContext(),"تلفن ثابت صحیح نمی باشد", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!mobile.matches(phonecheck) || !mobile.startsWith("09")) {

                    Toast.makeText(getApplicationContext(),"شماره موبایل صحیح نمی باشد", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!zipcode.matches(zipcodecheck)) {

                    Toast.makeText(getApplicationContext(),"کد پستی 10 رقمی وارد کنید", Toast.LENGTH_SHORT).show();
                    return;
                }

                CustomerData user = new CustomerData(
                        type,
                        name,
                        manager,
                        state,
                        city,
                        area,
                        adres,
                        zipcode,
                        phone,
                        mobile,
                        descrip,
                        networker
                );

                InfoSharedPref.getInstance(getApplicationContext()).currentcustomer(user);

                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(CustomerInfo.this);
                LayoutInflater inflater =LayoutInflater.from(CustomerInfo.this);
                View dialogView = inflater.inflate(R.layout.cat_dialog, null);
                dialogBuilder.setView(dialogView);
                final AlertDialog alertDialog2 = dialogBuilder.create();
                alertDialog2.show();

                dialogView.findViewById(R.id.btn_kafsh).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Intent intent = new Intent(getApplicationContext(), Kafsh.class);
                        startActivity(intent);
                        alertDialog2.dismiss();

                    }
                });


                dialogView.findViewById(R.id.btn_darmani).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(CustomerInfo.this);
                        LayoutInflater inflater =LayoutInflater.from(CustomerInfo.this);
                        View dialogView = inflater.inflate(R.layout.sell_type_dialog, null);
                        dialogBuilder.setView(dialogView);
                        final AlertDialog alertDialog = dialogBuilder.create();
                        alertDialog.show();

                        dialogView.findViewById(R.id.btn_riali).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                order="ریالی";
                                Intent intent = new Intent(getApplicationContext(), DarmaniRiali.class);
                                startActivity(intent);
                                alertDialog.dismiss();
                                alertDialog2.dismiss();


                            }
                        });

                        dialogView.findViewById(R.id.btn_offeri).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                order="آفری";
                                Intent intent = new Intent(getApplicationContext(), DarmaniOfferi.class);
                                startActivity(intent);
                                alertDialog.dismiss();
                                alertDialog2.dismiss();

                            }
                        });


                    }
                });

            }
        });


    }


    @Override
    public void onBackPressed() {

        finish();

    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }


}