package com.example.michaelcha.blackjack;

import android.util.Log;

import java.util.ArrayList;

/**
 *
 * Bradley Wilcox / Michael Cha
 * CSCI 4020
 * Assignment 1
 *
 */

public class Player {
    private ArrayList<Card> hand;

    public Player(){
        this.hand = new ArrayList<>();
    }

    /**
     *
     * @param card  Card to be added to the player's hand
     */
    public void hitMe(Card card){
        this.hand.add(card);
    }

    public int getHandTotal(){
        int total = 0;
        int numAces = 0;

        for(Card c : this.hand){
            int cardValue = c.getNumber();

            //aces
            if(cardValue == 1) {
                total += 11;
                numAces++;
            //face cards
            }else if(cardValue > 10)
                total += 10;
            else
                total += cardValue;
        }

        while(total > 21 && numAces > 0){
            total -= 10;
            numAces--;
        }

        return total;
    }

    public String getHandAsString(){
        String handStr = "";
        for(Card c: this.hand){
            handStr += c.toString();
        }

        return handStr;
    }

}
