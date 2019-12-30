package com.example.onlineshopping_ecommerce.api;



import com.example.onlineshopping_ecommerce.model.Cart;
import com.example.onlineshopping_ecommerce.model.Category;
import com.example.onlineshopping_ecommerce.model.Checkout;
import com.example.onlineshopping_ecommerce.model.CheckoutResponse;
import com.example.onlineshopping_ecommerce.model.ImageResponse;
import com.example.onlineshopping_ecommerce.model.LoginSignupResponse;
import com.example.onlineshopping_ecommerce.model.OTP;
import com.example.onlineshopping_ecommerce.model.Order;
import com.example.onlineshopping_ecommerce.model.PasswordResponse;
import com.example.onlineshopping_ecommerce.model.Product;
import com.example.onlineshopping_ecommerce.model.Users;

import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface APIServer {

    //For login

    @FormUrlEncoded
    @POST("api/account/login")
    Call<LoginSignupResponse> checkUser(@Field("email") String email , @Field("Password") String Password);

    //For Signup

    @FormUrlEncoded
    @POST("api/account/signup")
    Call<Void> register(@Field("FullName") String FullName , @Field("EmailId") String EmailId,@Field("Phone") String Phone ,@Field("Location") String Location , @Field("Password") String Password, @Field("RoleId") String RoleId);

    //For Category
    @GET("api/setup/category/grid/list")
    Call<List<Category>>getAllCategory(@Header("x-access-token") String token);

    //For Product
    @GET("api/setup/product/getByCat/{id}")
    Call<List<Product>>getProductByCat(@Header("x-access-token") String token,@Path("id") String id);


    //Search Product By Name
    @FormUrlEncoded
    @POST("api/setup/product/getByName")
    Call<List<Product>> getProductByName(@Header("x-access-token") String token,@Field("id") String id , @Field("name") String name);

  //Add To Cart
    @FormUrlEncoded
    @POST("api/cart/create")
    Call<Void> addtoCart(@Header("x-access-token") String token,@Field("Userid") String Userid , @Field("Productid") String Productid, @Field("Quantity") String Quantity, @Field("Price") String Price, @Field("Ischeckout") int Ischeckout);

    //Get Cart By User
    @GET("api/cart/Byuser/{id}")
    Call<List<Cart>>getCartByUser(@Header("x-access-token") String token, @Path("id") String id);

    // Delete Cart Item
    @DELETE("api/cart/delete/{id}")
    Call<Void> deleteEmployee(@Header("x-access-token") String token,@Path("id") String id);

    //Remove Every Cart Item of specific User
    @DELETE("api/cart/all/delete/{id}")
    Call<Void> RemoveAllCart(@Header("x-access-token") String token,@Path("id") String id);

    //At Checkout Get Total Amount from Cart
    @GET("api/cart/atcheckout/{id}")
    Call<Checkout>getCheckout(@Header("x-access-token") String token, @Path("id") String id);

    //Checkout
    @FormUrlEncoded
    @POST("api/order/create")
    Call<CheckoutResponse>checkout(@Header("x-access-token") String token, @Field("Userid") String Userid , @Field("Address") String Address, @Field("City") String City, @Field("Landmark") String Landmark, @Field("Phone") String Phone);

    //Get Logged in User info
    @GET("api/setup/user/get/{id}")
    Call<Users>getUserById(@Header("x-access-token") String token, @Path("id") String id);

    //Profile Update
    @FormUrlEncoded
    @POST("api/setup/user/update")
    Call<Void> updateProfile(@Header("x-access-token") String token, @Field("id") String id , @Field("FullName") String FullName , @Field("EmailId") String EmailId,@Field("Phone") String Phone ,@Field("Location") String Location,@Field("image") String image);

    //User Image Upload
    @Multipart
    @POST("api/setup/user/upload")
    Call<ImageResponse> uploadImage(@Header("x-access-token") String token,@Part MultipartBody.Part img);

    //For Order
    @GET("api/order/get/{id}")
    Call<List<Product>>getOrderProduct(@Header("x-access-token") String token, @Path("id") String id);

    //Get order by id
    @GET("api/order/getByUser/{id}")
    Call<List<Order>>getOrder(@Header("x-access-token") String token, @Path("id") String id);


    //Contact
    @FormUrlEncoded
    @POST("api/user/contact")
    Call<Void> contact(@Header("x-access-token") String token,@Field("Userid") String Userid , @Field("Subject") String Subject, @Field("Message") String Message);

    //For Password Recovery

    @FormUrlEncoded
    @POST("api/account/password")
    Call<PasswordResponse> forgotpassword(@Field("email") String email);

    //Code validation and Time

    @FormUrlEncoded
    @POST("api/account/otpvalidate")
    Call<OTP> codevalidate(@Field("code") String code, @Field("usercode") String usercode, @Field("currtime") String currtime, @Field("finaltime") String finaltime, @Field("currentDateandTime") String currentDateandTime);

    //Reset Password

    @FormUrlEncoded
    @POST("api/account/resetpassword")
    Call<Void> reset(@Field("email") String email, @Field("password") String password);

}

