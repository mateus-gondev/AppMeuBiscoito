package com.example.appmeubiscoito;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class FraseAdapter extends RecyclerView.Adapter<FraseAdapter.ViewHolder> {

    private List<Frase> lista;
    private Context context;
    private DatabaseHelper db;
    private Runnable onAtualizar; // callback para recarregar lista

    public FraseAdapter(Context ctx, List<Frase> lista,
                        DatabaseHelper db, Runnable onAtualizar) {
        this.context     = ctx;
        this.lista       = lista;
        this.db          = db;
        this.onAtualizar = onAtualizar;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context)
                .inflate(R.layout.item_frase, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder h, int pos) {
        Frase frase = lista.get(pos);
        h.tvTexto.setText(frase.getTexto());

        // Botão editar
        h.btnEditar.setOnClickListener(v -> {
            EditText et = new EditText(context);
            et.setText(frase.getTexto());
            new AlertDialog.Builder(context)
                    .setTitle("Editar frase")
                    .setView(et)
                    .setPositiveButton("Salvar", (d, w) -> {
                        String novo = et.getText().toString().trim();
                        if (!novo.isEmpty()) {
                            db.editar(frase.getId(), novo);
                            onAtualizar.run();
                        }
                    })
                    .setNegativeButton("Cancelar", null)
                    .show();
        });

        // Botão remover
        h.btnRemover.setOnClickListener(v ->
                new AlertDialog.Builder(context)
                        .setTitle("Remover frase")
                        .setMessage("Tem certeza que deseja remover esta frase?")
                        .setPositiveButton("Remover", (d, w) -> {
                            db.remover(frase.getId());
                            onAtualizar.run();
                        })
                        .setNegativeButton("Cancelar", null)
                        .show()
        );
    }

    @Override
    public int getItemCount() { return lista.size(); }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTexto;
        ImageButton btnEditar, btnRemover;
        ViewHolder(View v) {
            super(v);
            tvTexto    = v.findViewById(R.id.tvItemFrase);
            btnEditar  = v.findViewById(R.id.btnEditar);
            btnRemover = v.findViewById(R.id.btnRemover);
        }
    }
}