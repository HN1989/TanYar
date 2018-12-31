package ir.tanyar.app;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class Orders extends AppCompatActivity {

    public ListView lv;
    private String[] FilePathStrings;
    private String[] FileNameStrings;
    private File[] listFile;

    ArrayList<String> filelist;

    File file;
    ArrayAdapter<String> adapter;
    // Search EditText
    EditText inputSearch;

    Roozh shamsi;

    MediaPlayer hazf;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.orders);

        shamsi=new Roozh();

        filelist=new ArrayList<String>();


        lv=(ListView) findViewById(R.id.list);
        inputSearch = (EditText) findViewById(R.id.inputSearch);

        // Check for SD Card
        if (!Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            Toast.makeText(getApplicationContext(), "حافظه پیدا نشد!", Toast.LENGTH_LONG)
                    .show();
        } else {
            // Locate the image folder in your SD Card
            file = new File(Environment.getExternalStorageDirectory()
                    + File.separator + "Tanyar/Order");
            // Create a new folder if no folder named SDImageTutorial exist
            file.mkdirs();
        }

        if (file.isDirectory()) {
            listFile = file.listFiles();

            Arrays.sort( listFile, new Comparator<Object>()
            {
                public int compare(Object o1, Object o2) {

                    if (((File)o1).lastModified() > ((File)o2).lastModified()) {
                        return -1;
                    } else if (((File)o1).lastModified() < ((File)o2).lastModified()) {
                        return +1;
                    } else {
                        return 0;
                    }
                }

            });


            // Create a String array for FilePathStrings
            FilePathStrings = new String[listFile.length];
            // Create a String array for FileNameStrings
            FileNameStrings = new String[listFile.length];

            for (int i = 0; i < listFile.length; i++) {
                // Get the path of the image file
                FilePathStrings[i] = listFile[i].getAbsolutePath();
                // Get the name image file
                FileNameStrings[i] = listFile[i].getName();
            }
        }

        for(String name: FileNameStrings){
            name=name.substring(0, name.indexOf("."));
            filelist.add(name);
            Log.d("Files","FileName:"+name);
        }


        adapter = new FileAdapter(Orders.this, R.layout.order_item, R.id.tv_list,filelist);
        lv.setAdapter(adapter);
        lv.setTextFilterEnabled(true);

        inputSearch.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
                Orders.this.adapter.getFilter().filter(arg0);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                                          int arg3) {
                // TODO Auto-generated method stub

            }

            @Override
            public void afterTextChanged(Editable arg0) {


            }
        });






    }


    private class FileAdapter extends ArrayAdapter<String>{


        public FileAdapter(Context context, int resource, int textViewResourceId,
                           ArrayList<String> filelist) {
            super(context, resource, textViewResourceId, filelist);
            // TODO Auto-generated constructor stub
        }

        @SuppressLint("SimpleDateFormat")
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row = inflater.inflate(R.layout.order_item, parent, false);

            final String fileName = adapter.getItem(position);

            final File root = new File(Environment.getExternalStorageDirectory(), "Tanyar/Order/"+fileName+".png");
            Date last=new Date(root.lastModified());

            SimpleDateFormat dayformat=new SimpleDateFormat("dd");
            String day=dayformat.format(last);

            SimpleDateFormat monthformat=new SimpleDateFormat("MM");
            String month=monthformat.format(last);

            SimpleDateFormat yearformat=new SimpleDateFormat("yyyy");
            String year=yearformat.format(last);

            int cYear=Integer.parseInt(year);
            int cMonth=Integer.parseInt(month);
            int cDay=Integer.parseInt(day);

            shamsi.GregorianToPersian(cYear, cMonth, cDay);

            String tarikh=shamsi.toString();

            CardView item = (CardView) row.findViewById(R.id.order_item);
            Button send = (Button) row.findViewById(R.id.btn_send);
            TextView tv = (TextView) row.findViewById(R.id.tv_list);
            TextView tv2 = (TextView) row.findViewById(R.id.tv_date);
            tv2.setText(tarikh);
            tv.setText(fileName);

            tv.setTextSize(18);

            item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        Uri imgUri = FileProvider.getUriForFile(Orders.this, BuildConfig.APPLICATION_ID + ".provider", root);
                        Intent intent = new Intent();
                        intent.setAction(Intent.ACTION_VIEW);
                        intent.setData(imgUri);
                        intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                        startActivity(intent);
                    } else {
                        Intent intent = new Intent();
                        intent.setAction(Intent.ACTION_VIEW);
                        intent.setDataAndType(Uri.fromFile(root), "image/*");
                        startActivity(intent);
                    }

                }
            });

            send.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    //File root = new File(Environment.getExternalStorageDirectory(),  "Tanyar/Order/"+fileName+".png");
                    Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                    sharingIntent.setType("*/*");
                    sharingIntent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(root));
                    startActivity(Intent.createChooser(sharingIntent, "ارسال فاکتور سفارش"));
                }
            });

            return row;
        }



    }


    // Capture gridview item click

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }


}