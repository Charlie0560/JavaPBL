package com.example.priya_aman;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
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

public class Teacher_Reg extends AppCompatActivity {

    EditText mail_teacher,pass_teacher,code_teacher,name_teacher,confirm_pass_teacher;
    CardView register_teacher;
    FirebaseAuth auth;
    FirebaseFirestore fstore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_teacher_reg);

        mail_teacher=findViewById(R.id.mail_teacher);
        pass_teacher=findViewById(R.id.pass_teacher);
        code_teacher=findViewById(R.id.code_teacher);
        register_teacher=findViewById(R.id.register_teacher);
        confirm_pass_teacher=findViewById(R.id.confirm_pass_teacher);
        name_teacher=findViewById(R.id.name_teacher);

        auth = FirebaseAuth.getInstance();
        fstore=FirebaseFirestore.getInstance();

        String validemail = "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +

                "\\@" +

                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +

                "(" +

                "\\." +

                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +

                ")+";


        register_teacher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String code = "435435";
                String email = mail_teacher.getText().toString();
                String password = pass_teacher.getText().toString();
                String code_compare = code_teacher.getText().toString();
                String confirmPassword=confirm_pass_teacher.getText().toString();

                Matcher matcher= Pattern.compile(validemail).matcher(email);

                if (TextUtils.isEmpty(name_teacher.getText().toString()))
                {
                    name_teacher.setError("Enter Name");
                }
                else if (TextUtils.isEmpty(email))
                {
                    mail_teacher.setError("Enter email");
                }
                else if (TextUtils.isEmpty(password))
                {
                    pass_teacher.setError("Enter Password");
                }
                else if (password.length()<8)
                {
                    Toast.makeText(Teacher_Reg.this, "Password must be 8 characters", Toast.LENGTH_SHORT).show();
                }
                else if(!password.equals(confirmPassword))
                {
                    Toast.makeText(Teacher_Reg.this, "Password doesn't match", Toast.LENGTH_SHORT).show();
                }
                else if(matcher.matches())
                {
                    if(code.equals(code_compare))
                    {
                        auth.createUserWithEmailAndPassword(email,password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                            @Override
                            public void onSuccess(AuthResult authResult) {
                                Toast.makeText(Teacher_Reg.this, "Succesfully Registered", Toast.LENGTH_SHORT).show();
                                FirebaseUser user = auth.getCurrentUser();
                                DocumentReference df = fstore.collection("Teachers").document(user.getUid());
                                Map<String,Object> teacherInfo = new HashMap<>();
                                teacherInfo.put("Name",name_teacher.getText().toString());
                                teacherInfo.put("Email",email);
                                teacherInfo.put("Password",password);

                                df.set(teacherInfo);
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(Teacher_Reg.this, "Failed to register", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                    else{
                        code_teacher.setError("Invalid code");
                    }
                }
                else{
                    mail_teacher.setError("Invalid Email");
                }
            }
        });
    }
}