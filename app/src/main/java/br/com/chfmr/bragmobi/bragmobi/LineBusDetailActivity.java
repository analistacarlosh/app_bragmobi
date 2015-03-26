package br.com.chfmr.bragmobi.bragmobi;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TableLayout;
import android.widget.TextView;

import java.util.List;

import br.com.chfmr.bragmobi.bragmobi.Http.AppHttp;
import br.com.chfmr.bragmobi.bragmobi.model.LinhaDeOnibus;
import br.com.chfmr.bragmobi.bragmobi.model.HorariosLinhaDeOnibus;
import android.widget.TableRow;
import android.widget.TableRow.LayoutParams;


public class LineBusDetailActivity extends ActionBarActivity {

    private static final String TAG = "LIST_LINHAS";
    LinhasDeOnibusTask mTask;
    TextView mTextMensagem;
    ProgressBar mProgressBar;
    LinhaDeOnibusAdapter mAdapter;

    TextView mTextNomeLinha;
    TextView mTextItinerariosIda;
    TextView mTextItinerariosVolta;
    TextView mTextObservacao;
    List<HorariosLinhaDeOnibus> horariosLinhas;
    List<LinhaDeOnibus> detailLineBus;
    TableLayout tableLayoutHorarios;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_line_bus_detail);

        Intent it = getIntent();
        Integer idLineBus = it.getIntExtra("idLineBus", -1);

        mTextMensagem   = (TextView) findViewById(android.R.id.empty);
        mProgressBar    = (ProgressBar) findViewById(R.id.progressBar);
        mTextNomeLinha          = (TextView) findViewById(R.id.nomeLinha);
        mTextItinerariosIda     = (TextView) findViewById(R.id.content_itinerary_from);
        mTextItinerariosVolta   = (TextView) findViewById(R.id.content_itinerary_to);
        // mTextObservacao         = (TextView) findViewById(R.id.observacoes);
        tableLayoutHorarios =   (TableLayout) findViewById(R.id.tableHorarios);


                mTextNomeLinha.setVisibility(View.INVISIBLE);
        mTextItinerariosIda.setVisibility(View.INVISIBLE);
        mTextItinerariosVolta.setVisibility(View.INVISIBLE);

        if(mTask == null){
            if(AppHttp.hasConect(this)){
                iniciarDownload(idLineBus);
            } else {
                mTextMensagem.setText("Sem conexão com a internet");
            }
        } else if (mTask.getStatus() == AsyncTask.Status.RUNNING){
            exibirProgress(true);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_line_bus_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void exibirProgress(boolean exibir) {
        if (exibir) {
            mTextMensagem.setText("Baixando detalhes da linha...");
        }
        mTextMensagem.setVisibility(exibir ? View.VISIBLE : View.GONE);
        mProgressBar.setVisibility(exibir ? View.VISIBLE : View.GONE);

        mTextNomeLinha.setVisibility(exibir ? View.INVISIBLE : View.VISIBLE);
        mTextItinerariosIda.setVisibility(exibir ? View.INVISIBLE : View.VISIBLE);
        mTextItinerariosVolta.setVisibility(exibir ? View.INVISIBLE : View.VISIBLE);
    }

    public void iniciarDownload(Integer idLineBus) {
        if (mTask == null ||  mTask.getStatus() != AsyncTask.Status.RUNNING) {
            mTask = new LinhasDeOnibusTask();
            mTask.execute(idLineBus);
        }
    }

    class LinhasDeOnibusTask extends AsyncTask<Integer, Void, Boolean>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            exibirProgress(true);
        }

        @Override
        protected Boolean doInBackground(Integer... idLineBus) {

            horariosLinhas = HorariosLinhaDeOnibus.carregarHorariosLinhaOnibusJson();
            detailLineBus = LinhaDeOnibus.getDetailLineBus(idLineBus[0]);
            Log.i("APPBUS", "LineBusDetailActivity - doInBackground");

            return true;
        }

        @Override
        protected void onPostExecute(Boolean returnDoInBackground) {
            super.onPostExecute(returnDoInBackground);
            if (returnDoInBackground == true) {
                Log.i("APPBUS", "LineBusDetailActivity - onPostExecute");

                if(detailLineBus.size() > 0){
                    mTextNomeLinha.setText(detailLineBus.get(0).nome);
                    mTextItinerariosIda.setText(detailLineBus.get(0).linha_itinerarios_sentido_ida);
                    mTextItinerariosVolta.setText(detailLineBus.get(0).linha_itinerarios_sentido_volta);
                    // mTextObservacao.setText(detailLineBus.get(0).linha_observacoes);

                    tableLayoutHorarios.removeAllViews();

                    int rows = 2;
                    int cols = 2;

                    // outer for loop
                    for (int i = 1; i <= rows; i++) {

                        TableRow row = new TableRow(getBaseContext());
                        row.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
                                LayoutParams.WRAP_CONTENT));

                        // inner for loop
                        for (int j = 1; j <= cols; j++) {

                            TextView tv = new TextView(getBaseContext());
                            tv.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
                                    LayoutParams.WRAP_CONTENT));
                            tv.setBackgroundResource(R.drawable.background_button_red);
                            tv.setPadding(5, 5, 5, 5);
                            tv.setText("R " + i + ", C" + j);

                            row.addView(tv);

                        }

                        tableLayoutHorarios.addView(row);

                    }


                }

                exibirProgress(false);
            } else {
                exibirProgress(false);
                mTextMensagem.setText("Falha ao obter dados.");
            }
        }
    }
}
