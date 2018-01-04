package com.android.maplocation.geofence;

import android.Manifest;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.maplocation.R;
import com.android.maplocation.bean.CheckInTimeBean;
import com.android.maplocation.bean.CheckOutTimeBean;
import com.android.maplocation.bean.Locations;
import com.android.maplocation.bean.OfficeLocationBean;
import com.android.maplocation.pojo.OfficeLocations;
import com.android.maplocation.reports.UserTimeReport;
import com.android.maplocation.serviceparams.CheckInTimeParams;
import com.android.maplocation.serviceparams.CheckOutTimeParams;
import com.android.maplocation.serviceparams.OfficeLocationParams;
import com.android.maplocation.utils.SharedPreferencesHandler;
import com.android.maplocation.webservices.RetrofitClient;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingClient;
import com.google.android.gms.location.GeofencingRequest;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;
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
        GoogleMap.OnMarkerClickListener {

    private static final String TAG = GetMapLocationActivity.class.getSimpleName();
    private static final String[] paths = {"My office", "Vishakha Home", "Daman Home"};
    private static final String NOTIFICATION_MSG = "NOTIFICATION MSG";
    //permissions
    private static final int PERMISSION_ALL = 1;
    private static final long GEO_DURATION = 60 * 60 * 1000;
    private static final String GEOFENCE_REQ_ID = "My Geofence";
    //Radius draw for the selected location
    private static final float GEOFENCE_RADIUS = 1000.0f; // in meters
    private final static int REQUEST_CHECK_SETTINGS_GPS = 0x1;
    private final static int REQUEST_ID_MULTIPLE_PERMISSIONS = 0x2;
    private final int REQ_PERMISSION = 999;
    // Defined in mili seconds.
    // This number in extremely low, and should be used only for debug
    private final int UPDATE_INTERVAL = 1000;
    private final int FASTEST_INTERVAL = 900;
    private final int GEOFENCE_REQ_CODE = 0;
    private GeofencingClient mGeofencingClient;
    private ArrayList<OfficeLocations> latlnglist = new ArrayList<>();
    private GoogleMap map;
    private GoogleApiClient googleApiClient;
    private Location lastLocation;
    private ImageView reportsImage;
    private TextView textLat, textLong;
    private TextView checkIn, checkOut;
    private MapFragment mapFragment;
    private String workId;
    private int userStatus = 1;
    private String[] permission =
            {Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.INTERNET};
    private LocationRequest locationRequest;
    private Marker locationMarker;
    private Marker geoFenceMarker;
    private PendingIntent geoFencePendingIntent;
    private int sitelocationid;
    private GpsLocationReceiver locationReceiver=new GpsLocationReceiver();

    private AlertDialog dialog;

    private final BroadcastReceiver mGpsDetectionReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (!intent.getBooleanExtra("isGpsEnabled", false)) {
                showDialogForGPS(getString(R.string.msg_enable_gps));
            } else {
                dismissGpsDialog();
            }
        }
    };


    // Create a Intent send by the notification
    public static Intent makeNotificationIntent(Context context, String msg) {
        Intent intent = new Intent(context, GetMapLocationActivity.class);
        intent.putExtra(NOTIFICATION_MSG, msg);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        registerReceiver(mGpsDetectionReceiver, new IntentFilter("gpsDetectionIntent"));
        registerReceiver(locationReceiver, new IntentFilter("android.location.PROVIDERS_CHANGED"));

        textLat = findViewById(R.id.lat);
        textLong = findViewById(R.id.lon);
        checkIn = findViewById(R.id.tv_checkin);
        checkOut = findViewById(R.id.tv_checkout);
        checkIn.setBackgroundResource(R.drawable.disablebutton);
        checkIn.setEnabled(false);
        checkOut.setBackgroundResource(R.drawable.disablebutton);
        checkOut.setEnabled(false);
        reportsImage = findViewById(R.id.image_view_reports);

        // initialize GoogleMaps
        initGMaps();

        // create GoogleApiClient
        createGoogleApi();

        mGeofencingClient = LocationServices.getGeofencingClient(this);


        fetchLocationData();

        checkIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                sendCheckInData();
                checkIn.setBackgroundResource(R.drawable.disablebutton);
                checkIn.setEnabled(false);


            }
        });

        checkOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendCheckOutData();
                checkOut.setBackgroundResource(R.drawable.disablebutton);
                checkOut.setEnabled(false);
            }
        });

        reportsImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(GetMapLocationActivity.this, UserTimeReport.class);
                startActivity(intent);
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.logout: {
                SharedPreferencesHandler.clearAll(this);
                finish();
                return true;
            }

        }
        return super.onOptionsItemSelected(item);
    }

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

    // Start location Updates
    private void startLocationUpdates() {

        Log.i(TAG, "startLocationUpdates()");
        locationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(UPDATE_INTERVAL)
                .setFastestInterval(FASTEST_INTERVAL);
        if (checkPermission()&&googleApiClient!=null&&googleApiClient.isConnected()) {




            LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                    .addLocationRequest(locationRequest);
            builder.setAlwaysShow(true);
            LocationServices.FusedLocationApi
                    .requestLocationUpdates(googleApiClient, locationRequest, this);
            PendingResult<LocationSettingsResult> result =
                    LocationServices.SettingsApi
                            .checkLocationSettings(googleApiClient, builder.build());
            result.setResultCallback(new ResultCallback<LocationSettingsResult>() {

                @Override
                public void onResult(@NonNull LocationSettingsResult result) {
                    final Status status = result.getStatus();
                    switch (status.getStatusCode()) {
                        case LocationSettingsStatusCodes.SUCCESS:
                            // All location settings are satisfied.
                            // You can initialize location requests here.
                            int permissionLocation = ContextCompat
                                    .checkSelfPermission(GetMapLocationActivity.this,
                                            Manifest.permission.ACCESS_FINE_LOCATION);
                            if (permissionLocation == PackageManager.PERMISSION_GRANTED) {
                                lastLocation = LocationServices.FusedLocationApi
                                        .getLastLocation(googleApiClient);
                            }
                            break;
                        case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                            // Location settings are not satisfied.
                            // But could be fixed by showing the user a dialog.
                            try {
                                // Show the dialog by calling startResolutionForResult(),
                                // and check the result in onActivityResult().
                                // Ask to turn on GPS automatically
                                status.startResolutionForResult(GetMapLocationActivity.this,
                                        REQUEST_CHECK_SETTINGS_GPS);
                            } catch (IntentSender.SendIntentException e) {
                                // Ignore the error.
                            }
                            break;
                        case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                            // Location settings are not satisfied.
                            // However, we have no way
                            // to fix the
                            // settings so we won't show the dialog.
                            // finish();
                            break;
                    }
                }
            });


        }


    }

    @Override
    protected void onResume() {
        super.onResume();
        if (googleApiClient.isConnected()) {
            startLocationUpdates();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
//        stopLocationUpdates();
    }

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
        if (userStatus == 1 && enableCheckInAndOut()) {
            checkIn.setEnabled(true);
            checkIn.setBackgroundResource(R.drawable.checkinbutton);


        }

        else if(userStatus == 1 && !enableCheckInAndOut())
        {
            checkIn.setEnabled(false);
            checkIn.setBackgroundResource(R.drawable.disablebutton);
        }
        else if ((userStatus == 2) && (enableCheckInAndOut() || !enableCheckInAndOut())) {
            checkOut.setEnabled(true);
            checkOut.setBackgroundResource(R.drawable.checkoutbutton);
        } else if (userStatus == 3 && (enableCheckInAndOut() || !enableCheckInAndOut())) {
            userStatus = 1;
        }
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
           // drawGeofence();
            lastLocation = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
            if (lastLocation != null) {
                Log.i(TAG, "LasKnown location. " +
                        "Long: " + lastLocation.getLongitude() +
                        " | Lat: " + lastLocation.getLatitude());
                startLocationUpdates();
            } else {
                Log.w(TAG, "No location retrieved yet");
                startLocationUpdates();
            }
        } else {
            askPermission();
        }

    }

    private void writeActualLocation(Location location) {
        textLat.setText("Lat: " + location.getLatitude());
        textLong.setText("Long: " + location.getLongitude());

        markerLocation(new LatLng(location.getLatitude(), location.getLongitude()));
    }

    private void writeLastLocation() {
        writeActualLocation(lastLocation);
    }

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
    // Start Geofence creation process
    private void startGeofence() {
        Log.i(TAG, "startGeofence()");

        List<Geofence> geofencesList = new ArrayList<>();


        for (int i = 0; i < latlnglist.size(); i++) {
            Geofence geofence = createGeofence(latlnglist.get(i).getLatitude(), latlnglist.get(i).getLongitude(), latlnglist.get(i).getLocationName(), GEOFENCE_RADIUS);
            geofencesList.add(geofence);
        }
        GeofencingRequest geofenceRequest = createGeofenceRequest(geofencesList);
        addGeofence(geofenceRequest);

    }

    // Create a Geofence
    private Geofence createGeofence(double latitude, double longitude, String geoKey, float radius) {
        Log.d(TAG, "createGeofence---" + geoKey);
        return new Geofence.Builder()
                .setRequestId(geoKey)
                .setCircularRegion(latitude, longitude, radius)
                .setExpirationDuration(GEO_DURATION)
                .setTransitionTypes(Geofence.GEOFENCE_TRANSITION_ENTER
                        | Geofence.GEOFENCE_TRANSITION_EXIT
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

    private void sendCheckInData() {

        userStatus = 2;
        isMarkerIn1000();
        CheckInTimeParams params = new CheckInTimeParams();
        params.setTask("addWorklog");
        params.setUserId(SharedPreferencesHandler.getStringValues(this, getString(R.string.pref_user_id)));
        params.setLattitude(String.valueOf(lastLocation.getLatitude()));
        params.setLongitude(String.valueOf(lastLocation.getLongitude()));
        params.setSiteLocationId(sitelocationid);
        params.setUserTimeZone("Asia/Kolkata");
        //chck in time

        Calendar cal = Calendar.getInstance();

        System.out.println(cal.getTime());
        // Output "Wed Sep 26 14:23:28 EST 2012"
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String formatted = dateFormat.format(cal.getTime());
        params.setCheckinTime(formatted);
        Call<CheckInTimeBean> call = RetrofitClient.getRetrofitClient().checkin(params);
        call.enqueue(new Callback<CheckInTimeBean>() {
            @Override
            public void onResponse(Call<CheckInTimeBean> call, Response<CheckInTimeBean> response) {

                workId = response.body().getData().getUser_work_log_id();
              //  Toast.makeText(GetMapLocationActivity.this, response.body().getStatusMessage(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<CheckInTimeBean> call, Throwable t) {

            }
        });


    }

    private void sendCheckOutData() {
        userStatus = 3;
        CheckOutTimeParams params = new CheckOutTimeParams();
        params.setTask("addWorklog");
        params.setUserId(SharedPreferencesHandler.getStringValues(this, getString(R.string.pref_user_id)));
        params.setLattitude(String.valueOf(lastLocation.getLatitude()));
        params.setLongitude(String.valueOf(lastLocation.getLongitude()));
        params.setUserWorkLogId(workId);
        params.setUserTimeZone("Asia/Kolkata");
        Calendar cal = Calendar.getInstance();

        // Output "Wed Sep 26 14:23:28 EST 2012"
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        final String formatted = dateFormat.format(cal.getTime());
        params.setCheckoutTime(formatted);
        Call<CheckOutTimeBean> call = RetrofitClient.getRetrofitClient().checkout(params);
        call.enqueue(new Callback<CheckOutTimeBean>() {
            @Override
            public void onResponse(Call<CheckOutTimeBean> call, Response<CheckOutTimeBean> response) {
               // Toast.makeText(GetMapLocationActivity.this, response.body().getStatusMessage(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<CheckOutTimeBean> call, Throwable t) {

            }
        });

    }

    private void fetchLocationData() {

        OfficeLocationParams params = new OfficeLocationParams();
        params.setTask("getAllSiteslocation");

        Call<OfficeLocationBean> call = RetrofitClient.getRetrofitClient().location(params);

        call.enqueue(new Callback<OfficeLocationBean>() {
            @Override
            public void onResponse(Call<OfficeLocationBean> call, Response<OfficeLocationBean> response) {

                if (response.isSuccessful()) {
                    onSuccess(response.body());
                } else {
                    onError(response.errorBody().toString());
                }
            }

            @Override
            public void onFailure(Call<OfficeLocationBean> call, Throwable t) {
                onError(t.getMessage());
            }
        });
    }

    private void onSuccess(OfficeLocationBean response) {
        switch (response.getStatus()) {
            case 200:
                latlnglist = new ArrayList<>();
                List<Locations> locations = response.getDataBean();

                double latitude, longitude = 0.0;
                String locationName = null;
                int locationid = 0;
                for (int i = 0; i < locations.size(); i++) {
                    latitude = locations.get(i).getLattitude();
                    longitude = locations.get(i).getLongitude();
                    locationName = locations.get(i).getSite_name();
                    locationid = locations.get(i).getSitelocation_id();
                    latlnglist.add(new OfficeLocations(latitude, longitude, locationName, locationid));
                }
                if (checkPermission() ) {

                        startGeofence();
                       // getLastKnownLocation();

                }

                break;
            case 400:
               // Toast.makeText(this, "No Data", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    private void onError(String errorMessage) {
        //Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show();
    }


    private void isMarkerIn1000() {
        Location locationA = new Location("LocationA");
        locationA.setLatitude(lastLocation.getLatitude());
        locationA.setLongitude(lastLocation.getLongitude());

        for (int i = 0; i < latlnglist.size(); i++) {
            Location locationB = new Location("LocationB");
            locationB.setLatitude(latlnglist.get(i).getLatitude());
            locationB.setLongitude(latlnglist.get(i).getLongitude());
            float distance = locationA.distanceTo(locationB);
            if (distance < GEOFENCE_RADIUS) {
                sitelocationid = latlnglist.get(i).getLocation_id();
            }
        }
    }

    private boolean enableCheckInAndOut() {
        Location locationA = new Location("LocationA");
        locationA.setLatitude(lastLocation.getLatitude());
        locationA.setLongitude(lastLocation.getLongitude());

        for (int i = 0; i < latlnglist.size(); i++) {
            Location locationB = new Location("LocationB");
            locationB.setLatitude(latlnglist.get(i).getLatitude());
            locationB.setLongitude(latlnglist.get(i).getLongitude());
            float distance = locationA.distanceTo(locationB);
            if (distance < GEOFENCE_RADIUS) {
                return true;
            }
        }
        return false;
    }


    protected void showDialogForGPS(String message) {

        if (dialog == null) {
            dialog = new AlertDialog.Builder(this).create();
            dialog.setTitle(getString(R.string.app_name));
            dialog.setMessage(message);
            dialog.setCancelable(false);
            dialog.setButton(DialogInterface.BUTTON_POSITIVE, "Enable GPS", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                    Intent myIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    startActivity(myIntent);
                    paramDialogInterface.dismiss();
                }
            });
        }

        if (!dialog.isShowing())
            dialog.show();
    }

    protected void dismissGpsDialog() {
        if (dialog != null) dialog.dismiss();
    }


    protected boolean isGpsEnabled() {
        boolean gpsEnabled = false;
        LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        try {
            if (locationManager != null) {
                gpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return gpsEnabled;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_CHECK_SETTINGS_GPS:
                switch (resultCode) {
                    case Activity.RESULT_OK:
                        getLastKnownLocation();
                        break;
                    case Activity.RESULT_CANCELED:
                        finish();
                        break;
                }
                break;
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mGpsDetectionReceiver);
        unregisterReceiver(locationReceiver);
    }


    //check network state
    protected boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        } else {
            return false;
        }
    }

    public boolean checkConnection(){
        if(isOnline()){
            Toast.makeText(GetMapLocationActivity.this, "You are connected to Internet", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(GetMapLocationActivity.this, "You are not connected to Internet", Toast.LENGTH_SHORT).show();
        }
        return false;
    }
}
