package br.com.chfmr.bragmobi.bragmobi.model; /**
 * Created by carlosfm on 08/02/15.
 */

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;

import br.com.chfmr.bragmobi.bragmobi.Http.AppHttp;

public class ScheduleLineBus implements Serializable {

    public static final String ENVIROMENT = "PROD";

    public static final String ENVIROMENT_DEV = "http://127.0.0.1:8070/bragmobi/";
    public static final String ENVIROMENT_PROD = "http://apionibus.comercial.ws/bragmobi/";
    public static final String ENVIROMENT_URL = ENVIROMENT == "DEV" ? ENVIROMENT_DEV : ENVIROMENT_PROD;


    public static final String HORARIOS_DA_LINHA_URL_JSON = ENVIROMENT_URL + "linhas-de-onibus/horarios-da-linha/linha/1";

    public int fk_id_linha;
    public String horario;
    public String sentido;
    public String cor_linha_label;
    public String dias_da_semana;

    public ScheduleLineBus(int fk_id_linha, String horario, String sentido,
                           String cor_linha_label, String dias_da_semana){
        this.fk_id_linha = fk_id_linha;
        this.horario = horario;
        this.sentido = sentido;
        this.cor_linha_label = cor_linha_label;
        this.dias_da_semana = dias_da_semana;
    }

    @Override
    public String toString(){
        return horario;
    }

    public static List<ScheduleLineBus> carregarHorariosLinhaOnibusJson(){

        try{
            HttpURLConnection connecting = AppHttp.connect(HORARIOS_DA_LINHA_URL_JSON);
            int resposta = connecting.getResponseCode();

            Log.i("APPBUS", "resposta connect:" + resposta);
            Log.i("APPBUS", "HttpURLConnection.HTTP_OK:" + HttpURLConnection.HTTP_OK);

            if(resposta == HttpURLConnection.HTTP_OK){
                InputStream is = connecting.getInputStream();
                JSONObject json = new JSONObject(AppHttp.bytesToString(is));
                Log.i("APPBUS", "carregarHorariosLinhaOnibusJson" + json);
                return readJsonHorariosLineBus(json);
            }

        } catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public static List<ScheduleLineBus> readJsonHorariosLineBus(JSONObject json) throws JSONException {

        List<ScheduleLineBus> listaHorariosDeLinhaDeOnibus = new ArrayList<ScheduleLineBus>();

        JSONArray jsonHorariosLinhasDeOnibus = json.getJSONArray("horarios-da-linha");

        Log.i("APPBUS", "jsonLinhasDeOnibus:" + jsonHorariosLinhasDeOnibus.length());

        for(int contador = 0; contador < jsonHorariosLinhasDeOnibus.length(); contador++){

            Log.i("APPBUS", "readJsonLineBus contador:" + contador);

            JSONObject objetoHorariosLinhaDeOnibus = jsonHorariosLinhasDeOnibus.getJSONObject(contador);

            ScheduleLineBus linha = new ScheduleLineBus(
                    objetoHorariosLinhaDeOnibus.getInt("fk_id_linha"),
                    objetoHorariosLinhaDeOnibus.getString("horario"),
                    objetoHorariosLinhaDeOnibus.getString("sentido"),
                    objetoHorariosLinhaDeOnibus.getString("cor_linha_label"),
                    objetoHorariosLinhaDeOnibus.getString("dias_da_semana")
            );

            listaHorariosDeLinhaDeOnibus.add(linha);
        }

        return listaHorariosDeLinhaDeOnibus;
    }

}
