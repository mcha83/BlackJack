package com.example.michaelcha.blackjack;

import android.content.Context;
import android.util.Log;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/**
 *
 * Bradley Wilcox / Michael Cha
 * CSCI 4020
 * Assignment 1
 *
 */

public class Deck implements Serializable{

    public static final String DECK_DATA = "deck.txt";
    public static final int DeckSize = 52;

    private ArrayList<Card> cards;

    public Deck(int numDecks){
        int numCards = numDecks * DeckSize;
        this.cards = new ArrayList<>(numCards);

        generateCards(numDecks);
        shuffle();
    }

    private void generateCards(int numDecks){

        //for each deck, for each suit, 13 cards per suit
        for(int i = 0; i < numDecks; i++){
            for(Card.Suit s : Card.Suit.values()) {
                for (int k = 1; k <= 13; k++) {
                    this.cards.add(new Card(s, k));
                }
            }
        }

    }

    public void shuffle(){
        Collections.shuffle(this.cards, new Random(System.nanoTime()));
    }

    public Card dealCard(){
        Card dealing = this.cards.get(0);
        this.cards.remove(dealing);

        return dealing;
    }

    // Save serializable data to file
    public void Save(Context context){
        try {
            FileOutputStream fos = context.openFileOutput(DECK_DATA, Context.MODE_PRIVATE);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(this);
            oos.flush();
            oos.close();
        } catch (Exception ex) {
            Log.v("Err Saving Deck Data", ex.getMessage());
            ex.printStackTrace();
        }
    }

    // load data from file, or return new instance
    public static Deck loadDeck(Context context){
        try
        {
            FileInputStream fis = context.openFileInput(DECK_DATA);
            ObjectInputStream ois = new ObjectInputStream(fis);
            Object o = ois.readObject();

            return (Deck)o;
        }

        catch(Exception ex)
        {
            return new Deck(1);
        }
    }

    /**
     * Debug purposes, prints to logcat
     */
    public void printDeck(){
        Log.i("BLACKJACK", "Number of cards total: " + this.cards.size());
        for(int i = 0; i < this.cards.size(); i++){
            Log.i("BLACKJACK", "Card Position: " + (i+1) + " " + this.cards.get(i).getSuit() + " " + this.cards.get(i).getNumber());
        }
    }

}
