package ir.tanyar.app;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class CustomerAdapter extends RecyclerView.Adapter<CustomerAdapter.UserViewHolder> {

    private List<CustomerModel> listcustomer;
    private Context context;
    private LayoutInflater inflater;

    private OrderSqliteHelper db;

    public CustomerAdapter(Context context, List<CustomerModel> listcustomer) {

        this.context = context;
        this.listcustomer = listcustomer;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public UserViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // inflating recycler item view
        View rootView = inflater.inflate(R.layout.customer_item, parent, false);

        return new UserViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(final UserViewHolder holder, int position) {
        //holder.id.setText(String.valueOf(listPRO.get(position).getId()));

        final String id=String.valueOf(listcustomer.get(position).getId());
        final String type=listcustomer.get(position).getType();
        final String name=listcustomer.get(position).getName();
        final String manager=listcustomer.get(position).getManager();
        final String state=listcustomer.get(position).getState();
        final String city=listcustomer.get(position).getCity();
        final String area=listcustomer.get(position).getArea();
        final String adres=listcustomer.get(position).getAdres();
        final String zipcode=listcustomer.get(position).getZipcode();
        final String phone=listcustomer.get(position).getPhone();
        final String mobile=listcustomer.get(position).getMobile();

        holder.TVname.setText(type+" "+name);
        holder.TVmanager.setText("مسئول: "+manager);
        holder.TVadres.setText(state+" ، "+city+" ، "+area+" ، "+adres);

        holder.item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                        Intent ordering = new Intent(context, CustomerInfo.class);
                        ordering.putExtra("ID", id);
                        ordering.putExtra("TYPE", type);
                        ordering.putExtra("NAME", name);
                        ordering.putExtra("MANAGER", manager);
                        ordering.putExtra("STATE", state);
                        ordering.putExtra("CITY", city);
                        ordering.putExtra("AREA", area);
                        ordering.putExtra("ADRES", adres);
                        ordering.putExtra("ZIPCODE", zipcode);
                        ordering.putExtra("PHONE", phone);
                        ordering.putExtra("MOBILE", mobile);
                        context.startActivity(ordering);

            }

        });

        holder.remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
                LayoutInflater inflater =LayoutInflater.from(context);
                View dialogView = inflater.inflate(R.layout.customer_remove_dialog, null);
                dialogBuilder.setView(dialogView);
                final AlertDialog alertDialog = dialogBuilder.create();
                alertDialog.show();

                dialogView.findViewById(R.id.btn_yes).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        CustomerSqliteHelper databaseHelper = new CustomerSqliteHelper(context);
                        databaseHelper.deleteCustomer(Integer.valueOf(id));
                        alertDialog.dismiss();

                        Intent ordering = new Intent(context, Customers.class);
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
        Log.v(CustomerAdapter.class.getSimpleName(),""+listcustomer.size());
        return listcustomer.size();
    }

    /**
     * ViewHolder class
     */
    public class UserViewHolder extends RecyclerView.ViewHolder {

        public TextView TVname,TVmanager,TVadres;
        public Button remove;
        public CardView item;

        public UserViewHolder(View view) {
            super(view);
            TVname = (TextView) view.findViewById(R.id.customer_name);
            TVmanager = (TextView) view.findViewById(R.id.customer_manager);
            TVadres = (TextView) view.findViewById(R.id.customer_adres);

            remove = (Button) view.findViewById(R.id.btn_remove);
            item = (CardView) view.findViewById(R.id.customer_item);

        }
    }



    public void filterList(List<CustomerModel> filterdNames) {
        this.listcustomer = filterdNames;
        notifyDataSetChanged();
    }


}