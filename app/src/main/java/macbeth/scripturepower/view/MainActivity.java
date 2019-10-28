package macbeth.scripturepower.view;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import macbeth.scripturepower.R;
import macbeth.scripturepower.presenter.MainPresenter;

public class MainActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private CollectionPagerAdapter adapter;
    private MainPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        presenter = new MainPresenter(this);
        presenter.initialize();

        adapter = new CollectionPagerAdapter(getSupportFragmentManager());
        viewPager = findViewById(R.id.fragment);
        viewPager.setAdapter(adapter);

        loadFragment(R.id.menu_browse);

        BottomNavigationView bnv = (BottomNavigationView) findViewById(R.id.navigation);
        bnv.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                loadFragment(item.getItemId());
                return true;
            }
        });

    }

    @Override
    protected void onStart() {  // View
        super.onStart();
    }

    @Override
    protected void onResume() {  // Interact
        super.onResume();
    }

    @Override
    protected void onPause() {  // No Interact ... release onResume
        super.onPause();
    }

    @Override
    protected void onStop() { // No View ... Good place to save .. release onStart
        super.onStop();
    }

    @Override
    protected void onDestroy() { // Release onCreate
        super.onDestroy();
    }

    private void loadFragment(int menuId) {
        ActionBar actionBar = getSupportActionBar();
        switch (menuId) {
            case R.id.menu_browse :
                viewPager.setCurrentItem(0);
                actionBar.setTitle("Browse Scriptures");
                break;
            case R.id.menu_search :
                viewPager.setCurrentItem(1);
                actionBar.setTitle("Search Scriptures");
                break;
            case R.id.menu_settings :
                viewPager.setCurrentItem(2);
                actionBar.setTitle("Settings");
                break;
        }
    }

    public MainPresenter getPresenter() {
        return presenter;
    }

    private class CollectionPagerAdapter extends FragmentPagerAdapter {

        public CollectionPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int i) {
            Fragment fragment;

            switch(i) {
                case 0:
                    fragment = new BrowseFragment();
                    break;
                case 1:
                    fragment = new SearchFragment();
                    break;
                case 2:
                    fragment = new SettingsFragment();
                    break;

                default:
                    fragment = null;
            }
            return fragment;
        }

        @Override
        public int getCount() {
            return 3;
        }

    }
}
