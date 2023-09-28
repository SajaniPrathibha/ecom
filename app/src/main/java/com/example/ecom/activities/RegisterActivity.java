package com.example.ecom.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ecom.R;
import com.example.ecom.models.MyCartModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

    EditText name,email,password;
    CheckBox checkBox;


    private FirebaseAuth auth;

    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

//        getSupportActionBar().hide();

        auth = FirebaseAuth.getInstance();

        if (auth.getCurrentUser() != null){

            startActivity(new Intent(RegisterActivity.this,MainActivity.class));
            finish();

        }

        name = findViewById(R.id.name);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        checkBox = (CheckBox) findViewById(R.id.checkBox);

        sharedPreferences = getSharedPreferences("onBoardingScreen",MODE_PRIVATE);
        boolean isFirstTime = sharedPreferences.getBoolean("firstTime",true);

        if (isFirstTime){
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("firstTime",false);
            editor.commit();

            Intent intent = new  Intent(RegisterActivity.this,OnBoardingActivity.class);
            startActivity(intent);
            finish();
        }

    }

    public void signup(View view) {

        String userName = name.getText().toString();
        String userEmail = email.getText().toString();
        String userPassword = password.getText().toString();

        if (TextUtils.isEmpty(userName)){
            Toast.makeText(this,"Enter Name!",Toast.LENGTH_SHORT).show();
            return;

        }

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

        auth.createUserWithEmailAndPassword(userEmail,userPassword)
                .addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        FirebaseFirestore store = FirebaseFirestore.getInstance();
                        if (task.isSuccessful()){
                            Toast.makeText(RegisterActivity.this,"Successfully Register",Toast.LENGTH_SHORT).show();


                            DocumentReference df = store.collection("Üsers").document(auth.getCurrentUser().getUid());
                            Map<String,Object> userInfo = new HashMap<>();
                            userInfo.put("username", userName);
                            userInfo.put("email", userEmail);
                            userInfo.put("password", userPassword);
                            userInfo.put("isAdmin", ""+checkBox.isChecked());

                            df.set(userInfo);

                            startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
                            finish();
                        }else{
                            Toast.makeText(RegisterActivity.this,"Registration Failed"+task.getException(),Toast.LENGTH_SHORT).show();
                        }

                    }
                });


    }

    public void signin(View view) {
        startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
    }


}