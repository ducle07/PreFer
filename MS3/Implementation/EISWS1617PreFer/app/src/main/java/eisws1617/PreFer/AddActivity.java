package eisws1617.PreFer;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

//Diese Activity stellt den Screen zum Hinzufügen eines neuen Feldes dar.
//Sie besteht aus zwei weiteren Fragments.

public class AddActivity extends AppCompatActivity
        implements AddTabKarteFragment.OnDataPass {

    private SectionsPagerAdapter mSectionsPagerAdapter;

    private ViewPager mViewPager;

    JSONObject json = new JSONObject();

    //Es wird die View für die Activity erstellt, sobald die Activity geöffnet wird.
    //Sie besteht aus einem Tabs-Menü.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
    }

    //Das Menü wird erzeugt, sobald Activity geöffnet wurde.
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.check:
                //Ein JSON-Objekt wird gebaut, das aus dem Namen, der Größe des Feldes und
                //der Koordinaten des Polygons besteht.
                //Dieses JSON-Objekt wird durch ein POST-Request an den Server gesendet,
                //sobald der Nutzer den Button "check" betätigt.

                EditText editText = (EditText) findViewById(R.id.editText_name);
                JSONObject tempObject = new JSONObject();
                try {
                    tempObject.put("name", editText.getText().toString());
                    tempObject.put("size", json.get("size"));
                    JSONArray tempArray = json.getJSONArray("outline");
                    tempObject.put("outline", tempArray);
                    sendPOST(tempObject);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                Intent intent = new Intent(AddActivity.this, ListActivity.class);
                AddActivity.this.startActivity(intent);

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    //In dieser Adapter-Klasse werden die Tabs des Menü erzeugt.
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    AddTabInfoFragment tab1 = new AddTabInfoFragment();
                    return tab1;
                case 1:
                    AddTabKarteFragment tab2 = new AddTabKarteFragment();
                    return tab2;
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Info";
                case 1:
                    return "Karte";
            }
            return null;
        }
    }

    //Diese Methode dient zum Datenaustausch zwischen Fragment und Activity.
    public void onDataPass(JSONObject data) {
        json = getJSON(data);
    }

    //Diese Methode wird benötigt, um die Daten aus der onDataPass-Methode zu holen
    //und als JSONObject zurückzugeben.
    public JSONObject getJSON(JSONObject json) {
        return json;
    }

    //Diese Methode führt ein POST-Request mithilfe der volley-Library aus auf die URL "url".
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

