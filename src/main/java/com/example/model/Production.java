/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.model;

import java.util.ArrayList;

 
public class Production {
    private T2NT leftSide;
    private ArrayList<T2NT> rightSide;

    public Production() {
        rightSide = new ArrayList<> ();
    }

    public Production(String leftSide) {
        this.leftSide = new T2NT(leftSide, true);
        rightSide = new ArrayList<> ();
    } 

    public T2NT getLeftSide() {
        return leftSide;
    }

    public void setLeftSide(T2NT leftSide) {
        this.leftSide = leftSide;
    }

    public ArrayList<T2NT> getRightSide() {
        return rightSide;
    }

    public void setRightSide(ArrayList<T2NT> rightSide) {
        this.rightSide = rightSide;
    }
}
