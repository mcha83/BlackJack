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

    public Card(Suit suit, int number){
        this.suit = suit;
        this.number = number;
    }

}
