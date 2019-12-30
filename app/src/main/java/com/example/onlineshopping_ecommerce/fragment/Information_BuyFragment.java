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
public class Information_BuyFragment extends Fragment {
    private TextView point1,point2,point3;


    public Information_BuyFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_information__buy, container, false);
        point1=view.findViewById(R.id.point1);
        point2=view.findViewById(R.id.point2);
        point3=view.findViewById(R.id.point3);
        point1.setText("• Shopping through the shopping cart, select the items that will be purchased in accordance with your wishes");
        point2.setText("• Continue by filling the form with details of the total price");
        point3.setText("• After you place an order, we will immediately check the conditions. the availability  therefore donot send or transfer money before the confirmation.");
        return view;
    }

}
