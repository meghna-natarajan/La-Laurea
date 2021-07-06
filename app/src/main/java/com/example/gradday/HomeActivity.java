package com.example.gradday;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;


public class HomeActivity extends AppCompatActivity {
    ActionBar actionBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);

        actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#222222")));

        Button agenda = (Button) findViewById(R.id.button);
        agenda.setMovementMethod(LinkMovementMethod.getInstance());
        agenda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, AgendaActivity.class);
                startActivity(intent);
            }
        });

        Button rsvp = (Button) findViewById(R.id.button2);
        rsvp.setMovementMethod(LinkMovementMethod.getInstance());
        rsvp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, Rsvp.class);
                startActivity(intent);
            }
        });

        Button notes = (Button) findViewById(R.id.button3);
        notes.setMovementMethod(LinkMovementMethod.getInstance());
        notes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, NoteActivity.class);
                startActivity(intent);
            }
        });

        Button ein = (Button) findViewById(R.id.einvite);
        ein.setMovementMethod(LinkMovementMethod.getInstance());
        ein.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, eInviteActivity.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.signout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(HomeActivity.this, MainActivity.class);

                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);//makesure user cant go back
                startActivity(intent);
            }
        });
    }
}