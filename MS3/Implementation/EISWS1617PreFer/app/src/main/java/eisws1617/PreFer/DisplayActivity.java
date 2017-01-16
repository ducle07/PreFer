package eisws1617.PreFer;

import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

//Diese Activity stellt die in dem Server gespeicherten Daten dar,
//indem sie die Felddaten aus dem Server holt und präsentiert.

public class DisplayActivity extends AppCompatActivity {

    private SectionsPagerAdapter mSectionsPagerAdapter;

    private ViewPager mViewPager;

    //Es wird die View für die Activity erstellt, sobald die Activity geöffnet wird.
    //Sie besteht aus einem Tabs-Menü.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);

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
        getMenuInflater().inflate(R.menu.display_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    //In dieser Adapter-Klasse werden die Tabs des Menü erzeugt.

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Bundle extras = getIntent().getExtras();

            switch (position) {
                case 0:
                    DisplayTabInfoFragment tab1 = new DisplayTabInfoFragment();
                    return tab1;
                case 1:
                    DisplayTabKarteFragment tab2 = new DisplayTabKarteFragment();
                    tab2.setArguments(extras);
                    return tab2;
                case 2:
                    DisplayTabDuengerFragment tab3 = new DisplayTabDuengerFragment();
                    tab3.setArguments(extras);
                    return tab3;
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Info";
                case 1:
                    return "Karte";
                case 2:
                    return "Duenger";
            }
            return null;
        }
    }
}
