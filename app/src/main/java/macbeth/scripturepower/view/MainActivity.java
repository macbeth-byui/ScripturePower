package macbeth.scripturepower.view;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.Window;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;  // TODO: Use ViewPager2 and FragmentStateAdapter
import androidx.viewpager.widget.ViewPager;


import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.HashMap;
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

        // Remove the title bar to provide more room for the app
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        //getSupportActionBar().hide();

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

 /*   @Override
    protected void onStop() {
        super.onStop();
        BrowseFragment fragment = ((BrowseFragment)adapter.getFragment(0));
        if (fragment != null) {
            fragment.rememberScripture();
        }
    }*/

    public void registerFragment(MainPresenter.Listener fragment) {
        presenter.registerUsers(fragment);
    }

    private void loadFragment(int menuId) {
        ActionBar actionBar = getSupportActionBar();

        switch (menuId) {
            case R.id.menu_browse :
                viewPager.setCurrentItem(0);
                //actionBar.setTitle("Browse Scriptures");
                break;
            case R.id.menu_search :
                viewPager.setCurrentItem(1);
                //actionBar.setTitle("Search Scriptures");
                break;
            case R.id.menu_settings :
                viewPager.setCurrentItem(2);
                //actionBar.setTitle("Settings");
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

        private Map<Integer, Fragment> fragments;

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
