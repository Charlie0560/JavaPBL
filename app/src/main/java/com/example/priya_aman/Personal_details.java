package com.example.priya_aman;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Personal_details extends AppCompatActivity {

    EditText name_personal_details,roll_personal_details,email_personal_details,
            registerid_personal_details,phone_personal_details,dob_personal_details,curraddress_personal_details,permanentaddre_personal_details,
            father_name,father_phone_number,father_occupation,mothers_name,mothers_phone_number,mothers_occupation;
    CardView save_personal_details;
    Spinner dept_personal_details,year_personal_details,division_personal_details;
    ImageView back_personal_details;
    FirebaseAuth auth;
    FirebaseFirestore fstore;
    String name,email,registration_id,phone,department,year,division;
    int count = 0;

    String [] department_select = {"Select","Computer Science","Electronics and Telecommunication","Information Technology"};
    String [] year_select = {"Select","FE","SE","TE","BE"};
    String [] div_select = {"Select","I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX", "X","XI"};
    ArrayAdapter<String> adapter_dept;
    ArrayAdapter<String> adapter_year;
    ArrayAdapter<String> adapter_div;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_personal_details);

        back_personal_details=findViewById(R.id.back_personal_details);

        name_personal_details=findViewById(R.id.name_personal_details);
        roll_personal_details=findViewById(R.id.roll_personal_details);
        email_personal_details=findViewById(R.id.email_personal_details);
        registerid_personal_details=findViewById(R.id.registerid_personal_details);
        phone_personal_details=findViewById(R.id.phone_personal_details);
        dob_personal_details=findViewById(R.id.dob_personal_details);
        curraddress_personal_details=findViewById(R.id.curraddress_personal_details);
        permanentaddre_personal_details=findViewById(R.id.permanentaddre_personal_details);
        dept_personal_details=findViewById(R.id.dept_personal_details);
        year_personal_details=findViewById(R.id.year_personal_details);
        division_personal_details=findViewById(R.id.division_personal_details);
        father_name=findViewById(R.id.father_name);
        father_phone_number=findViewById(R.id.father_phone_number);
        father_occupation=findViewById(R.id.father_occupation);
        mothers_name=findViewById(R.id.mothers_name);
        mothers_phone_number=findViewById(R.id.mothers_phone_number);
        mothers_occupation=findViewById(R.id.mothers_occupation);


        save_personal_details=findViewById(R.id.save_personal_details);

        auth = FirebaseAuth.getInstance();
        fstore = FirebaseFirestore.getInstance();

        back_personal_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),DashBoard.class));
                finish();
            }
        });


        adapter_dept = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item,department_select);
        dept_personal_details.setAdapter(adapter_dept);

        adapter_year = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item,year_select);
        year_personal_details.setAdapter(adapter_year);

        adapter_div = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item,div_select);
        division_personal_details.setAdapter(adapter_div);

        //department select
        dept_personal_details.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                department = dept_personal_details.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        //Year select
        year_personal_details.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                year = year_personal_details.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        //division select
        division_personal_details.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                division = division_personal_details.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        save_personal_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String roll_no = roll_personal_details.getText().toString();
                String DOB = dob_personal_details.getText().toString();
                String current_address = curraddress_personal_details.getText().toString();
                String permanent_address = permanentaddre_personal_details.getText().toString();
                String fathers_name = father_name.getText().toString();
                String fathers_occupation = father_occupation.getText().toString();
                String father_phone = father_phone_number.getText().toString();
                String mother_name = mothers_name.getText().toString();
                String mother_phone = mothers_phone_number.getText().toString();
                String mother_occupation = mothers_occupation.getText().toString();

                if(check(roll_no))
                {
                    roll_personal_details.setError("Enter Roll No");
                }
                else if(department.equals("Select"))
                {
                    ((TextView)dept_personal_details.getSelectedView()).setError("Select Department");
                }
                else if(year.equals("Select"))
                {
                    ((TextView)year_personal_details.getSelectedView()).setError("Select Year");
                }
                else if(division.equals("Select"))
                {
                    ((TextView)division_personal_details.getSelectedView()).setError("Select Division");
                }
                else
                {
                    Map<String,Object> user_details = new HashMap<>();
                    DocumentReference documentReference = fstore.collection("Student Personal Details").document(auth.getCurrentUser().getUid());
                    user_details.put("Name",name);
                    user_details.put("Roll No",roll_no);
                    user_details.put("Email",email);
                    user_details.put("Registration ID",registration_id);
                    user_details.put("Phone Number",phone);
                    user_details.put("D.O.B",DOB);
                    user_details.put("Current Address",current_address);
                    user_details.put("Permanent Address",permanent_address);
                    user_details.put("Department",department);
                    user_details.put("Year",year);
                    user_details.put("Division",division);
                    user_details.put("Father's Name",fathers_name);
                    user_details.put("Father's Phone No.",father_phone);
                    user_details.put("Father's Occupation",fathers_occupation);
                    user_details.put("Mother's Name",mother_name);
                    user_details.put("Mother's Phone No.",mother_phone);
                    user_details.put("Mother's Occupation",mother_occupation);

                    documentReference.set(user_details);
                    count++;

                    Toast.makeText(Personal_details.this, "Successfully Saved", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(),DashBoard.class));
                    finish();
                }
            }
        });
    }

    boolean check(String s)
    {
        if(s.equals(""))
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (FirebaseAuth.getInstance().getCurrentUser()!=null)
        {
            checkUserAccesLevel(auth.getUid());
        }
    }
    private void checkUserAccesLevel(String uid) {
        DocumentReference df = fstore.collection("Students").document(uid);
        df.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                name = documentSnapshot.getString("Name");
                email = documentSnapshot.getString("Email");
                registration_id = documentSnapshot.getString("Registration ID");
                phone = documentSnapshot.getString("Phone number");

                name_personal_details.setText(name);
                email_personal_details.setText(email);
                registerid_personal_details.setText(registration_id);
                phone_personal_details.setText(phone);
            }
        });

//        if(count!=0) {
//        DocumentReference documentReference = fstore.collection("Student Personal Details").document(uid);
//        documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
//            @Override
//            public void onSuccess(DocumentSnapshot documentSnapshot) {
//
//                roll_personal_details.setText(documentSnapshot.getString("Roll No"));
//                dob_personal_details.setText(documentSnapshot.getString("D.O.B"));
//                curraddress_personal_details.setText(documentSnapshot.getString("Current Address"));
//                permanentaddre_personal_details.setText(documentSnapshot.getString("Permanent Address"));
//                father_name.setText(documentSnapshot.getString("Father's Name"));
//                father_occupation.setText(documentSnapshot.getString("Father's Occupation"));
//                father_phone_number.setText(documentSnapshot.getString("Father's Phone No."));
//                mothers_name.setText(documentSnapshot.getString("Mother's Name"));
//                mothers_phone_number.setText(documentSnapshot.getString("Mother's Phone No."));
//                mothers_occupation.setText(documentSnapshot.getString("Mother's Occupation"));
//            }
//        });
//        }
    }
    }
