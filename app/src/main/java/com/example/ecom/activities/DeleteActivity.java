package com.example.ecom.activities;

import android.content.Intent;
import android.os.Bundle;

import com.example.ecom.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.ecom.databinding.ActivityDeleteBinding;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class DeleteActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityDeleteBinding binding;

    EditText deleteItemName;
    Button deleteBtn;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete);


        deleteItemName = findViewById(R.id.delItemName);
        deleteBtn = findViewById(R.id.deleteBtn);
        FirebaseFirestore firestore = FirebaseFirestore.getInstance();

        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = deleteItemName.getText().toString();
                deleteItemName.setText("");
                firestore.collection("ShowAll").whereEqualTo("name",name).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful() && !task.getResult().isEmpty()){

                            DocumentSnapshot snapshot = task.getResult().getDocuments().get(0);
                            String dID = snapshot.getId();
                            firestore.collection("ShowAll").document(dID).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Toast.makeText(DeleteActivity.this, "Successed", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }
                });
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