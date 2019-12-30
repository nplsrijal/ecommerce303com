package com.example.onlineshopping_ecommerce.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.onlineshopping_ecommerce.R;
import com.example.onlineshopping_ecommerce.model.Product;
import com.example.onlineshopping_ecommerce.url.Url;

import java.io.InputStream;
import java.net.URL;
import java.util.List;

public class OrderProductAdapter extends RecyclerView.Adapter<OrderProductAdapter.ViewHolder> {
    private List<Product> productList;
    private Context context;

    public OrderProductAdapter(List<Product> productList, Context context){
        this.productList = productList;
        this.context=context;
    }

    @NonNull
    @Override
    public OrderProductAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(
                R.layout.activity_orderproduct_adapter,viewGroup,false);
        return new OrderProductAdapter.ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull OrderProductAdapter.ViewHolder viewHolder, int i) {
        final Product prdt = productList.get(i);
        String imgPath = Url.BASE_URL+"uploads/product/"+ prdt.getImage();

        StrictMode();

        try{
            URL url = new URL(imgPath);
            viewHolder.imgPhoto.setImageBitmap(BitmapFactory.decodeStream((InputStream) url.getContent()));
        } catch (Exception e) {
            e.printStackTrace();
        }

        viewHolder.tname.setText(prdt.getName());
        viewHolder.tqty.setText("Qty:"+ prdt.getQuantity());
        viewHolder.tprice.setText("रू " + prdt.getPrice());

    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgPhoto;
        TextView tname, tqty,tprice;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgPhoto=itemView.findViewById(R.id.img);
            tname=itemView.findViewById(R.id.tname);
            tqty=itemView.findViewById(R.id.tqty);
            tprice=itemView.findViewById(R.id.tprice);

        }
    }

    private void StrictMode(){
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
    }
}
