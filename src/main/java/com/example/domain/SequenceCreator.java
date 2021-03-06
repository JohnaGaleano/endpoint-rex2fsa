/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.domain;

import com.example.model.DoubleNode;
import com.example.model.ThompsonCreator;

import java.util.LinkedList;
import java.util.List;

 
public class SequenceCreator
{
    public String secuence;
    public String simbols;
    public String secSimbols;
    
    public SequenceCreator(String sec)
    {
        simbols = "()+*|.±┤■"; // ┤ es fin de secuencia. ± es secuencia vacía. ■ es la secuencia nula
        secuence = sec;
        secSimbols = "";
        separateSimbols();
    }
    
    private void separateSimbols ()
    {
        for (int i = 0; i < secuence.length(); i++)
        {
            String aux = secuence.substring(i, i+1);
            if(!simbols.contains(aux) && !secSimbols.contains(aux))
            {
                secSimbols += aux;
            }
        }
    }
    
    public DoubleNode CreateThompson() throws Exception
    {
        DoubleNode start = ThompsonCreator.nullConstruction();
        DoubleNode end = start.link1;
        end.uptakingState = true;
        String aux = secuence.substring(0,1);
        int counter = 1;
        switch (aux) 
        {
            case "(":
                divideSecWithStartingParenthesis(start, secuence, counter);
                break;
            default:
                String sec1 = aux;
                aux = secuence.substring(1, 2);
                executeDivision(aux, sec1, secuence, 2, start);
        }
        
        while (SequenceCreator.isExpandible(start, null))
        {
            SequenceCreator.expandTransitions(start, null);
        }
        
        return start;
    }
    
    public static int searchForParenthesisClose (int counter, String secuence) throws Exception
    {
        String aux = secuence.substring(counter,counter+1);
        counter++;
        int openP = 0;
        while (!(openP == 0 && aux.equals(")")) && counter < secuence.length())
        {
            if(aux.equals("("))
                openP++;
            else if (aux.equals(")") && openP > 0)
                openP--;
            else if (aux.equals(")") && openP == 0)
                break;
            aux = secuence.substring(counter, counter+1);
            counter++;
        }
        if (!aux.equals(")") || openP > 0)
            throw new Exception("Falta un cierre paréntesis, verifique la expresión ingresada.");
        return counter;
    }
    
    public static boolean isExpandible (DoubleNode start, List<DoubleNode> visited)
    {
        if (visited == null)
        {
            visited = new LinkedList<>();
        }
        
        if (!visited.contains(start))
        {
            visited.add(start);
            if (start.link1 == null && start.link2 == null)
            {
                return false;
            }
            else
            {
                if (start.link1 != null)
                {
                    if (start.transition1.length() > 1)
                        return true;
                    if (SequenceCreator.isExpandible(start.link1, visited))
                        return true;
                }
                
                if (start.link2 != null)
                {
                    if (start.transition2.length() > 1)
                        return true;
                    return SequenceCreator.isExpandible(start.link2, visited);
                }
            }
        }
        return false;
    }
    
    public static void expandTransitions (DoubleNode start, List<DoubleNode> visited) throws Exception
    {
        if (visited == null)
        {
            visited = new LinkedList<>();
        }
        
        if (!visited.contains(start))
        {
            visited.add(start);
            if (start.link1 == null && start.link2 == null)
                return;
            else
            {
                if (start.link1 != null)
                {
                    if (start.transition1.length() > 1)
                        expandTransition(start);
                    SequenceCreator.expandTransitions(start.link1, visited);
                }
                
                if (start.link2 != null)
                {
                    if (start.transition2.length() > 1)
                        expandTransition(start);
                    SequenceCreator.expandTransitions(start.link2, visited);
                }
            }
        }
    }
    
    public static void expandTransition (DoubleNode start) throws Exception
    {
        String secuence = start.transition1;
        if (secuence.length() <= 1)
            return;
        
        String aux = secuence.substring(0, 1);
        if (aux.equals("("))
        {
            divideSecWithStartingParenthesis(start, secuence, 1);
        } else
        {
            String sec1 = aux;
            aux = secuence.substring(1, 2);
            executeDivision(aux, sec1, secuence, 2, start);
        }
    }
    
    public static void divideSecWithStartingParenthesis (DoubleNode start, String secuence, int counter) throws Exception
    {
        int open = counter-1;
        counter = searchForParenthesisClose (counter, secuence); // Busca su respectivo cierre paréntesis y devuelve la posición.
        String sec1 = secuence.substring(open, counter);
        if (counter == secuence.length())
        {
            sec1 = secuence.substring(open + 1, counter - 1);
            start.transition1 = sec1;
        }   
        else
        {
            String aux = secuence.substring(counter, counter + 1);
            counter++;
            executeDivision(aux, sec1, secuence, counter, start);
        }  
    }
    
    public static void executeDivision (String operator, String sec1, String secuence, int counter,DoubleNode start) throws Exception
    {
        if (operator.equals("+") || operator.equals("*"))
        {
            if (counter == secuence.length())
                executeTimesOrPlusOperator(operator, sec1, start);
            else
            {
                sec1 += operator;
                operator = secuence.substring(counter, counter + 1);
                counter++;
                String sec2 = secuence.substring(counter);
                executeConcatOrUnionOperator(operator, sec1, sec2, start);
            }
        }
        else
        {
            String sec2 = secuence.substring(counter);
            executeConcatOrUnionOperator(operator, sec1, sec2, start);
        }
    }
    
    public static void executeTimesOrPlusOperator (String operator, String secuence, DoubleNode start) throws Exception
    {
        DoubleNode end = start.link1;
        if (operator.equals("*"))
            ThompsonCreator.asteriskConstruction(secuence, start, end);
        else if (operator.equals("+"))
            ThompsonCreator.plusConstruction(secuence, start, end);
        else
        {
            System.out.println("El operador ingresado no se reconoce: " + operator);
            throw new Exception ("Se esperaba + ó * y llegó: " + operator);
        }
    }
    
    public static void executeConcatOrUnionOperator (String operator, String sec1, String sec2, DoubleNode start) throws Exception
    {
        DoubleNode end = start.link1;
        if (operator.equals("|"))
            ThompsonCreator.unionConstruction(sec1, sec2, start, end);
        else if (operator.equals("."))
            ThompsonCreator.concatConstruction(sec1, sec2, start, end);
        else
        {
            System.out.println("El operador ingresado no se reconoce: " + operator);
            throw new Exception ("Se esperaba . ó | y llegó: " + operator);
        }
    }
}
