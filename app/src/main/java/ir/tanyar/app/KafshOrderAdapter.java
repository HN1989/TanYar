package ir.tanyar.app;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.util.List;

public class KafshOrderAdapter extends RecyclerView.Adapter<KafshOrderAdapter.UserViewHolder> {

    private List<KafshOrderModel> listorder;
    private Context context;
    private LayoutInflater inflater;
    int sum;

    public KafshOrderAdapter(Context context, List<KafshOrderModel> listorder) {

        this.context = context;
        this.listorder = listorder;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public UserViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // inflating recycler item view
        View rootView = inflater.inflate(R.layout.kafsh_order_item, parent, false);

        return new UserViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(final UserViewHolder holder, int position) {
        //holder.id.setText(String.valueOf(listPRO.get(position).getId()));

        final String radif=String.valueOf(listorder.get(position).getId());
        final String row=listorder.get(position).getRow();
        final String code=listorder.get(position).getCode();
        final String name=listorder.get(position).getName();
        final String sell=listorder.get(position).getSell();
        final String count=listorder.get(position).getCount();
        final String size=listorder.get(position).getSize();
        final String price=listorder.get(position).getPrice();
        final String total=listorder.get(position).getTotal();

        String pos=String.valueOf(position+1);

        final DecimalFormat formatter = new DecimalFormat("#,###,###");
        final int pricevalue=Integer.valueOf(price);
        final int totalvalue=Integer.valueOf(total);
        final String pricekafsh= formatter.format(pricevalue);
        final String totalkafsh= formatter.format(totalvalue);

        holder.TVradif.setText(pos);
        holder.TVrow.setText(row);
        holder.TVcode.setText(code);
        holder.TVname.setText(name);
        holder.TVsell.setText(sell);
        holder.TVcount.setText(count);
        holder.TVsize.setText(size);
        holder.TVprice.setText(pricekafsh);
        holder.TVtotla.setText(totalkafsh);

        holder.item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
                LayoutInflater inflater =LayoutInflater.from(context);
                View dialogView = inflater.inflate(R.layout.delete_order_dialog, null);
                dialogBuilder.setView(dialogView);
                final AlertDialog alertDialog = dialogBuilder.create();
                alertDialog.show();

                TextView TVname = (TextView) dialogView.findViewById(R.id.tx_delete);
                TVname.setText(name+" حذف شود؟ ");

                dialogView.findViewById(R.id.btn_yes).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        OrderSqliteHelper databaseHelper = new OrderSqliteHelper(context);
                        databaseHelper.deletekafshorder(Integer.valueOf(radif));
                        alertDialog.dismiss();

                        Intent ordering = new Intent(context, FinalOrder.class);
                        ((Activity)context).finish();
                        context.startActivity(ordering);

                        Toast.makeText(context, "حذف شد", Toast.LENGTH_SHORT).show();

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

    }

    @Override
    public int getItemCount() {
        Log.v(KafshOrderAdapter.class.getSimpleName(),""+listorder.size());
        return listorder.size();
    }

    /**
     * ViewHolder class
     */
    public class UserViewHolder extends RecyclerView.ViewHolder {

        public TextView TVradif,TVrow,TVcode,TVname,TVsell,TVcount,TVsize,TVprice,TVtotla;

        public LinearLayout item;

        public UserViewHolder(View view) {
            super(view);
            TVradif = (TextView) view.findViewById(R.id.kafsh_radif);
            TVrow = (TextView) view.findViewById(R.id.kafsh_row);
            TVcode = (TextView) view.findViewById(R.id.kafsh_code);
            TVname = (TextView) view.findViewById(R.id.kafsh_name);
            TVsell = (TextView) view.findViewById(R.id.kafsh_sell);
            TVcount = (TextView) view.findViewById(R.id.kafsh_count);
            TVsize = (TextView) view.findViewById(R.id.kafsh_size);
            TVprice = (TextView) view.findViewById(R.id.kafsh_price);
            TVtotla = (TextView) view.findViewById(R.id.kafsh_total);

            item = (LinearLayout) view.findViewById(R.id.kafsh_item);

        }
    }


}