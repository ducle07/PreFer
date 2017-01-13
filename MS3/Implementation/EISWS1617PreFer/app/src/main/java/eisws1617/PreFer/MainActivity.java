package eisws1617.PreFer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void gotoMaps(View view) {
        Intent intent = new Intent(this, MapsActivity.class);
        startActivity(intent);
    }

    public void goHeatMap(View view) {
        Intent intent = new Intent(this, ShowPolygonActivity.class);
        startActivity(intent);
    }

    public void goPolygon(View view) {
        Intent intent = new Intent(this, CreatePolygonActivity.class);
        startActivity(intent);
    }
}
