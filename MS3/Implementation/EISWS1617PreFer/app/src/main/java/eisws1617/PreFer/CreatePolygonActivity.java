package eisws1617.PreFer;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.maps.android.SphericalUtil;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class CreatePolygonActivity extends AppCompatActivity
        implements OnMapClickListener,
        OnMapReadyCallback {

    private TextView mTapTextView;
    private LatLng LatLng;
    private GoogleMap mMap;
    private double fieldSize;

    private ArrayList<LatLng> fieldArea = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_polygon);

        mTapTextView = (TextView) findViewById(R.id.tap_text);

        SupportMapFragment mapFragment =
                (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap map) {
        mMap = map;
        mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        mMap.setOnMapClickListener(this);
    }

    @Override
    public void onMapClick(LatLng point) {
        mTapTextView.setText("tapped, point=" + point);
        LatLng = point;
        fieldArea.add(LatLng);

        PolygonOptions fieldOptions = new PolygonOptions();
        fieldOptions.fillColor(Color.RED);
        fieldOptions.strokeColor(Color.RED);
        fieldOptions.strokeWidth(2);

        if(fieldArea.size() == 4) {
            savePolygon();
        }

        if(fieldArea.size() > 4) {
            return;
        }

        else {
            for (int i=0; i<fieldArea.size(); i++) {
                fieldOptions.add(fieldArea.get(i));
            }
            mMap.addPolygon(fieldOptions);
            fieldSize = SphericalUtil.computeArea(fieldArea) / 10000;
            fieldSize = fieldSize*100;
            fieldSize = Math.round(fieldSize);
            fieldSize = fieldSize/100;
        }
    }

    public void savePolygon() {
        JSONObject json = new JSONObject();
        JSONArray outline = new JSONArray();

        for(int i=0; i<fieldArea.size(); i++) {
            JSONObject point= new JSONObject();

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
        MySingleton.getInstance(this).addToRequestQueue(postRequest);
    }
}
