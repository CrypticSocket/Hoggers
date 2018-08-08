package com.crypticsocket.hoggers;

import android.content.DialogInterface;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    private TextView tv,tv1;
    private EditText user, pass;
    int i = 0,flag1=0,flag2=0;
    /*String a[]={"Saket","admin"};
    String b[]={"123","admin"};*/
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private static final int TIME_INTERVAL = 2000; // # milliseconds, desired time passed between two back presses.
    private long mBackPressed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        user = (EditText) findViewById(R.id.user);
        pass = (EditText) findViewById(R.id.pass);
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("users");
    }

    @Override
    public void onBackPressed() {
        if (mBackPressed + TIME_INTERVAL > System.currentTimeMillis())
        {
            super.onBackPressed();
            return;
        }
        else { Toast.makeText(getBaseContext(), "Tap back button in order to exit", Toast.LENGTH_SHORT).show(); }

        mBackPressed = System.currentTimeMillis();
    }

    public void changeText(View view)
    {
       /* checkpass();
        if(flag1==1&&flag2==1)
        {
            Intent intent = new Intent(this,Main2Activity.class);
            startActivity(intent);
        }
    }

    private void checkpass() {
        String a1,b1;
        a1=user.getText().toString();


        b1=pass.getText().toString();
        int x,j=0,pos=0;
        x = a.length;
        while(j<x)
        {
            tv.setText(""+j);
            if(a1.compareTo(a[j])==0)
            {
                tv.setText("username found");
                pos=j;
                flag1=1;
                break;
            }
            else
                tv.setText("not found");
            j++;
        }
        if(b1.compareTo(b[pos])==0) {
            tv1.setText("Password Matches");
            flag2=1;
        }
        else
            tv1.setText("Password Incorrect");*/
       String email = user.getText().toString().trim();
       String password = pass.getText().toString().trim();
       if(!TextUtils.isEmpty(email)&&!TextUtils.isEmpty(password))
       {
           mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
               @Override
               public void onComplete(@NonNull Task<AuthResult> task) {
                   if(task.isSuccessful())
                   {
                       checkUserExists();
                       Toast.makeText(MainActivity.this, "Logging In. Please Wait", Toast.LENGTH_SHORT).show();
                   }
                   else
                   {
                       Toast.makeText(MainActivity.this,"Invalid Password PLEASE TRY AGAIN",Toast.LENGTH_SHORT);
                   }
               }
           });
       }
       else
       {
           Toast.makeText(MainActivity.this,"Password Field Empty \n TRY AGAIN!",Toast.LENGTH_SHORT);
       }

    }

    public void checkUserExists()
    {
        final String user_id = mAuth.getCurrentUser().getUid();
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.hasChild(user_id))
                {
                    Intent menuIntent = new Intent(MainActivity.this, Main2Activity.class);
                    startActivity(menuIntent);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void signUp(View view)
    {
        Intent intent = new Intent(this, Main4Activity.class);
        startActivity(intent);
    }


}
