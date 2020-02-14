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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import macbeth.scripturepower.R;
import macbeth.scripturepower.model.SearchRecord;
import macbeth.scripturepower.presenter.MainPresenter;

// TODO: Remember fragment you were in before and start there

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

    public void registerFragment(MainPresenter.Listener fragment) {
        presenter.registerUsers(fragment);
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

    public void loadBrowseScripture(SearchRecord record) {
        loadFragment(R.id.menu_browse);
        ((BrowseFragment)adapter.getFragment(0)).jumpToScripture(record);
    }

    public MainPresenter getPresenter() {
        return presenter;
    }

    private class CollectionPagerAdapter extends FragmentPagerAdapter {

        private Map<Integer,Fragment> fragments;

        public CollectionPagerAdapter(FragmentManager fm) {
            super(fm);
            fragments = new HashMap<>();
        }

        @Override
        public Fragment getItem(int i) {
            Fragment fragment;

            switch(i) {
                case 0:
                    fragment = new BrowseFragment();
                    fragments.put(i, fragment);
                    break;
                case 1:
                    fragment = new SearchFragment();
                    fragments.put(i, fragment);
                    break;
                case 2:
                    fragment = new SettingsFragment();
                    fragments.put(i, fragment);
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

        public Fragment getFragment(int i) {
            return fragments.get(i);
        }

    }
}
