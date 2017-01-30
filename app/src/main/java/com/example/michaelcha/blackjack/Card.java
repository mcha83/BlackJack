package com.example.michaelcha.blackjack;

/**
 *
 * Bradley Wilcox / Michael Cha
 * CSCI 4020
 * Assignment 1
 *
 */

public class Card {

    public enum Suit {
        Clubs,
        Diamonds,
        Spades,
        Hearts
    }

    private Suit suit;
    private int number;

    /**
     * @param suit      Card suit, enum type
     * @param number    Number of card 1 - 13
     */
    public Card(Suit suit, int number){
        this.suit = suit;
        this.number = number;
    }

    public Suit getSuit(){
        return this.suit;
    }

    public int getNumber(){
        return this.number;
    }

}
