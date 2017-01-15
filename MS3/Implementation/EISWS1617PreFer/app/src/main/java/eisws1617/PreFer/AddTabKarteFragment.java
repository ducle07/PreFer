package eisws1617.PreFer;


import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.maps.android.SphericalUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class AddTabKarteFragment extends Fragment
        implements OnMapClickListener, OnMapReadyCallback {

    AddTabInfoFragment.OnDataPass dataPass;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            dataPass = (AddTabInfoFragment.OnDataPass) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnDataPass");
        }
    }

    MapView mMapView;
    private GoogleMap googleMap;
    private double fieldSize;
    private LatLng LatLng;

    private ArrayList<LatLng> fieldArea = new ArrayList<>();

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_add_tab_karte, container, false);
        mMapView = (MapView) rootView.findViewById(R.id.mapView);
        mMapView.onCreate(savedInstanceState);

        mMapView.onResume();

        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

        mMapView.getMapAsync(this);

        return rootView;
    }

    @Override
    public void onMapReady(GoogleMap map) {
        googleMap = map;
        googleMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        googleMap.setOnMapClickListener(this);

        if (ContextCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            googleMap.setMyLocationEnabled(true);
        }
    }

    @Override
    public void onMapClick(LatLng point) {
        LatLng = point;
        fieldArea.add(LatLng);

        PolygonOptions fieldOptions = new PolygonOptions();
        fieldOptions.fillColor(Color.RED);
        fieldOptions.strokeColor(Color.RED);
        fieldOptions.strokeWidth(2);

        if (fieldArea.size() == 4) {
            savePolygon();
        }

        if (fieldArea.size() > 4) {
            return;
        } else {
            for (int i = 0; i < fieldArea.size(); i++) {
                fieldOptions.add(fieldArea.get(i));
            }
            googleMap.addPolygon(fieldOptions);
            fieldSize = SphericalUtil.computeArea(fieldArea) / 10000;
            fieldSize = fieldSize * 100;
            fieldSize = Math.round(fieldSize);
            fieldSize = fieldSize / 100;
        }
    }

    public void savePolygon() {
        JSONObject json = new JSONObject();
        JSONArray outline = new JSONArray();

        for (int i = 0; i < fieldArea.size(); i++) {
            JSONObject point = new JSONObject();

            try {
                point.put("latitude", fieldArea.get(i).latitude);
                point.put("longitude", fieldArea.get(i).longitude);
                outline.put(point);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        try {
            json.put("name", "Namefeld");
            json.put("size", fieldSize);
            json.put("outline", outline);
            passData(json);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        sendPOST(json);
    }

    public void sendPOST(JSONObject jsonObject) {
        final String url = "http://192.168.1.12:3000/field";

        JsonObjectRequest postRequest = new JsonObjectRequest(Request.Method.POST, url, jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("Response", response.toString());
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Error.Response", error.getMessage());
                    }
                });
        MySingleton.getInstance(getActivity()).addToRequestQueue(postRequest);
    }

    public void passData(JSONObject data) {
        dataPass.onDataPass(data);
    }
}

