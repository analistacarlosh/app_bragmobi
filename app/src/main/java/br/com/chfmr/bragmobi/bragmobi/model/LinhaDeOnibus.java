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

public class LinhaDeOnibus implements Serializable {

    public static final String LINHAS_ONIBUS_URL_JSON = "http://apionibus.comercial.ws/bragmobi/linhas-de-onibus/offset/0/limit/10";

    public String nome;
    public int numero;
    public String sentido_id;
    public String sentido_volda;
    public String imagem;

    public LinhaDeOnibus(String nome, int numero, String sentido_id,
                         String sentido_volda, String imagem){
        this.nome = nome;
        this.numero = numero;
        this.sentido_id = sentido_id;
        this.sentido_volda = sentido_volda;
        this.imagem = imagem;
    }

    @Override
    public String toString(){
        return nome + numero;
    }

    public static List<LinhaDeOnibus> carregarLinhaOnibusJson(){

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

    public static List<LinhaDeOnibus> readJsonLineBus(JSONObject json) throws JSONException {

        List<LinhaDeOnibus> listaDeLinhaDeOnibus = new ArrayList<LinhaDeOnibus>();

        JSONArray jsonLinhasDeOnibus = json.getJSONArray("linhas_de_onibus");

        Log.i("APPBUS", "jsonLinhasDeOnibus:" + jsonLinhasDeOnibus.length());

        for(int contador = 0; contador < jsonLinhasDeOnibus.length(); contador++){

            Log.i("APPBUS", "readJsonLineBus contador:" + contador);

            JSONObject objetoLinhaDeOnibus = jsonLinhasDeOnibus.getJSONObject(contador);

            LinhaDeOnibus linha = new LinhaDeOnibus(
                    objetoLinhaDeOnibus.getString("nome"),
                    objetoLinhaDeOnibus.getInt("numero"),
                    objetoLinhaDeOnibus.getString("sentido_ida"),
                    objetoLinhaDeOnibus.getString("sendito_volta"),
                    "http://www.guiadebraganca.com.br/public/imagens/icon_facebook.png"
                    // objetoLinhaDeOnibus.getString("imagem")
            );

            listaDeLinhaDeOnibus.add(linha);
        }

        return listaDeLinhaDeOnibus;
    }
}
