package com.example.gradday;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


public class RegistrationActivity extends AppCompatActivity {

    ActionBar actionBar;
    EditText email,password,fullName,rollNo,dept;
    Button regButton;
    private FirebaseAuth mAuth;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private static final String TAG = "RegistrationActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registrationxml);

        actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#222222")));
//        TextView login = (TextView)findViewById(R.id.lnkLogin);
//        login.setMovementMethod(LinkMovementMethod.getInstance());
//        login.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(RegistrationActivity.this, MainActivity.class);
//                startActivity(intent);
//            }
//        });

        email = (EditText) findViewById(R.id.txtEmail);
        password = (EditText) findViewById(R.id.txtPwd);
        fullName = (EditText) findViewById(R.id.txtFn);
        dept = (EditText) findViewById(R.id.txtDept);
        rollNo = (EditText) findViewById(R.id.txtRollNo);
        regButton = (Button) findViewById(R.id.btnLogin);
        mAuth = FirebaseAuth.getInstance();

        regButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                final String em = email.getText().toString();
                String ps = password.getText().toString();
                final String fn = fullName.getText().toString();
                final String dpt = dept.getText().toString();
                final String rno = rollNo.getText().toString();

                if(TextUtils.isEmpty(em)){
                    Toast.makeText(getApplicationContext(),"Please enter your E-mail address",Toast.LENGTH_LONG).show();
                    return;
                }
                if(TextUtils.isEmpty(fn)){
                    Toast.makeText(getApplicationContext(),"Please enter your Full Name",Toast.LENGTH_LONG).show();
                    return;
                }
                if(TextUtils.isEmpty(dpt)){
                    Toast.makeText(getApplicationContext(),"Please enter your Department",Toast.LENGTH_LONG).show();
                    return;
                }
                if(TextUtils.isEmpty(rno)){
                    Toast.makeText(getApplicationContext(),"Please enter your Roll Number",Toast.LENGTH_LONG).show();
                    return;
                }
                if(TextUtils.isEmpty(ps)){
                    Toast.makeText(getApplicationContext(),"Please enter your Password",Toast.LENGTH_LONG).show();
                }
                if (ps.length() == 0){
                    Toast.makeText(getApplicationContext(),"Please enter your Password",Toast.LENGTH_LONG).show();
                }
                if (ps.length()<8){
                    Toast.makeText(getApplicationContext(),"Password must be more than 8 digit",Toast.LENGTH_LONG).show();
                }
                else{
                    mAuth.createUserWithEmailAndPassword(em,ps)
                            .addOnCompleteListener(RegistrationActivity.this, new OnCompleteListener<AuthResult>() {
                                public void onComplete(@NonNull Task<AuthResult> task) {

                                    if (!task.isSuccessful()) {
                                        Toast.makeText(RegistrationActivity.this, "ERROR",Toast.LENGTH_LONG).show();
                                        Log.e( TAG,"MY MESSAGE : " + task.getException().getMessage());
                                    }
                                    else {
                                        String id = mAuth.getCurrentUser().getUid();

                                        Map<String, Object> user = new HashMap<>();
                                        user.put("fullName",fn);
                                        user.put("email",em);
                                        user.put("dept",dpt);
                                        user.put("rollNo",rno);
                                        user.put("rsvp","No one is attending");
                                        user.put("seats","0");
                                        user.put("note"," ");

                                        db.collection("users").document(id).set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                startActivity(new Intent(RegistrationActivity.this, HomeActivity.class));
                                                finish();
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Log.w(TAG, "Error adding document", e);
                                            }
                                        });
                                    }
                                }
                            });
                }
            }
        });
    }
}