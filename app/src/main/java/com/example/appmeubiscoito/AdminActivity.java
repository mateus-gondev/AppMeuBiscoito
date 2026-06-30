package com.example.appmeubiscoito;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.textfield.TextInputEditText;
import java.util.List;

public class AdminActivity extends AppCompatActivity {
    // Declaração das Variáveis
    DatabaseHelper db;
    TextInputEditText etFrase;
    RecyclerView recycler;
    FraseAdapter adapter;
    List<Frase> lista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        db      = new DatabaseHelper(this);
        etFrase = findViewById(R.id.etFrase);
        recycler = findViewById(R.id.recyclerFrases);
        recycler.setLayoutManager(new LinearLayoutManager(this));

        carregarLista();

        findViewById(R.id.btnSalvar).setOnClickListener(v -> {
            String texto = etFrase.getText().toString().trim();
            if (texto.isEmpty()) {
                Toast.makeText(this, "Digite uma frase!", Toast.LENGTH_SHORT).show();
                return;
            }
            if (db.inserir(texto)) {
                etFrase.setText("");
                Toast.makeText(this, "Frase salva!", Toast.LENGTH_SHORT).show();
                carregarLista();
            }
        });
    }

    private void carregarLista() {
        lista = db.getAllFrases();
        adapter = new FraseAdapter(this, lista, db, this::carregarLista);
        recycler.setAdapter(adapter);
    }

}