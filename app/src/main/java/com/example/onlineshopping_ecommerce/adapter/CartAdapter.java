package com.example.onlineshopping_ecommerce.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.onlineshopping_ecommerce.CartActivity;
import com.example.onlineshopping_ecommerce.DashboardActivity;
import com.example.onlineshopping_ecommerce.ProductActivity;
import com.example.onlineshopping_ecommerce.ProductDescriptionActivity;
import com.example.onlineshopping_ecommerce.R;
import com.example.onlineshopping_ecommerce.api.APIServer;
import com.example.onlineshopping_ecommerce.model.Cart;
import com.example.onlineshopping_ecommerce.model.Category;
import com.example.onlineshopping_ecommerce.url.Url;

import java.io.InputStream;
import java.net.URL;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {

    private List<Cart> catList;
    private Context context;

    public CartAdapter(List<Cart> catList, Context context){
        this.catList = catList;
        this.context=context;
    }

    @NonNull
    @Override
    public CartAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(
                R.layout.cart_list,viewGroup,false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int i) {
        final Cart cat = catList.get(i);


        viewHolder.txtName.setText(cat.getProductId());
        viewHolder.txtPPrice.setText("रू "+cat.getPrice());
        viewHolder.txtQty.setText(cat.getQuantity());
        viewHolder.txtPrice.setText(cat.getPrice());

        viewHolder.ImgRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Confirmation!");
                builder.setMessage("You are about to remove this from Cart. Do you really want to proceed ?");
                builder.setCancelable(false);
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        APIServer server = Url.getInstance().create(APIServer.class);
                        String id=cat.getId();
                        Call<Void> usersCall = server.deleteEmployee(Url.token,id);
                        usersCall.enqueue(new Callback<Void>() {
                            @Override
                            public void onResponse(Call<Void> call, Response<Void> response) {
                                if (!response.isSuccessful()) {
                                    Toast.makeText(context, "code " + response.code(), Toast.LENGTH_SHORT).show();
                                    return;
                                }
                                Toast.makeText(context, "Removed", Toast.LENGTH_SHORT).show();

                                Intent intent = new Intent(context, CartActivity.class);

                                context.startActivity(intent);
                            }

                            @Override
                            public void onFailure(Call<Void> call, Throwable t) {
                                Toast.makeText(context, "Error " + t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });                    }
                });

                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(context, "You've changed your mind to delete all records", Toast.LENGTH_SHORT).show();
                    }
                });

                builder.show();





            }
        });



    }

    @Override
    public int getItemCount() {
        return catList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
          ImageView ImgRemove;
        TextView txtPrice,txtName,txtQty,txtPPrice;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtPrice=itemView.findViewById(R.id.txtPrice);
            txtName=itemView.findViewById(R.id.txtName);
            txtPPrice=itemView.findViewById(R.id.txtPPrice);
            txtQty=itemView.findViewById(R.id.txtQty);
            ImgRemove=itemView.findViewById(R.id.ImgRemove);

        }
    }


}
