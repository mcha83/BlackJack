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
    private int money;
    private int bet;

    public Player(){
        resetForNextHand();
        this.money = 2000;
    }

    public void resetForNextHand(){
        this.hand = new ArrayList<>();
        this.bet = 0;
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

    public String getHandAsString(boolean hideFirst){
        String handStr = "";
        int i = 0;
        for(Card c: this.hand){
            if(i == 0 && hideFirst == true)
                handStr += "[Hidden], ";
            else
                handStr += c.toString() + ", ";

            i++;
        }

        return handStr.substring(0, handStr.length() - 2);
    }

    public int getMoney(){
        return this.money;
    }

    public int getBet(){
        return this.bet;
    }

    public void placeBet(int bet){
        this.bet += bet;
        this.money -= bet;
    }

    public void rewardForWinningHand(){
        this.money += this.bet * 2;
    }

    /**
     * Debug purposes, prints to logcat
     */
    public void printHand(String debugName){
        for(Card c : this.hand) {
            Log.i("BLACKJACK", debugName + ": " + c.getSuit() + " " + c.getNumber());
        }
    }

}
