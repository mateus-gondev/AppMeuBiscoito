package com.example.appmeubiscoito;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DB_NAME    = "biscoito.db";
    private static final int    DB_VERSION = 1;
    private static final String TABLE      = "frases";
    private static final String COL_ID     = "id";
    private static final String COL_TEXTO  = "texto";

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE + " ("
                + COL_ID    + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COL_TEXTO + " TEXT NOT NULL)");

        // Frases iniciais para não começar vazio
        String[] iniciais = {
                "A felicidade está nos pequenos detalhes.",
                "Cada dia é uma nova chance de ser melhor.",
                "Sorria, pois o universo está do seu lado.",
                "O esforço de hoje é o sucesso de amanhã.",
                "Confie no seu caminho."
        };
        for (String f : iniciais) {
            ContentValues cv = new ContentValues();
            cv.put(COL_TEXTO, f);
            db.insert(TABLE, null, cv);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldV, int newV) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE);
        onCreate(db);
    }

    // Retorna uma frase aleatória
    public Frase getFraseAleatoria() {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(
                "SELECT * FROM " + TABLE + " ORDER BY RANDOM() LIMIT 1", null);
        Frase frase = null;
        if (cursor.moveToFirst()) {
            frase = new Frase(
                    cursor.getInt(cursor.getColumnIndexOrThrow(COL_ID)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COL_TEXTO))
            );
        }
        cursor.close();
        return frase;
    }

    // Lista todas as frases
    public List<Frase> getAllFrases() {
        List<Frase> lista = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE, null);
        while (cursor.moveToNext()) {
            lista.add(new Frase(
                    cursor.getInt(cursor.getColumnIndexOrThrow(COL_ID)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COL_TEXTO))
            ));
        }
        cursor.close();
        return lista;
    }

    // Inserir nova frase
    public boolean inserir(String texto) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COL_TEXTO, texto);
        return db.insert(TABLE, null, cv) != -1;
    }

    // Editar frase existente
    public boolean editar(int id, String novoTexto) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COL_TEXTO, novoTexto);
        return db.update(TABLE, cv, COL_ID + "=?",
                new String[]{String.valueOf(id)}) > 0;
    }

    // Remover frase
    public boolean remover(int id) {
        SQLiteDatabase db = getWritableDatabase();
        return db.delete(TABLE, COL_ID + "=?",
                new String[]{String.valueOf(id)}) > 0;
    }
}