package com.d1m0hkrasav4ik.notemanager;

import android.os.Bundle;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.d1m0hkrasav4ik.notemanager.data.Bridge;
import com.d1m0hkrasav4ik.notemanager.data.NoteCardSourceImpl;
import com.d1m0hkrasav4ik.notemanager.ui.AboutAppFragment;
import com.d1m0hkrasav4ik.notemanager.ui.NameNoteFragment;
import com.d1m0hkrasav4ik.notemanager.ui.SettingsFragment;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //init
        initView();
        if (Bridge.data == null) {
            Bridge.data = new NoteCardSourceImpl(getResources()).initData().initDataFireBase();
        }
    }

    private void initView() {
        Toolbar toolbar = initToolBar();
        initDrawer(toolbar);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private Toolbar initToolBar() {
        Toolbar toolBar = findViewById(R.id.toolbar);
        setSupportActionBar(toolBar);
        return toolBar;
    }

    private void initDrawer(Toolbar toolbar) {
        final DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close

        );
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = findViewById(R.id.nav_view);

        final int notes = R.id.action_main;
        final int settings = R.id.action_settings;
        final int aboutApp = R.id.action_about;
        navigationView.setNavigationItemSelectedListener(item -> {
            int id = item.getItemId();
            switch (id) {
                case notes:
                    addFragment(NameNoteFragment.newInstance());
                    return true;
                case settings:
                    addFragment(new SettingsFragment());
                    return true;
                case aboutApp:
                    addFragment(new AboutAppFragment());
                    return true;
            }

            return false;
        });
    }

    public void addFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.notes_app_сontainer, fragment);
        transaction.commit();
    }


}