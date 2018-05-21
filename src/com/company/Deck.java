package com.company;

import java.util.ArrayList;

public class Deck {

    private ArrayList<Card> cards;
    private int[] value = {2,3,4,5,6,7,8,9,10};
    private String[] suit = {"spades", "clubs", "diamonds", "hearts"};
    private String[] face = {"jack", "queen", "king", "ace"};

    public Deck() {
        this.cards = new ArrayList<Card>();
    }

    public void createDeck() {
        for(int i = 0; i < suit.length; i++) {
            for(int x = 0; x < value.length; x++) {
                this.cards.add(new Card(value[x],suit[i]));
            }
        }

        for(int i = 0; i < suit.length; i++) {
            for(int x = 0; x < face.length; x++) {
                if(face[x].equals("ace")) {
                    this.cards.add(new Card(11, face[x]));
                } else {
                    this.cards.add(new Card(10, face[x]));
                }
            }
        }
    }

    public void shuffle() {
        int deckSize = this.cards.size();
        for(int i = 0; i < deckSize; i++) {
            int rand = (int) (Math.random() * deckSize);
            cards.set(rand, this.cards.get(i));
            cards.set(i, this.cards.get(rand));
//            cards.set(i) = cards.get(rand);
//            cards.get(rand) = temp;
        }
    }

    public void removeCard(int i) {
        this.cards.remove(i);
    }

    public Card getCard(int i) {
        return this.cards.get(i);
    }

    public void deal(Deck deck){
        this.cards.add(deck.getCard(0));
        deck.removeCard(0);
    }

    public int deckSize() {
        return this.cards.size();
    }

    public int getHandTotal() {
        int current = 0;
        for(int x = 0; x < this.cards.size(); x++) {
            current += this.cards.get(x).getValue();
        }
        return current;
    }

    public boolean checkAce() {
        boolean check = false;
        for(int x = 0; x < cards.size(); x++) {
            if (this.cards.get(x).getSuit().equals("ace")) {
                check = true;
            } else {
                check = false;
            }
        }
        return check;
    }

    public String toString() {
        String cardList = "";
        for(int x = 0; x < cards.size(); x++) {
            cardList += cards.get(x).toString() + " ";
        }
        return cardList;
    }
}