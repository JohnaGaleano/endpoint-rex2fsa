package com.example.controller;

import com.example.domain.FSACreator;
import com.example.domain.SequenceCreator;
import com.example.model.*;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@RestController
public class EndpointController {
    boolean hasAExpression;
    FSA automaton;

    @CrossOrigin
    @PostMapping(path = "/rex", consumes = "application/json", produces = "application/json")
    public Response addRex(@RequestBody InputRex inputRex) {

        String string = inputRex.getInputRex();
        String stringAfterRex = transformString(string);

        char[] wordsStringAfterRex = stringAfterRex.toCharArray();
        ArrayList<Character> stringAfterDotsProccess = new ArrayList<>();
        int countWordsStringAfterRex = 0;
        for (int index = 0; index < wordsStringAfterRex.length - 1; index++) {
            stringAfterDotsProccess.add(wordsStringAfterRex[index]);
            if (conditions(wordsStringAfterRex[index], wordsStringAfterRex[index + 1])) {
                stringAfterDotsProccess.add('.');
            }
            countWordsStringAfterRex = index;
        }
        stringAfterDotsProccess.add(wordsStringAfterRex[countWordsStringAfterRex + 1]);

        String str = stringAfterDotsProccess.stream().map(e -> e.toString()).collect(Collectors.joining());

        String expression = str;

        List<String> inputSymbols3 = new ArrayList<>();
        List<StateResponse> states3 = new ArrayList<>();
        List<TransitionResponse> transitions3 = new ArrayList<>();

        if (!expression.equals("")) {
            SequenceCreator c = new SequenceCreator(expression); // (0|1.0*.1)*.0* = (0+10*1)*0*
            try {
                hasAExpression = false;
                DoubleNode start = c.CreateThompson();
                automaton = FSACreator.generateAFD(start, c.secSimbols);
                automaton.simplifyAutomaton();
                hasAExpression = true;
            } catch (Exception e) {
            }

            if (automaton != null) {
                setTableColumnIdentifiers(automaton.symbols, inputSymbols3);
                setTableRows(automaton.states, states3, transitions3, automaton.symbols);
            } else
                System.out.println("Error");
        }
        return new

                Response(inputSymbols3, states3, transitions3);
    }

    @CrossOrigin
    @GetMapping("/")
    public Response rex(@RequestParam(value = "rex", defaultValue = "") String rex) {
        if (rex.isEmpty()) {
            List<String> inputSymbols = new ArrayList<>();
            inputSymbols.add("0");
            inputSymbols.add("1");

            List<StateResponse> states = new ArrayList<>();
            states.add(new StateResponse("A", true, true));
            states.add(new StateResponse("B", false, false));
            states.add(new StateResponse("C", false, false));
            states.add(new StateResponse("D", false, false));

            List<TransitionResponse> transitions = new ArrayList<>();
            transitions.add(new TransitionResponse("A", "0", "C"));
            transitions.add(new TransitionResponse("A", "1", "B"));

            transitions.add(new TransitionResponse("B", "0", "D"));
            transitions.add(new TransitionResponse("B", "1", "A"));

            transitions.add(new TransitionResponse("C", "0", "A"));
            transitions.add(new TransitionResponse("C", "1", "D"));

            transitions.add(new TransitionResponse("D", "0", "B"));
            transitions.add(new TransitionResponse("D", "1", "C"));


            return new Response(inputSymbols, states, transitions);
        }
        List<String> inputSymbols2 = new ArrayList<>();
        List<StateResponse> states2 = new ArrayList<>();
        List<TransitionResponse> transitions2 = new ArrayList<>();
        states2.add(new StateResponse("State", true, true));
        transitions2.add(new TransitionResponse("A", "0", "1"));
        transitions2.add(new TransitionResponse("A", "0", "1"));
        inputSymbols2.add("A");
        inputSymbols2.add("B");
        return new Response(inputSymbols2, states2, transitions2);
    }

    public void setTableColumnIdentifiers(String symbols, List<String> InputS) {
        String[] headers = new String[symbols.length() + 2];
        headers[0] = "";
        headers[headers.length - 1] = "┤";

        for (int i = 1; i < headers.length - 1; i++) {
            InputS.add(symbols.substring(i - 1, i));
            headers[i] = symbols.substring(i - 1, i);
        }
    }

    public void setTableRows(List<LambdaState> states, List<StateResponse> stateR, List<TransitionResponse> transitionR, String symbols) {
        boolean firstState = true;
        boolean errorState = true;
        for (int i = 0; i < states.size(); i++) {
            LambdaState state = states.get(i);
            for (int j = 0; j < symbols.length(); j++) {

                String inputSymbol = symbols.substring(j, j + 1);
//                String actualState = Integer.toString(i);
                String actualState = int2String(i);
                String nextState;
                try {
//                    nextState = Integer.toString(state.transitions.get(inputSymbol));
                    nextState = int2String(state.transitions.get(inputSymbol));
                } catch (Exception e) {
                    nextState = "Error";
                    if (errorState) {
                        stateR.add(new StateResponse("Error", false, false));
                        errorState = false;
                    }
                }

                transitionR.add(new TransitionResponse(actualState, inputSymbol, nextState));
            }
//            String stateString = Integer.toString(i);
            String stateString = int2String(i);
            if (firstState) {
                stateR.add(new StateResponse(stateString, state.isUptakingState, true));
                firstState = false;
            } else {
                stateR.add(new StateResponse(stateString, state.isUptakingState, false));
            }
        }
    }

    /* (GO|GOTO|TOO|ON)*.ON.TOO = ((G.O)|(G.O.T.O)|(T.O.O)|(O.N))*.O.N.T.O.O     */
    public String transformString(String text) {
        if (!text.contains("|")) return text;
        List<Character> listChars = new ArrayList<>();
        char[] tempChars = text.toCharArray();
        for (char c : tempChars) {
            listChars.add(c);
        }

        for (int i = 0; i < listChars.size(); i++) {
            if (listChars.get(i) == '|') {
                int j = i + 1;
                while (j < listChars.size()) {
                    if (listChars.get(j) == '|' || listChars.get(j) == ')') {
                        listChars.add(j, ')');
                        j = listChars.size();
                    } else
                        j++;
                }
                listChars.add(i + 1, '(');
            }
        }
        int first = listChars.indexOf('|');
        int z = first - 1;
        listChars.add(first, ')');
        while (z >= 0) {
            if (listChars.get(z) == '(') {
                listChars.add(z, '(');
                z = -1;
            } else
                z--;
        }

        String str = listChars.stream().map(e -> e.toString()).collect(Collectors.joining());
        return str;

    }

    public boolean conditions(char first, char second) {
        if (Character.isDigit(first) && Character.isDigit(second)) return true;
        if (Character.isDigit(first) && Character.isLetter(second)) return true;
        if (Character.isLetter(first) && Character.isLetter(second)) return true;
        if (Character.isLetter(first) && Character.isDigit(second)) return true;
        if ((first == '*' || first == '+') && Character.isDigit(second)) return true;
        return (first == '*' || first == '+') && Character.isLetter(second);
    }

    public String int2String(int numb) {
        char[] charArray = IntStream.rangeClosed('A', 'Z')
                .mapToObj(c -> "" + (char) c).collect(Collectors.joining()).toCharArray();

        String state = String.valueOf(charArray[numb]);
        return state;
    }
}
