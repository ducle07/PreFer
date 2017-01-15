package eisws1617.PreFer;

import java.util.ArrayList;
import java.util.List;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageButton;

public class ListActivity extends AppCompatActivity {

    ImageButton floatButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        floatButton = (ImageButton) findViewById(R.id.imageButton);
        floatButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(ListActivity.this, AddActivity.class);
                ListActivity.this.startActivity(intent);
            }
        });

        List valueList = new ArrayList<String>();
        for (int i = 0; i < 10; i++) {
            valueList.add("Hofmeizen" + i);
        }

        ListAdapter adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, valueList);

        final ListView lv = (ListView) findViewById(R.id.listView1);

        lv.setAdapter(adapter);

        lv.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                Intent intent = new Intent();
                intent.setClassName(getPackageName(), getPackageName() + ".DisplayActivity");
                intent.putExtra("selected", lv.getAdapter().getItem(arg2).toString());
                startActivity(intent);
            }
        });
    }
}
