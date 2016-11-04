package com.benson.rsamechanic;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.benson.rsamechanic.models.Location;
import com.benson.rsamechanic.models.ServerRequest;
import com.benson.rsamechanic.models.ServerResponse;
import com.benson.rsamechanic.models.User;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import android.content.SharedPreferences;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private SharedPreferences pref;
    private RelativeLayout layout;
    private TextView tv_name;
    private AppCompatButton test;
    static String placename;
    static String latitude;
    static String longitude;
    static String address;
    static String email;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        layout = (RelativeLayout) findViewById(R.id.layout_map);
        pref= getSharedPreferences("myValue",0);
        tv_name= (TextView)findViewById(R.id.tv_name);
        test=(AppCompatButton)findViewById(R.id.test);
        test.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                locationProcess(pref.getString(Constants.PLACE,""), pref.getString(Constants.LATITUDE,""),
                        pref.getString(Constants.LONGITUDE,""),
                        pref.getString(Constants.ADDRESS,""), pref.getString(Constants.EMAIL,""));
            }
        });


    }



    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }
    private void locationProcess(String placename, String latitude,String longitude, String address,String email){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RequestInterface requestInterface = retrofit.create(RequestInterface.class);

        User user = new User();
        Location location = new Location();

        user.setEmail(email);
        location.setPlacename(placename);
        location.setLongitude(longitude);
        location.setLatitude(latitude);
        location.setAddress(address);
        ServerRequest request = new ServerRequest();
        request.setOperation(Constants.LOCATION_OPERATION);
        request.setLocation(location);
        request.setUser(user);
        Call<ServerResponse> response = requestInterface.operation(request);

        response.enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, retrofit2.Response<ServerResponse> response) {
                Log.e("inside parameter","inside inside");
                ServerResponse resp = response.body();

                Log.e("response",resp.getMessage()+resp.getJsonObject().toString());

                Snackbar.make(layout, resp.getMessage(), Snackbar.LENGTH_LONG).show();
                if(resp.getResult().equals(Constants.SUCCESS)){
                    SharedPreferences.Editor editor = pref.edit();
                    editor.putString(Constants.LATITUDE,resp.getLocation().getLatitude());
                    editor.putString(Constants.LONGITUDE,resp.getLocation().getLongitude());
                    editor.putString(Constants.ADDRESS,resp.getLocation().getAddress());
                    editor.apply();
                    tv_name.setText(pref.getString(Constants.ADDRESS,""));


                }
            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {

                //progress.setVisibility(View.INVISIBLE);
                Log.d(Constants.TAG,"failed");
                Snackbar.make(layout, t.getLocalizedMessage(), Snackbar.LENGTH_LONG).show();


            }
        });
    }
}
