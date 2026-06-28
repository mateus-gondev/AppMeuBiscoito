// Modelo das Tabelas
package com.example.appmeubiscoito;

public class Frase {
    private int id;
    private String texto;

    public Frase(int id, String texto) {
        this.id = id;
        this.texto = texto;
    }

    public int getId() { return id; }
    public String getTexto() { return texto; }
    public void setTexto(String texto) { this.texto = texto; }
}