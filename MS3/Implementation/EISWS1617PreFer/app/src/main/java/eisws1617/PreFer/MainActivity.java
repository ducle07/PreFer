package eisws1617.PreFer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

//Die Main-Activity beinhaltet lediglich einen Start-Button, der in die ListActivity leitet.

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void goList(View view) {
        Intent intent = new Intent(this, ListActivity.class);
        startActivity(intent);
    }
}
