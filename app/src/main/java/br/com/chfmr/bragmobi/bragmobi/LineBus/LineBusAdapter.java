package br.com.chfmr.bragmobi.bragmobi.LineBus;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import br.com.chfmr.bragmobi.bragmobi.R;
import br.com.chfmr.bragmobi.bragmobi.model.LineBus;

/**
 * Created by carlosfm on 04/02/15.
 */
public class LineBusAdapter extends ArrayAdapter<LineBus> {

    Context contexto;
    List<LineBus> linhasDeOnibus;

    public LineBusAdapter(Context contexto, List<LineBus> linhasDeOnibus){

        super(contexto, 0, linhasDeOnibus);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){

        LineBus linha = getItem(position);

        ViewHolder holder = null;
        if(convertView == null){
            Log.d("NGVL", "View Nova => position: " + position);
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_linha, null);

        holder = new ViewHolder();
        holder.imgIcon = (ImageView) convertView.findViewById(R.id.imgIcon);
        holder.txtNome = (TextView) convertView.findViewById(R.id.txtName);
        // holder.txtNumero = (TextView) convertView.findViewById(R.id.txtNumero);
        // holder.txtSentidoIda = (TextView) convertView.findViewById(R.id.txtSentidoIda);
        holder.txtSentidoVolta = (TextView) convertView.findViewById(R.id.txtSentidoVolta);
        convertView.setTag(holder);
        } else {
            Log.d("NGVL", "View existente => position: "+ position);
            holder = (ViewHolder)convertView.getTag();
        }

        Resources res = getContext().getResources();
        Drawable draw = res.getDrawable(R.drawable.ic_action_bus);

        holder.imgIcon.setImageDrawable(draw);
        holder.txtNome.setText(linha.nome);
       // holder.txtNumero.setText(Integer.toString(linha.numero) + " - ");
       // holder.txtSentidoIda.setText(linha.sentido_id);
        holder.txtSentidoVolta.setText("Sentido: " + linha.sentido_volda);

        return convertView;
    }

    static class ViewHolder {
        ImageView imgIcon;
        TextView txtNome;
        TextView txtNumero;
        TextView txtSentidoIda;
        TextView txtSentidoVolta;
    }
}
