package br.com.chfmr.bragmobi.bragmobi;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import br.com.chfmr.bragmobi.bragmobi.model.StationBus;
import br.com.chfmr.bragmobi.bragmobi.Http.AppHttp;

/**
 * Created by carlosfm on 14/04/15.
 */
public class StationBusFragment extends Fragment implements
        Response.Listener<JSONObject>, Response.ErrorListener {

    List<StationBus> mStationBus;
    ListView mListView;
    TextView mTextMesage;
    ProgressBar mProgressBar;
    ArrayAdapter<StationBus> mAdapter;

    boolean mIsRunning;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View layout = inflater.inflate(R.layout.fragment_station_bus, container, false);
        mProgressBar = (ProgressBar)layout.findViewById(R.id.progressBar);
        mTextMesage = (TextView)layout.findViewById(android.R.id.empty);
        mListView = (ListView)layout.findViewById(android.R.id.list);
        mListView.setEmptyView(mTextMesage);
        return layout;

        // Inflate the layout for this fragment
        // return inflater.inflate(R.layout.fragment_station_bus, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (mStationBus == null) {
            mStationBus = new ArrayList<StationBus>();
        }
        mAdapter = new StationBusAdapter(getActivity(), mStationBus);
        mListView.setAdapter(mAdapter);

        if (!mIsRunning) {
            if (AppHttp.hasConect(getActivity())) {
                iniciarDownload();
            } else {
                mTextMesage.setText("Sem conexão");
            }
        } else {
            exibirProgress(true);
        }
    }

    public void iniciarDownload() {
        mIsRunning = true;
        exibirProgress(true);
        RequestQueue queue = VolleySingleton.getInstance(getActivity()).getRequestQueue();

        JsonObjectRequest request =
                new JsonObjectRequest(
                        Request.Method.GET,    // Requisição via HTTP_GET
                        StationBus.URL_JSON_STATION_BUS,  // url da requisição
                        null,  // JSONObject a ser enviado via POST
                        this,  // Response.Listener
                        this); // Response.ErrorListener
        queue.add(request);
    }

    private void exibirProgress(boolean exibir) {
        if (exibir) {
            mTextMesage.setText("Baixando informações dos pontos de ônibus...");
        }
        mTextMesage.setVisibility(exibir ? View.VISIBLE : View.GONE);
        mProgressBar.setVisibility(exibir ? View.VISIBLE : View.GONE);
    }

    @Override
    public void onErrorResponse(VolleyError volleyError) {
        mIsRunning = false;
        exibirProgress(false);
        mTextMesage.setText("Falha ao obter os pontos de ônibus");
    }

    @Override
    public void onResponse(JSONObject jsonObject) {
        mIsRunning = false;
        exibirProgress(false);
        try {
            List<StationBus> stations = StationBus.readJsonStationBus(jsonObject);
            mStationBus.clear();
            mStationBus.addAll(stations);
            mAdapter.notifyDataSetChanged();

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
