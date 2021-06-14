package com.d1m0hkrasav4ik.notemanager;

import android.content.res.Configuration;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.d1m0hkrasav4ik.notemanager.ui.DescriptionFragment;

public class DescriptionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_description);

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            finish();
            return;
        }

        if (savedInstanceState == null) {
            DescriptionFragment f = new DescriptionFragment();
            f.setArguments(getIntent().getExtras());

            //добавляем фрагмент на активити
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, f)
                    .commit();
        }
    }
}