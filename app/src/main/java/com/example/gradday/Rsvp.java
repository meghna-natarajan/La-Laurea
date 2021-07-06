package com.example.gradday;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;


public class Rsvp extends AppCompatActivity {
    private static final String TAG = "RsvpActivity";
    private FirebaseAuth mAuth;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    TextView rsvp,seats;
    RadioGroup rg;
    RadioButton rb;
    Button apply;
    ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rsvp);

        actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#222222")));
        rsvp = (TextView) findViewById(R.id.rsvpop);
//        seats = (TextView) findViewById(R.id.seatsop);

        mAuth = FirebaseAuth.getInstance();
        String id = mAuth.getCurrentUser().getUid();

        final DocumentReference docRef = db.collection("users").document(id);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document != null) {
                        Log.d(TAG, "DocumentSnapshot data: " + task.getResult().getData());
                        String rsvpval = document.getString("rsvp");
//                        String temp = document.getString("seats");
//                        String seatsval = "No.of seats needed : " + temp;
                        rsvp.setText(rsvpval);
//                        seats.setText(seatsval);
                    } else {
                        Log.d(TAG, "No such document");
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });

        apply = (Button) findViewById(R.id.button_apply);
        apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rg = findViewById(R.id.radioGroup);
                int radioId = rg.getCheckedRadioButtonId();
                rb = findViewById(radioId);
                String rsvpopval = rb.getText().toString();
//                ElegantNumberButton button = (ElegantNumberButton) findViewById(R.id.seat);
//                String seatsopval = button.getNumber();
                docRef.update("rsvp",rsvpopval)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Log.d(TAG, "DocumentSnapshot successfully updated!");
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.w(TAG, "Error updating document", e);
                            }
                        });
                docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document != null) {
                                Log.d(TAG, "DocumentSnapshot data: " + task.getResult().getData());
                                String rsvpval = document.getString("rsvp");
//                                String temp = document.getString("seats");
//                                String seatsval = "No.of seats needed : " + temp;
                                rsvp.setText(rsvpval);
//                                seats.setText(seatsval);
                            } else {
                                Log.d(TAG, "No such document");
                            }
                        } else {
                            Log.d(TAG, "get failed with ", task.getException());
                        }
                    }
                });
            }
        });

//        Button agenda = (Button) findViewById(R.id.button);
//        agenda.setMovementMethod(LinkMovementMethod.getInstance());
//        agenda.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(HomeActivity.this, AgendaActivity.class);
//                startActivity(intent);
//            }
//        });
    }
}