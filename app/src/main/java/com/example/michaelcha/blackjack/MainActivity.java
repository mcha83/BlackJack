package com.example.michaelcha.blackjack;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

/**
 *
 * Bradley Wilcox / Michael Cha
 * CSCI 4020
 * Assignment 1
 *
 */

public class MainActivity extends Activity implements View.OnClickListener{

    private Button mbutt1, mbutt2, mbutt3, mbutt4;

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

        mbutt4 = (Button) findViewById(R.id.btnContinue);
        mbutt4.setOnClickListener(this);
        mbutt4.setVisibility(View.INVISIBLE);

        // if this file is found, there is an existing game that can be continued
        String[] files = fileList();
        for (String file : files) {
            if (file.equals(Player.PLAYER_DATA)) {
                mbutt4.setVisibility(View.VISIBLE);
            }
        }
    }

    public void onClick(View v)
    {
        if(v==mbutt1) {
            //erase existing game data to start new one
            deleteFile(Player.PLAYER_DATA);
            deleteFile(Player.DEALER_DATA);
            deleteFile(Deck.DECK_DATA);

            startActivity(new Intent(this, game.class));
        }
        else if(v==mbutt2)
            startActivity(new Intent(this, howto.class));
        else if(v==mbutt3)
            startActivity(new Intent(this, credits.class));
        else if(v == mbutt4)
            startActivity(new Intent(this, game.class));

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

