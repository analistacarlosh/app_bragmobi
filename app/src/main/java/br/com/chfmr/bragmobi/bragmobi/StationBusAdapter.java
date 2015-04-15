package br.com.chfmr.bragmobi.bragmobi;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;

import java.util.List;

import br.com.chfmr.bragmobi.bragmobi.model.StationBus;

/**
 * Created by carlosfm on 14/04/15.
 */
public class StationBusAdapter extends ArrayAdapter<StationBus> {

    private ImageLoader mLoader;
    private TextView txtLogradouro;
    private TextView txtNumeroProximo;
    private TextView txtTipo;
    private TextView txtObservacao;

    public StationBusAdapter(Context contexto, List<StationBus> StationBus){
        super(contexto, 0, StationBus);
        mLoader = VolleySingleton.getInstance(contexto).getmImageLoader();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){

        Context ctx = parent.getContext();

        if(convertView == null){
            Log.d("NGVL", "View Nova StationBusAdapter => position: " + position);
            convertView = LayoutInflater.from(ctx).inflate(R.layout.item_list_station_bus, null);
        }

        // NetworkImageView img = (NetworkImageView)convertView.findViewById(R.id.imgCapa);
        txtLogradouro       = (TextView)convertView.findViewById(R.id.txtLogradouro);
        txtNumeroProximo    = (TextView)convertView.findViewById(R.id.txtNumeroProximo);
        txtTipo             = (TextView)convertView.findViewById(R.id.txtTipo);
        txtObservacao       = (TextView)convertView.findViewById(R.id.txtObservacao);

        StationBus station = getItem(position);
        txtLogradouro.setText(station.logradouro);
        txtNumeroProximo.setText(station.numero_proximo);
        txtTipo.setText(station.tipo);
        txtObservacao.setText(station.observacao);
        // img.setImageUrl(station.imagem, mLoader);

        return convertView;
    }
}
