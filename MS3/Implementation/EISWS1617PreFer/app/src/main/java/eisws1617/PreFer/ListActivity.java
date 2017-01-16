package eisws1617.PreFer;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageButton;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;

public class ListActivity extends AppCompatActivity {

    ImageButton floatButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        floatButton = (ImageButton) findViewById(R.id.myFAB);
        floatButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(ListActivity.this, AddActivity.class);
                ListActivity.this.startActivity(intent);
            }
        });

        final ListView lv = (ListView) findViewById(R.id.listView1);

        //Bevor die ListView erzeugt werden kann, müssen die Felddaten aus dem Server geholt werden
        //und deren Namen als ListItem präsentiert werden.

        final String url = "http://192.168.1.12:3000/field";
        JsonArrayRequest getRequest = new JsonArrayRequest(url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d("Response", response.toString());
                        try {
                            List namesList = new ArrayList<String>();
                            for(int i=0; i<response.length(); i++) {
                                namesList.add(response.getJSONObject(i).get("name"));
                            }
                            ListAdapter adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, namesList) {
                                @Override
                                public View getView(int position, View convertView, ViewGroup parent) {
                                    TextView textView = (TextView) super.getView(position, convertView, parent);
                                    textView.setTextColor(Color.BLACK);
                                    return textView;
                                }
                            };
                            lv.setAdapter(adapter);
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

        MySingleton.getInstance(this).addToRequestQueue(getRequest);

        //Im onItemClickListener wird gesagt, dass beim Klicken eines ListItems die DisplayActivity dieses Item geöffnet werden soll.

        lv.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                Intent intent = new Intent(getApplicationContext(), DisplayActivity.class);
                intent.putExtra("name", lv.getAdapter().getItem(arg2).toString());
                startActivity(intent);
            }
        });
    }
}
