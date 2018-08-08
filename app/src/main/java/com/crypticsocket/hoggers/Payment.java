package com.crypticsocket.hoggers;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class Payment extends AppCompatActivity {

    private int flag=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        Intent intent = getIntent();
        flag= intent.getIntExtra("FLAG",flag);
    }

    public void showToast(View v)
    {
        Toast.makeText(this, "Payment option coming soon!", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(Payment.this, Main5Activity.class);
        intent.putExtra("FLAG",flag);
        startActivity(intent);
    }
}
