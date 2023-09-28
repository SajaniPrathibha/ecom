package com.example.ecom.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.ecom.R;
import com.example.ecom.models.NewProductModels;
import com.example.ecom.models.PopularProductsModel;
import com.example.ecom.models.ShowAllModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class DetailedActivity2 extends AppCompatActivity {

    ImageView detailedImg;
    TextView rating,name,description,price,quantity;
    Button addToCart,buyNow;
    ImageView addItems,removeItems;

    Toolbar toolbar;
    int totalQuantity = 1;
    int totalPrice = 0;

    //New Products
    NewProductModels newProductModels = null;
//    popular products
    PopularProductsModel popularProductsModel = null;

//    show All
    ShowAllModel showAllModel = null;

    FirebaseAuth auth;
    private FirebaseFirestore firestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed2);

        toolbar = findViewById(R.id.detailed_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        firestore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();

        final Object obj = getIntent().getSerializableExtra("detailed");

        if (obj instanceof NewProductModels){
            newProductModels = (NewProductModels) obj;

        } else if (obj instanceof PopularProductsModel)  {
            popularProductsModel = (PopularProductsModel) obj;

        }else if (obj instanceof ShowAllModel)  {
            showAllModel = (ShowAllModel) obj;

        }

        detailedImg = findViewById(R.id.detailed_img);
        quantity = findViewById(R.id.quantity);
        name = findViewById(R.id.detaild_name);
        rating = findViewById(R.id.rating);
        description = findViewById(R.id.detailed_desc);
        price = findViewById(R.id.detailed_price);

        addToCart = findViewById(R.id.add_to_cart);
        buyNow = findViewById(R.id.buy_now);

        addItems = findViewById(R.id.add_item);
        removeItems = findViewById(R.id.remove_item);

//        New Products
        if (newProductModels != null){
            Glide.with(getApplicationContext()).load(newProductModels.getImg_url()).into(detailedImg);
            rating.setText(newProductModels.getRating());
            name.setText(newProductModels.getName());
            description.setText(newProductModels.getDescription());
            price.setText(String.valueOf(newProductModels.getPrice()));
            name.setText(newProductModels.getName());

            totalPrice = newProductModels.getPrice()*totalQuantity;
        }
        //        popular products
        if (popularProductsModel != null){
            Glide.with(getApplicationContext()).load(popularProductsModel.getImg_url()).into(detailedImg);
            rating.setText(popularProductsModel.getRating());
            name.setText(popularProductsModel.getName());
            description.setText(popularProductsModel.getDescription());
            price.setText(String.valueOf(popularProductsModel.getPrice()));
            name.setText(popularProductsModel.getName());

            totalPrice = popularProductsModel.getPrice()*totalQuantity;
        }
        //   Show All products
        if (showAllModel != null){
            Glide.with(getApplicationContext()).load(showAllModel.getImg_url()).into(detailedImg);
            rating.setText(showAllModel.getRating());
            name.setText(showAllModel.getName());
            description.setText(showAllModel.getDescription());
            price.setText(String.valueOf(showAllModel.getPrice()));
            name.setText(showAllModel.getName());

            totalPrice = showAllModel.getPrice()*totalQuantity;
        }
        //Buy now

        buyNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                startActivity(new Intent(DetailedActivity2.this,AddressActivity.class));
              Intent intent = new Intent(DetailedActivity2.this,AddressActivity.class);
                if (newProductModels != null){
                    intent.putExtra("item",newProductModels);
                }
                if (popularProductsModel != null){
                    intent.putExtra("item",popularProductsModel);
                }
                if (showAllModel != null){
                    intent.putExtra("item",showAllModel);
                }
              startActivity(intent);
            }
        });

        //Add to cart
        addToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addToCart();
            }
        });

        addItems.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (totalQuantity < 10){
                    totalQuantity++;
                    quantity.setText(String.valueOf(totalQuantity));
                    if (newProductModels != null){
                        totalPrice = newProductModels.getPrice()*totalQuantity;
                    }
                    if (popularProductsModel != null){
                        totalPrice = popularProductsModel.getPrice()*totalQuantity;
                    }
                    if (showAllModel != null){
                        totalPrice = showAllModel.getPrice()*totalQuantity;
                    }
                }

            }
        });
        removeItems.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (totalQuantity>1){
                    totalQuantity--;
                    quantity.setText(String.valueOf(totalQuantity));
                }

            }
        });

    }
        private void addToCart() {
        String saveCurrentTime,saveCurrentDate;
        Calendar calForDate = Calendar.getInstance();

        SimpleDateFormat currentDate = new SimpleDateFormat("MM ,dd,yyy");
        saveCurrentDate = currentDate.format(calForDate.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime = currentTime.format(calForDate.getTime());

        final HashMap<String,Object>cartMap = new HashMap<>();

        cartMap.put("productName",name.getText().toString());
        cartMap.put("productPrice",price.getText().toString());
        cartMap.put("currentTime",saveCurrentTime);
        cartMap.put("currentDate",saveCurrentDate);
            cartMap.put("totalQuantity",quantity.getText().toString());
            cartMap.put("totalPrice",totalPrice);

        firestore.collection("AddToCart").document(auth.getCurrentUser().getUid())
                .collection("User").add(cartMap).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentReference> task) {
                        Toast.makeText(DetailedActivity2.this,"Added To A Cart",Toast.LENGTH_LONG).show();
                        finish();

                    }
                });
    }
}