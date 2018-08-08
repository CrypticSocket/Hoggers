package com.crypticsocket.hoggers;

import android.content.Intent;
import android.media.Image;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

public class singleFoodActivity extends AppCompatActivity {

    private String food_key = null,food_name,food_price,food_image;
    private DatabaseReference mDatabase, userData, mRef;
    private TextView singleFoodTitle, singleFoodPrice;
    private ImageView singleFoodImage;
    private Button orderButton;
    private FirebaseAuth mAuth;
    private FirebaseUser current_user;
    String user;
    int flag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_food);
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser().getEmail();
        food_key = getIntent().getExtras().getString("FoodId");
        Intent intent = getIntent();
        flag= intent.getIntExtra("FLAG",flag);
        if(flag==1) {
            mDatabase = FirebaseDatabase.getInstance().getReference("juice_Item");
            mRef = FirebaseDatabase.getInstance().getReference().child("juice_Orders");
        }
        else if (flag==2) {
            mDatabase = FirebaseDatabase.getInstance().getReference("maddhuk_Item");
            mRef= FirebaseDatabase.getInstance().getReference().child("maddhuk_Orders");
        }
        else if (flag==4) {
            mDatabase = FirebaseDatabase.getInstance().getReference("tea_Item");
            mRef = FirebaseDatabase.getInstance().getReference().child("tea_Orders");
        }
        else if (flag==3) {
            mDatabase = FirebaseDatabase.getInstance().getReference("dosa_Item");
            mRef = FirebaseDatabase.getInstance().getReference().child("dosa_Orders");
        }

        singleFoodImage = (ImageView) findViewById(R.id.singleImageView);
        singleFoodTitle = (TextView) findViewById(R.id.singleTitle);
        singleFoodPrice = (TextView) findViewById(R.id.singlePrice);
        current_user = mAuth.getCurrentUser();
        userData = FirebaseDatabase.getInstance().getReference().child("users").child(current_user.getUid());
        mAuth.getCurrentUser().getEmail();

        mDatabase.child(food_key).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                food_name = (String) dataSnapshot.child("name").getValue();
                food_price = (String) dataSnapshot.child("price").getValue();
                food_image = (String) dataSnapshot.child("image").getValue();
                singleFoodTitle.setText(food_name);
                singleFoodPrice.setText("Rs. " + food_price);
                Picasso.with(singleFoodActivity.this).load(food_image).into(singleFoodImage);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void orderItemClicked(View view)
    {
        final DatabaseReference newOrder = mRef.push();
        userData.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                newOrder.child("itemname").setValue(food_name);
                newOrder.child("username").setValue(dataSnapshot.child("Name").getValue()).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(singleFoodActivity.this, "Order Placed", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(singleFoodActivity.this, Payment.class);
                        intent.putExtra("FLAG",flag);
                        startActivity(intent);
                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(singleFoodActivity.this, "Order could not be placed", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
