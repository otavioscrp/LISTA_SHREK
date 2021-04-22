package com.example.listadepersonagensotavio.dao;

import android.app.Person;

import com.example.listadepersonagensotavio.model.Personagem;

import java.util.ArrayList;
import java.util.List;

//CRIAÇAO DA ARRAYLIST PERSONAGENS
//todas vez que for feita adição de personagens, é adicionada dentro do conteudo 'personagem salvo',
// onde é adicionado para a lista 'personagens'

public class PersonagemDAO {

    private final static List<Personagem> personagens = new ArrayList<>();
    private static int contadorDeId = 1;


    public void salva(Personagem personagemSalvo) {


        personagemSalvo.setId(contadorDeId);
        personagens.add(personagemSalvo);
        atualizaId();


    }

    private void atualizaId() {
        contadorDeId++;
    }

    //criação do metodo para editar

    public void editar(Personagem personagem) {
        Personagem personagemEscolhido = buscaPersonagemPorId(personagem);

        //se for diferente de nulo, pegar a posição ideal
        if (personagemEscolhido != null) {
            int posicaoDoPersonagem = personagens.indexOf(personagemEscolhido);
            personagens.set(posicaoDoPersonagem, personagem);
        }


    }


//procurar o personagem de forma mais precisa
    private Personagem buscaPersonagemPorId(Personagem personagem) {
        for (Personagem p : personagens) {
            //pegar posicionamento do metodo
            if (p.getId() == personagem.getId()) {
                return p;
            }
        }
        return null;
    }

    public List<Personagem> todos() {
        return new ArrayList<>(personagens);
    }

    //utilização de remoção dentro destaclasse
    public void remove(Personagem personagem)
    {
        Personagem personagemDevolvido = buscaPersonagemPorId(personagem);
        if(personagemDevolvido != null)
        {
            personagens.remove(personagemDevolvido);
        }
    }
}
