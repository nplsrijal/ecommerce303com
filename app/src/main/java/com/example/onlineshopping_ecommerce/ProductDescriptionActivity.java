package com.example.onlineshopping_ecommerce;

import android.app.Notification;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.StrictMode;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.onlineshopping_ecommerce.api.APIServer;
import com.example.onlineshopping_ecommerce.channel.Channel;
import com.example.onlineshopping_ecommerce.url.Url;

import java.io.InputStream;
import java.net.URL;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductDescriptionActivity extends AppCompatActivity {
    ImageView imgview;
    TextView txtid,txtname,txtprice,txtdesc,txtcat,txtcode,txtqty,txtcolor,txtsize;

    public String Productid,Price,orgqty;
    NotificationManagerCompat notificationManagerCompact;


    @Override
    public void onBackPressed() {
        Intent intent = new Intent(ProductDescriptionActivity.this, CategoryActivity.class);
        startActivity(intent);
        finish();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_description);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                alertCustomizedLayout();

//                    Snackbar.make(view, "Added to Cart", Snackbar.LENGTH_LONG)
//                            .setAction("Action", null).show();

                }




        });
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle("Product Description");
        mToolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);

        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProductDescriptionActivity.this, CategoryActivity.class);
                startActivity(intent);
                finish();
            }
        });

        notificationManagerCompact=NotificationManagerCompat.from(this);
        Channel channel = new Channel(this);
        channel.createChannel();


        imgview=findViewById(R.id.imgPhoto_pd);
        txtid=findViewById(R.id.tvID_pd);

        txtname=findViewById(R.id.tvName_pd);
        txtprice=findViewById(R.id.tvPrice_pd);
        txtdesc=findViewById(R.id.tvDesc_pd);
        txtcat=findViewById(R.id.tvCat_pd);
        txtcode=findViewById(R.id.tvCode_pd);
        txtqty=findViewById(R.id.tvQty_pd);
        txtcolor=findViewById(R.id.tvColor_pd);
        txtsize=findViewById(R.id.tvSize_pd);

        Bundle bundle =getIntent().getExtras();
        if(bundle !=null){
            mToolbar.setTitle(bundle.getString("name"));

            Productid=bundle.getString("id");
            Price=bundle.getString("price");
            orgqty=bundle.getString("qty");


            txtid.setText(bundle.getString("id"));

            txtname.setText("Name : " + bundle.getString("name"));
            txtcode.setText("Code : " + bundle.getString("code"));
            txtcat.setText("Category : " + bundle.getString("cat"));
            txtprice.setText("Price :रू "+bundle.getString("price"));
            txtdesc.setText("Description : " +bundle.getString("desc"));
            txtqty.setText("Quantity : " +bundle.getString("qty"));
            txtcolor.setText("Color : " +bundle.getString("color"));
            txtsize.setText("Size : " +bundle.getString("size"));

            String imgPath = Url.BASE_URL+"uploads/product/"+ bundle.getString("image");

            StrictMode();

            try{
                URL url = new URL(imgPath);
                imgview.setImageBitmap(BitmapFactory.decodeStream((InputStream) url.getContent()));
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        if (android.os.Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.colorPrimaryDark));
        }
    }

    private void StrictMode(){
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
    }

    public void alertCustomizedLayout(){

        AlertDialog.Builder alert = new AlertDialog.Builder(ProductDescriptionActivity.this);
        alert.setTitle("Add to Cart");

        LinearLayout layout = new LinearLayout(ProductDescriptionActivity.this);
        layout.setOrientation(LinearLayout.VERTICAL);

        final EditText Qty = new EditText(ProductDescriptionActivity.this);
        Qty.setHint("Quantity");
        Qty.requestFocus();
        Qty.setInputType(InputType.TYPE_CLASS_NUMBER);
        layout.addView(Qty);





        alert.setView(layout);

        alert.setPositiveButton("Submit", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {

                if(Qty.getText().toString().length()==0 ){
                    Toast.makeText(ProductDescriptionActivity.this,"All Fields Are Required",Toast.LENGTH_LONG).show();

                    return ;

                }else if(Integer.parseInt(Qty.getText().toString())>Integer.parseInt(orgqty)){
                    Toast.makeText(ProductDescriptionActivity.this,"Quantity Exceeded",Toast.LENGTH_SHORT).show();
                      return;


                }
                else{
                    String Userid = Url.userid;
                    String Quantity=Qty.getText().toString().trim();
                    int Ischeckout=0;
                    APIServer server = Url.getInstance().create(APIServer.class);
                    Call<Void> usersCall = server.addtoCart(Url.token,Userid,Productid,Quantity,Price,Ischeckout);
                    usersCall.enqueue(new Callback<Void>() {
                        @Override
                        public void onResponse(Call<Void> call, Response<Void> response) {
                            if (!response.isSuccessful()) {
                                Snackbar
                                        .make(imgview, "Something Went Wrong", Snackbar.LENGTH_LONG)
                                        .show();                                return;
                            }
                            Snackbar
                                    .make(imgview, "Added to Cart", Snackbar.LENGTH_LONG)
                                    .show();   displaySuccessNotificaiton();                     }

                        @Override
                        public void onFailure(Call<Void> call, Throwable t) {
                            Toast.makeText(ProductDescriptionActivity.this, "Error " + t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });



                }




            }
        });

        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                // what ever you want to do with No option.
            }
        });

        alert.show();

    }

    private void displaySuccessNotificaiton() {
        Notification notification= new NotificationCompat.Builder(ProductDescriptionActivity.this, Channel.CHANNEL_1)

                .setSmallIcon(R.drawable.ic_add_shopping_cart_black_24dp)
                .setContentTitle("Success")
                .setContentText("Added to Cart")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .build();

        notificationManagerCompact.notify(1,notification);
    }
}
