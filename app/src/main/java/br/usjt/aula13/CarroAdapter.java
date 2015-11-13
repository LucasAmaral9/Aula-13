package br.usjt.aula13;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.SectionIndexer;
import android.widget.TextView;

import java.text.NumberFormat;
import java.util.Hashtable;
import java.util.Locale;

public class CarroAdapter extends BaseAdapter implements SectionIndexer {

    Activity context;
    Carro[] carros;
    Object[] sectionHeaders;
    Hashtable<Integer, Integer> positionForSectionMap;
    Hashtable<Integer, Integer> sectionForPositionMap;

    public CarroAdapter(Activity context, Carro[] carros){
        this.context = context;
        this.carros = carros;
        sectionHeaders = SectionIndexBuilder.BuildSectionHeaders(carros);
        positionForSectionMap = SectionIndexBuilder.BuildPositionForSectionMap(carros);
        sectionForPositionMap = SectionIndexBuilder.BuildSectionForPositionMap(carros);

    }

    @Override
    public int getCount() {
        return carros.length;
    }

    @Override
    public Object getItem(int position) {
        if(position >= 0 && position < carros.length)
            return carros[position];
        else
            return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //o list view recicla os layouts para melhor performance
        //o layout reciclado vem no parametro convert view
        View view = convertView;
        //se nao recebeu um layout para reutilizar deve inflar um
        if(view == null) {
            //um inflater transforma um layout em uma view
            LayoutInflater inflater = (LayoutInflater) context.getSystemService
                    (Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.linha_carro, parent, false);

            ImageView fotoCarro = (ImageView)view.findViewById(R.id.fotoCarroImageView);
            TextView marcaCarro = (TextView)view.findViewById(R.id.MarcaCarroTextView);
            TextView fabricanteCarro = (TextView)view.findViewById(R.id.FabricanteCarroTextView);
            //faz cache dos widgets instanciados na tag da view para reusar quando houver reciclagem
            view.setTag(new ViewHolder(fotoCarro, marcaCarro, fabricanteCarro ));
        }
        //usa os widgets cacheados na view reciclada
        ViewHolder holder = (ViewHolder)view.getTag();
        //carrega os novos valores
        Drawable drawable = Util.getDrawable(context, carros[position].getImagem());
        holder.getFotoCarro().setImageDrawable(drawable);
        Locale locale = new Locale("pt", "BR");
        NumberFormat formatter = NumberFormat.getCurrencyInstance(locale);
        holder.getNomeCarro().setText(carros[position].getMarca());
        holder.getDetalhesCarro().setText(String.format("%s - %s", carros[position].getFabricante(),formatter.format(carros[position].getPreco())));

        return view;
    }

    @Override
    public Object[] getSections() {
        return sectionHeaders;
    }

    @Override
    public int getPositionForSection(int sectionIndex) {
        return positionForSectionMap.get(sectionIndex).intValue();
    }

    @Override
    public int getSectionForPosition(int position) {
        return sectionForPositionMap.get(position).intValue();
    }
}