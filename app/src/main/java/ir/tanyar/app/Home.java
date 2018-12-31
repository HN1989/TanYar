package ir.tanyar.app;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.DexterError;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.PermissionRequestErrorListener;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class Home extends AppCompatActivity {

    private Toolbar toolbar;

    ProgressBar progressBar;

    Button factor,ordering,customers,orders,logut,database;

    private TextView user,curdate;

    private OrderSqliteHelper db;

    private UserSqliteHelper db2;

    private ProSqliteHelper dbhkafsh;
    private KafshModel prokafsh;

    private ProSqliteHelper dbhdarmani1;
    private DarmaniModel prodarmani1;

    private ProSqliteHelper dbhdarmani2;
    private DarmaniModel prodarmani2;


    RequestQueue queue;
    int curvc;
    int lastvc;
    String PATH;

    private static String TAG;
    AlertDialog alertDialog;
    ProgressBar download;
    TextView percent;
    TextView status;
    String appurl;
    public static String offer;

    private static final int BUFFER_SIZE = 4096;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        getconfig();

        if (!UserSharedPref.getInstance(this).isLoggedIn()) {
            finish();
            startActivity(new Intent(this, LoginPage.class));
        }

        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        user = (TextView) findViewById(R.id.tx_username);
        curdate = (TextView) findViewById(R.id.tx_date);
        factor = (Button) findViewById(R.id.btn_factor);
        ordering = (Button) findViewById(R.id.btn_ordering);
        customers = (Button) findViewById(R.id.btn_customers);
        orders = (Button) findViewById(R.id.btn_orders);
        logut = (Button) findViewById(R.id.btn_logout);
        database = (Button) findViewById(R.id.btn_database);
        final UserData current = UserSharedPref.getInstance(getApplicationContext()).getUser();
        final String username=String.valueOf(current.getUsername());
        db2 = new UserSqliteHelper(getApplicationContext());
        String name=db2.logeduser(username);
        user.setText(name);

        factor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(), FinalOrder.class);
                startActivity(intent);

            }
        });

        orders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(), Orders.class);
                startActivity(intent);

            }
        });

        ordering.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                        Intent intent = new Intent(getApplicationContext(), CustomerInfo.class);
                        startActivity(intent);

            }
        });

        customers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(), Customers.class);
                startActivity(intent);
            }
        });


        logut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               UserSharedPref.getInstance(getApplicationContext()).logout();
                InfoSharedPref.getInstance(getApplicationContext()).clearCustomer();
               Intent intent = new Intent(getApplicationContext(), LoginPage.class);
               startActivity(intent);
            }
        });

        database.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new UpdateDB().execute();

            }
        });

        getdate();

    }

    // DownloadFile AsyncTask
    class UpdateDB extends AsyncTask<String,Integer,Boolean> {

        final ProgressDialog dialog = new ProgressDialog(Home.this);

        @Override
        protected void onPreExecute() {
            super.onPreExecute();


            dialog.setTitle("بروزرسانی لیست محصولات");
            dialog.setMessage("منتظر باشید...");
            dialog.setIndeterminate(true);
            dialog.setCancelable(false);
            dialog.show();

        }

        protected void onProgressUpdate(Integer... progress) {
            super.onProgressUpdate(progress);


        }
        @Override
        protected void onPostExecute(Boolean result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);

            dialog.dismiss();
                Toast.makeText(getApplicationContext(),"بروزرسانی شد",
                        Toast.LENGTH_SHORT).show();


        }

        @Override
        protected Boolean doInBackground(String... arg0) {
            Boolean flag = false;

            try {

                ProSqliteHelper REMOVEdb = new ProSqliteHelper(getApplicationContext());
                REMOVEdb.deleteAllKafsh();
                REMOVEdb.deleteAlldarmani1();
                REMOVEdb.deleteAlldarmani2();
                addkafsh();
                adddarmani1();
                adddarmani2();


                flag = true;
            } catch (Exception e) {

                flag = false;
            }
            return flag;

        }

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
        String tarikh=shamsi.toString();

        SimpleDateFormat sdf = new SimpleDateFormat("EEEE");
        String dayOfTheWeek = sdf.format(cur);

        if(dayOfTheWeek.equals("Friday")){dayOfTheWeek="جمعه";}
        if(dayOfTheWeek.equals("Thursday")){dayOfTheWeek="پنجشنبه";}
        if(dayOfTheWeek.equals("Wednesday")){dayOfTheWeek="چهارشنبه";}
        if(dayOfTheWeek.equals("Tuesday")){dayOfTheWeek="سه شنبه";}
        if(dayOfTheWeek.equals("Monday")){dayOfTheWeek="دوشنبه";}
        if(dayOfTheWeek.equals("Sunday")){dayOfTheWeek="یکشنبه";}
        if(dayOfTheWeek.equals("Saturday")){dayOfTheWeek="شنبه";}

        curdate.setText(dayOfTheWeek+" "+tarikh+" ساعت "+time);
        //Toast.makeText(getApplicationContext(),tarikh, Toast.LENGTH_SHORT).show();

    }

    //add kafsh product
    public void addkafsh (){
        try {
            File yourFile = new File(Environment.getExternalStorageDirectory(), "Tanyar/Data/kafsh.json");
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
            JSONArray m_jArry = obj.getJSONArray("kafsh");
            dbhkafsh = new ProSqliteHelper(getApplicationContext());
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
                String b1name = jo_inside.getString("b1name");
                String b1price = jo_inside.getString("b1price");
                String b2name = jo_inside.getString("b2name");
                String b2price = jo_inside.getString("b2price");
                String b3name = jo_inside.getString("b3name");
                String b3price = jo_inside.getString("b3price");
                String b4name = jo_inside.getString("b4name");
                String b4price = jo_inside.getString("b4price");
                String size = jo_inside.getString("size");
                String selltype = jo_inside.getString("selltype");

                prokafsh = new KafshModel();
                prokafsh.setRadif(radif);
                prokafsh.setGroup(group);
                prokafsh.setCode(code);
                prokafsh.setColor(color);
                prokafsh.setProperty(property);
                prokafsh.setBox(box);
                prokafsh.setHulfbox(hulfbox);
                prokafsh.setJur(jur);
                prokafsh.setNajur(najur);
                prokafsh.setB1name(b1name);
                prokafsh.setB1price(b1price);
                prokafsh.setB2name(b2name);
                prokafsh.setB2price(b2price);
                prokafsh.setB3name(b3name);
                prokafsh.setB3price(b3price);
                prokafsh.setB4name(b4name);
                prokafsh.setB4price(b4price);
                prokafsh.setSize(size);
                prokafsh.setSelltype(selltype);
                dbhkafsh.addKafsh(prokafsh);
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    //add darmani1 product
    public void adddarmani1 (){
        try {
            File yourFile = new File(Environment.getExternalStorageDirectory(), "Tanyar/Data/darmani1.json");
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
            JSONArray m_jArry = obj.getJSONArray("darmani1");
            dbhdarmani1 = new ProSqliteHelper(getApplicationContext());
            //dbh.deleteAllProduct();

            for (int i = 0; i < m_jArry.length(); i++) {
                JSONObject jo_inside = m_jArry.getJSONObject(i);

                String row = jo_inside.getString("row");
                String code = jo_inside.getString("code");
                String name = jo_inside.getString("name");
                String size = jo_inside.getString("size");
                String property = jo_inside.getString("property");
                String buyer = jo_inside.getString("buyer");
                String consumer = jo_inside.getString("consumer");
                String b1name = jo_inside.getString("b1name");
                String b1price = jo_inside.getString("b1price");
                String b2name = jo_inside.getString("b2name");
                String b2price = jo_inside.getString("b2price");
                String b3name = jo_inside.getString("b3name");
                String b3price = jo_inside.getString("b3price");
                String b4name = jo_inside.getString("b4name");
                String b4price = jo_inside.getString("b4price");

                prodarmani1 = new DarmaniModel();
                prodarmani1.setRow(row);
                prodarmani1.setCode(code);
                prodarmani1.setName(name);
                prodarmani1.setSize(size);
                prodarmani1.setProperty(property);
                prodarmani1.setBuyer(buyer);
                prodarmani1.setConsumer(consumer);
                prodarmani1.setB1name(b1name);
                prodarmani1.setB1price(b1price);
                prodarmani1.setB2name(b2name);
                prodarmani1.setB2price(b2price);
                prodarmani1.setB3name(b3name);
                prodarmani1.setB3price(b3price);
                prodarmani1.setB4name(b4name);
                prodarmani1.setB4price(b4price);
                dbhdarmani1.addDarmani1(prodarmani1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    //add darmani2 product
    public void adddarmani2 (){
        try {
            File yourFile = new File(Environment.getExternalStorageDirectory(), "Tanyar/Data/darmani2.json");
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
            JSONArray m_jArry = obj.getJSONArray("darmani2");
            dbhdarmani2 = new ProSqliteHelper(getApplicationContext());
            //dbh.deleteAllProduct();

            for (int i = 0; i < m_jArry.length(); i++) {
                JSONObject jo_inside = m_jArry.getJSONObject(i);

                String row = jo_inside.getString("row");
                String code = jo_inside.getString("code");
                String name = jo_inside.getString("name");
                String size = jo_inside.getString("size");
                String property = jo_inside.getString("property");
                String buyer = jo_inside.getString("buyer");
                String consumer = jo_inside.getString("consumer");
                String b1name = jo_inside.getString("b1name");
                String b1price = jo_inside.getString("b1price");
                String b2name = jo_inside.getString("b2name");
                String b2price = jo_inside.getString("b2price");
                String b3name = jo_inside.getString("b3name");
                String b3price = jo_inside.getString("b3price");
                String b4name = jo_inside.getString("b4name");
                String b4price = jo_inside.getString("b4price");

                prodarmani2 = new DarmaniModel();
                prodarmani2.setRow(row);
                prodarmani2.setCode(code);
                prodarmani2.setName(name);
                prodarmani2.setSize(size);
                prodarmani2.setProperty(property);
                prodarmani2.setBuyer(buyer);
                prodarmani2.setConsumer(consumer);
                prodarmani2.setB1name(b1name);
                prodarmani2.setB1price(b1price);
                prodarmani2.setB2name(b2name);
                prodarmani2.setB2price(b2price);
                prodarmani2.setB3name(b3name);
                prodarmani2.setB3price(b3price);
                prodarmani2.setB4name(b4name);
                prodarmani2.setB4price(b4price);
                dbhdarmani2.addDarmani2(prodarmani2);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


//config
    public void getconfig (){
        try {
            File yourFile = new File(Environment.getExternalStorageDirectory(), "Tanyar/Data/config.json");
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

            JSONArray obj = new JSONArray(jsonStr);

            for (int i = 0; i < obj.length(); i++) {
                try {

                    JSONObject response = obj.getJSONObject(i);

                    JSONArray note = response.getJSONArray("notes");
                    curvc =response.getInt("version");
                    appurl=response.getString("url");
                    offer=response.getString("offer");

                    int length = note.length();
                    if (length > 0) {
                        String [] recipients = new String [length];
                        for (int j = 0; j < length; j++) {
                            recipients[j] = note.getString(j);
                        }

                        StringBuilder builder = new StringBuilder();
                        for (String details : recipients) {
                            builder.append(details + "\n\n");
                        }

                        String NOTES=builder.toString();

                        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(Home.this);
                        LayoutInflater inflater =LayoutInflater.from(Home.this);
                        View dialogView = inflater.inflate(R.layout.config_dialog, null);
                        dialogBuilder.setView(dialogView);
                        final AlertDialog alertDialog = dialogBuilder.create();
                        alertDialog.show();

                        alertDialog.setCanceledOnTouchOutside(false);
                        alertDialog.setCancelable(false);

                        TextView TVname = (TextView) dialogView.findViewById(R.id.tx_config);
                        TVname.setText(NOTES);

                        dialogView.findViewById(R.id.btn_update).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {


                                if(!isNetworkConnected()){
                                    Toast.makeText(getApplicationContext(), "اینترنت گوشی خود را روشن کنید", Toast.LENGTH_SHORT).show();
                                }else {
                                    Update();
                                    alertDialog.dismiss();
                                }

                            }
                        });

                        dialogView.findViewById(R.id.btn_inter).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                alertDialog.dismiss();

                            }
                        });

                    }} catch (Exception e) {
                    System.out.println(e.getMessage());
                } finally {

                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void Update() {

        progressBar.setVisibility(View.VISIBLE);

            String upurl = "http://www.siegefall.ir/tanyar/config.json";
            queue = NetworkController.getInstance(this).getRequestQueue();
            JsonArrayRequest newsReq = new JsonArrayRequest(upurl, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {

                    progressBar.setVisibility(View.INVISIBLE);

                    for (int i = 0; i < response.length(); i++) {
                        try {

                            JSONObject obj = response.getJSONObject(i);

                            lastvc = obj.getInt("version");
                            //appurl=obj.getString("url");
                            //Toast.makeText(getApplicationContext(), "code: "+appurl, Toast.LENGTH_SHORT).show();

                        } catch (Exception e) {
                            System.out.println(e.getMessage());
                        } finally {

                        }
                    }

                    if (lastvc > curvc) {

                        //Toast.makeText(getApplicationContext(), "آپدیت لازم است", Toast.LENGTH_SHORT).show();
                        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(Home.this);
                        LayoutInflater inflater = LayoutInflater.from(Home.this);
                        View dialogView = inflater.inflate(R.layout.app_update, null);
                        dialogBuilder.setView(dialogView);
                        alertDialog = dialogBuilder.create();
                        alertDialog.show();

                        //alertDialog.setCancelable(false);
                        alertDialog.setCanceledOnTouchOutside(false);

                        download = (ProgressBar) dialogView.findViewById(R.id.pr_update);
                        percent = (TextView) dialogView.findViewById(R.id.tx_percent);
                        status = (TextView) dialogView.findViewById(R.id.tx_status);
                        download.getProgressDrawable().setColorFilter(Color.parseColor("#2a8ee6"), PorterDuff.Mode.SRC_IN);

                        dialogView.findViewById(R.id.btn_update).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                // new DownloadNewVersion().execute();
                                UpdatePermission();

                            }
                        });

                    }else{

                        Toast.makeText(getApplicationContext(), "برنامه نیاز به بروزرسانی ندارد", Toast.LENGTH_LONG).show();

                    }


                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    progressBar.setVisibility(View.INVISIBLE);

                }
            });
            queue.add(newsReq);



    }

    // DownloadFile AsyncTask
    class DownloadNewVersion extends AsyncTask<String,Integer,Boolean> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            //download.setIndeterminate(true);
            download.setVisibility(View.VISIBLE);
            percent.setVisibility(View.VISIBLE);
            status.setVisibility(View.VISIBLE);

        }

        protected void onProgressUpdate(Integer... progress) {
            super.onProgressUpdate(progress);

            //download.setIndeterminate(false);
            download.setMax(100);
            download.setProgress(progress[0]);
            String msg = "";
            String percentage = "";
            if(progress[0]>99){

                msg="آماده سازی... ";

            }else {

                msg="در حال دانلود... ";
                percentage=progress[0]+"%";
            }
            percent.setText(percentage);
            status.setText(msg);

        }
        @Override
        protected void onPostExecute(Boolean result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);

            //download.dismiss();

            if(result){

                //Toast.makeText(getApplicationContext(),"دانلود تمام شد", Toast.LENGTH_SHORT).show();
                alertDialog.dismiss();
                new UnzipUpdate().execute();
                database.performClick();


            }else{

                Toast.makeText(getApplicationContext(),"خطایی رخ داد دوباره تلاش کنید",
                        Toast.LENGTH_SHORT).show();

            }

        }

        @Override
        protected Boolean doInBackground(String... arg0) {
            Boolean flag = false;

            try {


                URL url = new URL(appurl);

                HttpURLConnection c = (HttpURLConnection) url.openConnection();
                c.setRequestMethod("GET");
                c.setDoOutput(true);
                c.connect();

                PATH = Environment.getExternalStorageDirectory()+"/Tanyar/Data/";
                File file = new File(PATH);
                file.mkdirs();

                File outputFile = new File(file,"update.zip");

                if(outputFile.exists()){
                    outputFile.delete();
                }

                FileOutputStream fos = new FileOutputStream(outputFile);
                InputStream is = c.getInputStream();

                int total_size = 3670016;//size of apk

                byte[] buffer = new byte[1024];
                int len1 = 0;
                int per = 0;
                int downloaded=0;
                while ((len1 = is.read(buffer)) != -1) {
                    fos.write(buffer, 0, len1);
                    downloaded +=len1;
                    per = (int) (downloaded * 100 / total_size);
                    publishProgress(per);
                }
                fos.close();
                is.close();

                //OpenNewVersion(PATH);


                /**
                    File[] content = file.listFiles();
                    for(int i = 0; i < content.length; i++) {
                        //move content[i]
                        File delete = new File(Environment.getExternalStorageDirectory()+"/Tanyar/Data/"+content[i].getName());
                        delete.delete();

                        if(!delete.exists()){content[i].renameTo(new File(Environment.getExternalStorageDirectory()+"/Tanyar/Data/"+content[i].getName()));}
                    }
                 **/

                flag = true;
            } catch (Exception e) {
                Log.e(TAG, "Update Error: " + e.getMessage());
                flag = false;
            }
            return flag;

        }

    }

    // DownloadFile AsyncTask
    class UnzipUpdate extends AsyncTask<String,Integer,Boolean> {

        final ProgressDialog dialog = new ProgressDialog(Home.this);

        @Override
        protected void onPreExecute() {
            super.onPreExecute();


            dialog.setTitle("بروزرسانی برنامه");
            dialog.setMessage("منتظر باشید...");
            dialog.setIndeterminate(true);
            dialog.setCancelable(false);
            dialog.show();

        }

        protected void onProgressUpdate(Integer... progress) {
            super.onProgressUpdate(progress);


        }
        @Override
        protected void onPostExecute(Boolean result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);

            dialog.dismiss();
            Toast.makeText(getApplicationContext(),"بروزرسانی شد",
                    Toast.LENGTH_SHORT).show();


        }

        @Override
        protected Boolean doInBackground(String... arg0) {
            Boolean flag = false;

            try {

                unzip(PATH+"update.zip",Environment.getExternalStorageDirectory()+"/Tanyar/Data");

                flag = true;
            } catch (Exception e) {

                flag = false;
            }
            return flag;

        }

    }


    public void UpdatePermission() {
        Dexter.withActivity(this)
                .withPermissions(
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        // check if all permissions are granted
                        if (report.areAllPermissionsGranted()) {
                            //Toast.makeText(getApplicationContext(), "All permissions are granted!", Toast.LENGTH_SHORT).show();
                            new DownloadNewVersion().execute();
                        }

                        // check for permanent denial of any permission
                        if (report.isAnyPermissionPermanentlyDenied()) {
                            // show alert dialog navigating to Settings

                            Toast.makeText(getApplicationContext(),"لطفا دسترسی های لازم را از بخش تنظیمات تایید کنید", Toast.LENGTH_LONG).show();
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

    public void unzip(String zipFilePath, String destDirectory) throws IOException {
        File destDir = new File(destDirectory);
        if (!destDir.exists()) {
            destDir.mkdir();
        }
        ZipInputStream zipIn = new ZipInputStream(new FileInputStream(zipFilePath));
        ZipEntry entry = zipIn.getNextEntry();

        // iterates over entries in the zip file
        while (entry != null) {
            String filePath = destDirectory + File.separator + entry.getName();
            if (!entry.isDirectory()) {
                // if the entry is a file, extracts it
                extractFile(zipIn, filePath);
            } else {
                // if the entry is a directory, make the directory
                File dir = new File(filePath);
                dir.mkdir();
            }
            zipIn.closeEntry();
            entry = zipIn.getNextEntry();
        }
        zipIn.close();
    }

    private void extractFile(ZipInputStream zipIn, String filePath) throws IOException {
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(filePath));
        byte[] bytesIn = new byte[BUFFER_SIZE];
        int read = 0;
        while ((read = zipIn.read(bytesIn)) != -1) {
            bos.write(bytesIn, 0, read);
        }
        bos.close();
    }



    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;
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