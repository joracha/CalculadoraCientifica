/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.example.calculadoracientifica.eval;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

/**
 * @author jorac
 */

public class Token {

    private String token;
    private operator operator;
    private Double value;

    public Token(String token) {
        this.token = token;
        this.operator = getOperatorInit();
        this.value = getValueInit();
    }

    public operator getOperator() {
        return this.operator;
    }

    public Double getValue() {
        return value;
    }

    public boolean isOperator() {
        return operator != null;
    }

    public boolean isNumber() {
        return token.matches("^[-]?\\d+\\.?\\d*$");
    }

    public static List<Token> tokenize(String mathExpr) {

        List<Token> tokens = new ArrayList<Token>();
        String[] arrayString = mathExpr.split("\\s+", 0); // Dividimos el string en un array donde el separador es el espacio en blanco
        for (String c : arrayString) {
            tokens.add(new Token(c));
        }

        return tokens;
    }

    private Double getValueInit() {
        if (!this.isNumber()) {
            return null;
        } else {
            return Double.parseDouble(token);
        }
    }

    private operator getOperatorInit() {

        if (this.isNumber()) {
            return null;
        }

        switch (token) {
            case "+":
                return operator.SUMA;
            case "-":
                return operator.RESTA;
            case "*":
                return operator.MUL;
            case "/":
                return operator.DIV;
            case "^":
                return operator.POW;
            case "%":
                return operator.MOD;
            case "fact":
                return operator.FACT;
            case "sqrt":
                return operator.SQRT;
            case "sin":
                return operator.SIN;
            case "cos":
                return operator.COS;
            case "tan":
                return operator.TAN;
            case "sec":
                return operator.SEC;
            case "csc":
                return operator.CSC;
            case "ctg":
                return operator.CTG;
            case "(":
                return operator.OPEN;
            case ")":
                return operator.CLOSE;
            default:
                return null;
        }
    }

    @Override
    public String toString() {
        return this.token; //To change body of generated methods, choose Tools | Templates.
    }


}
