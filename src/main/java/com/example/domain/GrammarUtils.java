/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.domain;

import com.example.model.*;

import java.util.ArrayList;
import java.util.List;

 
public class GrammarUtils {
    public static Grammar automatonToGramatic (FSA automaton)
    {
        ArrayList<Production> productions = new ArrayList<>(); // Producciones de la gramática
        List<LambdaState> states = automaton.states; // Estados del autómata
        String inSymbols = automaton.symbols; // Symbolos de entrada del autómata
        
        for (int i = 0; i < states.size(); i++)
        {
            LambdaState state = states.get(i);
            for (int j = 0; j < inSymbols.length(); j++)
            {
                String symbol = inSymbols.substring(j, j+1);
                Production prod = production(String.valueOf(i), state, symbol);
                if (prod != null) 
                    productions.add(prod);
            }
            if (state.isUptakingState)
            {
                Production prod = new Production(String.valueOf(i));
                prod.getRightSide().add(new T2NT("λ", false));
                productions.add(prod);
            }
        }
        Grammar grammar = new Grammar(productions);
        return grammar;
    }
    
    private static Production production (String leftSide, LambdaState state, String symbol)
    {
        Production prod = new Production(leftSide);
        if (state.transitions.containsKey(symbol))
        {
            T2NT terminal = new T2NT(symbol, false);
            T2NT nonTerminal = new T2NT(String.valueOf(state.transitions.get(symbol)), true);
            prod.getRightSide().add(terminal);
            prod.getRightSide().add(nonTerminal);
        } else
            return null;
        return prod;
    }
    
    // Este método calcula los primeros de una gramática de la forma especial
    public static ArrayList<String> productionFirst (Grammar grammar)
    {
        ArrayList<String> first = new ArrayList<>();
        grammar.getProductions().forEach((prod) -> {
            first.add(prod.getRightSide().get(0).getSymbol());
        });
        return first;
    }
}

