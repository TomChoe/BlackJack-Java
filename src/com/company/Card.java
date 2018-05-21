package com.company;

public class Card {
    private int value;
    private String suit;

    public Card(int value, String suit) {
        this.value = value;
        this.suit = suit;
    }

    public String toString() {
        return Integer.toString(value) + "-" + this.suit.toString();
    }

    public int getValue() {
        return this.value;
    }

    public void setValue(int i) {
        this.value = i;
    }

    public String getSuit() {
        return this.suit;
    }
}
