package com.example.michaelcha.blackjack;

import android.content.Context;
import android.util.Log;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * Bradley Wilcox / Michael Cha
 * CSCI 4020
 * Assignment 1
 *
 */

public class Player implements Serializable {
    public static final String PLAYER_DATA = "player.txt";
    public static final String DEALER_DATA = "dealer.txt";

    private ArrayList<Card> hand;
    private int money;
    private int bet;
    private boolean isHandOver;

    public Player(){
        resetForNextHand();
        this.money = 2000;
    }

    public void resetForNextHand(){
        this.hand = new ArrayList<>();
        this.bet = 0;
        this.isHandOver = false;
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

    public ArrayList<Card> getHand(){
        return this.hand;
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

    public void setHandOver(boolean isOver){
        this.isHandOver = isOver;
    }

    public boolean getHandOver(){
        return this.isHandOver;
    }

    public void rewardForWinningHand(){
        this.money += this.bet * 2;
    }

    public void refund(){
        this.money += this.bet;
    }

    // Save serializable data to file
    public void Save(Context context, boolean isDealer){
        try {
            FileOutputStream fos = context.openFileOutput(isDealer ? DEALER_DATA : PLAYER_DATA, Context.MODE_PRIVATE);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(this);
            oos.flush();
            oos.close();
        } catch (Exception ex) {
            Log.v("Err Saving Player Data", ex.getMessage());
            ex.printStackTrace();
        }
    }

    // load data from file, or return new instance
    public static Player loadPlayer(Context context, boolean isDealer){
        try
        {
            FileInputStream fis = context.openFileInput(isDealer ? DEALER_DATA : PLAYER_DATA);
            ObjectInputStream ois = new ObjectInputStream(fis);
            Object o = ois.readObject();

            return (Player)o;
        }

        catch(Exception ex)
        {
            return new Player();
        }
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
