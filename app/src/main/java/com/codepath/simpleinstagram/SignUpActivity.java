package com.codepath.simpleinstagram;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class SignUpActivity extends AppCompatActivity {

    EditText username;
    EditText password;
    Button signUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        username = findViewById(R.id.newUsernameView);
        password = findViewById(R.id.newPasswordView);
        signUp = findViewById(R.id.submitSignUpBtn);

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Create the ParseUser
                ParseUser user = new ParseUser();

                // Set core properties
                user.setUsername(username.getText().toString());
                user.setPassword(password.getText().toString());

                // Invoke signUpInBackground
                user.signUpInBackground(new SignUpCallback() {

                    @Override
                    public void done(ParseException e) {
                        if (e == null) {
                            // Hooray! Let them use the app now.
                            Log.d("SignUpActivity", "Sign up successful!");
                            final Intent intent = new Intent(SignUpActivity.this, TimelineActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            // Sign up didn't succeed. Look at the ParseException
                            // to figure out what went wrong
                            Log.e("SignUpActivity", "Sign up failure.");
                            e.printStackTrace();
                        }
                    }
                });

            }
        });
    }
}
