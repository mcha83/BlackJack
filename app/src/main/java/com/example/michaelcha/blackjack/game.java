package com.example.michaelcha.blackjack;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Created by MichaelCha on 1/27/2017.
 */

public class game extends Activity implements View.OnClickListener {

    Button draw, reset, quit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game);

        draw = (Button) findViewById(R.id.button);
        draw.setOnClickListener(this);

        reset = (Button) findViewById(R.id.button4);
        reset.setOnClickListener(this);

        quit = (Button) findViewById(R.id.button5);
        quit.setOnClickListener(this);

    }

    public void onClick(View v)
    {

        if(v==draw)
        {

        }
        else if(v==reset)
        {
            startActivity(new Intent(this, game.class));
        }
        else if(v==quit)
        {
            startActivity(new Intent(this, MainActivity.class));
        }

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
