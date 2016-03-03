package com.clicklabs.shopkeeperassignment.adapters;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.clicklabs.shopkeeperassignment.R;
import com.clicklabs.shopkeeperassignment.activity.DisplayDetailsCustomer;
import com.clicklabs.shopkeeperassignment.models.customersdata.Customer;

import java.util.List;


public class CustomersDisplayRecyclerAdapter extends RecyclerView.Adapter<CustomersDisplayRecyclerAdapter.myViewHolder> implements View.OnClickListener {

    private final Context context;

    private final LayoutInflater inflater;
    private List<Customer> data ;

    public CustomersDisplayRecyclerAdapter(Activity context, List<Customer> data) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.data = data;

    }

    @Override
    public myViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.element_recycler_view_customers_display, parent, false);

        return new myViewHolder(view);
    }

    @Override
    public void onBindViewHolder(myViewHolder holder, int position) {
        holder.Name.setText(data.get(position).getName());
        holder.phone.setText(String.valueOf(data.get(position).getPhoneNo()));
        //holder.picture.setImageResource((Integer) data.get(position).getProfilePicURL().getOriginal());
        holder.pos = position;
        holder.itemView.setTag(holder);
        holder.itemView.setOnClickListener(this);

    }

    public void resetData(List<Customer> newData) {

        this.data = newData;
        notifyDataSetChanged();

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public void onClick(View v) {

        myViewHolder holder = (myViewHolder) v.getTag();
        Intent intent = new Intent(context, DisplayDetailsCustomer.class);
        intent.putExtra("onClickDataDisplay", data.get(holder.pos));
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        ((Activity) context).startActivityForResult(intent, 2);
    }


    public class myViewHolder extends RecyclerView.ViewHolder {
        final TextView Name;
        final ImageView picture;
        final TextView phone;
        int pos;

        public myViewHolder(View itemView) {
            super(itemView);
            Name = (TextView) itemView.findViewById(R.id.tv_label_display_customer_name);
            picture = (ImageView) itemView.findViewById(R.id.iv_picture_display_customer);
            phone = (TextView) itemView.findViewById(R.id.tv_label_display_customer_phone);

        }


    }

}
