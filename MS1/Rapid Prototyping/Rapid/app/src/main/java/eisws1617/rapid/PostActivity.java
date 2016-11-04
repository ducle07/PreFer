package eisws1617.rapid;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

public class PostActivity extends AppCompatActivity {
    EditText mEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
    }

    public void postMessage(View view) {
        final String url = "http://192.168.1.5:3000/users";
        mEdit = (EditText) findViewById(R.id.editText);

        JSONObject obj = new JSONObject();

        try {
            obj.put("email", new String(mEdit.getText().toString()));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest postRequest = new JsonObjectRequest(Request.Method.POST, url, obj,
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
