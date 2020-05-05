package com.example.calculadoracientifica;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView pantalla;
    private Button boton_ac, boton_c, boton_1, boton_2, boton_3, boton_4, boton_5, boton_6,
            boton_7, boton_8, boton_9, boton_0, boton_suma, boton_resta, boton_mul, boton_div;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calculadora);

        pantalla = findViewById(R.id.pantalla);
        boton_0 = findViewById(R.id.btn_0);
        boton_1 = findViewById(R.id.btn_1);
        boton_2 = findViewById(R.id.btn_2);
        boton_3 = findViewById(R.id.btn_3);
        boton_4 = findViewById(R.id.btn_4);
        boton_5 = findViewById(R.id.btn_5);
        boton_6 = findViewById(R.id.btn_6);
        boton_7 = findViewById(R.id.btn_7);
        boton_8 = findViewById(R.id.btn_8);
        boton_9 = findViewById(R.id.btn_9);
        boton_suma = findViewById(R.id.btn_suma);
        boton_resta = findViewById(R.id.btn_resta);
        boton_mul = findViewById(R.id.btn_mul);
        boton_div = findViewById(R.id.btn_div);
        boton_c = findViewById(R.id.btn_c);
        boton_ac = findViewById(R.id.btn_ac);

        GridLayout padre = findViewById(R.id.GridLayout1);

        for(int i = 0; i < padre.getChildCount(); i++){
            View actual = padre.getChildAt(i);
            if(actual instanceof Button){
                actual.setOnClickListener(this);
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

        }
    }
}
