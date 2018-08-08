package com.crypticsocket.hoggers;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

public class Main3Activity extends AppCompatActivity {

    /*TextView totalCost,qt;
    String names[],prices[];
    int priceOfItems=0;
    Adapter ad;
    Button qt1,qt2;*/
    int flag;
    private RecyclerView mFoodList;
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        /*qt1 = (Button) findViewById(R.id.add);
        totalCost = (TextView) findViewById(R.id.totalCost);
        qt2 = (Button) findViewById(R.id.sub);
        qt = (TextView) findViewById(R.id.qty);
        rv= (RecyclerView) findViewById(R.id.recyclerView);
//Toast.makeText(this, ""+flag, Toast.LENGTH_SHORT).show();  //delete after used. Just for verification
        setStrings();
        ad = new Adapter(this,names,prices);
        rv.setAdapter(ad);
        rv.setLayoutManager(new LinearLayoutManager(this));*/
        Intent intent = getIntent();
        flag = intent.getIntExtra("FLAG",flag);
        mFoodList = (RecyclerView) findViewById(R.id.recyclerView);
        mFoodList.setHasFixedSize(true);
        mFoodList.setLayoutManager(new LinearLayoutManager(this));
        if(flag==1)
        {
            mDatabase = FirebaseDatabase.getInstance().getReference().child("juice_Item");
        }
        else if(flag == 2)
        {
            mDatabase = FirebaseDatabase.getInstance().getReference().child("maddhuk_Item");
        }
        else if(flag == 3)
        {
            mDatabase = FirebaseDatabase.getInstance().getReference().child("dosa_Item");
        }
        else if(flag == 4)
        {
            mDatabase = FirebaseDatabase.getInstance().getReference().child("tea_Item");
        } //for different shops change this
        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (firebaseAuth.getCurrentUser() == null) {
                    Intent intent = new Intent(Main3Activity.this, MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);

                }
            }
        };
    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
        FirebaseRecyclerAdapter<Food,FoodViewHolder> FBRA = new FirebaseRecyclerAdapter<Food, FoodViewHolder>(
                Food.class,
                R.layout.menu,
                FoodViewHolder.class,
                mDatabase
        ) {
            @Override
            protected void populateViewHolder(FoodViewHolder viewHolder, Food model, int position) {
                viewHolder.setName(model.getName());
                viewHolder.setPrice(model.getPrice());
                viewHolder.setImage(getApplicationContext(),model.getImage());
                final String food_key = getRef(position).getKey().toString();
                viewHolder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent singleFoodActivity = new Intent (Main3Activity.this, singleFoodActivity.class);
                        singleFoodActivity.putExtra("FoodId",food_key);
                        singleFoodActivity.putExtra("FLAG",flag);
                        startActivity(singleFoodActivity);
                    }
                });
            }
        };
        mFoodList.setAdapter(FBRA);
    }

    public static class FoodViewHolder extends RecyclerView.ViewHolder{

        View mView;
        public FoodViewHolder(View itemView) {
            super(itemView);
            mView= itemView;
        }

        public void setName(String name){
            TextView food_name = (TextView) mView.findViewById(R.id.nameFood);
            food_name.setText(name);
        }

        public void setPrice(String price){
            TextView food_price = (TextView) mView.findViewById(R.id.priceFood);
            food_price.setText(price);
        }

        public void setImage(Context ctx, String image){
            ImageView food_image = (ImageView) mView.findViewById(R.id.foodImage);
            Picasso.with(ctx).load(image).into(food_image);
        }
    }

    /*private void setStrings() {
        if(flag == 1)
        {
            names = getResources().getStringArray(R.array.food_list_JuiceBerg);
            prices = getResources().getStringArray(R.array.prices_JuiceBerg);
            for(int i =0; i<prices.length;i++) {
                getTotalCost(prices[i]);
            }
        }

        else if(flag == 2)
        {
            names = getResources().getStringArray(R.array.food_list_MC);
            prices = getResources().getStringArray(R.array.prices_MC);
            for(int i =0; i<prices.length;i++) {
                getTotalCost(prices[i]);
            }
        }

        else if(flag == 3)
        {
            names = getResources().getStringArray(R.array.food_list_DC);
            prices = getResources().getStringArray(R.array.prices_DC);
            for(int i =0; i<prices.length;i++) {
                getTotalCost(prices[i]);
            }
        }

        else if(flag == 4)
        {
            names = getResources().getStringArray(R.array.food_list_ex1);
            prices = getResources().getStringArray(R.array.prices_ex1);
            for(int i =0; i<prices.length;i++) {
                getTotalCost(prices[i]);
            }
        }

        else if(flag == 5)
        {
            names = getResources().getStringArray(R.array.food_list_ex2);
            prices = getResources().getStringArray(R.array.prices_ex2);
            for(int i =0; i<prices.length;i++) {
                getTotalCost(prices[i]);
            }
        }
        //totalCost.setText(priceOfItems);
        //Toast.makeText(this, ""+priceOfItems, Toast.LENGTH_SHORT).show();
    }
    // to open payment activity after clicking button
    public void toPayment(View view){

        //String paymentToBeMade = totalCost.getText().toString();
        Intent intent= new Intent(this, Payment.class);
        startActivity(intent);

    }
// To calculate the price of the payment to be made using the prices array ,
    // intial quantity of every item to be zero ->
    // so total cost would include items with quantity non zero//

    public void getTotalCost(String itemPrices) {

        priceOfItems= priceOfItems+Integer.parseInt(itemPrices);
        //Toast.makeText(this, ""+priceOfItems+" "+itemPrices, Toast.LENGTH_SHORT).show();

    }

    /*public void changeQty(View view)
    {
        int i=0;
        switch (view.getId()){
            case R.id.add: {
                if (i < 10) {

                    i = Integer.parseInt(qt.getText().toString());
                    qt.setText(i + 1);
                } else
                    Toast.makeText(this, "Quantity too high", Toast.LENGTH_SHORT).show();
            }

            case R.id.sub: {
                if(i>0){
                    i = Integer.parseInt(qt.getText().toString());
                    qt.setText(i-1);
                }
                else
                    Toast.makeText(this, "Cannot go lower", Toast.LENGTH_SHORT).show();
            }

            default:
                Toast.makeText(this, "Unknown command", Toast.LENGTH_SHORT).show();
        }*/

}
