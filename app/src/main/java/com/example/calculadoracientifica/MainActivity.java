/*
 * Josue Ramirez Chacon
 * Diego Babb Jimenez
 * */

package com.example.calculadoracientifica;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.calculadoracientifica.eval.operator;

import java.util.Objects;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Model model;
    private TextView pantalla;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calculadora);
        Objects.requireNonNull(getSupportActionBar()).hide();
        this.pantalla = findViewById(R.id.pantalla);


        GridLayout padre = findViewById(R.id.GridLayout1);
        for (int i = 0; i < padre.getChildCount(); i++) {
            View actual = padre.getChildAt(i);
            if (actual instanceof Button)
                actual.setOnClickListener(this);
        }

        // Enlazamos la pantalla al modelo
        final Observer<String> pantallaObserver = new Observer<String>() {
            @Override
            public void onChanged(@Nullable final String newName) {
                pantalla.setText(newName);
            }
        };

        this.model = new Model(this, pantallaObserver);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_0:
                model.escribirNumero("0");
                break;
            case R.id.btn_1:
                model.escribirNumero("1");
                break;
            case R.id.btn_2:
                model.escribirNumero("2");
                break;
            case R.id.btn_3:
                model.escribirNumero("3");
                break;
            case R.id.btn_4:
                model.escribirNumero("4");
                break;
            case R.id.btn_5:
                model.escribirNumero("5");
                break;
            case R.id.btn_6:
                model.escribirNumero("6");
                break;
            case R.id.btn_7:
                model.escribirNumero("7");
                break;
            case R.id.btn_8:
                model.escribirNumero("8");
                break;
            case R.id.btn_9:
                model.escribirNumero("9");
                break;
            case R.id.btn_c:
                model.reset();
                break;
            case R.id.btn_ac:
                model.eliminarUltimo();
                break;
            case R.id.btn_pow:
                model.escribirOperador("^");
                break;
            case R.id.btn_suma:
                model.escribirOperador("+");
                break;
            case R.id.btn_resta:
                model.escribirOperador("-");
                break;
            case R.id.btn_mul:
                model.escribirOperador("*");
                break;
            case R.id.btn_div:
                model.escribirOperador("/");
                break;
            case R.id.btn_pi:
                model.escribirPi();
                break;
            case R.id.btn_parenAbierto:
                model.escribirOperador("(");
                break;
            case R.id.btn_parenCerrado:
                model.escribirOperador(")");
                break;
            case R.id.btn_decimal:
                model.escribirPunto();
                break;
            case R.id.btn_porcent:
                model.escribirOperador("%");
                break;
            case R.id.btn_sin:
                model.escribirOperador(operator.SIN);
                break;
            case R.id.btn_cos:
                model.escribirOperador(operator.COS);
                break;
            case R.id.btn_tan:
                model.escribirOperador(operator.TAN);
                break;
            case R.id.btn_csc:
                model.escribirOperador(operator.CSC);
                break;
            case R.id.btn_sec:
                model.escribirOperador(operator.SEC);
                break;
            case R.id.btn_ctg:
                model.escribirOperador(operator.CTG);
                break;
            case R.id.btn_fact:
                model.escribirOperador(operator.FACT);
                break;
            case R.id.btn_sqrt:
                model.escribirOperador(operator.SQRT);
                break;
            case R.id.btn_igual:
                model.ejecutarIgual();
                break;

        }
    }
}
