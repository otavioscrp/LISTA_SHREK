package com.example.listadepersonagensotavio.ui.activities;

import android.app.Person;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.listadepersonagensotavio.R;
import com.example.listadepersonagensotavio.dao.PersonagemDAO;
import com.example.listadepersonagensotavio.model.Personagem;
import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;

import java.io.Serializable;

import static com.example.listadepersonagensotavio.ui.activities.ConstantesActivities.CHAVE_PERSONAGEM;

public class FormularioShrekActivity extends AppCompatActivity {

    //atribuição de variaveis para os campos inseridos, titulos e objetos
    public static final String TITULO_APPBAR_EDITA_PERSONAGEM = "EDITAR PERSONAGENS";
    public static final String TITULO_APPBAR_NOVO_PERSONAGEM = "NOVO PERSONAGENS";
    private EditText campoNome;
    private EditText campoAltura;
    private EditText campoNascimento;
    private EditText campoDescricao;
    private final PersonagemDAO dao = new PersonagemDAO();
    private Personagem personagem;

    //criação do menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_formulario_personagem_menu_salvar, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        if(itemId == R.id.actvity_formulario_personagem_menu_salvar){
            finalizarFormulario();
        }
        return super.onOptionsItemSelected(item);
    }

    //
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario_shrek);
        //extração dos  metodos criados (Ctrl + Alt + M)
        inicializacaoCampos();
        configuraBotaoSalvar();
        carregaPersonagem();

    }

    private void carregaPersonagem() {
        Intent dados = getIntent();

        if(dados.hasExtra(CHAVE_PERSONAGEM))
        {
            //setando o titulo para editar o personagem
            setTitle(TITULO_APPBAR_EDITA_PERSONAGEM);
            personagem = (Personagem) dados.getSerializableExtra(CHAVE_PERSONAGEM);
            preencheCampos();
        } else {
            //setando o titulo de novo personagem
            setTitle(TITULO_APPBAR_NOVO_PERSONAGEM);
            personagem = new Personagem();
        }
    }

    //preenchimento dos campos inseridos
    private void preencheCampos() {
        campoNome.setText(personagem.getNome());
        campoAltura.setText(personagem.getAltura());
        campoNascimento.setText(personagem.getNascimento());
        campoDescricao.setText(personagem.getDescricao());
    }
    //configuração do botão salvar
    private void configuraBotaoSalvar() {
        //importar classe button e puxar o botão da activity
        Button botaoSalvar = findViewById(R.id.button_salvar);
        //sobrescrever a superclasse onclick(chamar uma view) com override
        botaoSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finalizarFormulario();
            }
        });
    }

    //encerramento do formulario para a edição e o salvamento
    private void finalizarFormulario() {
        preencherPersonagem();
        if(personagem.IdValido())
        {
            dao.editar(personagem);
            finish();
        }
        else
            {
                dao.salva(personagem);
            }
        finish();
    }

    private void inicializacaoCampos() {
        //pegando as IDS dos campos
        campoNome       = findViewById(R.id.edittext_nome);
        campoAltura     = findViewById(R.id.edittext_altura);
        campoNascimento = findViewById(R.id.edittext_nascimento);
        campoDescricao  = findViewById(R.id.edittext_descricao);

        //limitar as respostas do usuario para apenas altura e data formatadas corretamente
        SimpleMaskFormatter smfAltura = new SimpleMaskFormatter("N,NN");
        MaskTextWatcher mtwAltura = new MaskTextWatcher(campoAltura, smfAltura);
        campoAltura.addTextChangedListener(mtwAltura);

        SimpleMaskFormatter smfNascimento = new SimpleMaskFormatter("NN/NN/NNNN");
        MaskTextWatcher mtwNascimento = new MaskTextWatcher(campoNascimento, smfNascimento);
        campoNascimento.addTextChangedListener(mtwNascimento);

    }

    private void preencherPersonagem()
    {
        //puxar a classe criada 'personagem' e puxar os parametros criados
        String nome       = campoNome.getText().toString();
        String altura     = campoAltura.getText().toString();
        String nascimento = campoNascimento.getText().toString();
        String descricao  = campoDescricao.getText().toString();

        //quando pressionar o botao, são puxadas informaçoes para cada edittext
        personagem.setNome(nome);
        personagem.setAltura(altura);
        personagem.setNascimento(nascimento);
        personagem.setDescricao(descricao);



    }


}