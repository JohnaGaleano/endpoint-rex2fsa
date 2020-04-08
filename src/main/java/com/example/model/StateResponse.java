package com.example.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize
public class StateResponse {
    String name;
    Boolean acceptance;
    Boolean initial;

    public StateResponse(String name, Boolean acceptance, Boolean initial) {
        this.name = name;
        this.acceptance = acceptance;
        this.initial = initial;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getAcceptance() {
        return acceptance;
    }

    public void setAcceptance(Boolean acceptance) {
        this.acceptance = acceptance;
    }

    public Boolean getInitial() {
        return initial;
    }

    public void setInitial(Boolean initial) {
        this.initial = initial;
    }
}
