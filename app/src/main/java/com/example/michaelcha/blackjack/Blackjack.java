package com.example.michaelcha.blackjack;

import android.content.Context;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;

/**
 *
 * Bradley Wilcox / Michael Cha
 * CSCI 4020
 * Assignment 1
 *
 */

public class Blackjack {
    private Deck deck;
    private Player dealer;
    private Player player;

    public Blackjack(){
        deck = new Deck(1);
        dealer = new Player();
        player = new Player();
    }

    public Blackjack(Context context){
        deck = Deck.loadDeck(context);
        dealer = Player.loadPlayer(context, true);
        player = Player.loadPlayer(context, false);
    }

    // Start everyone out with two cards
   public void startGame(){
        player.hitMe(deck.dealCard());
        dealer.hitMe(deck.dealCard());
        player.hitMe(deck.dealCard());
        dealer.hitMe(deck.dealCard());
    }

    public void nextHand(){
        player.resetForNextHand();
        dealer.resetForNextHand();
    }


    public void doDealersTurn(){
        while(dealer.getHandTotal() < 18) {
            dealer.hitMe(deck.dealCard());
        }

        dealer.setHandOver(true);
    }


    public Deck getDeck(){
        return this.deck;
    }

    public Player getPlayer(){
        return this.player;
    }

    public boolean didPlayerBust(){
        return this.player.getHandTotal() > 21;
    }

    public boolean didDealerBust(){
        return this.dealer.getHandTotal() > 21;
    }

    public Player getDealer(){
        return this.dealer;
    }


}
