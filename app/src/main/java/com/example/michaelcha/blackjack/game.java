package com.example.michaelcha.blackjack;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

/**
 * Created by MichaelCha on 1/27/2017.
 */

public class game extends Activity implements View.OnClickListener {

    Button draw, reset, quit, stand;
    TextView dealerHand, playerHand, playerTotal, dealerTotal;
    Blackjack blackjackGame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game);

        draw = (Button) findViewById(R.id.btnHit);
        draw.setOnClickListener(this);

        stand = (Button) findViewById(R.id.btnStand);
        stand.setOnClickListener(this);

        reset = (Button) findViewById(R.id.button4);
        reset.setOnClickListener(this);

        quit = (Button) findViewById(R.id.button5);
        quit.setOnClickListener(this);

        dealerHand = (TextView)findViewById(R.id.tvDealerHand);
        playerHand = (TextView)findViewById(R.id.tvPlayerHand);
        playerTotal = (TextView)findViewById(R.id.textView2);
        dealerTotal = (TextView)findViewById(R.id.textView3);

        blackjackGame = new Blackjack();

        // update this to be after betting
        blackjackGame.startGame();
        dealerHand.setText(blackjackGame.getDealer().getHandAsString());
        playerHand.setText(blackjackGame.getPlayer().getHandAsString());

        dealerTotal.setText(blackjackGame.getDealer().getHandTotal() + "");
        playerTotal.setText(blackjackGame.getPlayer().getHandTotal() + "");
    }

    public void onClick(View v)
    {

        if(v==draw)
        {
            Card cardDealt = blackjackGame.getDeck().dealCard();
            blackjackGame.getPlayer().hitMe(cardDealt);

            playerHand.setText(playerHand.getText().toString() + cardDealt.toString());
            playerTotal.setText(blackjackGame.getPlayer().getHandTotal() + "");

            if(blackjackGame.didPlayerBust())
                Toast.makeText(this, "Player Busted - Dealer Wins", Toast.LENGTH_SHORT).show();
        }
        else if(v == stand)
        {
            blackjackGame.doDealersTurn();
            dealerHand.setText(blackjackGame.getDealer().getHandAsString());
            dealerTotal.setText(blackjackGame.getDealer().getHandTotal() + "");

            if(blackjackGame.didDealerBust())
                Toast.makeText(this, "Dealer Busted - Player Wins", Toast.LENGTH_SHORT).show();
            else if(blackjackGame.getDealer().getHandTotal() > blackjackGame.getPlayer().getHandTotal())
                Toast.makeText(this, "Dealer Wins", Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(this, "Player Wins", Toast.LENGTH_SHORT).show();
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
