package com.example.michaelcha.blackjack;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 *
 * Bradley Wilcox / Michael Cha
 * CSCI 4020
 * Assignment 1
 *
 */

public class game extends Activity implements View.OnClickListener {

    Button draw, reset, quit, stand, dollarOne, dollarTen, dollarOneHundred, dollarFiveHundred, deal, playNextHand;
    TextView dealerHand, playerHand, playerTotal, dealerTotal, greeting, purse, bet;
    ImageView ply1, ply2, ply3, ply4, ply5, dlr1, dlr2, dlr3, dlr4, dlr5;
    Blackjack blackjackGame;
    boolean saveOnPause = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game);

        // Initialize all the activity variables
        draw = (Button) findViewById(R.id.btnHit);
        draw.setOnClickListener(this);
        draw.setVisibility(View.INVISIBLE);

        stand = (Button) findViewById(R.id.btnStand);
        stand.setOnClickListener(this);
        stand.setVisibility(View.INVISIBLE);

        playNextHand = (Button) findViewById(R.id.btnNextHand);
        playNextHand.setOnClickListener(this);
        playNextHand.setVisibility(View.INVISIBLE);

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

        ply1 = (ImageView)findViewById(R.id.imageView);
        ply2 = (ImageView) findViewById(R.id.imageView2);
        ply3 = (ImageView) findViewById(R.id.imageView3);
        ply4 = (ImageView) findViewById(R.id.imageView4);
        ply5 = (ImageView)findViewById(R.id.imageView5);

        dlr1 = (ImageView) findViewById(R.id.imageView6);
        dlr2 = (ImageView) findViewById(R.id.imageView7);
        dlr3 = (ImageView) findViewById(R.id.imageView8);
        dlr4 = (ImageView) findViewById(R.id.imageView9);
        dlr5 = (ImageView) findViewById(R.id.imageView10);

        //Create the game object
        blackjackGame = new Blackjack(this);
        greeting.setText("Welcome to BlackJack, Place your bet");
        purse.setText("$" + blackjackGame.getPlayer().getMoney());

        // Setup display state for continued games

        // cards haven't been dealt
        if(blackjackGame.getPlayer().getHandTotal() == 0) {
            setDisplayForBet();
        // Hand is over
        }else if(blackjackGame.getDealer().getHandOver()){
            setDisplayForDeal();
            setDisplayForBet();
            setDisplayForStand();
            waitForNextHand();
        // Hand in progress
        }else{
            setDisplayForDeal();
            setDisplayForBet();
        }
    }

    public void onClick(View v)
    {

        // Player clicks draw button
        if(v==draw) {

            greeting.setText("");

            Card cardDealt = blackjackGame.getDeck().dealCard();
            blackjackGame.getPlayer().hitMe(cardDealt);

            setDisplayForPlayerCards();

            playerHand.setText("Player's hand: \n" + blackjackGame.getPlayer().getHandAsString(false));
            playerTotal.setText(blackjackGame.getPlayer().getHandTotal() + "");

            if (blackjackGame.didPlayerBust()) {
                Toast.makeText(this, "Player Busted - Dealer Wins", Toast.LENGTH_LONG).show();
                blackjackGame.getDealer().setHandOver(true);
                waitForNextHand();
            }

        }

        // Player clicks stand button, dealer's turn
        else if(v == stand) {

            blackjackGame.doDealersTurn(blackjackGame.getPlayer().getHandTotal());
            setDisplayForStand();

            if(blackjackGame.didDealerBust()) {
                Toast.makeText(this, "Dealer Busted - Player Wins", Toast.LENGTH_LONG).show();
                blackjackGame.getPlayer().rewardForWinningHand();

            }else if(blackjackGame.didPlayerBust()) {
                Toast.makeText(this, "Player Busted - Dealer Wins", Toast.LENGTH_LONG).show();
            }else if(blackjackGame.getDealer().getHandTotal() > blackjackGame.getPlayer().getHandTotal()){
                Toast.makeText(this, "Dealer Wins", Toast.LENGTH_LONG).show();
            }else if(blackjackGame.getDealer().getHandTotal() == blackjackGame.getPlayer().getHandTotal()) {
                Toast.makeText(this, "Push (tie)", Toast.LENGTH_LONG).show();
                blackjackGame.getPlayer().refund();
            }else{
                Toast.makeText(this, "Player Wins", Toast.LENGTH_LONG).show();
                blackjackGame.getPlayer().rewardForWinningHand();
            }

            waitForNextHand();
        }

        // poker chip button clicks
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

        // player clicks deal, after betting
        else if(v == deal){
            deal();
        }

        // hand over, player chooses to play another hand
        else if(v == playNextHand){
            setupHand();
        }

        // reset the entire game, deleting all data
        else if(v==reset)
        {
            saveOnPause = false;
            deleteFile(Player.PLAYER_DATA);
            deleteFile(Player.DEALER_DATA);
            deleteFile(Deck.DECK_DATA);

            startActivity(new Intent(this, game.class));
        }
        else if(v==quit)
        {
            startActivity(new Intent(this, MainActivity.class));
        }

    }

    private void placeBet(int betAmount){
        blackjackGame.getPlayer().placeBet(betAmount);
        setDisplayForBet();
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
        playNextHand.setVisibility(View.INVISIBLE);

        ply1.setImageResource(0);
        ply2.setImageResource(0);
        ply3.setImageResource(0);
        ply4.setImageResource(0);
        ply5.setImageResource(0);

        dlr1.setImageResource(0);
        dlr2.setImageResource(0);
        dlr3.setImageResource(0);
        dlr4.setImageResource(0);
        dlr5.setImageResource(0);

        dealerTotal.setText("");
        playerTotal.setText("");
        playerHand.setText("");
        dealerHand.setText("");
        bet.setText("Bet: $0");
        purse.setText("$" + blackjackGame.getPlayer().getMoney());

        blackjackGame.nextHand();
    }



    private void waitForNextHand(){
        dollarOne.setVisibility(View.INVISIBLE);
        dollarTen.setVisibility(View.INVISIBLE);
        dollarOneHundred.setVisibility(View.INVISIBLE);
        dollarFiveHundred.setVisibility(View.INVISIBLE);
        deal.setVisibility(View.INVISIBLE);
        greeting.setVisibility(View.INVISIBLE);

        draw.setVisibility(View.INVISIBLE);
        stand.setVisibility(View.INVISIBLE);

        playNextHand.setVisibility(View.VISIBLE);
    }

    // Start game, after betting
    private void deal(){

        if(blackjackGame.getPlayer().getBet() <= 0){
            Toast.makeText(this, "Please place a bet first", Toast.LENGTH_SHORT).show();
            return;
        }

        blackjackGame.startGame();

        setDisplayForDeal();
    }

    /*** Display methods, for setting up UI in different states ***/

    private void setDisplayForBet(){
        purse.setText("$" + blackjackGame.getPlayer().getMoney());
        bet.setText("Bet: $" + blackjackGame.getPlayer().getBet());
    }

    private void setDisplayForDeal(){
        dollarOne.setVisibility(View.INVISIBLE);
        dollarTen.setVisibility(View.INVISIBLE);
        dollarOneHundred.setVisibility(View.INVISIBLE);
        dollarFiveHundred.setVisibility(View.INVISIBLE);
        deal.setVisibility(View.INVISIBLE);
        greeting.setVisibility(View.INVISIBLE);

        draw.setVisibility(View.VISIBLE);
        stand.setVisibility(View.VISIBLE);

        setDisplayForPlayerCards();
        setDisplayForDealersCards(false);

        dealerHand.setText("Dealer's hand: " + blackjackGame.getDealer().getHandAsString(true));
        playerHand.setText("Player's hand: " + blackjackGame.getPlayer().getHandAsString(false));

        dealerTotal.setText("?");
        playerTotal.setText(blackjackGame.getPlayer().getHandTotal() + "");
    }

    private void setDisplayForStand(){
        setDisplayForDealersCards(true);
        dealerHand.setText("Dealer's hand: " + blackjackGame.getDealer().getHandAsString(false));
        dealerTotal.setText(blackjackGame.getDealer().getHandTotal() + "");
    }

    public void setDisplayForPlayerCards(){
        int i = 1;
        for(Card c : blackjackGame.getPlayer().getHand()){
            switch (i){
                case 1:
                    ply1.setImageResource(c.toImageId(this));
                    break;
                case 2:
                    ply2.setImageResource(c.toImageId(this));
                    break;
                case 3:
                    ply3.setImageResource(c.toImageId(this));
                    break;
                case 4:
                    ply4.setImageResource(c.toImageId(this));
                    break;
                case 5:
                    ply5.setImageResource(c.toImageId(this));
                    break;
            }

            i++;
        }
    }

    public void setDisplayForDealersCards(boolean showFirstCard){
        int i = 1;
        for(Card c : blackjackGame.getDealer().getHand()){
            switch (i){
                case 1:
                    if(showFirstCard)
                        dlr1.setImageResource(c.toImageId(this));
                    else
                        dlr1.setImageResource(R.drawable.back);
                    break;
                case 2:
                    dlr2.setImageResource(c.toImageId(this));
                    break;
                case 3:
                    dlr3.setImageResource(c.toImageId(this));
                    break;
                case 4:
                    dlr4.setImageResource(c.toImageId(this));
                    break;
                case 5:
                    dlr5.setImageResource(c.toImageId(this));
                    break;
            }

            i++;
        }
    }

    // Anytime the game is 'paused' and its not a reset, save all the data so the user can continue
    @Override
    protected void onPause()
    {
        super.onPause();

        if(saveOnPause) {
            blackjackGame.getPlayer().Save(this, false);
            blackjackGame.getDealer().Save(this, true);
            blackjackGame.getDeck().Save(this);
        }
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
    }

}
