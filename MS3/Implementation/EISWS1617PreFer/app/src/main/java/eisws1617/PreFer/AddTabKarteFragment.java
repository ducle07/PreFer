package eisws1617.PreFer;


import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

//Dieses Fragment erzeugt eine MapView unter dem Tab "Karte".

public class AddTabKarteFragment extends Fragment
        implements OnMapClickListener, OnMapReadyCallback {

    //Das Interface dient zur Kommunikation zwischen Activity und Fragment.
    public interface OnDataPass {
        void onDataPass(JSONObject json);
    }

    OnDataPass dataPass;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            dataPass = (OnDataPass) context;
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

    //Diese Methode erzeugt die View, sobald das Fragment geöffnet wurde.
    //
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

    //Sobald die View erzeugt wurde, wird eine Karte von Google Maps in der View dargestellt.
    //Die dargestellte Karte ist eine Satellitenkarte.
    //Die Karte horcht auf Klicken der Karte durch den Nutzer.

    @Override
    public void onMapReady(GoogleMap map) {
        googleMap = map;
        googleMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        googleMap.setOnMapClickListener(this);
    }

    //Wenn ein Nutzer auf die Karte klickt, dann wird ein Punkt gesetzt, der anschließend in der Liste "fieldArea"
    //gespeichert wird. Es können maximal fünf Punkte (siehe maxPoints) gesetzt werden.

    @Override
    public void onMapClick(LatLng point) {
        LatLng = point;
        fieldArea.add(LatLng);
        int maxPoints = 5;

        //Vordefinierte Einstellungen für das Polygon
        PolygonOptions fieldOptions = new PolygonOptions();
        fieldOptions.fillColor(0x7FFF0000);
        fieldOptions.strokeColor(Color.BLACK);
        fieldOptions.strokeWidth(2);

        if (fieldArea.size() == maxPoints) {
            buildJSON();
        }

        if (fieldArea.size() > maxPoints) {
            return;
        } else {
            for (int i = 0; i < fieldArea.size(); i++) {
                fieldOptions.add(fieldArea.get(i));
            }
            googleMap.addPolygon(fieldOptions); //Polygon wird auf der Karte erzeugt.

            //Die Größe des Polygons wird berechnet und in ha umgewandelt.
            fieldSize = SphericalUtil.computeArea(fieldArea) / 10000;
            fieldSize = fieldSize * 100;
            fieldSize = Math.round(fieldSize);
            fieldSize = fieldSize / 100;
        }
    }

    //Diese Methode baut mithilfe der Größe und der Koordinaten des Polygons ein JSON-Objekt.
    //Durch die passData-Methode wird dieses JSON-Objekt an die parent-Activity(AddActivity) gesendet.
    public void buildJSON() {
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
            json.put("size", fieldSize);
            json.put("outline", outline);
            passData(json);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    //Diese Methode schickt Daten von dieser Fragment an die parent-Activity.
    public void passData(JSONObject data) {
        dataPass.onDataPass(data);
    }
}

