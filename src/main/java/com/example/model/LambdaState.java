/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.model;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

 
public class LambdaState {
    public List<Integer> lamdaTransitions;
    public HashMap<String, Integer> transitions;
    public boolean isUptakingState;
    
    public LambdaState()
    {
        lamdaTransitions = new LinkedList<>();
        transitions = new HashMap<String, Integer>();
        isUptakingState = false;
    }
    
    public LambdaState(List<Integer> lamda, boolean accepting)
    {
        lamdaTransitions = lamda;
        transitions = new HashMap<String, Integer>();
        isUptakingState = accepting;
    }
    
    public void printTransitions ()
    {
        for (Map.Entry<String, Integer> entry : transitions.entrySet())
        {
            System.out.println(entry.getKey() + " : " + entry.getValue());
        }
    }
}
