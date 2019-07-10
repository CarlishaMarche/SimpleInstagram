package com.codepath.simpleinstagram;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.codepath.simpleinstagram.fragments.ComposeFragment;
import com.codepath.simpleinstagram.fragments.PostsFragment;

public class TimelineActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);

        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        final FragmentManager fragmentManager = getSupportFragmentManager();

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                Fragment fragment = new ComposeFragment();
                //TODO correct fragments
                switch (menuItem.getItemId()) {
                    case R.id.action_compose:
                        // do something here
                        fragment = new ComposeFragment();
                        break;
                    case R.id.action_home:
                        // do something here
                        fragment = new PostsFragment();
                        break;
                    case R.id.action_notifications:
                        // do something here
                        fragment = new ComposeFragment();
                        break;
                    case R.id.action_profile:
                        //do something here
                        fragment = new ComposeFragment();
                        break;
                    case R.id.action_search:
                        //do something here
                        fragment = new ComposeFragment();
                        break;
                    default:
                        fragment = new ComposeFragment();
                        break;
                }

                fragmentManager.beginTransaction().replace(R.id.flContainer, fragment).commit();
                return true;
            }
        });

        //set default selection
        bottomNavigationView.setSelectedItemId((R.id.action_home));
    }

}
