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

public class MainActivity extends AppCompatActivity {

    DatabaseHelper db;
    ImageView ivBiscoito;
    CardView cardResultado;
    TextView tvFrase;
    Button btnRecarregar, btnAdmin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new DatabaseHelper(this);
        ivBiscoito    = findViewById(R.id.ivBiscoito);
        cardResultado = findViewById(R.id.cardResultado);
        tvFrase       = findViewById(R.id.tvFrase);
        btnRecarregar = findViewById(R.id.btnRecarregar);
        btnAdmin      = findViewById(R.id.btnAdmin);

        // Clique no biscoito → mostra frase
        ivBiscoito.setOnClickListener(v -> {
            v.startAnimation(AnimationUtils.loadAnimation(this, android.R.anim.fade_in));
            mostrarFrase();
        });

        // Recarregar → esconde resultado e prepara novo clique
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