package com.example.michaelcha.blackjack;

import java.util.ArrayList;

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
    private int numCards;

    public Deck(int numDecks){
        this.numCards = numDecks * DeckSize;
        this.cards = new ArrayList<>(this.numCards);
    }
}
