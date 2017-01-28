package com.example.michaelcha.blackjack;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Created by MichaelCha on 1/26/2017.
 */

public class credits extends Activity implements View.OnClickListener {

    private Button back;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate((savedInstanceState));
        setContentView(R.layout.credits);

        back = (Button) findViewById(R.id.backButt);
        back.setOnClickListener(this);
    }

    public void onClick(View v)
    {
        if(v==back)
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
