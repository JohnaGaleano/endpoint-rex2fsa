package com.example.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonDeserialize
@JsonSerialize
public class InputRex {
    String inputRex;

    public InputRex() {
        super();
    }

    public InputRex(String inputRex) {
        this.inputRex = inputRex;
    }

    public String getInputRex() {
        return inputRex;
    }

    public void setInputRex(String inputRex) {
        this.inputRex = inputRex;
    }
}
