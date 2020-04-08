package com.example.model;


import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.util.List;

@JsonDeserialize
public class Response {
    List<String> inputSymbols;
    List<StateResponse> states;
    List<TransitionResponse> transitions;

    public Response(List<String> inputSymbols, List<StateResponse> states, List<TransitionResponse> transitions) {
        this.inputSymbols = inputSymbols;
        this.states = states;
        this.transitions = transitions;
    }

    public List<String> getInputSymbols() {
        return inputSymbols;
    }

    public void setInputSymbols(List<String> inputSymbols) {
        this.inputSymbols = inputSymbols;
    }

    public List<StateResponse> getStates() {
        return states;
    }

    public void setStates(List<StateResponse> states) {
        this.states = states;
    }

    public List<TransitionResponse> getTransitions() {
        return transitions;
    }

    public void setTransitions(List<TransitionResponse> transitions) {
        this.transitions = transitions;
    }
}
