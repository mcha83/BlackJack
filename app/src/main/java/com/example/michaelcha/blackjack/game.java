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
    TextView dealerHand, playerHand, playerTotal, dealerTotal, greeting;
    Blackjack blackjackGame;
    Integer x = 52;

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
        greeting = (TextView)findViewById(R.id.textView4);

        blackjackGame = new Blackjack();
        greeting.setText("Welcome to BlackJack");

        // update this to be after betting
        blackjackGame.startGame();

        dealerHand.setText("Dealer dealt " + blackjackGame.getDealer().getHandAsString());
        playerHand.setText("Player dealt " + blackjackGame.getPlayer().getHandAsString());

        dealerTotal.setText(blackjackGame.getDealer().getHandTotal() + "");
        playerTotal.setText(blackjackGame.getPlayer().getHandTotal() + "");
    }

    public void onClick(View v)
    {

        if(v==draw) {

            greeting.setText("");
            playerHand.setText("");
            dealerHand.setText("");

            /*if(blackjackGame.getPlayer().getHandTotal() == 21)
                Toast.makeText(this, "Player Wins", Toast.LENGTH_SHORT).show();*/

            Card cardDealt = blackjackGame.getDeck().dealCard();
            blackjackGame.getPlayer().hitMe(cardDealt);

            playerHand.setText("Player dealt " + playerHand.getText().toString() + cardDealt.toString());
            playerTotal.setText(blackjackGame.getPlayer().getHandTotal() + "");

            if (blackjackGame.didPlayerBust())
                Toast.makeText(this, "Player Busted - Dealer Wins", Toast.LENGTH_LONG).show();
            else if (blackjackGame.didDealerBust())
                Toast.makeText(this, "Dealer Busted - Player Wins", Toast.LENGTH_LONG).show();

        }

        else if(v == stand)
        {
            playerHand.setText("");
            dealerHand.setText("");

            Card cardDealt3 = blackjackGame.getDeck().dealCard();
            blackjackGame.getDealer().hitMe(cardDealt3);

            dealerHand.setText("Dealer draws " + dealerHand.getText().toString() + cardDealt3.toString());
            dealerTotal.setText(blackjackGame.getDealer().getHandTotal() + "");


            if(blackjackGame.didDealerBust())
                Toast.makeText(this, "Dealer Busted - Player Wins", Toast.LENGTH_LONG).show();
            else if(blackjackGame.didPlayerBust())
                Toast.makeText(this, "Player Busted - Dealer Wins", Toast.LENGTH_LONG).show();
            else {
                dealerHand.setText("");
                greeting.setText("Player turn");

            }
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
