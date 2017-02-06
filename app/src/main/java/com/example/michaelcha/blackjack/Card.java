package com.example.michaelcha.blackjack;

import java.io.Serializable;

/**
 *
 * Bradley Wilcox / Michael Cha
 * CSCI 4020
 * Assignment 1
 *
 */

public class Card implements Serializable{

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

    public String toString(){
        String num;
        switch(this.number){
            case 1:
                num = "Ace";
                break;
            case 11:
                num = "Jack";
                break;
            case 12:
                num = "Queen";
                break;
            case 13:
                num = "King";
                break;
            default:
                num = this.number + "";
                break;
        }

        return num + " of " + this.suit;
    }

}
