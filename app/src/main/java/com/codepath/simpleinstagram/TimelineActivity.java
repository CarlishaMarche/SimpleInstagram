package com.codepath.simpleinstagram;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.codepath.simpleinstagram.fragments.ComposeFragment;
import com.codepath.simpleinstagram.fragments.PostsFragment;
import com.codepath.simpleinstagram.fragments.ProfileFragment;

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
                Fragment fragment = new PostsFragment();
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
                        break;
                    case R.id.action_profile:
                        //do something here
                        fragment = new ProfileFragment();
                        break;
                    case R.id.action_search:
                        //do something here
                        break;
                    default:
                        fragment = new PostsFragment();
                        break;
                }

                fragmentManager.beginTransaction().replace(R.id.flContainer, fragment).commit();
                return true;
            }
        });

        //set default selection
        bottomNavigationView.setSelectedItemId((R.id.action_home));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //only the profile image can be clicked, so I will skip writing switch statement
        Intent i = new Intent(TimelineActivity.this, ProfileActivity.class);
        startActivity(i);
        finish();
        return true;
    }
}
