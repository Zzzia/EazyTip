package com.zia.notice.page.main;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.facebook.stetho.Stetho;
import com.zia.notice.R;
import com.zia.notice.database.AppDatabase;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private BottomNavigationView bottomNavigationView;
    private MainFragment mainFragment;
    private MessageFragment messageFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_main);
        setTitle("简忆");
        Stetho.initializeWithDefaults(this);
        findWidgets();
        initView();
        setDrawerLayout();
        transaction(mainFragment);
    }

    private void initView() {
        mainFragment = new MainFragment();
        messageFragment = new MessageFragment();
        transaction(messageFragment);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
    }

    private void setDrawerLayout() {
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.drawer_open, R.string.drawer_close);
        actionBarDrawerToggle.syncState();
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
    }

    private void findWidgets() {
        bottomNavigationView = findViewById(R.id.main_bottomNavigationView);
        drawerLayout = findViewById(R.id.main_drawerLayout);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                actionBarDrawerToggle.onOptionsItemSelected(item);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        AppDatabase.destroyInstance();
        super.onDestroy();
    }

    private void transaction(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.main_frameLayout, fragment);
        transaction.commit();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.navigation_home:
                transaction(mainFragment);
                return true;
            case R.id.navigation_message:
                transaction(messageFragment);
                return true;
        }
        return false;
    }
}
