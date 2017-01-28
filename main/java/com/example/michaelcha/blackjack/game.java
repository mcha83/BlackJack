package com.example.michaelcha.blackjack;

import android.app.Activity;
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
        setContentView(R.layout.howto);

        htButt = (Button) findViewById(R.id.htbutton);
        htButt.setOnClickListener(this);

    }


}
