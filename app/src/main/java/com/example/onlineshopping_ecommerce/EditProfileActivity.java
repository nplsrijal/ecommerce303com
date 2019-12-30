package com.example.onlineshopping_ecommerce;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.content.CursorLoader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.onlineshopping_ecommerce.adapter.CategoryAdapter;
import com.example.onlineshopping_ecommerce.api.APIServer;
import com.example.onlineshopping_ecommerce.model.Category;
import com.example.onlineshopping_ecommerce.model.ImageResponse;
import com.example.onlineshopping_ecommerce.model.Users;
import com.example.onlineshopping_ecommerce.url.Url;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditProfileActivity extends AppCompatActivity {
    private EditText txtname,txtemail,txtphone,txtlocation;
    private CircleImageView img;
   private Button btnsave;
    String imagePath;
    String imageName;
   boolean clickedimage;

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(EditProfileActivity.this, ProfileActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode==RESULT_OK){
            if (data==null){
                Toast.makeText(this, "Please select an image", Toast.LENGTH_SHORT).show();
            }
        }
        Uri uri=data.getData();
        imagePath=getRealPathFromUri(uri);
        previewImage(imagePath);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        txtname=findViewById(R.id.user_profile_name);
        txtemail=findViewById(R.id.user_profile_email);
        txtphone=findViewById(R.id.user_profile_phone);
        txtlocation=findViewById(R.id.user_profile_location);
        img=findViewById(R.id.user_profile_photo);
        btnsave=findViewById(R.id.btnsave);

        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle("Edit Profile");
        mToolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EditProfileActivity.this, ProfileActivity.class);
                startActivity(intent);
                finish();
            }
        });
        btnsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                   if(validate()){
                       submit();
                   }

            }
        });
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BrowseImage();
                clickedimage=true;


            }
        });

        String id =Url.userid;
        APIServer api = Url.getInstance().create(APIServer.class);
        Call<Users> call = api.getUserById(Url.token,id);
        call.enqueue(new Callback<Users>() {
            @Override
            public void onResponse(Call<Users> call, Response<Users> response) {

                if(!response.isSuccessful())
                {
                    Toast.makeText(EditProfileActivity.this, "Error" + response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }
                if(response.body()==null)
                {
                    Toast.makeText(EditProfileActivity.this, "No data", Toast.LENGTH_SHORT).show();
                    return;
                }
                String imgPath = Url.BASE_URL+"uploads/user/"+ response.body().getImage();
                StrictMode();

                try{
                    URL url = new URL(imgPath);
                    img.setImageBitmap(BitmapFactory.decodeStream((InputStream) url.getContent()));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                txtname.setText(response.body().getFullname());
                txtemail.setText(response.body().getEmailId());
                txtphone.setText(response.body().getPhone());
                txtlocation.setText(response.body().getLocation());


            }

            @Override
            public void onFailure(Call<Users> call, Throwable t) {
                Toast.makeText(EditProfileActivity.this,"Error "+t.getLocalizedMessage(),Toast.LENGTH_SHORT).show();


            }
        });

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
    private boolean validate(){




        boolean checkvalidate=true;
        // Get all edittext texts
        String FullName = txtname.getText().toString();
        String EmailId = txtemail.getText().toString();
        String Phone = txtphone.getText().toString();
        String Location = txtlocation.getText().toString();

        // Pattern match for email id
        Pattern p = Pattern.compile(Utils.regEx);
        Matcher m = p.matcher(EmailId);

        // Check if all strings are null or not


        if (FullName.equals("") || FullName.length() == 0
                || EmailId.equals("") || EmailId.length() == 0
                || Phone.equals("") || Phone.length() == 0
                || Location.equals("") || Location.length() == 0
              ){
            checkvalidate=false;
            Toast.makeText(EditProfileActivity.this, "All Fields are Required", Toast.LENGTH_SHORT).show();
        }else  if (!m.find()){
            checkvalidate=false;
            Toast.makeText(EditProfileActivity.this, "Email Pattern Invalid", Toast.LENGTH_SHORT).show();
        }






        return  checkvalidate;

    }


    private void submit(){
        if(clickedimage==true){
            SaveImageOnly();

        }
        Toast.makeText(EditProfileActivity.this, "Profile"+clickedimage, Toast.LENGTH_SHORT).show();

        APIServer server = Url.getInstance().create(APIServer.class);
        String FullName = txtname.getText().toString();
        String EmailId = txtemail.getText().toString();
        String Phone = txtphone.getText().toString();
        String Location = txtlocation.getText().toString();
        String id=Url.userid;


        Call<Void> usersCall = server.updateProfile(Url.token,id,FullName,EmailId,Phone,Location,imageName);
        usersCall.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(EditProfileActivity.this, "code " + response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }
                Toast.makeText(EditProfileActivity.this, "Profile Updated Successfully", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(EditProfileActivity.this, "Error " + t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void BrowseImage() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent,0);
    }
    private String getRealPathFromUri(Uri uri) {
        String[] projection ={MediaStore.Images.Media.DATA};
        CursorLoader loader = new CursorLoader(getApplicationContext(), uri, projection,
                null, null, null);
        Cursor cursor = loader.loadInBackground();
        int colIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String result = cursor.getString(colIndex);
        cursor.close();
        return result;
    }
    private void previewImage(String imagePath) {

        File imgFile = new File(imagePath);
        if (imgFile.exists()){
            Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());



            img.setImageBitmap(myBitmap);
        }
    }

    private void SaveImageOnly(){


        File file = new File(imagePath);

        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"),file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("imageFile",file.getName(),requestBody);
        APIServer server = Url.getInstance().create(APIServer.class);
        Call<ImageResponse> responseBodyCall = server.uploadImage(Url.token,body);

        StrictMode();
        try{
            Response<ImageResponse> imageResponseResponse = responseBodyCall.execute();
            imageName = imageResponseResponse.body().getFilename();
            Toast.makeText(this, imageName, Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }
}
