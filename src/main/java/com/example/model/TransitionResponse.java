package com.example.model;

public class TransitionResponse {
    String actualState;
    String inputSymbol;
    String nextState;

    public TransitionResponse(String actualState, String inputSymbol, String nextState) {
        this.actualState = actualState;
        this.inputSymbol = inputSymbol;
        this.nextState = nextState;
    }

    public String getActualState() {
        return actualState;
    }

    public void setActualState(String actualState) {
        this.actualState = actualState;
    }

    public String getInputSymbol() {
        return inputSymbol;
    }

    public void setInputSymbol(String inputSymbol) {
        this.inputSymbol = inputSymbol;
    }

    public String getNextState() {
        return nextState;
    }

    public void setNextState(String nextState) {
        this.nextState = nextState;
    }
}
