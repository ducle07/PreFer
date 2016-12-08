package eisws1617.PreFer;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class PostActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        final TextView mTxtDisplay;
        mTxtDisplay = (TextView) findViewById(R.id.txtDisplay);
        final String url = "http://192.168.1.5:3000/polygon";

        JsonArrayRequest getRequest = new JsonArrayRequest(url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d("Response", response.toString());
                        mTxtDisplay.setText("Response: " + response.toString());
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Error.Response", error.getMessage());
                    }
                }
        );

        MySingleton.getInstance(this).addToRequestQueue(getRequest);
    }

    public void postMessage(View view) {
        final String url = "http://192.168.1.5:3000/polygon";

        JSONObject mainobj = new JSONObject();
        JSONArray coordinates = new JSONArray();
        JSONObject point1 = new JSONObject();
        JSONObject point2 = new JSONObject();
        JSONObject point3 = new JSONObject();

        try {
            point1.put("latitude", 25.774);
            point1.put("longitude", -80.190);
            point2.put("latitude", 18.466);
            point2.put("longitude", -66.118);
            point3.put("latitude", 32.321);
            point3.put("longitude", -64.757);
            coordinates.put(point1);
            coordinates.put(point2);
            coordinates.put(point3);
            mainobj.put("name", "Polygon");
            mainobj.put("type", "Polygon");
            mainobj.put("coordinates", coordinates);

            System.out.println(mainobj);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest postRequest = new JsonObjectRequest(Request.Method.POST, url, mainobj,
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
