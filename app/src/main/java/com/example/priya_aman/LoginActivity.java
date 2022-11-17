package com.example.priya_aman;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class LoginActivity extends AppCompatActivity {

    CardView login_main;
    TextView signup_in_login,forgot_password;
    Spinner spinner_user_select;
    EditText email_login,password_login,email_reset;
    FirebaseAuth auth;
    FirebaseFirestore fStore;
    LinearLayout reset_pass_btn;

    String [] user_selection = {"Select","Student","Teacher","HOD"};
    ArrayAdapter<String> adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);




        email_login = findViewById(R.id.email_login);
        password_login=findViewById(R.id.password_login);
        auth = FirebaseAuth.getInstance();
        fStore=FirebaseFirestore.getInstance();
        login_main = findViewById(R.id.login_main);
        signup_in_login = findViewById(R.id.signup_in_login);


        //forgot password
        forgot_password = findViewById(R.id.forgot_password);
        Dialog dialog = new Dialog(LoginActivity.this);
        dialog.setContentView(R.layout.activity_forgot_password);
        dialog.setCancelable(true);

        forgot_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.show();
                email_reset = dialog.findViewById(R.id.email_reset);
                reset_pass_btn = dialog.findViewById(R.id.reset_pass_btn);

                reset_pass_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String mail = email_reset.getText().toString();

                        if (mail.equals(""))
                        {
                            email_reset.setError("Enter Email");
                        }
                        else
                        {
                            auth.sendPasswordResetEmail(mail).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    dialog.dismiss();
                                    Toast.makeText(LoginActivity.this, "Successfully Sent", Toast.LENGTH_SHORT).show();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    dialog.dismiss();
                                    Toast.makeText(LoginActivity.this, "Invalid Email", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }
                });
            }
        });



        //Login


        login_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email,password;
                email = email_login.getText().toString();
                password=password_login.getText().toString();

                if (TextUtils.isEmpty(email))
                {
                    email_login.setError("Enter email");
                }
                else if (TextUtils.isEmpty(password))
                {
                    password_login.setError("Enter Password");
                }
                else if (password.length()<8)
                {
                    Toast.makeText(getApplicationContext(), "Password must be 8 characters", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    auth.signInWithEmailAndPassword(email,password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            Toast.makeText(LoginActivity.this, "logged in", Toast.LENGTH_SHORT).show();
                            checkUserAccesLevel(authResult.getUser().getUid());
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(LoginActivity.this, "", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });




        //Spinner


        spinner_user_select = findViewById(R.id.spinner_user_select);

        adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item,user_selection);
        spinner_user_select.setAdapter(adapter);
        signup_in_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                spinner_user_select.setVisibility(View.VISIBLE);

                spinner_user_select.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        String s = spinner_user_select.getSelectedItem().toString();

                        if(s.equals("Student"))
                        {
                            startActivity(new Intent(getApplicationContext(),Student_Reg.class));
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });
            }
        });

    }

    private void checkUserAccesLevel(String uid) {
        DocumentReference df = fStore.collection("Students").document(uid);
        df.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.getString("isStudent") != null)
                {
                    startActivity(new Intent(getApplicationContext(),DashBoard.class));
                    finish();
                }
            }
        });


        DocumentReference df2 = fStore.collection("Teachers").document(uid);
        df2.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.getString("isTeacher") != null)
                {
                    Toast.makeText(LoginActivity.this, "You are a teacher", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (FirebaseAuth.getInstance().getCurrentUser()!=null)
        {
            checkUserAccesLevel(auth.getUid());
        }
    }
}