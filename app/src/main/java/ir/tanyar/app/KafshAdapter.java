package ir.tanyar.app;

import android.content.Context;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class KafshAdapter extends RecyclerView.Adapter<KafshAdapter.UserViewHolder> {

    private List<KafshModel> listPRO;
    private Context context;
    private LayoutInflater inflater;

    Spinner SPsell,SPcolor,SPsize,SPcount;
    ArrayList<String> stringArraySell ;
    ArrayList<String> stringArrayColor ;
    ArrayList<String> stringArraySize ;
    ArrayList<String> stringArrayCount ;

    private OrderSqliteHelper databaseHelper;
    private KafshOrderModel order;

    public KafshAdapter(Context context, List<KafshModel> listPRO) {

        this.context = context;
        this.listPRO = listPRO;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public UserViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // inflating recycler item view
        View rootView = inflater.inflate(R.layout.kafsh_item, parent, false);

        return new UserViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(final UserViewHolder holder, int position) {
        //holder.id.setText(String.valueOf(listPRO.get(position).getId()));

        final String code=listPRO.get(position).getCode();
        final String row=listPRO.get(position).getRadif();
        final String property=listPRO.get(position).getProperty();
        final String jur=listPRO.get(position).getJur();
        final String najur=listPRO.get(position).getNajur();
        final String box=listPRO.get(position).getBox();
        final String hulfbox=listPRO.get(position).getHulfbox();
        final String color=listPRO.get(position).getColor();
        final String group=listPRO.get(position).getGroup();
        final String b1name=listPRO.get(position).getB1name();
        final String b1price=listPRO.get(position).getB1price();
        final String b2name=listPRO.get(position).getB2name();
        final String b2price=listPRO.get(position).getB2price();
        final String b3name=listPRO.get(position).getB3name();
        final String b3price=listPRO.get(position).getB3price();
        final String b4name=listPRO.get(position).getB4name();
        final String b4price=listPRO.get(position).getB4price();
        final String size=listPRO.get(position).getSize();
        final String selltype=listPRO.get(position).getSelltype();

        if(code.startsWith("5")){holder.TVname.setText("کفش مردانه");}
        if(code.startsWith("4")){holder.TVname.setText("کفش زنانه");}
        if(code.startsWith("3")){holder.TVname.setText("صندل مردانه");}
        if(code.startsWith("2")){holder.TVname.setText("صندل زنانه");}

        //holder.TVproperty.setText(listPRO.get(position).getProperty());
       // holder.TVjur.setText(listPRO.get(position).getJur());
       // holder.TVnajur.setText(listPRO.get(position).getNajur());
        holder.TVcode.setText(code);
        holder.TVrow.setText("شماره "+row);

       // Context context = holder.photo.getContext();
       // int id = context.getResources().getIdentifier(code, "drawable", context.getPackageName());
       // holder.photo.setImageResource(id);

        DisplayMetrics displayMetrics=context.getResources().getDisplayMetrics();
        int height = displayMetrics.heightPixels;
        int width = displayMetrics.widthPixels;
        holder.photo.getLayoutParams().height = (width/3)-20;
        holder.photo.getLayoutParams().width = width/3;

        final File image = new File(Environment.getExternalStorageDirectory(), "Tanyar/Data/"+code+".jpg");
        //String filename = "Sample.jpg";
        //String path = "/mnt/sdcard/" + filename;
        //File f = new File(path);  //

        if(image.exists()){
            Uri imageUri = Uri.fromFile(image);
            Picasso.with(context).load(imageUri).placeholder(R.drawable.loading_image).into(holder.photo);
        }else{
            holder.photo.setImageResource(R.drawable.empty);
        }

            // holder.photo.setImageResource(R.drawable.k508);

        //holder.nameTxt.setText(s.getName());


        //Toast.makeText(context,String.valueOf(listPRO.get(position).getId()), Toast.LENGTH_SHORT).show();

        holder.item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
                LayoutInflater inflater = LayoutInflater.from(context);
                View dialogView = inflater.inflate(R.layout.kafsh_dialog, null);
                dialogBuilder.setView(dialogView);
                final AlertDialog alertDialog = dialogBuilder.create();
                alertDialog.show();

                alertDialog.setCanceledOnTouchOutside(false);

                final ImageView IMphoto;
                final TextView TVname,TVjur,TVnajur,TVproperty,TVcode,TVrow,TVsize,TVcount;
                final TextView TVbrand1,TVbrand2,TVbrand3,TVbrand4;
                final TableRow TBnajur;
                final Button BTadd;

                TBnajur = (TableRow) dialogView.findViewById(R.id.tb_najur);
                BTadd = (Button) dialogView.findViewById(R.id.btn_add);

                IMphoto = (ImageView) dialogView.findViewById(R.id.kafsh_photo);
                TVname = (TextView) dialogView.findViewById(R.id.kafsh_name);
                TVjur = (TextView) dialogView.findViewById(R.id.kafsh_jur);
                TVnajur = (TextView) dialogView.findViewById(R.id.kafsh_najur);
                TVproperty = (TextView) dialogView.findViewById(R.id.kafsh_property);
                TVcode = (TextView) dialogView.findViewById(R.id.kafsh_code);
                TVrow = (TextView) dialogView.findViewById(R.id.kafsh_row);
                TVsize = (TextView) dialogView.findViewById(R.id.tx_size);
                TVcount = (TextView) dialogView.findViewById(R.id.tx_count);

                TVbrand1 = (TextView) dialogView.findViewById(R.id.brand1);
                TVbrand2 = (TextView) dialogView.findViewById(R.id.brand2);
                TVbrand3 = (TextView) dialogView.findViewById(R.id.brand3);
                TVbrand4 = (TextView) dialogView.findViewById(R.id.brand4);

                TVbrand1.setText(b1name+" "+b1price+" ریال ");
                TVbrand2.setText(b2name+" "+b2price+" ریال ");
                TVbrand3.setText(b3name+" "+b3price+" ریال ");
                TVbrand4.setText(b4name+" "+b4price+" ریال ");

                TVjur.setText(" قیمت کارتن "+jur+" ریال");
                TVnajur.setText(" قیمت جفت "+najur+" ریال");
                TVproperty.setText(property);
                TVcode.setText(" کد محصول "+code);
                TVrow.setText(" شماره محصول "+row);

                if(code.startsWith("5")){TVname.setText("کفش مردانه");}
                if(code.startsWith("4")){TVname.setText("کفش زنانه");}
                if(code.startsWith("3")){TVname.setText("صندل مردانه");}
                if(code.startsWith("2")){TVname.setText("صندل زنانه");}

                //sell type
                SPsell=(Spinner) dialogView.findViewById(R.id.sp_sell_type);
                stringArraySell = new ArrayList<String>();
                String[] items2 = selltype.split("-");

                for (String s: items2) {

                    if(s.equals("کارتن")){stringArraySell.add("کارتن"+box+"تایی");}
                    if(s.equals("نیم کارتن")){stringArraySell.add("نیم کارتن"+hulfbox+"تایی");}
                    if(s.equals("جفت")){stringArraySell.add("جفت");}
                }


                ArrayAdapter<String> adaptersell = new ArrayAdapter<String>(context, R.layout.spineritem, stringArraySell);
                adaptersell.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                SPsell.setAdapter(adaptersell);

                //color type
                SPcolor=(Spinner) dialogView.findViewById(R.id.sp_color_type);
                stringArrayColor = new ArrayList<String>();

                if(color.contains("0")){stringArrayColor.add("سفید(0)");}
                if(color.contains("1")){stringArrayColor.add("استخوانی(1)");}
                if(color.contains("2")){stringArrayColor.add("فیلی(2)");}
                if(color.contains("3")){stringArrayColor.add("قرمز(3)");}
                if(color.contains("4")){stringArrayColor.add("زرشکی(4)");}
                if(color.contains("5")){stringArrayColor.add("عسلی(5)");}
                if(color.contains("6")){stringArrayColor.add("قهوه ای(6)");}
                if(color.contains("7")){stringArrayColor.add("سرمه ای(7)");}
                if(color.contains("8")){stringArrayColor.add("طوسی(8)");}
                if(color.contains("9")){stringArrayColor.add("مشکی(9)");}

                ArrayAdapter<String> adaptercolor = new ArrayAdapter<String>(context, R.layout.spineritem, stringArrayColor);
                adaptercolor.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                SPcolor.setAdapter(adaptercolor);

                //size
                SPsize=(Spinner) dialogView.findViewById(R.id.sp_size);
                String[] items = size.split("-");
                stringArraySize = new ArrayList<String>(Arrays.asList(items));

                ArrayAdapter<String> adaptersize = new ArrayAdapter<String>(context, R.layout.spineritem, stringArraySize);
                adaptersize.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                SPsize.setAdapter(adaptersize);

                SPsell.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        String sell = SPsell.getSelectedItem().toString();
                        if(!sell.contains("جفت")){
                            SPsize.setEnabled(false);
                            TBnajur.setVisibility(View.GONE);
                            BTadd.setVisibility(View.GONE);

                        }else{
                            SPsize.setEnabled(true);
                            TBnajur.setVisibility(View.VISIBLE);
                            BTadd.setVisibility(View.VISIBLE);

                        }
                    }
                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                });

                //count
                SPcount=(Spinner) dialogView.findViewById(R.id.sp_count);
                stringArrayCount = new ArrayList<String>();

                for (int i = 1; i <= 300; i++) {
                    stringArrayCount.add(String.valueOf(i));
                }

                ArrayAdapter<String> adaptercount = new ArrayAdapter<String>(context, R.layout.spineritem, stringArrayCount);
                adaptercount.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                SPcount.setAdapter(adaptercount);

                //photo
                if(image.exists()){
                    Uri imageUri = Uri.fromFile(image);
                    Picasso.with(context).load(imageUri).placeholder(R.drawable.loading_image).into(IMphoto);
                }else{
                    IMphoto.setImageResource(R.drawable.empty);
                }

                dialogView.findViewById(R.id.btn_add).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        int count=Integer.valueOf(SPcount.getSelectedItem().toString());
                        String size=SPsize.getSelectedItem().toString();

                        String color = SPcolor.getSelectedItem().toString().replaceAll("[0-9]","").replace(")","").replace("(","");

                        int cur=Integer.valueOf(TVcount.getText().toString());
                        int main=cur+count;
                        TVcount.setText(String.valueOf(main));
                       // TVsize.setText(TVsize.getText().toString()+" "+color+size+"("+count+")");
                        TVsize.setText(TVsize.getText().toString()+" "+size+"("+count+")");

                    }
                });


                dialogView.findViewById(R.id.btn_remove).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        TVsize.setText("");
                        TVcount.setText("0");

                    }
                });


                //add order
                databaseHelper = new OrderSqliteHelper(context);
                order = new KafshOrderModel();

                dialogView.findViewById(R.id.btn_order).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {


                        String sizevalue="";
                        String sell = SPsell.getSelectedItem().toString();
                        if(!sell.contains("جفت")){sizevalue="-";}else{sizevalue=TVsize.getText().toString();}

                        String pricevalue="";
                        if(!sell.contains("جفت")){pricevalue=jur;}else{pricevalue=najur;}

                        String totalvalue="";
                        int tedad=0;
                        if(sell.contains("جفت")){tedad=Integer.valueOf(TVcount.getText().toString());}
                        if(sell.contains("کارتن15تایی")){tedad=15;}
                        if(sell.contains("کارتن12تایی")){tedad=12;}
                        if(sell.contains("کارتن10تایی")){tedad=10;}
                        if(sell.contains("نیم کارتن8تایی")){tedad=8;}
                        if(sell.contains("نیم کارتن6تایی")){tedad=6;}
                        if(sell.contains("نیم کارتن5تایی")){tedad=5;}

                        String colorcode="";
                        String colors = SPcolor.getSelectedItem().toString();
                        if(colors.contains("0")){colorcode="0";}
                        if(colors.contains("1")){colorcode="1";}
                        if(colors.contains("2")){colorcode="2";}
                        if(colors.contains("3")){colorcode="3";}
                        if(colors.contains("4")){colorcode="4";}
                        if(colors.contains("5")){colorcode="5";}
                        if(colors.contains("6")){colorcode="6";}
                        if(colors.contains("7")){colorcode="7";}
                        if(colors.contains("8")){colorcode="8";}
                        if(colors.contains("9")){colorcode="9";}

                        int count=Integer.valueOf(SPcount.getSelectedItem().toString());
                        int price=Integer.valueOf(pricevalue);
                        if(sell.contains("جفت")){totalvalue=String.valueOf(tedad*price);}else{totalvalue=String.valueOf(tedad*count*price);}

                        String name=code+colorcode+"-"+TVname.getText().toString()+" "+SPcolor.getSelectedItem().toString();
                        //if(sell.contains("جفت")){
                        //    name=code+"-"+TVname.getText().toString();
                        //}else{
                        // name=code+colorcode+"-"+TVname.getText().toString()+" "+SPcolor.getSelectedItem().toString();
                       // }

                        String counts="";
                        if(sell.contains("جفت")){counts=TVcount.getText().toString();}else{
                            counts=SPcount.getSelectedItem().toString();
                        }

                        order.setCode(code);
                        order.setRow(row);
                        order.setName(name);
                        order.setSell(SPsell.getSelectedItem().toString());
                        order.setCount(counts);
                        order.setSize(sizevalue);
                        order.setPrice(pricevalue);
                        order.setTotal(totalvalue);

                        databaseHelper.addKafshOrder(order);
                        alertDialog.dismiss();

                        Toast.makeText(context,"سفارش ثبت شد", Toast.LENGTH_SHORT).show();
                       // ((Activity)context).finish();
                        //Intent intent = new Intent(context, Kafsh.class);
                        //context.startActivity(intent);

                        DecimalFormat formatter = new DecimalFormat("#,###,###");
                        OrderSqliteHelper dbh1 = new OrderSqliteHelper(context);
                        int total=dbh1.getsumkafshOrder()+dbh1.getsumWithpriceOrder()+dbh1.getsumNOpriceOrder();
                        final String totalprice= formatter.format(total);
                        Kafsh.factor.setText("مشاهده فاکتور ("+totalprice+" ریال )");
                    }
                });


            }
        });
    }

    @Override
    public int getItemCount() {
        Log.v(KafshAdapter.class.getSimpleName(),""+listPRO.size());
        return listPRO.size();
    }

    /**
     * ViewHolder class
     */
    public class UserViewHolder extends RecyclerView.ViewHolder {

        public TextView TVname,TVrow,TVjur,TVnajur,TVcode;
        public ImageView photo;

        public CardView item;

        public UserViewHolder(View view) {
            super(view);
            TVname = (TextView) view.findViewById(R.id.kafsh_name);
            //TVproperty = (TextView) view.findViewById(R.id.kafsh_property);
           // TVjur = (AppCompatTextView) view.findViewById(R.id.kafsh_jur);
           // TVnajur = (AppCompatTextView) view.findViewById(R.id.kafsh_najur);
            TVcode = (TextView) view.findViewById(R.id.kafsh_code);
            TVrow = (TextView) view.findViewById(R.id.kafsh_row);


            photo = (ImageView) view.findViewById(R.id.kafsh_photo);
            item = (CardView) view.findViewById(R.id.kafsh_item);

        }
    }


    public void filterList(List<KafshModel> filterdNames) {
        this.listPRO = filterdNames;
        notifyDataSetChanged();
    }

}