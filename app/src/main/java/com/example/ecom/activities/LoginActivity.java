package com.example.ecom.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ecom.R;
import com.example.ecom.models.MyCartModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class LoginActivity extends AppCompatActivity {

    EditText email,password;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

//        getSupportActionBar().hide();

        auth = FirebaseAuth.getInstance();

        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
    }

    public void signIn(View view) {

        String userEmail = email.getText().toString();
        String userPassword = password.getText().toString();

        if (TextUtils.isEmpty(userEmail)){
            Toast.makeText(this,"Enter Email Address!",Toast.LENGTH_SHORT).show();
            return;

        }

        if (TextUtils.isEmpty(userPassword)){
            Toast.makeText(this,"Enter Password!",Toast.LENGTH_SHORT).show();
            return;

        }
        if (userPassword.length()<6){
            Toast.makeText(this,"Password too short, enter minimum 6 characters!",Toast.LENGTH_SHORT).show();
            return;

        }
        FirebaseFirestore store = FirebaseFirestore.getInstance();
        auth.signInWithEmailAndPassword(userEmail,userPassword)
                        .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()){
                                    Toast.makeText(LoginActivity.this,"Login Successful",Toast.LENGTH_SHORT).show();

                                    DocumentReference df = store.collection("Users").document(auth.getCurrentUser().getUid());
                                    df.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                        @Override
                                        public void onSuccess(DocumentSnapshot documentSnapshot) {

                                            Log.d("TAG", "onSuccess"+documentSnapshot.getData());

                                            if(documentSnapshot.getString("isAdmin") != "false"){
                                                startActivity(new Intent(getApplicationContext(), AdminActivity.class));
                                                finish();
                                            }else {
                                                startActivity(new Intent(getApplicationContext(),MainActivity.class));
                                                finish();
                                            }

                                        }
                                    });


                                }else {
                                    Toast.makeText(LoginActivity.this,"Error"+task.getException(),Toast.LENGTH_SHORT).show();
                                }

                            }
                        });

    }

    public void signUp(View view) {
        startActivity(new Intent(LoginActivity.this,RegisterActivity.class));

    }
}