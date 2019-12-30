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


import com.example.onlineshopping_ecommerce.ProductDescriptionActivity;
import com.example.onlineshopping_ecommerce.R;

import com.example.onlineshopping_ecommerce.model.Product;
import com.example.onlineshopping_ecommerce.url.Url;

import java.io.InputStream;
import java.net.URL;
import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {
    private List<Product> productList;
    private Context context;

    public ProductAdapter(List<Product> productList, Context context){
        this.productList = productList;
        this.context=context;
    }

    @NonNull
    @Override
    public ProductAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(
                R.layout.activity_product_adapter,viewGroup,false);
        return new ProductAdapter.ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ProductAdapter.ViewHolder viewHolder, int i) {
        final Product prdt = productList.get(i);
        String imgPath = Url.BASE_URL+"uploads/product/"+ prdt.getImage();

        StrictMode();

        try{
            URL url = new URL(imgPath);
            viewHolder.imgPhoto.setImageBitmap(BitmapFactory.decodeStream((InputStream) url.getContent()));
        } catch (Exception e) {
            e.printStackTrace();
        }

        viewHolder.tvName.setText(prdt.getName());
        viewHolder.tvPrice.setText("रू " + prdt.getPrice());
        viewHolder.tvId.setText(prdt.getId());

        viewHolder.imgPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ProductDescriptionActivity.class);
                intent.putExtra("id",prdt.getId());

                intent.putExtra("name",prdt.getName());
                intent.putExtra("code",prdt.getCode());
                intent.putExtra("price",prdt.getPrice());
                intent.putExtra("cat",prdt.getCategoryId());
                intent.putExtra("desc",prdt.getDescription());
                if(Integer.parseInt(prdt.getOQuantity())==0){
                    intent.putExtra("qty",prdt.getQuantity());

                }else{
                    int Final=Integer.parseInt(prdt.getQuantity())-Integer.parseInt(prdt.getOQuantity());
                    String val=Integer.toString(Final);
                    intent.putExtra("qty",val);

                }

                intent.putExtra("color",prdt.getColor());
                intent.putExtra("size",prdt.getSize());
                intent.putExtra("image",prdt.getImage());

                context.startActivity(intent);





            }
        });
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgPhoto;
        TextView tvName, tvId,tvPrice;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgPhoto=itemView.findViewById(R.id.imgPhoto_p);
            tvName=itemView.findViewById(R.id.tvName_p);
            tvPrice=itemView.findViewById(R.id.tvPrice_p);
            tvId=itemView.findViewById(R.id.tvID_p);

        }
    }

    private void StrictMode(){
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
    }
}
