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

    Button draw, reset, quit, stand, dollarOne, dollarTen, dollarOneHundred, dollarFiveHundred, deal;
    TextView dealerHand, playerHand, playerTotal, dealerTotal, greeting, purse, bet;
    Blackjack blackjackGame;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game);

        draw = (Button) findViewById(R.id.btnHit);
        draw.setOnClickListener(this);
        draw.setVisibility(View.INVISIBLE);

        stand = (Button) findViewById(R.id.btnStand);
        stand.setOnClickListener(this);
        stand.setVisibility(View.INVISIBLE);

        deal = (Button) findViewById(R.id.btnDeal);
        deal.setOnClickListener(this);

        reset = (Button) findViewById(R.id.button4);
        reset.setOnClickListener(this);

        quit = (Button) findViewById(R.id.button5);
        quit.setOnClickListener(this);

        dollarOne = (Button) findViewById(R.id.btnMoneyOne);
        dollarOne.setOnClickListener(this);
        dollarTen = (Button) findViewById(R.id.btnMoney10);
        dollarTen.setOnClickListener(this);
        dollarOneHundred = (Button) findViewById(R.id.btnMoney100);
        dollarOneHundred.setOnClickListener(this);
        dollarFiveHundred = (Button) findViewById(R.id.btnMoney500);
        dollarFiveHundred.setOnClickListener(this);

        dealerHand = (TextView)findViewById(R.id.tvDealerHand);
        playerHand = (TextView)findViewById(R.id.tvPlayerHand);
        playerTotal = (TextView)findViewById(R.id.textView2);
        dealerTotal = (TextView)findViewById(R.id.textView3);
        greeting = (TextView)findViewById(R.id.textView4);
        purse = (TextView) findViewById(R.id.tvPurse);
        bet = (TextView) findViewById(R.id.tvBet);

        blackjackGame = new Blackjack();
        greeting.setText("Welcome to BlackJack, Place your bet");
        purse.setText("$" + blackjackGame.getPlayer().getMoney());

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

            if (blackjackGame.didPlayerBust()) {
                Toast.makeText(this, "Player Busted - Dealer Wins", Toast.LENGTH_LONG).show();
                setupHand();
            }
            else if (blackjackGame.didDealerBust()) {
                Toast.makeText(this, "Dealer Busted - Player Wins", Toast.LENGTH_LONG).show();
                setupHand();
            }

        }

        else if(v == stand)
        {
            playerHand.setText("");
            dealerHand.setText("");

            blackjackGame.doDealersTurn();
            dealerHand.setText("Dealer draws " + dealerHand.getText().toString());
            dealerTotal.setText(blackjackGame.getDealer().getHandTotal() + "");

            if(blackjackGame.didDealerBust()) {
                Toast.makeText(this, "Dealer Busted - Player Wins", Toast.LENGTH_LONG).show();
                blackjackGame.getPlayer().rewardForWinningHand();

            }else if(blackjackGame.didPlayerBust()) {
                Toast.makeText(this, "Player Busted - Dealer Wins", Toast.LENGTH_LONG).show();
            }else if(blackjackGame.getDealer().getHandTotal() > blackjackGame.getPlayer().getHandTotal()){
                Toast.makeText(this, "Dealer Wins", Toast.LENGTH_LONG).show();
            }else{
                Toast.makeText(this, "Player Wins", Toast.LENGTH_LONG).show();
                blackjackGame.getPlayer().rewardForWinningHand();
            }

            setupHand();
        }

        else if(v == dollarOne){
            placeBet(1);
        }
        else if(v == dollarTen){
            placeBet(10);
        }
        else if(v == dollarOneHundred){
            placeBet(100);
        }
        else if(v == dollarFiveHundred){
            placeBet(500);
        }

        else if(v == deal){
            deal();
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

    //add a deal button, make betting buttons invis, make hit/stand vis.... restart after hand
    private void placeBet(int betAmount){

        blackjackGame.getPlayer().placeBet(betAmount);
        purse.setText("$" + blackjackGame.getPlayer().getMoney());
        bet.setText("Bet: $" + blackjackGame.getPlayer().getBet());

    }

    private void setupHand(){
        dollarOne.setVisibility(View.VISIBLE);
        dollarTen.setVisibility(View.VISIBLE);
        dollarOneHundred.setVisibility(View.VISIBLE);
        dollarFiveHundred.setVisibility(View.VISIBLE);
        deal.setVisibility(View.VISIBLE);
        greeting.setVisibility(View.VISIBLE);

        draw.setVisibility(View.INVISIBLE);
        stand.setVisibility(View.INVISIBLE);

        dealerTotal.setText("");
        playerTotal.setText("");
        playerHand.setText("");
        dealerHand.setText("");
        bet.setText("Bet: $0");
        purse.setText("$" + blackjackGame.getPlayer().getMoney());

        blackjackGame.nextHand();
    }

    private void deal(){

        if(blackjackGame.getPlayer().getBet() <= 0){
            Toast.makeText(this, "Please place a bet first", Toast.LENGTH_SHORT).show();
            return;
        }

        dollarOne.setVisibility(View.INVISIBLE);
        dollarTen.setVisibility(View.INVISIBLE);
        dollarOneHundred.setVisibility(View.INVISIBLE);
        dollarFiveHundred.setVisibility(View.INVISIBLE);
        deal.setVisibility(View.INVISIBLE);
        greeting.setVisibility(View.INVISIBLE);

        draw.setVisibility(View.VISIBLE);
        stand.setVisibility(View.VISIBLE);

        blackjackGame.startGame();

        dealerHand.setText("Dealer dealt " + blackjackGame.getDealer().getHandAsString());
        playerHand.setText("Player dealt " + blackjackGame.getPlayer().getHandAsString());

        dealerTotal.setText(blackjackGame.getDealer().getHandTotal() + "");
        playerTotal.setText(blackjackGame.getPlayer().getHandTotal() + "");
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
