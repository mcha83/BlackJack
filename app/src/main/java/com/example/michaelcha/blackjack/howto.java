package com.example.michaelcha.blackjack;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 *
 * Bradley Wilcox / Michael Cha
 * CSCI 4020
 * Assignment 1
 *
 */

public class howto extends Activity implements View.OnClickListener {

    Button htButt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.howto);

        htButt = (Button) findViewById(R.id.htbutton);
        htButt.setOnClickListener(this);

    }

    public void onClick(View v)
    {
        if(v==htButt)
            startActivity(new Intent(this, MainActivity.class));

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
