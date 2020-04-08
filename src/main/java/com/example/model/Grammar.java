/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.model;
import java.util.ArrayList;

 

public class Grammar {
    private ArrayList<Production> productions;

    public Grammar() {
        productions = new ArrayList<>();
    }
    
    public Grammar(ArrayList<Production> productions) {
        this.productions = productions;
    }

    public ArrayList<Production> getProductions() {
        return productions;
    }

    public void setProductions(ArrayList<Production> productions) {
        this.productions = productions;
    }
    
    public void print () // cuando se vaya ese ruido desmuteo
    {
        System.out.println("<-----------------start gramatic--------------->");
        for (Production prod : productions)
        {
            System.out.print("<" + prod.getLeftSide().getSymbol() + "> -> ");
            for (T2NT tnt :  prod.getRightSide())
            {
                if (tnt.isIsNonTerminal())
                    System.out.print("<" + tnt.getSymbol() + ">");
                else
                    System.out.print(tnt.getSymbol());
            }
            System.out.println();
        }
        System.out.println("<---------------end gramatic----------------->");
    }
}
