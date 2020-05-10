/*
 * Josue Ramirez Chacon
 * Diego Babb Jimenez
 * Clase ViewModel para una calculadora cientifica
 * Dicha calculadora realiza las operaciones en el orden dado por el usuario
 * por ende no toma en cuenta la prioridad de las operaciones
 * */

package com.example.calculadoracientifica;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.example.calculadoracientifica.eval.MathExpr;
import com.example.calculadoracientifica.eval.Token;
import com.example.calculadoracientifica.eval.operator;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Model {
    private String expresion;
    private MutableLiveData<String> pantalla;
    private double resultado;
    private boolean error;


    public Model(@NonNull LifecycleOwner owner, @NonNull Observer<? super String> observer) {
        this.expresion = "";
        this.pantalla = new MutableLiveData<>();
        this.pantalla.observe(owner, observer);
        this.pantalla.postValue("");
        this.error = false;
    }

    public String textoActual() {
        return pantalla.getValue();
    }

    public void limpiarPantalla() {
        pantalla.setValue("");
    }

    public void eliminarUltimo() {
        if(error) return;

        if (textoActual().length() > 0)
            pantalla.postValue(textoActual().substring(0, textoActual().length() - 1));
        expresion = expresion.substring(0, expresion.length() - 1);
    }

    public void escribirPunto() {
        if (error) return;

        if (textoActual().length() > 0) {
            if (!textoActual().contains("."))
                pantalla.setValue(textoActual() + ".");
            expresion += ".";
        }
    }

    public void escribirPi() {
        if (error) return;

        expresion += "3.14159";
        pantalla.setValue(textoActual() + "π");
    }

    public void escribirOperador(String token) {
        if (error) return;

        pantalla.setValue(textoActual() + token);
        expresion += (" " + token + " ");
    }

    public void escribirNumero(String token) {
        if (error) return;

        pantalla.setValue(textoActual() + token);
        expresion += token;
    }

    public void escribirOperador(operator op) {
        if(error) return;

        String[] temp = escribirOperadorUnicoOperando(op);
        pantalla.setValue(textoActual() + temp[0]);
        expresion += " " + temp[1] + " ( ";
    }

    // Se llamara al presionar el boton de igual
    public void ejecutarIgual() {
        try {
            MathExpr calc = new MathExpr(expresion);
            resultado = calc.getResultado();
            mostrarResultado();
        } catch (Exception e) {
            mostrarError();
        }

    }

    private void mostrarError() {
        pantalla.setValue("ERROR ( Press C to reset )");
        expresion = "";
        resultado = 0;
        error = true;
    }

    // Resetea todos los valores de la calculadora
    public void reset() {
        limpiarPantalla();
        expresion = "";
        resultado = 0;
        error = false;
    }

    // Muestra en pantalla las funciones matematicas especiales con el fin de escribir dentro de ellas
    private String[] escribirOperadorUnicoOperando(operator op) {
        String[] simbolos = new String[2];

        switch (op) {
            case SQRT:
                simbolos[0] = "√(";
                simbolos[1] = "sqrt ";
                break;
            case FACT:
                simbolos[0] = "fact(";
                simbolos[1] = "fact ";
                break;
            case SIN:
                simbolos[0] = "sin(";
                simbolos[1] = "sin ";
                break;
            case COS:
                simbolos[0] = "cos(";
                simbolos[1] = "cos ";
                break;
            case TAN:
                simbolos[0] = "tan(";
                simbolos[1] = "tan ";
                break;
            case CSC:
                simbolos[0] = "csc(";
                simbolos[1] = "csc ";
                break;
            case SEC:
                simbolos[0] = "sec(";
                simbolos[1] = "sec ";
                break;
            case CTG:
                simbolos[0] = "ctg(";
                simbolos[1] = "ctg ";
                break;
        }
        return simbolos;
    }

    // Muestra el resultado formateado en pantalla.
    private void mostrarResultado() {
        String resul = String.valueOf(resultado).trim();
        if (resul.matches("-?\\d*([.][0])$")) {
            String intNumber = resul.substring(0, resul.indexOf('.'));
            pantalla.postValue(intNumber);
        } else {
            BigDecimal bd = new BigDecimal(resultado).setScale(2, RoundingMode.HALF_UP);
            pantalla.postValue(String.valueOf(bd.doubleValue()));
        }
    }

}
