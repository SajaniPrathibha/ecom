package com.example.ecom.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
//import android.widget.Toolbar;

import com.example.ecom.R;

public class paymentActivity extends AppCompatActivity {
    Toolbar toolbar;
    TextView subTotal,discount,shipping,total;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        //toolbar
        toolbar = findViewById(R.id.payment_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        double amount = 0.0;
        amount = getIntent().getDoubleExtra("amount",0.0);

        subTotal = findViewById(R.id.sub_total);
        discount = findViewById(R.id.textView17);
        shipping = findViewById(R.id.textView18);
        total = findViewById(R.id.total_amt);

        subTotal.setText(amount+"$");
    }
}