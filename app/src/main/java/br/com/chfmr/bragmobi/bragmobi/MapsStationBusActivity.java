package br.com.chfmr.bragmobi.bragmobi;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


public class MapsStationBusActivity extends ActionBarActivity {

    GoogleMap mGoogleMap;
    LatLng mOrigem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps_station_bus);

        SupportMapFragment fragment = (SupportMapFragment)
                getSupportFragmentManager().findFragmentById(R.id.map);

        mGoogleMap = fragment.getMap();
        mGoogleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        // Latitude: -22.9523, Longitude: -46.5425
        mOrigem = new LatLng(-22.9523, -46.5425);
        updateMaps();

    }

    private void updateMaps(){

        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(mOrigem)
                .zoom(18)
                .bearing(90)
                .tilt(45)
                .build();

        mGoogleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

        mGoogleMap.addMarker(new MarkerOptions()
                .position(mOrigem)
                .title("Av. Pires Pimentel")
                .snippet("Bragança Pta."));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_maps_station_bus, menu);
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
