package br.com.chfmr.bragmobi.bragmobi;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import br.com.chfmr.bragmobi.bragmobi.model.LinhaDeOnibus;
import br.com.chfmr.bragmobi.bragmobi.Http.AppHttp;

/**
 * Created by carlosfm on 15/02/15.
 */
public class LinhasDeOnibusListFragment extends ListFragment {

    private static final String TAG = "LIST_LINHAS";
    LinhasDeOnibusTask mTask;
    List<LinhaDeOnibus> mLinhaDeOnibus;
    ListView mListView;
    TextView mTextMensagem;
    ProgressBar mProgressBar;
    LinhaDeOnibusAdapter mAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        View layout     = inflater.inflate(R.layout.fragment_linhas_de_onibus_list, null);
        mTextMensagem   = (TextView)layout.findViewById(android.R.id.empty);
        mProgressBar    = (ProgressBar)layout.findViewById(R.id.progressBar);
        mListView       = (ListView)layout.findViewById(android.R.id.list);
        //android.R.id.list
        mListView.setEmptyView(mTextMensagem);
        return layout;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);

        if(mLinhaDeOnibus == null){
            mLinhaDeOnibus = new ArrayList<LinhaDeOnibus>();
        }

        mAdapter = new LinhaDeOnibusAdapter(this.getActivity(), mLinhaDeOnibus);
        mListView.setAdapter(mAdapter);

        if(mTask == null){
            if(AppHttp.hasConect(this.getActivity())){
                iniciarDownload();
            } else {
                mTextMensagem.setText("Sem conexão com a internet");
            }
        } else if (mTask.getStatus() == AsyncTask.Status.RUNNING){
            exibirProgress(true);
        }
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        int idLinha = mLinhaDeOnibus.get(position).id_linha;

        Intent intent = new Intent(this.getActivity(), LineBusDetailActivity.class);
        intent.putExtra("idLineBus", idLinha);
        startActivity(intent);
    }

    private void exibirProgress(boolean exibir) {
        if (exibir) {
            mTextMensagem.setText("Baixando informações...");
        }
        mTextMensagem.setVisibility(exibir ? View.VISIBLE : View.GONE);
        mProgressBar.setVisibility(exibir ? View.VISIBLE : View.GONE);
    }

    public void iniciarDownload() {
        if (mTask == null ||  mTask.getStatus() != AsyncTask.Status.RUNNING) {
            mTask = new LinhasDeOnibusTask();
            mTask.execute();
        }
    }

    class LinhasDeOnibusTask extends AsyncTask<Void, Void, List<LinhaDeOnibus>>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            exibirProgress(true);
        }

        @Override
        protected List<LinhaDeOnibus> doInBackground(Void... strings) {
            return LinhaDeOnibus.carregarLinhaOnibusJson();
        }

        @Override
        protected void onPostExecute(List<LinhaDeOnibus> linhas) {
            super.onPostExecute(linhas);
            exibirProgress(false);
            if (linhas != null) {
                mLinhaDeOnibus.clear();
                mLinhaDeOnibus.addAll(linhas);
                mAdapter.notifyDataSetChanged();
            } else {
                mTextMensagem.setText("Falha ao obter dados.");
            }
        }
    }
}
