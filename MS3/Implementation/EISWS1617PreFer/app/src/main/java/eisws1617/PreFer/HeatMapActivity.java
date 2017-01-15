package eisws1617.PreFer;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.TileOverlay;
import com.google.android.gms.maps.model.TileOverlayOptions;
import com.google.maps.android.heatmaps.Gradient;
import com.google.maps.android.heatmaps.HeatmapTileProvider;
import com.google.maps.android.heatmaps.WeightedLatLng;

import java.util.ArrayList;
import java.util.List;

public class HeatMapActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    private HeatmapTileProvider mProvider;
    private TileOverlay mOverlay;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_heatmap);

        SupportMapFragment mapFragment =
                (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap map) {
        mMap = map;
        mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        addHeatMap();
    }

    private void addHeatMap() {
        ArrayList<WeightedLatLng> list = new ArrayList<WeightedLatLng>();
        LatLng point1 = new LatLng(51.054900, 7.552804);
        LatLng point2 = new LatLng(51.055278, 7.553869);
        LatLng point3 = new LatLng(51.055602, 7.554901);
        LatLng point4 = new LatLng(51.055333, 7.555560);
        LatLng point5 = new LatLng(51.054805, 7.556392);
        LatLng point6 = new LatLng(51.054661, 7.555605);
        LatLng point7 = new LatLng(51.054616, 7.554618);
        LatLng point8 = new LatLng(51.054499, 7.553516);
        LatLng point9 = new LatLng(51.054633, 7.552640);

        list.add(new WeightedLatLng(point1, 1.2));
        list.add(new WeightedLatLng(point2, 1.5));
        list.add(new WeightedLatLng(point3, 2));
        list.add(new WeightedLatLng(point4, 1.7));
        list.add(new WeightedLatLng(point5, 0.1));
        list.add(new WeightedLatLng(point6, 1.1));
        list.add(new WeightedLatLng(point7, 1.3));
        list.add(new WeightedLatLng(point8, 0.6));
        list.add(new WeightedLatLng(point9, 0.7));

        int[] colors = {
                Color.rgb(255, 0, 0),    // red
                Color.rgb(102, 225, 0) // green
        };

        float[] startPoints = {
                0.2f, 1f
        };

        Gradient gradient = new Gradient(colors, startPoints);

        mProvider = new HeatmapTileProvider.Builder()
                .weightedData(list)
                .gradient(gradient)
                .build();
        mProvider.setRadius(100);
        mOverlay = mMap.addTileOverlay(new TileOverlayOptions().tileProvider(mProvider));
    }
}
