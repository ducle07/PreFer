package eisws1617.PreFer;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.PolygonOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

//Dieses Fragment erzeugt eine MapView unter dem Tab "Duenger".
//Geplant war durch eine Interpolation die Daten der Düngeempfehlungen so zu vermehren,
//dass auf dem Polygon eine Nährstoffkarte erzeugt werden kann.

public class DisplayTabDuengerFragment extends Fragment
        implements OnMapReadyCallback{

    MapView mMapView;
    private GoogleMap googleMap;

    //Diese Methode erzeugt die MapView, sobald das Fragment geöffnet wurde.

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_display_tab_karte, container, false);
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

    //Sobald die View erzeugt wurde, wird eine Karte von Google Maps in der View dargestellt.
    //Die dargestellte Karte ist eine Satellitenkarte.
    //Die Methode holt außerdem aus dem Server die eingetragenen Polygon-Daten für das Feld und
    //präsentiert dies auf der Map.

    @Override
    public void onMapReady(GoogleMap mMap) {
        googleMap = mMap;
        googleMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);

        String getArgument = getArguments().getString("name");
        final String url = "http://192.168.1.12:3000/field?name=" + getArgument;
        JsonObjectRequest getRequest = new JsonObjectRequest(url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("Response", response.toString());
                        try {
                            JSONArray array = response.getJSONArray("outline");

                            PolygonOptions rectOptions = new PolygonOptions();
                            ArrayList<LatLng> latlngList = new ArrayList<>();

                            for (int i = 0; i < array.length(); i++) {
                                JSONObject point = (JSONObject) array.get(i);
                                double lat = (double) point.get("latitude");
                                double lng = (double) point.get("longitude");
                                rectOptions.add(new LatLng(lat, lng));
                                latlngList.add(new LatLng(lat, lng));
                            }

                            rectOptions.fillColor(0x7F00FF00);
                            rectOptions.strokeWidth(4);
                            rectOptions.strokeColor(Color.BLACK);
                            googleMap.addPolygon(rectOptions);

                            LatLngBounds.Builder builder = new LatLngBounds.Builder();
                            for (LatLng point : latlngList) {
                                builder.include(point);
                            }
                            LatLngBounds bounds = builder.build();

                            int padding = 100;
                            CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, padding);

                            googleMap.moveCamera(cu);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Error.Response", error.getMessage());
                    }
                }


        );

        MySingleton.getInstance(getActivity()).addToRequestQueue(getRequest);
    }
}
