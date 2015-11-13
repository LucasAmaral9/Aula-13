package br.usjt.aula13;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;

import br.usjt.aula13.CarroAdapter;
import br.usjt.aula13.R;
import br.usjt.aula13.Carro;



//listaCarrosActivity
public class SecondaryActivity extends AppCompatActivity {

    ListView listView;
    Activity atividade;
    public final static String CARROS = "br.usjt.CARRO";
    Carro[] carros;
    final String servidor = "10.0.2.2:8080";

    CarroRequester requester;
    ArrayList<Carro> to;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_secondary);
        atividade = this;


        //pega a mensagem do intent
        final Intent[] intent = {getIntent()};
        carros = ((ArrayList<Carro>) intent[0].getSerializableExtra(MainActivity.CARROS)).toArray(new Carro[0]);

        //cria o listview de cervejas
        listView = (ListView) findViewById(R.id.view_lista_carro);

        CarroAdapter adapter = new CarroAdapter(this, carros);

        listView.setAdapter(adapter);

        // listener de click em um item do listview

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                final String marca = findViewById(R.id.MarcaCarroTextView).toString();

                requester = new CarroRequester();
                if(requester.isConnected(this)){
                   final Intent intent = new Intent(SecondaryActivity.this, TertiaryActivity.class);


                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                to = requester.get("http://" + servidor + "AndroidRest/selecao2.json", marca);
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        intent.putExtra(CARROS, carros);
                                        startActivity(intent);
                                    }
                                });

                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }).start();
                } else {
                    Toast toast = Toast.makeText(SecondaryActivity.this, "Rede indisponível!", Toast.LENGTH_LONG);
                    toast.show();
                }
            }



        });
    }

}