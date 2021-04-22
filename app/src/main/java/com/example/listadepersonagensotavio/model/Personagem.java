package com.example.listadepersonagensotavio.model;

import androidx.annotation.NonNull;

import java.io.Serializable;

public class Personagem implements Serializable {


    //atribuição de variaveis
    private String nome;
    private String altura;
    private String nascimento;
    private String descricao;
    private int id = 0;

    //parametros sendo passados para o construtor
    public Personagem(String nome, String altura, String nascimento, String descricao) {


        //setando variaveis
        this.nome = nome;
        this.altura = altura;
        this.nascimento = nascimento;
        this.descricao = descricao;
    }

    public Personagem() {
    }


    @NonNull
    @Override

    //converte nome para string sendo exibido

    public String toString() {
        return nome;
    }
    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public String getAltura() {
        return altura;
    }
    public void setAltura(String altura) {
        this.altura = altura;
    }
    public String getNascimento() {
        return nascimento;
    }
    public void setNascimento(String nascimento) {
        this.nascimento = nascimento;
    }
    public String getDescricao() {
        return descricao;
    }
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }



    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }


    public boolean IdValido() { return id > 0; }


    //codigo para eliminar bug de aparecer um link para a resposta do formulario
    /*public String getNome() {
        return nome;
    }

    public String getAltura() {
        return altura;
    }

    public String getNascimento() {
        return nascimento;
    }

    public String getDescricao() {
        return descricao;
    }*/
}
