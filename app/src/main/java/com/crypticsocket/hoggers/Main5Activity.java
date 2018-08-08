package com.crypticsocket.hoggers;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class Main5Activity extends AppCompatActivity {

    private int flag=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main5);
        Intent intent = getIntent();
        flag = intent.getIntExtra("FLAG", flag);
    }

    public void backToList(View view)
    {
        Intent intent = new Intent(Main5Activity.this, Main3Activity.class);
        intent.putExtra("FLAG",flag);
        startActivity(intent);
    }

    public void mainMenu (View view)
    {
        Intent intent = new Intent(Main5Activity.this, Main2Activity.class);
        startActivity(intent);
    }

    public void logOut (View view)
    {
        Intent intent = new Intent(Main5Activity.this, MainActivity.class);
        Toast.makeText(this, "Log Out Successful", Toast.LENGTH_SHORT).show();
        startActivity(intent);
    }
}
