/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.model;

 
public class T2NT {
    private String  symbol;
    private boolean isNonTerminal;

    public T2NT() {
        isNonTerminal = false;
    }
    
    public T2NT(String symbol, boolean isNonTerminal) {
        this.symbol = symbol;
        this.isNonTerminal = isNonTerminal;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public boolean isIsNonTerminal() {
        return isNonTerminal;
    }

    public void setIsNonTerminal(boolean isNonTerminal) {
        this.isNonTerminal = isNonTerminal;
    }
}
