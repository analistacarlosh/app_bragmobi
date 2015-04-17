package br.com.chfmr.bragmobi.bragmobi;

import android.location.Location;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsStationBusActivity extends ActionBarActivity implements
        ConnectionCallbacks, OnConnectionFailedListener {

    protected static final String TAG = "basic-location-sample";
    protected GoogleApiClient mGoogleApiClient;
    protected Location mLastLocation;
    protected LatLng MyLatLng;

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

        buildGoogleApiClient();

        updateMaps();

    }

    private void updateMaps(){

        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(mOrigem)
                .zoom(18)
                // .bearing(90)
                // .tilt(45)
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

    /**
     * Builds a GoogleApiClient. Uses the addApi() method to request the LocationServices API.
     */
    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
               // .addApi(Drive.API)
               // .addScope(Drive.SCOPE_FILE)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();
        mGoogleApiClient.connect();

        updateMaps();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();


        if (mGoogleApiClient != null && mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
    }

    /**
     * Runs when a GoogleApiClient object successfully connects.
     */
    @Override
    public void onConnected(Bundle connectionHint) {
        // Provides a simple way of getting a device's location and is well suited for
        // applications that do not require a fine-grained location and that do not need location
        // updates. Gets the best and most recent location currently available, which may be null
        // in rare cases when a location is not available.
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if (mLastLocation != null) {
            // mLatitudeText.setText(String.valueOf(mLastLocation.getLatitude()));
            //mLongitudeText.setText(String.valueOf(mLastLocation.getLongitude()));
            Log.i(TAG, "getLatitude: " + mLastLocation.getLatitude());
            Log.i(TAG, "getLongitude:" + mLastLocation.getLongitude());

            MyLatLng = new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude());

            mGoogleMap.addMarker(new MarkerOptions()
                    .position(MyLatLng)
                    .title("Eu")
                    .snippet("EU"));

        } else {
            Toast.makeText(this, "nao acessou mLastLocation", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onConnectionFailed(ConnectionResult result) {
        // Refer to the javadoc for ConnectionResult to see what error codes might be returned in
        // onConnectionFailed.
        Log.i(TAG, "Connection failed: ConnectionResult.getErrorCode() = " + result.getErrorCode());
        Log.i(TAG, "Connection failed: ConnectionResult.getResolution() = " + result.getResolution());
    }

    @Override
    public void onConnectionSuspended(int cause) {
        // The connection to Google Play services was lost for some reason. We call connect() to
        // attempt to re-establish the connection.
        Log.i(TAG, "Connection suspended");
        if(mGoogleApiClient != null){
            mGoogleApiClient.connect();
        }
    }
}
