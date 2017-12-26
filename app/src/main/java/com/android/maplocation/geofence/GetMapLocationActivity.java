package com.android.maplocation.geofence;

import android.Manifest;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.maplocation.R;
import com.android.maplocation.bean.CheckInTimeBean;
import com.android.maplocation.bean.CheckOutTimeBean;
import com.android.maplocation.pojo.OfficeLocations;
import com.android.maplocation.serviceparams.CheckInTimeParams;
import com.android.maplocation.serviceparams.CheckOutTimeParams;
import com.android.maplocation.webservices.RetrofitClient;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingClient;
import com.google.android.gms.location.GeofencingRequest;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class GetMapLocationActivity extends AppCompatActivity
        implements
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener,
        OnMapReadyCallback/*,
            GoogleMap.OnMapClickListener*/,
        GoogleMap.OnMarkerClickListener,
        AdapterView.OnItemSelectedListener,
        ResultCallback<Status> {

    private GeofencingClient mGeofencingClient;

    private static final String TAG = GetMapLocationActivity.class.getSimpleName();

    private ArrayList<OfficeLocations> latlnglist = new ArrayList<>();
    //handler for timer
    private Handler customHandler = new Handler();
    long timeInMilliseconds = 0L;
    long timeSwapBuff = 0L;
    long updatedTime = 0L;
    private long startTime = 0L;
    private long stopTime = 0L;
    private GoogleMap map;
    private GoogleApiClient googleApiClient;
    private Location lastLocation;

    private TextView textLat, textLong;
    private TextView inTime, outTime;
    private TextView tvTimer, tvLatLong;
    private Button checkInButton, checkOutButton;
    //Add spinner for office location
    private Spinner spinner;
    private static final String[] paths = {"My office", "Vishakha Home", "Daman Home"};
    private MapFragment mapFragment;
    Date checkInTime, checkOutTime;
    private String userid;

    private static final String NOTIFICATION_MSG = "NOTIFICATION MSG";

    // Create a Intent send by the notification
    public static Intent makeNotificationIntent(Context context, String msg) {
        Intent intent = new Intent(context, GetMapLocationActivity.class);
        intent.putExtra(NOTIFICATION_MSG, msg);
        return intent;
    }

    // add timer

    private Runnable updateTimerThread = new Runnable() {
        @Override
        public void run() {
            timeInMilliseconds = SystemClock.uptimeMillis() - startTime;
            updatedTime = timeSwapBuff + timeInMilliseconds;

            int secs = (int) (updatedTime / 1000);
            int mins = secs / 60;
            int hours = mins / 60;


            secs = secs % 60;
            int milliseconds = (int) (updatedTime % 1000);
            tvTimer.setText("" + hours + ":"

                    + String.format("%02d", mins) + ":"

                    + String.format("%02d", secs));

            customHandler.postDelayed(this, 0);

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textLat = (TextView) findViewById(R.id.lat);
        textLong = (TextView) findViewById(R.id.lon);
        checkInButton = (Button) findViewById(R.id.bt_checkin);
        checkOutButton = (Button) findViewById(R.id.bt_checkout);

        tvLatLong = (TextView) findViewById(R.id.tv_lat_long);
        try {
            latlnglist = getIntent().getParcelableArrayListExtra("OfficeList");
            userid=getIntent().getStringExtra("id");
        } catch (Exception e) {
            e.printStackTrace();
        }

        spinner = findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.planets_array, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        // initialize GoogleMaps
        initGMaps();

        // create GoogleApiClient
        createGoogleApi();

        mGeofencingClient = LocationServices.getGeofencingClient(this);
        startGeofence();

        checkInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendCheckInData();
            }
        });

        checkOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendCheckOutData();
            }
        });


    }


    // Create GoogleApiClient instance
    private void createGoogleApi() {
        Log.d(TAG, "createGoogleApi()");
        if (googleApiClient == null) {
            googleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        // Call GoogleApiClient connection when starting the Activity
        googleApiClient.connect();
    }

    @Override
    protected void onStop() {
        super.onStop();

        // Disconnect GoogleApiClient when stopping Activity
        googleApiClient.disconnect();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    private final int REQ_PERMISSION = 999;

    // Check for permission to access Location
    private boolean checkPermission() {
        Log.d(TAG, "checkPermission()");
        // Ask for permission if it wasn't granted yet
        return (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED);
    }

    // Asks for permission
    private void askPermission() {
        Log.d(TAG, "askPermission()");
        ActivityCompat.requestPermissions(
                this,
                new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                REQ_PERMISSION
        );
    }

    // Verify user's response of the permission requested
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        Log.d(TAG, "onRequestPermissionsResult()");
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQ_PERMISSION: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permission granted
                    getLastKnownLocation();

                } else {
                    // Permission denied
                    permissionsDenied();
                }
                break;
            }
        }
    }

    // App cannot work without the permissions
    private void permissionsDenied() {
        Log.w(TAG, "permissionsDenied()");
        // TODO close app and warn user
    }

    // Initialize GoogleMaps
    private void initGMaps() {
        mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    // Callback called when Map is ready
    @Override
    public void onMapReady(GoogleMap googleMap) {
        Log.d(TAG, "onMapReady()");
        map = googleMap;
        map.setOnMarkerClickListener(this);


    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        Log.d(TAG, "onMarkerClickListener: " + marker.getPosition());
        return false;
    }

    private LocationRequest locationRequest;
    // Defined in mili seconds.
    // This number in extremely low, and should be used only for debug
    private final int UPDATE_INTERVAL = 1000;
    private final int FASTEST_INTERVAL = 900;

    // Start location Updates
    private void startLocationUpdates() {
        Log.i(TAG, "startLocationUpdates()");
        locationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(UPDATE_INTERVAL)
                .setFastestInterval(FASTEST_INTERVAL);

        if (checkPermission())
            LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, locationRequest, this);
    }

    /*@Override
    protected void onResume() {
        super.onResume();
        if (googleApiClient.isConnected()) {
            startLocationUpdates();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        stopLocationUpdates();
    }
*/
    private void stopLocationUpdates() {
        try {
            if (googleApiClient.isConnected())
                LocationServices.FusedLocationApi.removeLocationUpdates(
                        googleApiClient, this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        Log.d(TAG, "onLocationChanged [" + location + "]");
        lastLocation = location;
        writeActualLocation(location);
    }

    // GoogleApiClient.ConnectionCallbacks connected
    @Override
    public void onConnected(@Nullable Bundle bundle) {
        Log.i(TAG, "onConnected()");

        getLastKnownLocation();


    }

    // GoogleApiClient.ConnectionCallbacks suspended
    @Override
    public void onConnectionSuspended(int i) {
        Log.w(TAG, "onConnectionSuspended()");
    }

    // GoogleApiClient.OnConnectionFailedListener fail
    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.w(TAG, "onConnectionFailed()");
    }

    // Get last known location
    private void getLastKnownLocation() {
        Log.d(TAG, "getLastKnownLocation()");
        if (checkPermission()) {
            lastLocation = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
            if (lastLocation != null) {
                Log.i(TAG, "LasKnown location. " +
                        "Long: " + lastLocation.getLongitude() +
                        " | Lat: " + lastLocation.getLatitude());
                writeLastLocation();
                startLocationUpdates();
            } else {
                Log.w(TAG, "No location retrieved yet");
                startLocationUpdates();
            }
        } else askPermission();
    }

    private void writeActualLocation(Location location) {
        textLat.setText("Lat: " + location.getLatitude());
        textLong.setText("Long: " + location.getLongitude());

        markerLocation(new LatLng(location.getLatitude(), location.getLongitude()));
    }

    private void writeLastLocation() {
        writeActualLocation(lastLocation);
    }

    private Marker locationMarker;

    private void markerLocation(LatLng latLng) {
        Log.i(TAG, "markerLocation(" + latLng + ")");
        String title = latLng.latitude + ", " + latLng.longitude;
        MarkerOptions markerOptions = new MarkerOptions()
                .position(latLng)
                .title(title);
        if (map != null) {
            if (locationMarker != null)
                locationMarker.remove();
            locationMarker = map.addMarker(markerOptions);
            float zoom = 14f;
            CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, zoom);
            map.animateCamera(cameraUpdate);
        }

    }


    private Marker geoFenceMarker;

    private void markerForGeofence(LatLng latLng) {
        Log.i(TAG, "markerForGeofence(" + latLng + ")");
        String title = latLng.latitude + ", " + latLng.longitude;
        // Define marker options
        MarkerOptions markerOptions = new MarkerOptions()
                .position(latLng)
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE))
                .title(title);
        if (map != null) {
            geoFenceMarker = map.addMarker(markerOptions);

        }
    }


    //fetch locations data to create region


    private double latitude = 30.6754302, longitude = 76.7405481;

    // Start Geofence creation process
    private void startGeofence() {
        Log.i(TAG, "startGeofence()");

        List<Geofence> geofencesList = new ArrayList<>();


        for (int i = 0; i < latlnglist.size(); i++) {
            Geofence geofence = createGeofence(latlnglist.get(i).getLatitude(), latlnglist.get(i).getLongitude(), latlnglist.get(i).getKey(), GEOFENCE_RADIUS);
            geofencesList.add(geofence);
        }
        GeofencingRequest geofenceRequest = createGeofenceRequest(geofencesList);
        addGeofence(geofenceRequest);

    }

    private static final long GEO_DURATION = 60 * 60 * 1000;
    private static final String GEOFENCE_REQ_ID = "My Geofence";
    //Radius draw for the selected location
    private static final float GEOFENCE_RADIUS = 1000.0f; // in meters


    // Create a Geofence
    private Geofence createGeofence(double latitude, double longitude, String geoKey, float radius) {
        Log.d(TAG, "createGeofence---" + geoKey);
        return new Geofence.Builder()
                .setRequestId(geoKey)
                .setCircularRegion(latitude, longitude, radius)
                .setExpirationDuration(GEO_DURATION)
                .setTransitionTypes(Geofence.GEOFENCE_TRANSITION_ENTER
                                | Geofence.GEOFENCE_TRANSITION_EXIT
//                        | Geofence.GEOFENCE_TRANSITION_DWELL
                )
                .setLoiteringDelay(30000)
                .build();
    }

    // Create a Geofence Request
    private GeofencingRequest createGeofenceRequest(List<Geofence> geofence) {
        Log.d(TAG, "createGeofenceRequest");
        return new GeofencingRequest.Builder()
                .setInitialTrigger(GeofencingRequest.INITIAL_TRIGGER_ENTER)
                .addGeofences(geofence)
                .build();
    }

    private PendingIntent geoFencePendingIntent;
    private final int GEOFENCE_REQ_CODE = 0;

    private PendingIntent createGeofencePendingIntent() {
        Log.d(TAG, "createGeofencePendingIntent");
        if (geoFencePendingIntent != null)
            return geoFencePendingIntent;

        Intent intent = new Intent(this, GeofenceReciever.class);
        intent.setAction("com.android.maplocation.geofence.GeofenceReciever.ACTION_RECIEVE_GEOFENCE");
        return PendingIntent.getBroadcast(
                this, GEOFENCE_REQ_CODE, intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    // Add the created GeofenceRequest to the device's monitoring list
    private void addGeofence(GeofencingRequest request) {
        Log.d(TAG, "addGeofence");
        if (checkPermission()) {
            mGeofencingClient.addGeofences(request, createGeofencePendingIntent())
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                        }
                    })
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            drawGeofence();
                        }
                    });
//            LocationServices.GeofencingApi.addGeofences(
//                    googleApiClient,
//                    request,
//                    createGeofencePendingIntent()
//            ).setResultCallback(this);
        }

    }

    @Override
    public void onResult(@NonNull Status status) {
        Log.i(TAG, "onResult: " + status);
        if (status.isSuccess()) {

            //saveGeofence();
            drawGeofence();
        } else {
            // inform about failmarkerForGeofence
            System.out.println("GetMapLocationActivity.onResult--" + status.isSuccess());
            /*saveGeofence();
            drawGeofence();*/
        }
    }

    // Draw Geofence circle on GoogleMap

    private void drawGeofence() {
        Log.d(TAG, "drawGeofence()");
        LatLng latLng = null;
        for (int i = 0; i < latlnglist.size(); i++) {
            latLng = new LatLng(latlnglist.get(i).getLatitude(), latlnglist.get(i).getLongitude());
            markerForGeofence(latLng);
            CircleOptions circleOptions = new CircleOptions()
                    .center(latLng)
                    .strokeColor(Color.argb(50, 70, 70, 70))
                    .fillColor(Color.argb(100, 150, 150, 150))
                    .radius(GEOFENCE_RADIUS);
            map.addCircle(circleOptions);
        }
    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        // TODO Auto-generated method stub
        Toast.makeText(this, "YOUR SELECTION IS : " + adapterView.getItemAtPosition(i).toString(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    private void sendCheckInData()
    {

            CheckInTimeParams params = new CheckInTimeParams();
            params.setTask("addWorklog");
            params.setUserId(userid);
            params.setLattitude(String.valueOf(lastLocation.getLatitude()));
            params.setLongitude(String.valueOf(lastLocation.getLongitude()));
        /*for(int i=0;i<latlnglist.size();i++)
        {
            if(((latlnglist.get(i).getLatitude())==lastLocation.getLatitude() &&
                    ((latlnglist.get(i).getLongitude())==lastLocation.getLongitude() )))
            {
                params.setSiteLocationId(latlnglist.get(i).getLocation_id());
            }
        }*/

            //chck in time

        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, 1);
        System.out.println(cal.getTime());
        // Output "Wed Sep 26 14:23:28 EST 2012"
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String formatted = dateFormat.format(cal.getTime());
            params.setCheckinTime(formatted);
            Call<CheckInTimeBean> call = RetrofitClient.getRetrofitClient().checkin(params);
            call.enqueue(new Callback<CheckInTimeBean>() {
                @Override
                public void onResponse(Call<CheckInTimeBean> call, Response<CheckInTimeBean> response) {

                    tvLatLong.setText(response.body().getStatusMessage());
                }

                @Override
                public void onFailure(Call<CheckInTimeBean> call, Throwable t) {

                }
            });


    }

    private void sendCheckOutData()
    {
        CheckOutTimeParams params = new CheckOutTimeParams();
        params.setTask("addWorklog");
        params.setUserId(userid);
        params.setLattitude(String.valueOf(lastLocation.getLatitude()));
        params.setLongitude(String.valueOf(lastLocation.getLongitude()));
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, 1);
        System.out.println(cal.getTime());
        // Output "Wed Sep 26 14:23:28 EST 2012"
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String formatted = dateFormat.format(cal.getTime());
        params.setCheckoutTime(formatted);
        Call<CheckOutTimeBean> call = RetrofitClient.getRetrofitClient().checkout(params);
        call.enqueue(new Callback<CheckOutTimeBean>() {
            @Override
            public void onResponse(Call<CheckOutTimeBean> call, Response<CheckOutTimeBean> response) {

                tvLatLong.setText(response.body().getStatusMessage());
            }

            @Override
            public void onFailure(Call<CheckOutTimeBean> call, Throwable t) {

            }
        });

    }

}
