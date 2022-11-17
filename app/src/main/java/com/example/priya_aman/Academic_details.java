package com.example.priya_aman;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Academic_details extends AppCompatActivity {

    EditText fe_sem1_cgpa,fe_sem2_cgpa,se_sem1_cgpa,se_sem2_cgpa,te_sem1_cgpa,te_sem2_cgpa,be_sem1_cgpa,be_sem2_cgpa;
    TextView display_result_cgpa;

    LinearLayout fe_marksheet,se_marksheet,te_marksheet,be_marksheet;
    CardView calculate_cgpa,upload_marksheet_btn;
    ImageView plus_se,plus_te,plus_be;

    FirebaseAuth auth;
    FirebaseFirestore fstore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_academic_details);


        fe_sem1_cgpa = findViewById(R.id.fe_sem1_cgpa);
        fe_sem2_cgpa = findViewById(R.id.fe_sem2_cgpa);
        se_sem1_cgpa = findViewById(R.id.se_sem1_cgpa);
        se_sem2_cgpa = findViewById(R.id.se_sem2_cgpa);
        te_sem1_cgpa = findViewById(R.id.te_sem1_cgpa);
        te_sem2_cgpa = findViewById(R.id.te_sem2_cgpa);
        be_sem1_cgpa = findViewById(R.id.be_sem1_cgpa);
        be_sem2_cgpa = findViewById(R.id.be_sem2_cgpa);

        display_result_cgpa = findViewById(R.id.display_result_cgpa);
        calculate_cgpa = findViewById(R.id.calculate_cgpa);

        upload_marksheet_btn=findViewById(R.id.upload_marksheet_btn);
        fe_marksheet = findViewById(R.id.fe_marksheet);
        plus_se=findViewById(R.id.plus_se);
        se_marksheet = findViewById(R.id.se_marksheet);
        plus_te=findViewById(R.id.plus_te);
        te_marksheet = findViewById(R.id.te_marksheet);
        plus_be=findViewById(R.id.plus_be);
        be_marksheet=findViewById(R.id.be_marksheet);

        auth = FirebaseAuth.getInstance();
        fstore  = FirebaseFirestore.getInstance();

        calculate_cgpa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Double  fesem1,fesem2,sesem1,sesem2,tesem1,tesem2,besem1,besem2;
                Double count = 0.0;
                if(fe_sem1_cgpa.getText().toString().equals(""))
                {
                    fesem1 = 0.0;
                }
                else
                {
                    count++;
                    fesem1 = Double.parseDouble(fe_sem1_cgpa.getText().toString());
                }

                if(fe_sem2_cgpa.getText().toString().equals(""))
                {
                    fesem2 = 0.0;
                }
                else
                {
                    count++;
                    fesem2 = Double.parseDouble(fe_sem2_cgpa.getText().toString());
                }

                if(se_sem1_cgpa.getText().toString().equals(""))
                {
                    sesem1 = 0.0;
                }
                else
                {
                    count++;
                    sesem1 = Double.parseDouble(se_sem1_cgpa.getText().toString());
                }

                if(se_sem2_cgpa.getText().toString().equals(""))
                {
                    sesem2 = 0.0;
                }
                else
                {
                    count++;
                    sesem2 = Double.parseDouble(se_sem2_cgpa.getText().toString());
                }
                if(te_sem1_cgpa.getText().toString().equals(""))
                {
                    tesem1 = 0.0;
                }
                else
                {
                    count++;
                    tesem1 = Double.parseDouble(te_sem1_cgpa.getText().toString());
                }


                if(te_sem2_cgpa.getText().toString().equals(""))
                {
                    tesem2 = 0.0;
                }
                else
                {
                    count++;
                    tesem2 = Double.parseDouble(te_sem2_cgpa.getText().toString());
                }

                if(be_sem1_cgpa.getText().toString().equals(""))
                {
                    besem1 = 0.0;
                }
                else
                {
                    count++;
                    besem1 = Double.parseDouble(be_sem1_cgpa.getText().toString());
                }

                if(be_sem2_cgpa.getText().toString().equals(""))
                {
                    besem2 = 0.0;
                }
                else {
                    count++;
                    besem2 = Double.parseDouble(be_sem2_cgpa.getText().toString());
                }

                float result = (float) ((fesem1+fesem2+sesem1+sesem2+tesem1+tesem2+besem1+besem2)/count);
                String s = result+"";

                display_result_cgpa.setText(s);


                DocumentReference df = fstore.collection("Academic Details").document(auth.getCurrentUser().getUid());
                Map<String,Object> user_academic_details = new HashMap<>();

                if(!fe_sem1_cgpa.getText().toString().equals(""))
                {
                    user_academic_details.put("First Year Sem 1",fe_sem1_cgpa.getText().toString());
                }
                if(!fe_sem2_cgpa.getText().toString().equals(""))
                {
                    user_academic_details.put("First Year Sem 2",fe_sem2_cgpa.getText().toString());
                }
                if(!se_sem1_cgpa.getText().toString().equals(""))
                {
                    user_academic_details.put("Second Year Sem 1",se_sem1_cgpa.getText().toString());
                }
                if(!se_sem2_cgpa.getText().toString().equals(""))
                {
                    user_academic_details.put("Second Year Sem 2",se_sem2_cgpa.getText().toString());
                }
                if(!te_sem1_cgpa.getText().toString().equals(""))
                {
                    user_academic_details.put("Third Year Sem 1",te_sem1_cgpa.getText().toString());
                }
                if(!te_sem2_cgpa.getText().toString().equals(""))
                {
                    user_academic_details.put("Third Year Sem 2",te_sem2_cgpa.getText().toString());
                }
                if(!be_sem1_cgpa.getText().toString().equals(""))
                {
                    user_academic_details.put("Fourth Year Sem 1",be_sem1_cgpa.getText().toString());
                }
                if(!be_sem2_cgpa.getText().toString().equals(""))
                {
                    user_academic_details.put("Fourth Year Sem 2",be_sem2_cgpa.getText().toString());
                }
                user_academic_details.put("Total CGPA",s);
                df.set(user_academic_details);
            }
        });


        upload_marksheet_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fe_marksheet.setVisibility(View.VISIBLE);
                plus_se.setVisibility(View.VISIBLE);
            }
        });

        plus_se.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                plus_se.setVisibility(View.INVISIBLE);
                se_marksheet.setVisibility(View.VISIBLE);
                plus_te.setVisibility(View.VISIBLE);
            }
        });

        plus_te.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                plus_te.setVisibility(View.INVISIBLE);
                te_marksheet.setVisibility(View.VISIBLE);
                plus_be.setVisibility(View.VISIBLE);
            }
        });

        plus_be.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                plus_be.setVisibility(View.INVISIBLE);
                be_marksheet.setVisibility(View.VISIBLE);
            }
        });

    }
}