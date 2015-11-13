package br.usjt.aula13;

import android.widget.ImageView;
import android.widget.TextView;

public class ViewHolder {
    private ImageView fotoCarro;
    private TextView nomeCarro, detalhesCarro;

    public ViewHolder(ImageView fotoCarro, TextView nomeCarro, TextView detalhesCarro) {
        this.fotoCarro = fotoCarro;
        this.nomeCarro = nomeCarro;
        this.detalhesCarro = detalhesCarro;
    }

    public ImageView getFotoCarro() {
        return fotoCarro;
    }

    public void setFotoCarro(ImageView fotoCarro) {
        this.fotoCarro = fotoCarro;
    }

    public TextView getNomeCarro() {
        return nomeCarro;
    }

    public void setNomeCarro(TextView nomeCarro) {
        this.nomeCarro = nomeCarro;
    }

    public TextView getDetalhesCarro() {
        return detalhesCarro;
    }

    public void setDetalhesCarro(TextView detalhesCarro) {
        this.detalhesCarro = detalhesCarro;
    }


}
