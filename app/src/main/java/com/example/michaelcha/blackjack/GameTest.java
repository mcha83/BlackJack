package com.example.michaelcha.blackjack;

import android.util.Log;

/**
 *
 * Bradley Wilcox / Michael Cha
 * CSCI 4020
 * Assignment 1
 *
 */

public class GameTest {

    public GameTest(){
        Deck deck = new Deck(1);
        Player player = new Player();
        Player dealer = new Player();

        deck.printDeck();

        //Simple Test, give the player and dealer each three cards
        //Will need to add some basic decision making for dealer in actual game
        player.hitMe(deck.dealCard());
        dealer.hitMe(deck.dealCard());
        player.hitMe(deck.dealCard());
        dealer.hitMe(deck.dealCard());
        player.hitMe(deck.dealCard());
        dealer.hitMe(deck.dealCard());

        player.printHand("Player");
        dealer.printHand("Dealer");

        int playerTotal = player.getHandTotal();
        int dealerTotal = dealer.getHandTotal();

        if(playerTotal > 21)
            Log.i("BLACKJACK", "Player Busts");
        else if(dealerTotal > 21)
            Log.i("BLACKJACK", "Dealer Busts");
        else if(playerTotal > dealerTotal)
            Log.i("BLACKJACK", "Player Wins!");
        else
            Log.i("BLACKJACK", "Dealer Wins!");
    }

    public void DealTest(Deck deck) {
        Card dealt = deck.dealCard();
        Log.i("BLACKJACK", "Card Dealt: " + dealt.getSuit() + " " + dealt.getNumber());
    }

}
