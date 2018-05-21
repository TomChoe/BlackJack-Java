package com.company;

public class Bank {

    private int bank;

    public Bank(int bank) {
        this.bank = bank;
    }

    public int getValue() {
        return this.bank;
    }

    public void plusValue(int bank) {
        this.bank += bank;
    }

    public void minusValue(int bank) {
        this.bank = this.bank - bank;
    }

}