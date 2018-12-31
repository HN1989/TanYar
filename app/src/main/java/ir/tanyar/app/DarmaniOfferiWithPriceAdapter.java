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

public class DarmaniOfferiWithPriceAdapter extends RecyclerView.Adapter<DarmaniOfferiWithPriceAdapter.UserViewHolder> {

    private List<DarmaniModel> listPRO;
    private Context context;
    private LayoutInflater inflater;

    Spinner SPsize,SPcount,SPsize2,SPcount2;
    List<String> stringArraySize ;
    List<String> stringArraySize2 ;
    ArrayList<String> stringArrayCount ;
    ArrayList<String> stringArrayCount2 ;

    private OrderSqliteHelper databaseHelper, databaseHelper2;
    private DarmaniOrderModel order,order2;

    public DarmaniOfferiWithPriceAdapter(Context context, List<DarmaniModel> listPRO) {

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
                final View dialogView = inflater.inflate(R.layout.darmani_offeri_dialog, null);
                dialogBuilder.setView(dialogView);
                final AlertDialog alertDialog = dialogBuilder.create();
                alertDialog.show();

                alertDialog.setCanceledOnTouchOutside(false);

                final ImageView IMphoto;
                final TextView TVname,TVbuyer,TVconsumer,TVproperty,TVcode,TVrow,TVmainsize,TVmaincount,TVoffersize,TVoffercount;
                final TextView TVbrand1,TVbrand2,TVbrand3,TVbrand4;

                IMphoto = (ImageView) dialogView.findViewById(R.id.darmani_photo);
                TVname = (TextView) dialogView.findViewById(R.id.darmani_name);
                TVbuyer = (TextView) dialogView.findViewById(R.id.darmani_buyer);
                TVconsumer = (TextView) dialogView.findViewById(R.id.darmani_consumer);
                TVproperty = (TextView) dialogView.findViewById(R.id.darmani_property);
                TVcode = (TextView) dialogView.findViewById(R.id.darmani_code);
                TVrow = (TextView) dialogView.findViewById(R.id.darmani_row);
                TVmainsize = (TextView) dialogView.findViewById(R.id.tx_size_main);
                TVmaincount = (TextView) dialogView.findViewById(R.id.tx_count_main);
                TVoffersize = (TextView) dialogView.findViewById(R.id.tx_size_offer);
                TVoffercount = (TextView) dialogView.findViewById(R.id.tx_count_offer);

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

                //size2
                SPsize2=(Spinner) dialogView.findViewById(R.id.sp_size2);
                stringArraySize2 = new ArrayList<String>(Arrays.asList(items));
                ArrayAdapter<String> adaptersize2 = new ArrayAdapter<String>(context, R.layout.spineritem, stringArraySize2);
                adaptersize2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                SPsize2.setAdapter(adaptersize2);

                //count
                SPcount=(Spinner) dialogView.findViewById(R.id.sp_count);
                stringArrayCount = new ArrayList<String>();

                for (int i = 1; i <= 300; i++) {
                    stringArrayCount.add(String.valueOf(i));
                }

                ArrayAdapter<String> adaptercount = new ArrayAdapter<String>(context, R.layout.spineritem, stringArrayCount);
                adaptercount.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                SPcount.setAdapter(adaptercount);

                //count2
                SPcount2=(Spinner) dialogView.findViewById(R.id.sp_count2);
                stringArrayCount2 = new ArrayList<String>();

                for (int i = 1; i <= 300; i++) {
                    stringArrayCount2.add(String.valueOf(i));
                }

                ArrayAdapter<String> adaptercount2 = new ArrayAdapter<String>(context, R.layout.spineritem, stringArrayCount2);
                adaptercount2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                SPcount2.setAdapter(adaptercount2);


                //add order
                databaseHelper = new OrderSqliteHelper(context);
                order = new DarmaniOrderModel();

                //add offer
                databaseHelper2 = new OrderSqliteHelper(context);
                order2 = new DarmaniOrderModel();

                final int asli=DarmaniOfferiWithPrice.asli;
                final int prize=DarmaniOfferiWithPrice.prize;
                final float Fasli=Float.valueOf(asli);
                final float Fprize=Float.valueOf(prize);
                final float factor=(Fprize/Fasli);
                //Toast.makeText(context,factor+"", Toast.LENGTH_SHORT).show();

                dialogView.findViewById(R.id.btn_add_main).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        int count=Integer.valueOf(SPcount.getSelectedItem().toString());
                        String size=SPsize.getSelectedItem().toString();

                        int cur=Integer.valueOf(TVmaincount.getText().toString());
                        int main=cur+count;
                        TVmaincount.setText(String.valueOf(main));
                        TVmainsize.setText(TVmainsize.getText().toString()+" "+size+"("+count+")");


                    }
                });


                dialogView.findViewById(R.id.btn_add_offer).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        int count2=Integer.valueOf(SPcount2.getSelectedItem().toString());
                        String size2=SPsize2.getSelectedItem().toString();

                        int cur2=Integer.valueOf(TVoffercount.getText().toString());
                        int main2=cur2+count2;
                        TVoffercount.setText(String.valueOf(main2));
                        TVoffersize.setText(TVoffersize.getText().toString()+" "+size2+"("+count2+")");


                    }
                });


                dialogView.findViewById(R.id.btn_order_main).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        int total=Integer.valueOf(TVmaincount.getText().toString());
                        int price=Integer.valueOf(buyer);
                        String totalvalue=String.valueOf(total*price);


                        int total2=Integer.valueOf(TVoffercount.getText().toString());
                        String totalvalue2=String.valueOf(total2*price);

                        int maincount=Integer.valueOf(TVmaincount.getText().toString());
                        int offercount=Integer.valueOf(TVoffercount.getText().toString());

                        int prizecount=Math.round(factor*maincount);


                        if (maincount== 0) {

                            Toast.makeText(context, "تعداد سفارش اصلی باید مضربی از "+asli+" باشد ", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        if (!((maincount % asli) == 0)) {

                            Toast.makeText(context, "تعداد سفارش اصلی باید مضربی از "+asli+" باشد ", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        if (offercount== 0 || offercount!=prizecount) {

                            Toast.makeText(context, "تعداد سفارش آفر باید "+(prizecount)+" عدد باشد ", Toast.LENGTH_SHORT).show();
                            return;
                        }



                        order.setCode(code);
                        order.setRow(row);
                        order.setName(TVname.getText().toString());
                        order.setCount(TVmaincount.getText().toString());
                        order.setSize(TVmainsize.getText().toString());
                        order.setPrice(buyer);
                        order.setTotal(totalvalue);

                        order2.setCode(code);
                        order2.setRow(row);
                        order2.setName(TVname.getText().toString());
                        order2.setCount(TVoffercount.getText().toString());
                        order2.setSize(TVoffersize.getText().toString());
                        order2.setPrice(buyer);
                        order2.setTotal(totalvalue2);

                        databaseHelper.addWithpriceOrder(order);
                        databaseHelper2.addOfferOrder(order2);

                        alertDialog.dismiss();

                        Toast.makeText(context,"سفارش ثبت شد", Toast.LENGTH_SHORT).show();

                        DecimalFormat formatter = new DecimalFormat("#,###,###");
                        OrderSqliteHelper dbh1 = new OrderSqliteHelper(context);
                        int totals=dbh1.getsumkafshOrder()+dbh1.getsumWithpriceOrder()+dbh1.getsumNOpriceOrder();
                        final String totalprice= formatter.format(totals);
                        DarmaniNoPrice.factor.setText("مشاهده فاکتور ("+totalprice+" ریال )");
                        DarmaniOfferiWithPrice.factor.setText("مشاهده فاکتور ("+totalprice+" ریال )");


                    }
                });


                dialogView.findViewById(R.id.btn_remove_main).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        TVmainsize.setText("");
                        TVmaincount.setText("0");

                    }
                });

                dialogView.findViewById(R.id.btn_remove_offer).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        TVoffersize.setText("");
                        TVoffercount.setText("0");

                    }
                });


            }


        });

    }

    @Override
    public int getItemCount() {
        Log.v(DarmaniOfferiWithPriceAdapter.class.getSimpleName(),""+listPRO.size());
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