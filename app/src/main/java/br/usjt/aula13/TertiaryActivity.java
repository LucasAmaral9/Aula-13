package br.usjt.aula13;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.text.NumberFormat;
import java.util.Locale;

import br.usjt.aula13.R;
import br.usjt.aula13.Carro;
import br.usjt.aula13.CarroRequester;


//Tertiary È terceira como secondary È segunda
//DetalhesCarroActivity
public class TertiaryActivity extends ActionBarActivity{

    TextView carroMarca;
    ImageView carroImageView;
    TextView carroFabricante;
    TextView carroPreco;
    TextView carroPrecoCon;
    TextView carroEstado;
    CarroRequester requester;
    ProgressBar mProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tertiary);

        Intent intent = getIntent();
        final Carro carro = (Carro)intent.getSerializableExtra(SecondaryActivity.CARROS);
        setupViews(carro);

        requester = new CarroRequester();
        if(requester.isConnected((AdapterView.OnItemClickListener) this)) {

            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        mProgress.setVisibility(View.VISIBLE);
                        final Bitmap img = requester.getImage(carro.getImagem());
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                carroImageView.setImageBitmap(img);
                                mProgress.setVisibility(View.INVISIBLE);
                            }
                        });

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        } else {
            Resources res = getResources();
            Drawable drawable = res.getDrawable(R.drawable.carro00);
            carroImageView.setImageDrawable(drawable);
            Toast toast = Toast.makeText(this, "Rede indispon√≠vel!", Toast.LENGTH_LONG);
            toast.show();
        }

    }

    private void setupViews(Carro carro) {
        carroMarca = (TextView) findViewById(R.id.txt_carro_marca);
        carroMarca.setText(carro.getMarca());
        carroImageView = (ImageView) findViewById(R.id.carro_image_view);
        Drawable drawable = Util.getDrawable(this, carro.getImagem());
        carroImageView.setImageDrawable(drawable);
        carroFabricante = (TextView) findViewById(R.id.txt_carro_fabricante);
        carroFabricante.setText(carro.getFabricante());
        carroPreco = (TextView) findViewById(R.id.txt_carro_preco);
        Locale locale = new Locale("pt", "BR");
        NumberFormat formatter = NumberFormat.getCurrencyInstance(locale);
        carroPreco.setText(""+formatter.format(carro.getPreco()));
        carroPrecoCon = (TextView) findViewById(R.id.txt_carro_precoCon);
        carroPrecoCon.setText(""+formatter.format(carro.getPrecoCon()));
        carroEstado = (TextView) findViewById(R.id.txt_carro_estado);
        carroEstado.setText(carro.getEstado());
        mProgress = (ProgressBar) findViewById(R.id.carregando_carro);
        mProgress.setVisibility(View.INVISIBLE);
    }

}