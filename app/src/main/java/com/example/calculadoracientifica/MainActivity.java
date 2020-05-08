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

        Button btn_0 = findViewById(R.id.btn_0);
        Button btn_1 = findViewById(R.id.btn_1);
        Button btn_2 = findViewById(R.id.btn_2);
        Button btn_3 = findViewById(R.id.btn_3);
        Button btn_4 = findViewById(R.id.btn_4);
        Button btn_5 = findViewById(R.id.btn_5);
        Button btn_6 = findViewById(R.id.btn_6);
        Button btn_7 = findViewById(R.id.btn_7);
        Button btn_8 = findViewById(R.id.btn_8);
        Button btn_9 = findViewById(R.id.btn_9);
        Button btn_suma = findViewById(R.id.btn_suma);
        Button btn_resta = findViewById(R.id.btn_resta);
        Button btn_mul = findViewById(R.id.btn_mul);
        Button btn_div = findViewById(R.id.btn_div);
        Button btn_c = findViewById(R.id.btn_c);
        Button btn_ac = findViewById(R.id.btn_ac);

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
                model.ejecutarOperacion(operador.POW);
                break;
            case R.id.btn_sqrt:
                model.ejecutarOperacion(operador.SQRT);
                break;
            case R.id.btn_suma:
                model.ejecutarOperacion(operador.SUMA);
                break;
            case R.id.btn_resta:
                model.ejecutarOperacion(operador.RESTA);
                break;
            case R.id.btn_mul:
                model.ejecutarOperacion(operador.MUL);
                break;
            case R.id.btn_div:
                model.ejecutarOperacion(operador.DIV);
                break;
            case R.id.btn_par:
                Toast.makeText(this, "()", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_decimal:
                model.escribirPunto();
                break;
            case R.id.btn_porcent:
                model.ejecutarOperacion(operador.MOD);
                break;
            case R.id.btn_sin:
                model.ejecutarOperacion(operador.SIN);
                break;
            case R.id.btn_cos:
                model.ejecutarOperacion(operador.COS);
                break;
            case R.id.btn_tan:
                model.ejecutarOperacion(operador.TAN);
                break;
            case R.id.btn_csc:
                model.ejecutarOperacion(operador.CSC);
                break;
            case R.id.btn_sec:
                model.ejecutarOperacion(operador.SEC);
                break;
            case R.id.btn_ctg:
                model.ejecutarOperacion(operador.CTG);
                break;
            case R.id.btn_fact:
                model.ejecutarOperacion(operador.FACT);
                break;
            case R.id.btn_igual:
                model.ejecutarIgual();
                break;
        }
    }
}
