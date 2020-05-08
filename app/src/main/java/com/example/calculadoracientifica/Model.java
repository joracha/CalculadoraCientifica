/*
* Josue Ramirez Chacon
* Diego Babb Jimenez
* Clase ViewModel para una calculadora cientifica
* Dicha calculadora realiza las operaciones en el orden dado por el usuario
* por ende toma en cuenta la prioridad de las operaciones
* */


package com.example.calculadoracientifica;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import java.math.BigDecimal;
import java.math.RoundingMode;


enum operador {
    SUMA, RESTA, MUL, DIV, POW, MOD, SQRT, FACT, SIN, COS, TAN, CSC, SEC, CTG;

    public boolean esDeDobleOperando() {
        return this == operador.SUMA || this == operador.RESTA || this == operador.MUL
                || this == operador.DIV || this == operador.POW || this == operador.MOD;
    }
}

public class Model {
    private String operando_unico; // Operando para las operaciones de un operando
    private double primer_operando;
    private double segundo_operando;
    private operador ultimoOperador;
    private operador penultimoOperador;
    private double resultado;
    private boolean error;
    private MutableLiveData<String> pantalla; // Estara enlazado al texto de la pantalla en el view

    public Model(@NonNull LifecycleOwner owner, @NonNull Observer<? super String> observer) {
        this.pantalla = new MutableLiveData<String>();
        this.pantalla.observe(owner, observer);
        this.pantalla.postValue("");
        this.primer_operando = this.segundo_operando = this.resultado = 0;
        this.operando_unico = "";
        this.ultimoOperador = penultimoOperador = null;
        this.error = false;
    }

    public String textoActual() {
        return pantalla.getValue();
    }

    public int cantidadDigitosActual() {
        return textoActual().length();
    }

    public void limpiarPantalla() {
        pantalla.setValue("");
    }

    // Elimina el ultimo digito en pantalla, si esta dentro de un parentisis elimina el ultimo digito del parentisis
    public void eliminarUltimo() {
        if(error) return;

        int paren = textoActual().indexOf(")");
        if (paren != -1) {
            if (textoActual().matches(".*\\d.*")) {
                pantalla.postValue(textoActual().substring(0, textoActual().length() - 2) + ")");
            } else {
                limpiarPantalla();
                ultimoOperador = penultimoOperador = null;//
                operando_unico = "";
            }
        } else {
            if (cantidadDigitosActual() > 0)
                pantalla.postValue(textoActual().substring(0, textoActual().length() - 1));
        }
    }

    // Escribe un numero en pantalla tomando en cuenta si esta en medio de alguna funcion trigonometrica o las otras
    public void escribirNumero(String num) {
        if(error) return;

        if (ultimoOperador != null && !ultimoOperador.esDeDobleOperando()) {
            String nuev_texto = textoActual().replaceAll("\\d|\\)|\\s|\\.|!", "");
            operando_unico += num;
            nuev_texto += operando_unico + ")";
            pantalla.setValue(nuev_texto);
            if (ultimoOperador == operador.FACT) pantalla.setValue(textoActual() + "!");
        } else
            pantalla.setValue(textoActual() + num);
    }

    // Para manejar numeros con decimales
    public void escribirPunto() {
        if (ultimoOperador != null && !ultimoOperador.esDeDobleOperando()) {
            String nuev_texto = textoActual().replaceAll("\\d|\\)|\\s|\\.|!", "");
            if (operando_unico.length() > 0) {
                if (!operando_unico.contains(".")) {
                    operando_unico += ".";
                }
            }
            nuev_texto += operando_unico;
            pantalla.setValue(nuev_texto + ")");
        } else {
            if (cantidadDigitosActual() > 0) {
                if (!textoActual().contains("."))
                    pantalla.setValue(textoActual() + ".");
            }
        }
    }

    // Se ejectuara al presionar cualquier boton con las operaciones
    // llegara por parametro dicho operador presionado
    public void ejecutarOperacion(operador op) {
        if(error) return;

        try {
            if (op.esDeDobleOperando()) {
                if (ultimoOperador == null) {
                    primer_operando = Double.parseDouble(textoActual());
                    limpiarPantalla();
                } else {
                    if (ultimoOperador.esDeDobleOperando()) {
                        segundo_operando = Double.parseDouble(textoActual());
                        primer_operando = evaluar(ultimoOperador);
                        limpiarPantalla();
                    } else {
                        limpiarPantalla();
                        if (penultimoOperador != null && penultimoOperador.esDeDobleOperando()) {
                            segundo_operando = evaluar(ultimoOperador);
                            primer_operando = evaluar(penultimoOperador);
                            penultimoOperador = ultimoOperador;
                        } else {
                            primer_operando = evaluar(ultimoOperador);
                            penultimoOperador = op;
                        }
                        operando_unico = "";
                    }
                }
            } else {
                if (ultimoOperador != null && ultimoOperador.esDeDobleOperando()) {
                    penultimoOperador = ultimoOperador;
                }
                limpiarPantalla();
                escribirOperadorUnicoOperando(op);
            }
            ultimoOperador = op;

        } catch (java.lang.NumberFormatException ex) {
            mostrarMensajeError();
        }
    }

    // Se llamara al presionar el boton de igual
    public void ejecutarIgual() {
        if(error) return;

        try {
            if (operando_unico.isEmpty()) {
                segundo_operando = Double.parseDouble(textoActual());
                resultado = ultimoOperador != null ? evaluar(ultimoOperador) : segundo_operando;
            } else {
                if (penultimoOperador != null) {
                    segundo_operando = evaluar(ultimoOperador);
                    resultado = evaluar(penultimoOperador);
                } else
                    resultado = ultimoOperador != null ? evaluar(ultimoOperador) : primer_operando;
            }
            mostrarResultado();
            ultimoOperador = penultimoOperador = null;
            primer_operando = segundo_operando = 0;
            operando_unico = "";

        } catch (java.lang.NumberFormatException ex) {
            mostrarMensajeError();
        }
    }
    // Encargado de realizar las operaciones dependiendo del estado de los operandos
    private double evaluar(operador op) {
        switch (op) {
            case SUMA:
                return primer_operando + segundo_operando;
            case RESTA:
                return primer_operando - segundo_operando;
            case MUL:
                return primer_operando * segundo_operando;
            case DIV:
                return primer_operando / segundo_operando;
            case MOD:
                return primer_operando % segundo_operando;
            case POW:
                return Math.pow(primer_operando, segundo_operando);
            case SQRT:
                return Math.sqrt(Double.parseDouble(operando_unico));
            case SIN:
                return Math.sin(Math.toRadians(Double.parseDouble(operando_unico)));
            case COS:
                return Math.cos(Math.toRadians(Double.parseDouble(operando_unico)));
            case TAN:
                return Math.tan(Math.toRadians(Double.parseDouble(operando_unico)));
            case CSC:
                return 1 / Math.sin(Math.toRadians(Double.parseDouble(operando_unico)));
            case SEC:
                return 1 / Math.cos(Math.toRadians(Double.parseDouble(operando_unico)));
            case CTG:
                return 1 / Math.tan(Math.toRadians(Double.parseDouble(operando_unico)));
            case FACT:
                return fact((int) Double.parseDouble(operando_unico), 1);
            default:
                return segundo_operando;
        }
    }

    private double fact(int n, int r) {
        return (n <= 1) ? r : fact(n - 1, n * r);
    }

    // Muestra el resultado formateado en pantalla.
    private void mostrarResultado() {
        String resul = String.valueOf(resultado).trim();
        if (resul.matches("\\d*([.][0])$")) {
            String intNumber = resul.substring(0, resul.indexOf('.'));
            pantalla.postValue(intNumber);
        } else {
            BigDecimal bd = new BigDecimal(resultado).setScale(2, RoundingMode.HALF_UP);
            pantalla.postValue(String.valueOf(bd.doubleValue()));
        }
    }
    // Resetea todos los valores de la calculadora
    public void reset() {
        limpiarPantalla();
        primer_operando = segundo_operando = resultado = 0;
        ultimoOperador = penultimoOperador = null;
        operando_unico = "";
        error = false;
    }

    // Muestra en pantalla las funciones matematicas especiales con el fin de escribir dentro de ellas
    private void escribirOperadorUnicoOperando(operador op) {
        String simbolo = "";
        switch (op) {
            case SQRT:
                simbolo = "√( )";
                break;
            case FACT:
                simbolo = "( )!";
                break;
            case SIN:
                simbolo = "SIN( )";
                break;
            case COS:
                simbolo = "COS( )";
                break;
            case TAN:
                simbolo = "TAN( )";
                break;
            case CSC:
                simbolo = "CSC( )";
                break;
            case SEC:
                simbolo = "SEC( )";
                break;
            case CTG:
                simbolo = "CTG( )";
                break;
        }
        pantalla.setValue(simbolo);
    }

    private void mostrarMensajeError(){
        pantalla.setValue("ERROR! (Press C to reset)");
        error = true;
        primer_operando = segundo_operando = resultado = 0;
        ultimoOperador = penultimoOperador = null;
        operando_unico = "";
    }
}
