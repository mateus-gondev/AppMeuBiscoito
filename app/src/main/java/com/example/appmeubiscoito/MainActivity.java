package com.example.appmeubiscoito;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

// Declaração das Variáveis
public class MainActivity extends AppCompatActivity {
    DatabaseHelper db;
    ImageView ivBiscoito;
    CardView cardResultado;
    TextView tvFrase;
    Button btnRecarregar, btnAdmin;

    // Metodo que cria o MainActivity
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); // super chama o onCreate da AppCompatActivity, inicializa coisas internas do Android
        setContentView(R.layout.activity_main); // ja aqui to dizendo que o activity_main.xml é o visual desta tela"

        // Conectando Java com XML
        db = new DatabaseHelper(this);
        ivBiscoito    = findViewById(R.id.ivBiscoito);
        cardResultado = findViewById(R.id.cardResultado);
        tvFrase       = findViewById(R.id.tvFrase);
        btnRecarregar = findViewById(R.id.btnRecarregar);
        btnAdmin      = findViewById(R.id.btnAdmin);

        // Ele procura dentro do activity_main.xml um elemento com aquele ID e entrega o controle para a variável Java.

        // Clique no biscoito e ele mostra a frase
        ivBiscoito.setOnClickListener(v -> {
            v.startAnimation(AnimationUtils.loadAnimation(this, android.R.anim.fade_in));
            mostrarFrase(); // Metodo que mostra a frase
        });

        // Recarregar e depois esconde resultado e prepara novo clique
        btnRecarregar.setOnClickListener(v -> {
            cardResultado.setVisibility(View.GONE);
            btnRecarregar.setVisibility(View.GONE);
            tvFrase.setText("");
        });

        // Ir para tela Admin
        btnAdmin.setOnClickListener(v ->
                startActivity(new Intent(this, AdminActivity.class))
        );
    }

    // Método mostrar Frase
    private void mostrarFrase() {
        Frase frase = db.getFraseAleatoria();
        if (frase != null) {
            tvFrase.setText("\"" + frase.getTexto() + "\"");
            cardResultado.setVisibility(View.VISIBLE);
            btnRecarregar.setVisibility(View.VISIBLE);
        } else {
            tvFrase.setText("Nenhuma frase cadastrada ainda.");
            cardResultado.setVisibility(View.VISIBLE);
        }
    }
}