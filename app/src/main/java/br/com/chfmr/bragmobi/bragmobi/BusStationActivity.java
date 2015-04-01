package br.com.chfmr.bragmobi.bragmobi;

import android.content.Intent;
import android.content.IntentSender;
import android.location.Location;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import android.provider.Settings;

public class BusStationActivity extends ActionBarActivity implements
        GooglePlayServicesClient.ConnectionCallbacks,
        GooglePlayServicesClient.OnConnectionFailedListener {

    GoogleMap mGoogleMap;
    LocationClient mLocationCliente;
    LatLng  mOrigem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus_station);

        Intent it = getIntent();

        SupportMapFragment fragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mGoogleMap = fragment.getMap();
        // mGoogleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        // mOrigem = new LatLng(-22.9523, -46.5425);
        // updateMap();

        mLocationCliente = new LocationClient(this, this, this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_bus_station, menu);
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

    @Override
    protected void onStart(){
        super.onStart();
        mLocationCliente.connect();
    }

    @Override
    protected void onStop(){
        mLocationCliente.disconnect();
        super.onStop();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){

        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == PlayServicesUtils.REQUEST_CODE_ERRO_PLAY_SERVICES
                && resultCode == RESULT_OK){
            mLocationCliente.connect();
        }
    }

    @Override
    public void onConnected(Bundle dataBundle){
        Log.d("APP_BUS", "onConnected");

        Location location = mLocationCliente.getLastLocation();

        if(mLocationCliente.isConnected() == false){
            startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
        }

        if(location != null){
            mOrigem = new LatLng(location.getLatitude(), location.getLongitude());
            updateMap();
        } else {
                Log.d("APP_BUS", "location else onConnected");
        }

    }

    @Override
    public void onDisconnected(){
        Log.d("APP_BUS", "onDisconnected");
    }

    @Override
    public  void onConnectionFailed(ConnectionResult connectionResult){

            if(connectionResult.hasResolution()){
                try {
                    connectionResult.startResolutionForResult(this,
                            PlayServicesUtils.REQUEST_CODE_ERRO_PLAY_SERVICES);
                } catch (IntentSender.SendIntentException e) {
                    e.printStackTrace();
                }
            } else {
                PlayServicesUtils.showMesageOfErro(this, connectionResult.getErrorCode());
            }
    }

    private void updateMap(){

        Log.d("APP_BUS", "updateMap");
        BitmapDescriptor icon = BitmapDescriptorFactory.fromResource(R.drawable.ic_action_bus);

        mGoogleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        // mOrigem = new LatLng(-22.9523, -46.5425);

        mGoogleMap.addMarker(new MarkerOptions()
                .position(mOrigem)
                .icon(icon)
                .title("Av. Paulista")
                .snippet("São Paulo"));

        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(mOrigem)
                .zoom(9.0f)
                .bearing(90)
                .tilt(45)
                .build();

        mGoogleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
    }

}
