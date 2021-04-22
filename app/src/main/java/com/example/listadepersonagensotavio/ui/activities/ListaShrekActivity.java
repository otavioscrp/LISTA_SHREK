package com.example.listadepersonagensotavio.ui.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.listadepersonagensotavio.R;
import com.example.listadepersonagensotavio.dao.PersonagemDAO;
import com.example.listadepersonagensotavio.model.Personagem;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import static com.example.listadepersonagensotavio.ui.activities.ConstantesActivities.CHAVE_PERSONAGEM;

//importar de dentro da programação java
public class ListaShrekActivity extends AppCompatActivity {

    public static final String TITULO_APPBAR = "LISTA DE PERSONAGENS OTAVIO";
    private final PersonagemDAO dao = new PersonagemDAO();
    private ArrayAdapter<Personagem> adapter;

    //override é colocado para buscar a classe dentro do codigo
    @Override
    //buscar da superclasse essa informação
    protected void onCreate(@NonNull Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //criar layout dentro de file
        setContentView(R.layout.activity_lista_shrek);

        //configurando titulo
        setTitle(TITULO_APPBAR);
        CONFIGURAFABNOVOPERSONAGEM();
        configuraLista();

        //criaçao de listas (lista dos personagens)
        //List<String> personagem = new ArrayList<>(Arrays.asList("Shrek", "Fiona", "Burro"));
        //ao clicar no botao, enviar para o formulario
        // METODO USANDO TEXT VIEW (NÃO É NECESSÁRIO DEVIDO A CRIAÇÃO DA LISTA):
        //pegar a id e o index da lista, representando cada personagem
    }

    private void CONFIGURAFABNOVOPERSONAGEM() {
        FloatingActionButton botaoNovoPersonagem = findViewById(R.id.fab_add);
        botaoNovoPersonagem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                abreFormulario();
            }
        });
    }

    //abrir o formulario
    private void abreFormulario() {
        startActivity(new Intent(this, FormularioShrekActivity.class));
    }


    //salvar os dados mesmo apos pressionar o botão 'BACK' do celular

    @Override
    protected void onResume() {
        super.onResume();
        atualizaPersonagem();
    }

    private void atualizaPersonagem() {
        adapter.clear();
        adapter.addAll(dao.todos());
    }

    //fazer remoção direto do DAO
    private void remove(Personagem personagem) {
        dao.remove(personagem);
        adapter.remove(personagem);
    }

    @Override
    //quando clicar no item da lista aparecer a opção 'remover'
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        //menu.add("Remover");
        getMenuInflater().inflate(R.menu.activity_lista_personagem_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        configuraMenu(item);
        return super.onContextItemSelected(item);
    }

    private void configuraMenu(MenuItem item) {
        int itemId1 = item.getItemId();
        //CharSequence tituloDoMenu = item.getTitle();
        //aparecer mensagem para remoção do usuario
        if (itemId1 == R.id.activity_lista_shrek_menu_remover) {

            new AlertDialog.Builder(this)
                    .setTitle("Removendo Personagem")
                    .setMessage("Tem certeza que deseja remover?")
                    .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
                            Personagem personagemEscolhido = adapter.getItem(menuInfo.position);
                            remove(personagemEscolhido);
                        }
                    })
                    .setNegativeButton("Não", null)
                    .show();
        }
    }

    private void configuraLista() {
        //referenciar o index
        ListView listaDePersonagens = findViewById(R.id.LISTA);

        //trazer a informação para dentro da lista usando o recurso "ADAPTER" (apresenta de forma ordenada as informações para dentro do componente)

        //final List<Personagem> personagens = dao.todos();
        listaDePersonagens(listaDePersonagens);
        configuraItemPorClique(listaDePersonagens);
        registerForContextMenu(listaDePersonagens);
    }

    private void configuraItemPorClique(ListView listaDePersonagens) {
        listaDePersonagens.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Personagem personagemEscolhido = (Personagem) adapterView.getItemAtPosition(position);
                abreFormularioEditar(personagemEscolhido);
            }
        });
    }

    private void abreFormularioEditar(Personagem personagemEscolhido) {
        Intent vaiParaFormulario = new Intent(ListaShrekActivity.this, FormularioShrekActivity.class);
        vaiParaFormulario.putExtra(CHAVE_PERSONAGEM, personagemEscolhido);
        startActivity(vaiParaFormulario);
    }

    private void listaDePersonagens(ListView listaDePersonagens) {
        //setando o personagem

        //cria função adapter
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
        listaDePersonagens.setAdapter(adapter);
    }
}
