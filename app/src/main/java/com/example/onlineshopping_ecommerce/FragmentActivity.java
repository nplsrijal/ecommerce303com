package com.example.onlineshopping_ecommerce;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.onlineshopping_ecommerce.fragment.Information_BuyFragment;
import com.example.onlineshopping_ecommerce.fragment.Infromation_PaymentFragment;

public class FragmentActivity extends AppCompatActivity implements View.OnClickListener  {
    private Button btnfragment;
    private Boolean status=true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);
        btnfragment=findViewById(R.id.btngo);
        btnfragment.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction =fragmentManager.beginTransaction();
        if(status){
            Information_BuyFragment firstFragment=new Information_BuyFragment();
            fragmentTransaction.replace(R.id.fragmentcontainer,firstFragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
            btnfragment.setText("How To Buy");
            status=false;
        }
        else{
            Infromation_PaymentFragment secondFragment =new Infromation_PaymentFragment();
            fragmentTransaction.replace(R.id.fragmentcontainer,secondFragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
            btnfragment.setText("Payment");
            status=true;
        }
    }
}