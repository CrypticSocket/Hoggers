package com.crypticsocket.hoggers;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Main2Activity extends AppCompatActivity {

    Button juice, madd, dosa, ex1;
    int flag=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        juice =  findViewById(R.id.b_JB);
        madd =  findViewById(R.id.b_MC);
        dosa =  findViewById(R.id.b_DC);
        ex1 =  findViewById(R.id.b_ex1);
        Intent intent = getIntent();
        flag = intent.getIntExtra("FLAG",flag);
        TextView textView = findViewById(R.id.restaurantChoices);
        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/PWYummyDonuts.ttf");
        textView.setTypeface(typeface);
        juice.setTypeface(typeface);
        madd.setTypeface(typeface);
        dosa.setTypeface(typeface);
        ex1.setTypeface(typeface);


    }

    public void toMenu(View view)
    {
        switch (view.getId()){
            case R.id.b_JB :
                flag=1;
                break;
            case R.id.b_MC :
                flag=2;
                break;
            case R.id.b_DC :
                flag=3;
                break;
            case R.id.b_ex1 :
                flag=4;
                break;
            default:
                Toast.makeText(this, "Unknown click", Toast.LENGTH_SHORT).show();
        }
        Intent intent = new Intent(this,Main3Activity.class);
        intent.putExtra("FLAG",flag);
        startActivity(intent);
    }
}
