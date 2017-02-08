package com.example.michaelcha.blackjack;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;



public class game extends Activity implements View.OnClickListener {

    Button draw, reset, quit, stand, dollarOne, dollarTen, dollarOneHundred, dollarFiveHundred, deal, playNextHand;
    TextView dealerHand, playerHand, playerTotal, dealerTotal, greeting, purse, bet;
    ImageView ply1, ply2, ply3, ply4, ply5, dlr1, dlr2, dlr3, dlr4, dlr5;
    Blackjack blackjackGame;
    Integer imageRd = 1;

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
        ply1.setVisibility(View.INVISIBLE);
        ply2 = (ImageView) findViewById(R.id.imageView2);
        ply2.setVisibility(View.INVISIBLE);
        ply3 = (ImageView) findViewById(R.id.imageView3);
        ply3.setVisibility(View.INVISIBLE);
        ply4 = (ImageView) findViewById(R.id.imageView4);
        ply4.setVisibility(View.INVISIBLE);
        ply5 = (ImageView)findViewById(R.id.imageView5);
        ply5.setVisibility(View.INVISIBLE);

        dlr1 = (ImageView) findViewById(R.id.imageView6);
        dlr1.setVisibility(View.INVISIBLE);
        dlr2 = (ImageView) findViewById(R.id.imageView7);
        dlr2.setVisibility(View.INVISIBLE);
        dlr3 = (ImageView) findViewById(R.id.imageView8);
        dlr3.setVisibility(View.INVISIBLE);
        dlr4 = (ImageView) findViewById(R.id.imageView9);
        dlr4.setVisibility(View.INVISIBLE);
        dlr5 = (ImageView) findViewById(R.id.imageView10);
        dlr5.setVisibility(View.INVISIBLE);

        blackjackGame = new Blackjack();
        greeting.setText("Welcome to BlackJack, Place your bet");
        purse.setText("$" + blackjackGame.getPlayer().getMoney());

    }

    public void onClick(View v)
    {

        if(v==draw) {

            greeting.setText("");

            Card cardDealt = blackjackGame.getDeck().dealCard();
            blackjackGame.getPlayer().hitMe(cardDealt);
            cardDisp(cardDealt);

            playerHand.setText("Player's hand: \n" + blackjackGame.getPlayer().getHandAsString(false));
            playerTotal.setText(blackjackGame.getPlayer().getHandTotal() + "");

            if (blackjackGame.didPlayerBust()) {
                Toast.makeText(this, "Player Busted - Dealer Wins", Toast.LENGTH_LONG).show();
                waitForNextHand();
            }

        }

        else if(v == stand)
        {

            blackjackGame.doDealersTurn(blackjackGame.getDealer().getHandTotal());
            dealerHand.setText("Dealer's hand: \n" + blackjackGame.getDealer().getHandAsString(false));
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

            waitForNextHand();
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

        else if(v == playNextHand){
            setupHand();
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
        playNextHand.setVisibility(View.INVISIBLE);

        ply1.setVisibility(View.INVISIBLE);
        ply2.setVisibility(View.INVISIBLE);
        ply3.setVisibility(View.INVISIBLE);
        ply4.setVisibility(View.INVISIBLE);
        ply5.setVisibility(View.INVISIBLE);
        imageRd = 1;

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

        //blackjackGame.startGame();
        Card cardDealt1 = blackjackGame.getDeck().dealCard();
        blackjackGame.getPlayer().hitMe(cardDealt1);
        cardDisp(cardDealt1);

        Card cardDealt2 = blackjackGame.getDeck().dealCard();
        blackjackGame.getDealer().hitMe(cardDealt2);
        cardDisp(cardDealt2);

        Card cardDealt3 = blackjackGame.getDeck().dealCard();
        blackjackGame.getPlayer().hitMe(cardDealt3);
        cardDisp(cardDealt3);

        Card cardDealt4 = blackjackGame.getDeck().dealCard();
        blackjackGame.getDealer().hitMe(cardDealt4);
        cardDisp(cardDealt4);


        dealerHand.setText("Dealer's hand: " + blackjackGame.getDealer().getHandAsString(true));
        playerHand.setText("Player's hand: " + blackjackGame.getPlayer().getHandAsString(false));

        dealerTotal.setText("?");
        playerTotal.setText(blackjackGame.getPlayer().getHandTotal() + "");
    }

   public void cardDisp(Card C) {

        if (C.getSuit() == Card.Suit.Clubs && C.getNumber() == 1) {
            if (imageRd == 1) {
                ply1.setImageResource(R.drawable.c01);
                ply1.setVisibility(View.VISIBLE);
                imageRd = imageRd + 1;
            } else if (imageRd == 2) {
                ply2.setImageResource(R.drawable.c01);
                ply2.setVisibility(View.VISIBLE);
                imageRd = imageRd + 1;
            } else if (imageRd == 3) {
                ply3.setImageResource(R.drawable.c01);
                ply3.setVisibility(View.VISIBLE);
                imageRd = imageRd + 1;
            } else if (imageRd == 4) {
                ply4.setImageResource(R.drawable.c01);
                ply4.setVisibility(View.VISIBLE);
                imageRd = imageRd + 1;
            } else if (imageRd == 5) {
                ply5.setImageResource(R.drawable.c01);
                ply5.setVisibility(View.VISIBLE);
                imageRd = imageRd + 1;
            }

        } else if (C.getSuit() == Card.Suit.Clubs && C.getNumber() == 2) {
            if (imageRd == 1) {
                ply1.setImageResource(R.drawable.c02);
                ply1.setVisibility(View.VISIBLE);
                imageRd = imageRd + 1;
            } else if (imageRd == 2) {
                ply2.setImageResource(R.drawable.c02);
                ply2.setVisibility(View.VISIBLE);
                imageRd = imageRd + 1;
            } else if (imageRd == 3) {
                ply3.setImageResource(R.drawable.c02);
                ply3.setVisibility(View.VISIBLE);
                imageRd = imageRd + 1;
            } else if (imageRd == 4) {
                ply4.setImageResource(R.drawable.c02);
                ply4.setVisibility(View.VISIBLE);
                imageRd = imageRd + 1;
            } else if (imageRd == 5) {
                ply5.setImageResource(R.drawable.c02);
                ply5.setVisibility(View.VISIBLE);
                imageRd = imageRd + 1;
            }

        } else if (C.getSuit() == Card.Suit.Clubs && C.getNumber() == 3) {
            if (imageRd == 1) {
                ply1.setImageResource(R.drawable.c03);
                ply1.setVisibility(View.VISIBLE);
                imageRd = imageRd + 1;
            } else if (imageRd == 2) {
                ply2.setImageResource(R.drawable.c03);
                ply2.setVisibility(View.VISIBLE);
                imageRd = imageRd + 1;
            } else if (imageRd == 3) {
                ply3.setImageResource(R.drawable.c03);
                ply3.setVisibility(View.VISIBLE);
                imageRd = imageRd + 1;
            } else if (imageRd == 4) {
                ply4.setImageResource(R.drawable.c03);
                ply4.setVisibility(View.VISIBLE);
                imageRd = imageRd + 1;
            } else if (imageRd == 5) {
                ply5.setImageResource(R.drawable.c03);
                ply5.setVisibility(View.VISIBLE);
                imageRd = imageRd + 1;
            }

        } else if (C.getSuit() == Card.Suit.Clubs && C.getNumber() == 4) {
            if (imageRd == 1) {
                ply1.setImageResource(R.drawable.c04);
                ply1.setVisibility(View.VISIBLE);
                imageRd = imageRd + 1;
            } else if (imageRd == 2) {
                ply2.setImageResource(R.drawable.c04);
                ply2.setVisibility(View.VISIBLE);
                imageRd = imageRd + 1;
            } else if (imageRd == 3) {
                ply3.setImageResource(R.drawable.c04);
                ply3.setVisibility(View.VISIBLE);
                imageRd = imageRd + 1;
            } else if (imageRd == 4) {
                ply4.setImageResource(R.drawable.c04);
                ply4.setVisibility(View.VISIBLE);
                imageRd = imageRd + 1;
            } else if (imageRd == 5) {
                ply5.setImageResource(R.drawable.c04);
                ply5.setVisibility(View.VISIBLE);
                imageRd = imageRd + 1;
            }

        } else if (C.getSuit() == Card.Suit.Clubs && C.getNumber() == 5) {
            if (imageRd == 1) {
                ply1.setImageResource(R.drawable.c05);
                ply1.setVisibility(View.VISIBLE);
                imageRd = imageRd + 1;
            } else if (imageRd == 2) {
                ply2.setImageResource(R.drawable.c05);
                ply2.setVisibility(View.VISIBLE);
                imageRd = imageRd + 1;
            } else if (imageRd == 3) {
                ply3.setImageResource(R.drawable.c05);
                ply3.setVisibility(View.VISIBLE);
                imageRd = imageRd + 1;
            } else if (imageRd == 4) {
                ply4.setImageResource(R.drawable.c05);
                ply4.setVisibility(View.VISIBLE);
                imageRd = imageRd + 1;
            } else if (imageRd == 5) {
                ply5.setImageResource(R.drawable.c05);
                ply5.setVisibility(View.VISIBLE);
                imageRd = imageRd + 1;
            }

        } else if (C.getSuit().equals(Card.Suit.Clubs)  && C.getNumber() == 6) {
            if (imageRd == 1) {
                ply1.setImageResource(R.drawable.c06);
                ply1.setVisibility(View.VISIBLE);
                imageRd = imageRd + 1;
            } else if (imageRd == 2) {
                ply2.setImageResource(R.drawable.c06);
                ply2.setVisibility(View.VISIBLE);
                imageRd = imageRd + 1;
            } else if (imageRd == 3) {
                ply3.setImageResource(R.drawable.c06);
                ply3.setVisibility(View.VISIBLE);
                imageRd = imageRd + 1;
            } else if (imageRd == 4) {
                ply4.setImageResource(R.drawable.c06);
                ply4.setVisibility(View.VISIBLE);
                imageRd = imageRd + 1;
            } else if (imageRd == 5) {
                ply5.setImageResource(R.drawable.c06);
                ply5.setVisibility(View.VISIBLE);
                imageRd = imageRd + 1;
            }

        } else if (C.getSuit() == Card.Suit.Clubs && C.getNumber() == 7) {
            if (imageRd == 1) {
                ply1.setImageResource(R.drawable.c07);
                ply1.setVisibility(View.VISIBLE);
                imageRd = imageRd + 1;
            } else if (imageRd == 2) {
                ply2.setImageResource(R.drawable.c07);
                ply2.setVisibility(View.VISIBLE);
                imageRd = imageRd + 1;
            } else if (imageRd == 3) {
                ply3.setImageResource(R.drawable.c07);
                ply3.setVisibility(View.VISIBLE);
                imageRd = imageRd + 1;
            } else if (imageRd == 4) {
                ply4.setImageResource(R.drawable.c07);
                ply4.setVisibility(View.VISIBLE);
                imageRd = imageRd + 1;
            } else if (imageRd == 5) {
                ply5.setImageResource(R.drawable.c07);
                ply5.setVisibility(View.VISIBLE);
                imageRd = imageRd + 1;
            }

        } else if (C.getSuit() == Card.Suit.Clubs && C.getNumber() == 8) {
            if (imageRd == 1) {
                ply1.setImageResource(R.drawable.c08);
                ply1.setVisibility(View.VISIBLE);
                imageRd = imageRd + 1;
            } else if (imageRd == 2) {
                ply2.setImageResource(R.drawable.c08);
                ply2.setVisibility(View.VISIBLE);
                imageRd = imageRd + 1;
            } else if (imageRd == 3) {
                ply3.setImageResource(R.drawable.c08);
                ply3.setVisibility(View.VISIBLE);
                imageRd = imageRd + 1;
            } else if (imageRd == 4) {
                ply4.setImageResource(R.drawable.c08);
                ply4.setVisibility(View.VISIBLE);
                imageRd = imageRd + 1;
            } else if (imageRd == 5) {
                ply5.setImageResource(R.drawable.c08);
                ply5.setVisibility(View.VISIBLE);
                imageRd = imageRd + 1;
            }

        } else if (C.getSuit() == Card.Suit.Clubs && C.getNumber() == 9) {
            if (imageRd == 1) {
                ply1.setImageResource(R.drawable.c09);
                ply1.setVisibility(View.VISIBLE);
                imageRd = imageRd + 1;
            } else if (imageRd == 2) {
                ply2.setImageResource(R.drawable.c09);
                ply2.setVisibility(View.VISIBLE);
                imageRd = imageRd + 1;
            } else if (imageRd == 3) {
                ply3.setImageResource(R.drawable.c09);
                ply3.setVisibility(View.VISIBLE);
                imageRd = imageRd + 1;
            } else if (imageRd == 4) {
                ply4.setImageResource(R.drawable.c09);
                ply4.setVisibility(View.VISIBLE);
                imageRd = imageRd + 1;
            } else if (imageRd == 5) {
                ply5.setImageResource(R.drawable.c09);
                ply5.setVisibility(View.VISIBLE);
                imageRd = imageRd + 1;
            }

        } else if (C.getSuit() == Card.Suit.Clubs && C.getNumber() == 10) {
            if (imageRd == 1) {
                ply1.setImageResource(R.drawable.c10);
                ply1.setVisibility(View.VISIBLE);
                imageRd = imageRd + 1;
            } else if (imageRd == 2) {
                ply2.setImageResource(R.drawable.c10);
                ply2.setVisibility(View.VISIBLE);
                imageRd = imageRd + 1;
            } else if (imageRd == 3) {
                ply3.setImageResource(R.drawable.c10);
                ply3.setVisibility(View.VISIBLE);
                imageRd = imageRd + 1;
            } else if (imageRd == 4) {
                ply4.setImageResource(R.drawable.c10);
                ply4.setVisibility(View.VISIBLE);
                imageRd = imageRd + 1;
            } else if (imageRd == 5) {
                ply5.setImageResource(R.drawable.c10);
                ply5.setVisibility(View.VISIBLE);
                imageRd = imageRd + 1;
            }

        } else if (C.getSuit() == Card.Suit.Clubs && C.getNumber() == 11) {
            if (imageRd == 1) {
                ply1.setImageResource(R.drawable.c11);
                ply1.setVisibility(View.VISIBLE);
                imageRd = imageRd + 1;
            } else if (imageRd == 2) {
                ply2.setImageResource(R.drawable.c11);
                ply2.setVisibility(View.VISIBLE);
                imageRd = imageRd + 1;
            } else if (imageRd == 3) {
                ply3.setImageResource(R.drawable.c11);
                ply3.setVisibility(View.VISIBLE);
                imageRd = imageRd + 1;
            } else if (imageRd == 4) {
                ply4.setImageResource(R.drawable.c11);
                ply4.setVisibility(View.VISIBLE);
                imageRd = imageRd + 1;
            } else if (imageRd == 5) {
                ply5.setImageResource(R.drawable.c11);
                ply5.setVisibility(View.VISIBLE);
                imageRd = imageRd + 1;
            }

        } else if (C.getSuit() == Card.Suit.Clubs && C.getNumber() == 12) {
            if (imageRd == 1) {
                ply1.setImageResource(R.drawable.c12);
                ply1.setVisibility(View.VISIBLE);
                imageRd = imageRd + 1;
            } else if (imageRd == 2) {
                ply2.setImageResource(R.drawable.c12);
                ply2.setVisibility(View.VISIBLE);
                imageRd = imageRd + 1;
            } else if (imageRd == 3) {
                ply3.setImageResource(R.drawable.c12);
                ply3.setVisibility(View.VISIBLE);
                imageRd = imageRd + 1;
            } else if (imageRd == 4) {
                ply4.setImageResource(R.drawable.c12);
                ply4.setVisibility(View.VISIBLE);
                imageRd = imageRd + 1;
            } else if (imageRd == 5) {
                ply5.setImageResource(R.drawable.c12);
                ply5.setVisibility(View.VISIBLE);
                imageRd = imageRd + 1;
            }

        } else if (C.getSuit() == Card.Suit.Clubs && C.getNumber() == 13) {
            if (imageRd == 1) {
                ply1.setImageResource(R.drawable.c13);
                ply1.setVisibility(View.VISIBLE);
                imageRd = imageRd + 1;
            } else if (imageRd == 2) {
                ply2.setImageResource(R.drawable.c13);
                ply2.setVisibility(View.VISIBLE);
                imageRd = imageRd + 1;
            } else if (imageRd == 3) {
                ply3.setImageResource(R.drawable.c13);
                ply3.setVisibility(View.VISIBLE);
                imageRd = imageRd + 1;
            } else if (imageRd == 4) {
                ply4.setImageResource(R.drawable.c13);
                ply4.setVisibility(View.VISIBLE);
                imageRd = imageRd + 1;
            } else if (imageRd == 5) {
                ply5.setImageResource(R.drawable.c13);
                ply5.setVisibility(View.VISIBLE);
                imageRd = imageRd + 1;
            }

        } else if (C.getSuit() == Card.Suit.Diamonds && C.getNumber() == 1) {
            if (imageRd == 1) {
                ply1.setImageResource(R.drawable.d01);
                ply1.setVisibility(View.VISIBLE);
                imageRd = imageRd + 1;
            } else if (imageRd == 2) {
                ply2.setImageResource(R.drawable.d01);
                ply2.setVisibility(View.VISIBLE);
                imageRd = imageRd + 1;
            } else if (imageRd == 3) {
                ply3.setImageResource(R.drawable.d01);
                ply3.setVisibility(View.VISIBLE);
                imageRd = imageRd + 1;
            } else if (imageRd == 4) {
                ply4.setImageResource(R.drawable.d01);
                ply4.setVisibility(View.VISIBLE);
                imageRd = imageRd + 1;
            } else if (imageRd == 5) {
                ply5.setImageResource(R.drawable.d01);
                ply5.setVisibility(View.VISIBLE);
                imageRd = imageRd + 1;
            }

        } else if (C.getSuit() == Card.Suit.Diamonds && C.getNumber() == 2) {
            if (imageRd == 1) {
                ply1.setImageResource(R.drawable.d02);
                ply1.setVisibility(View.VISIBLE);
                imageRd = imageRd + 1;
            } else if (imageRd == 2) {
                ply2.setImageResource(R.drawable.d02);
                ply2.setVisibility(View.VISIBLE);
                imageRd = imageRd + 1;
            } else if (imageRd == 3) {
                ply3.setImageResource(R.drawable.d02);
                ply3.setVisibility(View.VISIBLE);
                imageRd = imageRd + 1;
            } else if (imageRd == 4) {
                ply4.setImageResource(R.drawable.d02);
                ply4.setVisibility(View.VISIBLE);
                imageRd = imageRd + 1;
            } else if (imageRd == 5) {
                ply5.setImageResource(R.drawable.d02);
                ply5.setVisibility(View.VISIBLE);
                imageRd = imageRd + 1;
            }

        } else if (C.getSuit() == Card.Suit.Diamonds && C.getNumber() == 3) {
            if (imageRd == 1) {
                ply1.setImageResource(R.drawable.d03);
                ply1.setVisibility(View.VISIBLE);
                imageRd = imageRd + 1;
            } else if (imageRd == 2) {
                ply2.setImageResource(R.drawable.d03);
                ply2.setVisibility(View.VISIBLE);
                imageRd = imageRd + 1;
            } else if (imageRd == 3) {
                ply3.setImageResource(R.drawable.d03);
                ply3.setVisibility(View.VISIBLE);
                imageRd = imageRd + 1;
            } else if (imageRd == 4) {
                ply4.setImageResource(R.drawable.d03);
                ply4.setVisibility(View.VISIBLE);
                imageRd = imageRd + 1;
            } else if (imageRd == 5) {
                ply5.setImageResource(R.drawable.d03);
                ply5.setVisibility(View.VISIBLE);
                imageRd = imageRd + 1;
            }

        } else if (C.getSuit() == Card.Suit.Diamonds && C.getNumber() == 4) {
            if (imageRd == 1) {
                ply1.setImageResource(R.drawable.d04);
                ply1.setVisibility(View.VISIBLE);
                imageRd = imageRd + 1;
            } else if (imageRd == 2) {
                ply2.setImageResource(R.drawable.d04);
                ply2.setVisibility(View.VISIBLE);
                imageRd = imageRd + 1;
            } else if (imageRd == 3) {
                ply3.setImageResource(R.drawable.d04);
                ply3.setVisibility(View.VISIBLE);
                imageRd = imageRd + 1;
            } else if (imageRd == 4) {
                ply4.setImageResource(R.drawable.d04);
                ply4.setVisibility(View.VISIBLE);
                imageRd = imageRd + 1;
            } else if (imageRd == 5) {
                ply5.setImageResource(R.drawable.d04);
                ply5.setVisibility(View.VISIBLE);
                imageRd = imageRd + 1;
            }

        } else if (C.getSuit() == Card.Suit.Diamonds && C.getNumber() == 5) {
            if (imageRd == 1) {
                ply1.setImageResource(R.drawable.d05);
                ply1.setVisibility(View.VISIBLE);
                imageRd = imageRd + 1;
            } else if (imageRd == 2) {
                ply2.setImageResource(R.drawable.d05);
                ply2.setVisibility(View.VISIBLE);
                imageRd = imageRd + 1;
            } else if (imageRd == 3) {
                ply3.setImageResource(R.drawable.d05);
                ply3.setVisibility(View.VISIBLE);
                imageRd = imageRd + 1;
            } else if (imageRd == 4) {
                ply4.setImageResource(R.drawable.d05);
                ply4.setVisibility(View.VISIBLE);
                imageRd = imageRd + 1;
            } else if (imageRd == 5) {
                ply5.setImageResource(R.drawable.d05);
                ply5.setVisibility(View.VISIBLE);
                imageRd = imageRd + 1;
            }

        } else if (C.getSuit() == Card.Suit.Diamonds && C.getNumber() == 6) {
            if (imageRd == 1) {
                ply1.setImageResource(R.drawable.d06);
                ply1.setVisibility(View.VISIBLE);
                imageRd = imageRd + 1;
            } else if (imageRd == 2) {
                ply2.setImageResource(R.drawable.d06);
                ply2.setVisibility(View.VISIBLE);
                imageRd = imageRd + 1;
            } else if (imageRd == 3) {
                ply3.setImageResource(R.drawable.d06);
                ply3.setVisibility(View.VISIBLE);
                imageRd = imageRd + 1;
            } else if (imageRd == 4) {
                ply4.setImageResource(R.drawable.d06);
                ply4.setVisibility(View.VISIBLE);
                imageRd = imageRd + 1;
            } else if (imageRd == 5) {
                ply5.setImageResource(R.drawable.d06);
                ply5.setVisibility(View.VISIBLE);
                imageRd = imageRd + 1;
            }

        } else if (C.getSuit() == Card.Suit.Diamonds && C.getNumber() == 7) {
            if (imageRd == 1) {
                ply1.setImageResource(R.drawable.d07);
                ply1.setVisibility(View.VISIBLE);
                imageRd = imageRd + 1;
            } else if (imageRd == 2) {
                ply2.setImageResource(R.drawable.d07);
                ply2.setVisibility(View.VISIBLE);
                imageRd = imageRd + 1;
            } else if (imageRd == 3) {
                ply3.setImageResource(R.drawable.d07);
                ply3.setVisibility(View.VISIBLE);
                imageRd = imageRd + 1;
            } else if (imageRd == 4) {
                ply4.setImageResource(R.drawable.d07);
                ply4.setVisibility(View.VISIBLE);
                imageRd = imageRd + 1;
            } else if (imageRd == 5) {
                ply5.setImageResource(R.drawable.d07);
                ply5.setVisibility(View.VISIBLE);
                imageRd = imageRd + 1;
            }

        } else if (C.getSuit() == Card.Suit.Diamonds && C.getNumber() == 8) {
            if (imageRd == 1) {
                ply1.setImageResource(R.drawable.d08);
                ply1.setVisibility(View.VISIBLE);
                imageRd = imageRd + 1;
            } else if (imageRd == 2) {
                ply2.setImageResource(R.drawable.d08);
                ply2.setVisibility(View.VISIBLE);
                imageRd = imageRd + 1;
            } else if (imageRd == 3) {
                ply3.setImageResource(R.drawable.d08);
                ply3.setVisibility(View.VISIBLE);
                imageRd = imageRd + 1;
            } else if (imageRd == 4) {
                ply4.setImageResource(R.drawable.d08);
                ply4.setVisibility(View.VISIBLE);
                imageRd = imageRd + 1;
            } else if (imageRd == 5) {
                ply5.setImageResource(R.drawable.d08);
                ply5.setVisibility(View.VISIBLE);
                imageRd = imageRd + 1;
            }

        } else if (C.getSuit() == Card.Suit.Diamonds && C.getNumber() == 9) {
            if (imageRd == 1) {
                ply1.setImageResource(R.drawable.d09);
                ply1.setVisibility(View.VISIBLE);
                imageRd = imageRd + 1;
            } else if (imageRd == 2) {
                ply2.setImageResource(R.drawable.d09);
                ply2.setVisibility(View.VISIBLE);
                imageRd = imageRd + 1;
            } else if (imageRd == 3) {
                ply3.setImageResource(R.drawable.d09);
                ply3.setVisibility(View.VISIBLE);
                imageRd = imageRd + 1;
            } else if (imageRd == 4) {
                ply4.setImageResource(R.drawable.d09);
                ply4.setVisibility(View.VISIBLE);
                imageRd = imageRd + 1;
            } else if (imageRd == 5) {
                ply5.setImageResource(R.drawable.d09);
                ply5.setVisibility(View.VISIBLE);
                imageRd = imageRd + 1;
            }

        } else if (C.getSuit() == Card.Suit.Diamonds && C.getNumber() == 10) {
            if (imageRd == 1) {
                ply1.setImageResource(R.drawable.d10);
                ply1.setVisibility(View.VISIBLE);
                imageRd = imageRd + 1;
            } else if (imageRd == 2) {
                ply2.setImageResource(R.drawable.d10);
                ply2.setVisibility(View.VISIBLE);
                imageRd = imageRd + 1;
            } else if (imageRd == 3) {
                ply3.setImageResource(R.drawable.d10);
                ply3.setVisibility(View.VISIBLE);
                imageRd = imageRd + 1;
            } else if (imageRd == 4) {
                ply4.setImageResource(R.drawable.d10);
                ply4.setVisibility(View.VISIBLE);
                imageRd = imageRd + 1;
            } else if (imageRd == 5) {
                ply5.setImageResource(R.drawable.d10);
                ply5.setVisibility(View.VISIBLE);
                imageRd = imageRd + 1;
            }

        } else if (C.getSuit() == Card.Suit.Diamonds && C.getNumber() == 11) {
            if (imageRd == 1) {
                ply1.setImageResource(R.drawable.d11);
                ply1.setVisibility(View.VISIBLE);
                imageRd = imageRd + 1;
            } else if (imageRd == 2) {
                ply2.setImageResource(R.drawable.d11);
                ply2.setVisibility(View.VISIBLE);
                imageRd = imageRd + 1;
            } else if (imageRd == 3) {
                ply3.setImageResource(R.drawable.d11);
                ply3.setVisibility(View.VISIBLE);
                imageRd = imageRd + 1;
            } else if (imageRd == 4) {
                ply4.setImageResource(R.drawable.d11);
                ply4.setVisibility(View.VISIBLE);
                imageRd = imageRd + 1;
            } else if (imageRd == 5) {
                ply5.setImageResource(R.drawable.d11);
                ply5.setVisibility(View.VISIBLE);
                imageRd = imageRd + 1;
            }

        } else if (C.getSuit() == Card.Suit.Diamonds && C.getNumber() == 12) {
            if (imageRd == 1) {
                ply1.setImageResource(R.drawable.d12);
                ply1.setVisibility(View.VISIBLE);
                imageRd = imageRd + 1;
            } else if (imageRd == 2) {
                ply2.setImageResource(R.drawable.d12);
                ply2.setVisibility(View.VISIBLE);
                imageRd = imageRd + 1;
            } else if (imageRd == 3) {
                ply3.setImageResource(R.drawable.d12);
                ply3.setVisibility(View.VISIBLE);
                imageRd = imageRd + 1;
            } else if (imageRd == 4) {
                ply4.setImageResource(R.drawable.d12);
                ply4.setVisibility(View.VISIBLE);
                imageRd = imageRd + 1;
            } else if (imageRd == 5) {
                ply5.setImageResource(R.drawable.d12);
                ply5.setVisibility(View.VISIBLE);
                imageRd = imageRd + 1;
            }

        } else if (C.getSuit() == Card.Suit.Diamonds && C.getNumber() == 13) {
            if (imageRd == 1) {
                ply1.setImageResource(R.drawable.d13);
                ply1.setVisibility(View.VISIBLE);
                imageRd = imageRd + 1;
            } else if (imageRd == 2) {
                ply2.setImageResource(R.drawable.d13);
                ply2.setVisibility(View.VISIBLE);
                imageRd = imageRd + 1;
            } else if (imageRd == 3) {
                ply3.setImageResource(R.drawable.d13);
                ply3.setVisibility(View.VISIBLE);
                imageRd = imageRd + 1;
            } else if (imageRd == 4) {
                ply4.setImageResource(R.drawable.d13);
                ply4.setVisibility(View.VISIBLE);
                imageRd = imageRd + 1;
            } else if (imageRd == 5) {
                ply5.setImageResource(R.drawable.d13);
                ply5.setVisibility(View.VISIBLE);
                imageRd = imageRd + 1;
            }

        } else if (C.getSuit() == Card.Suit.Hearts && C.getNumber() == 1) {
            if (imageRd == 1) {
                ply1.setImageResource(R.drawable.h01);
                ply1.setVisibility(View.VISIBLE);
                imageRd = imageRd + 1;
            } else if (imageRd == 2) {
                ply2.setImageResource(R.drawable.h01);
                ply2.setVisibility(View.VISIBLE);
                imageRd = imageRd + 1;
            } else if (imageRd == 3) {
                ply3.setImageResource(R.drawable.h01);
                ply3.setVisibility(View.VISIBLE);
                imageRd = imageRd + 1;
            } else if (imageRd == 4) {
                ply4.setImageResource(R.drawable.h01);
                ply4.setVisibility(View.VISIBLE);
                imageRd = imageRd + 1;
            } else if (imageRd == 5) {
                ply5.setImageResource(R.drawable.h01);
                ply5.setVisibility(View.VISIBLE);
                imageRd = imageRd + 1;
            }

        } else if (C.getSuit() == Card.Suit.Hearts && C.getNumber() == 2) {
            if (imageRd == 1) {
                ply1.setImageResource(R.drawable.h02);
                ply1.setVisibility(View.VISIBLE);
                imageRd = imageRd + 1;
            } else if (imageRd == 2) {
                ply2.setImageResource(R.drawable.h02);
                ply2.setVisibility(View.VISIBLE);
                imageRd = imageRd + 1;
            } else if (imageRd == 3) {
                ply3.setImageResource(R.drawable.h02);
                ply3.setVisibility(View.VISIBLE);
                imageRd = imageRd + 1;
            } else if (imageRd == 4) {
                ply4.setImageResource(R.drawable.h02);
                ply4.setVisibility(View.VISIBLE);
                imageRd = imageRd + 1;
            } else if (imageRd == 5) {
                ply5.setImageResource(R.drawable.h02);
                ply5.setVisibility(View.VISIBLE);
                imageRd = imageRd + 1;
            }

        } else if (C.getSuit() == Card.Suit.Hearts && C.getNumber() == 3) {
            if (imageRd == 1) {
                ply1.setImageResource(R.drawable.h03);
                ply1.setVisibility(View.VISIBLE);
                imageRd = imageRd + 1;
            } else if (imageRd == 2) {
                ply2.setImageResource(R.drawable.h03);
                ply2.setVisibility(View.VISIBLE);
                imageRd = imageRd + 1;
            } else if (imageRd == 3) {
                ply3.setImageResource(R.drawable.h03);
                ply3.setVisibility(View.VISIBLE);
                imageRd = imageRd + 1;
            } else if (imageRd == 4) {
                ply4.setImageResource(R.drawable.h03);
                ply4.setVisibility(View.VISIBLE);
                imageRd = imageRd + 1;
            } else if (imageRd == 5) {
                ply5.setImageResource(R.drawable.h03);
                ply5.setVisibility(View.VISIBLE);
                imageRd = imageRd + 1;
            }

        } else if (C.getSuit() == Card.Suit.Hearts && C.getNumber() == 4) {
            if (imageRd == 1) {
                ply1.setImageResource(R.drawable.h04);
                ply1.setVisibility(View.VISIBLE);
                imageRd = imageRd + 1;
            } else if (imageRd == 2) {
                ply2.setImageResource(R.drawable.h04);
                ply2.setVisibility(View.VISIBLE);
                imageRd = imageRd + 1;
            } else if (imageRd == 3) {
                ply3.setImageResource(R.drawable.h04);
                ply3.setVisibility(View.VISIBLE);
                imageRd = imageRd + 1;
            } else if (imageRd == 4) {
                ply4.setImageResource(R.drawable.h04);
                ply4.setVisibility(View.VISIBLE);
                imageRd = imageRd + 1;
            } else if (imageRd == 5) {
                ply5.setImageResource(R.drawable.h04);
                ply5.setVisibility(View.VISIBLE);
                imageRd = imageRd + 1;
            }

        } else if (C.getSuit() == Card.Suit.Hearts && C.getNumber() == 5) {
            if (imageRd == 1) {
                ply1.setImageResource(R.drawable.h05);
                ply1.setVisibility(View.VISIBLE);
                imageRd = imageRd + 1;
            } else if (imageRd == 2) {
                ply2.setImageResource(R.drawable.h05);
                ply2.setVisibility(View.VISIBLE);
                imageRd = imageRd + 1;
            } else if (imageRd == 3) {
                ply3.setImageResource(R.drawable.h05);
                ply3.setVisibility(View.VISIBLE);
                imageRd = imageRd + 1;
            } else if (imageRd == 4) {
                ply4.setImageResource(R.drawable.h05);
                ply4.setVisibility(View.VISIBLE);
                imageRd = imageRd + 1;
            } else if (imageRd == 5) {
                ply5.setImageResource(R.drawable.h05);
                ply5.setVisibility(View.VISIBLE);
                imageRd = imageRd + 1;
            }

        } else if (C.getSuit() == Card.Suit.Hearts && C.getNumber() == 6) {
            if (imageRd == 1) {
                ply1.setImageResource(R.drawable.h06);
                ply1.setVisibility(View.VISIBLE);
                imageRd = imageRd + 1;
            } else if (imageRd == 2) {
                ply2.setImageResource(R.drawable.h06);
                ply2.setVisibility(View.VISIBLE);
                imageRd = imageRd + 1;
            } else if (imageRd == 3) {
                ply3.setImageResource(R.drawable.h06);
                ply3.setVisibility(View.VISIBLE);
                imageRd = imageRd + 1;
            } else if (imageRd == 4) {
                ply4.setImageResource(R.drawable.h06);
                ply4.setVisibility(View.VISIBLE);
                imageRd = imageRd + 1;
            } else if (imageRd == 5) {
                ply5.setImageResource(R.drawable.h06);
                ply5.setVisibility(View.VISIBLE);
                imageRd = imageRd + 1;
            }

        } else if (C.getSuit() == Card.Suit.Hearts && C.getNumber() == 7) {
            if (imageRd == 1) {
                ply1.setImageResource(R.drawable.h07);
                ply1.setVisibility(View.VISIBLE);
                imageRd = imageRd + 1;
            } else if (imageRd == 2) {
                ply2.setImageResource(R.drawable.h07);
                ply2.setVisibility(View.VISIBLE);
                imageRd = imageRd + 1;
            } else if (imageRd == 3) {
                ply3.setImageResource(R.drawable.h07);
                ply3.setVisibility(View.VISIBLE);
                imageRd = imageRd + 1;
            } else if (imageRd == 4) {
                ply4.setImageResource(R.drawable.h07);
                ply4.setVisibility(View.VISIBLE);
                imageRd = imageRd + 1;
            } else if (imageRd == 5) {
                ply5.setImageResource(R.drawable.h07);
                ply5.setVisibility(View.VISIBLE);
                imageRd = imageRd + 1;
            }

        } else if (C.getSuit() == Card.Suit.Hearts && C.getNumber() == 8) {
            if (imageRd == 1) {
                ply1.setImageResource(R.drawable.h08);
                ply1.setVisibility(View.VISIBLE);
                imageRd = imageRd + 1;
            } else if (imageRd == 2) {
                ply2.setImageResource(R.drawable.h08);
                ply2.setVisibility(View.VISIBLE);
                imageRd = imageRd + 1;
            } else if (imageRd == 3) {
                ply3.setImageResource(R.drawable.h08);
                ply3.setVisibility(View.VISIBLE);
                imageRd = imageRd + 1;
            } else if (imageRd == 4) {
                ply4.setImageResource(R.drawable.h08);
                ply4.setVisibility(View.VISIBLE);
                imageRd = imageRd + 1;
            } else if (imageRd == 5) {
                ply5.setImageResource(R.drawable.h08);
                ply5.setVisibility(View.VISIBLE);
                imageRd = imageRd + 1;
            }

        } else if (C.getSuit() == Card.Suit.Hearts && C.getNumber() == 9) {
            if (imageRd == 1) {
                ply1.setImageResource(R.drawable.h09);
                ply1.setVisibility(View.VISIBLE);
                imageRd = imageRd + 1;
            } else if (imageRd == 2) {
                ply2.setImageResource(R.drawable.h09);
                ply2.setVisibility(View.VISIBLE);
                imageRd = imageRd + 1;
            } else if (imageRd == 3) {
                ply3.setImageResource(R.drawable.h09);
                ply3.setVisibility(View.VISIBLE);
                imageRd = imageRd + 1;
            } else if (imageRd == 4) {
                ply4.setImageResource(R.drawable.h09);
                ply4.setVisibility(View.VISIBLE);
                imageRd = imageRd + 1;
            } else if (imageRd == 5) {
                ply5.setImageResource(R.drawable.h09);
                ply5.setVisibility(View.VISIBLE);
                imageRd = imageRd + 1;
            }

        } else if (C.getSuit() == Card.Suit.Hearts && C.getNumber() == 10) {
            if (imageRd == 1) {
                ply1.setImageResource(R.drawable.h10);
                ply1.setVisibility(View.VISIBLE);
                imageRd = imageRd + 1;
            } else if (imageRd == 2) {
                ply2.setImageResource(R.drawable.h10);
                ply2.setVisibility(View.VISIBLE);
                imageRd = imageRd + 1;
            } else if (imageRd == 3) {
                ply3.setImageResource(R.drawable.h10);
                ply3.setVisibility(View.VISIBLE);
                imageRd = imageRd + 1;
            } else if (imageRd == 4) {
                ply4.setImageResource(R.drawable.h10);
                ply4.setVisibility(View.VISIBLE);
                imageRd = imageRd + 1;
            } else if (imageRd == 5) {
                ply5.setImageResource(R.drawable.h10);
                ply5.setVisibility(View.VISIBLE);
                imageRd = imageRd + 1;
            }

        } else if (C.getSuit() == Card.Suit.Hearts && C.getNumber() == 11) {
            if (imageRd == 1) {
                ply1.setImageResource(R.drawable.h11);
                ply1.setVisibility(View.VISIBLE);
                imageRd = imageRd + 1;
            } else if (imageRd == 2) {
                ply2.setImageResource(R.drawable.h11);
                ply2.setVisibility(View.VISIBLE);
                imageRd = imageRd + 1;
            } else if (imageRd == 3) {
                ply3.setImageResource(R.drawable.h11);
                ply3.setVisibility(View.VISIBLE);
                imageRd = imageRd + 1;
            } else if (imageRd == 4) {
                ply4.setImageResource(R.drawable.h11);
                ply4.setVisibility(View.VISIBLE);
                imageRd = imageRd + 1;
            } else if (imageRd == 5) {
                ply5.setImageResource(R.drawable.h11);
                ply5.setVisibility(View.VISIBLE);
                imageRd = imageRd + 1;
            }

        } else if (C.getSuit() == Card.Suit.Hearts && C.getNumber() == 12) {
            if (imageRd == 1) {
                ply1.setImageResource(R.drawable.h12);
                ply1.setVisibility(View.VISIBLE);
                imageRd = imageRd + 1;
            } else if (imageRd == 2) {
                ply2.setImageResource(R.drawable.h12);
                ply2.setVisibility(View.VISIBLE);
                imageRd = imageRd + 1;
            } else if (imageRd == 3) {
                ply3.setImageResource(R.drawable.h12);
                ply3.setVisibility(View.VISIBLE);
                imageRd = imageRd + 1;
            } else if (imageRd == 4) {
                ply4.setImageResource(R.drawable.h12);
                ply4.setVisibility(View.VISIBLE);
                imageRd = imageRd + 1;
            } else if (imageRd == 5) {
                ply5.setImageResource(R.drawable.h12);
                ply5.setVisibility(View.VISIBLE);
                imageRd = imageRd + 1;
            }

        } else if (C.getSuit() == Card.Suit.Hearts && C.getNumber() == 13) {
            if (imageRd == 1) {
                ply1.setImageResource(R.drawable.h13);
                ply1.setVisibility(View.VISIBLE);
                imageRd = imageRd + 1;
            } else if (imageRd == 2) {
                ply2.setImageResource(R.drawable.h13);
                ply2.setVisibility(View.VISIBLE);
                imageRd = imageRd + 1;
            } else if (imageRd == 3) {
                ply3.setImageResource(R.drawable.h13);
                ply3.setVisibility(View.VISIBLE);
                imageRd = imageRd + 1;
            } else if (imageRd == 4) {
                ply4.setImageResource(R.drawable.h13);
                ply4.setVisibility(View.VISIBLE);
                imageRd = imageRd + 1;
            } else if (imageRd == 5) {
                ply5.setImageResource(R.drawable.h13);
                ply5.setVisibility(View.VISIBLE);
                imageRd = imageRd + 1;
            }

        } else if (C.getSuit() == Card.Suit.Spades && C.getNumber() == 1) {
            if (imageRd == 1) {
                ply1.setImageResource(R.drawable.s01);
                ply1.setVisibility(View.VISIBLE);
                imageRd = imageRd + 1;
            } else if (imageRd == 2) {
                ply2.setImageResource(R.drawable.s01);
                ply2.setVisibility(View.VISIBLE);
                imageRd = imageRd + 1;
            } else if (imageRd == 3) {
                ply3.setImageResource(R.drawable.s01);
                ply3.setVisibility(View.VISIBLE);
                imageRd = imageRd + 1;
            } else if (imageRd == 4) {
                ply4.setImageResource(R.drawable.s01);
                ply4.setVisibility(View.VISIBLE);
                imageRd = imageRd + 1;
            } else if (imageRd == 5) {
                ply5.setImageResource(R.drawable.s01);
                ply5.setVisibility(View.VISIBLE);
                imageRd = imageRd + 1;
            }

        } else if (C.getSuit() == Card.Suit.Spades && C.getNumber() == 2) {
            if (imageRd == 1) {
                ply1.setImageResource(R.drawable.s02);
                ply1.setVisibility(View.VISIBLE);
                imageRd = imageRd + 1;
            } else if (imageRd == 2) {
                ply2.setImageResource(R.drawable.s02);
                ply2.setVisibility(View.VISIBLE);
                imageRd = imageRd + 1;
            } else if (imageRd == 3) {
                ply3.setImageResource(R.drawable.s02);
                ply3.setVisibility(View.VISIBLE);
                imageRd = imageRd + 1;
            } else if (imageRd == 4) {
                ply4.setImageResource(R.drawable.s02);
                ply4.setVisibility(View.VISIBLE);
                imageRd = imageRd + 1;
            } else if (imageRd == 5) {
                ply5.setImageResource(R.drawable.s02);
                ply5.setVisibility(View.VISIBLE);
                imageRd = imageRd + 1;
            }

        } else if (C.getSuit() == Card.Suit.Spades && C.getNumber() == 3) {
            if (imageRd == 1) {
                ply1.setImageResource(R.drawable.s03);
                ply1.setVisibility(View.VISIBLE);
                imageRd = imageRd + 1;
            } else if (imageRd == 2) {
                ply2.setImageResource(R.drawable.s03);
                ply2.setVisibility(View.VISIBLE);
                imageRd = imageRd + 1;
            } else if (imageRd == 3) {
                ply3.setImageResource(R.drawable.s03);
                ply3.setVisibility(View.VISIBLE);
                imageRd = imageRd + 1;
            } else if (imageRd == 4) {
                ply4.setImageResource(R.drawable.s03);
                ply4.setVisibility(View.VISIBLE);
                imageRd = imageRd + 1;
            } else if (imageRd == 5) {
                ply5.setImageResource(R.drawable.s03);
                ply5.setVisibility(View.VISIBLE);
                imageRd = imageRd + 1;
            }

        } else if (C.getSuit() == Card.Suit.Spades && C.getNumber() == 4) {
            if (imageRd == 1) {
                ply1.setImageResource(R.drawable.s04);
                ply1.setVisibility(View.VISIBLE);
                imageRd = imageRd + 1;
            } else if (imageRd == 2) {
                ply2.setImageResource(R.drawable.s04);
                ply2.setVisibility(View.VISIBLE);
                imageRd = imageRd + 1;
            } else if (imageRd == 3) {
                ply3.setImageResource(R.drawable.s04);
                ply3.setVisibility(View.VISIBLE);
                imageRd = imageRd + 1;
            } else if (imageRd == 4) {
                ply4.setImageResource(R.drawable.s04);
                ply4.setVisibility(View.VISIBLE);
                imageRd = imageRd + 1;
            } else if (imageRd == 5) {
                ply5.setImageResource(R.drawable.s04);
                ply5.setVisibility(View.VISIBLE);
                imageRd = imageRd + 1;
            }

        } else if (C.getSuit() == Card.Suit.Spades && C.getNumber() == 5) {
            if (imageRd == 1) {
                ply1.setImageResource(R.drawable.s05);
                ply1.setVisibility(View.VISIBLE);
                imageRd = imageRd + 1;
            } else if (imageRd == 2) {
                ply2.setImageResource(R.drawable.s05);
                ply2.setVisibility(View.VISIBLE);
                imageRd = imageRd + 1;
            } else if (imageRd == 3) {
                ply3.setImageResource(R.drawable.s05);
                ply3.setVisibility(View.VISIBLE);
                imageRd = imageRd + 1;
            } else if (imageRd == 4) {
                ply4.setImageResource(R.drawable.s05);
                ply4.setVisibility(View.VISIBLE);
                imageRd = imageRd + 1;
            } else if (imageRd == 5) {
                ply5.setImageResource(R.drawable.s05);
                ply5.setVisibility(View.VISIBLE);
                imageRd = imageRd + 1;
            }

        } else if (C.getSuit() == Card.Suit.Spades && C.getNumber() == 6) {
            if (imageRd == 1) {
                ply1.setImageResource(R.drawable.s06);
                ply1.setVisibility(View.VISIBLE);
                imageRd = imageRd + 1;
            } else if (imageRd == 2) {
                ply2.setImageResource(R.drawable.s06);
                ply2.setVisibility(View.VISIBLE);
                imageRd = imageRd + 1;
            } else if (imageRd == 3) {
                ply3.setImageResource(R.drawable.s06);
                ply3.setVisibility(View.VISIBLE);
                imageRd = imageRd + 1;
            } else if (imageRd == 4) {
                ply4.setImageResource(R.drawable.s06);
                ply4.setVisibility(View.VISIBLE);
                imageRd = imageRd + 1;
            } else if (imageRd == 5) {
                ply5.setImageResource(R.drawable.s06);
                ply5.setVisibility(View.VISIBLE);
                imageRd = imageRd + 1;
            }

        } else if (C.getSuit() == Card.Suit.Spades && C.getNumber() == 7) {
            if (imageRd == 1) {
                ply1.setImageResource(R.drawable.s07);
                ply1.setVisibility(View.VISIBLE);
                imageRd = imageRd + 1;
            } else if (imageRd == 2) {
                ply2.setImageResource(R.drawable.s07);
                ply2.setVisibility(View.VISIBLE);
                imageRd = imageRd + 1;
            } else if (imageRd == 3) {
                ply3.setImageResource(R.drawable.s07);
                ply3.setVisibility(View.VISIBLE);
                imageRd = imageRd + 1;
            } else if (imageRd == 4) {
                ply4.setImageResource(R.drawable.s07);
                ply4.setVisibility(View.VISIBLE);
                imageRd = imageRd + 1;
            } else if (imageRd == 5) {
                ply5.setImageResource(R.drawable.s07);
                ply5.setVisibility(View.VISIBLE);
                imageRd = imageRd + 1;
            }

        } else if (C.getSuit() == Card.Suit.Spades && C.getNumber() == 8) {
            if (imageRd == 1) {
                ply1.setImageResource(R.drawable.s08);
                ply1.setVisibility(View.VISIBLE);
                imageRd = imageRd + 1;
            } else if (imageRd == 2) {
                ply2.setImageResource(R.drawable.s08);
                ply2.setVisibility(View.VISIBLE);
                imageRd = imageRd + 1;
            } else if (imageRd == 3) {
                ply3.setImageResource(R.drawable.s08);
                ply3.setVisibility(View.VISIBLE);
                imageRd = imageRd + 1;
            } else if (imageRd == 4) {
                ply4.setImageResource(R.drawable.s08);
                ply4.setVisibility(View.VISIBLE);
                imageRd = imageRd + 1;
            } else if (imageRd == 5) {
                ply5.setImageResource(R.drawable.s08);
                ply5.setVisibility(View.VISIBLE);
                imageRd = imageRd + 1;
            }

        } else if (C.getSuit() == Card.Suit.Spades && C.getNumber() == 9) {
            if (imageRd == 1) {
                ply1.setImageResource(R.drawable.s09);
                ply1.setVisibility(View.VISIBLE);
                imageRd = imageRd + 1;
            } else if (imageRd == 2) {
                ply2.setImageResource(R.drawable.s09);
                ply2.setVisibility(View.VISIBLE);
                imageRd = imageRd + 1;
            } else if (imageRd == 3) {
                ply3.setImageResource(R.drawable.s09);
                ply3.setVisibility(View.VISIBLE);
                imageRd = imageRd + 1;
            } else if (imageRd == 4) {
                ply4.setImageResource(R.drawable.s09);
                ply4.setVisibility(View.VISIBLE);
                imageRd = imageRd + 1;
            } else if (imageRd == 5) {
                ply5.setImageResource(R.drawable.s09);
                ply5.setVisibility(View.VISIBLE);
                imageRd = imageRd + 1;
            }

        } else if (C.getSuit() == Card.Suit.Spades && C.getNumber() == 10) {
            if (imageRd == 1) {
                ply1.setImageResource(R.drawable.s10);
                ply1.setVisibility(View.VISIBLE);
                imageRd = imageRd + 1;
            } else if (imageRd == 2) {
                ply2.setImageResource(R.drawable.s10);
                ply2.setVisibility(View.VISIBLE);
                imageRd = imageRd + 1;
            } else if (imageRd == 3) {
                ply3.setImageResource(R.drawable.s10);
                ply3.setVisibility(View.VISIBLE);
                imageRd = imageRd + 1;
            } else if (imageRd == 4) {
                ply4.setImageResource(R.drawable.s10);
                ply4.setVisibility(View.VISIBLE);
                imageRd = imageRd + 1;
            } else if (imageRd == 5) {
                ply5.setImageResource(R.drawable.s10);
                ply5.setVisibility(View.VISIBLE);
                imageRd = imageRd + 1;
            }

        } else if (C.getSuit() == Card.Suit.Spades && C.getNumber() == 11) {
            if (imageRd == 1) {
                ply1.setImageResource(R.drawable.s11);
                ply1.setVisibility(View.VISIBLE);
                imageRd = imageRd + 1;
            } else if (imageRd == 2) {
                ply2.setImageResource(R.drawable.s11);
                ply2.setVisibility(View.VISIBLE);
                imageRd = imageRd + 1;
            } else if (imageRd == 3) {
                ply3.setImageResource(R.drawable.s11);
                ply3.setVisibility(View.VISIBLE);
                imageRd = imageRd + 1;
            } else if (imageRd == 4) {
                ply4.setImageResource(R.drawable.s11);
                ply4.setVisibility(View.VISIBLE);
                imageRd = imageRd + 1;
            } else if (imageRd == 5) {
                ply5.setImageResource(R.drawable.s11);
                ply5.setVisibility(View.VISIBLE);
                imageRd = imageRd + 1;
            }

        } else if (C.getSuit() == Card.Suit.Spades && C.getNumber() == 12) {
            if (imageRd == 1) {
                ply1.setImageResource(R.drawable.s12);
                ply1.setVisibility(View.VISIBLE);
                imageRd = imageRd + 1;
            } else if (imageRd == 2) {
                ply2.setImageResource(R.drawable.s12);
                ply2.setVisibility(View.VISIBLE);
                imageRd = imageRd + 1;
            } else if (imageRd == 3) {
                ply3.setImageResource(R.drawable.s12);
                ply3.setVisibility(View.VISIBLE);
                imageRd = imageRd + 1;
            } else if (imageRd == 4) {
                ply4.setImageResource(R.drawable.s12);
                ply4.setVisibility(View.VISIBLE);
                imageRd = imageRd + 1;
            } else if (imageRd == 5) {
                ply5.setImageResource(R.drawable.s12);
                ply5.setVisibility(View.VISIBLE);
                imageRd = imageRd + 1;
            }

        } else if (C.getSuit() == Card.Suit.Spades && C.getNumber() == 13) {
            if (imageRd == 1) {
                ply1.setImageResource(R.drawable.s13);
                ply1.setVisibility(View.VISIBLE);
                imageRd = imageRd + 1;
            } else if (imageRd == 2) {
                ply2.setImageResource(R.drawable.s13);
                ply2.setVisibility(View.VISIBLE);
                imageRd = imageRd + 1;
            } else if (imageRd == 3) {
                ply3.setImageResource(R.drawable.s13);
                ply3.setVisibility(View.VISIBLE);
                imageRd = imageRd + 1;
            } else if (imageRd == 4) {
                ply4.setImageResource(R.drawable.s13);
                ply4.setVisibility(View.VISIBLE);
                imageRd = imageRd + 1;
            } else if (imageRd == 5) {
                ply5.setImageResource(R.drawable.s13);
                ply5.setVisibility(View.VISIBLE);
                imageRd = imageRd + 1;
            }


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
