package com.example.onlineshopping_ecommerce.fragment;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.example.onlineshopping_ecommerce.CustomToast;
import com.example.onlineshopping_ecommerce.DashboardActivity;
import com.example.onlineshopping_ecommerce.ForgotPasswordActivity;
import com.example.onlineshopping_ecommerce.MainActivity;
import com.example.onlineshopping_ecommerce.R;
import com.example.onlineshopping_ecommerce.SplashActivity;
import com.example.onlineshopping_ecommerce.Utils;
import com.example.onlineshopping_ecommerce.ViewPagerActivity;
import com.example.onlineshopping_ecommerce.api.APIServer;
import com.example.onlineshopping_ecommerce.model.LoginSignupResponse;
import com.example.onlineshopping_ecommerce.url.Url;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.content.res.XmlResourceParser;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.InputType;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginFragment extends Fragment implements OnClickListener {
    private static View view;

    private static EditText emailid, password;
    private static Button loginButton;
    private static TextView forgotPassword, signUp;
    private static CheckBox show_hide_password;
    private static LinearLayout loginLayout;
    private static Animation shakeAnimation;
    private static FragmentManager fragmentManager;

    public LoginFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_login, container, false);
        initViews();
        setListeners();
        return view;
    }

    // Initiate Views
    private void initViews() {
        fragmentManager = getActivity().getSupportFragmentManager();

        emailid = (EditText) view.findViewById(R.id.login_emailid);
        password = (EditText) view.findViewById(R.id.login_password);
        loginButton = (Button) view.findViewById(R.id.loginBtn);
        forgotPassword = (TextView) view.findViewById(R.id.forgot_password);
        signUp = (TextView) view.findViewById(R.id.createAccount);
        show_hide_password = (CheckBox) view
                .findViewById(R.id.show_hide_password);
        loginLayout = (LinearLayout) view.findViewById(R.id.login_layout);

        // Load ShakeAnimation
        shakeAnimation = AnimationUtils.loadAnimation(getActivity(),
                R.anim.shake);

        // Setting text selector over textviews
        XmlResourceParser xrp = getResources().getXml(R.drawable.text_selector);
        try {
            ColorStateList csl = ColorStateList.createFromXml(getResources(),
                    xrp);

            forgotPassword.setTextColor(csl);
            show_hide_password.setTextColor(csl);
            signUp.setTextColor(csl);
        } catch (Exception e) {
        }
    }

    // Set Listeners
    private void setListeners() {
        loginButton.setOnClickListener(this);
        forgotPassword.setOnClickListener(this);
        signUp.setOnClickListener(this);

        // Set check listener over checkbox for showing and hiding password
        show_hide_password
                .setOnCheckedChangeListener(new OnCheckedChangeListener() {

                    @Override
                    public void onCheckedChanged(CompoundButton button,
                                                 boolean isChecked) {

                        // If it is checkec then show password else hide
                        // password
                        if (isChecked) {

                            show_hide_password.setText(R.string.hide_pwd);// change
                            // checkbox
                            // text

                            password.setInputType(InputType.TYPE_CLASS_TEXT);
                            password.setTransformationMethod(HideReturnsTransformationMethod
                                    .getInstance());// show password
                        } else {
                            show_hide_password.setText(R.string.show_pwd);// change
                            // checkbox
                            // text

                            password.setInputType(InputType.TYPE_CLASS_TEXT
                                    | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                            password.setTransformationMethod(PasswordTransformationMethod
                                    .getInstance());// hide password

                        }

                    }
                });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.loginBtn:
                if(validate()){
                    doLogin();
                }
                break;


            case R.id.createAccount:

                // Replace signup frgament with animation
                fragmentManager
                        .beginTransaction()
                        .setCustomAnimations(R.anim.right_enter, R.anim.left_out)
                        .replace(R.id.frameContainer, new SignupFragment(),
                                Utils.SignUp_Fragment).commit();
                break;

            case R.id.forgot_password:
                Intent intent = new Intent(getActivity(), ForgotPasswordActivity.class);
                startActivity(intent);

                break;
        }

    }

    // Check Validation before login

    private boolean validate(){
        boolean checkvalidate=true;
        if (emailid.getText().toString().equals("") || emailid.getText().toString().length() == 0
                || password.getText().toString().equals("") || password.getText().toString().length() == 0) {
            checkvalidate=false;
            loginLayout.startAnimation(shakeAnimation);
            new CustomToast().Show_Toast(getActivity(), view,
                    "Enter both credentials.");

        }
        if(TextUtils.isEmpty(emailid.getText().toString())){
            loginLayout.startAnimation(shakeAnimation);
            new CustomToast().Show_Toast(getActivity(), view,
                    "Enter Username credentials.");
            emailid.setError("Username is required");
            emailid.requestFocus();
            checkvalidate=false;
        }
        if(TextUtils.isEmpty(password.getText().toString())){
            loginLayout.startAnimation(shakeAnimation);
            new CustomToast().Show_Toast(getActivity(), view,
                    "Enter Password credentials.");
            password.setError("Password is required");
            password.requestFocus();
            checkvalidate=false;
        }
        return  checkvalidate;

    }

    private void doLogin() {
        APIServer server = Url.getInstance().create(APIServer.class);

        String email = emailid.getText().toString();
        String Password = password.getText().toString();

        Call<LoginSignupResponse> usersCall = server.checkUser(email,Password);
        usersCall.enqueue(new Callback<LoginSignupResponse>() {
            @Override
            public void onResponse(Call<LoginSignupResponse> call, Response<LoginSignupResponse> response) {


                if(!response.isSuccessful()){
                    Toast.makeText(getActivity(),"Either EmailId or Password is Incorrect ",Toast.LENGTH_SHORT).show();
                    return;

                }else{
                    //Toast.makeText(getActivity()," "+response.body().getToken(),Toast.LENGTH_SHORT).show();


                    if(response.body().getSuccess()){
                        SharedPreferences sharedPreferences=getActivity().getSharedPreferences("User", Context.MODE_PRIVATE);
                        SharedPreferences.Editor edt = sharedPreferences.edit();
                        edt.putString("userid",response.body().getUserid());
                        edt.putString("token",response.body().getToken());

                        edt.putBoolean("activity_executed", true);
                        edt.commit();

                        Url.userid=response.body().getUserid();

                        Url.token=response.body().getToken();
                        // Toast.makeText(getActivity(),"Success",Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(getActivity(), DashboardActivity.class);
                        startActivity(intent);
                    }else{
                        Toast.makeText(getActivity(),"Either EmailId or Password is Incorrect ",Toast.LENGTH_SHORT).show();
                        return;

                    }
                }
            }

            @Override
            public void onFailure(Call<LoginSignupResponse> call, Throwable t) {
                Toast.makeText(getActivity(),"Error "+t.getLocalizedMessage(),Toast.LENGTH_SHORT).show();

            }
        });
    }

}