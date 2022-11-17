package com.example.priya_aman;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Student_Reg extends AppCompatActivity {
    EditText mail,pass,name_student,phone_student,confirmPassword_student,registration_id_student;
    CardView register;
    FirebaseAuth auth;
    FirebaseFirestore fstore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_student_reg);


        mail=findViewById(R.id.mail_student_regis);
        pass=findViewById(R.id.pass_student);
        register=findViewById(R.id.register);
        name_student=findViewById(R.id.name_student);
        phone_student=findViewById(R.id.phone_student);
        confirmPassword_student=findViewById(R.id.confirmPassword_student);
        registration_id_student=findViewById(R.id.registration_id_student);

        auth = FirebaseAuth.getInstance();
        fstore=FirebaseFirestore.getInstance();

        String validemail = "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +

                "\\@" +

                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +

                "(" +

                "\\." +

                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +

                ")+";


        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email,password,confirmPassword;
                email = mail.getText().toString();
                password=pass.getText().toString();
                confirmPassword=confirmPassword_student.getText().toString();
                Matcher matcher= Pattern.compile(validemail).matcher(email);

                if (TextUtils.isEmpty(name_student.getText().toString()))
                {
                    name_student.setError("Enter Name");
                }
                else if (TextUtils.isEmpty(registration_id_student.getText().toString()))
                {
                    registration_id_student.setError("Enter Registration ID");
                }
                else if (registration_id_student.getText().toString().length()!=11)
                {
                    registration_id_student.setError("Enter Valid Registration ID");
                }
                else if (TextUtils.isEmpty(phone_student.getText().toString()))
                {
                    phone_student.setError("Enter Phone number");
                }
                else if(phone_student.getText().toString().length()!=10 )
                {
                    phone_student.setError("Invalid Phone number");
                }
                else if (TextUtils.isEmpty(email))
                {
                    mail.setError("Enter email");
                }
                else if (TextUtils.isEmpty(password))
                {
                    pass.setError("Enter Password");
                }
                else if (password.length()<8)
                {
                    Toast.makeText(Student_Reg.this, "Password must be 8 characters", Toast.LENGTH_SHORT).show();
                }
                else if(!password.equals(confirmPassword))
                {
                    Toast.makeText(Student_Reg.this, "Password doesn't match", Toast.LENGTH_SHORT).show();
                }
                else if (matcher.matches()) {
                    auth.createUserWithEmailAndPassword(email,password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            Toast.makeText(Student_Reg.this, "Succesfully Registered", Toast.LENGTH_SHORT).show();
                            FirebaseUser user = auth.getCurrentUser();
                            String registration_id = registration_id_student.getText().toString();
                            DocumentReference df = fstore.collection("Students").document(auth.getCurrentUser().getUid());
                            Map<String,Object> userInfo = new HashMap<>();


                            userInfo.put("Name",name_student.getText().toString());
                            userInfo.put("Registration ID",registration_id);
                            userInfo.put("Email",email);
                            userInfo.put("Password",password);
                            userInfo.put("Phone number",phone_student.getText().toString());
                            userInfo.put("isStudent","1");
                            userInfo.put("isTeacher",null);
                            userInfo.put("isHOD",null);

                            df.set(userInfo);

                            startActivity(new Intent(getApplicationContext(),DashBoard.class));
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(Student_Reg.this, "Failed to register", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                else{
                    mail.setError("Invalid Email");
                }
            }
        });




    }
}