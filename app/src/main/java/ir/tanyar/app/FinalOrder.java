package ir.tanyar.app;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class FinalOrder extends AppCompatActivity {

    private Toolbar toolbar;
    private Button confirm,remove,next,home;

    TextView TVtype,TVname,TVmanager,TVfulladres,TVzipcode,TVphone,TVmobile,TVdescrip,TVnetworker,TVdate,TVnumber,TVtotal;
    TextView TVkafshtotal,TVwithpricetotal,TVnopricetotal,TVoffertotal,tcwp,tcnp,tcoff,tckf;
     // RelativeLayout all;
     NestedScrollView all;

    TextView wp,np,off,kf;
    TableRow Twp,Tnp,Toff,Tkf;
    View Vwp,Vnp,Voff,Vkf;

    private RecyclerView kafshorder,withpriceorder,nopriceorder,offerorder;

    private List<KafshOrderModel> listorder;
    private KafshOrderAdapter orderAdapter;

    private List<DarmaniOrderModel> listorder2;
    private DarmaniWPOrderAdapter orderAdapter2;

    private List<DarmaniOrderModel> listorder3;
    private DarmaniNPOrderAdapter orderAdapter3;

    private List<DarmaniOrderModel> listorder4;
    private DarmaniOFOrderAdapter orderAdapter4;

    private OrderSqliteHelper dbh1,dbh2,dbh3,dbh4;

    String filename;

   DecimalFormat formatter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.final_order);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        formatter = new DecimalFormat("#,###,###");

        kafshorder = (RecyclerView) findViewById(R.id.rc_kafsh_order);
        withpriceorder = (RecyclerView) findViewById(R.id.rc_withprice_order);
        nopriceorder = (RecyclerView) findViewById(R.id.rc_noprice_order);
        offerorder = (RecyclerView) findViewById(R.id.rc_offer_order);

        TVtype = (TextView) findViewById(R.id.tx_type);
        TVname = (TextView) findViewById(R.id.tx_name);
        TVmanager = (TextView) findViewById(R.id.tx_manager);
        TVfulladres = (TextView) findViewById(R.id.tx_full_adres);
        TVzipcode = (TextView) findViewById(R.id.tx_zipcode);
        TVphone = (TextView) findViewById(R.id.tx_phone);
        TVmobile = (TextView) findViewById(R.id.tx_mobile);
        TVdescrip = (TextView) findViewById(R.id.tx_descrip);
        TVnetworker = (TextView) findViewById(R.id.tx_networker);
        TVdate = (TextView) findViewById(R.id.tx_date);
        TVnumber = (TextView) findViewById(R.id.tx_number);
        TVtotal = (TextView) findViewById(R.id.tx_total);

        wp = (TextView) findViewById(R.id.withprice_tx);
        np = (TextView) findViewById(R.id.noprice_tx);
        off = (TextView) findViewById(R.id.offer_tx);
        kf = (TextView) findViewById(R.id.kafsh_tx);

        Twp = (TableRow) findViewById(R.id.withprice_tb);
        Tnp = (TableRow) findViewById(R.id.noprice_tb);
        Toff = (TableRow) findViewById(R.id.offer_tb);
        Tkf = (TableRow) findViewById(R.id.kafsh_tb);

        Vwp = (View) findViewById(R.id.withprice_v);
        Vnp = (View) findViewById(R.id.noprice_v);
        Voff = (View) findViewById(R.id.offer_v);
        Vkf = (View) findViewById(R.id.kafsh_v);

        confirm = (Button) findViewById(R.id.btn_confirm);
        remove = (Button) findViewById(R.id.btn_remove);
        next = (Button) findViewById(R.id.btn_next);
        home = (Button) findViewById(R.id.btn_home);


        TVkafshtotal = (TextView) findViewById(R.id.kafsh_total);
        TVwithpricetotal = (TextView) findViewById(R.id.withprice_total);
        TVnopricetotal = (TextView) findViewById(R.id.noprice_total);
        TVoffertotal = (TextView) findViewById(R.id.offer_total);
        tcwp = (TextView) findViewById(R.id.withprice_totalcount);
        tcnp = (TextView) findViewById(R.id.noprice_totalcount);
        tcoff = (TextView) findViewById(R.id.offer_totalcount);
        tckf = (TextView) findViewById(R.id.kafsh_totalcount);

        final CustomerData current = InfoSharedPref.getInstance(getApplicationContext()).getCustomer();

        final String state=String.valueOf(current.getState());
        final String city=String.valueOf(current.getCity());
        final String area=String.valueOf(current.getArea());
        final String adres=String.valueOf(current.getAdres());

        TVtype.setText(String.valueOf(current.getType()));
        TVname.setText(String.valueOf(current.getName()));
        TVmanager.setText(String.valueOf(current.getManager()));
        TVfulladres.setText(state+" ،"+city+" ،"+area+" ،"+adres);
        TVzipcode.setText(String.valueOf(current.getZipcode()));
        TVphone.setText(String.valueOf(current.getPhone()));
        TVmobile.setText(String.valueOf(current.getMobile()));
        TVdescrip.setText(String.valueOf(current.getDescrip()));
        TVnetworker.setText(String.valueOf(current.getNetworker()));

        //kafah order
        listorder = new ArrayList<>();
        orderAdapter = new KafshOrderAdapter(this,listorder);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        //GridLayoutManager layoutManager = new GridLayoutManager(Kafsh.this, 3, GridLayoutManager.VERTICAL, false);
        kafshorder.setLayoutManager(layoutManager);
        kafshorder.setItemAnimator(new DefaultItemAnimator());
        kafshorder.setHasFixedSize(true);
        kafshorder.setAdapter(orderAdapter);
        kafshorder.setNestedScrollingEnabled(false);

        //withprice order
        listorder2 = new ArrayList<>();
        orderAdapter2 = new DarmaniWPOrderAdapter(this,listorder2);
        RecyclerView.LayoutManager layoutManager2 = new LinearLayoutManager(getApplicationContext());
        //GridLayoutManager layoutManager = new GridLayoutManager(Kafsh.this, 3, GridLayoutManager.VERTICAL, false);
        withpriceorder.setLayoutManager(layoutManager2);
        withpriceorder.setItemAnimator(new DefaultItemAnimator());
        withpriceorder.setHasFixedSize(true);
        withpriceorder.setAdapter(orderAdapter2);
        withpriceorder.setNestedScrollingEnabled(false);

        //noprice order
        listorder3 = new ArrayList<>();
        orderAdapter3 = new DarmaniNPOrderAdapter(this,listorder3);
        RecyclerView.LayoutManager layoutManager3 = new LinearLayoutManager(getApplicationContext());
        //GridLayoutManager layoutManager = new GridLayoutManager(Kafsh.this, 3, GridLayoutManager.VERTICAL, false);
        nopriceorder.setLayoutManager(layoutManager3);
        nopriceorder.setItemAnimator(new DefaultItemAnimator());
        nopriceorder.setHasFixedSize(true);
        nopriceorder.setAdapter(orderAdapter3);
        nopriceorder.setNestedScrollingEnabled(false);

        //offer order
        listorder4 = new ArrayList<>();
        orderAdapter4 = new DarmaniOFOrderAdapter(this,listorder4);
        RecyclerView.LayoutManager layoutManager4 = new LinearLayoutManager(getApplicationContext());
        //GridLayoutManager layoutManager = new GridLayoutManager(Kafsh.this, 3, GridLayoutManager.VERTICAL, false);
        offerorder.setLayoutManager(layoutManager4);
        offerorder.setItemAnimator(new DefaultItemAnimator());
        offerorder.setHasFixedSize(true);
        offerorder.setAdapter(orderAdapter4);
        offerorder.setNestedScrollingEnabled(false);

        dbh1 = new OrderSqliteHelper(this);
        dbh2 = new OrderSqliteHelper(this);
        dbh3 = new OrderSqliteHelper(this);
        dbh4 = new OrderSqliteHelper(this);

        getKafshOrderFromSQLite();
        getWithpriceOrderFromSQLite();
        getNopriceOrderFromSQLite();
        getOfferOrderFromSQLite();

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                finish();
                Intent intent = new Intent(getApplicationContext(), Home.class);
                startActivity(intent);

            }
        });


        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(FinalOrder.this);
                LayoutInflater inflater =LayoutInflater.from(FinalOrder.this);
                View dialogView = inflater.inflate(R.layout.confirm_dialog, null);
                dialogBuilder.setView(dialogView);
                final AlertDialog alertDialog = dialogBuilder.create();
                alertDialog.show();

                dialogView.findViewById(R.id.btn_yes).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        confirmorder();
                        alertDialog.dismiss();

                    }
                });

                dialogView.findViewById(R.id.btn_no).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        alertDialog.dismiss();

                    }
                });




            }
        });

        remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(FinalOrder.this);
                LayoutInflater inflater =LayoutInflater.from(FinalOrder.this);
                View dialogView = inflater.inflate(R.layout.oedering_dialog, null);
                dialogBuilder.setView(dialogView);
                final AlertDialog alertDialog = dialogBuilder.create();
                alertDialog.show();

                dialogView.findViewById(R.id.btn_yes).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        OrderSqliteHelper db = new OrderSqliteHelper(getApplicationContext());
                        db.deleteAllWithpriceOrder();
                        db.deleteAllNOpriceOrder();
                        db.deleteAllOfferOrder();
                        db.deleteAllOrder();

                        finish();
                        Intent intent = new Intent(getApplicationContext(), FinalOrder.class);
                        startActivity(intent);
                        Toast.makeText(getApplicationContext(),"فاکتور پاک شد", Toast.LENGTH_LONG).show();
                        alertDialog.dismiss();

                    }
                });

                dialogView.findViewById(R.id.btn_no).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        alertDialog.dismiss();

                    }
                });


            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(FinalOrder.this);
                LayoutInflater inflater =LayoutInflater.from(FinalOrder.this);
                View dialogView = inflater.inflate(R.layout.cat_dialog, null);
                dialogBuilder.setView(dialogView);
                final AlertDialog alertDialog = dialogBuilder.create();
                alertDialog.show();

                dialogView.findViewById(R.id.btn_kafsh).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        alertDialog.dismiss();
                        Intent intent = new Intent(getApplicationContext(), Kafsh.class);
                        startActivity(intent);


                    }
                });


                dialogView.findViewById(R.id.btn_darmani).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {


                        if(CustomerInfo.order.equals("")){
                            Toast.makeText(getApplicationContext(), "ابتدا سفارش جدید ایجاد کنید", Toast.LENGTH_SHORT).show();
                            return;
                        }


                       if(CustomerInfo.order.equals("ریالی")) {
                           alertDialog.dismiss();
                           Intent intent = new Intent(getApplicationContext(), DarmaniRiali.class);
                           startActivity(intent);

                       }
                       if(CustomerInfo.order.equals("آفری")) {
                            alertDialog.dismiss();
                            Intent intent = new Intent(getApplicationContext(), DarmaniOfferi.class);
                            startActivity(intent);

                        }

                    }
                });

            }
        });


        getdate();



    }

    private void getKafshOrderFromSQLite() {
        // AsyncTask is used that SQLite operation not blocks the UI Thread.
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                listorder.clear();
                listorder.addAll(dbh1.getAllOrder());

                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                orderAdapter.notifyDataSetChanged();
                final String totalkafsh= formatter.format(dbh1.getsumkafshOrder());
                final String countkafsh= formatter.format(dbh1.getcountkafshOrder());
                TVkafshtotal.setText("جمع کل "+totalkafsh+" ریال ");
                tckf.setText("تعداد کل "+countkafsh+" عدد ");

                if(listorder.size()==0){
                    kf.setVisibility(View.GONE);
                    Tkf.setVisibility(View.GONE);
                    Vkf.setVisibility(View.GONE);
                    TVkafshtotal.setVisibility(View.GONE);
                    tckf.setVisibility(View.GONE);
                }
            }
        }.execute();

    }

    private void getWithpriceOrderFromSQLite() {
        // AsyncTask is used that SQLite operation not blocks the UI Thread.
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {

                listorder2.clear();
                listorder2.addAll(dbh2.getAllWithpriceOrder());

                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                orderAdapter2.notifyDataSetChanged();
                final String totalwithprice= formatter.format(dbh2.getsumWithpriceOrder());
                final String countwithprice= formatter.format(dbh2.getcountWithpriceOrder());
                TVwithpricetotal.setText("جمع کل "+totalwithprice+" ریال ");
                tcwp.setText("تعداد کل "+countwithprice+" عدد ");

                if(listorder2.size()==0){
                    wp.setVisibility(View.GONE);
                    Twp.setVisibility(View.GONE);
                    Vwp.setVisibility(View.GONE);
                    TVwithpricetotal.setVisibility(View.GONE);
                    tcwp.setVisibility(View.GONE);
                }
            }
        }.execute();

    }


    private void getNopriceOrderFromSQLite() {
        // AsyncTask is used that SQLite operation not blocks the UI Thread.
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {

                listorder3.clear();
                listorder3.addAll(dbh3.getAllNOpriceOrder());

                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                orderAdapter3.notifyDataSetChanged();
                final String totalnoprice= formatter.format(dbh3.getsumNOpriceOrder());
                final String countnoprice= formatter.format(dbh3.getcountNOpriceOrder());
                TVnopricetotal.setText("جمع کل "+totalnoprice+" ریال ");
                tcnp.setText("تعداد کل "+countnoprice+" عدد ");

                if(listorder3.size()==0){
                    np.setVisibility(View.GONE);
                    Tnp.setVisibility(View.GONE);
                    Vnp.setVisibility(View.GONE);
                    TVnopricetotal.setVisibility(View.GONE);
                    tcnp.setVisibility(View.GONE);
                }
            }
        }.execute();

    }


    private void getOfferOrderFromSQLite() {
        // AsyncTask is used that SQLite operation not blocks the UI Thread.
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {

                listorder4.clear();
                listorder4.addAll(dbh4.getAllOfferOrder());

                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                orderAdapter4.notifyDataSetChanged();
                final String totaloffer= formatter.format(dbh4.getsumOfferOrder());
                final String countoffer= formatter.format(dbh4.getcountOfferOrder());
                TVoffertotal.setText("ارزش آفر "+totaloffer+" ریال ");
                tcoff.setText("تعداد آفر "+countoffer+" عدد ");

                if(listorder4.size()==0){
                    off.setVisibility(View.GONE);
                    Toff.setVisibility(View.GONE);
                    Voff.setVisibility(View.GONE);
                    TVoffertotal.setVisibility(View.GONE);
                    tcoff.setVisibility(View.GONE);
                }

                int total=dbh1.getsumkafshOrder()+dbh2.getsumWithpriceOrder()+dbh3.getsumNOpriceOrder();
                final String totalprice= formatter.format(total);
                TVtotal.setText("جمع کل فاکتور "+totalprice+" ریال ");

            }
        }.execute();

    }


    private void confirmorder() {
        //sharing implementation here
        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);

      // all=(RelativeLayout) findViewById(R.id.rl_all);
        all=(NestedScrollView) findViewById(R.id.ns_order);

        int totalHeight = all.getChildAt(0).getHeight();
        int totalWidth = all.getChildAt(0).getWidth();

        Bitmap shareBody = Bitmap.createBitmap(totalWidth,totalHeight,Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(shareBody);
        all.draw(canvas);

        String root = Environment.getExternalStorageDirectory().toString();
        File myDir = new File(root+"/Tanyar/Order");
        myDir.mkdirs();
        filename=TVnumber.getText().toString().trim()+" - "+TVtype.getText().toString().trim()+" "+TVname.getText().toString().trim();
        long imagename = System.currentTimeMillis();
        String fname =filename+".png";
        File file = new File (myDir, fname);
        if (file.exists ()) file.delete ();
        try {
            FileOutputStream out = new FileOutputStream(file);
            shareBody.compress(Bitmap.CompressFormat.PNG, 100, out);
            out.flush();
            out.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        Toast.makeText(getApplicationContext(),"فاکتور در بخش سفارشات من ذخیره شد", Toast.LENGTH_LONG).show();

        //sharingIntent.setType("image/*");
        //sharingIntent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file));
       // startActivity(Intent.createChooser(sharingIntent, "ارسال فاکتور سفارش"));
    }

    public void getdate(){

        //date
        Roozh shamsi=new Roozh();
        Date cur = Calendar.getInstance().getTime();

        SimpleDateFormat timeformat=new SimpleDateFormat("HH:mm");
        String time=timeformat.format(cur);
        SimpleDateFormat dayformat=new SimpleDateFormat("dd");
        String day=dayformat.format(cur);
        SimpleDateFormat monthformat=new SimpleDateFormat("MM");
        String month=monthformat.format(cur);
        SimpleDateFormat yearformat=new SimpleDateFormat("yyyy");
        String year=yearformat.format(cur);
        int cYear=Integer.parseInt(year);
        int cMonth=Integer.parseInt(month);
        int cDay=Integer.parseInt(day);
        shamsi.GregorianToPersian(cYear, cMonth, cDay);
        String date=shamsi.toString();
        String tarikh=shamsi.toString()+" - "+time;

        final UserData current = UserSharedPref.getInstance(getApplicationContext()).getUser();
        final String username=String.valueOf(current.getUsername());

        TVnumber.setText(username+date.replace("/", "")+time.replace(":", ""));

        TVdate.setText(tarikh);
        //Toast.makeText(getApplicationContext(),tarikh, Toast.LENGTH_SHORT).show();

    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }


}