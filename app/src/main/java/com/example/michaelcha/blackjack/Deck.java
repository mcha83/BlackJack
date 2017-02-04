package com.example.michaelcha.blackjack;

import android.util.Log;

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

public class Deck {

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

}
