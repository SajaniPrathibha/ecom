package com.example.ecom.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.ecom.R;
import com.example.ecom.adapter.AdminShowAllAdapter;
import com.example.ecom.models.AdminShowAllModel;
import com.example.ecom.models.ShowAllModel;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.remote.WatchChange;

import java.util.ArrayList;
import java.util.List;

public class RemoveActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<AdminShowAllModel> list;
    AdminShowAllAdapter showAllAdapter;
    FirebaseFirestore firestore;

    Button delete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remove);


        recyclerView = findViewById(R.id.recycleView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        firestore = FirebaseFirestore.getInstance();
        list = new ArrayList<>();
        showAllAdapter = new AdminShowAllAdapter(getApplicationContext(),list);

        recyclerView.setAdapter(showAllAdapter);

        firestore.collection("ShowAll").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {

                for(DocumentChange dC: value.getDocumentChanges()){
//                    if(dC.getType() == DocumentChange.Type.ADDED){
//                        list.add(dC.getDocument().toObject(ShowAllModel.class));
//                    }
                    list.add(dC.getDocument().toObject(AdminShowAllModel.class));
                    showAllAdapter.notifyDataSetChanged();




                }
            }
        });



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.admin_view,menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id== R.id.menu_view) {

            startActivity(new Intent( getApplicationContext(),AdminActivity.class));
            finish();

        }

        return true;
    }


}