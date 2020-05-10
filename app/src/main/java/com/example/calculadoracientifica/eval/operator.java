/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.calculadoracientifica.eval;

/**
 * @author jorac
 */
public enum operator {
    SUMA, RESTA, MUL, DIV, POW, MOD, SQRT, FACT, SIN, COS, TAN, CSC, SEC, CTG, OPEN, CLOSE;

    public int weight() {

        if (this == operator.SUMA || this == operator.RESTA) {
            return 1;
        } else if (this == operator.MUL || this == operator.DIV) {
            return 2;
        } else if (this == operator.POW || this == operator.MOD) {
            return 3;
        } else if (isUnique()) {
            return 4;
        } else {
            return 0;
        }

    }

    public boolean isUnique() {
        return this == operator.SQRT || this == operator.FACT
                || this == operator.SIN || this == operator.COS || this == operator.TAN || this == operator.CSC
                || this == operator.SEC || this == operator.CTG;
    }

    public boolean isDouble() {
        return this == operator.SUMA || this == operator.RESTA || this == operator.MUL
                || this == operator.DIV || this == operator.POW || this == operator.MOD;
    }

    public boolean isOpenParen() {
        return this == operator.OPEN;
    }

    public boolean isClosedParen() {
        return this == operator.CLOSE;
    }

}
