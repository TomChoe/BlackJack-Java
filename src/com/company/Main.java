package com.company;

import java.util.InputMismatchException;

import java.util.Scanner;

public class Main {

    static Scanner input = new Scanner(System.in);
    static Bank currentBank = new Bank(100);
    static int bet = 0;
    static Deck deck = new Deck();
    static Deck player;
    static Deck dealer;
    static boolean endRound = true;

    public static void start() {
        endRound = true;
        System.out.println("Would you like to play? (y)Yes or (n)No");
        String start = input.next().toLowerCase();
        if (start.equals("y")) {
            bet();
        } else if (start.equals("n")) {
            System.out.println("Ok, maybe next time");
            System.exit(0);
        } else if (start != "y") {
            System.out.println("invalid input try again");
            start();
        }
    }

    public static void bet() {
        System.out.println("Your Bank : $" + currentBank.getValue());
        System.out.println("Please enter your wager");
        boolean end = false;
        do {
            try {
                bet = input.nextInt();
                end = true;
            } catch(InputMismatchException ex) {
                System.out.println("An error ocurred: invalid input");
                System.out.println("Try again please integers only");
                input.nextLine();
            }
        } while(end == false);
        if(bet < 1) {
            System.out.println("You can't bet that!");
            bet();
        } else if(bet > currentBank.getValue()) {
            System.out.println("You Can't bet more than you have");
            System.out.println("Please re-enter bet");
            bet();
        }
        System.out.println("You bet $" + bet + "\n");
        dealCards();
    }

    public static void doubleDown() {
        System.out.println("Doubled Down");
        bet = bet * 2;
        hit();
        if(player.getHandTotal() <= 21) {
            stand();
        }
    }

    public static void dealCards() {
        player = new Deck();
        dealer = new Deck();
        String currentFace;
        for (int i = 0; i < 2; i++) {
            player.deal(deck);
            dealer.deal(deck);
        }
        int currentHand = player.getHandTotal();
        if (currentHand == 21) {
            System.out.println("BLACKJACK! You win $ " + bet);
            currentBank.plusValue(bet);
            endRound = false;
        } else if (currentHand > 21 && player.checkAce()) {
            player.getCard(0).setValue(1);
        }
    }

    public static void hit() {
        player.deal(deck);
        int playerHand = player.getHandTotal();
        System.out.println(" * Player hits *");
        System.out.println("Current Player hand");
        System.out.println(player + " " + playerHand);

        for(int x = 0; x < player.deckSize(); x++) {
            if(playerHand > 21 && player.checkAce()) {
//            if(playerHand > 21 && player.getCard(x).getSuit().equals("ace")) {
                player.getCard(x).setValue(1);
            } else if (playerHand > 21) {
                System.out.println("You busted! Lose $" + bet);
                currentBank.minusValue(bet);
                endRound = false;
                return;
            }
        }
    }

    public static void stand() {
        int dealerHand = dealer.getHandTotal();
        int playerHand = player.getHandTotal();
        System.out.println("player stands with a " + playerHand + "\n");

        System.out.println("Dealer shows " + dealer.toString());

        // if 2 aces appears for dealer
        if (dealerHand > 21 && dealer.checkAce()) {
            dealer.getCard(0).setValue(1);
        }

        System.out.println("dealers total: " + dealer.getHandTotal());

        while (dealerHand < 17) {
            dealer.deal(deck);
            dealerHand += dealer.getCard(dealer.deckSize()-1).getValue();
            System.out.println("* dealer hits *");

            System.out.println("dealer total " + dealer + " " + dealerHand);

            for (int x = 0; x < dealer.deckSize(); x++) {
                if (dealerHand > 21 && dealer.checkAce()) {
                    dealer.getCard(x).setValue(1);
                }
            }

            if (dealerHand > 21) {
                System.out.println("Dealer busts, you win $" + bet);
                currentBank.plusValue(bet);
                endRound = false;
            } else if (dealerHand == 21 && playerHand != 21) {
                System.out.println("Dealer BlackJack! you lose $" + bet);
                currentBank.minusValue(bet);
                endRound = false;
            } else if (playerHand == dealerHand) {
                System.out.println("Push!");
                endRound = false;
            }
        }
        if (playerHand > dealerHand) {
            System.out.println("Player wins! $" + bet + " has been added to bank");
            currentBank.plusValue(bet);
            endRound = false;
        } else if (dealerHand > playerHand && dealerHand <= 21) {
            System.out.println("Dealer Win! You lose $" + bet);
            currentBank.minusValue(bet);
            endRound = false;
        }
    }

    public static void main(String[] args) {
        System.out.println("Welcome to Black Jack!\nCompete against the dealer and win!\n");
        while(currentBank.getValue() > 0) {
            deck.createDeck();
            deck.shuffle();

            start();
            System.out.println("Your Hand: " + player.getHandTotal() + "\n" + player.toString() + "\n\n");
            System.out.println("Dealer Hand: (stays on soft 17)\n" + dealer.getCard(0).toString() + " hidden\n");

            while (endRound) {
                System.out.println("(1)Hit or (2)Stand");
                int hit = input.nextInt();
                if (hit == 1) {
                    hit();
                } else if (hit == 2) {
                    stand();
                }
            }
        }
    }
}
