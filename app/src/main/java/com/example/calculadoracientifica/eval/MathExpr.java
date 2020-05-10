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
import java.util.Stack;

/**
 * @author jorac
 */
public class MathExpr {

    private String infija;
    private String postfija;
    private double resultado;

    public MathExpr(String expr) throws Exception {
        this.infija = expr;
        this.postfija = generatePostfix();
        this.resultado = generateValue();
    }

    public String getPostfija() {
        return postfija;
    }

    public double getResultado() {
        return resultado;
    }

    private List<Token> toPostFix() throws Exception {

        List<Token> postFix = new ArrayList<>(); // Salida
        List<Token> tokens = Token.tokenize(infija);
        Stack<Token> pila = new Stack<>(); // Pila usada para resolver el algoritmo 

        for (Token t : tokens) {
            if (t.isNumber()) {
                postFix.add(t);
            } else if (t.isOperator()) {
                if (t.getOperator().isOpenParen()) {
                    pila.push(t);
                } else if (t.getOperator().isClosedParen()) {

                    while (!pila.isEmpty() && !pila.peek().getOperator().isOpenParen()) {
                        postFix.add(pila.pop());
                    }

                    pila.pop(); // Sacamos al ( de la pila

                } else {
                    while (!pila.isEmpty() && t.getOperator().weight() <= pila.peek().getOperator().weight()) {
                        postFix.add(pila.pop());
                    }
                    pila.push(t);
                }
            }
        }

        while (!pila.isEmpty()) {
            postFix.add(pila.pop());
        }

        return postFix;
    }

    private String generatePostfix() throws Exception {
        String salida = "";
        for (Token s : toPostFix()) {
            salida += s + " ";
        }

        return salida;
    }

    private double generateValue() throws Exception {
        Stack<Double> pila = new Stack<>();
        for (Token t : toPostFix()) {
            if (t.isNumber()) {
                pila.push(t.getValue());
            } else if (t.isOperator()) {
                if (t.getOperator().isDouble()) {
                    Double segundo_operando = pila.pop();
                    Double primer_operando = pila.pop();

                    switch (t.getOperator()) {
                        case SUMA:
                            pila.push(primer_operando + segundo_operando);
                            break;
                        case RESTA:
                            pila.push(primer_operando - segundo_operando);
                            break;
                        case MUL:
                            pila.push(primer_operando * segundo_operando);
                            break;
                        case DIV:
                            pila.push(primer_operando / segundo_operando);
                            break;
                        case MOD:
                            pila.push(primer_operando % segundo_operando);
                            break;
                        case POW:
                            pila.push(Math.pow(primer_operando, segundo_operando));
                            break;
                    }
                } else if (t.getOperator().isUnique()) {
                    Double operando_unico = pila.pop();
                    switch (t.getOperator()) {
                        case SQRT:
                            pila.push(Math.sqrt(operando_unico));
                            break;
                        case SIN:
                            pila.push(Math.sin(operando_unico));
                            break;
                        case COS:
                            pila.push(Math.cos(operando_unico));
                            break;
                        case TAN:
                            pila.push(Math.tan(operando_unico));
                            break;
                        case CSC:
                            pila.push(1 / Math.sin(operando_unico));
                            break;
                        case SEC:
                            pila.push(1 / Math.cos(operando_unico));
                            break;
                        case CTG:
                            pila.push(1 / Math.tan(operando_unico));
                            break;
                        case FACT:
                            pila.push(fact(operando_unico, 1.0));
                            break;
                        default:
                            pila.push(operando_unico);
                    }
                }
            }
        }

        return round(pila.pop());
    }

    private Double fact(Double n, Double r) {
        return (n <= 1) ? r : fact(n - 1, n * r);
    }

    private double round(Double d) {
        BigDecimal bd = new BigDecimal(d).setScale(2, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
}
