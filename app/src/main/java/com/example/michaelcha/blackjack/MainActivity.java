package com.example.michaelcha.blackjack;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity implements View.OnClickListener{

    private Button mbutt1, mbutt2, mbutt3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mbutt1 = (Button) findViewById(R.id.button1);
        mbutt1.setOnClickListener(this);

        mbutt2 = (Button) findViewById(R.id.button2);
        mbutt2.setOnClickListener(this);

        mbutt3 = (Button) findViewById(R.id.button3);
        mbutt3.setOnClickListener(this);
    }

    public void onClick(View v)
    {
        if(v==mbutt1)
            startActivity(new Intent(this, MainActivity.class));
        else if(v==mbutt2)
            startActivity(new Intent(this, howto.class));
        else if(v==mbutt3)
            startActivity(new Intent(this, credits.class));

    }



    @Override
    protected void onPause()
    {
        super.onPause();
    }
    @Override
    protected void onDestroy()
    {
        super.onDestroy();
    }
}

