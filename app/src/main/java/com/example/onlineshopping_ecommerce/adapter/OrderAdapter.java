package com.example.onlineshopping_ecommerce.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.onlineshopping_ecommerce.OrderProductActivity;
import com.example.onlineshopping_ecommerce.R;

import com.example.onlineshopping_ecommerce.model.Order;


import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder> {

    private List<Order> catList;
    private Context context;

    public OrderAdapter(List<Order> catList, Context context){
        this.catList = catList;
        this.context=context;
    }

    @NonNull
    @Override
    public OrderAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(
                R.layout.activity_order_adapter,viewGroup,false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull final OrderAdapter.ViewHolder viewHolder, int i) {
        final Order cat = catList.get(i);


        viewHolder.tprice.setText(cat.getTotalprice());


        if(cat.getOrderdate().equals("")){
            viewHolder.todate.setText("Not provided");
        }else{
            viewHolder.todate.setText(cat.getOrderdate());

        }
        if(cat.getDeliverdate().equals("")){
            viewHolder.tddate.setText("Not provided");
        }else{
            viewHolder.tddate.setText(cat.getDeliverdate());

        }


        if(Integer.parseInt(cat.getDeliverstatus())==0){
            viewHolder.tstatus.setText("Order Received");
            viewHolder.tstatus.setTextColor(Color.parseColor("#FF0000"));

        }else{
            viewHolder.tstatus.setText("Delivered");
            viewHolder.tstatus.setTextColor(Color.parseColor("#008000"));

        }
        viewHolder.tstatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, OrderProductActivity.class);
                intent.putExtra("id",cat.getId());

                context.startActivity(intent);


            }
        });




    }

    @Override
    public int getItemCount() {
        return catList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tprice,todate,tddate,tstatus;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tprice=itemView.findViewById(R.id.tprice);
            todate=itemView.findViewById(R.id.todate);
            tddate=itemView.findViewById(R.id.tddate);
            tstatus=itemView.findViewById(R.id.tstatus);

        }
    }


}
