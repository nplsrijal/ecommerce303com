package com.example.onlineshopping_ecommerce.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.onlineshopping_ecommerce.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class Infromation_PaymentFragment extends Fragment {
    private TextView point1,point2,point3;



    public Infromation_PaymentFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_infromation__payment, container, false);
        point1=view.findViewById(R.id.point1);
        point2=view.findViewById(R.id.point2);
        point3=view.findViewById(R.id.point3);
        point1.setText("• Once, the order is confirmed,You will be notified about the payment details. Payment can be made :");
        point2.setText("• Cash On Delivery");
        point3.setText("• Online transfer to our account");
        return view;    }

}
