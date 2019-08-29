package com.nawdroidtv.layartancep21.view;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.nawdroidtv.layartancep21.R;
import com.nawdroidtv.layartancep21.fragment.FavoriteFragment;
import com.nawdroidtv.layartancep21.fragment.NowFragment;
import com.nawdroidtv.layartancep21.fragment.SearchFragment;
import com.nawdroidtv.layartancep21.fragment.UpcomingFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.navigation)
    BottomNavigationView navigation;


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_now:
                    NowFragment nowFragment = new NowFragment();
                    FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.fl_main, nowFragment);
                    fragmentTransaction.commit();
                    return true;
                case R.id.navigation_up:
                    UpcomingFragment upFragment = new UpcomingFragment();
                    fragmentTransaction = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.fl_main, upFragment);
                    fragmentTransaction.commit();
                    return true;
                case R.id.navigation_fav:
                    FavoriteFragment favFragment = new FavoriteFragment();
                    fragmentTransaction = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.fl_main, favFragment);
                    fragmentTransaction.commit();
                    return true;
                case R.id.navigation_sear:
                    SearchFragment searchFragment = new SearchFragment();
                    fragmentTransaction = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.fl_main, searchFragment);
                    fragmentTransaction.commit();
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("");
        }

        NowFragment nowFragment = new NowFragment();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fl_main, nowFragment);
        fragmentTransaction.commit();

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.item, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            startActivity(new Intent(Settings.ACTION_LOCALE_SETTINGS));
            return true;
        } else if (id == R.id.action_reminder) {
            startActivity(new Intent(getApplicationContext(), SettingActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
