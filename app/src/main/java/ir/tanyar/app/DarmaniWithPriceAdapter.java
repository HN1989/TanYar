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
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DarmaniWithPriceAdapter extends RecyclerView.Adapter<DarmaniWithPriceAdapter.UserViewHolder> {

    private List<DarmaniModel> listPRO;
    private Context context;
    private LayoutInflater inflater;

    Spinner SPsize,SPcount;
    List<String> stringArraySize ;
    ArrayList<String> stringArrayCount ;

    private OrderSqliteHelper databaseHelper;
    private DarmaniOrderModel order;

    public DarmaniWithPriceAdapter(Context context, List<DarmaniModel> listPRO) {

        this.context = context;
        this.listPRO = listPRO;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public UserViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // inflating recycler item view
        View rootView = inflater.inflate(R.layout.darmani_item, parent, false);

        return new UserViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(final UserViewHolder holder, int position) {
        //holder.id.setText(String.valueOf(listPRO.get(position).getId()));

        final String code=listPRO.get(position).getCode();
        final String row=listPRO.get(position).getRow();
        final String property=listPRO.get(position).getProperty();
        final String name=listPRO.get(position).getName();
        final String size=listPRO.get(position).getSize();
        final String buyer=listPRO.get(position).getBuyer();
        final String consumer=listPRO.get(position).getConsumer();
        final String b1name=listPRO.get(position).getB1name();
        final String b1price=listPRO.get(position).getB1price();
        final String b2name=listPRO.get(position).getB2name();
        final String b2price=listPRO.get(position).getB2price();
        final String b3name=listPRO.get(position).getB3name();
        final String b3price=listPRO.get(position).getB3price();
        final String b4name=listPRO.get(position).getB4name();
        final String b4price=listPRO.get(position).getB4price();

        holder.TVname.setText(name);
        holder.TVcode.setText(code);
        holder.TVrow.setText("شماره "+row);

        DisplayMetrics displayMetrics=context.getResources().getDisplayMetrics();
        int height = displayMetrics.heightPixels;
        int width = displayMetrics.widthPixels;
        holder.photo.getLayoutParams().height = (width/3)-20;
        holder.photo.getLayoutParams().width = width/3;

        final File image = new File(Environment.getExternalStorageDirectory(), "Tanyar/Data/"+code+".jpg");

        if(image.exists()){
            Uri imageUri = Uri.fromFile(image);
            Picasso.with(context).load(imageUri).placeholder(R.drawable.loading_image).into(holder.photo);
        }else{
            holder.photo.setImageResource(R.drawable.empty);
        }

        //Toast.makeText(context,String.valueOf(listPRO.get(position).getId()), Toast.LENGTH_SHORT).show();


        holder.item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
                LayoutInflater inflater = LayoutInflater.from(context);
                View dialogView = inflater.inflate(R.layout.darmani_dialog, null);
                dialogBuilder.setView(dialogView);
                final AlertDialog alertDialog = dialogBuilder.create();
                alertDialog.show();

                alertDialog.setCanceledOnTouchOutside(false);

                final ImageView IMphoto;
                final TextView TVname,TVbuyer,TVconsumer,TVproperty,TVcode,TVrow,TVsize,TVcount;
                final TextView TVbrand1,TVbrand2,TVbrand3,TVbrand4;

                IMphoto = (ImageView) dialogView.findViewById(R.id.darmani_photo);
                TVname = (TextView) dialogView.findViewById(R.id.darmani_name);
                TVbuyer = (TextView) dialogView.findViewById(R.id.darmani_buyer);
                TVconsumer = (TextView) dialogView.findViewById(R.id.darmani_consumer);
                TVproperty = (TextView) dialogView.findViewById(R.id.darmani_property);
                TVcode = (TextView) dialogView.findViewById(R.id.darmani_code);
                TVrow = (TextView) dialogView.findViewById(R.id.darmani_row);
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

                TVbuyer.setText(" قیمت خریدار "+buyer+" ریال ");
                TVconsumer.setText(" قیمت مصرف کننده "+consumer+" ریال");
                TVproperty.setText(" بسته بندی "+"\n"+property);
                TVcode.setText(" کد محصول "+"\n"+code);
                TVrow.setText(" شماره محصول "+"\n"+row);
                TVname.setText(name);

                //photo
                if(image.exists()){
                    Uri imageUri = Uri.fromFile(image);
                    Picasso.with(context).load(imageUri).placeholder(R.drawable.loading_image).into(IMphoto);
                }else{
                    IMphoto.setImageResource(R.drawable.empty);
                }


                //size
                SPsize=(Spinner) dialogView.findViewById(R.id.sp_size);
                String[] items = size.split("-");
                stringArraySize = new ArrayList<String>(Arrays.asList(items));
                ArrayAdapter<String> adaptersize = new ArrayAdapter<String>(context, R.layout.spineritem, stringArraySize);
                adaptersize.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                SPsize.setAdapter(adaptersize);

                //count
                SPcount=(Spinner) dialogView.findViewById(R.id.sp_count);
                stringArrayCount = new ArrayList<String>();

                for (int i = 1; i <= 300; i++) {
                    stringArrayCount.add(String.valueOf(i));
                }

                ArrayAdapter<String> adaptercount = new ArrayAdapter<String>(context, R.layout.spineritem, stringArrayCount);
                adaptercount.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                SPcount.setAdapter(adaptercount);

                //add order
                databaseHelper = new OrderSqliteHelper(context);
                order = new DarmaniOrderModel();

                dialogView.findViewById(R.id.btn_add).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        int count=Integer.valueOf(SPcount.getSelectedItem().toString());
                        String size=SPsize.getSelectedItem().toString();

                        int cur=Integer.valueOf(TVcount.getText().toString());
                        int main=cur+count;
                        TVcount.setText(String.valueOf(main));
                        TVsize.setText(TVsize.getText().toString()+" "+size+"("+count+")");


                    }
                });

                dialogView.findViewById(R.id.btn_order).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {


                        int total=Integer.valueOf(TVcount.getText().toString());
                        int price=Integer.valueOf(buyer);
                        String totalvalue=String.valueOf(total*price);

                        if ((Integer.valueOf(TVcount.getText().toString())== 0)) {

                            Toast.makeText(context, "تعداد سفارش نمی تواند صفر باشد", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        order.setCode(code);
                        order.setRow(row);
                        order.setName(TVname.getText().toString());
                        order.setCount(TVcount.getText().toString());
                        order.setSize(TVsize.getText().toString());
                        order.setPrice(buyer);
                        order.setTotal(totalvalue);

                        databaseHelper.addWithpriceOrder(order);

                        alertDialog.dismiss();

                        Toast.makeText(context,"سفارش ثبت شد", Toast.LENGTH_SHORT).show();

                        DecimalFormat formatter = new DecimalFormat("#,###,###");
                        OrderSqliteHelper dbh1 = new OrderSqliteHelper(context);
                        int totals=dbh1.getsumkafshOrder()+dbh1.getsumWithpriceOrder()+dbh1.getsumNOpriceOrder();
                        final String totalprice= formatter.format(totals);
                        DarmaniNoPrice.factor.setText("مشاهده فاکتور ("+totalprice+" ریال )");
                        DarmaniWithPrice.factor.setText("مشاهده فاکتور ("+totalprice+" ریال )");

                    }
                });

                dialogView.findViewById(R.id.btn_remove).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        TVsize.setText("");
                        TVcount.setText("0");

                    }
                });


            }


        });

    }

    @Override
    public int getItemCount() {
        Log.v(DarmaniWithPriceAdapter.class.getSimpleName(),""+listPRO.size());
        return listPRO.size();
    }

    /**
     * ViewHolder class
     */
    public class UserViewHolder extends RecyclerView.ViewHolder {

        public TextView TVname,TVcode,TVrow;
        public ImageView photo;
        public CardView item;

        public UserViewHolder(View view) {
            super(view);
            TVname = (TextView) view.findViewById(R.id.darmani_name);
            TVcode = (TextView) view.findViewById(R.id.darmani_code);
            TVrow = (TextView) view.findViewById(R.id.darmani_row);

            photo = (ImageView) view.findViewById(R.id.darmani_photo);
            item = (CardView) view.findViewById(R.id.darmani_item);

        }
    }


    public void filterList(List<DarmaniModel> filterdNames) {
        this.listPRO = filterdNames;
        notifyDataSetChanged();
    }
}