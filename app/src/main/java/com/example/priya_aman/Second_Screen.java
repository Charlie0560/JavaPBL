package com.example.priya_aman;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

public class Second_Screen extends AppCompatActivity {

    Button student_reg,teacher_reg,hod_reg,login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_second_screen);

        student_reg = findViewById(R.id.student_reg);
        teacher_reg = findViewById(R.id.teacher_reg);
        hod_reg = findViewById(R.id.hod_reg);
        login = findViewById(R.id.login);

        student_reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),Student_Reg.class));
            }
        });
        teacher_reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),Teacher_Reg.class));
            }
        });
    }
}