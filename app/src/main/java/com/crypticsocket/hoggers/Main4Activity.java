package com.crypticsocket.hoggers;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Main4Activity extends AppCompatActivity {

    private EditText email,pass,passCheck;
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    private TextView t2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);
        email = (EditText) findViewById(R.id.userSign);
        pass  = (EditText) findViewById(R.id.passSign);
        passCheck = (EditText) findViewById(R.id.passCheckSign);
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("users");
        t2 = (TextView) findViewById(R.id.t2);
    }

    public void signUpNow(View view)
    {
        final String email_text = email.getText().toString().trim();
        String pass_text = pass.getText().toString().trim();
        String passCheck_text = passCheck.getText().toString().trim();
        if(!TextUtils.isEmpty(email_text)&&!TextUtils.isEmpty(pass_text)&&!TextUtils.isEmpty(passCheck_text))
        {
            if(pass_text.length()<6)
            {
                t2.setText("Password should be of min 6 characters");
            }
            else
            {
                if(pass_text.equals(passCheck_text))
                {
                    mAuth.createUserWithEmailAndPassword(email_text,pass_text).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful())
                            {
                                String user_id = mAuth.getCurrentUser().getUid();
                                DatabaseReference currentUser = mDatabase.child(user_id);
                                currentUser.child("Name").setValue(email_text);
                            }
                        }
                    });
                    Intent intent = new Intent(this, MainActivity.class);
                    startActivity(intent);
                }
                else
                {

                    Toast.makeText(this, "Password do not match", Toast.LENGTH_SHORT).show();
                    t2.setText("Passwords do not match");
                }
            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        return;
    }
}
