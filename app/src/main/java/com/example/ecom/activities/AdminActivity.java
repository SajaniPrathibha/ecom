package com.example.ecom.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ecom.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.protobuf.Any;

import java.util.HashMap;
import java.util.Map;

public class AdminActivity extends AppCompatActivity {

    EditText name,description,price,itemQty,type,imgUrl;
    Fragment homeFragment;
    Toolbar toolbar;
    FirebaseAuth auth;
    Button addItem;
    DatabaseReference databaseReference;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        toolbar = findViewById(R.id.home_toolbar);
        setSupportActionBar(toolbar);
        auth = FirebaseAuth.getInstance();

        name = findViewById(R.id.item_name);
        description = findViewById(R.id.itemDes);
        price = findViewById(R.id.price);
        type = findViewById(R.id.type);
        imgUrl = findViewById(R.id.imgUrl);
        itemQty = findViewById(R.id.qty);
        addItem = findViewById(R.id.button3);


        databaseReference = FirebaseDatabase.getInstance().getReference();

        addItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    String itemName = name.getText().toString();
                    String itemType = type.getText().toString();
                    String itemDes  = description.getText().toString();
                    String itemImgUrl = imgUrl.getText().toString();
                    Integer itemPrice = Integer.parseInt(price.getText().toString());
                    Integer qty = Integer.parseInt(itemQty.getText().toString());


        FirebaseFirestore firestore = FirebaseFirestore.getInstance();

                Map<String, Object> items = new HashMap<>();
                items.put("name",itemName);
                items.put("price",itemPrice);
                items.put("type",itemType);
                items.put("description",itemDes);
                items.put("img_url",itemImgUrl);
                items.put("qty" , qty);
                Task<DocumentReference> df = firestore.collection("ShowAll").add(items);
                df.addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(AdminActivity.this, "Successed", Toast.LENGTH_SHORT).show();

                        startActivity(new Intent(getApplicationContext(),AdminActivity.class));
                        finish();
                    }
                });



                }

        });


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.admin_logout,menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id== R.id.menu_logout) {
            auth.signOut();
            startActivity(new Intent(getApplicationContext(), RegisterActivity.class));
            finish();

        } else if (id== R.id.menu_view) {

            startActivity(new Intent( getApplicationContext(),RemoveActivity.class));
            finish();

        } else if (id== R.id.menu_delete) {
            startActivity(new Intent( getApplicationContext(),DeleteActivity.class));
            finish();
        }

        return true;
    }

}