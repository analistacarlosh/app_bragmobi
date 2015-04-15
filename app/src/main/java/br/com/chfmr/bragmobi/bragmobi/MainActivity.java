package br.com.chfmr.bragmobi.bragmobi;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import br.com.chfmr.bragmobi.bragmobi.LineBus.LineBusActivity;

public class MainActivity extends ActionBarActivity
        implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btn_horarios_de_onibus = (Button) findViewById(R.id.btn_horarios_de_onibus);
        btn_horarios_de_onibus.setOnClickListener((View.OnClickListener) this);

        Button btn_bus_station = (Button) findViewById(R.id.btn_bus_station_list);
        btn_bus_station.setOnClickListener((View.OnClickListener) this);

        Button btn_disk_entrega = (Button) findViewById(R.id.btn_disk_entrega);
        btn_disk_entrega.setOnClickListener((View.OnClickListener) this);

        Button btn_contato_app = (Button) findViewById(R.id.btn_contato_app);
        btn_contato_app.setOnClickListener((View.OnClickListener) this);
    }

    @Override
    public void onClick(View view){

        Uri uri = null;
        Intent intent = null;

        switch (view.getId()){
            case R.id.btn_horarios_de_onibus:
                intent = new Intent(this, LineBusActivity.class);
                startActivity(intent);
                break;
           /* case R.id.btn_denuncia_anonima:
                intent = new Intent(this, BusStationActivity.class);
                startActivity(intent);
                break;
            */
            case R.id.btn_bus_station_list:
                intent = new Intent(this, StationBusActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_disk_entrega:
                intent = new Intent(this, StreetViewActivity.class);
                // intent = new Intent(this, CategoryActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_contato_app:
                intent = new Intent(this, LineBusActivity.class);
                // intent = new Intent(this, ContactActivity.class);
                startActivity(intent);
                break;
       }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
}
