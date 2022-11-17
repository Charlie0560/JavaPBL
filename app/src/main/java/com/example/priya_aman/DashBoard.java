package com.example.priya_aman;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class DashBoard extends AppCompatActivity {
    CardView personal_details;
    CardView academic_details;
    CardView Internship_details;
    ImageView logout_btn;
    TextView no_logout,yes_logout;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_dash_board);

        personal_details = findViewById(R.id.personal_details);
        academic_details = findViewById(R.id.academic_details);
        Internship_details = findViewById(R.id.Internship_details);


        auth = FirebaseAuth.getInstance();

        logout_btn = findViewById(R.id.logout_btn);

        Dialog dialog = new Dialog(DashBoard.this);
        dialog.setContentView(R.layout.activity_logout_alert);
        dialog.setCancelable(false);

        logout_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.show();
                no_logout = dialog.findViewById(R.id.no_logout);
                yes_logout = dialog.findViewById(R.id.yes_logout);
                no_logout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
                yes_logout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        auth.signOut();
                        startActivity(new Intent(getApplicationContext(),LoginActivity.class));
                        finish();
                    }
                });
            }
        });


        personal_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),Personal_details.class));
            }
        });
        academic_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),Academic_details.class));
            }
        });
        Internship_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), com.example.priya_aman.Internship_details.class));
            }
        });
    }
}