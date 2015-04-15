package br.com.chfmr.bragmobi.bragmobi.model;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by carlosfm on 4/14/15.
 */
public class StationBus implements Serializable {

    public static final String URL_JSON_STATION_BUS = "http://apionibus.comercial.ws/bragmobi/bus-station/offset/1/limit/20";

    public int id_ponto;
    public String cep_logradouro;
    public String numero_proximo;
    public String numero_logradouro;
    public String observacao;
    public String quantidade;
    public String latitude;
    public String longitude;
    public String data_criacao;
    public String data_atualizacao;
    public String logradouro;
    public String tipo;

    boolean mIsRunning;

    public StationBus(int id_ponto, String cep_logradouro,  String numero_proximo,
                      String numero_logradouro, String observacao, String quantidade,
                      String latitude, String longitude, String data_criacao,
                      String data_atualizacao, String logradouro, String tipo){

        this.id_ponto           = id_ponto;
        this.cep_logradouro     = cep_logradouro;
        this.numero_proximo     = numero_proximo;
        this.numero_logradouro  = numero_logradouro;
        this.observacao         = observacao;
        this.quantidade         = quantidade;
        this.latitude           = latitude;
        this.longitude          = longitude;
        this.data_criacao       = data_criacao;
        this.data_atualizacao   = data_atualizacao;
        this.logradouro         = logradouro;
        this.tipo               = tipo;
    }

    @Override
    public String toString(){
        return logradouro + " - " + tipo;
    }

    public static List<StationBus> readJsonStationBus(JSONObject json) throws JSONException {

        List<StationBus> listStationBus = new ArrayList<StationBus>();

        JSONArray jsonStationBus = json.getJSONArray("bus_station");

        Log.i("APPBUS", "jsonStationBus:" + jsonStationBus.length());

        for(int count = 0; count < jsonStationBus.length(); count++){

            Log.i("APPBUS", "readJsonStationBus contador:" + count);

            JSONObject objectStationBus = jsonStationBus.getJSONObject(count);

            StationBus station = new StationBus(
                    objectStationBus.getInt("id_ponto"),
                    objectStationBus.getString("cep_logradouro"),
                    objectStationBus.getString("numero_proximo"),
                    objectStationBus.getString("numero_logradouro"),
                    objectStationBus.getString("observacao"),
                    objectStationBus.getString("quantidade"),
                    objectStationBus.getString("latitude"),
                    objectStationBus.getString("longitude"),
                    objectStationBus.getString("data_criacao"),
                    objectStationBus.getString("data_atualizacao"),
                    objectStationBus.getString("logradouro"),
                    objectStationBus.getString("tipo")
            );

            listStationBus.add(station);
        }

        return listStationBus;
    }
}
