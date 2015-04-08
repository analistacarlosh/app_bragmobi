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

// Percelable
public class LineBus implements Serializable {

    public static final String ENVIROMENT = "PROD";

    public static final String ENVIROMENT_DEV = "http://127.0.0.1:8070/bragmobi/";
    public static final String ENVIROMENT_PROD = "http://apionibus.comercial.ws/bragmobi/";
    public static final String ENVIROMENT_URL = ENVIROMENT == "DEV" ? ENVIROMENT_DEV : ENVIROMENT_PROD;

    public static final String LINHAS_ONIBUS_URL_JSON = ENVIROMENT_URL + "linhas-de-onibus/offset/0/limit/10";
    public static final String DETAIL_LINE_BUS_URL    = ENVIROMENT_URL + "linhas-de-onibus/linha/";

    public int id_linha;
    public String nome;
    public int numero;
    public String sentido_id;
    public String sentido_volda;
    public String imgIcon;
    public String linha_observacoes;
    public String linha_itinerarios_sentido_ida;
    public String linha_itinerarios_sentido_volta;

    public LineBus(int id_linha, String nome, int numero, String sentido_id,
                   String sentido_volda, String imgIcon,
                   String linha_observacoes, String linha_itinerarios_sentido_ida,
                   String linha_itinerarios_sentido_volta){
        this.id_linha = id_linha;
        this.nome = nome;
        this.numero = numero;
        this.sentido_id = sentido_id;
        this.sentido_volda = sentido_volda;
        this.imgIcon = imgIcon;
        this.linha_observacoes = linha_observacoes;
        this.linha_itinerarios_sentido_ida = linha_itinerarios_sentido_ida;
        this.linha_itinerarios_sentido_volta = linha_itinerarios_sentido_volta;
    }

    @Override
    public String toString(){
        return nome + numero;
    }

    public static List<LineBus> carregarLinhaOnibusJson(){

        try{
            HttpURLConnection connecting = AppHttp.connect(LINHAS_ONIBUS_URL_JSON);
            int resposta = connecting.getResponseCode();

            Log.i("APPBUS", "resposta connect:" + resposta);
            Log.i("APPBUS", "HttpURLConnection.HTTP_OK:" + HttpURLConnection.HTTP_OK);

            if(resposta == HttpURLConnection.HTTP_OK){
                InputStream is = connecting.getInputStream();
                JSONObject json = new JSONObject(AppHttp.bytesToString(is));
                Log.i("APPBUS", "carregouLinhaOnibusJson" + json);
                return readJsonLineBus(json);
            }

        } catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public static List<LineBus> readJsonLineBus(JSONObject json) throws JSONException {

        List<LineBus> listaDeLinhaDeOnibus = new ArrayList<LineBus>();

        JSONArray jsonLinhasDeOnibus = json.getJSONArray("linhas_de_onibus");

        Log.i("APPBUS", "jsonLinhasDeOnibus:" + jsonLinhasDeOnibus.length());

        for(int contador = 0; contador < jsonLinhasDeOnibus.length(); contador++){

            Log.i("APPBUS", "readJsonLineBus contador:" + contador);

            JSONObject objetoLinhaDeOnibus = jsonLinhasDeOnibus.getJSONObject(contador);

            LineBus linha = new LineBus(
                    objetoLinhaDeOnibus.getInt("id_linha"),
                    objetoLinhaDeOnibus.getString("nome"),
                    objetoLinhaDeOnibus.getInt("numero"),
                    objetoLinhaDeOnibus.getString("sentido_ida"),
                    objetoLinhaDeOnibus.getString("sendito_volta"),
                    objetoLinhaDeOnibus.getString("nome"),
                    objetoLinhaDeOnibus.getString("linha_observacoes"),
                    objetoLinhaDeOnibus.getString("linha_itinerarios_sentido_ida"),
                    objetoLinhaDeOnibus.getString("linha_itinerarios_sentido_volta")
            );

            listaDeLinhaDeOnibus.add(linha);
        }

        return listaDeLinhaDeOnibus;
    }

    public static List<LineBus> getDetailLineBus(int idLineBus){

        try {

            Log.i("APPBUS", "idLineBus: " + Integer.toString(idLineBus));

            HttpURLConnection connecting = AppHttp.connect(DETAIL_LINE_BUS_URL + Integer.toString(idLineBus));
            int resposta = connecting.getResponseCode();

            if(resposta == HttpURLConnection.HTTP_OK){
                InputStream is = connecting.getInputStream();
                JSONObject json = new JSONObject(AppHttp.bytesToString(is));
                Log.i("APPBUS", "getDetailLineBus" + json);
                return readDetailLineBus(json);
            }

        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public static List<LineBus> readDetailLineBus(JSONObject json) throws JSONException {

        List<LineBus> detailLineBus = new ArrayList<LineBus>();
        JSONArray jsonDetailLineBus = json.getJSONArray("dados-da-linha");
        JSONObject objectDetailLineBus = jsonDetailLineBus.getJSONObject(0);

            LineBus linha = new LineBus(
                    objectDetailLineBus.getInt("id_linha"),
                    objectDetailLineBus.getString("nome"),
                    objectDetailLineBus.getInt("numero"),
                    objectDetailLineBus.getString("sentido_ida"),
                    objectDetailLineBus.getString("sendito_volta"),
                    objectDetailLineBus.getString("nome"),
                    objectDetailLineBus.getString("linha_observacoes"),
                    objectDetailLineBus.getString("linha_itinerarios_sentido_ida"),
                    objectDetailLineBus.getString("linha_itinerarios_sentido_volta")
            );

        detailLineBus.add(linha);

        return detailLineBus;
    }
}
