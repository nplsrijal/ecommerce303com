package com.example.onlineshopping_ecommerce.fragment;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.example.onlineshopping_ecommerce.CustomToast;
import com.example.onlineshopping_ecommerce.MainActivity;
import com.example.onlineshopping_ecommerce.R;
import com.example.onlineshopping_ecommerce.Utils;
import com.example.onlineshopping_ecommerce.api.APIServer;
import com.example.onlineshopping_ecommerce.url.Url;

import android.content.res.ColorStateList;
import android.content.res.XmlResourceParser;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignupFragment extends Fragment implements OnClickListener {
    private static View view;
    private static EditText fullName, emailId, mobileNumber, location,
            password, confirmPassword;
    private static TextView login;
    private static Button signUpButton;
    private CheckBox terms;


    public SignupFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_signup, container, false);
        initViews();
        setListeners();
        return view;
    }

    // Initialize all views
    private void initViews() {
        fullName = (EditText) view.findViewById(R.id.fullName);
        emailId = (EditText) view.findViewById(R.id.userEmailId);
        mobileNumber = (EditText) view.findViewById(R.id.mobileNumber);
        location = (EditText) view.findViewById(R.id.location);
        password = (EditText) view.findViewById(R.id.password);
        confirmPassword = (EditText) view.findViewById(R.id.confirmPassword);
        signUpButton = (Button) view.findViewById(R.id.signUpBtn);
        login = (TextView) view.findViewById(R.id.already_user);
        terms = (CheckBox) view.findViewById(R.id.terms_conditions);


        // Setting text selector over textviews
        XmlResourceParser xrp = getResources().getXml(R.drawable.text_selector);
        try {
            ColorStateList csl = ColorStateList.createFromXml(getResources(),
                    xrp);

            login.setTextColor(csl);

        } catch (Exception e) {
        }
    }

    // Set Listeners
    private void setListeners() {
        signUpButton.setOnClickListener(this);
        login.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.signUpBtn:

                // Call checkValidation method
                if(validate()){
                    doSignup();
                }
                break;

            case R.id.already_user:

                // Replace login fragment
                new MainActivity().replaceLoginFragment();
                break;
        }

    }

    // Check Validation Method

    private boolean validate(){




        boolean checkvalidate=true;
        // Get all edittext texts
        String getFullName = fullName.getText().toString();
        String getEmailId = emailId.getText().toString();
        String getMobileNumber = mobileNumber.getText().toString();
        String getLocation = location.getText().toString();
        String getPassword = password.getText().toString();
        String getConfirmPassword = confirmPassword.getText().toString();

        // Pattern match for email id
        Pattern p = Pattern.compile(Utils.regEx);
        Matcher m = p.matcher(getEmailId);

        // Check if all strings are null or not


        if (getFullName.equals("") || getFullName.length() == 0
                || getEmailId.equals("") || getEmailId.length() == 0
                || getMobileNumber.equals("") || getMobileNumber.length() == 0
                || getLocation.equals("") || getLocation.length() == 0
                || getPassword.equals("") || getPassword.length() == 0
                || getConfirmPassword.equals("")
                || getConfirmPassword.length() == 0){
            checkvalidate=false;
            new CustomToast().Show_Toast(getActivity(), view,
                    "All fields are required.");}else  if (!m.find()){
            checkvalidate=false;
            new CustomToast().Show_Toast(getActivity(), view,
                    "Your Email Id is Invalid.");}else if(terms.isChecked()==false){
            checkvalidate=false;
            new CustomToast().Show_Toast(getActivity(), view,
                    "Terms and Conditions Required.");
        }



            // Check if both password should be equal
         if (!getConfirmPassword.equals(getPassword)){
             checkvalidate=false;
            new CustomToast().Show_Toast(getActivity(), view,
                    "Both password doesn't match.");}




        return  checkvalidate;

    }

    private void doSignup() {
        APIServer server = Url.getInstance().create(APIServer.class);
        String FullName = fullName.getText().toString();
        String EmailId = emailId.getText().toString();
        String Phone = mobileNumber.getText().toString();
        String Location = location.getText().toString();
        String Password = password.getText().toString().trim();
        String RoleId="2993b1e352c840028236f5a930343gf3";


        Call<Void> usersCall = server.register(FullName,EmailId,Phone,Location,Password,RoleId);
        usersCall.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(getActivity(), "code " + response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }
                Toast.makeText(getActivity(), "Registered Succesfully", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(getActivity(), "Error " + t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
