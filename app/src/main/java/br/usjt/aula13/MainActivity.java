package br.usjt.aula13;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;

import br.usjt.aula13.R;
import br.usjt.aula13.Carro;
import br.usjt.aula13.CarroRequester;

public class MainActivity extends ActionBarActivity {


    Spinner spinnerEstado;
    Button consultar;
    String estado;
    ArrayList<Carro> carros;

       final String servidor = "192.168.1.4";
    //  final String servidor = "jbossews-cerveja.rhcloud.com";
 //   final String servidor = "10.0.2.2:8080";
    CarroRequester requester;
    ProgressBar mProgress;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupViews();

    }

    private void setupViews() {
        estado = "";
        consultar = (Button) findViewById(R.id.botao_enviar);
        spinnerEstado = (Spinner) findViewById(R.id.dropdown_estados);
        spinnerEstado.setOnItemSelectedListener(new EstadoSelecionado());
        mProgress = (ProgressBar) findViewById(R.id.carregando);
        mProgress.setVisibility(View.INVISIBLE);
    }

    private class EstadoSelecionado implements AdapterView.OnItemSelectedListener {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            estado = (String) parent.getItemAtPosition(position);
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }

    // constante static para identificar a mensagem
    public final static String CARROS = "br.usjt.CARROS";

    //será chamado quando o usuário clicar em enviar
    public void consultarCarros(View view) {

        final String pEstado = this.estado.equals("Escolha o estado")?"":estado;

        requester = new CarroRequester();
        if(requester.isConnected((AdapterView.OnItemClickListener) this)){
            intent = new Intent(this, SecondaryActivity.class);

            mProgress.setVisibility(View.VISIBLE);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        carros = requester.get("http://" + servidor + "AndroidRest/selecaoJSON.java", pEstado);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                intent.putExtra(CARROS, carros);
                                mProgress.setVisibility(View.INVISIBLE);
                                startActivity(intent);
                            }
                        });

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        } else {
            Toast toast = Toast.makeText(this, "Rede indispon�vel!", Toast.LENGTH_LONG);
            toast.show();
        }
    }

}