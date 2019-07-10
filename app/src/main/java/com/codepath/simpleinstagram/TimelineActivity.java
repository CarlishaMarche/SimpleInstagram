package com.codepath.simpleinstagram;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.codepath.simpleinstagram.fragments.ComposeFragment;
import com.codepath.simpleinstagram.fragments.PostsFragment;
import com.codepath.simpleinstagram.fragments.ProfileFragment;
import com.parse.ParseUser;

public class TimelineActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private Button logOutBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);

        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        logOutBtn = findViewById(R.id.logOutBtn);

        logOutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ParseUser.logOut();
//                ParseUser currentUser = ParseUser.getCurrentUser();
                Intent i = new Intent(TimelineActivity.this, LogInActivity.class);
                startActivity(i);
                finish();
            }
        });

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
                        fragment = new ComposeFragment();
                        break;
                    case R.id.action_profile:
                        //do something here
                        fragment = new ProfileFragment();
                        break;
                    case R.id.action_search:
                        //do something here
                        fragment = new ComposeFragment();
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

}
